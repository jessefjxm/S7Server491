package com.bdoemu.gameserver.model.move;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.ActionVerifyOptionConfig;
import com.bdoemu.core.network.sendable.SMLoadField;
import com.bdoemu.core.network.sendable.SMLoadFieldComplete;
import com.bdoemu.core.network.sendable.SMMovePlayerNak;
import com.bdoemu.gameserver.model.ai.deprecated.CreatureAI;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.utils.MathUtils;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Player movement controller.
 * This controller is used to prevent players from using cheats
 * to move to their specified destination more quicker.
 * @author H1X4
 */
public class PlayerMovementController extends AMovementController<Player> {
    private static Logger _log = LoggerFactory.getLogger(PlayerMovementController.class);
    private long _lastMovementUpdateTime;   // When was the last movement activity by the player character?
    private long _movementUnlockTime;       // When should the movement be unlocked if player did not send
                                            // a CMSetReadyToPlay packet or is using speed-hacks.
    private int _movementTolerance;         // How many times the player has triggered the protection.
    private Location _oldLocation;          // Where to teleport the player after cheat detected.
    private long _oldLocationTick;          // Last location saved time.

    /**
     * Default constructor for player movement
     * @param owner Owner of the player movement controller
     */
    public PlayerMovementController(Player owner) {
        super(owner);

        _lastMovementUpdateTime = GameTimeService.getServerTimeInMillis();
        _movementUnlockTime = GameTimeService.getServerTimeInMillis();
        _oldLocationTick = GameTimeService.getServerTimeInMillis();
        _oldLocation = new Location(owner.getLocation());
    }

    /**
     * Checks if owner of this controller can move to specified location.
     * @param x Position X
     * @param y Position Y
     * @return true if can move, otherwise false.
     */
    @Override
    public boolean canMove(double x, double y) {
        Location originLocation = getOwner().getLocation();

        // If user is blocked from moving, due to suspected cheating, he will not be permitted from moving.
        if (isMovementBlocked(originLocation))
            return false;

        // If user is currently still in a loading screen, he will not be allowed to move.
        // This prevents issues like denying player movement in loading screen.
        if (!getOwner().isReadyToPlay()) {
            getOwner().sendPacket(new SMMovePlayerNak(originLocation.getX(), originLocation.getY(), originLocation.getZ(), EStringTable.eErrNoActorActionIsRestricted));
            return false;
        }

        // Don't allow user to update it's location when he is dead.
        if (getOwner().isDead()) {
            getOwner().sendPacket(new SMMovePlayerNak(originLocation.getX(), originLocation.getY(), originLocation.getZ(), EStringTable.eErrNoActorIsDead));
            return false;
        }

        double distanceToPosition = MathUtils.getDistance(originLocation.getX(), originLocation.getY(), x, y);
        float movementSpeed = getOwner().getActionStorage().getActionChartActionT().getMaxMoveSpeed();

        // Vehicle movement bonus
        if (isMounted())
        {
            Creature vehicle = getOwner().getCurrentVehicle();
            movementSpeed += vehicle.getActionStorage().getActionChartActionT().getMaxMoveSpeed();
            movementSpeed += vehicle.getGameStats().getMaxMoveSpeedRate().getIntMaxValue() / 10_000;
            if (vehicle.getActionStorage().getAction().getActionChartActionT().getApplySpeedBuffType().isMove())
                movementSpeed *= (1 + vehicle.getGameStats().getMoveSpeedRate().getMoveSpeedRate() / 1_000_000.0f);
        }
        else // Player movement bonus.
        {
            if (getOwner().getActionStorage().getAction().getActionChartActionT().getApplySpeedBuffType().isMove())
                movementSpeed *= (1 + getOwner().getGameStats().getMoveSpeedRate().getMoveSpeedRate() / 1_000_000.0f);
        }

        double originToDestinationMovementSpeed = distanceToPosition / ((GameTimeService.getServerTimeInMillis() - _lastMovementUpdateTime) / 1000f);
        if (movementSpeed > 0 && isMoving() && !Double.isInfinite(originToDestinationMovementSpeed) && !Double.isNaN(originToDestinationMovementSpeed) && originToDestinationMovementSpeed > 0) {
            // Check if player is moving too fast, then restrict him.
            if (ActionVerifyOptionConfig.ACTION_VERIFY_MOVEMENT_ENABLE) {
                // If player moved more than our allowed distance, we should kill him!
                // Usually 2D distance is sufficient for this operation. 3D is not recommended.
                if (distanceToPosition > ActionVerifyOptionConfig.ACTION_VERIFY_MOVEMENT_MAX_DISTANCE_TOLERANCE) {
                    teleportToOldPosition();
                    return false;
                }


                movementSpeed *= getOwner().getActionStorage().getActionChartActionT().getAnimationSpeed();
                movementSpeed *= 2.5;
                //System.out.println("[" + getOwner().getName() + "] Movement speed: " + movementSpeed + "; Current: " + originToDestinationMovementSpeed + "; Tolerance: " + _movementTolerance);
                if (originToDestinationMovementSpeed > movementSpeed)
                {
                    if (++_movementTolerance > ActionVerifyOptionConfig.ACTION_VERIFY_MOVEMENT_TOLERANCE)
                    {
                        teleportToOldPosition();
                        //if (getOwner().isPlayer())
                        //    getOwner().sendMessage("You have moved too fast. Your movement will be limited for few seconds.", true);
                        _log.warn("[{}] Movement speed exceeded. Expected={}. Moved={}", getOwner().getName(), movementSpeed, originToDestinationMovementSpeed);
                        _movementUnlockTime = GameTimeService.getServerTimeInMillis() + ActionVerifyOptionConfig.ACTION_VERIFY_MOVEMENT_LOCK_TIME;
                        _movementTolerance = 0;
                        if (isMovementBlocked(originLocation))
                            return false;
                    }
                }
                else
                    _movementTolerance = 0;
            }

            // Will only add the distance traveled, if you are actually moving!
            getOwner().addDistanceTraveled(distanceToPosition);
        }
        _lastMovementUpdateTime = GameTimeService.getServerTimeInMillis();
        if (GameTimeService.getServerTimeInMillis() - _oldLocationTick > ActionVerifyOptionConfig.ACTION_VERIFY_MOVEMENT_SAVE_TIME) {
            _oldLocationTick = GameTimeService.getServerTimeInMillis();
            _oldLocation.setLocation(getOwner().getLocation());
        }
        return true;
    }

    /**
     * Teleport's player to an old position when requested.
     */
    private void teleportToOldPosition() {
        getOwner().sendPacket(new SMMovePlayerNak(_oldLocation.getX(), _oldLocation.getY(), _oldLocation.getZ(), EStringTable.eErrNoActionChartActionIsFast));
        //getOwner().sendPacket(new SMLoadField());
        //getOwner().setReadyToPlay(false);
        //World.getInstance().teleport(getOwner(), _oldLocation);
        //getOwner().sendPacket(new SMLoadFieldComplete());
    }

    /**
     * Checks if the controller is currently moving.
     * @return true if creature is moving, otherwise false.
     */
    @Override
    public boolean isMoving() {
        return GameTimeService.getServerTimeInMillis() - _lastMovementUpdateTime < 2000;
    }

    /**
     * Returns the location from where the character has started moving.
     * @return Origin location from where the character is currently moving.
     */
    @Override
    public Location getOrigin() {
        return null;
    }

    /**
     * Returns the destination to where the character is currently moving.
     * @return Destination to where the character is going to move.
     */
    @Override
    public Location getDestination() {
        return null;
    }

    /**
     * Cancels a movement task, when no movement is needed, or we need
     * to forcibly stop a moving player.
     */
    @Override
    public void cancelMoveTask() {
        // no impl
    }

    /**
     * Checks if movement is currently blocked.
     * @param originLocation The location where player will be teleported if failed.
     * @return true if movement has been blocked, false otherwise.
     */
    private boolean isMovementBlocked(Location originLocation) {
        if (_movementUnlockTime > GameTimeService.getServerTimeInMillis()) {
            getOwner().sendPacket(new SMMovePlayerNak(originLocation.getX(), originLocation.getY(), originLocation.getZ(), EStringTable.eErrNoActionChartActionIsFast));
            return true;
        }
        return false;
    }

    /**
     * Checks if player is currently mounted on a vehicle.
     * @return true if mounted, false otherwise.
     */
    private boolean isMounted() {
        return getOwner().getCurrentVehicle() != null;
    }

    /**
     * Returns player vehicle that is currently mounted on.
     * @apiNote Please use isMounted() if u need this function.
     * @return Creature object if vehicle exists.
     */
    private Creature getVehicle() {
        return getOwner().getServantController().getCurrentVehicle();
    }
}