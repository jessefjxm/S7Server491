package com.bdoemu.gameserver.model.stats.templates;

import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;
import com.bdoemu.gameserver.utils.DiceUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreatureStatsTemplate extends StatsTemplate {
    public CreatureStatsTemplate(final ResultSet rs) throws SQLException {
        /*if (rs.getInt("Index") == 20601 || rs.getInt("Index") == 20602) {
			System.out.println("Trying to parse for index: " + rs.getInt("Index") + ", DDD: " + rs.getString("DDD").toUpperCase());
			int[] dice = DiceUtils.getDiceRnd(rs.getString("DDD").toUpperCase());
			for (int i = 0; i < dice.length; ++i) {
				System.out.println(dice[i]);
			}
			System.exit(0);
		}*/
        this.baseContainer.put(StatEnum.HP, new BaseElement(rs.getFloat("HP")));
        this.baseContainer.put(StatEnum.MP, new BaseElement(rs.getFloat("MP")));
        this.baseContainer.put(StatEnum.HPRegen, new BaseElement(rs.getInt("HPRegen")));
        this.baseContainer.put(StatEnum.MPRegen, new BaseElement(rs.getInt("MPRegen")));
        this.baseContainer.put(StatEnum.DDD, new BaseElement(DiceUtils.getDiceRnd(rs.getString("DDD"))));
        this.baseContainer.put(StatEnum.DHIT, new BaseElement(rs.getInt("DHIT")));
        this.baseContainer.put(StatEnum.DDV, new BaseElement(rs.getInt("DDV")));
        this.baseContainer.put(StatEnum.HDDV, new BaseElement(rs.getInt("HDDV")));
        this.baseContainer.put(StatEnum.DPV, new BaseElement(rs.getInt("DPV")));
        this.baseContainer.put(StatEnum.HDPV, new BaseElement(rs.getInt("HDPV")));
        this.baseContainer.put(StatEnum.RDD, new BaseElement(DiceUtils.getDiceRnd(rs.getString("RDD"))));
        this.baseContainer.put(StatEnum.RHIT, new BaseElement(rs.getInt("RHIT")));
        this.baseContainer.put(StatEnum.RDV, new BaseElement(rs.getInt("RDV")));
        this.baseContainer.put(StatEnum.HRDV, new BaseElement(rs.getInt("HRDV")));
        this.baseContainer.put(StatEnum.RPV, new BaseElement(rs.getInt("RPV")));
        this.baseContainer.put(StatEnum.HRPV, new BaseElement(rs.getInt("HRPV")));
        this.baseContainer.put(StatEnum.MDD, new BaseElement(DiceUtils.getDiceRnd(rs.getString("MDD"))));
        this.baseContainer.put(StatEnum.MHIT, new BaseElement(rs.getInt("MHIT")));
        this.baseContainer.put(StatEnum.MDV, new BaseElement(rs.getInt("MDV")));
        this.baseContainer.put(StatEnum.HMDV, new BaseElement(rs.getInt("HMDV")));
        this.baseContainer.put(StatEnum.MPV, new BaseElement(rs.getInt("MPV")));
        this.baseContainer.put(StatEnum.HMPV, new BaseElement(rs.getInt("HMPV")));
        this.baseContainer.put(StatEnum.ResistStun, new BaseElement(rs.getInt("StunResist")));
        this.baseContainer.put(StatEnum.ResistHorror, new BaseElement(rs.getInt("HorrorResist")));
        this.baseContainer.put(StatEnum.ResistCapture, new BaseElement(rs.getInt("CaptureResist")));
        this.baseContainer.put(StatEnum.ResistKnockBack, new BaseElement(rs.getInt("KnockBackResist")));
        this.baseContainer.put(StatEnum.ResistKnockDown, new BaseElement(rs.getInt("KnockDownResist")));
        this.baseContainer.put(StatEnum.ResistStandDown, new BaseElement(rs.getInt("StandDownResist")));
        this.baseContainer.put(StatEnum.ResistGuardCrush, new BaseElement(rs.getInt("GuardCrushResist")));
        this.baseContainer.put(StatEnum.ResistRigid, new BaseElement(rs.getInt("RigidResist")));
        this.baseContainer.put(StatEnum.ResistBound, new BaseElement(rs.getInt("BoundResist")));
        this.baseContainer.put(StatEnum.SubResourcePoint, new BaseElement(rs.getInt("SubResourcePoint")));
    }
}
