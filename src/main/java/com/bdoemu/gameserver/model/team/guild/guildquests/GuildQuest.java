package com.bdoemu.gameserver.model.team.guild.guildquests;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMUpdateGuildQuest;
import com.bdoemu.gameserver.dataholders.GuildQuestData;
import com.bdoemu.gameserver.model.conditions.complete.ICompleteConditionHandler;
import com.bdoemu.gameserver.model.creature.observers.Observer;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.GuildMember;
import com.bdoemu.gameserver.model.team.guild.guildquests.templates.GuildQuestT;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class GuildQuest extends JSONable {
    private final int[] _steps;
    private Guild _guild;
    private GuildQuestT _guildQuestT;
    private long _acceptDate;
    private boolean _isRewardCollected;

    public GuildQuest(final Guild guild, final GuildQuestT guildQuestT) {
        _steps = new int[5];
        _guild = guild;
        _guildQuestT = guildQuestT;
        _acceptDate = GameTimeService.getServerTimeInMillis();
        _isRewardCollected = false;
        registerObservers();
    }

    public GuildQuest(final Guild guild, final BasicDBObject dbObject) {
        _steps = new int[5];
        _guild = guild;
        _guildQuestT = GuildQuestData.getInstance().getTemplate(dbObject.getInt("questId"));
        _acceptDate = dbObject.getLong("acceptDate");
        _isRewardCollected = false;

        final BasicDBList stepsDB = (BasicDBList) dbObject.get("steps");
        for (int i = 0; i < stepsDB.size(); ++i)
            _steps[i] = (int) stepsDB.get(i);
        registerObservers();
    }

    public void registerObserverOnLogin(Player player) {
        if (player != null) {
            final GuildMember member = _guild.getMember(player.getAccountId());
            if (member != null) {
                CopyOnWriteArrayList<Observer> observers = member.getObservers();
                if (observers != null) {
                    for (Observer obs : observers) {
                        if (obs != null)
                            player.getObserveController().putObserver(obs);
                    }
                }
            }
        }
    }

    public void deregisterObserverPlayer(Player player) {
        if (player != null) {
            final GuildMember member = _guild.getMember(player.getAccountId());
            if (member != null) {
                CopyOnWriteArrayList<Observer> observers = member.getObservers();
                if (observers != null) {
                    for (Observer obs : observers) {
                        if (obs != null) {
                            player.getObserveController().removeObserver(obs);
                            member.removeObserver(obs);
                        }
                    }
                }
            }
        }
    }

    private final void registerObservers() {
        int i = 0;
        for (final ICompleteConditionHandler condition : _guildQuestT.getCompleteConditions()) {
            final int stepIndex = i;
            final EObserveType observeType = condition.getObserveType();

            for (Map.Entry<Long, GuildMember> entry : _guild.getMembers().entrySet()) {
                if (entry.getValue() != null && entry.getValue().isContractValid()) {
                    final Observer observer = new Observer(observeType, condition.getKey()) {
                        @Override
                        public void notify(final EObserveType type, final Object... params) {
                            synchronized (condition) {
                                if (_steps[stepIndex] < condition.getStepCount()) {
                                    final int stepCounts = condition.checkCondition(params);
                                    if (stepCounts > 0) {
                                        _steps[stepIndex] += stepCounts;
                                        if (_steps[stepIndex] >= condition.getStepCount()) {
                                            _steps[stepIndex] = condition.getStepCount();

                                            for (Map.Entry<Long, GuildMember> entry : _guild.getMembers().entrySet()) {
                                                if (entry.getValue() != null) {
                                                    Player player = entry.getValue().getPlayer();
                                                    if (player != null)
                                                        player.getObserveController().removeObserver(this);
                                                    entry.getValue().removeObserver(this);
                                                }
                                            }
                                        }

                                        if (entry.getValue().getPlayer() != null) {
                                            final Player pl = entry.getValue().getPlayer();
                                            _guild.sendBroadcastPacket(new SMUpdateGuildQuest(_guild, pl));

                                            final GuildMember member = _guild.getMember(pl.getAccountId());
                                            member.addActivityPoints(_guildQuestT.getActivityPerDemand());
                                        }
                                    }
                                }
                            }
                        }
                    };

                    entry.getValue().addObserver(observer);
                    if (entry.getValue().getPlayer() != null)
                        entry.getValue().getPlayer().getObserveController().putObserver(observer);
                }
            }
            ++i;
        }
    }

    public void clearObservers() {
        for (Map.Entry<Long, GuildMember> entry : _guild.getMembers().entrySet()) {
            if (entry.getValue() != null) {
                final CopyOnWriteArrayList<Observer> observers = entry.getValue().getObservers();
                if (observers != null) {
                    for (Observer observer : observers) {
                        if (entry.getValue().getPlayer() != null)
                            entry.getValue().getPlayer().getObserveController().removeObserver(observer);
                        entry.getValue().removeObserver(observer);
                    }
                }

                entry.getValue().setObservers(null);
            }
        }
    }

    public boolean isCompleteQuest() {
        int stepIndex = 0;

        for (final ICompleteConditionHandler condition : _guildQuestT.getCompleteConditions()) {
            if (_steps[stepIndex++] < condition.getStepCount())
                return false;
        }

        return true;
    }

    public boolean isRewardCollected() {
        return _isRewardCollected;
    }

    public void setRewardCollected() {
        _isRewardCollected = true;
    }

    public Guild getGuild() {
        return _guild;
    }

    public int getQuestId() {
        return _guildQuestT.getGuildQuestNr();
    }

    public int[] getSteps() {
        return _steps;
    }

    public GuildQuestT getGuildQuestT() {
        return _guildQuestT;
    }

    public long getAcceptDate() {
        return _acceptDate;
    }

    public long getEndDate() {
        return _acceptDate + _guildQuestT.getLimitMinute() * 60 * 1000;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > getEndDate();
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("questId", getQuestId());
        builder.append("steps", getSteps());
        builder.append("acceptDate", getAcceptDate());
        return builder.get();
    }
}