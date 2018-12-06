package com.bdoemu.gameserver.model.stats.templates;

import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;
import com.bdoemu.gameserver.utils.DiceUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServantStatsTemplate extends StatsTemplate {
    public ServantStatsTemplate(final ResultSet rs, final CreatureTemplate template) throws SQLException {
        this.baseContainer.put(StatEnum.STR, new BaseElement(10.0f));
        this.baseContainer.put(StatEnum.VIT, new BaseElement(10.0f));
        this.baseContainer.put(StatEnum.WIS, new BaseElement(10.0f));
        this.baseContainer.put(StatEnum.INT, new BaseElement(10.0f));
        this.baseContainer.put(StatEnum.DEX, new BaseElement(10.0f));
        this.baseContainer.put(StatEnum.HP, new BaseElement(rs.getFloat("HP") + template.getGameStatsTemplate().getBaseElement(StatEnum.HP).getValue()));
        this.baseContainer.put(StatEnum.MP, new BaseElement(rs.getFloat("MP") + template.getGameStatsTemplate().getBaseElement(StatEnum.MP).getValue()));
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
        this.baseContainer.put(StatEnum.MoveSpeedRate, new BaseElement(rs.getInt("MaxMoveSpeed")));
        this.baseContainer.put(StatEnum.AttackSpeedRate, new BaseElement(rs.getInt("MaxAttackSpeed")));
        this.baseContainer.put(StatEnum.CastingSpeedRate, new BaseElement(rs.getInt("MaxCastingSpeed")));
        this.baseContainer.put(StatEnum.CriticalRate, new BaseElement(rs.getInt("MaxCriticalRate")));
        this.baseContainer.put(StatEnum.DropRate, new BaseElement(rs.getInt("MaxDropItemLuck")));
        this.baseContainer.put(StatEnum.FishingLuck, new BaseElement(rs.getInt("MaxFishingLuck")));
        this.baseContainer.put(StatEnum.CollectionLuck, new BaseElement(rs.getInt("MaxCollectionLuck")));
        this.baseContainer.put(StatEnum.ResistKnockDown, new BaseElement(200000.0f));
        this.baseContainer.put(StatEnum.ResistStun, new BaseElement(200000.0f));
        this.baseContainer.put(StatEnum.ResistKnockBack, new BaseElement(200000.0f));
        this.baseContainer.put(StatEnum.ResistCapture, new BaseElement(200000.0f));
        this.baseContainer.put(StatEnum.Adrenalin, new BaseElement(100.0f));
        this.baseContainer.put(StatEnum.AccelerationRate, new BaseElement(rs.getInt("AccelerationRate")));
        this.baseContainer.put(StatEnum.MaxMoveSpeedRate, new BaseElement(rs.getInt("MaxMoveSpeedRate")));
        this.baseContainer.put(StatEnum.CorneringSpeedRate, new BaseElement(rs.getInt("CorneringSpeedRate")));
        this.baseContainer.put(StatEnum.BrakeSpeedRate, new BaseElement(rs.getInt("BrakeSpeedRate")));
    }
}
