// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.card.templates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CardTemplate {
    private int cardId;
    private int mainTheme;
    private int variedValue;
    private int applyTurn;
    private int validTurn;
    private int hit;
    private int minDd;
    private int maxDd;
    private Integer buffType;
    private Set<Integer> contentsGroupKey;

    public CardTemplate(final ResultSet rs) throws SQLException {
        this.contentsGroupKey = new HashSet<Integer>();
        this.cardId = rs.getInt("Key");
        this.mainTheme = rs.getInt("MainTheme");
        this.hit = rs.getInt("Hit");
        this.minDd = rs.getInt("MinDd");
        this.maxDd = rs.getInt("MaxDd");
        if (rs.getString("BuffType") != null) {
            this.buffType = rs.getInt("BuffType");
        }
        this.variedValue = rs.getInt("VariedValue");
        this.applyTurn = rs.getInt("ApplyTurn");
        this.validTurn = rs.getInt("ValidTurn");
        final String[] split;
        final String[] contentGroupKeyData = split = rs.getString("ContentsGroupKey").split(",");
        for (final String contentGroupData : split) {
            this.contentsGroupKey.add(Integer.parseInt(contentGroupData.trim()));
        }
    }

    public Integer getBuffType() {
        return this.buffType;
    }

    public int getMinDd() {
        return this.minDd;
    }

    public int getMaxDd() {
        return this.maxDd;
    }

    public int getHit() {
        return this.hit;
    }

    public int getVariedValue() {
        return this.variedValue;
    }

    public int getValidTurn() {
        return this.validTurn;
    }

    public int getApplyTurn() {
        return this.applyTurn;
    }

    public int getMainTheme() {
        return this.mainTheme;
    }

    public int getCardId() {
        return this.cardId;
    }

    public Set<Integer> getContentsGroupKey() {
        return this.contentsGroupKey;
    }
}
