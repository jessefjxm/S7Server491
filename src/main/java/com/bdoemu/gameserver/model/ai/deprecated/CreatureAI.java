package com.bdoemu.gameserver.model.ai.deprecated;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.commons.utils.ClassUtils;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMAiWorldNotifier;
import com.bdoemu.gameserver.dataholders.xml.WaypointData;
import com.bdoemu.gameserver.model.actions.ActionStorage;
import com.bdoemu.gameserver.model.actions.ActionTask;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EActionType;
import com.bdoemu.gameserver.model.actions.enums.EBattleAimedActionType;
import com.bdoemu.gameserver.model.actions.enums.ENaviType;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.agrolist.AggroInfo;
import com.bdoemu.gameserver.model.creature.agrolist.IAggroList;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.enums.EVehicleType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.misc.enums.ETradeCommerceType;
import com.bdoemu.gameserver.model.skills.buffs.ModuleBuffType;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.team.party.CreatureParty;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.model.waypoints.WaypointT;
import com.bdoemu.gameserver.model.weather.enums.EWeatherFactorType;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.model.world.region.GameSector;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.service.GeoService;
import com.bdoemu.gameserver.utils.MathUtils;
import com.bdoemu.gameserver.utils.comparators.CreatureHateComparator;
import com.bdoemu.gameserver.utils.comparators.CreatureNearestComparator;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Rewritten and improved MonsterAI.
 * @author H1X4
 */
public abstract class CreatureAI implements Runnable {
    /********************************************************************************************************************/
    /****************************************** DEFAULT IMPLEMENTATION **************************************************/
    /********************************************************************************************************************/
    private static Logger                   _log = LoggerFactory.getLogger(CreatureAI.class);   // Logger class
    private static Map<Long, Method>        _handlers;                                          // All handlers registered globally
    private boolean                         _aiStopped;                                         // Notifies that current AI is stopped.
    private boolean                         _isFindPathCompleted;                               // Internal variable for path manager.
    private int                             _callCount;                                         // How many times a class has been called.
    private Map<Long, Integer>           	_aiVariables;                                       // AI and Script variable container.
    private Map<Long, Integer[]>           	_aiVariableArray;                                   // Script variable array container.
    private ScheduledFuture                 _currentTask;                                       // Currently being executed task.
    private Consumer<Method>                _scheduledMethod;                                   // A scheduled method that will be executed.
    protected Creature                      _actor;                                             // Actor that owns this MonsterAI.
    protected Creature                      _sender;                                            // Actor that an handler has been executed by.
    protected Long	                        _state;                                             // Currently executing state.
    protected EAIBehavior                   _behavior;                                          // State behavior.
    private Long                            _aiStartTime;                                       // Start time of the AI.
    // Initializes all handlers that are usable.
    static {
        _handlers = new HashMap<>();
        @SuppressWarnings("unchecked")
        List<Method> handlerMethods = (List<Method>) ClassUtils.getMethodsAnnotatedWith(CreatureAI.class, (Class) IAIHandler.class);
        if (!handlerMethods.isEmpty()) {
            for (Method handlerMethod : handlerMethods) {
                IAIHandler iaiHandler = handlerMethod.getAnnotation(IAIHandler.class);
                _handlers.put(Long.valueOf(iaiHandler.hash()), handlerMethod);
            }
        }
    }
    /********************************************************************************************************************/
    /****************************************** DEFAULT IMPLEMENTATION **************************************************/
    /********************************************************************************************************************/
    /**
     * Default constructor for CreatureAI.
     * @param actor         Actor that owns CreatureAI.
     * @param aiVariables   AI variables received by Action Index from Action Charts.
     */
    public CreatureAI(Creature actor, Map<Long, Integer> aiVariables) {
        _callCount 				= 0;
        _state 					= 0L;
        _aiStopped 				= false;
        _isFindPathCompleted 	= false;
        _behavior 				= EAIBehavior.idle;
        _actor 					= actor;
        _aiVariables 			= aiVariables;
        _aiVariableArray        = new HashMap<>();
    }

    /**
     * Initial state of a script.
     * @param blendTime The blend time that starts with.
     * @implNote        Required to implement by all scripts.
     */
    protected abstract void InitialState(double blendTime);

    /**
     * State terminator.
     * @param blendTime The blend time that starts with.
     * @implNote        Default state to terminate to if there are no actions to perform.
     */
    protected void TerminateState(double blendTime) { }
    /********************************************************************************************************************/
    /******************************************** STATE IMPLEMENTATION **************************************************/
    /********************************************************************************************************************/
    /**
     * It is used when the character needs to be rotated.
     * <state name="" behavior="turn" action="" method="" degree="" CallCycleTime="" exit="" Complete="" TargetLost=""/>
     * @param turnMethod Direction or method to use the state.
     * @param degree     Degree to rotate.
     * @param onEnd      Handler to execute when state executed successfully.
     * @param onExit     Handler to execute when state has failed.
     */
    protected void turn(EAITurnMethod turnMethod, int degree, Callable<Boolean> onEnd, Consumer<Method> onExit) {
        // SMActionRotate
        _isFindPathCompleted = true;
        Boolean onEndReturn = false;
        if (onEnd != null) {
            try {
                onEndReturn = onEnd.call();
            } catch (Exception e) {
                _log.error("Error while calling onEnd method from move task!", e);
            }
        }

        if (!onEndReturn && onExit != null)
            onExit.accept(null);
    }

    /**
     * It is used when the character needs to be rotated.
     * <state name="" behavior="turn" action="" method="" degree="" CallCycleTime="" exit="" Complete="" TargetLost=""/>
     * @param turnMethod Direction or method to use the state.
     * @param x          Position X.
     * @param z          Position Z.
     * @param y          Position Y.
     * @param onEnd      Handler to execute when state executed successfully.
     * @param onExit     Handler to execute when state has failed.
     */
    protected void turn(EAITurnMethod turnMethod, int x, int z, int y, Callable<Boolean> onEnd, Consumer<Method> onExit) {
        // SMActionRotate
        _isFindPathCompleted = true;
        Boolean onEndReturn = false;
        if (onEnd != null) {
            try {
                onEndReturn = onEnd.call();
            } catch (Exception e) {
                _log.error("Error while calling onEnd method from move task!", e);
            }
        }

        if (!onEndReturn && onExit != null)
            onExit.accept(null);
    }

    /**
     * This is used when you need to move the character to the left and right of the target based on the position of the target.
     * <state name="" behavior="around" action="" Rotate="" radius="" min="" max="" FailFindPath="" CallCycleTime="" exit="" TargetLost=""/>
     * @param radius     Radius that will be executed randomly to which position to move to.
     * @param naviType   Navigation type
     * @param onEnd      Handler to execute when state executed successfully.
     * @param onExit     Handler to execute when state has failed.
     */
    protected void moveAround(long radius, ENaviType naviType, Callable<Boolean> onEnd, Consumer<Method> onExit) {
        double angle = Rnd.nextDouble() * Math.PI * 2;
        double producedRadius = Math.sqrt(Rnd.nextDouble()) * radius;

        Location centerPosition = getActor().getSpawnPlacement().getLocation();
        Location destination = new Location(
                centerPosition.getX() + radius * Math.cos(angle),
                centerPosition.getY() + radius * Math.sin(angle),
                centerPosition.getZ());

        double distance2 = MathUtils.get3DDistance(centerPosition, destination);
        double angle2 = Math.atan2((destination.getY() - centerPosition.getY()) / distance2, (destination.getX() - centerPosition.getX()) / distance2);
        destination.setDirection(angle2);

        if (naviType.isAir())
            destination.setZ(GeoService.getInstance().validateZ(destination.getX(), destination.getY(), destination.getZ()));
        getActor().getMovementController().startMove(destination, onEnd, onExit, naviType, true);
    }

    /**
     * It is used to move the character along a specified path by the user or the following specific character.
     * <state name="" behavior="walk" action="" Rotate="" dest="" min="" max="" CurveForce="" OffsetX="" OffsetY="" NavigationType="" fromCurrentPos="" waypoint="" routekey="" FailFindPath="" FailByCurvedPath="" CallCycleTime="" exit=""/>
     * @param dest              Destination type
     * @param offsetx           Position X offset for movement.
     * @param offsety           Position Y offset for movement.
     * @param offsetz           Position Z offset for movement.
     * @param min               Minimum position to delay.
     * @param max               Maximum position to delay.
     * @param fromCurrentPos    If should move from current position.
     * @param naviType          Navigation Type
     * @param onEnd             Handler to execute when state executed successfully.
     * @param onExit            Handler to execute when state has failed.
     */
    protected void move(EAIMoveDestType dest, long offsetx, long offsety, long offsetz, long min, long max, boolean fromCurrentPos, ENaviType naviType, Callable<Boolean> onEnd, Consumer<Method> onExit) {
        Location destination = null;
        switch (dest) {
            case Random: {
                Location fromLoc = fromCurrentPos ? getActor().getLocation() : getActor().getSpawnPlacement().getLocation();
                double angle = Rnd.get(0, 62800) / 10000.0f;
                angle = ((angle > Math.PI) ? (angle - Math.PI * 2) : angle);
                double distance = Rnd.get(min, max);
                double cos = Math.cos(angle);
                double sin = Math.sin(angle);
                double nx = fromLoc.getX() + cos * distance;
                double ny = fromLoc.getY() + sin * distance;
                destination = new Location(nx + offsetx, ny + offsety, (naviType.isAir() ? GeoService.getInstance().validateZ(nx, ny, fromLoc.getZ()) : fromLoc.getZ()) + offsetz);
                break;
            }
            case OwnerPosition:
            case OwnerPath: {
                long offsetX = (min >= 0L && max > 0L) ? Rnd.get(min, max) : offsetx;
                long offsetY = (min >= 0L && max > 0L) ? Rnd.get(min, max) : offsety;
                if (getActor().getOwner() != null) {
                    Location ownerLoc = getActor().getOwner().getLocation();
                    destination = new Location(ownerLoc.getX() + offsetX, ownerLoc.getY() + offsetY, ownerLoc.getZ() + offsetz);
                    break;
                }
                break;
            }
            case SenderDestination: {
                if (_sender != null && _sender.getMovementController().isMoving()) {
                    long offsetX = (min >= 0L && max > 0L) ? Rnd.get(min, max) : offsetx;
                    long offsetY = (min >= 0L && max > 0L) ? Rnd.get(min, max) : offsety;
                    Location senderLoc = getActor().getMovementController().getDestination();
                    destination = new Location(senderLoc.getX() + offsetX, senderLoc.getY() + offsetY, senderLoc.getZ() + offsetz);
                    break;
                }
                break;
            }
            case SenderPosition: {
                if (_sender != null) {
                    Location senderLoc2 = _sender.getLocation();
                    destination = new Location(senderLoc2.getX() + offsetx, senderLoc2.getY() + offsety, senderLoc2.getZ() + offsetz);
                    break;
                }
                break;
            }
            case SenderTarget: {
                if (_sender != null) {
                    Creature senderTarget = _sender.getAggroList().getTarget();
                    if (senderTarget != null) {
                        Location senderTargetLoc = senderTarget.getLocation();
                        destination = new Location(senderTargetLoc.getX() + offsetx, senderTargetLoc.getY() + offsety, senderTargetLoc.getZ() + offsetz);
                    }
                    break;
                }
                break;
            }
        }
        if (destination != null) {
            Location actorLoc = getActor().getLocation();
            double distance2 = MathUtils.get3DDistance(actorLoc, destination);
            if (distance2 > 0.0) {
                double angle2 = Math.atan2((destination.getY() - actorLoc.getY()) / distance2, (destination.getX() - actorLoc.getX()) / distance2);
                double cos = Math.cos(angle2);
                double sin = Math.sin(angle2);
                actorLoc.setLocation(cos, sin);
                destination.setLocation(cos, sin);
                getActor().getMovementController().startMove(destination, onEnd, onExit, naviType, true);
                return;
            }
        }
        _isFindPathCompleted = true;
        Boolean onEndReturn = false;
        if (onEnd != null) {
            try {
                onEndReturn = onEnd.call();
            } catch (Exception e) {
                _log.error("Error while calling onEnd method from move task!", e);
            }
        }
        if (!onEndReturn && onExit != null) {
            onExit.accept(null);
        }
    }

    /**
     * It is used when the character has to follow the distance between target and his/her distance through the route logic.
     * <state name="" behavior="chase" action="" Rotate="" TargetCycle="" FailFindPath="" CallCycleTime="" exit="" TargetLost=""/>
     * @param targetCycleTime   As the number increases, the frequency becomes lower and the lower the frequency, the more often the road is curved and the character moves.
     * @param onEnd2            Handler to execute when state executed successfully.
     * @param onExit            Handler to execute when state has failed.
     */
    protected void chase(int targetCycleTime, Callable<Boolean> onEnd2, Consumer<Method> onExit) {
        //System.out.println("Queueing chase action=[" + System.currentTimeMillis() + "]");
        Creature target = getActor().getAggroList().getTarget();
        if (target != null) {
            Location targetLoc = target.getLocation();
            Location actorLoc = getActor().getLocation();
            double distance = getDistanceTo(target);
            if (distance > 0.0) {
                double angle = Math.atan2((targetLoc.getY() - actorLoc.getY()) / distance, (targetLoc.getX() - actorLoc.getX()) / distance);
                double cos = Math.cos(angle);
                double sin = Math.sin(angle);
                final double nx = actorLoc.getX() + cos * distance;
                final double ny = actorLoc.getY() + sin * distance;
                actorLoc.setLocation(cos, sin);

                // TargetCycle time will call the end every frequency.
                final Location destination = new Location(nx, ny, GeoService.getInstance().validateZ(nx, ny, targetLoc.getZ()), cos, sin);
                getActor().getMovementController().startMove(destination, onEnd2, onExit, ENaviType.ground, true);
                return;
            }
        }
        _isFindPathCompleted = true;
        Boolean onEndReturn = false;
        if (onEnd2 != null) {
            try {
                onEndReturn = onEnd2.call();
            } catch (Exception e) {
                _log.error("Error while calling onEnd method from move task!", e);
            }
        }

        if (!onEndReturn && onExit != null)
            onExit.accept(null);
    }

    /**
     * Chase is used when you need to compare the distance of the target with its own distance and move to the opposite direction to the target.
     * <state name="" behavior="escape" action="" Rotate="" radius="" inverse="" TargetCycle="" FailFindPath="" CallCycleTime="" exit="" TargetLost=""/>
     * @param radius            Spread distance to reach the target
     * @param onEnd2            Handler to execute when state executed successfully.
     * @param onExit            Handler to execute when state has failed.
     */
    protected void escape(long radius, Callable<Boolean> onEnd, Consumer<Method> onExit) {
        Creature target = getActor().getAggroList().getTarget();
        if (target != null) {
            Location actorLoc = getActor().getLocation();
            Location targetLoc = target.getLocation();
            double distance = MathUtils.get3DDistance(targetLoc, actorLoc);
            if (distance > 0.0) {
                double angle = Math.atan2((actorLoc.getY() - targetLoc.getY()) / distance, (actorLoc.getX() - targetLoc.getX()) / distance);
                double cos = Math.cos(angle);
                double sin = Math.sin(angle);
                double nx = actorLoc.getX() + cos * radius;
                double ny = actorLoc.getY() + sin * radius;
                Location destination = new Location(nx, ny, GeoService.getInstance().validateZ(nx, ny, actorLoc.getZ()), cos, sin);
                actorLoc.setLocation(cos, sin);
                destination.setLocation(cos, sin);
                getActor().getMovementController().startMove(destination, onEnd, onExit, ENaviType.ground, true);
                return;
            }
        }
        _isFindPathCompleted = true;
        Boolean onEndReturn = false;
        if (onEnd != null) {
            try {
                onEndReturn = onEnd.call();
            } catch (Exception e) {
                _log.error("Error while calling onEnd method from move task!", e);
            }
        }

        if (!onEndReturn && onExit != null)
            onExit.accept(null);
    }

    /**
     * When entering this state, the Health is fully recovered and the character is automatically moved to the initial spawn position and in a moving state. It is in an avoid state that is not suitable for attack.
     * <state name="" behavior="return" action="" min="" max="" FailFindPath="" CallCycleTime="" exit=""/>
     * @param onEnd2            Handler to execute when state executed successfully.
     * @param onExit            Handler to execute when state has failed.
     */
    protected void recoveryAndReturn(Callable<Boolean> onEnd, Consumer<Method> onExit) {
        clearAggro(true);
        getActor().getBuffList().dispelBuffs();
        getActor().getGameStats().getHp().fill();
        Location destination = getActor().getSpawnPlacement().getLocation();
        Location actorLoc = getActor().getLocation();
        double distance = MathUtils.get3DDistance(actorLoc, destination);
        if (distance > 0.0) {
            double angle = Math.atan2((destination.getY() - actorLoc.getY()) / distance, (destination.getX() - actorLoc.getX()) / distance);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            actorLoc.setLocation(cos, sin);
            destination.setLocation(cos, sin);
            getActor().getMovementController().startMove(destination, onEnd, onExit, ENaviType.ground, true);
        } else {
            _isFindPathCompleted = true;
            Boolean onEndReturn = false;
            if (onEnd != null) {
                try {
                    onEndReturn = onEnd.call();
                } catch (Exception e) {
                    _log.error("Error while calling onEnd method from move task!", e);
                }
            }

            if (!onEndReturn && onExit != null)
                onExit.accept(null);
        }
    }

    /**
     * .-.
     * @param dest          Movement type
     * @param waypointData  Waypoint movement data
     * @param naviType      Navigation type
     * @param onEnd         Event fired when movement has been completed successfully.
     * @param onExit        Event fired when movement has failed.
     */
    protected void move(EAIMoveDestType dest, Integer[] waypointData, ENaviType naviType, Callable<Boolean> onEnd, Consumer<Method> onExit) {
        if (dest == EAIMoveDestType.WaypointVariable) {
            if (waypointData != null && waypointData.length == 2 && waypointData[0] != 0 && waypointData[1] != 0) {
                WaypointT waypoint = WaypointData.getInstance().getWaypoint(waypointData);
                if (waypoint != null) {
                    Location actorLoc = getActor().getLocation();
                    Location destination = new Location(waypoint.getPosX(), waypoint.getPosY(), waypoint.getPosZ());
                    double distance = MathUtils.get3DDistance(actorLoc, destination);
                    if (distance > 0.0) {
                        double angle = Math.atan2((destination.getY() - actorLoc.getY()) / distance, (destination.getX() - actorLoc.getX()) / distance);
                        double cos = Math.cos(angle);
                        double sin = Math.sin(angle);
                        actorLoc.setLocation(cos, sin);
                        destination.setLocation(cos, sin);
                        getActor().getMovementController().startMove(destination, onEnd, onExit, naviType, true);
                        return;
                    }
                }
            }
        } else {
            _log.warn("Attempt moving by non EAIMoveDestType.waypoint_variable type! dest: [{}]", dest);
        }
        _isFindPathCompleted = true;
        Boolean onEndReturn = false;
        if (onEnd != null) {
            try {
                onEndReturn = onEnd.call();
            } catch (Exception e) {
                _log.error("Error while calling onEnd method from move task!", e);
            }
        }
        if (!onEndReturn && onExit != null) {
            onExit.accept(null);
        }
    }

    /**
     * .-.
     * @param waypointFile  Waypoint file
     * @param waypointName  Waypoint name
     * @param naviType      Navigation type
     * @param onEnd         Event fired when movement has been completed successfully.
     * @param onExit        Event fired when movement has failed.
     */
    protected void moveToWaypoint(String waypointFile, String waypointName, ENaviType naviType, Callable<Boolean> onEnd, Consumer<Method> onExit) {
        String[] waypointNames = waypointName.split(" ");
        Collection<Location> destinations = new ArrayList<>();
        Location lastDestination;
        Location actorLoc = lastDestination = getActor().getLocation();
        if (!waypointName.equals("sender_destination")) {
            /*for (String _waypointName : waypointNames) {
                WaypointT waypoint = WaypointData.getInstance().getWaypoint(waypointFile.toLowerCase(), _waypointName.toLowerCase());
                if (waypoint != null) {
                    Location destination = new Location(waypoint.getPosX(), waypoint.getPosY(), waypoint.getPosZ());
                    double distance = MathUtils.get3DDistance(lastDestination, destination);
                    if (distance > 0.0) {
                        double angle = Math.atan2((destination.getY() - lastDestination.getY()) / distance, (destination.getX() - lastDestination.getX()) / distance);
                        double cos = Math.cos(angle);
                        double sin = Math.sin(angle);
                        actorLoc.setLocation(cos, sin);
                        destination.setLocation(cos, sin);
                        lastDestination = destination;
                        destinations.add(destination);
                    }
                }
            }*/
            // temporary disabled this shit for tests
        } else {
            getActor().getMovementController().startMove(destinations, onEnd, onExit, naviType);
        }
    }

    /**
     * State to restore health.
     * @param hpRate Health(%) to restore.
     * @param onEnd  Event fired when movement has been completed successfully.
     * @param onExit Event fired when movement has failed.
     */
    protected void revive(int hpRate, Callable<Boolean> onEnd, Consumer<Method> onExit) {
        getActor().getGameStats().getHp().fill((int) hpRate);
        _isFindPathCompleted = true;
        Boolean onEndReturn = false;
        if (onEnd != null) {
            try {
                onEndReturn = onEnd.call();
            } catch (Exception e) {
                _log.error("Error while calling onEnd method from move task!", e);
            }
        }

        if (!onEndReturn && onExit != null)
            onExit.accept(null);
    }
    /********************************************************************************************************************/
    /**************************************** SHARED FUNCTIONS BETWEEN STATES *******************************************/
    /********************************************************************************************************************/
    /**
     * Returns objects by type.
     * @param findTargetType Target find method.
     * @param filter        Target data to filter
     * @return              Targets stream
     */
    protected Stream<Creature> getObjects(EAIFindTargetType findTargetType, Predicate<Creature> filter) {
        GameSector sector = getActor().getLocation().getGameSector();

        if (sector != null) {
            switch(findTargetType)
            {
                case Parent:
                    return getActor().getParty() != null
                            ? Collections.singletonList(getActor().getParty().getLeader()).stream().filter(filter)
                            : Collections.<Creature>emptyList().stream();

                case Child:
                    return getActor().getParty() != null
                            ? (getActor().getParty().isPartyLeader(getActor())
                                ? getActor().getParty().getMembers(getActor().getParty().getLeader()).stream().filter(filter)
                                : Collections.<Creature>emptyList().stream())
                            : Collections.<Creature>emptyList().stream();

                case Character:
                    return sector.getObjects().stream().filter(object -> object != getActor() && object.isCharacter()).filter(filter);

                case Player:
                    return sector.getObjectsByType(ECharKind.Player).stream().filter(filter); // compare against self?

                case Monster:
                    return sector.getObjectsByType(ECharKind.Monster).stream().filter(object -> object != getActor()).filter(filter);

                case EliteMonster:
                    break;

                case Npc:
                    return sector.getObjectsByType(ECharKind.Npc).stream().filter(object -> object != getActor()).filter(filter);

                case Interaction:
                    return sector.getObjectsByType(ECharKind.Alterego).stream().filter(object -> object != getActor()).filter(filter);

                case Sibling:
                    return getActor().getParty() != null
                            ? getActor().getParty().getMembers(getActor().getParty().getLeader()).stream().filter(filter)
                            : Collections.<Creature>emptyList().stream();

                case Enemy:
                    return sector.getObjects().stream().filter(object -> object != getActor() && getActor().isEnemy(object)).filter(filter);

                case Collect:
                    return sector.getObjectsByType(ECharKind.Collect).stream().filter(object -> object != getActor()).filter(filter);

                case Corpse:
                    return sector.getObjectsByType(ECharKind.Deadbody).stream().filter(object -> object != getActor()).filter(filter);

                case PKPlayer:
                    return sector.getObjectsByType(ECharKind.Player).stream().filter(object -> ((Player) object).getTendency() < 0).filter(filter);

                case EnemyLordOrKingTent:
                    return sector.getObjectsByType(ECharKind.Household).stream().filter(object -> getActor().isEnemy(object)).filter(filter);

                case EnemyBarricade:
                    return sector.getObjectsByType(ECharKind.Vehicle).stream().filter(object -> object != getActor() && getActor().isEnemy(object) && object.getTemplate().getVehicleType() == EVehicleType.RidableBarricade).filter(filter);

                case AllyLordOrKingTent:
                    return sector.getObjectsByType(ECharKind.Household).stream().filter(object -> getActor().isAlly(object)).filter(filter);

                case OwnerPlayer:
                    return getActor().getOwner() != null && getActor().getOwner().isPlayer()
                            ? Collections.singletonList(getActor().getOwner()).stream()
                            : Collections.<Creature>emptyList().stream();

                case LordOrKingPlayer:
                    return sector.getObjectsByType(ECharKind.Player).stream().filter(object -> object.isLordOrKing(getActor())).filter(filter);

                case AllyVehicle:
                    return sector.getObjectsByType(ECharKind.Vehicle).stream().filter(object -> object != getActor() && object.isVehicle() && getActor().isAlly(object)).filter(filter);

                case VehicleHorse:
                case Tent:
                case CombineWaveAlly:
                case CombineWavePlayer:
                case Installation:
                case InTheHouse:
                case RidingVehicle:
                case _Skill0_TargetType0:
                case _Skill0_TargetType1:
                    break;

                default:
                    _log.error("CreatureAI::getObjects() does not contain implementation for '{}' !", findTargetType.toString());
                    break;
            }
        }
        return Collections.<Creature>emptyList().stream();
    }

    /**
     * Finds a target that is ether near or far.
     * @param findTargetType Target find method.
     * @param findType       Target find type
     * @param isAlly         Should target be an ally
     * @param filter         Target data to filter
     * @return true if target found, false otherwise.
     */
    protected boolean findTarget(EAIFindTargetType findTargetType, EAIFindType findType, boolean isAlly, Predicate<Creature> filter) {
        Stream<Creature> targets = getObjects(findTargetType, filter);

        // Check if target is an ally, we want to avoid them!
        if (isAlly)
            targets = targets.filter(object -> !getActor().isAlly(object));

        // Compare by distance!
        if (findType == EAIFindType.nearest)
            targets = targets.sorted((o1, o2) -> -Double.compare(MathUtils.getDistance(getActor().getLocation(), o1.getLocation()), MathUtils.getDistance(getActor().getLocation(), o2.getLocation())));

        Optional<Creature> target = targets.findFirst();
        if (target.isPresent()) {
            getActor().getAggroList().addCreature(target.get());
            getActor().getAggroList().setTarget(target.get());
            return true;
        }
        return false;
    }

    /**
     * Finds a target in aggro list.
     * @param findTargetType Target find method.
     * @param findType       Target find type
     * @param filter         Target data to filter
     * @return true if target found, false otherwise.
     */
    protected boolean findTargetInAggro(EAIFindTargetType findTargetType, EAIFindType findType, Predicate<Creature> filter) {
        Stream<Creature> targets = getActor().getAggroList().getAggroCreatures().stream();

        switch (findTargetType) {
            case Enemy                  : targets = targets.filter(object -> getActor().isEnemy(object)).filter(filter); break;
            case EnemyLordOrKingTent    : targets = targets.filter(Creature::isHousehold).filter(creatureObject -> getActor().isEnemy(creatureObject)).filter(filter); break;
            case EnemyBarricade         : targets = targets.filter(Creature::isVehicle).filter(creatureObject -> creatureObject != getActor() && creatureObject.getTemplate().getVehicleType() == EVehicleType.RidableBarricade).filter(filter); break;
            case Corpse                 : targets = targets.filter(Creature::isDeadbody).filter(filter); break;
            case Player                 : targets = targets.filter(Creature::isPlayer).filter(filter); break;
            case PKPlayer               : targets = targets.filter(creature -> creature.isPlayer() && ((Player) creature).getTendency() < 0).filter(filter); break;
            case Monster                : targets = targets.filter(Creature::isMonster).filter(filter); break;
            case Npc                    : targets = targets.filter(Creature::isNpc).filter(filter); break;
            case Collect                : targets = targets.filter(Creature::isCollect).filter(filter); break;
            case Character              : targets = targets.filter(Creature::isCharacter).filter(filter); break;
            case AllyVehicle            : targets = Collections.<Creature>emptyList().stream(); break;
            case AllyLordOrKingTent     : targets = Collections.<Creature>emptyList().stream(); break;
            default:
                _log.error("CreatureAI::findTargetInAggro() does not contain implementation for '{}' !", findTargetType.toString());
                targets = Collections.<Creature>emptyList().stream();
                break;
        }

        // Compare by distance!
        if (findType == EAIFindType.nearest)
            targets = targets.sorted((o1, o2) -> -Double.compare(MathUtils.getDistance(getActor().getLocation(), o1.getLocation()), MathUtils.getDistance(getActor().getLocation(), o2.getLocation())));
        else // Compare by hate!
            targets = targets.sorted((o1, o2) -> {
                IAggroList aggroInfo = getActor().getAggroList();
                AggroInfo aggro1 = aggroInfo.getAggroInfo(o1);
                AggroInfo aggro2 = aggroInfo.getAggroInfo(o2);
                return Double.compare((aggro1 != null) ? aggro1.getHate() : 0.0, (aggro2 != null) ? aggro2.getHate() : 0.0);
            });

        Optional<Creature> target = targets.findFirst();
        if (target.isPresent()) {
            getActor().getAggroList().addCreature(target.get());
            getActor().getAggroList().setTarget(target.get());
            return true;
        }
        return false;
    }

    /**
     * Finds character count in specified range.
     * @param findTargetType Target find method.
     * @param isAlly         Should target be an ally
     * @param filter         Target data to filter
     * @return
     */
    protected int findCharacterCount(EAIFindTargetType findTargetType, boolean isAlly, Predicate<Creature> filter) {
        Stream<Creature> targets = getObjects(findTargetType, filter);
        if (isAlly)
            targets = targets.filter(object -> getActor().isAlly(object));
        return (int) targets.count();
    }
    /********************************************************************************************************************/
    /*********************************** INTERNAL FUNCTIONS FOR CREATURE AI MANAGMENT ***********************************/
    /********************************************************************************************************************/
    /**
     * Schedules a state to be executed.
     * @param stateMethod
     * @param waitTime
     * @return true if state has been scheduled, false otherwise.
     */
    protected synchronized boolean scheduleState(Consumer<Method> stateMethod, long waitTime) {
        if (_aiStopped || !getActor().isSpawned())
            return false;

        if (_currentTask != null)
            _currentTask.cancel(true);
        _scheduledMethod = stateMethod;
        _currentTask 	 = ThreadPool.getInstance().scheduleAi(() -> _scheduledMethod.accept(null), waitTime, TimeUnit.MILLISECONDS);
        return true;
    }

    /**
     * Changes the state immediately to process the next thing.
     * @param stateMethod
     * @return true if state change has been successfull, false otherwise.
     */
    protected synchronized boolean changeState(Consumer<Method> stateMethod) {
        if (_aiStopped || !getActor().isSpawned())
            return false;

        if (_currentTask != null)
            _currentTask.cancel(true);
        _scheduledMethod = stateMethod;
        _scheduledMethod.accept(null);
        return true;
    }

    /**
     * Initializes Creature AI for first use.
     * This is usually called right after spawning to world.
     */
    @Override
    public void run() {
        _aiStartTime = System.currentTimeMillis();
        InitialState(0.1);
        getActor().getGameStats().getHp().startHpRegenTask();
        getActor().getGameStats().getMp().startMpRegenTask();
    }

    /**
     * Notifies that AI should start working.
     * Note:    Should we resume what it was doing?
     *          Maybe monster stopped and it will never recover from the state!
     */
    public void notifyStart() {
        _aiStartTime = System.currentTimeMillis();
        _aiStopped = false;
        getActor().getGameStats().getHp().startHpRegenTask();
        getActor().getGameStats().getMp().startMpRegenTask();
    }

    /**
     * Notifies that AI should stop working.
     * Note: Should we save the current executing state so that it might be resumed
     *       later by the thread?
     */
    public void notifyStop() {
        _aiStopped = true;
        if (_currentTask != null)
            _currentTask.cancel(true);
        TerminateState(0.1);
        getActor().getGameStats().getHp().stopHpRegenTask();
        getActor().getGameStats().getMp().stopMpRegenTask();
        getActor().getMovementController().cancelMoveTask();
    }

    /**
     * Checks if CreatureAI is currently stopped.
     * @return true if CreatureAI is stopped, false otherwise.
     */
    public boolean isStopped() {
        return _aiStopped;
    }
    /********************************************************************************************************************/
    /********************************** INTERNAL FUNCTIONS FOR CREATURE AI CONDITIONS ***********************************/
    /********************************************************************************************************************/
    /**
     * Returns a variable value from a shared container.
     * @param varName Variable hash to return from shared container.
     * @return variable value.
     */
    protected Integer getVariable(Long varName) {
        // Health rate
        if (varName == 0x3F487035L)
            return getHpRate();

        // Character Key
        if (varName == 0x47C57E4AL)
            return getActor().getTemplate().getCreatureId();

        // Fetch the variable from variable map.
        if (_aiVariables.containsKey(varName))
            return _aiVariables.get(varName);

        // Body height
        if (varName == 0x3715AB9DL)
            return getActor().getTemplate().getBodyHeight();

        // Degree to target
        if (varName == 0xE5BD13F2L)
            return getAngleToTarget(getActor().getAggroList().getTarget());

        // Distance to owner
        if (varName == 0xCBEEF8C7L)
            return getDistanceToOwner();

        //log.error("Unknown variable={}", varName);
        return 0;
    }

    /**
     * Sets multiple AI variables.
     * @param aiVariables
     * @implNote It will reset _aiVariables private variable with another instance.
     */
    public void setVariables(Map<Long, Integer> aiVariables) {
        _aiVariables = aiVariables;
    }

    /**
     * Sets a specific variable to a value.
     * @param varName Variable hash to set.
     * @param value Variable value to set.
     * @return true if variable has been set successfully, false otherwise.
     */
    protected boolean setVariable(Long varName, Integer value) {
        _aiVariables.put(varName, value);
        return true;
    }

    /**
     * Returns a variable array from a container.
     * @param varName Variable hash to return.
     * @return if exists, it will return a variable array,
     *        else it will return a nulled variable array.
     */
    protected Integer[] getVariableArray(Long varName)
    {
        if (_aiVariableArray.containsKey(varName))
            return _aiVariableArray.get(varName);
        //_log.error("Unknown variableArray={}", varName);
        return new Integer[0];
    }

    /**
     * Sets a specific variable to an array.
     * @param varName Variable hash to set.
     * @param value Variable value to set.
     * @return true if variable array has been set successfully, false otherwise.
     */
    protected boolean setVariableArray(Long varName, Integer[] value)
    {
        _aiVariableArray.put(varName, value);
        return true;
    }
    /********************************************************************************************************************/
    /**************************************** HELPER FUNCTIONS FOR EMULATOR *********************************************/
    /********************************************************************************************************************/
    /**
     * Executes a script handler with specified hash.
     * @param handlerHash Hash of the handler.
     * @param creature Creature that invoked the handler.
     * @param eventData Passed event data while invoking the handler.
     */
    public void executeHandler(long handlerHash, Creature creature, Integer[] eventData) {
        if (_handlers.containsKey(handlerHash)) {
            try {
                _handlers.get(handlerHash).invoke(this, creature, eventData);
            } catch (Exception e) {
                _log.error("There was an error while invoking a handler method={} ({})", _handlers.get(handlerHash).getName(), handlerHash, e);
            }
        }
    }

    /**
     * Fetches current AI Owner.
     * @return CreatureAI Owner.
     */
    public Creature getActor() {
        return _actor;
    }

    /**
     * Fetches current state hash.
     * @return State hash.
     */
    public Long getState() {
        return _state;
    }

    /**
     * Sets current state hash.
     * @param state State hash.
     */
    protected void setState(Long state) {
        if (_state.equals(state))
            ++_callCount;
        else
            _callCount = 1;
        _state = state;
    }

    /**
     * Returns call count of current state.
     * @return Current state call count.
     */
    protected int getCallCount() {
        return _callCount;
    }

    /**
     * Current state behavior. Helper function to indicate which state to use.
     * @return Current behavior.
     */
    public EAIBehavior getBehavior() {
        return _behavior;
    }

    /**
     * Sets state behavior to indicate which state handler to use.
     * @param behavior Behavior to set.
     * @apiNote Function can only be used by scripts that etend CreatureAI.
     */
    protected void setBehavior(EAIBehavior behavior) {
        getActor().getMovementController().cancelMoveTask();
        _behavior = behavior;
    }

    /********************************************************************************************************************/
    /************************************* HANDLER FUNCTIONS IMPLEMENTED BY STATE ***************************************/
    /********************************************************************************************************************/
    /**
     * Returns a target if one exists.
     * @return Returns Creature, otherwise null.
     */
    public Creature getTarget() {
        return getActor().getAggroList().getTarget();
    }

    /**
     * Returns if target is lost.
     * @return true if target is lost, otherwise false.
     */
    public boolean isTargetLost() {
        Creature target = getTarget();
        return target == null || target.isDead(); // || !MathUtils.isInRange(getActor().getSpawnPlacement().getLocation(), target.getLocation(), getActor().getTemplate().getResetDistance());
    }

    protected int getBodyHeight() {
        return _actor.getTemplate().getBodyHeight();
    }

    protected int getHpRate() {
        return (int) (_actor.getGameStats().getHp().getCurrentHp() / _actor.getGameStats().getHp().getMaxHp() * 100.0f);
    }

    public double getDistanceToWrapper(Creature target) {
        return getDistanceTo(target);
    }

    protected int getPetSkillUsableDistance(int x) {
        return 0;
    }

    protected int getPetSkillDetectDistance(int x) {
        return 0;
    }

    protected int getPetSkillTargetType(int x) {
        return 0;
    }

    protected int getPetSkillType(int x) {
        return 0;
    }

    protected double getDistanceTo(Creature target) {
        return MathUtils.get3DDistance(_actor.getLocation(), target.getLocation())
                - _actor.getTemplate().getBodySize()
                - target.getTemplate().getBodySize();
    }

    protected double getDistanceToSpawn() {
        return MathUtils.get3DDistance(_actor.getLocation(), _actor.getSpawnPlacement() != null ? _actor.getSpawnPlacement().getLocation() : _actor.getLocation())
                - _actor.getTemplate().getBodySize();
    }

    protected Integer getDistanceToTarget() {
        return (int) getDistanceToTarget(getActor().getAggroList().getTarget(), false);
    }

    protected double getDistanceToTarget(Creature target) {
        return getDistanceToTarget(target, false);
    }

    protected double getDistanceToTarget(Creature target, boolean checkCrouch) {
        if (target == null) {
            return Double.MIN_VALUE;
        }
        double distance = getDistanceTo(target);
        return (checkCrouch && getTargetIsCrouchAction(target)) ? (distance * 2.0) : distance;
    }

    protected int getDistanceToOwner() {
        Creature owner = _actor.getOwner();
        if (owner != null) {
            return (int) getDistanceTo(owner);
        }
        return Integer.MIN_VALUE;
    }

    protected int getDistanceToChild() {
        int maxDistance = 0;
        if (_actor.hasParty()) {
            IParty<Creature> party = (IParty<Creature>) _actor.getParty();
            for (Creature child : party.getMembers(party.getLeader())) {
                int distance = (int) getDistanceTo(child);
                if (maxDistance < distance) {
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }

    protected int getDistanceToOwner(long divider) {
        return (int) (getDistanceToOwner() / divider);
    }

    protected int getDistanceToOwnerNotFormation() {
        return getDistanceToOwner();
    }

    protected boolean isCreatureVisible(Creature target, boolean doPhysicalCheck) {
        return target != null && target.getLocation().getGameSector() != null && target.isCreatureVisible() && _actor.canSee(target) && GeoService.getInstance().canSee(_actor, target);
    }

    protected boolean isTargetRidable(Creature target) {
        return target != null && target.getTemplate().isVehicleDriverRidable();
    }

    protected boolean isTargetDeliver(Creature target) {
        return target != null && target.isDeliver();
    }

    protected boolean checkInstanceTeamNo() {
        return true;
    }

    protected boolean checkParentInstanceTeamNo() {
        return true;
    }

    protected int getAngleToTarget(Creature target) {
        double anglediff = 0.0;
        if (target != null) {
            double angleOfTarget = Math.toDegrees(Math.atan2(target.getLocation().getY() - _actor.getLocation().getY(), target.getLocation().getX() - _actor.getLocation().getX()));
            double facingAngle = Math.toDegrees(Math.atan2(_actor.getLocation().getSin(), _actor.getLocation().getCos()));
            anglediff = (facingAngle - angleOfTarget + 180.0) % 360.0 - 180.0;
        }
        return (int) anglediff;
    }

    protected int getDegreeToTarget() {
        return getAngleToTarget(getActor().getAggroList().getTarget());
    }

    protected long getDegreeToTarget(Creature target) {
        return getAngleToTarget(target);
    }

    protected double getSelfHp() {
        return getHpRate();
    }

    protected double getTargetHp(Creature target) {
        if (target == null) {
            return 0;
        }
        if (target.getGameStats() != null) {
            return target.getGameStats().getHp().getCurrentHp();
        }
        _log.warn("Gamestats HP for notnull target {} is null", target);
        return 0;
    }

    protected int getTargetHpRate() {
        return getTargetHpRate(getActor().getAggroList().getTarget());
    }

    protected int getTargetHpRate(Creature target) {
        if (target == null) {
            return 0;
        }
        if (target.getGameStats() != null) {
            return (int) (target.getGameStats().getHp().getCurrentHp() / target.getGameStats().getHp().getMaxHp() * 100.0f);
        }
        _log.warn("Gamestats HP for notnull target {} is null", target);
        return 0;
    }

    protected int getTargetLevel(Creature target) {
        return (target != null) ? (target.getLevel() - getActor().getLevel()) : 0;
    }

    protected long getTargetAction(Creature target) {
        return (target != null) ? target.getActionStorage().getActionHash() : 0L;
    }

    protected int getTargetCharacterKey(Creature target) {
        return (target != null) ? target.getCreatureId() : 0;
    }

    protected int getOwnerCharacterKey() {
        return getActor().getOwner() != null ? getActor().getOwner().getCreatureId() : 0;
    }

    protected boolean isDarkSpiritMonster() {
        return _actor.isDarkSpiritMonster();
    }

    protected int getBitFlag_NaviType(ENaviType check, ENaviType expected) {
        return ((check.getId() & expected.getId()) == expected.getId()) ? 1 : 0;
    }

    protected ENaviType getCurrentPos_NaviType(int bodyHeight) {
        return ENaviType.ground;
    }

    protected ENaviType getCurrentPos_NaviType() {
        return ENaviType.ground;
    }

    protected ENaviType getNaviTypeStringToEnum(String naviType) {
        String[] naviData = naviType.split(" ");
        String enumValue = String.join("_", (CharSequence[]) naviData);
        try {
            return ENaviType.valueOf(enumValue);
        } catch (Exception e) {
            _log.error("Can't find ENaviType value for {}", (Object) enumValue);
            return ENaviType.valueOf(naviData[0]);
        }
    }

    protected boolean isTargetProcessQuest(Creature target, long groupId, long questId) {
        if (target != null && target.isPlayer()) {
            Player player = (Player) target;
            if (player.getPlayerQuestHandler().isProgressQuest((int) groupId, (short) questId)) {
                return true;
            }
        }
        return false;
    }

    protected int getSummonerClass() {
        Creature owner = getActor().getOwner();
        if (owner.isPlayer())
            return ((Player) owner).getClassType().getId();
        return 0;
    }

    protected double getTargetHeightdiff(Creature target) {
        return (target != null) ? (-target.getLocation().getZ() - -_actor.getLocation().getZ()) : 0.0;
    }

    protected int getTargetBodyHeight() {
        Creature target = getActor().getAggroList().getTarget();
        return (target != null) ? target.getTemplate().getBodyHeight() : 0;
    }

    protected boolean isTargetInHouseAny(Creature target) {
        return target != null && target.isPlayer() && ((Player) target).getHouseVisit().isInHouseAny();
    }

    protected Integer getLastDestination() {
        return 0;
    }

    protected int getTime() {
        return (int) (System.currentTimeMillis() - _aiStartTime);
    }

    protected int getGameTimeHour() {
        return GameTimeService.getInstance().getGameHour();
    }

    protected int getGameTimeMinute() {
        return GameTimeService.getInstance().getGameMinute();
    }

    protected boolean isRiderExist() {
        if (_actor.isVehicle()) {
            Servant servant = (Servant) _actor;
            return servant.isOwnerMounted();
        }
        return false;
    }

    protected int getSelfInventoryWeight() {
        if (_actor.isPlayable()) {
            Playable playable = (Playable) _actor;
            return playable.getGameStats().getWeight().getIntValue();
        }
        return 0;
    }

    protected int getSelfPartHp(int partIndex) {
        return 1;
    }

    protected String getClientType() {
        return "server";
    }

    protected int checkWeather(EWeatherFactorType weatherKind) {
        return 1;
    }

    protected boolean checkTrigger(long triggerId) {
        return false;
    }

    protected Integer[] getSenderInfoDestination() {
        return new Integer[0];
    }

    protected boolean isSelfRidable() {
        return _actor.getTemplate().isVehicleDriverRidable();
    }

    protected boolean checkBuff(Creature target, long moduleTypeIndex) {
        return target != null ? target.getBuffList().hasBuff(ModuleBuffType.valueOf((int) moduleTypeIndex)) : false;
    }

    protected void createParty(long minSize, long maxSize) {
        IParty<Creature> party = new CreatureParty(getActor(), (int) minSize, (int) maxSize);
        getActor().setParty(party);
        party.startTask();
    }

    protected boolean isPartyMember() {
        IParty<Creature> party = (IParty<Creature>) _actor.getParty();
        if (party != null) {
            return !party.isPartyLeader(_actor);
        }
        return getActor().getOwner() != null && getActor().getOwner().isPlayer();
    }

    protected boolean isPartyLeader() {
        IParty<Creature> party = (IParty<Creature>) _actor.getParty();
        return party != null && party.isPartyLeader(getActor());
    }

    protected boolean isParty() {
        return isPartyMember();
    }

    protected int getPartyMembersCount() {
        IParty<Creature> party = (IParty<Creature>) getActor().getParty();
        return (party != null) ? party.getMembersCount() : 0;
    }

    protected boolean checkOwnerEvadeEmergency() {
        return _actor.getActionStorage().getActionChartActionT().isEvadeEmergency();
    }

    protected void doAction(long actionHash, double blendTime, Consumer<Method> onActionEnd) {
        //System.out.println("!!!!!!!!!!!! Queueing doAction action=[" + System.currentTimeMillis() + "]");
        _isFindPathCompleted = true; // Fix for buggy AI thats getting stuck all the time.
        ActionStorage actionStorage = _actor.getActionStorage();
        if (actionStorage.getCurrentPackageMap() != null) {
            IAction parentAction = getActor().getActionStorage().getAction();
            if (parentAction.getActionHash() == actionHash && !parentAction.getActionChartActionT().getActionType().isRepeatable()) {
                onActionEnd.accept(null);
                return;
            }
            IAction action = actionStorage.getNewAction(actionHash);
            if (action == null) {
                onActionEnd.accept(null);
                return;
            }

            Creature target = getActor().getAggroList().getTarget();
            ActionChartActionT actionChartActionT = action.getActionChartActionT();
            action.setBlendTime(blendTime);
            action.setType((byte) 1);
            action.setNewLocation(getActor().getLocation());
            /*if (getActor().isMonster() && getActor().getOwner() != null && getActor().getOwner().isPlayer()) {
                Creature owner = getActor().getOwner();
                if (owner.getSummonStorage().containSummon(getActor())) {
                    IAction ownerAction = owner.getActionStorage().getAction();

                    if (ownerAction.getTargetGameObj() > 0) {
                        target = World.getInstance().getObjectById(ownerAction.getTargetGameObj());
                    }

                    if (target == null) {
                        Location centerLocation = owner.getLocation();
                        Location projectileLocation = new Location(
                                ownerAction.getOldX(),
                                ownerAction.getOldY(),
                                ownerAction.getOldZ(),
                                ownerAction.getSin(),
                                ownerAction.getCos()
                        );
                        double distanceToEnding = MathUtils.get3DDistance(projectileLocation, centerLocation);
                        int radius = getActor().getTemplate().getAttackRange() / 2;
                        if (distanceToEnding > owner.getTemplate().getAttackRange() / 2) {
                            double fromOriginToObjectX = projectileLocation.getX() - centerLocation.getX();
                            double fromOriginToObjectY = projectileLocation.getY() - centerLocation.getY();
                            double fromOriginToObjectZ = projectileLocation.getZ() - centerLocation.getZ();

                            fromOriginToObjectX *= radius / distanceToEnding;
                            fromOriginToObjectY *= radius / distanceToEnding;
                            fromOriginToObjectZ *= radius / distanceToEnding;

                            projectileLocation.setXYZ(
                                    centerLocation.getX() + fromOriginToObjectX,
                                    centerLocation.getY() + fromOriginToObjectY,
                                    centerLocation.getZ() + fromOriginToObjectZ
                            );
                        }

                        Stream<Creature> ctx = getObjects(EAIFindTargetType.Enemy, obj -> !obj.isDead() && MathUtils.isInRange(obj.getLocation(), projectileLocation, radius));
                        Optional<Creature> ctt = ctx.findFirst();
                        if (ctt.isPresent()) {
                            target = ctt.get();
                            action.setNewLocation(target.getLocation());
                        } else {
                            action.setNewLocation(projectileLocation);
                        }
                    } else {
                        action.setNewLocation(target.getLocation());
                    }
                }
            } else {*/
                if (actionChartActionT.isForceMove() && target != null) {
                    Location targetLoc = target.getLocation();
                    action.setTargetX(targetLoc.getX());
                    action.setTargetY(targetLoc.getY());
                    action.setTargetZ(targetLoc.getZ());
                } else {
                    action.setTargetX(0.0);
                    action.setTargetY(0.0);
                    action.setTargetZ(0.0);
                }
            //}
            action.setOwner(getActor());
            action.setTargetGameObjId((target != null) ? target.getGameObjectId() : -1024);
            getActor().setAction(action);
            action.doAction(target);
            getActor().getActionStorage().cancelActionTask();
            ActionTask actionTask = new ActionTask(action, target, _behavior, onActionEnd);
            actionTask.start();
        } else {
            onActionEnd.accept(null);
        }
    }

    protected void doTeleport(EAIMoveDestType method, int offsetX, int offsetY, int min, int max) {
        Location teleportLocation = null;
        int rndOffset = Rnd.get(min, max);
        switch (method) {
            case Random: {
                break;
            }
            case OwnerPosition: {
                Creature owner = getActor().getOwner();
                if (owner != null) {
                    teleportLocation = owner.getLocation();
                    break;
                }
                break;
            }
            default: {
                _log.warn("doTeleport method [{}] not implemented!", method);
                break;
            }
        }
        if (teleportLocation != null) {
            World.getInstance().teleport(getActor(), teleportLocation);
            _isFindPathCompleted = true;
        }
    }

    protected void doTeleportToWaypoint(String waypointName, String waypointFile, int offsetX, int offsetY, int min, int max) {
        WaypointT waypoint = WaypointData.getInstance().getWaypoint(waypointFile.toLowerCase(), waypointName.toLowerCase());
        if (waypoint != null) {
            Location teleportLocation = new Location(waypoint.getPosX() + offsetX + Rnd.get(min, max), waypoint.getPosY() + offsetY + Rnd.get(min, max), waypoint.getPosZ());
            World.getInstance().teleport(getActor(), teleportLocation);
            _isFindPathCompleted = true;
        }
    }

    protected void doTeleportToWaypoint(long[] waypointValue, int offsetX, int offsetY, int min, int max) {
    }

    protected void doTeleportWithOwnerToWaypoint(String waypointName, String waypointFile, int offsetX, int offsetY, int min, int max) {
        WaypointT waypoint = WaypointData.getInstance().getWaypoint(waypointFile.toLowerCase(), waypointName.toLowerCase());
        if (waypoint != null) {
            Location teleportLocation = new Location(waypoint.getPosX() + offsetX + Rnd.get(min, max), waypoint.getPosY() + offsetY + Rnd.get(min, max), waypoint.getPosZ());
            Creature owner = getActor().getOwner();
            if (owner != null) {
                World.getInstance().teleport(owner, teleportLocation);
            }
            World.getInstance().teleport(getActor(), teleportLocation);
            _isFindPathCompleted = true;
        }
    }

    protected void doTeleportPassengerToWaypoint(String waypointName, String waypointFile, int offsetX, int offsetY, int min, int max) {
        WaypointT waypoint = WaypointData.getInstance().getWaypoint(waypointFile.toLowerCase(), waypointName.toLowerCase());
        if (waypoint != null) {
            Location teleportLocation = new Location(waypoint.getPosX() + offsetX + Rnd.get(min, max), waypoint.getPosY() + offsetY + Rnd.get(min, max), waypoint.getPosZ());
            if (getActor().isVehicle()) {
                Servant servant = (Servant) getActor();
                if (servant != null) {
                    for (Player rider : servant.getCurrentRiders().values()) {
                        World.getInstance().teleport(rider, teleportLocation);
                    }
                }
            }
            _isFindPathCompleted = true;
        }
    }

    protected void useSkill(int skillId, int skillLevel, boolean isWideSkill, EAIFindTargetType targetType, Predicate<Creature> filter) {
        if (targetType != EAIFindTargetType.Self && isWideSkill) {
            Stream<Creature> targets = getObjects(targetType,
                    object -> !object.isDead()
                            && getActor().isEnemy(object)
                            && MathUtils.isInRange(getActor(), object, getActor().getTemplate().getAttackRange() * 2)
            ).filter(filter);

            SkillService.useSkill(getActor(), skillId, null, targets.collect(Collectors.toList()));
        } else {
            Creature target = getActor().getAggroList().getTarget();
            if (target != null) {
                SkillService.useSkill(getActor(), skillId, null, Collections.singletonList(target));
            }
        }

        _isFindPathCompleted = true;
    }

    protected void clearAggro(boolean dontKeepLastTarget) {
        getActor().getAggroList().clear(dontKeepLastTarget);
    }

    protected void worldNotify(EChatNoticeType type, String sheet, String value) {
        World.getInstance().broadcastWorldPacket(new SMAiWorldNotifier(getActor().getName(), type, sheet, value, 1));
    }

    protected void instanceNotify(EChatNoticeType type, String sheet, String value) {
        getActor().sendBroadcastPacket(new SMAiWorldNotifier(getActor().getName(), type, sheet, value, 0));
    }

    protected void setMonsterCollect(boolean isCollectable) {
        getActor().setIsCollectable(isCollectable);
    }

    protected int getRandom() {
        return getRandom(100);
    }

    protected int getRandom(long number) {
        return Rnd.get((int) number);
    }

    protected int getRandom(int number) {
        return Rnd.get(number);
    }

    public void setFindPathCompleted(boolean isFindPathCompleted) {
        isFindPathCompleted = isFindPathCompleted;
    }

    protected int isFindPathCompleted() {
        return _isFindPathCompleted ? 1 : 0;
    }

    protected int getSelfCombinePoint() {
        return getActor().getGameStats().getSubResourcePointStat().getIntValue();
    }

    protected int getTendency(Creature target) {
        if (target.isPlayer()) {
            Player player = (Player) target;
            return player.getTendency();
        }
        return 0;
    }

    protected boolean isTargetTypeOf(Creature target, EAIFindTargetType findTargetType) {
        if (target == null) {
            return false;
        }
        switch (findTargetType) {
            case Player:
            case PKPlayer: {
                return target.isPlayer();
            }
            case Monster: {
                return target.isMonster();
            }
            case Npc: {
                return target.isNpc();
            }
            case Collect: {
                return target.isCollect();
            }
            case Corpse: {
                return target.isDeadbody();
            }
            case Character: {
                return target.isCharacter();
            }
            case Enemy: {
                return getActor().isEnemy(target);
            }
            default: {
                _log.error("isTargetTypeOf of type " + findTargetType.toString() + " not handled!");
                return false;
            }
        }
    }

    protected boolean getTargetBattleAimedActionType(String actionType) {
        return true; // TODO lol
    }

    protected boolean getTargetBattleAimedActionType(Creature target, EBattleAimedActionType action) {
        return target.getActionStorage().getAction().getActionChartActionT().getBattleAimedActionType() == action;
    }

    protected int getTargetCollectorEquipmentGrade(Creature target) {
        return 0;
    }

    protected boolean getTargetProtectedRegion() {
        return getTargetProtectedRegion(getActor().getAggroList().getTarget());
    }

    protected boolean getTargetProtectedRegion(Creature target) {
        return target != null && target.getRegion().getTemplate().isSafe();
    }

    protected boolean getTargetIsCrouchAction() {
        return getTargetIsCrouchAction(getActor().getAggroList().getTarget());
    }

    protected boolean getTargetIsCrouchAction(Creature target) {
        return getTargetBattleAimedActionType(target, EBattleAimedActionType.Crouch);
    }

    protected boolean getTargetActionType(String actionType) {
        return false; // TODO
    }

    protected boolean getTargetActionType(Creature target, EActionType actionType) {
        return target.getActionStorage().getAction().getActionChartActionT().getActionType() == actionType;
    }

    protected void setTradePrice(long territoryKey, long npcKey, Map<ETradeCommerceType, Long> categoryPercentData, long eventMessageIndex) {
    }

    protected void setFormation(long id) {
    }

    protected void sendChat(String text) {
    }

    protected void summonCharacters(int characterKey, int actionIndex, int offsetX, int offsetY, int offsetZ, boolean isChild, int randomIntervalX, int randomIntervalY, int randomNumberRangeX, int randomNumberRangeY, int minSummonCount, int maxSummonCount) {
    }

    protected void summonCharacter(int characterKey, int actionIndex, int offsetX, int offsetY, int offsetZ, boolean isChild) {
    }

    protected void logout() {
        World.getInstance().deSpawn(_actor, ERemoveActorType.DespawnMonster);
    }

    protected void resetTamingInfo() {
        Creature target = getActor().getAggroList().getTarget();
        if (target != null) {
            ((Servant) getActor()).setTamed(false);
            ((Servant) getActor()).setTamingSugar(false);
            target.getAi().HandleFailTaming(getActor(), null);
        }
    }

    protected boolean isRegionSiegeBeing() {
        return false;
    }

    protected boolean isTargetVehicle(Creature target) {
        return target != null && target.isVehicle();
    }

    protected void deliveryItem(EAIDeliveryType type) {
        deliveryItem(type, "");
    }

    protected void deliveryItem(EAIDeliveryType type, String waypoint) {
        switch (type) {
            case prepare: {
                if (waypoint == null) {
                    break;
                }
                break;
            }
            case start: {
            }
        }
    }

    protected long getBuildingRate() {
        return 100L;
    }

    protected void setSettedPosition(Location settedPosition) {
        settedPosition = settedPosition;
    }

    @IAIHandler(hash = "1290912178")
    public EAiHandlerResult AllChildDie(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2484218329")
    public EAiHandlerResult Allkill(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2740311353")
    public EAiHandlerResult Appear(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "905919148")
    public EAiHandlerResult Appear_Dummy(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "791705105")
    public EAiHandlerResult Arrow_1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2067171133")
    public EAiHandlerResult Arrow_3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1450008214")
    public EAiHandlerResult Attack1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1066581056")
    public EAiHandlerResult AttackChild(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "607701532")
    public EAiHandlerResult Attack_Enemy(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4063847625")
    public EAiHandlerResult Attack_Go(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1800094866")
    public EAiHandlerResult Attack_Go_3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1207363245")
    public EAiHandlerResult Attack_Mid(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2115049902")
    public EAiHandlerResult Attack_Start(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "718796828")
    public EAiHandlerResult Attack_Stop(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2462800556")
    public EAiHandlerResult Attack_Stop_3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2443699652")
    public EAiHandlerResult AvoidBuffEnd(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2380053411")
    public EAiHandlerResult AvoidBuffStart(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "446470383")
    public EAiHandlerResult Avoid_Off(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4152934551")
    public EAiHandlerResult Avoid_On(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1913278491")
    public EAiHandlerResult BLAZEUP(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2822298286")
    public EAiHandlerResult Basic_1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "63499949")
    public EAiHandlerResult BattleWait70(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2995691259")
    public EAiHandlerResult Breath(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2737610566")
    public EAiHandlerResult BreathTurn(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1161243412")
    public EAiHandlerResult Broken_Leg(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "874773574")
    public EAiHandlerResult Broken_Shield(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3255765863")
    public EAiHandlerResult BuffAvoid(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3583113955")
    public EAiHandlerResult BuffCreateServant(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1550380537")
    public EAiHandlerResult BuffPowerUp(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2462413778")
    public EAiHandlerResult ByeEverybody(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "447497438")
    public EAiHandlerResult ByeWall(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4131143577")
    public EAiHandlerResult Call_from_Dialog(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2342654134")
    public EAiHandlerResult Call_remove(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1410820173")
    public EAiHandlerResult Call_stop(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1885305430")
    public EAiHandlerResult Charge_Complete(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2612603430")
    public EAiHandlerResult ChildAllDie(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "427659888")
    public EAiHandlerResult CreateServantBuffStart(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2791717878")
    public EAiHandlerResult Damage_Tet(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3560687232")
    public EAiHandlerResult Delete_Die(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3313471378")
    public EAiHandlerResult Destination(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2855604931")
    public EAiHandlerResult DestroyLeg1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2257777285")
    public EAiHandlerResult DestroyLeg2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3299647664")
    public EAiHandlerResult DestroyLeg3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "394216023")
    public EAiHandlerResult DestroyLeg4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2158890168")
    public EAiHandlerResult DestroyShield(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2090445936")
    public EAiHandlerResult Destroyed_Shrine(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2430336312")
    public EAiHandlerResult Die(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2630485736")
    public EAiHandlerResult Die_1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2381310103")
    public EAiHandlerResult Die_2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2066125109")
    public EAiHandlerResult Die_3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "454209141")
    public EAiHandlerResult Disappear(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1422518181")
    public EAiHandlerResult Disappear_Dummy(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3394423077")
    public EAiHandlerResult Disappear_Summoner(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "997136711")
    public EAiHandlerResult EarthsCalling_Dead(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2208469266")
    public EAiHandlerResult End_1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "78530330")
    public EAiHandlerResult End_3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "896550670")
    public EAiHandlerResult Evade(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2736870217")
    public EAiHandlerResult Fake(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1881801771")
    public EAiHandlerResult Fire_1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "152419010")
    public EAiHandlerResult Fire_2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3473282034")
    public EAiHandlerResult Flying(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3587558281")
    public EAiHandlerResult Golem_Eater(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "193725861")
    public EAiHandlerResult Gotohell(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2472935342")
    public EAiHandlerResult GuardBreak(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1682543590")
    public EAiHandlerResult GuardCrash(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3321826024")
    public EAiHandlerResult HandleActiveSiegeObject(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3187836589")
    public EAiHandlerResult HandleAirFloat(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1415363862")
    public EAiHandlerResult HandleAirSmash(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3993068578")
    public EAiHandlerResult HandleArshaDoorClose(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "676292460")
    public EAiHandlerResult HandleArshaDoorOpen(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3626416843")
    public EAiHandlerResult HandleAtMorning(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1866615037")
    public EAiHandlerResult HandleAtNight(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "353639391")
    public EAiHandlerResult HandleAtSpawnEndTime(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "201352807")
    public EAiHandlerResult HandleAtSpawnStartTime(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "89592832")
    public EAiHandlerResult HandleAwakeGuildBoss(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2396605453")
    public EAiHandlerResult HandleBattleAttack1_Miss(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2257749140")
    public EAiHandlerResult HandleBattleEnd(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1322471972")
    public EAiHandlerResult HandleBattleMode(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1309034404")
    public EAiHandlerResult HandleBound(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "624921365")
    public EAiHandlerResult HandleBrotherCall(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3258312682")
    public EAiHandlerResult HandleCallDrop(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1931877142")
    public EAiHandlerResult HandleCallSummon(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3077731808")
    public EAiHandlerResult HandleCampingTentClose(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4001679758")
    public EAiHandlerResult HandleCanRunAway(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3889421901")
    public EAiHandlerResult HandleCapture(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1548328339")
    public EAiHandlerResult HandleChangeBuildingRate(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3541169752")
    public EAiHandlerResult HandleCharacterSelected(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1609361535")
    public EAiHandlerResult HandleCharacterUnSelected(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "968835853")
    public EAiHandlerResult HandleCheckStatus_Idle(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1973164328")
    public EAiHandlerResult HandleCheckStatus_Run(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3628849585")
    public EAiHandlerResult HandleClose(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3976331182")
    public EAiHandlerResult HandleCollectFollow(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1286756339")
    public EAiHandlerResult HandleCollectHere(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1829097392")
    public EAiHandlerResult HandleComeon(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "442668428")
    public EAiHandlerResult HandleCompleteHouseMesh(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "853920211")
    public EAiHandlerResult HandleCompleteSiegeObject(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3892619197")
    public EAiHandlerResult HandleCrankClose(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3974958039")
    public EAiHandlerResult HandleCrankOpen(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3547425893")
    public EAiHandlerResult HandleDead(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4045645602")
    public EAiHandlerResult HandleDestroyedBase(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2732536145")
    public EAiHandlerResult HandleDisableAvoidBuff(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "991432970")
    public EAiHandlerResult HandleDisablePowerUpBuff(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2146225628")
    public EAiHandlerResult HandleDonRunAway(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "980614633")
    public EAiHandlerResult HandleDownSmash(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "550607027")
    public EAiHandlerResult HandleDriveEnd(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "596517142")
    public EAiHandlerResult HandleEvade(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2092281803")
    public EAiHandlerResult HandleEvent1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "311697560")
    public EAiHandlerResult HandleEvent2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1201583084")
    public EAiHandlerResult HandleEvent3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1346851635")
    public EAiHandlerResult HandleEvent_Battle(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3581824485")
    public EAiHandlerResult HandleEvent_Corpse(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "327942035")
    public EAiHandlerResult HandleEvent_Follow(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "376981050")
    public EAiHandlerResult HandleEvent_Stress(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1021733138")
    public EAiHandlerResult HandleExplosion(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "583240832")
    public EAiHandlerResult HandleFailSteal(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1359351956")
    public EAiHandlerResult HandleFailTaming(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1987938251")
    public EAiHandlerResult HandleFearReleased(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3405531475")
    public EAiHandlerResult HandleFeared(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3827643539")
    public EAiHandlerResult HandleFireWork(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4294393355")
    public EAiHandlerResult HandleFishCountZero(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2525978823")
    public EAiHandlerResult HandleFollow(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "341616890")
    public EAiHandlerResult HandleFollowMe(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3613096071")
    public EAiHandlerResult HandleFollowMeBattle(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1761415691")
    public EAiHandlerResult HandleFollowMeOwnerPath(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2958762349")
    public EAiHandlerResult HandleGoHome(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1363764655")
    public EAiHandlerResult HandleGoMoveBombPos(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "473418052")
    public EAiHandlerResult HandleGoPatrol(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1648822609")
    public EAiHandlerResult HandleGroggy(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2284542644")
    public EAiHandlerResult HandleGuardCrash(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1238867070")
    public EAiHandlerResult HandleHelpMe(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1835048577")
    public EAiHandlerResult HandleHelpMe_Goral(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3816682433")
    public EAiHandlerResult HandleIceFreeze(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "227307338")
    public EAiHandlerResult HandleIdle(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4124421458")
    public EAiHandlerResult HandleInitialize(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "837041619")
    public EAiHandlerResult HandleKnockBack(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3139211571")
    public EAiHandlerResult HandleKnockDown(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "51297134")
    public EAiHandlerResult HandleLobby(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3822788232")
    public EAiHandlerResult HandleLordKingGreeting(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1926689818")
    public EAiHandlerResult HandleLostWay(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1217796820")
    public EAiHandlerResult HandleMoveInWater(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1201321949")
    public EAiHandlerResult HandleOnOwnerDead(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "401526075")
    public EAiHandlerResult HandleOnResetAI(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3010628996")
    public EAiHandlerResult HandleOnRifleDead(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1813101302")
    public EAiHandlerResult HandleOneStepClose(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1330789192")
    public EAiHandlerResult HandleOneStepOpen(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "709393928")
    public EAiHandlerResult HandleOpen(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3631129163")
    public EAiHandlerResult HandleOrderAttack(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4183464306")
    public EAiHandlerResult HandleOrderDie(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1611427956")
    public EAiHandlerResult HandleOrderMove(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2774063496")
    public EAiHandlerResult HandleOrderReturn(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2509308169")
    public EAiHandlerResult HandleOrderSkill1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1600442817")
    public EAiHandlerResult HandleOrderSkill2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "127482713")
    public EAiHandlerResult HandleOrderSkill3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3591230271")
    public EAiHandlerResult HandleOrderSkill4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3357121337")
    public EAiHandlerResult HandleOrderStone(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1446457670")
    public EAiHandlerResult HandleParkingHorse(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3395025101")
    public EAiHandlerResult HandleParkingOff(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "205610247")
    public EAiHandlerResult HandlePartyCheck(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1875385985")
    public EAiHandlerResult HandlePartyInvited(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4105890744")
    public EAiHandlerResult HandlePartyInvited2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2546575704")
    public EAiHandlerResult HandlePartyReleased(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2205388004")
    public EAiHandlerResult HandlePetAttachBoned(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2927580331")
    public EAiHandlerResult HandlePetAttachable(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3711932128")
    public EAiHandlerResult HandlePetNotAttachable(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2956928306")
    public EAiHandlerResult HandlePushsheep(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3871610810")
    public EAiHandlerResult HandleReadyForRush(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3936072436")
    public EAiHandlerResult HandleReleased(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1631608360")
    public EAiHandlerResult HandleRestrictReleased(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "626048896")
    public EAiHandlerResult HandleRevive(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1108539522")
    public EAiHandlerResult HandleRideDead(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2814951686")
    public EAiHandlerResult HandleRideEnd(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "854308447")
    public EAiHandlerResult HandleRideOff(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "863559949")
    public EAiHandlerResult HandleRideOffRun_Faster(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1818521086")
    public EAiHandlerResult HandleRideOffRun_Fastest(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1571334030")
    public EAiHandlerResult HandleRideOffWalk(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4215890524")
    public EAiHandlerResult HandleRideOff_Back(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1718950311")
    public EAiHandlerResult HandleRideOff_Lv1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1080328733")
    public EAiHandlerResult HandleRideOff_Lv2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2128185277")
    public EAiHandlerResult HandleRideOff_Lv4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2925865590")
    public EAiHandlerResult HandleRideOff_Wait(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4281175236")
    public EAiHandlerResult HandleRideOn(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3382457948")
    public EAiHandlerResult HandleRigid(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "110441680")
    public EAiHandlerResult HandleSacredBarrier(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2313555520")
    public EAiHandlerResult HandleSkillLearnFailBack(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1327502285")
    public EAiHandlerResult HandleSkillLearnFailFront(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3836774399")
    public EAiHandlerResult HandleSpewLava(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1529285313")
    public EAiHandlerResult HandleStance1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3920838939")
    public EAiHandlerResult HandleStance10(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "666349263")
    public EAiHandlerResult HandleStance11(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1216364741")
    public EAiHandlerResult HandleStance12(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3279103436")
    public EAiHandlerResult HandleStance13(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2095602460")
    public EAiHandlerResult HandleStance14(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3185925271")
    public EAiHandlerResult HandleStance15(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1240653976")
    public EAiHandlerResult HandleStance16(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "798588796")
    public EAiHandlerResult HandleStance17(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3006649996")
    public EAiHandlerResult HandleStance18(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "676778045")
    public EAiHandlerResult HandleStance19(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1423575966")
    public EAiHandlerResult HandleStance2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2921324482")
    public EAiHandlerResult HandleStance20(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3665107672")
    public EAiHandlerResult HandleStance21(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3487915199")
    public EAiHandlerResult HandleStance22(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4008299373")
    public EAiHandlerResult HandleStance23(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1166824298")
    public EAiHandlerResult HandleStance24(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3614164034")
    public EAiHandlerResult HandleStance25(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3958420928")
    public EAiHandlerResult HandleStance3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1620437360")
    public EAiHandlerResult HandleStance4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4054913021")
    public EAiHandlerResult HandleStance5(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1597961229")
    public EAiHandlerResult HandleStance50(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2568185090")
    public EAiHandlerResult HandleStance51(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1921357945")
    public EAiHandlerResult HandleStance52(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4171865259")
    public EAiHandlerResult HandleStance60(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3738077158")
    public EAiHandlerResult HandleStance98(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "902022121")
    public EAiHandlerResult HandleStance99(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "690982510")
    public EAiHandlerResult HandleStanceAttack(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1434682025")
    public EAiHandlerResult HandleStanceDefense(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "43326215")
    public EAiHandlerResult HandleStanceHold(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3469476637")
    public EAiHandlerResult HandleStop(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "330017025")
    public EAiHandlerResult HandleStun(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1864554786")
    public EAiHandlerResult HandleStuned(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "761323196")
    public EAiHandlerResult HandleTakeDamage(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3919954971")
    public EAiHandlerResult HandleTakeTarget(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "73657324")
    public EAiHandlerResult HandleTakeTeamDamage(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "220056680")
    public EAiHandlerResult HandleTaming(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2407352108")
    public EAiHandlerResult HandleTamingStep1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1562854400")
    public EAiHandlerResult HandleTamingStep2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1546847314")
    public EAiHandlerResult HandleTamingSugar(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1869838902")
    public EAiHandlerResult HandleTargetHit(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1556925130")
    public EAiHandlerResult HandleTargetInMyArea(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3719452519")
    public EAiHandlerResult HandleTeleportToParent(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1497015852")
    public EAiHandlerResult HandleThrowBomb(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1466898781")
    public EAiHandlerResult HandleTimeout(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3612495228")
    public EAiHandlerResult HandleTryTaming(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1851945326")
    public EAiHandlerResult HandleUnderWater(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2084695777")
    public EAiHandlerResult HandleUnderWaterDead(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4005042604")
    public EAiHandlerResult HandleUpdateCombineWave(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2304111963")
    public EAiHandlerResult HandleWhistle(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "697218282")
    public EAiHandlerResult HandleWorkingEnd(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2602741002")
    public EAiHandlerResult HandleWorkingStart(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3887600735")
    public EAiHandlerResult Handle_RideOn(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3173309821")
    public EAiHandlerResult HandlerAtSpawnEndTime(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2455435060")
    public EAiHandlerResult HandlerDestinationAttackFastMove(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "945499764")
    public EAiHandlerResult HandlerDestinationAttackMove(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4098324299")
    public EAiHandlerResult HandlerDestinationFastMove(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1919879514")
    public EAiHandlerResult HandlerDestinationMove(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2132345350")
    public EAiHandlerResult HandlerEscort_Reset(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "640709018")
    public EAiHandlerResult HandlerNaturalDeath(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2774138489")
    public EAiHandlerResult HandlerSendToEnd(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "379736233")
    public EAiHandlerResult HandlerSendToGrow(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "350075393")
    public EAiHandlerResult HandlerTalkToDie(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "344379914")
    public EAiHandlerResult HandlerTalkToGrow(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3825286469")
    public EAiHandlerResult HandlerTargetAttackFastMove(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2291620525")
    public EAiHandlerResult HandlerTargetAttackMove(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4041130865")
    public EAiHandlerResult HandlerTargetFastMove(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "625534686")
    public EAiHandlerResult HandlerTargetMove(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3561064346")
    public EAiHandlerResult HandlerTypeHappenEvent(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2907164153")
    public EAiHandlerResult Handler_Eavesdrop(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3785189473")
    public EAiHandlerResult HappyNewYear(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "78110663")
    public EAiHandlerResult HiddenTet_Off(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3901738502")
    public EAiHandlerResult HiddenTet_On(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2843930506")
    public EAiHandlerResult Inhale_Start(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2001172249")
    public EAiHandlerResult Inhale_Stop(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3824159579")
    public EAiHandlerResult JumpHigh(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4121823118")
    public EAiHandlerResult JumpLow(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4171902737")
    public EAiHandlerResult Lightning_1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "727179714")
    public EAiHandlerResult Lightning_2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3778689843")
    public EAiHandlerResult Lightning_3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "904280106")
    public EAiHandlerResult LookAtMe1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3073383964")
    public EAiHandlerResult LookAtMe2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1490857914")
    public EAiHandlerResult LookAtMe3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "306108620")
    public EAiHandlerResult LookAtMe4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4088530404")
    public EAiHandlerResult LookAtMe5(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2569515461")
    public EAiHandlerResult Mami(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2200348211")
    public EAiHandlerResult Mamibye(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3722537660")
    public EAiHandlerResult Missile(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3626522909")
    public EAiHandlerResult NextMove(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3308599880")
    public EAiHandlerResult NextStage(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2569426954")
    public EAiHandlerResult Order(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2713589166")
    public EAiHandlerResult OrderHT_Awakening_Bash_A(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3017571034")
    public EAiHandlerResult OrderHT_Awakening_Bash_B(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3632300525")
    public EAiHandlerResult OrderHT_Awakening_Bash_C(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3041072002")
    public EAiHandlerResult OrderHT_Awakening_Bash_D(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2542790834")
    public EAiHandlerResult OrderHT_Awakening_Crush(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1084830378")
    public EAiHandlerResult OrderHT_Awakening_Crush_A(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2657228905")
    public EAiHandlerResult OrderHT_Awakening_Dash(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2839645697")
    public EAiHandlerResult OrderHT_Awakening_SummonHaetae(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1317318710")
    public EAiHandlerResult OrderHT_Awakening_SummonHaetae_AGGRO(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2624603230")
    public EAiHandlerResult OrderHT_BattleAttack1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "596335419")
    public EAiHandlerResult OrderHT_BattleAttack2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "836097144")
    public EAiHandlerResult OrderHT_BattleAttack3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "261695438")
    public EAiHandlerResult OrderHT_BattleWait(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3813507075")
    public EAiHandlerResult OrderHT_FrontSractch(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2310148107")
    public EAiHandlerResult OrderHT_GoodDie(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "294133472")
    public EAiHandlerResult OrderHT_HaetaeSpear(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "446009256")
    public EAiHandlerResult OrderHT_Rage200(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3205169908")
    public EAiHandlerResult OrderHT_Roaring(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1560048548")
    public EAiHandlerResult OrderHT_STAY(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1686167625")
    public EAiHandlerResult OrderHT_Teleport(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2275351631")
    public EAiHandlerResult OrderHT_ThunderStorm(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1494996454")
    public EAiHandlerResult OrderHT_ThunderZone(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3453216163")
    public EAiHandlerResult OrderHT_ThunderZone_Cool(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2386602288")
    public EAiHandlerResult Parent_Die(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2402100715")
    public EAiHandlerResult Park_Open(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3707051721")
    public EAiHandlerResult PowerUpBuffEnd(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "172513177")
    public EAiHandlerResult PowerUpBuffStart(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4055849981")
    public EAiHandlerResult Provocation(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "283053700")
    public EAiHandlerResult Return(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1504565621")
    public EAiHandlerResult Revival(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1612213247")
    public EAiHandlerResult Roar(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3081367948")
    public EAiHandlerResult Roarattack(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3461356579")
    public EAiHandlerResult Rotate1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "561682168")
    public EAiHandlerResult Rotate2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3390844402")
    public EAiHandlerResult Rotate3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2521363158")
    public EAiHandlerResult Rotate4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1543969990")
    public EAiHandlerResult Rotate5(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1412885419")
    public EAiHandlerResult Searching(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4064049900")
    public EAiHandlerResult SeeYouAgain(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3125921989")
    public EAiHandlerResult Seek(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1397209514")
    public EAiHandlerResult Send_Target(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1220807052")
    public EAiHandlerResult SkeletonKing(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "958939878")
    public EAiHandlerResult Skill_1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3605133580")
    public EAiHandlerResult SpeedUp(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3161274069")
    public EAiHandlerResult StandUpFriend(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3375105074")
    public EAiHandlerResult Start_Bomb(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2364529875")
    public EAiHandlerResult Stop_Summon(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "356238739")
    public EAiHandlerResult Straight(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "651319827")
    public EAiHandlerResult Suicide(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "887467522")
    public EAiHandlerResult Summon(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3603366565")
    public EAiHandlerResult SummonForFight(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4103769510")
    public EAiHandlerResult Summon_1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3583051855")
    public EAiHandlerResult Summon_2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1055819269")
    public EAiHandlerResult Summon_3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "588521832")
    public EAiHandlerResult Summon_4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3513124609")
    public EAiHandlerResult Summon_Bell(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "398165994")
    public EAiHandlerResult TargetInfo(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2294523694")
    public EAiHandlerResult Teleport(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3311899225")
    public EAiHandlerResult Teleport_Position_A1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4128937788")
    public EAiHandlerResult Teleport_Position_A2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "6597013")
    public EAiHandlerResult Teleport_Position_A3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4285217465")
    public EAiHandlerResult Teleport_Position_A4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2759718864")
    public EAiHandlerResult Teleport_Position_A5(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1752567701")
    public EAiHandlerResult Teleport_Position_B1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "166232532")
    public EAiHandlerResult Teleport_Position_B2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1201808693")
    public EAiHandlerResult Teleport_Position_B3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2116110346")
    public EAiHandlerResult Teleport_Position_B4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3764826799")
    public EAiHandlerResult Teleport_Position_B5(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1198041463")
    public EAiHandlerResult Teleport_Position_C1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1504763516")
    public EAiHandlerResult Teleport_Position_C2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2771368023")
    public EAiHandlerResult Teleport_Position_C3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2319301858")
    public EAiHandlerResult Teleport_Position_C4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "47569606")
    public EAiHandlerResult Teleport_Position_C5(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4068913879")
    public EAiHandlerResult Teleport_Position_D1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3246998530")
    public EAiHandlerResult Teleport_Position_D2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "169576579")
    public EAiHandlerResult Teleport_Position_D3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2960456187")
    public EAiHandlerResult Teleport_Position_D4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2027048973")
    public EAiHandlerResult Teleport_Position_D5(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "578144826")
    public EAiHandlerResult Teleport_Position_E1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3948085046")
    public EAiHandlerResult Teleport_Position_E2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4042942031")
    public EAiHandlerResult Teleport_Position_E3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2578726001")
    public EAiHandlerResult Teleport_Position_E4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4088892051")
    public EAiHandlerResult Teleport_Position_E5(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4088353418")
    public EAiHandlerResult The_End(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2763515143")
    public EAiHandlerResult ThunderCloud(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3350247032")
    public EAiHandlerResult ThunderCloud2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1073871538")
    public EAiHandlerResult ThunderFall(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1048306759")
    public EAiHandlerResult ThunderFall2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1432925103")
    public EAiHandlerResult TimeAttack(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "559358876")
    public EAiHandlerResult TimeStop(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3333745027")
    public EAiHandlerResult Tornado(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2168560282")
    public EAiHandlerResult Trap(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "364918374")
    public EAiHandlerResult Trap2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2052176001")
    public EAiHandlerResult Turn_Undead(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3990853728")
    public EAiHandlerResult Valentine(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3986940732")
    public EAiHandlerResult Victory(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2255254665")
    public EAiHandlerResult Wait(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3412417991")
    public EAiHandlerResult WakeUp(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "81723104")
    public EAiHandlerResult WakeUp_Test(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4274955020")
    public EAiHandlerResult Wall_1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "697162408")
    public EAiHandlerResult Wall_2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2724199768")
    public EAiHandlerResult WaterFall(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1894107243")
    public EAiHandlerResult Water_1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3343629053")
    public EAiHandlerResult Water_2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3125422553")
    public EAiHandlerResult Wave1_A(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1153071630")
    public EAiHandlerResult Wave1_B(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4146998502")
    public EAiHandlerResult Wave1_C(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3299778720")
    public EAiHandlerResult Wave2_A(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2258123273")
    public EAiHandlerResult Wave2_B(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "314240016")
    public EAiHandlerResult Wave2_B2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1202820681")
    public EAiHandlerResult Wave2_C(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2289803508")
    public EAiHandlerResult Wave3_A(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3942032409")
    public EAiHandlerResult Wave3_A1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1142032631")
    public EAiHandlerResult Wave3_A2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2486064215")
    public EAiHandlerResult Wave3_B(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "233360107")
    public EAiHandlerResult Wave3_C(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4222693119")
    public EAiHandlerResult Wave4_A(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "552424337")
    public EAiHandlerResult Wave4_B(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2375552750")
    public EAiHandlerResult Wave4_C(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1304405504")
    public EAiHandlerResult Wave4_C1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2694418065")
    public EAiHandlerResult Wave4_C2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4190145078")
    public EAiHandlerResult Wave4_C3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2301483860")
    public EAiHandlerResult Wave4_C4(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3531290792")
    public EAiHandlerResult Wave4_END(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "503146685")
    public EAiHandlerResult Wave5_A(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4245466875")
    public EAiHandlerResult Wave5_B(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2868313832")
    public EAiHandlerResult Wave5_C(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4005754321")
    public EAiHandlerResult WaveClear(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1589712054")
    public EAiHandlerResult YouDie(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4251683673")
    public EAiHandlerResult _1Phase(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3472272119")
    public EAiHandlerResult _200GO(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3897444988")
    public EAiHandlerResult _2Phase(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3062082026")
    public EAiHandlerResult _Alert(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2631590622")
    public EAiHandlerResult _AllChildDie(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3984710144")
    public EAiHandlerResult _Battle_End(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3966891199")
    public EAiHandlerResult _Battle_Str(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2797348099")
    public EAiHandlerResult _Deadstate(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1196389652")
    public EAiHandlerResult _DestroyedBase(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2903048478")
    public EAiHandlerResult _GargoyleDie(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2660107905")
    public EAiHandlerResult _PathEnd(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "873113471")
    public EAiHandlerResult _StageBossDie(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3556200437")
    public EAiHandlerResult _StrAllmaidLogOut(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3562356314")
    public EAiHandlerResult _Target_Attack(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2112119810")
    public EAiHandlerResult _TimeOut(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3106146113")
    public EAiHandlerResult _helpme(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3302413739")
    public EAiHandlerResult _maidLogOut(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "4215738317")
    public EAiHandlerResult _marketMaid(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2949421024")
    public EAiHandlerResult _marketMaidLogOut(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3904977447")
    public EAiHandlerResult _warehouseMaid(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1308513324")
    public EAiHandlerResult _warehouseMaidLogOut(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "33343585")
    public EAiHandlerResult fly_escape(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3919943268")
    public EAiHandlerResult fly_near(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2555726981")
    public EAiHandlerResult handlePetFindThatOff(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3852055557")
    public EAiHandlerResult handlePetFindThatOn(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1817170390")
    public EAiHandlerResult handlePetFollowMaster(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1229907964")
    public EAiHandlerResult handlePetGetItemOff(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2594697492")
    public EAiHandlerResult handlePetGetItemOn(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "2880602597")
    public EAiHandlerResult handlePetWaitMaster(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1466898781")
    public EAiHandlerResult handleTimeout(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1254184497")
    public EAiHandlerResult handler_ComeBack(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "1954113150")
    public EAiHandlerResult handler_LoadMove(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3528122573")
    public EAiHandlerResult handler_LoadOff(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "250853726")
    public EAiHandlerResult handler_LoadStuff(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3912555689")
    public EAiHandlerResult handler_Measure(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "738706747")
    public EAiHandlerResult handler_OrderToGo(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "956457588")
    public EAiHandlerResult handler_Reset(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3407326234")
    public EAiHandlerResult handler_ScaleStuff_1(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "956536152")
    public EAiHandlerResult handler_ScaleStuff_2(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "771396221")
    public EAiHandlerResult handler_ScaleStuff_3(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
    @IAIHandler(hash = "3752352915")
    public EAiHandlerResult handler_Scale_Reset(final Creature sender, final Integer[] eventData) {
        return EAiHandlerResult.NONE;
    }
}
