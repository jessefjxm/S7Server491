package com.bdoemu.gameserver.model.creature.player.appearance.holders;

import com.bdoemu.commons.database.mongo.JSONable;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class AppearanceTattooHolder extends JSONable {
    private int faceTattooId;
    private int faceTattooX;
    private int faceTattooY;
    private int faceTattooRotation;
    private int faceTattooWidth;
    private int faceTattooHeight;
    private int faceTattooDensity;
    private int faceTattooShine;
    private int faceTattooColorId;
    private int bodyTattooId;
    private int bodyTattooX;
    private int bodyTattooY;
    private int bodyTattooRotation;
    private int bodyTattooWidth;
    private int bodyTattooHeight;
    private int bodyTattooDensity;
    private int bodyTattooShine;
    private int bodyTattooColorId;

    public AppearanceTattooHolder() {
    }

    public AppearanceTattooHolder(final BasicDBObject dbObject) {
        this.faceTattooId = dbObject.getInt("faceTattooId");
        this.faceTattooX = dbObject.getInt("faceTattooX");
        this.faceTattooY = dbObject.getInt("faceTattooY");
        this.faceTattooRotation = dbObject.getInt("faceTattooRotation");
        this.faceTattooWidth = dbObject.getInt("faceTattooWidth");
        this.faceTattooHeight = dbObject.getInt("faceTattooHeight");
        this.faceTattooDensity = dbObject.getInt("faceTattooDensity");
        this.faceTattooShine = dbObject.getInt("faceTattooShine");
        this.faceTattooColorId = dbObject.getInt("faceTattooColorId");
        this.bodyTattooId = dbObject.getInt("bodyTattooId");
        this.bodyTattooX = dbObject.getInt("bodyTattooX");
        this.bodyTattooY = dbObject.getInt("bodyTattooY");
        this.bodyTattooRotation = dbObject.getInt("bodyTattooRotation");
        this.bodyTattooWidth = dbObject.getInt("bodyTattooWidth");
        this.bodyTattooHeight = dbObject.getInt("bodyTattooHeight");
        this.bodyTattooDensity = dbObject.getInt("bodyTattooDensity");
        this.bodyTattooShine = dbObject.getInt("bodyTattooShine");
        this.bodyTattooColorId = dbObject.getInt("bodyTattooColorId");
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("faceTattooId", this.faceTattooId);
        builder.append("faceTattooX", this.faceTattooX);
        builder.append("faceTattooY", this.faceTattooY);
        builder.append("faceTattooRotation", this.faceTattooRotation);
        builder.append("faceTattooWidth", this.faceTattooWidth);
        builder.append("faceTattooHeight", this.faceTattooHeight);
        builder.append("faceTattooDensity", this.faceTattooDensity);
        builder.append("faceTattooShine",  this.faceTattooShine);
        builder.append("faceTattooColorId", this.faceTattooColorId);
        builder.append("bodyTattooId", this.bodyTattooId);
        builder.append("bodyTattooX", this.bodyTattooX);
        builder.append("bodyTattooY", this.bodyTattooY);
        builder.append("bodyTattooRotation", this.bodyTattooRotation);
        builder.append("bodyTattooWidth", this.bodyTattooWidth);
        builder.append("bodyTattooHeight", this.bodyTattooHeight);
        builder.append("bodyTattooDensity", this.bodyTattooDensity);
        builder.append("bodyTattooShine", this.bodyTattooShine);
        builder.append("bodyTattooColorId", this.bodyTattooColorId);
        return builder.get();
    }

    public int getFaceTattooId() {
        return this.faceTattooId;
    }

    public void setFaceTattooId(final int faceTattooId) {
        this.faceTattooId = faceTattooId;
    }

    public int getFaceTattooX() {
        return this.faceTattooX;
    }

    public void setFaceTattooX(final int faceTattooX) {
        this.faceTattooX = faceTattooX;
    }

    public int getFaceTattooY() {
        return this.faceTattooY;
    }

    public void setFaceTattooY(final int faceTattooY) {
        this.faceTattooY = faceTattooY;
    }

    public int getFaceTattooRotation() {
        return this.faceTattooRotation;
    }

    public void setFaceTattooRotation(final int faceTattooRotation) {
        this.faceTattooRotation = faceTattooRotation;
    }

    public int getFaceTattooWidth() {
        return this.faceTattooWidth;
    }

    public void setFaceTattooWidth(final int faceTattooWidth) {
        this.faceTattooWidth = faceTattooWidth;
    }

    public int getFaceTattooHeight() {
        return this.faceTattooHeight;
    }

    public void setFaceTattooHeight(final int faceTattooHeight) {
        this.faceTattooHeight = faceTattooHeight;
    }

    public int getFaceTattooDensity() {
        return this.faceTattooDensity;
    }

    public void setFaceTattooDensity(final int faceTattooDensity) {
        this.faceTattooDensity = faceTattooDensity;
    }

    public int getFaceTattooShine() {
        return this.faceTattooShine;
    }

    public void setFaceTattooShine(final int faceTattooShine) {
        this.faceTattooShine = faceTattooShine;
    }

    public int getFaceTattooColorId() {
        return this.faceTattooColorId;
    }

    public void setFaceTattooColorId(final int faceTattooColorId) {
        this.faceTattooColorId = faceTattooColorId;
    }

    public int getBodyTattooId() {
        return this.bodyTattooId;
    }

    public void setBodyTattooId(final int bodyTattooId) {
        this.bodyTattooId = bodyTattooId;
    }

    public int getBodyTattooX() {
        return this.bodyTattooX;
    }

    public void setBodyTattooX(final int bodyTattooX) {
        this.bodyTattooX = bodyTattooX;
    }

    public int getBodyTattooY() {
        return this.bodyTattooY;
    }

    public void setBodyTattooY(final int bodyTattooY) {
        this.bodyTattooY = bodyTattooY;
    }

    public int getBodyTattooRotation() {
        return this.bodyTattooRotation;
    }

    public void setBodyTattooRotation(final int bodyTattooRotation) {
        this.bodyTattooRotation = bodyTattooRotation;
    }

    public int getBodyTattooWidth() {
        return this.bodyTattooWidth;
    }

    public void setBodyTattooWidth(final int bodyTattooWidth) {
        this.bodyTattooWidth = bodyTattooWidth;
    }

    public int getBodyTattooHeight() {
        return this.bodyTattooHeight;
    }

    public void setBodyTattooHeight(final int bodyTattooHeight) {
        this.bodyTattooHeight = bodyTattooHeight;
    }

    public int getBodyTattooDensity() {
        return this.bodyTattooDensity;
    }

    public void setBodyTattooDensity(final int bodyTattooDensity) {
        this.bodyTattooDensity = bodyTattooDensity;
    }

    public int getBodyTattooShine() {
        return this.bodyTattooShine;
    }

    public void setBodyTattooShine(final int bodyTattooShine) {
        this.bodyTattooShine = bodyTattooShine;
    }

    public int getBodyTattooColorId() {
        return this.bodyTattooColorId;
    }

    public void setBodyTattooColorId(final int bodyTattooColorId) {
        this.bodyTattooColorId = bodyTattooColorId;
    }
}
