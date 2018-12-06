package com.bdoemu.gameserver.model.creature.player.quests;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMUpdateQuest;
import com.bdoemu.gameserver.dataholders.QuestData;
import com.bdoemu.gameserver.model.conditions.complete.ICompleteConditionHandler;
import com.bdoemu.gameserver.model.creature.observers.Observer;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.quests.templates.QuestTemplate;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

public class Quest extends JSONable {
    private final QuestTemplate template;
    private final Player player;
    private final int[] steps;
    private final long acceptTime;
    private List<Observer> observers;

    public Quest(final Player player, final QuestTemplate template) {
        this.steps = new int[5];
        this.player = player;
        this.template = template;
        this.registerObservers();
        this.acceptTime = GameTimeService.getServerTimeInMillis();
    }

    public Quest(final Player player, final BasicDBObject dbObject) {
        this.steps = new int[5];
        this.player = player;
        final int groupId = dbObject.getInt("groupId");
        final int questId = dbObject.getInt("questId");
        final BasicDBList stepsDB = (BasicDBList) dbObject.get("steps");
        for (int index = 0; index < stepsDB.size(); ++index) {
            this.steps[index] = (int) stepsDB.get(index);
        }
        this.template = QuestData.getInstance().getTemplate(groupId, questId);
        this.acceptTime = dbObject.getLong("acceptedTime");
        this.registerObservers();
    }

    public static Quest newQuest(final Player player, final BasicDBObject dbObject) {
        final int groupId = dbObject.getInt("groupId");
        final int questId = dbObject.getInt("questId");
        final QuestTemplate template = QuestData.getInstance().getTemplate(groupId, questId);
        if (template == null) {
            return null;
        }
        return new Quest(player, dbObject);
    }

    private final void registerObservers() {
        this.observers = new ArrayList<>();
        int i = 0;
        for (final ICompleteConditionHandler condition : this.template.getCompleteConditions()) {
            final int stepIndex = i;
            final EObserveType observeType = condition.getObserveType();
            final Observer observer = new Observer(observeType, condition.getKey()) {
                @Override
                public void notify(final EObserveType type, final Object... params) {
                    synchronized (condition) {
                        final int questStep = Quest.this.steps[stepIndex];
                        if (questStep < condition.getStepCount()) {
                            final int stepCounts = condition.checkCondition(params);
                            if (stepCounts > 0) {
                                Quest.this.steps[stepIndex] += stepCounts;
                                if (questStep >= condition.getStepCount()) {
                                    Quest.this.steps[stepIndex] = condition.getStepCount();
                                    Quest.this.player.getObserveController().removeObserver(this);
                                    Quest.this.observers.remove(this);
                                }
                                Quest.this.player.sendPacket(new SMUpdateQuest(Quest.this));
                            }
                        }
                    }
                }
            };
            this.player.getObserveController().putObserver(observer);
            this.observers.add(observer);
            ++i;
        }
    }

    public void clearObservers() {
        if (this.observers != null) {
            for (final Observer observer : this.observers) {
                this.player.getObserveController().removeObserver(observer);
            }
            this.observers.clear();
            this.observers = null;
        }
    }

    public boolean isCompleteQuest() {
        int stepIndex = 0;
        for (final ICompleteConditionHandler condition : this.template.getCompleteConditions()) {
            final int questStep = this.steps[stepIndex];
            if (questStep < condition.getStepCount()) {
                return false;
            }
            ++stepIndex;
        }
        return true;
    }

    public boolean isCompleteStep(final int stepIndex) {
        if (stepIndex < 0 || stepIndex > this.template.getCompleteConditions().size() - 1) {
            return false;
        }
        final ICompleteConditionHandler condition = this.template.getCompleteConditions().get(stepIndex);
        final int questStep = this.steps[stepIndex];
        return questStep >= condition.getStepCount();
    }

    public void onAcceptQuest() {
        int stepIndex = 0;
        for (final ICompleteConditionHandler condition : this.template.getCompleteConditions()) {
            synchronized (condition) {
                final int questStep = this.steps[stepIndex];
                if (questStep < condition.getStepCount() && condition.checkStep(this.player)) {
                    this.steps[stepIndex] = condition.getStepCount();
                    this.player.sendPacket(new SMUpdateQuest(this));
                }
            }
            ++stepIndex;
        }
    }

    public long getAcceptTime() {
        return this.acceptTime;
    }

    public int[] getSteps() {
        return this.steps;
    }

    public QuestTemplate getTemplate() {
        return this.template;
    }

    public int getQuestGroupId() {
        return this.template.getQuestGroupId();
    }

    public int getQuestId() {
        return this.template.getQuestId();
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.add("groupId", this.getQuestGroupId());
        builder.add("questId", this.getQuestId());
        builder.add("steps", this.getSteps());
        builder.add("acceptedTime", this.getAcceptTime());
        return builder.get();
    }
}
