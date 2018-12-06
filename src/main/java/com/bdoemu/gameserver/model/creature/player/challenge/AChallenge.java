package com.bdoemu.gameserver.model.creature.player.challenge;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.challenge.enums.EChallengeState;
import com.bdoemu.gameserver.model.creature.player.challenge.templates.ChallengeT;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public abstract class AChallenge extends JSONable {
    protected ChallengeT template;
    protected EChallengeState state;
    protected ChallengeHandler handler;
    protected long lastRewardedDate;
    protected int completeCount;
    protected Player player;

    public AChallenge() {
        this.state = EChallengeState.None;
    }

    protected abstract void init();

    public long getCompleteDate() {
        return this.lastRewardedDate;
    }

    public int getCompleteCount() {
        return this.completeCount;
    }

    public EChallengeState getState() {
        return this.state;
    }

    public void init(final Player player, final ChallengeHandler handler, final ChallengeT template) {
        this.template = template;
        this.player = player;
        this.handler = handler;
        this.init();
    }

    public void init(final Player player, final ChallengeHandler handler, final ChallengeT template, final BasicDBObject object) {
        this.template = template;
        this.lastRewardedDate = object.getLong("lastRewardedDate");
        this.completeCount = object.getInt("completeCount");
        this.player = player;
        this.handler = handler;
        this.init();
    }

    public int getChallengeId() {
        return this.template.getChallengeId();
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("challengeId", this.getChallengeId());
        builder.append("lastRewardedDate", this.lastRewardedDate);
        builder.append("completeCount", this.completeCount);
        return builder.get();
    }
}
