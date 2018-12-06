package com.bdoemu.gameserver.model.creature.player.appearance.holders;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.creature.player.appearance.holders.common.AppearanceDecalHolder;
import com.bdoemu.gameserver.model.creature.player.appearance.holders.common.AppearanceSizeHolder;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class AppearanceFaceHolder extends JSONable {
    private int objectId;
    private int colorId;
    private int shine;
    private int idleAnimationId;
    private int idleAnimationPower;
    private int wrinklesBrow;
    private int wrinklesEyes;
    private int wrinklesLips;
    private AppearanceDecalHolder bristle;
    private AppearanceDecalHolder beard;
    private AppearanceDecalHolder mustache;
    private AppearanceDecalHolder whiskers;
    private AppearanceDecalHolder eyebrows;
    private AppearanceDecalHolder eyebrowsMakeUp;
    private AppearanceDecalHolder eyeMakeUp;
    private AppearanceDecalHolder rougeMakeUp;
    private AppearanceDecalHolder lipsMakeUp;
    private AppearanceDecalHolder eyelinerMakeUp;
    private AppearanceDecalHolder eyeShadowsMakeUp;
    private AppearanceDecalHolder eyelashesMakeUp;
    private AppearanceDecalHolder leftEyePupil;
    private AppearanceDecalHolder leftEyeIris;
    private AppearanceDecalHolder leftEyeLenses;
    private AppearanceDecalHolder rightEyePupil;
    private AppearanceDecalHolder rightEyeIris;
    private AppearanceDecalHolder rightEyeLenses;
    private AppearanceSizeHolder chinDeformation;
    private AppearanceSizeHolder chin;
    private AppearanceSizeHolder skullLeft;
    private AppearanceSizeHolder skullRight;
    private AppearanceSizeHolder skull2Left;
    private AppearanceSizeHolder skull2Right;
    private AppearanceSizeHolder skull3Left;
    private AppearanceSizeHolder skull3Right;
    private AppearanceSizeHolder earLeft;
    private AppearanceSizeHolder earRight;
    private AppearanceSizeHolder earLobes;
    private AppearanceSizeHolder brow;
    private AppearanceSizeHolder eyebrowLeft;
    private AppearanceSizeHolder eyebrowRight;
    private AppearanceSizeHolder noseBridgeUp;
    private AppearanceSizeHolder eyebrowLeft2;
    private AppearanceSizeHolder eyebrowRight2;
    private AppearanceSizeHolder eyelidDownLeft;
    private AppearanceSizeHolder eyelidDownRight;
    private AppearanceSizeHolder eyeCornerRight;
    private AppearanceSizeHolder eyeCorner2Left;
    private AppearanceSizeHolder eyelidUpLeft;
    private AppearanceSizeHolder eyelidUpRight;
    private AppearanceSizeHolder eyeCorner2Right;
    private AppearanceSizeHolder eyeCornerLeft;
    private AppearanceSizeHolder eyeLeft;
    private AppearanceSizeHolder eyeRight;
    private AppearanceSizeHolder cheekCrownLeft;
    private AppearanceSizeHolder cheekCrownRight;
    private AppearanceSizeHolder noseCheekLeft;
    private AppearanceSizeHolder noseCheekRight;
    private AppearanceSizeHolder nose;
    private AppearanceSizeHolder noseCheek2Left;
    private AppearanceSizeHolder noseCheek2Right;
    private AppearanceSizeHolder noseBridge;
    private AppearanceSizeHolder lipCenterUp;
    private AppearanceSizeHolder lipSideUpLeft;
    private AppearanceSizeHolder lipSideUpRight;
    private AppearanceSizeHolder lipSide2UpLeft;
    private AppearanceSizeHolder lipSide2UpRight;
    private AppearanceSizeHolder lipSideDownLeft;
    private AppearanceSizeHolder lipSideDownRight;
    private AppearanceSizeHolder lipCenterDown;

    public AppearanceFaceHolder() {
        this.bristle = new AppearanceDecalHolder();
        this.beard = new AppearanceDecalHolder();
        this.mustache = new AppearanceDecalHolder();
        this.whiskers = new AppearanceDecalHolder();
        this.eyebrows = new AppearanceDecalHolder();
        this.eyebrowsMakeUp = new AppearanceDecalHolder();
        this.eyeMakeUp = new AppearanceDecalHolder();
        this.rougeMakeUp = new AppearanceDecalHolder();
        this.lipsMakeUp = new AppearanceDecalHolder();
        this.eyelinerMakeUp = new AppearanceDecalHolder();
        this.eyeShadowsMakeUp = new AppearanceDecalHolder();
        this.eyelashesMakeUp = new AppearanceDecalHolder();
        this.leftEyePupil = new AppearanceDecalHolder();
        this.leftEyeIris = new AppearanceDecalHolder();
        this.leftEyeLenses = new AppearanceDecalHolder();
        this.rightEyePupil = new AppearanceDecalHolder();
        this.rightEyeIris = new AppearanceDecalHolder();
        this.rightEyeLenses = new AppearanceDecalHolder();
        this.chinDeformation = new AppearanceSizeHolder();
        this.chin = new AppearanceSizeHolder();
        this.skullLeft = new AppearanceSizeHolder();
        this.skullRight = new AppearanceSizeHolder();
        this.skull2Left = new AppearanceSizeHolder();
        this.skull2Right = new AppearanceSizeHolder();
        this.skull3Left = new AppearanceSizeHolder();
        this.skull3Right = new AppearanceSizeHolder();
        this.earLeft = new AppearanceSizeHolder();
        this.earRight = new AppearanceSizeHolder();
        this.earLobes = new AppearanceSizeHolder();
        this.brow = new AppearanceSizeHolder();
        this.eyebrowLeft = new AppearanceSizeHolder();
        this.eyebrowRight = new AppearanceSizeHolder();
        this.noseBridgeUp = new AppearanceSizeHolder();
        this.eyebrowLeft2 = new AppearanceSizeHolder();
        this.eyebrowRight2 = new AppearanceSizeHolder();
        this.eyelidDownLeft = new AppearanceSizeHolder();
        this.eyelidDownRight = new AppearanceSizeHolder();
        this.eyeCornerRight = new AppearanceSizeHolder();
        this.eyeCorner2Left = new AppearanceSizeHolder();
        this.eyelidUpLeft = new AppearanceSizeHolder();
        this.eyelidUpRight = new AppearanceSizeHolder();
        this.eyeCorner2Right = new AppearanceSizeHolder();
        this.eyeCornerLeft = new AppearanceSizeHolder();
        this.eyeLeft = new AppearanceSizeHolder();
        this.eyeRight = new AppearanceSizeHolder();
        this.cheekCrownLeft = new AppearanceSizeHolder();
        this.cheekCrownRight = new AppearanceSizeHolder();
        this.noseCheekLeft = new AppearanceSizeHolder();
        this.noseCheekRight = new AppearanceSizeHolder();
        this.nose = new AppearanceSizeHolder();
        this.noseCheek2Left = new AppearanceSizeHolder();
        this.noseCheek2Right = new AppearanceSizeHolder();
        this.noseBridge = new AppearanceSizeHolder();
        this.lipCenterUp = new AppearanceSizeHolder();
        this.lipSideUpLeft = new AppearanceSizeHolder();
        this.lipSideUpRight = new AppearanceSizeHolder();
        this.lipSide2UpLeft = new AppearanceSizeHolder();
        this.lipSide2UpRight = new AppearanceSizeHolder();
        this.lipSideDownLeft = new AppearanceSizeHolder();
        this.lipSideDownRight = new AppearanceSizeHolder();
        this.lipCenterDown = new AppearanceSizeHolder();
    }

    public AppearanceFaceHolder(final BasicDBObject dbObject) {
        this.objectId = dbObject.getInt("_id");
        this.colorId = dbObject.getInt("colorId");
        this.shine = dbObject.getInt("shine");
        this.idleAnimationId = dbObject.getInt("idleAnimationId");
        this.idleAnimationPower = dbObject.getInt("idleAnimationPower");
        this.wrinklesBrow = dbObject.getInt("wrinklesBrow");
        this.wrinklesEyes = dbObject.getInt("wrinklesEyes");
        this.wrinklesLips = dbObject.getInt("wrinklesLips");
        this.bristle = new AppearanceDecalHolder((BasicDBObject) dbObject.get("bristle"));
        this.beard = new AppearanceDecalHolder((BasicDBObject) dbObject.get("beard"));
        this.mustache = new AppearanceDecalHolder((BasicDBObject) dbObject.get("mustache"));
        this.whiskers = new AppearanceDecalHolder((BasicDBObject) dbObject.get("whiskers"));
        this.eyebrows = new AppearanceDecalHolder((BasicDBObject) dbObject.get("eyebrows"));
        this.eyebrowsMakeUp = new AppearanceDecalHolder((BasicDBObject) dbObject.get("eyebrowsMakeUp"));
        this.eyeMakeUp = new AppearanceDecalHolder((BasicDBObject) dbObject.get("eyeMakeUp"));
        this.rougeMakeUp = new AppearanceDecalHolder((BasicDBObject) dbObject.get("rougeMakeUp"));
        this.lipsMakeUp = new AppearanceDecalHolder((BasicDBObject) dbObject.get("lipsMakeUp"));
        this.eyelinerMakeUp = new AppearanceDecalHolder((BasicDBObject) dbObject.get("eyelinerMakeUp"));
        this.eyeShadowsMakeUp = new AppearanceDecalHolder((BasicDBObject) dbObject.get("eyeShadowsMakeUp"));
        this.eyelashesMakeUp = new AppearanceDecalHolder((BasicDBObject) dbObject.get("eyelashesMakeUp"));
        this.leftEyePupil = new AppearanceDecalHolder((BasicDBObject) dbObject.get("leftEyePupil"));
        this.leftEyeIris = new AppearanceDecalHolder((BasicDBObject) dbObject.get("leftEyeIris"));
        this.leftEyeLenses = new AppearanceDecalHolder((BasicDBObject) dbObject.get("leftEyeLenses"));
        this.rightEyePupil = new AppearanceDecalHolder((BasicDBObject) dbObject.get("leftEyePupil"));
        this.rightEyeIris = new AppearanceDecalHolder((BasicDBObject) dbObject.get("rightEyeIris"));
        this.rightEyeLenses = new AppearanceDecalHolder((BasicDBObject) dbObject.get("rightEyeLenses"));
        this.chinDeformation = new AppearanceSizeHolder((BasicDBObject) dbObject.get("chinDeformation"));
        this.chin = new AppearanceSizeHolder((BasicDBObject) dbObject.get("chin"));
        this.skullLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("skullLeft"));
        this.skullRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("skullRight"));
        this.skull2Left = new AppearanceSizeHolder((BasicDBObject) dbObject.get("skull2Left"));
        this.skull2Right = new AppearanceSizeHolder((BasicDBObject) dbObject.get("skull2Right"));
        this.skull3Left = new AppearanceSizeHolder((BasicDBObject) dbObject.get("skull3Left"));
        this.skull3Right = new AppearanceSizeHolder((BasicDBObject) dbObject.get("skull3Right"));
        this.earLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("earLeft"));
        this.earRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("earRight"));
        this.earLobes = new AppearanceSizeHolder((BasicDBObject) dbObject.get("earLobes"));
        this.brow = new AppearanceSizeHolder((BasicDBObject) dbObject.get("brow"));
        this.eyebrowLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyebrowLeft"));
        this.eyebrowRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyebrowRight"));
        this.noseBridgeUp = new AppearanceSizeHolder((BasicDBObject) dbObject.get("noseBridgeUp"));
        this.eyebrowLeft2 = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyebrowLeft2"));
        this.eyebrowRight2 = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyebrowRight2"));
        this.eyelidDownLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyelidDownLeft"));
        this.eyelidDownRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyelidDownRight"));
        this.eyeCornerRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyeCornerRight"));
        this.eyeCorner2Left = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyeCorner2Left"));
        this.eyelidUpLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyelidUpLeft"));
        this.eyelidUpRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyelidUpRight"));
        this.eyeCorner2Right = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyeCorner2Right"));
        this.eyeCornerLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyeCornerLeft"));
        this.eyeLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyeLeft"));
        this.eyeRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("eyeRight"));
        this.cheekCrownLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("cheekCrownLeft"));
        this.cheekCrownRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("cheekCrownRight"));
        this.noseCheekLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("noseCheekLeft"));
        this.noseCheekRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("noseCheekRight"));
        this.nose = new AppearanceSizeHolder((BasicDBObject) dbObject.get("nose"));
        this.noseCheek2Left = new AppearanceSizeHolder((BasicDBObject) dbObject.get("noseCheek2Left"));
        this.noseCheek2Right = new AppearanceSizeHolder((BasicDBObject) dbObject.get("noseCheek2Right"));
        this.noseBridge = new AppearanceSizeHolder((BasicDBObject) dbObject.get("noseBridge"));
        this.lipCenterUp = new AppearanceSizeHolder((BasicDBObject) dbObject.get("lipCenterUp"));
        this.lipSideUpLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("lipSideUpLeft"));
        this.lipSideUpRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("lipSideUpRight"));
        this.lipSide2UpLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("lipSide2UpLeft"));
        this.lipSide2UpRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("lipSide2UpRight"));
        this.lipSideDownLeft = new AppearanceSizeHolder((BasicDBObject) dbObject.get("lipSideDownLeft"));
        this.lipSideDownRight = new AppearanceSizeHolder((BasicDBObject) dbObject.get("lipSideDownRight"));
        this.lipCenterDown = new AppearanceSizeHolder((BasicDBObject) dbObject.get("lipCenterDown"));
    }

    public int getId() {
        return this.objectId;
    }

    public void setId(final int objectId) {
        this.objectId = objectId;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("_id", (Object) this.objectId);
        builder.append("colorId", (Object) this.colorId);
        builder.append("shine", (Object) this.shine);
        builder.append("idleAnimationId", (Object) this.idleAnimationId);
        builder.append("idleAnimationPower", (Object) this.idleAnimationPower);
        builder.append("wrinklesBrow", (Object) this.wrinklesBrow);
        builder.append("wrinklesEyes", (Object) this.wrinklesEyes);
        builder.append("wrinklesLips", (Object) this.wrinklesLips);
        builder.append("bristle", (Object) this.bristle.toDBObject());
        builder.append("beard", (Object) this.beard.toDBObject());
        builder.append("mustache", (Object) this.mustache.toDBObject());
        builder.append("whiskers", (Object) this.whiskers.toDBObject());
        builder.append("eyebrows", (Object) this.eyebrows.toDBObject());
        builder.append("eyebrowsMakeUp", (Object) this.eyebrowsMakeUp.toDBObject());
        builder.append("eyeMakeUp", (Object) this.eyeMakeUp.toDBObject());
        builder.append("rougeMakeUp", (Object) this.rougeMakeUp.toDBObject());
        builder.append("eyelinerMakeUp", (Object) this.eyelinerMakeUp.toDBObject());
        builder.append("eyeShadowsMakeUp", (Object) this.eyeShadowsMakeUp.toDBObject());
        builder.append("eyelashesMakeUp", (Object) this.eyelashesMakeUp.toDBObject());
        builder.append("lipsMakeUp", (Object) this.lipsMakeUp.toDBObject());
        builder.append("leftEyePupil", (Object) this.leftEyePupil.toDBObject());
        builder.append("leftEyeIris", (Object) this.leftEyeIris.toDBObject());
        builder.append("leftEyeLenses", (Object) this.leftEyeLenses.toDBObject());
        builder.append("rightEyePupil", (Object) this.rightEyePupil.toDBObject());
        builder.append("rightEyeIris", (Object) this.rightEyeIris.toDBObject());
        builder.append("rightEyeLenses", (Object) this.rightEyeLenses.toDBObject());
        builder.append("chinDeformation", (Object) this.chinDeformation.toDBObject());
        builder.append("chin", (Object) this.chin.toDBObject());
        builder.append("skullLeft", (Object) this.skullLeft.toDBObject());
        builder.append("skullRight", (Object) this.skullRight.toDBObject());
        builder.append("skull2Left", (Object) this.skull2Left.toDBObject());
        builder.append("skull2Right", (Object) this.skull2Right.toDBObject());
        builder.append("skull3Left", (Object) this.skull3Left.toDBObject());
        builder.append("skull3Right", (Object) this.skull3Right.toDBObject());
        builder.append("earLeft", (Object) this.earLeft.toDBObject());
        builder.append("earRight", (Object) this.earRight.toDBObject());
        builder.append("earLobes", (Object) this.earLobes.toDBObject());
        builder.append("brow", (Object) this.brow.toDBObject());
        builder.append("eyebrowLeft", (Object) this.eyebrowLeft.toDBObject());
        builder.append("eyebrowRight", (Object) this.eyebrowRight.toDBObject());
        builder.append("noseBridgeUp", (Object) this.noseBridgeUp.toDBObject());
        builder.append("eyebrowLeft2", (Object) this.eyebrowLeft2.toDBObject());
        builder.append("eyebrowRight2", (Object) this.eyebrowRight2.toDBObject());
        builder.append("eyelidDownLeft", (Object) this.eyelidDownLeft.toDBObject());
        builder.append("eyelidDownRight", (Object) this.eyelidDownRight.toDBObject());
        builder.append("eyeCornerRight", (Object) this.eyeCornerRight.toDBObject());
        builder.append("eyeCorner2Left", (Object) this.eyeCorner2Left.toDBObject());
        builder.append("eyelidUpLeft", (Object) this.eyelidUpLeft.toDBObject());
        builder.append("eyelidUpRight", (Object) this.eyelidUpRight.toDBObject());
        builder.append("eyeCorner2Right", (Object) this.eyeCorner2Right.toDBObject());
        builder.append("eyeCornerLeft", (Object) this.eyeCornerLeft.toDBObject());
        builder.append("eyeLeft", (Object) this.eyeLeft.toDBObject());
        builder.append("eyeRight", (Object) this.eyeRight.toDBObject());
        builder.append("cheekCrownLeft", (Object) this.cheekCrownLeft.toDBObject());
        builder.append("cheekCrownRight", (Object) this.cheekCrownRight.toDBObject());
        builder.append("noseCheekLeft", (Object) this.noseCheekLeft.toDBObject());
        builder.append("noseCheekRight", (Object) this.noseCheekRight.toDBObject());
        builder.append("nose", (Object) this.nose.toDBObject());
        builder.append("noseCheek2Left", (Object) this.noseCheek2Left.toDBObject());
        builder.append("noseCheek2Right", (Object) this.noseCheek2Right.toDBObject());
        builder.append("noseBridge", (Object) this.noseBridge.toDBObject());
        builder.append("lipCenterUp", (Object) this.lipCenterUp.toDBObject());
        builder.append("lipSideUpLeft", (Object) this.lipSideUpLeft.toDBObject());
        builder.append("lipSideUpRight", (Object) this.lipSideUpRight.toDBObject());
        builder.append("lipSide2UpLeft", (Object) this.lipSide2UpLeft.toDBObject());
        builder.append("lipSide2UpRight", (Object) this.lipSide2UpRight.toDBObject());
        builder.append("lipSideDownLeft", (Object) this.lipSideDownLeft.toDBObject());
        builder.append("lipSideDownRight", (Object) this.lipSideDownRight.toDBObject());
        builder.append("lipCenterDown", (Object) this.lipCenterDown.toDBObject());
        return builder.get();
    }

    public int getColorId() {
        return this.colorId;
    }

    public void setColorId(final int colorId) {
        this.colorId = colorId;
    }

    public int getShine() {
        return this.shine;
    }

    public void setShine(final int shine) {
        this.shine = shine;
    }

    public int getIdleAnimationId() {
        return this.idleAnimationId;
    }

    public void setIdleAnimationId(final int idleAnimationId) {
        this.idleAnimationId = idleAnimationId;
    }

    public int getIdleAnimationPower() {
        return this.idleAnimationPower;
    }

    public void setIdleAnimationPower(final int idleAnimationPower) {
        this.idleAnimationPower = idleAnimationPower;
    }

    public int getWrinklesBrow() {
        return this.wrinklesBrow;
    }

    public void setWrinklesBrow(final int wrinklesBrow) {
        this.wrinklesBrow = wrinklesBrow;
    }

    public int getWrinklesEyes() {
        return this.wrinklesEyes;
    }

    public void setWrinklesEyes(final int wrinklesEyes) {
        this.wrinklesEyes = wrinklesEyes;
    }

    public int getWrinklesLips() {
        return this.wrinklesLips;
    }

    public void setWrinklesLips(final int wrinklesLips) {
        this.wrinklesLips = wrinklesLips;
    }

    public AppearanceDecalHolder getBristle() {
        return this.bristle;
    }

    public void setBristle(final AppearanceDecalHolder bristle) {
        this.bristle = bristle;
    }

    public AppearanceDecalHolder getBeard() {
        return this.beard;
    }

    public void setBeard(final AppearanceDecalHolder beard) {
        this.beard = beard;
    }

    public AppearanceDecalHolder getMustache() {
        return this.mustache;
    }

    public void setMustache(final AppearanceDecalHolder mustache) {
        this.mustache = mustache;
    }

    public AppearanceDecalHolder getWhiskers() {
        return this.whiskers;
    }

    public void setWhiskers(final AppearanceDecalHolder whiskers) {
        this.whiskers = whiskers;
    }

    public AppearanceDecalHolder getEyebrows() {
        return this.eyebrows;
    }

    public void setEyebrows(final AppearanceDecalHolder eyebrows) {
        this.eyebrows = eyebrows;
    }

    public AppearanceDecalHolder getEyebrowsMakeUp() {
        return this.eyebrowsMakeUp;
    }

    public void setEyebrowsMakeUp(final AppearanceDecalHolder eyebrowsMakeUp) {
        this.eyebrowsMakeUp = eyebrowsMakeUp;
    }

    public AppearanceDecalHolder getEyeMakeUp() {
        return this.eyeMakeUp;
    }

    public void setEyeMakeUp(final AppearanceDecalHolder eyeMakeUp) {
        this.eyeMakeUp = eyeMakeUp;
    }

    public AppearanceDecalHolder getRougeMakeUp() {
        return this.rougeMakeUp;
    }

    public void setRougeMakeUp(final AppearanceDecalHolder rougeMakeUp) {
        this.rougeMakeUp = rougeMakeUp;
    }

    public AppearanceDecalHolder getEyelinerMakeUp() {
        return this.eyelinerMakeUp;
    }

    public void setEyelinerMakeUp(final AppearanceDecalHolder eyelinerMakeUp) {
        this.eyelinerMakeUp = eyelinerMakeUp;
    }

    public AppearanceDecalHolder getEyeShadowsMakeUp() {
        return this.eyeShadowsMakeUp;
    }

    public void setEyeShadowsMakeUp(final AppearanceDecalHolder eyeShadowsMakeUp) {
        this.eyeShadowsMakeUp = eyeShadowsMakeUp;
    }

    public AppearanceDecalHolder getEyelashesMakeUp() {
        return this.eyelashesMakeUp;
    }

    public void setEyelashesMakeUp(final AppearanceDecalHolder eyelashesMakeUp) {
        this.eyelashesMakeUp = eyelashesMakeUp;
    }

    public AppearanceDecalHolder getLeftEyePupil() {
        return this.leftEyePupil;
    }

    public void setLeftEyePupil(final AppearanceDecalHolder leftEyePupil) {
        this.leftEyePupil = leftEyePupil;
    }

    public AppearanceDecalHolder getLeftEyeIris() {
        return this.leftEyeIris;
    }

    public void setLeftEyeIris(final AppearanceDecalHolder leftEyeIris) {
        this.leftEyeIris = leftEyeIris;
    }

    public AppearanceDecalHolder getLeftEyeLenses() {
        return this.leftEyeLenses;
    }

    public void setLeftEyeLenses(final AppearanceDecalHolder leftEyeLenses) {
        this.leftEyeLenses = leftEyeLenses;
    }

    public AppearanceDecalHolder getRightEyePupil() {
        return this.rightEyePupil;
    }

    public void setRightEyePupil(final AppearanceDecalHolder rightEyePupil) {
        this.rightEyePupil = rightEyePupil;
    }

    public AppearanceDecalHolder getRightEyeIris() {
        return this.rightEyeIris;
    }

    public void setRightEyeIris(final AppearanceDecalHolder rightEyeIris) {
        this.rightEyeIris = rightEyeIris;
    }

    public AppearanceDecalHolder getRightEyeLenses() {
        return this.rightEyeLenses;
    }

    public void setRightEyeLenses(final AppearanceDecalHolder rightEyeLenses) {
        this.rightEyeLenses = rightEyeLenses;
    }

    public AppearanceSizeHolder getChinDeformation() {
        return this.chinDeformation;
    }

    public void setChinDeformation(final AppearanceSizeHolder chinDeformation) {
        this.chinDeformation = chinDeformation;
    }

    public AppearanceSizeHolder getChin() {
        return this.chin;
    }

    public void setChin(final AppearanceSizeHolder chin) {
        this.chin = chin;
    }

    public AppearanceSizeHolder getSkullLeft() {
        return this.skullLeft;
    }

    public void setSkullLeft(final AppearanceSizeHolder skullLeft) {
        this.skullLeft = skullLeft;
    }

    public AppearanceSizeHolder getSkullRight() {
        return this.skullRight;
    }

    public void setSkullRight(final AppearanceSizeHolder skullRight) {
        this.skullRight = skullRight;
    }

    public AppearanceSizeHolder getSkull2Left() {
        return this.skull2Left;
    }

    public void setSkull2Left(final AppearanceSizeHolder skull2Left) {
        this.skull2Left = skull2Left;
    }

    public AppearanceSizeHolder getSkull2Right() {
        return this.skull2Right;
    }

    public void setSkull2Right(final AppearanceSizeHolder skull2Right) {
        this.skull2Right = skull2Right;
    }

    public AppearanceSizeHolder getSkull3Left() {
        return this.skull3Left;
    }

    public void setSkull3Left(final AppearanceSizeHolder skull3Left) {
        this.skull3Left = skull3Left;
    }

    public AppearanceSizeHolder getSkull3Right() {
        return this.skull3Right;
    }

    public void setSkull3Right(final AppearanceSizeHolder skull3Right) {
        this.skull3Right = skull3Right;
    }

    public AppearanceSizeHolder getEarLeft() {
        return this.earLeft;
    }

    public void setEarLeft(final AppearanceSizeHolder earLeft) {
        this.earLeft = earLeft;
    }

    public AppearanceSizeHolder getEarRight() {
        return this.earRight;
    }

    public void setEarRight(final AppearanceSizeHolder earRight) {
        this.earRight = earRight;
    }

    public AppearanceSizeHolder getEarLobes() {
        return this.earLobes;
    }

    public void setEarLobes(final AppearanceSizeHolder earLobes) {
        this.earLobes = earLobes;
    }

    public AppearanceSizeHolder getBrow() {
        return this.brow;
    }

    public void setBrow(final AppearanceSizeHolder brow) {
        this.brow = brow;
    }

    public AppearanceSizeHolder getEyebrowLeft() {
        return this.eyebrowLeft;
    }

    public void setEyebrowLeft(final AppearanceSizeHolder eyebrowLeft) {
        this.eyebrowLeft = eyebrowLeft;
    }

    public AppearanceSizeHolder getEyebrowRight() {
        return this.eyebrowRight;
    }

    public void setEyebrowRight(final AppearanceSizeHolder eyebrowRight) {
        this.eyebrowRight = eyebrowRight;
    }

    public AppearanceSizeHolder getNoseBridgeUp() {
        return this.noseBridgeUp;
    }

    public void setNoseBridgeUp(final AppearanceSizeHolder noseBridgeUp) {
        this.noseBridgeUp = noseBridgeUp;
    }

    public AppearanceSizeHolder getEyebrowLeft2() {
        return this.eyebrowLeft2;
    }

    public void setEyebrowLeft2(final AppearanceSizeHolder eyebrowLeft2) {
        this.eyebrowLeft2 = eyebrowLeft2;
    }

    public AppearanceSizeHolder getEyebrowRight2() {
        return this.eyebrowRight2;
    }

    public void setEyebrowRight2(final AppearanceSizeHolder eyebrowRight2) {
        this.eyebrowRight2 = eyebrowRight2;
    }

    public AppearanceSizeHolder getEyelidDownLeft() {
        return this.eyelidDownLeft;
    }

    public void setEyelidDownLeft(final AppearanceSizeHolder eyelidDownLeft) {
        this.eyelidDownLeft = eyelidDownLeft;
    }

    public AppearanceSizeHolder getEyelidDownRight() {
        return this.eyelidDownRight;
    }

    public void setEyelidDownRight(final AppearanceSizeHolder eyelidDownRight) {
        this.eyelidDownRight = eyelidDownRight;
    }

    public AppearanceSizeHolder getEyeCornerRight() {
        return this.eyeCornerRight;
    }

    public void setEyeCornerRight(final AppearanceSizeHolder eyeCornerRight) {
        this.eyeCornerRight = eyeCornerRight;
    }

    public AppearanceSizeHolder getEyeCorner2Left() {
        return this.eyeCorner2Left;
    }

    public void setEyeCorner2Left(final AppearanceSizeHolder eyeCorner2Left) {
        this.eyeCorner2Left = eyeCorner2Left;
    }

    public AppearanceSizeHolder getEyelidUpLeft() {
        return this.eyelidUpLeft;
    }

    public void setEyelidUpLeft(final AppearanceSizeHolder eyelidUpLeft) {
        this.eyelidUpLeft = eyelidUpLeft;
    }

    public AppearanceSizeHolder getEyelidUpRight() {
        return this.eyelidUpRight;
    }

    public void setEyelidUpRight(final AppearanceSizeHolder eyelidUpRight) {
        this.eyelidUpRight = eyelidUpRight;
    }

    public AppearanceSizeHolder getEyeCorner2Right() {
        return this.eyeCorner2Right;
    }

    public void setEyeCorner2Right(final AppearanceSizeHolder eyeCorner2Right) {
        this.eyeCorner2Right = eyeCorner2Right;
    }

    public AppearanceSizeHolder getEyeCornerLeft() {
        return this.eyeCornerLeft;
    }

    public void setEyeCornerLeft(final AppearanceSizeHolder eyeCornerLeft) {
        this.eyeCornerLeft = eyeCornerLeft;
    }

    public AppearanceSizeHolder getEyeLeft() {
        return this.eyeLeft;
    }

    public void setEyeLeft(final AppearanceSizeHolder eyeLeft) {
        this.eyeLeft = eyeLeft;
    }

    public AppearanceSizeHolder getEyeRight() {
        return this.eyeRight;
    }

    public void setEyeRight(final AppearanceSizeHolder eyeRight) {
        this.eyeRight = eyeRight;
    }

    public AppearanceSizeHolder getCheekCrownLeft() {
        return this.cheekCrownLeft;
    }

    public void setCheekCrownLeft(final AppearanceSizeHolder cheekCrownLeft) {
        this.cheekCrownLeft = cheekCrownLeft;
    }

    public AppearanceSizeHolder getCheekCrownRight() {
        return this.cheekCrownRight;
    }

    public void setCheekCrownRight(final AppearanceSizeHolder cheekCrownRight) {
        this.cheekCrownRight = cheekCrownRight;
    }

    public AppearanceSizeHolder getNoseCheekLeft() {
        return this.noseCheekLeft;
    }

    public void setNoseCheekLeft(final AppearanceSizeHolder noseCheekLeft) {
        this.noseCheekLeft = noseCheekLeft;
    }

    public AppearanceSizeHolder getNoseCheekRight() {
        return this.noseCheekRight;
    }

    public void setNoseCheekRight(final AppearanceSizeHolder noseCheekRight) {
        this.noseCheekRight = noseCheekRight;
    }

    public AppearanceSizeHolder getNose() {
        return this.nose;
    }

    public void setNose(final AppearanceSizeHolder nose) {
        this.nose = nose;
    }

    public AppearanceSizeHolder getNoseCheek2Left() {
        return this.noseCheek2Left;
    }

    public void setNoseCheek2Left(final AppearanceSizeHolder noseCheek2Left) {
        this.noseCheek2Left = noseCheek2Left;
    }

    public AppearanceSizeHolder getNoseCheek2Right() {
        return this.noseCheek2Right;
    }

    public void setNoseCheek2Right(final AppearanceSizeHolder noseCheek2Right) {
        this.noseCheek2Right = noseCheek2Right;
    }

    public AppearanceSizeHolder getNoseBridge() {
        return this.noseBridge;
    }

    public void setNoseBridge(final AppearanceSizeHolder noseBridge) {
        this.noseBridge = noseBridge;
    }

    public AppearanceSizeHolder getLipCenterUp() {
        return this.lipCenterUp;
    }

    public void setLipCenterUp(final AppearanceSizeHolder lipCenterUp) {
        this.lipCenterUp = lipCenterUp;
    }

    public AppearanceSizeHolder getLipSideUpLeft() {
        return this.lipSideUpLeft;
    }

    public void setLipSideUpLeft(final AppearanceSizeHolder lipSideUpLeft) {
        this.lipSideUpLeft = lipSideUpLeft;
    }

    public AppearanceSizeHolder getLipSideUpRight() {
        return this.lipSideUpRight;
    }

    public void setLipSideUpRight(final AppearanceSizeHolder lipSideUpRight) {
        this.lipSideUpRight = lipSideUpRight;
    }

    public AppearanceSizeHolder getLipSide2UpLeft() {
        return this.lipSide2UpLeft;
    }

    public void setLipSide2UpLeft(final AppearanceSizeHolder lipSide2UpLeft) {
        this.lipSide2UpLeft = lipSide2UpLeft;
    }

    public AppearanceSizeHolder getLipSide2UpRight() {
        return this.lipSide2UpRight;
    }

    public void setLipSide2UpRight(final AppearanceSizeHolder lipSide2UpRight) {
        this.lipSide2UpRight = lipSide2UpRight;
    }

    public AppearanceSizeHolder getLipSideDownLeft() {
        return this.lipSideDownLeft;
    }

    public void setLipSideDownLeft(final AppearanceSizeHolder lipSideDownLeft) {
        this.lipSideDownLeft = lipSideDownLeft;
    }

    public AppearanceSizeHolder getLipSideDownRight() {
        return this.lipSideDownRight;
    }

    public void setLipSideDownRight(final AppearanceSizeHolder lipSideDownRight) {
        this.lipSideDownRight = lipSideDownRight;
    }

    public AppearanceSizeHolder getLipCenterDown() {
        return this.lipCenterDown;
    }

    public void setLipCenterDown(final AppearanceSizeHolder lipCenterDown) {
        this.lipCenterDown = lipCenterDown;
    }

    public AppearanceDecalHolder getLipsMakeUp() {
        return this.lipsMakeUp;
    }

    public void setLipsMakeUp(final AppearanceDecalHolder lipsMakeUp) {
        this.lipsMakeUp = lipsMakeUp;
    }
}
