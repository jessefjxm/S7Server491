package com.bdoemu.gameserver.model.creature.player.challenge;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.ChallengeData;
import com.bdoemu.gameserver.model.creature.player.challenge.templates.ChallengeT;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class ChallengeReward extends JSONable {
    private final ChallengeT template;
    private int rewardCount;

    public ChallengeReward(final ChallengeT template, final BasicDBObject dbObject) {
        this.template = template;
        this.rewardCount = dbObject.getInt("rewardCount");
    }

    public ChallengeReward(final ChallengeT template) {
        this.template = template;
    }

    public static ChallengeReward newChallengeReward(final int challengeId) {
        final ChallengeT template = ChallengeData.getInstance().getTemplate(challengeId);
        return (template == null) ? null : new ChallengeReward(template);
    }

    public ChallengeT getTemplate() {
        return this.template;
    }

    public int getRewardCount() {
        return this.rewardCount;
    }

    public void setRewardCount(final int rewardCount) {
        this.rewardCount = rewardCount;
    }

    public int getChallengeId() {
        return this.template.getChallengeId();
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("challengeId", (Object) this.getChallengeId());
        builder.append("rewardCount", (Object) this.getRewardCount());
        return builder.get();
    }
}
