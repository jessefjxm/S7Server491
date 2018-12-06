package com.bdoemu.gameserver.model.creature.player.encyclopedia.templates;

import com.bdoemu.gameserver.model.creature.player.encyclopedia.enums.EEncyclopediaType;
import com.bdoemu.gameserver.model.misc.enums.EFishEncyclopediaCategory;
import com.bdoemu.gameserver.utils.DiceUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EncyclopediaT {
    private int key;
    private int baseSize;
    private int typeKey;
    private int rareness;
    private DiceUtils.DiceValue[] varySize;
    private EEncyclopediaType encyclopediaType;
    private EFishEncyclopediaCategory fishEncyclopediaCategory;

    public EncyclopediaT(final ResultSet rs) throws SQLException {
        this.varySize = new DiceUtils.DiceValue[5];
        this.key = rs.getInt("Key");
        this.encyclopediaType = EEncyclopediaType.valueOf(rs.getInt("Type"));
        this.fishEncyclopediaCategory = EFishEncyclopediaCategory.values()[rs.getInt("Category")];
        this.rareness = rs.getInt("Rareness");
        this.typeKey = rs.getInt("TypeKey");
        this.baseSize = rs.getInt("BaseSize");
        for (int i = 0; i < this.varySize.length; ++i) {
            this.varySize[i] = DiceUtils.getDiceValueRnd(rs.getString("VarySize_" + i));
        }
    }

    public EEncyclopediaType getEncyclopediaType() {
        return this.encyclopediaType;
    }

    public EFishEncyclopediaCategory getFishEncyclopediaCategory() {
        return this.fishEncyclopediaCategory;
    }

    public int getKey() {
        return this.key;
    }

    public int getBaseSize() {
        return this.baseSize;
    }

    public int getTypeKey() {
        return this.typeKey;
    }

    public DiceUtils.DiceValue[] getVarySize() {
        return this.varySize;
    }

    public int getRareness() {
        return this.rareness;
    }
}
