package com.bdoemu.gameserver.model.creature.servant.templates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class PetTemplate {
    private int id;
    private int hunger;
    private int race;
    private int tier;
    private int kind;
    private int petChangeLookKey;
    private int defaultActionIndex;
    private int equipSkillAquireKey;
    private HashMap<Integer, Integer> expTemplates;

    public PetTemplate(final ResultSet rs, final HashMap<Integer, Integer> expTemplates) throws SQLException {
        this.expTemplates = expTemplates;
        this.id = rs.getInt("CharacterKey");
        this.hunger = rs.getInt("Hunger");
        this.race = rs.getInt("Race");
        this.tier = rs.getInt("Tier");
        this.kind = rs.getInt("Kind");
        this.petChangeLookKey = rs.getInt("PetChangeLookKey");
        this.defaultActionIndex = rs.getInt("DefaultActionIndex");
        this.equipSkillAquireKey = rs.getInt("EquipSkillAquireKey");
    }

    public HashMap<Integer, Integer> getExpTemplates() {
        return this.expTemplates;
    }

    public Integer getMaxExp(final int level) {
        return this.expTemplates.get(level);
    }

    public int getId() {
        return this.id;
    }

    public int getHunger() {
        return this.hunger;
    }

    public int getDefaultActionIndex() {
        return this.defaultActionIndex;
    }

    public int getPetChangeLookKey() {
        return this.petChangeLookKey;
    }

    public int getTier() {
        return this.tier;
    }

    public int getRace() {
        return this.race;
    }

    public int getKind() {
        return this.kind;
    }

    public int getEquipSkillAquireKey() {
        return this.equipSkillAquireKey;
    }
}
