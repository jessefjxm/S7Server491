package com.bdoemu.gameserver.model.creature.player.appearance.holders;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.creature.player.appearance.holders.common.AppearanceSizeHolder;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class AppearanceBodyHolder extends JSONable {
    private int height;
    private int chestMuscles;
    private int armsMuscles;
    private int legsMuscles;
    private AppearanceSizeHolder calvesLeft;
    private AppearanceSizeHolder calvesRight;
    private AppearanceSizeHolder thighLeft;
    private AppearanceSizeHolder thighRight;
    private AppearanceSizeHolder footLeft;
    private AppearanceSizeHolder footRight;
    private AppearanceSizeHolder neck;
    private AppearanceSizeHolder groin;
    private AppearanceSizeHolder stomach;
    private AppearanceSizeHolder chest;
    private AppearanceSizeHolder breast;
    private AppearanceSizeHolder buttockLeft;
    private AppearanceSizeHolder buttockRight;
    private AppearanceSizeHolder shouldersLeft;
    private AppearanceSizeHolder shouldersRight;
    private AppearanceSizeHolder forearmLeft;
    private AppearanceSizeHolder forearmRight;
    private AppearanceSizeHolder armLeft;
    private AppearanceSizeHolder armRight;
    private AppearanceSizeHolder handLeft;
    private AppearanceSizeHolder handRight;
    private byte[] additionalSizeData;

    public AppearanceBodyHolder() {
        this.calvesLeft = new AppearanceSizeHolder();
        this.calvesRight = new AppearanceSizeHolder();
        this.thighLeft = new AppearanceSizeHolder();
        this.thighRight = new AppearanceSizeHolder();
        this.footLeft = new AppearanceSizeHolder();
        this.footRight = new AppearanceSizeHolder();
        this.neck = new AppearanceSizeHolder();
        this.groin = new AppearanceSizeHolder();
        this.stomach = new AppearanceSizeHolder();
        this.chest = new AppearanceSizeHolder();
        this.breast = new AppearanceSizeHolder();
        this.buttockLeft = new AppearanceSizeHolder();
        this.buttockRight = new AppearanceSizeHolder();
        this.shouldersLeft = new AppearanceSizeHolder();
        this.shouldersRight = new AppearanceSizeHolder();
        this.forearmLeft = new AppearanceSizeHolder();
        this.forearmRight = new AppearanceSizeHolder();
        this.armLeft = new AppearanceSizeHolder();
        this.armRight = new AppearanceSizeHolder();
        this.handLeft = new AppearanceSizeHolder();
        this.handRight = new AppearanceSizeHolder();
    }

    public AppearanceBodyHolder(final BasicDBObject dbObject) {
        this.height = dbObject.getInt("height");
        this.chestMuscles = dbObject.getInt("chestMuscles");
        this.armsMuscles = dbObject.getInt("armsMuscles");
        this.legsMuscles = dbObject.getInt("legsMuscles");
        this.calvesLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("calvesLeft"));
        this.calvesRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("calvesRight"));
        this.thighLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("thighLeft"));
        this.thighRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("thighRight"));
        this.footLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("footLeft"));
        this.footRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("footRight"));
        this.neck = new AppearanceSizeHolder((BasicDBObject) dbObject.get("neck"));
        this.groin = new AppearanceSizeHolder((BasicDBObject) dbObject.get("groin"));
        this.stomach = new AppearanceSizeHolder((BasicDBObject) dbObject.get("stomach"));
        this.chest = new AppearanceSizeHolder((BasicDBObject) dbObject.get("chest"));
        this.breast = new AppearanceSizeHolder((BasicDBObject) dbObject.get("breast"));
        this.buttockLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("buttockLeft"));
        this.buttockRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("buttockRight"));
        this.shouldersLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("shouldersLeft"));
        this.shouldersRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("shouldersRight"));
        this.forearmLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("forearmLeft"));
        this.forearmRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("forearmRight"));
        this.armLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("armLeft"));
        this.armRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("armRight"));
        this.handLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("handLeft"));
        this.handRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("handRight"));
        this.additionalSizeData = (byte[]) dbObject.get("additionalSizeData");
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("height", this.height);
        builder.append("chestMuscles", this.chestMuscles);
        builder.append("armsMuscles", this.armsMuscles);
        builder.append("legsMuscles", this.legsMuscles);
        builder.append("calvesLeft", this.calvesLeft.toDBObject());
        builder.append("calvesRight", this.calvesRight.toDBObject());
        builder.append("thighLeft", this.thighLeft.toDBObject());
        builder.append("thighRight", this.thighRight.toDBObject());
        builder.append("footLeft", this.footLeft.toDBObject());
        builder.append("footRight", this.footRight.toDBObject());
        builder.append("neck", this.neck.toDBObject());
        builder.append("groin", this.groin.toDBObject());
        builder.append("stomach", this.stomach.toDBObject());
        builder.append("chest", this.chest.toDBObject());
        builder.append("breast", this.breast.toDBObject());
        builder.append("buttockLeft", this.buttockLeft.toDBObject());
        builder.append("buttockRight", this.buttockRight.toDBObject());
        builder.append("shouldersLeft", this.shouldersLeft.toDBObject());
        builder.append("shouldersRight", this.shouldersRight.toDBObject());
        builder.append("forearmLeft", this.forearmLeft.toDBObject());
        builder.append("forearmRight", this.forearmRight.toDBObject());
        builder.append("armLeft", this.armLeft.toDBObject());
        builder.append("armRight", this.armRight.toDBObject());
        builder.append("handLeft", this.handLeft.toDBObject());
        builder.append("handRight", this.handRight.toDBObject());
        builder.append("additionalSizeData", this.additionalSizeData);
        return builder.get();
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public int getChestMuscles() {
        return this.chestMuscles;
    }

    public void setChestMuscles(final int chestMuscles) {
        this.chestMuscles = chestMuscles;
    }

    public int getArmsMuscles() {
        return this.armsMuscles;
    }

    public void setArmsMuscles(final int armsMuscles) {
        this.armsMuscles = armsMuscles;
    }

    public int getLegsMuscles() {
        return this.legsMuscles;
    }

    public void setLegsMuscles(final int legsMuscles) {
        this.legsMuscles = legsMuscles;
    }

    public AppearanceSizeHolder getCalvesLeft() {
        return this.calvesLeft;
    }

    public void setCalvesLeft(final AppearanceSizeHolder calvesLeft) {
        this.calvesLeft = calvesLeft;
    }

    public AppearanceSizeHolder getCalvesRight() {
        return this.calvesRight;
    }

    public void setCalvesRight(final AppearanceSizeHolder calvesRight) {
        this.calvesRight = calvesRight;
    }

    public AppearanceSizeHolder getThighLeft() {
        return this.thighLeft;
    }

    public void setThighLeft(final AppearanceSizeHolder thighLeft) {
        this.thighLeft = thighLeft;
    }

    public AppearanceSizeHolder getThighRight() {
        return this.thighRight;
    }

    public void setThighRight(final AppearanceSizeHolder thighRight) {
        this.thighRight = thighRight;
    }

    public AppearanceSizeHolder getFootLeft() {
        return this.footLeft;
    }

    public void setFootLeft(final AppearanceSizeHolder footLeft) {
        this.footLeft = footLeft;
    }

    public AppearanceSizeHolder getFootRight() {
        return this.footRight;
    }

    public void setFootRight(final AppearanceSizeHolder footRight) {
        this.footRight = footRight;
    }

    public AppearanceSizeHolder getNeck() {
        return this.neck;
    }

    public void setNeck(final AppearanceSizeHolder neck) {
        this.neck = neck;
    }

    public AppearanceSizeHolder getGroin() {
        return this.groin;
    }

    public void setGroin(final AppearanceSizeHolder groin) {
        this.groin = groin;
    }

    public AppearanceSizeHolder getStomach() {
        return this.stomach;
    }

    public void setStomach(final AppearanceSizeHolder stomach) {
        this.stomach = stomach;
    }

    public AppearanceSizeHolder getChest() {
        return this.chest;
    }

    public void setChest(final AppearanceSizeHolder chest) {
        this.chest = chest;
    }

    public AppearanceSizeHolder getBreast() {
        return this.breast;
    }

    public void setBreast(final AppearanceSizeHolder breast) {
        this.breast = breast;
    }

    public AppearanceSizeHolder getButtockLeft() {
        return this.buttockLeft;
    }

    public void setButtockLeft(final AppearanceSizeHolder buttockLeft) {
        this.buttockLeft = buttockLeft;
    }

    public AppearanceSizeHolder getButtockRight() {
        return this.buttockRight;
    }

    public void setButtockRight(final AppearanceSizeHolder buttockRight) {
        this.buttockRight = buttockRight;
    }

    public AppearanceSizeHolder getShouldersLeft() {
        return this.shouldersLeft;
    }

    public void setShouldersLeft(final AppearanceSizeHolder shouldersLeft) {
        this.shouldersLeft = shouldersLeft;
    }

    public AppearanceSizeHolder getShouldersRight() {
        return this.shouldersRight;
    }

    public void setShouldersRight(final AppearanceSizeHolder shouldersRight) {
        this.shouldersRight = shouldersRight;
    }

    public AppearanceSizeHolder getForearmLeft() {
        return this.forearmLeft;
    }

    public void setForearmLeft(final AppearanceSizeHolder forearmLeft) {
        this.forearmLeft = forearmLeft;
    }

    public AppearanceSizeHolder getForearmRight() {
        return this.forearmRight;
    }

    public void setForearmRight(final AppearanceSizeHolder forearmRight) {
        this.forearmRight = forearmRight;
    }

    public AppearanceSizeHolder getArmLeft() {
        return this.armLeft;
    }

    public void setArmLeft(final AppearanceSizeHolder armLeft) {
        this.armLeft = armLeft;
    }

    public AppearanceSizeHolder getArmRight() {
        return this.armRight;
    }

    public void setArmRight(final AppearanceSizeHolder armRight) {
        this.armRight = armRight;
    }

    public AppearanceSizeHolder getHandLeft() {
        return this.handLeft;
    }

    public void setHandLeft(final AppearanceSizeHolder handLeft) {
        this.handLeft = handLeft;
    }

    public AppearanceSizeHolder getHandRight() {
        return this.handRight;
    }

    public void setHandRight(final AppearanceSizeHolder handRight) {
        this.handRight = handRight;
    }

    public byte[] getAdditionalSizeData() {
        return this.additionalSizeData;
    }

    public void setAdditionalSizeData(final byte[] additionalSizeData) {
        this.additionalSizeData = additionalSizeData;
    }
}
