package com.bdoemu.gameserver.model.creature.player.appearance;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.creature.player.appearance.holders.AppearanceBodyHolder;
import com.bdoemu.gameserver.model.creature.player.appearance.holders.AppearanceFaceHolder;
import com.bdoemu.gameserver.model.creature.player.appearance.holders.AppearanceHairHolder;
import com.bdoemu.gameserver.model.creature.player.appearance.holders.AppearanceTattooHolder;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class PlayerAppearanceStorage extends JSONable {
    private int voicePitch;
    private AppearanceHairHolder hairs;
    private AppearanceTattooHolder tattoo;
    private AppearanceBodyHolder body;
    private AppearanceFaceHolder face;

    public PlayerAppearanceStorage() {
        this.voicePitch = 50;
        this.hairs = new AppearanceHairHolder();
        this.tattoo = new AppearanceTattooHolder();
        this.body = new AppearanceBodyHolder();
        this.face = new AppearanceFaceHolder();
    }

    public PlayerAppearanceStorage(final BasicDBObject dbObject) {
        this.voicePitch = dbObject.getInt("voicePitch");
        this.hairs = new AppearanceHairHolder((BasicDBObject) dbObject.get("hairs"));
        this.tattoo = new AppearanceTattooHolder((BasicDBObject) dbObject.get("tattoo"));
        this.body = new AppearanceBodyHolder((BasicDBObject) dbObject.get("body"));
        this.face = new AppearanceFaceHolder((BasicDBObject) dbObject.get("face"));
    }

    public int getVoicePitch() {
        return this.voicePitch;
    }

    public void setVoicePitch(final int voicePitch) {
        this.voicePitch = voicePitch;
    }

    public AppearanceBodyHolder getBody() {
        return this.body;
    }

    public AppearanceFaceHolder getFace() {
        return this.face;
    }

    public AppearanceHairHolder getHairs() {
        return this.hairs;
    }

    public AppearanceTattooHolder getTattoo() {
        return this.tattoo;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("voicePitch", this.voicePitch);
        builder.append("hairs", this.hairs.toDBObject());
        builder.append("tattoo", this.tattoo.toDBObject());
        builder.append("body", this.body.toDBObject());
        builder.append("face", this.face.toDBObject());
        return builder.get();
    }
}
