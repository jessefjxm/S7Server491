package com.bdoemu.gameserver.model.creature.player.challenge;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMGetChallengeReward;
import com.bdoemu.core.network.sendable.SMListChallengeReward;
import com.bdoemu.core.network.sendable.SMListCompleteChallenge;
import com.bdoemu.core.network.sendable.SMListProgressChallenge;
import com.bdoemu.gameserver.dataholders.ChallengeData;
import com.bdoemu.gameserver.model.creature.player.AccountData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.challenge.templates.ChallengeT;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AddItemEvent;
import com.bdoemu.gameserver.model.creature.player.rewards.templates.RewardTemplate;
import com.bdoemu.gameserver.model.items.Item;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ChallengeHandler extends JSONable {
    private final ConcurrentHashMap<Integer, AChallenge> challenges;
    private final ConcurrentHashMap<Integer, ChallengeReward> rewards;
    private final Player player;
    private final AccountData accountData;

    public ChallengeHandler(final Player player, final AccountData accountData) {
        this.challenges = new ConcurrentHashMap<>();
        this.rewards = new ConcurrentHashMap<>();
        this.player = player;
        this.accountData = accountData;
    }

    public ChallengeHandler(final Player player, final AccountData accountData, final BasicDBObject dbObject) {
        this.challenges = new ConcurrentHashMap<>();
        this.rewards = new ConcurrentHashMap<>();
        this.player = player;
        this.accountData = accountData;
        final BasicDBList challengeRewardtDB = (BasicDBList) dbObject.get("challengeReward");
        for (final Object aChallengeRewardtDB : challengeRewardtDB) {
            final BasicDBObject challengeDB = (BasicDBObject) aChallengeRewardtDB;
            final int challengeId = challengeDB.getInt("challengeId");
            final ChallengeT template = ChallengeData.getInstance().getTemplate(challengeId);
            if (template != null) {
                this.rewards.put(challengeId, new ChallengeReward(template, challengeDB));
            }
        }
        final BasicDBList challengeListDB = (BasicDBList) dbObject.get("challenges");
        for (final Object aChallengeListDB : challengeListDB) {
            final BasicDBObject challengeDB2 = (BasicDBObject) aChallengeListDB;
            final int challengeId2 = challengeDB2.getInt("challengeId");
            final ChallengeT template2 = ChallengeData.getInstance().getTemplate(challengeId2);
            if (template2 != null) {
                final AChallenge pc = template2.getCompleteType().newChallengeInstance();
                if (pc == null) {
                    continue;
                }
                pc.init(player, this, template2, challengeDB2);
                this.challenges.put(pc.getChallengeId(), pc);
            }
        }
        for (final ChallengeT template3 : ChallengeData.getInstance().getTemplates()) {
            if (!this.challenges.containsKey(template3.getChallengeId())) {
                final AChallenge pc2 = template3.getCompleteType().newChallengeInstance();
                if (pc2 == null) {
                    continue;
                }
                pc2.init(player, this, template3);
                this.challenges.put(pc2.getChallengeId(), pc2);
            }
        }
    }

    public void setReward(final int challengeId) {
        synchronized (this.rewards) {
            final ChallengeReward reward = this.rewards.computeIfAbsent(challengeId, integer -> ChallengeReward.newChallengeReward(challengeId));
            reward.setRewardCount(reward.getRewardCount() + 1);
        }
    }

    public void reward(final int challengeId, final int selectRewardIndex) {
        synchronized (this.rewards) {
            final ChallengeReward challengereward = this.rewards.get(challengeId);
            if (challengereward == null) {
                return;
            }
            final RewardTemplate baseReward = challengereward.getTemplate().getBaseRewardT();
            final RewardTemplate selectReward = challengereward.getTemplate().getSelectRewardT();
            final ConcurrentLinkedQueue<Item> addItemTasks = new ConcurrentLinkedQueue<Item>();
            if (selectReward != null) {
                selectReward.getRewardItems(selectRewardIndex, addItemTasks);
            }
            if (baseReward != null) {
                baseReward.getRewardItems(-1, addItemTasks);
            }
            if (!addItemTasks.isEmpty() && !this.player.getPlayerBag().onEvent(new AddItemEvent(this.player, addItemTasks))) {
                return;
            }
            if (baseReward != null) {
                baseReward.rewardPlayer(this.player, -1, -1);
            }
            challengereward.setRewardCount(challengereward.getRewardCount() - 1);
            if (challengereward.getRewardCount() <= 0) {
                this.rewards.remove(challengeId);
            }
            this.player.sendPacket(new SMGetChallengeReward(challengeId));
        }
    }

    public AccountData getAccountData() {
        return this.accountData;
    }

    public Collection<AChallenge> getProgressChallenge() {
        return this.challenges.values().stream().filter(iChallenge -> iChallenge.getState().isProgress()).collect(Collectors.toCollection(ArrayList::new));
    }

    public Collection<AChallenge> getCompleteChallenge() {
        return this.challenges.values().stream().filter(iChallenge -> iChallenge.getState().isComplete()).collect(Collectors.toCollection(ArrayList::new));
    }

    public Collection<ChallengeReward> getRewards() {
        return this.rewards.values();
    }

    public void onLogin() {
        final Collection<AChallenge> progressChallenge = this.getProgressChallenge();
        if (!progressChallenge.isEmpty()) {
            this.player.sendPacket(new SMListProgressChallenge(progressChallenge));
        }
        final Collection<AChallenge> completeChallenge = this.getCompleteChallenge();
        if (!completeChallenge.isEmpty()) {
            this.player.sendPacket(new SMListCompleteChallenge(completeChallenge));
        }
        final Collection<ChallengeReward> challengeReward = this.getRewards();
        if (!challengeReward.isEmpty()) {
            this.player.sendPacket(new SMListChallengeReward(challengeReward));
        }
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList challengeRewardDB = new BasicDBList();
        for (final ChallengeReward challenge : this.rewards.values()) {
            challengeRewardDB.add(challenge.toDBObject());
        }
        final BasicDBList challengeListDB = new BasicDBList();
        for (final AChallenge challenge2 : this.challenges.values()) {
            challengeListDB.add(challenge2.toDBObject());
        }
        builder.append("challengeReward", challengeRewardDB);
        builder.append("challenges", challengeListDB);
        return builder.get();
    }
}
