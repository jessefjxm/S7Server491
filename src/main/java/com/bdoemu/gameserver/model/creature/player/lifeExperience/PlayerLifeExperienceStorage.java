package com.bdoemu.gameserver.model.creature.player.lifeExperience;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class PlayerLifeExperienceStorage extends JSONable {
    private LifeExperienceInformation[] lifeExperiences;

    public PlayerLifeExperienceStorage() {
        this.lifeExperiences = new LifeExperienceInformation[ELifeExpType.values().length - 1];
        for (final ELifeExpType type : ELifeExpType.values()) {
            if (!type.isNone()) {
                this.lifeExperiences[type.ordinal()] = new LifeExperienceInformation(type);
            }
        }
    }

    public PlayerLifeExperienceStorage(final BasicDBObject dbObject) {
        this();
        final BasicDBList lifeExperienceListDB = (BasicDBList) dbObject.get("lifeExperienceList");
        for (final Object lifeExperienceDBObject : lifeExperienceListDB) {
            final BasicDBObject lifeExperienceDB = (BasicDBObject) lifeExperienceDBObject;
            ELifeExpType type;
            if (lifeExperienceDB.containsField("type")) {
                type = ELifeExpType.valueOf(lifeExperienceDB.getString("type"));
            } else {
                type = ELifeExpType.valueOf(lifeExperienceDB.getInt("typeId"));
            }
            final long exp = lifeExperienceDB.getLong("exp");
            final int level = lifeExperienceDB.getInt("level");
            this.lifeExperiences[type.ordinal()].setValues(exp, level);
        }
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList lifeExperienceListDB = new BasicDBList();
        for (final LifeExperienceInformation lifeExperience : this.lifeExperiences) {
            final BasicDBObject obj = new BasicDBObject();
            obj.append("typeId", lifeExperience.getType().ordinal());
            obj.append("exp", lifeExperience.getExp());
            obj.append("level", lifeExperience.getLevel());
            lifeExperienceListDB.add(obj);
        }
        builder.append("lifeExperienceList", lifeExperienceListDB);
        return builder.get();
    }

    public LifeExperienceInformation[] getLifeExperiences() {
        return this.lifeExperiences;
    }

    public LifeExperienceInformation getLifeExperience(final ELifeExpType type) {
        return this.lifeExperiences[type.ordinal()];
    }
}
