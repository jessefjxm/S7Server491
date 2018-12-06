package com.bdoemu.gameserver.model.creature.player.appearance.holders;

import com.bdoemu.gameserver.model.creature.player.appearance.holders.common.AppearanceDecalHolder;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class AppearanceHairHolder extends AppearanceDecalHolder {
    private int backLength;
    private int sideLength;
    private int tipsSaturation;
    private int tipsLength;
    private int tipsColorId;
    private int rootsSaturation;
    private int rootsLength;
    private int rootsColorId;
    private byte[] additionalSizeData;

    public AppearanceHairHolder() {
    }

    public AppearanceHairHolder(final BasicDBObject dbObject) {
        super(dbObject);
        this.backLength = dbObject.getInt("backLength");
        this.sideLength = dbObject.getInt("sideLength");
        this.tipsSaturation = dbObject.getInt("tipsSaturation");
        this.tipsLength = dbObject.getInt("tipsLength");
        this.tipsColorId = dbObject.getInt("tipsColorId");
        this.rootsSaturation = dbObject.getInt("rootsSaturation");
        this.rootsLength = dbObject.getInt("rootsLength");
        this.rootsColorId = dbObject.getInt("rootsColorId");
        this.additionalSizeData = (byte[]) dbObject.get("additionalSizeData");
    }

    @Override
    public DBObject toDBObject() {
        final DBObject superObject = super.toDBObject();
        superObject.put("backLength", this.backLength);
        superObject.put("sideLength", this.sideLength);
        superObject.put("tipsSaturation", this.tipsSaturation);
        superObject.put("tipsLength", this.tipsLength);
        superObject.put("tipsColorId", this.tipsColorId);
        superObject.put("rootsSaturation", this.rootsSaturation);
        superObject.put("rootsLength", this.rootsLength);
        superObject.put("rootsColorId", this.rootsColorId);
        superObject.put("additionalSizeData", this.additionalSizeData);
        return superObject;
    }

    public int getBackLength() {
        return this.backLength;
    }

    public void setBackLength(final int backLength) {
        this.backLength = backLength;
    }

    public int getSideLength() {
        return this.sideLength;
    }

    public void setSideLength(final int sideLength) {
        this.sideLength = sideLength;
    }

    public int getTipsSaturation() {
        return this.tipsSaturation;
    }

    public void setTipsSaturation(final int tipsSaturation) {
        this.tipsSaturation = tipsSaturation;
    }

    public int getTipsLength() {
        return this.tipsLength;
    }

    public void setTipsLength(final int tipsLength) {
        this.tipsLength = tipsLength;
    }

    public int getTipsColorId() {
        return this.tipsColorId;
    }

    public void setTipsColorId(final int tipsColorId) {
        this.tipsColorId = tipsColorId;
    }

    public int getRootsSaturation() {
        return this.rootsSaturation;
    }

    public void setRootsSaturation(final int rootsSaturation) {
        this.rootsSaturation = rootsSaturation;
    }

    public int getRootsLength() {
        return this.rootsLength;
    }

    public void setRootsLength(final int rootsLength) {
        this.rootsLength = rootsLength;
    }

    public int getRootsColorId() {
        return this.rootsColorId;
    }

    public void setRootsColorId(final int rootsColorId) {
        this.rootsColorId = rootsColorId;
    }

    public byte[] getAdditionalSizeData() {
        return this.additionalSizeData;
    }

    public void setAdditionalSizeData(final byte[] additionalSizeData) {
        this.additionalSizeData = additionalSizeData;
    }
}
