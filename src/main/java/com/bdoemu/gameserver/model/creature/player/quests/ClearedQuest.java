package com.bdoemu.gameserver.model.creature.player.quests;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.QuestData;
import com.bdoemu.gameserver.model.creature.player.quests.templates.QuestTemplate;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class ClearedQuest extends JSONable {
    private final QuestTemplate template;
    private long repeatTime;

    public ClearedQuest(final BasicDBObject dbObject) {
        final int groupId = dbObject.getInt("groupId");
        final int questId = dbObject.getInt("questId");
        this.repeatTime = dbObject.getLong("repeatTime");
        this.template = QuestData.getInstance().getTemplate(groupId, questId);
    }

    public ClearedQuest(final QuestTemplate template) {
        this.template = template;
        if (template.getRepeatTime() > 0) {
            this.repeatTime = GameTimeService.getServerTimeInMillis() + template.getRepeatTime() * 60 * 60 * 1000;
        }
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

    public long getRepeatTime() {
        return this.repeatTime;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.add("groupId", this.getQuestGroupId());
        builder.add("questId", this.getQuestId());
        builder.add("repeatTime", this.getRepeatTime());
        return builder.get();
    }
}
