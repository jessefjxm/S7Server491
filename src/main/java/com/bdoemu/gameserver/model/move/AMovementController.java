package com.bdoemu.gameserver.model.move;

import com.bdoemu.gameserver.model.actions.enums.ENaviType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.world.Location;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * @author H1X4
 * @param <T> Type of Creature that needs a movement controller.
 */
public abstract class AMovementController<T extends Creature> {
    private T _owner;

    /**
     * Default constructor for Movement controller.
     * @param owner Movement controller owner.
     */
    @SuppressWarnings("WeakerAccess")
    protected AMovementController(final T owner) {
        _owner = owner;
    }

    /**
     * Checks if owner of this controller can move to specified location.
     * @param x Position X
     * @param y Position Y
     * @return true if can move, otherwise false.
     */
    public abstract boolean canMove(double x, double y);

    /**
     * Starts moving the character to specified destination using already generated waypoints
     *
     * @param destinations  List of destinations to move the character.
     * @param onEnd         Function to be called when destination has been reached.
     * @param onExit        Function to be called when we failed to reach the destination.
     * @param naviType      Navigation type of movement.
     */
    public void startMove(Collection<Location> destinations, Callable<Boolean> onEnd, Consumer<Method> onExit, ENaviType naviType) {
        //
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
    public void startMove(Location destination, Callable<Boolean> movementCompleted, Consumer<Method> movementFailed, ENaviType naviType, boolean moveStraight) {
        //
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
        //
    }

    /**
     * Checks if the controller is currently moving.
     * @return true if creature is moving, otherwise false.
     */
    public abstract boolean isMoving();

    /**
     * Returns the location from where the character has started moving.
     * @return Origin location from where the character is currently moving.
     */
    public abstract Location getOrigin();

    /**
     * Returns the destination to where the character is currently moving.
     * @return Destination to where the character is going to move.
     */
    public abstract Location getDestination();

    /**
     * Cancels a movement task, when no movement is needed, or we need
     * to forcibly stop a moving player.
     */
    public abstract void cancelMoveTask();

    /**
     * Gets the owner of the controller.
     * @return Creature owner.
     * @see Creature
     */
    public T getOwner() {
        return _owner;
    }
}