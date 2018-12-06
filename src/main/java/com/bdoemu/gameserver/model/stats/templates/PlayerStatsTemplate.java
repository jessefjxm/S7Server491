package com.bdoemu.gameserver.model.stats.templates;

import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;
import com.bdoemu.gameserver.utils.DiceUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerStatsTemplate extends StatsTemplate {
    public PlayerStatsTemplate(final ResultSet rs, final CreatureTemplate template) throws SQLException {
        this.baseContainer.put(StatEnum.STR, new BaseElement(rs.getFloat("Str")));
        this.baseContainer.put(StatEnum.VIT, new BaseElement(rs.getFloat("Vit")));
        this.baseContainer.put(StatEnum.WIS, new BaseElement(rs.getFloat("Wis")));
        this.baseContainer.put(StatEnum.INT, new BaseElement(rs.getFloat("Int")));
        this.baseContainer.put(StatEnum.DEX, new BaseElement(rs.getFloat("Dex")));
        this.baseContainer.put(StatEnum.HP, new BaseElement(rs.getFloat("HP")));
        this.baseContainer.put(StatEnum.MP, new BaseElement(rs.getFloat("MP")));
        this.baseContainer.put(StatEnum.HPRegen, new BaseElement(rs.getInt("HPRegen")));
        this.baseContainer.put(StatEnum.MPRegen, new BaseElement(rs.getInt("MPRegen")));
        this.baseContainer.put(StatEnum.Weight, new BaseElement(rs.getLong("possessableWeight")));
        this.baseContainer.put(StatEnum.DDD, new BaseElement(DiceUtils.getDiceRnd(rs.getString("DDD"))));
        this.baseContainer.put(StatEnum.RDD, new BaseElement(DiceUtils.getDiceRnd(rs.getString("RDD"))));
        this.baseContainer.put(StatEnum.MDD, new BaseElement(DiceUtils.getDiceRnd(rs.getString("MDD"))));
        this.baseContainer.put(StatEnum.RHIT, new BaseElement(rs.getFloat("RHIT")));
        this.baseContainer.put(StatEnum.DHIT, new BaseElement(rs.getFloat("DHIT")));
        this.baseContainer.put(StatEnum.MHIT, new BaseElement(rs.getFloat("MHIT")));
        this.baseContainer.put(StatEnum.DDV, new BaseElement(rs.getFloat("DDV")));
        this.baseContainer.put(StatEnum.RDV, new BaseElement(rs.getFloat("RDV")));
        this.baseContainer.put(StatEnum.MDV, new BaseElement(rs.getFloat("MDV")));
        this.baseContainer.put(StatEnum.DPV, new BaseElement(rs.getFloat("DPV")));
        this.baseContainer.put(StatEnum.RPV, new BaseElement(rs.getFloat("RPV")));
        this.baseContainer.put(StatEnum.MPV, new BaseElement(rs.getFloat("MPV")));
        this.baseContainer.put(StatEnum.StunGauge, new BaseElement(rs.getInt("StunGauge")));
        this.baseContainer.put(StatEnum.Stamina, new BaseElement(1000.0f));
        this.baseContainer.put(StatEnum.MoveSpeedRate, new BaseElement(rs.getInt("MaxMoveSpeed")));
        this.baseContainer.put(StatEnum.AttackSpeedRate, new BaseElement(rs.getInt("MaxAttackSpeed")));
        this.baseContainer.put(StatEnum.CastingSpeedRate, new BaseElement(rs.getInt("MaxCastingSpeed")));
        this.baseContainer.put(StatEnum.CriticalRate, new BaseElement(rs.getInt("MaxCriticalRate")));
        this.baseContainer.put(StatEnum.DropRate, new BaseElement(rs.getInt("MaxDropItemLuck")));
        this.baseContainer.put(StatEnum.FishingLuck, new BaseElement(rs.getInt("MaxFishingLuck")));
        this.baseContainer.put(StatEnum.CollectionLuck, new BaseElement(rs.getInt("MaxCollectionLuck")));
        this.baseContainer.put(StatEnum.SubResourcePoint, new BaseElement(rs.getInt("SubResourcePoint")));
        this.baseContainer.put(StatEnum.ResistKnockDown, new BaseElement(template.getGameStatsTemplate().getBaseElement(StatEnum.ResistKnockDown).getValue()));
        this.baseContainer.put(StatEnum.ResistKnockBack, new BaseElement(template.getGameStatsTemplate().getBaseElement(StatEnum.ResistKnockBack).getValue()));
        this.baseContainer.put(StatEnum.ResistCapture, new BaseElement(template.getGameStatsTemplate().getBaseElement(StatEnum.ResistCapture).getValue()));
        this.baseContainer.put(StatEnum.ResistStun, new BaseElement(template.getGameStatsTemplate().getBaseElement(StatEnum.ResistStun).getValue()));
        this.baseContainer.put(StatEnum.ResistRigid, new BaseElement(template.getGameStatsTemplate().getBaseElement(StatEnum.ResistRigid).getValue()));
        this.baseContainer.put(StatEnum.ResistBound, new BaseElement(template.getGameStatsTemplate().getBaseElement(StatEnum.ResistBound).getValue()));
        this.baseContainer.put(StatEnum.ResistHorror, new BaseElement(template.getGameStatsTemplate().getBaseElement(StatEnum.ResistHorror).getValue()));
        this.baseContainer.put(StatEnum.ResistStandDown, new BaseElement(template.getGameStatsTemplate().getBaseElement(StatEnum.ResistStandDown).getValue()));
        this.baseContainer.put(StatEnum.Adrenalin, new BaseElement(500.0f));
        this.baseContainer.put(StatEnum.WPRegen, new BaseElement(1.0f));
    }
}











