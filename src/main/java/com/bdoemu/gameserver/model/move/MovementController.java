package com.bdoemu.gameserver.model.move;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.network.sendable.SMMoveNonPlayer;
import com.bdoemu.core.network.sendable.SMStartCharacterMoving;
import com.bdoemu.core.network.sendable.SMStopNonPlayerMoving;
import com.bdoemu.gameserver.model.actions.enums.ENaviType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.model.world.region.GameSector;
import com.bdoemu.gameserver.service.GeoService;
import com.bdoemu.gameserver.utils.MathUtils;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Non-Vehicle Movement Controller.
 * Used for all monster based movement generated path.
 * @author H1X4
 */
public class MovementController extends AMovementController<Creature> implements Runnable {
    private static final Logger                 _log = LoggerFactory.getLogger(MovementController.class);
    private Location                            _destinationLocation;
    private Long                                _movementTick;
    private Callable<Boolean>                   _movementCompleted;
    private Consumer<Method>                    _movementFailed;
    private Future<?>                           _movementTask;
    private ConcurrentLinkedQueue<Location>     _waypoints;
    private boolean                             _isSectorActive;
    private final Object                        _mutex = new Object();
    private Creature                            _followTarget;
    private ENaviType                           _navigationType;

    /**
     * Default constructor for movement controller.
     * @param owner Owner of the CreatureAI
     */
    public MovementController(Creature owner) {
        super(owner);
        _movementCompleted   = null;
        _movementFailed      = null;
        _destinationLocation = null;
        _waypoints           = new ConcurrentLinkedQueue<>();
        _movementTick        = System.currentTimeMillis();
        _movementTask        = null;
        _followTarget        = null;
    }

    /**
     * Checks if owner of this controller can move to specified location.
     * @param x Position X
     * @param y Position Y
     * @return true if can move, otherwise false.
     */
    @Override
    public boolean canMove(double x, double y) {
        return !getOwner().isDead();
    }

    /**
     * Starts moving the character to specified destination.
     *
     * @param destinations  List of destinations to move the character.
     * @param onEnd         Function to be called when destination has been reached.
     * @param onExit        Function to be called when we failed to reach the destination.
     * @param naviType      Navigation type of movement.
     */
    @Override
    public void startMove(Collection<Location> destinations, Callable<Boolean> onEnd, Consumer<Method> onExit, ENaviType naviType) {
        cancelMoveTask();
        if (destinations.isEmpty()) {
            onMovementComplete();
            return;
        }

        synchronized (_mutex) {
            _movementCompleted = onEnd;
            _movementFailed = onExit;
        }

        getOwner().getAi().setFindPathCompleted(false);
        _waypoints.addAll(destinations);
        _movementTick = System.currentTimeMillis();
        _navigationType = naviType;

        if (_movementTask == null || _movementTask.isDone())
            _movementTask = ThreadPool.getInstance().scheduleAiAtFixedRate(this, 300, 300, TimeUnit.MILLISECONDS);
    }

    /**
     * Starts moving the character to specified destination.
     *
     * @param destination           List of destinations to move the character.
     * @param movementCompleted     Function to be called when destination has been reached.
     * @param movementFailed        Function to be called when we failed to reach the destination.
     * @param naviType              Navigation type of movement.
     * @param moveStraight          Move straight, don't generate path!
     */
    @Override
    public void startMove(Location destination, Callable<Boolean> movementCompleted, Consumer<Method> movementFailed, ENaviType naviType, boolean moveStraight) {
        cancelMoveTask();
        if (destination == null || !destination.isValid()) {
            onMovementComplete();
            return;
        }

        synchronized (_mutex) {
            _movementCompleted = movementCompleted;
            _movementFailed = movementFailed;
        }

        getOwner().getAi().setFindPathCompleted(false);
        _waypoints.add(destination);
        _movementTick = System.currentTimeMillis();
        _navigationType = naviType;

        if (_movementTask == null || _movementTask.isDone())
            _movementTask = ThreadPool.getInstance().scheduleAiAtFixedRate(this, 0, 150, TimeUnit.MILLISECONDS);
    }

    /**
     * Starts moving the character to specified destination.
     *
     * @param target        Character to follow.
     * @param onEnd         Function to be called when destination has been reached.
     * @param onExit        Function to be called when we failed to reach the destination.
     * @param naviType      Navigation type of movement.
     */
    public void startFollow(Creature target, Callable<Boolean> onEnd, Consumer<Method> onExit, ENaviType naviType) {
        cancelMoveTask();
        if (!target.getLocation().isValid()) {
            onMovementComplete();
            return;
        }

        synchronized (_mutex) {
            _movementCompleted = onEnd;
            _movementFailed = onExit;
        }

        getOwner().getAi().setFindPathCompleted(false);
        _followTarget        = target;
        _destinationLocation = new Location(_followTarget.getLocation());
        _isSectorActive      = true;
        _movementTick        = System.currentTimeMillis();
        _navigationType      = naviType;

        if (_movementTask == null || _movementTask.isDone()) {
            notifySector(true);
            _movementTask = ThreadPool.getInstance().scheduleAiAtFixedRate(this, 150, 150, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Using Runnable to avoid it from getting stuck and to be able to make it cancellable.
     */
    @Override
    public void run() {
        updateMovement();
    }

    /**
     * Private task that updates the movement of current Creature.
     */
    private void updateMovement() {
        if (_movementTask == null)
            return;

        if (getOwner().isDead() || _followTarget != null && _followTarget.isDead()) {
            cancelMoveTask2(true);
            return;
        }

        // Movement is invalid! Queue a new movement and wait again!
        if (_destinationLocation == null)
        {
            if (_waypoints.peek() != null) {
                _destinationLocation = _waypoints.poll();

                if (getOwner().getLocation().getGameSector() != null && getOwner().getLocation().getGameSector().hasActiveNeighbours()) {
                    double validatedZ = GeoService.getInstance().validateZ(_destinationLocation.getX(), _destinationLocation.getY(), _destinationLocation.getZ());
                    if (validatedZ == Double.MIN_VALUE) {
                        _movementTick = System.currentTimeMillis();
                        getOwner().getAi().setFindPathCompleted(false);
                        cancelMoveTask2(true);
                        onMovementComplete();
                        return;
                    }

                    if (_destinationLocation != null)
                        _destinationLocation.setZ(validatedZ);
                    else {
                        _movementTick = System.currentTimeMillis();
                        getOwner().getAi().setFindPathCompleted(false);
                        cancelMoveTask2(true);
                        onMovementComplete();
                        return;
                    }
                    _isSectorActive = true;
                }
                else
                    _isSectorActive = false;
                notifySector(true);
            }
            else
            {
                _movementTick = System.currentTimeMillis();
                getOwner().getAi().setFindPathCompleted(true);
                cancelMoveTask2(true);
                onMovementComplete();
                return;
            }
        }

        if (_destinationLocation != null) {
            // Update current character movement, the location.
            float deltaTime = (System.currentTimeMillis() - _movementTick) / 1000.0f;
            float movementSpeed = getOwner().getActionStorage().getMoveSpeed();
            if (getOwner().getActionStorage().getAction().getActionChartActionT().getApplySpeedBuffType().isMove())
                movementSpeed *= (getOwner().getGameStats().getMoveSpeedRate().getMoveSpeedRate() + 1_000_000.0f) / 1_000_000.0f;

            Location ownerLocation = getOwner().getLocation();

            double movementDirectionX = _destinationLocation.getX() - ownerLocation.getX();
            double movementDirectionY = _destinationLocation.getY() - ownerLocation.getY();
            double movementDirectionZ = _destinationLocation.getZ() - ownerLocation.getZ();

            double directionLength = Math.sqrt(Math.pow(movementDirectionX, 2) + Math.pow(movementDirectionY, 2) + Math.pow(movementDirectionZ, 2));
            movementDirectionX /= directionLength;
            movementDirectionY /= directionLength;
            movementDirectionZ /= directionLength;

            ownerLocation.setXYZ(
                    ownerLocation.getX() + movementDirectionX * movementSpeed * deltaTime,
                    ownerLocation.getY() + movementDirectionY * movementSpeed * deltaTime,
                    ownerLocation.getZ() + movementDirectionZ * movementSpeed * deltaTime
            );

            if (_isSectorActive && !_navigationType.isAir()) {
                double validatedZ = GeoService.getInstance().validateZ(ownerLocation.getX(), ownerLocation.getY(), ownerLocation.getZ());
                if (validatedZ == Double.MIN_VALUE) {
                    getOwner().getAi().setFindPathCompleted(false);
                    cancelMoveTask2(true);
                    onMovementComplete();
                    return;
                }

                ownerLocation.setZ(validatedZ);
            }

            if (ownerLocation.isValid() && World.getInstance().getWorldMap().updateLocation(getOwner(), ownerLocation.getX(), ownerLocation.getY())) {
                double distanceToPoint = MathUtils.getDistance(ownerLocation, _destinationLocation) - getOwner().getTemplate().getBodySize();
                notifyVehicleMovement();

                if (_followTarget != null) {
                    distanceToPoint -= _followTarget.getTemplate().getBodySize();

                    if (!MathUtils.isInRange(_destinationLocation, _followTarget.getLocation(), 150)) {
                        _destinationLocation.setLocation(_followTarget.getLocation());
                        notifySector(true);
                    } else { // No more waypoint's available. Send movement stop and events.
                        if (distanceToPoint < 75 || distanceToPoint < getOwner().getTemplate().getAttackRange() && getOwner().getAi().getBehavior().isChase()) {
                            getOwner().getAi().setFindPathCompleted(true);
                            cancelMoveTask2(false);
                            onMovementComplete();
                        }
                    }
                } else {
                    // Check if we are near another destination, then we will
                    // switch to another waypoint instead to continue our journey.
                    Location nextWaypoint = _waypoints.peek();
                    if (nextWaypoint != null) {
                        if (distanceToPoint < 100)
                            _destinationLocation = null;
                    } else { // No more waypoint's available. Send movement stop and events.
                        if (distanceToPoint < 75 || distanceToPoint < getOwner().getTemplate().getAttackRange() && getOwner().getAi().getBehavior().isChase()) {
                            getOwner().getAi().setFindPathCompleted(true);
                            cancelMoveTask2(false);
                            onMovementComplete();
                        }
                    }
                }
            } else {
                getOwner().getAi().setFindPathCompleted(false);
                cancelMoveTask2(true);
                onMovementComplete();
            }
        }
        _movementTick = System.currentTimeMillis();
    }

    /**
     * Sends a vehicle movement update whenever player moves.
     */
    private void notifyVehicleMovement() {
        if (getOwner() != null && getOwner().isVehicle() && getOwner().getOwner() != null && getOwner().getOwner().isPlayer())
            getOwner().sendBroadcastPacket(new SMMoveNonPlayer(getOwner()));
    }

    /**
     * Notifies current game sector if movement should be started or stopped.
     * @param movementStart true if started, otherwise false.
     */
    private void notifySector(boolean movementStart) {
        GameSector gameSector = getOwner().getLocation().getGameSector();
        if (gameSector != null && gameSector.hasActiveNeighbours()) {
            if (movementStart)
                getOwner().sendBroadcastPacket(new SMStartCharacterMoving(getOwner()));
            else
                getOwner().sendBroadcastPacket(new SMStopNonPlayerMoving(getOwner()));
            _isSectorActive = true;
        }
        else
            _isSectorActive = false;
    }

    /**
     * Checks if the controller is currently moving.
     * @return true if creature is moving, otherwise false.
     */
    @Override
    public boolean isMoving() {
        return _movementTask != null && !_movementTask.isDone();
    }

    /**
     * Returns the location from where the character has started moving.
     * @return Origin location from where the character is currently moving.
     */
    @Override
    public Location getOrigin() {
        return getOwner().getLocation();
    }

    /**
     * Returns the destination to where the character is currently moving.
     * @return Destination to where the character is going to move.
     */
    @Override
    public Location getDestination() {
        return _destinationLocation != null ? _destinationLocation : getOrigin();
    }

    /**
     * Cancels a movement task, when no movement is needed, or we need
     * to forcibly stop a moving player.
     */
    @Override
    public void cancelMoveTask() {
        if (_movementTask != null) {
            cancelMoveTask2(true);
            _movementTask = null;
        }
    }

    /**
     * Cancels a movement task that is currently running and notifies the game sector
     * that the movement has stopped.
     * @apiNote Does not call movement complete actions.
     */
    private void cancelMoveTask2(boolean shouldNotifySector) {
        if (_movementTask != null && !_movementTask.isDone()) {
            _movementTask.cancel(true);

            if (shouldNotifySector)
                notifySector(false);
        }
    }

    /**
     * An event fired whenever movement controller has finished the task.
     */
    private void onMovementComplete() {
        Callable<Boolean> movementCompleted;
        Consumer<Method> movementFailed;

        synchronized (_mutex) {
            movementCompleted = _movementCompleted;
            movementFailed = _movementFailed;

            _movementCompleted = null;
            _movementFailed = null;
        }

        boolean bMovementCompleted = false;
        if (movementCompleted != null) {
            try {
                bMovementCompleted = movementCompleted.call();
            } catch (Exception e) {
                _log.error("An error occurred while completing a movement.", e);
            }
        }

        if (!bMovementCompleted && movementFailed != null)
            movementFailed.accept(null);
    }
}