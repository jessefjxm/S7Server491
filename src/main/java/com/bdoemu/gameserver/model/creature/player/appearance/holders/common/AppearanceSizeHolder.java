package com.bdoemu.gameserver.model.creature.player.appearance.holders.common;

import com.bdoemu.commons.database.mongo.JSONable;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class AppearanceSizeHolder extends JSONable {
    private int positionX;
    private int positionY;
    private int positionZ;
    private int rotationX;
    private int rotationY;
    private int rotationZ;
    private int stretchX;
    private int stretchY;
    private int stretchZ;

    public AppearanceSizeHolder() {
    }

    public AppearanceSizeHolder(final BasicDBObject dbObject) {
        this.positionX = dbObject.getInt("positionX");
        this.positionY = dbObject.getInt("positionY");
        this.positionZ = dbObject.getInt("positionZ");
        this.rotationX = dbObject.getInt("rotationX");
        this.rotationY = dbObject.getInt("rotationY");
        this.rotationZ = dbObject.getInt("rotationZ");
        this.stretchX = dbObject.getInt("stretchX");
        this.stretchY = dbObject.getInt("stretchY");
        this.stretchZ = dbObject.getInt("stretchZ");
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("positionX", this.positionX);
        builder.append("positionY", this.positionY);
        builder.append("positionZ", this.positionZ);
        builder.append("rotationX", this.rotationX);
        builder.append("rotationY", this.rotationY);
        builder.append("rotationZ", this.rotationZ);
        builder.append("stretchX", this.stretchX);
        builder.append("stretchY", this.stretchY);
        builder.append("stretchZ", this.stretchZ);
        return builder.get();
    }

    public int getPositionX() {
        return this.positionX;
    }

    public void setPositionX(final int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public void setPositionY(final int positionY) {
        this.positionY = positionY;
    }

    public int getPositionZ() {
        return this.positionZ;
    }

    public void setPositionZ(final int positionZ) {
        this.positionZ = positionZ;
    }

    public int getRotationX() {
        return this.rotationX;
    }

    public void setRotationX(final int rotationX) {
        this.rotationX = rotationX;
    }

    public int getRotationY() {
        return this.rotationY;
    }

    public void setRotationY(final int rotationY) {
        this.rotationY = rotationY;
    }

    public int getRotationZ() {
        return this.rotationZ;
    }

    public void setRotationZ(final int rotationZ) {
        this.rotationZ = rotationZ;
    }

    public int getStretchX() {
        return this.stretchX;
    }

    public void setStretchX(final int stretchX) {
        this.stretchX = stretchX;
    }

    public int getStretchY() {
        return this.stretchY;
    }

    public void setStretchY(final int stretchY) {
        this.stretchY = stretchY;
    }

    public int getStretchZ() {
        return this.stretchZ;
    }

    public void setStretchZ(final int stretchZ) {
        this.stretchZ = stretchZ;
    }
}
