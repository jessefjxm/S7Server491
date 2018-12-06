package com.bdoemu.gameserver.model.creature.servant.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PetFusionTemplate {
    private int race;
    private int kind;
    private int tier;
    private int selectRate0;
    private int selectRate1;
    private int selectTier0;
    private int selectTier1;

    public PetFusionTemplate(final ResultSet resultSet) throws SQLException {
        this.race = resultSet.getInt("Race");
        this.kind = resultSet.getInt("Kind");
        this.tier = resultSet.getInt("Tier");
        this.selectRate0 = resultSet.getInt("SelectRate_0");
        this.selectTier0 = resultSet.getInt("SelectTier_0");
        this.selectRate1 = resultSet.getInt("SelectRate_1");
        this.selectTier1 = resultSet.getInt("SelectTier_1");
    }

    public int getRace() {
        return this.race;
    }

    public void setRace(final int race) {
        this.race = race;
    }

    public int getKind() {
        return this.kind;
    }

    public void setKind(final int kind) {
        this.kind = kind;
    }

    public int getTier() {
        return this.tier;
    }

    public void setTier(final int tier) {
        this.tier = tier;
    }

    public int getSelectRate0() {
        return this.selectRate0;
    }

    public void setSelectRate0(final int selectRate0) {
        this.selectRate0 = selectRate0;
    }

    public int getSelectRate1() {
        return this.selectRate1;
    }

    public void setSelectRate1(final int selectRate1) {
        this.selectRate1 = selectRate1;
    }

    public int getSelectTier0() {
        return this.selectTier0;
    }

    public void setSelectTier0(final int selectTier0) {
        this.selectTier0 = selectTier0;
    }

    public int getSelectTier1() {
        return this.selectTier1;
    }

    public void setSelectTier1(final int selectTier1) {
        this.selectTier1 = selectTier1;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PetFusionTemplate)) {
            return false;
        }
        final PetFusionTemplate other = (PetFusionTemplate) o;
        return other.canEqual(this) && this.getRace() == other.getRace() && this.getKind() == other.getKind() && this.getTier() == other.getTier() && this.getSelectRate0() == other.getSelectRate0() && this.getSelectRate1() == other.getSelectRate1() && this.getSelectTier0() == other.getSelectTier0() && this.getSelectTier1() == other.getSelectTier1();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PetFusionTemplate;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getRace();
        result = result * 59 + this.getKind();
        result = result * 59 + this.getTier();
        result = result * 59 + this.getSelectRate0();
        result = result * 59 + this.getSelectRate1();
        result = result * 59 + this.getSelectTier0();
        result = result * 59 + this.getSelectTier1();
        return result;
    }

    @Override
    public String toString() {
        return "PetFusionTemplate(race=" + this.getRace() + ", kind=" + this.getKind() + ", tier=" + this.getTier() + ", selectRate0=" + this.getSelectRate0() + ", selectRate1=" + this.getSelectRate1() + ", selectTier0=" + this.getSelectTier0() + ", selectTier1=" + this.getSelectTier1() + ")";
    }
}
