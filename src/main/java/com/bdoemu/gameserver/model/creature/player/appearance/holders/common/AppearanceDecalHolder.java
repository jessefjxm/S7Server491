package com.bdoemu.gameserver.model.creature.player.appearance.holders.common;

import com.bdoemu.commons.database.mongo.JSONable;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class AppearanceDecalHolder extends JSONable {
    protected int id;
    protected int length;
    protected int curls;
    protected int swirls;
    protected int colorId;
    protected int density;
    protected int shine;
    protected int area;
    protected int highlight;
    protected int height;
    protected int width;
    protected int size;
    protected int purity;

    public AppearanceDecalHolder() {
    }

    public AppearanceDecalHolder(final BasicDBObject dbObject) {
        this.id = dbObject.getInt("_id");
        this.length = dbObject.getInt("length");
        this.curls = dbObject.getInt("curls");
        this.swirls = dbObject.getInt("swirls");
        this.colorId = dbObject.getInt("colorId");
        this.density = dbObject.getInt("density");
        this.shine = dbObject.getInt("shine");
        this.area = dbObject.getInt("area");
        this.highlight = dbObject.getInt("highlight");
        this.height = dbObject.getInt("height");
        this.width = dbObject.getInt("width");
        this.size = dbObject.getInt("size");
        this.purity = dbObject.getInt("purity");
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("_id", this.id);
        builder.append("length", this.length);
        builder.append("curls", this.curls);
        builder.append("swirls", this.swirls);
        builder.append("colorId", this.colorId);
        builder.append("density", this.density);
        builder.append("shine", this.shine);
        builder.append("area", this.area);
        builder.append("highlight", this.highlight);
        builder.append("height", this.height);
        builder.append("width", this.width);
        builder.append("size", this.size);
        builder.append("purity", this.purity);
        return builder.get();
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public int getCurls() {
        return this.curls;
    }

    public void setCurls(final int curls) {
        this.curls = curls;
    }

    public int getSwirls() {
        return this.swirls;
    }

    public void setSwirls(final int swirls) {
        this.swirls = swirls;
    }

    public int getColorId() {
        return this.colorId;
    }

    public void setColorId(final int colorId) {
        this.colorId = colorId;
    }

    public int getDensity() {
        return this.density;
    }

    public void setDensity(final int density) {
        this.density = density;
    }

    public int getShine() {
        return this.shine;
    }

    public void setShine(final int shine) {
        this.shine = shine;
    }

    public int getArea() {
        return this.area;
    }

    public void setArea(final int area) {
        this.area = area;
    }

    public int getHighlight() {
        return this.highlight;
    }

    public void setHighlight(final int highlight) {
        this.highlight = highlight;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public int getPurity() {
        return this.purity;
    }

    public void setPurity(final int purity) {
        this.purity = purity;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }
}
