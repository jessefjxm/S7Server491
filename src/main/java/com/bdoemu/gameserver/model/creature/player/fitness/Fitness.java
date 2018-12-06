package com.bdoemu.gameserver.model.creature.player.fitness;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.network.sendable.SMFitnessExperienceInformation;
import com.bdoemu.gameserver.dataholders.FitnessData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.fitness.enums.EFitnessType;
import com.bdoemu.gameserver.model.creature.player.fitness.templates.FitnessTemplate;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class Fitness extends JSONable {
    private final EFitnessType fitnessType;
    private FitnessTemplate template;
    private int level;
    private long exp;

    public Fitness(final BasicDBObject basicDBObject) {
        this.level = 1;
        this.fitnessType = EFitnessType.valueOf(basicDBObject.getString("type"));
        this.level = basicDBObject.getInt("level");
        this.exp = basicDBObject.getLong("exp");
        this.template = FitnessData.getInstance().getTemplate(this.fitnessType, this.level);
    }

    public Fitness(final EFitnessType fitnessType) {
        this.level = 1;
        this.fitnessType = fitnessType;
        this.template = FitnessData.getInstance().getTemplate(fitnessType, this.level);
    }

    public FitnessTemplate getTemplate() {
        return this.template;
    }

    public EFitnessType getFitnessType() {
        return this.fitnessType;
    }

    public int getLevel() {
        return this.level;
    }

    public long getExp() {
        return this.exp;
    }

    public synchronized void addExp(final Player player, final int exp) {
        if (exp <= 0) {
            return;
        }
        final int ratedExp = (int) (exp * RateConfig.RATE_FITNESS_EXP / 100.0f);
        this.exp += ratedExp;
        int addStamina = 0;
        int addWeight = 0;
        int addHp = 0;
        int addMp = 0;
        if (this.exp >= this.template.getNeedExp()) {
            final FitnessTemplate nextTemplate = FitnessData.getInstance().getTemplate(this.fitnessType, this.level + 1);
            if (nextTemplate == null) {
                this.exp = this.template.getNeedExp();
                return;
            }
            ++this.level;
            this.exp = 0L;
            addStamina = nextTemplate.getAddStamina() - this.template.getAddStamina();
            addWeight = nextTemplate.getAddWeight() - this.template.getAddWeight();
            addHp = nextTemplate.getAddHp() - this.template.getAddHp();
            addMp = nextTemplate.getAddMp() - this.template.getAddMp();
            this.template = nextTemplate;
            player.getFitnessHandler().updateStats(player, addStamina, addWeight, addHp, addMp);
        }
        player.sendPacket(new SMFitnessExperienceInformation(this, addStamina, addWeight, addHp, addMp));
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("type", this.getFitnessType().name());
        builder.append("level", this.getLevel());
        builder.append("exp", this.getExp());
        return builder.get();
    }
}
