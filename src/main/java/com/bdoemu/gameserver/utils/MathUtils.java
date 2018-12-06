package com.bdoemu.gameserver.utils;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.world.Location;

public class MathUtils {
    public static boolean isInRange(final double x1, final double y1, final double x2, final double y2, final int range) {
        return getDistance(x1, y1, x2, y2) < range;
    }

    public static boolean isInRange(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2, final int range) {
        return getDistance(x1, y1, z1, x2, y2, z2) < range;
    }

    public static boolean isInRange(final Location loc1, final Location loc2, final int range) {
        return getDistance(loc1.getX(), loc1.getY(), loc2.getX(), loc2.getY()) < range;
    }

    public static boolean isInRange(final Creature object1, final Creature object2, final int range) {
        return isInRange(object1.getLocation(), object2.getLocation(), range);
    }

    public static boolean isIn3DRange(final Creature object1, final Creature object2, final int range) {
        final Location loc1 = object1.getLocation();
        final Location loc2 = object2.getLocation();
        return getDistance(loc1.getX(), loc1.getY(), loc1.getZ(), loc2.getX(), loc2.getY(), loc2.getZ()) < range;
    }

    public static double getDistance(final double x1, final double y1, final double x2, final double y2) {
        final double xDif = x1 - x2;
        final double yDif = y1 - y2;
        return Math.sqrt(xDif * xDif + yDif * yDif);
    }

    public static double getDistance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double xDif = x1 - x2;
        final double yDif = y1 - y2;
        final double zDif = z1 - z2;
        return Math.sqrt(xDif * xDif + yDif * yDif + zDif * zDif);
    }

    public static double getDistance(final Location from, final Location to) {
        final double xDif = from.getX() - to.getX();
        final double yDif = from.getY() - to.getY();
        return Math.sqrt(xDif * xDif + yDif * yDif);
    }

    public static double get3DDistance(final Location from, final Location to) {
        final double xDif = from.getX() - to.getX();
        final double yDif = from.getY() - to.getY();
        final double zDif = from.getZ() - to.getZ();
        return Math.sqrt(xDif * xDif + yDif * yDif + zDif * zDif);
    }

    public static double getAngle(final Location from, final Location to) {
        return Math.atan2(from.getY() - to.getY(), from.getX() - to.getX());
    }

    public static boolean isInRangeExclude(final int low, final int hi, final int value) {
        return value > low && value < hi;
    }

    public static boolean isInRangeInclude(final int low, final int hi, final int value) {
        return value >= low && value <= hi;
    }
}
