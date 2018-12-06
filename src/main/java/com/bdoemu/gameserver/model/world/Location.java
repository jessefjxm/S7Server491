package com.bdoemu.gameserver.model.world;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.world.region.GameSector;
import com.bdoemu.gameserver.model.world.region.Region;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class Location extends JSONable implements Cloneable {
    private double x;
    private double y;
    private double z;
    private double cos;
    private double sin;
    private double vehicleX;
    private double vehicleY;
    private double vehicleZ;
    private GameSector gameSector;
    private Region region;

    public Location() {
        this.sin = 1.0;
    }

    public Location(final double x, final double y, final double z, final double cos, final double sin) {
        this.sin = 1.0;
        this.x = x;
        this.y = y;
        this.z = z;
        this.cos = cos;
        this.sin = sin;
    }

    public Location(final double x, final double y, final double z) {
        this(x, y, z, 0.0, 0.0);
    }

    public Location(final SpawnPlacementT spawnPlacementT) {
        this.sin = 1.0;
        if (spawnPlacementT != null && spawnPlacementT.getLocation() != null) {
            final Location loc = spawnPlacementT.getLocation();
            this.x = loc.getX();
            this.y = loc.getY();
            this.z = loc.getZ();
            this.cos = loc.getCos();
            this.sin = loc.getSin();
        }
    }

    public Location(final Location loc) {
        this(loc.getX(), loc.getY(), loc.getZ(), loc.getCos(), loc.getSin());
    }

    public Location(final BasicDBObject dbObject) {
        this.sin = 1.0;
        if (dbObject != null) {
            this.x = dbObject.getDouble("x");
            this.y = dbObject.getDouble("y");
            this.z = dbObject.getDouble("z");
            this.cos = dbObject.getDouble("cos");
            this.sin = dbObject.getDouble("sin");
        }
    }

    public void unsetLocation() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.cos = 0.0;
        this.sin = 0.0;
    }

    public void setLocation(final Location location) {
        if (location != null) {
            this.x = location.x;
            this.y = location.y;
            this.z = location.z;
            this.cos = location.cos;
            this.sin = location.sin;
        }
    }

    public void setLocation(final double x, final double y, final double z, final double cos, final double sin) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.cos = cos;
        this.sin = sin;
    }

    public void setXYZ(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setLocation(final double cos, final double sin) {
        this.cos = cos;
        this.sin = sin;
    }

    public void setDirection(final double direction) {
        this.cos = Math.cos(direction);
        this.sin = Math.sin(direction);
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(final Region region) {
        this.region = region;
    }

    public void setVehicleLocation(final double x, final double y, final double z) {
        this.vehicleX = x;
        this.vehicleY = y;
        this.vehicleZ = z;
    }

    public double getVehicleX() {
        return this.vehicleX;
    }

    public double getVehicleY() {
        return this.vehicleY;
    }

    public double getVehicleZ() {
        return this.vehicleZ;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public double getCos() {
        return this.cos;
    }

    public double getSin() {
        return this.sin;
    }

    public GameSector getGameSector() {
        return this.gameSector;
    }

    public boolean isValid() {
        return x != 0 && y != 0 && z != 0;
    }

    public void invalidate() {
        x = 0;
        y = 0;
        z = 0;
    }

    public void setGameSector(final GameSector gameSector) {
        this.gameSector = gameSector;
    }

    public Location clone() {
        final Location loc = new Location(this);
        loc.setGameSector(this.getGameSector());
        return loc;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("x", this.x);
        builder.append("y", this.y);
        builder.append("z", this.z);
        builder.append("cos", this.cos);
        builder.append("sin", this.sin);
        return builder.get();
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Location: ");
        sb.append("x: ").append(this.x).append(", ");
        sb.append("y: ").append(this.y).append(", ");
        sb.append("z: ").append(this.z).append(", ");
        sb.append("sin: ").append(this.sin).append(", ");
        sb.append("cos: ").append(this.cos).append("; ");
        if (this.gameSector != null) {
            sb.append(this.gameSector.toString());
        }
        if (this.region != null) {
            sb.append("regionId: ").append(this.region.getTemplate().getRegionId()).append(";");
        }
        return sb.toString();
    }
}
