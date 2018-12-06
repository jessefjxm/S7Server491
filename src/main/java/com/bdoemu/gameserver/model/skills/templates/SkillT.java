// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.templates;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import com.bdoemu.gameserver.model.creature.player.enums.EquipType;
import com.bdoemu.gameserver.model.skills.enums.ESkillAwakeningType;
import com.bdoemu.gameserver.model.skills.enums.ESkillOwnerType;
import com.bdoemu.gameserver.model.skills.enums.ESkillType;
import com.bdoemu.gameserver.model.stats.elements.Element;
import com.bdoemu.gameserver.model.stats.elements.SkillElement;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;
import com.bdoemu.gameserver.utils.DiceUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SkillT {
    private final List<Integer> buffs;
    private int skillId;
    private int level;
    private int requireSP;
    private int requireMP;
    private int uiDisplayType;
    private int needSkillNo0ForLearning;
    private int needSkillNo1ForLearning;
    private ESkillType skillType;
    private ESkillOwnerType skillOwnerType;
    private int reuseGroup;
    private int reuseCycle;
    private int applyNumber;
    private int skillPointForLearning;
    private int pcLevelForLearning;
    private int needItemId;
    private int needItemCount;
    private boolean doCheckHit;
    private int variedHit;
    private int maxTargetsCount;
    private boolean usableInCoolTime;
    private boolean isPromptForLearning;
    private boolean isSettableQuickSlot;
    private String actionName;
    private EquipType requireEquipType;
    private int weaponEnduranceDecreaseRate;
    private int[] classPermissions;
    private HashMap<StatEnum, Element> stats;
    private Set<Integer> contentsGroupKey;
    private ESkillAwakeningType skillAwakeningType;

    public SkillT(final ResultSet rs) throws SQLException {
        this.buffs = new ArrayList<Integer>();
        this.classPermissions = new int[32];
        this.stats = new HashMap<StatEnum, Element>();
        this.contentsGroupKey = new HashSet<Integer>();
        this.skillId = rs.getInt("skill_id");
        this.level = rs.getInt("SkillLevel");
        this.actionName = rs.getString("action_name");
        this.isPromptForLearning = (rs.getInt("IsPrompt_ForLearning") == 1);
        this.isSettableQuickSlot = (rs.getInt("IsSettableQuickSlot") == 1);
        this.skillPointForLearning = rs.getInt("SkillPoint_ForLearning");
        this.pcLevelForLearning = rs.getInt("PcLevel_ForLearning");
        this.requireSP = rs.getInt("RequireSP");
        this.requireMP = rs.getInt("RequireMP");
        this.skillType = ESkillType.values()[rs.getInt("SkillType")];
        this.skillOwnerType = ESkillOwnerType.values()[rs.getInt("SkillOwnerType")];
        this.uiDisplayType = rs.getInt("UiDisplayType");
        this.reuseGroup = rs.getInt("ReuseGroup");
        this.reuseCycle = rs.getInt("ReuseCycle");
        for (int i = 0; i < 10; ++i) {
            final String param = rs.getString("Buff" + i);
            if (param != null) {
                this.buffs.add(rs.getInt("Buff" + i));
            }
        }
        this.requireEquipType = EquipType.valueOf(rs.getInt("RequireEquipType"));
        this.weaponEnduranceDecreaseRate = rs.getInt("WeaponEnduranceDecreseRate");
        for (int i = 0; i < this.classPermissions.length; ++i) {
            this.classPermissions[i] = rs.getInt(String.valueOf(i));
        }
        this.needSkillNo0ForLearning = rs.getInt("NeedSkillNo0_ForLearning");
        this.needSkillNo1ForLearning = rs.getInt("NeedSkillNo1_ForLearning");
        this.needItemId = rs.getInt("NeedItemID");
        this.needItemCount = rs.getInt("NeedItemCount");
        this.applyNumber = rs.getInt("ApplyNumber");
        this.doCheckHit = (rs.getInt("DoCheckHit") == 1);
        this.variedHit = rs.getInt("VariedHit");
        this.maxTargetsCount = rs.getInt("ApplyNumber");
        this.usableInCoolTime = (rs.getInt("UsableInCoolTime") == 1);
        final String[] split;
        final String[] contentGroupKeyData = split = rs.getString("ContentsGroupKey").split(",");
        for (final String contentGroupData : split) {
            this.contentsGroupKey.add(Integer.parseInt(contentGroupData.trim()));
        }
        this.stats.put(StatEnum.DDD, new SkillElement(DiceUtils.getDiceRnd(rs.getString("DDD"))));
        this.stats.put(StatEnum.DHIT, new SkillElement(rs.getInt("DHIT")));
        this.stats.put(StatEnum.DDV, new SkillElement(rs.getInt("DDV")));
        this.stats.put(StatEnum.HDDV, new SkillElement(rs.getInt("HDDV")));
        this.stats.put(StatEnum.DPV, new SkillElement(rs.getInt("DPV")));
        this.stats.put(StatEnum.HDPV, new SkillElement(rs.getInt("HDPV")));
        this.stats.put(StatEnum.RDD, new SkillElement(DiceUtils.getDiceRnd(rs.getString("RDD"))));
        this.stats.put(StatEnum.RHIT, new SkillElement(rs.getInt("RHIT")));
        this.stats.put(StatEnum.RDV, new SkillElement(rs.getInt("RDV")));
        this.stats.put(StatEnum.HRDV, new SkillElement(rs.getInt("HRDV")));
        this.stats.put(StatEnum.RPV, new SkillElement(rs.getInt("RPV")));
        this.stats.put(StatEnum.HRPV, new SkillElement(rs.getInt("HRPV")));
        this.stats.put(StatEnum.MDD, new SkillElement(DiceUtils.getDiceRnd(rs.getString("MDD"))));
        this.stats.put(StatEnum.MHIT, new SkillElement(rs.getInt("MHIT")));
        this.stats.put(StatEnum.MDV, new SkillElement(rs.getInt("MDV")));
        this.stats.put(StatEnum.HMDV, new SkillElement(rs.getInt("HMDV")));
        this.stats.put(StatEnum.MPV, new SkillElement(rs.getInt("MPV")));
        this.stats.put(StatEnum.HMPV, new SkillElement(rs.getInt("HMPV")));
        this.skillAwakeningType = ESkillAwakeningType.values()[rs.getInt("skillAwakeningType")];
    }

    public ESkillAwakeningType getSkillAwakeningType() {
        return this.skillAwakeningType;
    }

    public ESkillOwnerType getSkillOwnerType() {
        return this.skillOwnerType;
    }

    public int getApplyNumber() {
        return this.applyNumber;
    }

    public EquipType getRequireEquipType() {
        return this.requireEquipType;
    }

    public int getReuseGroup() {
        return this.reuseGroup;
    }

    public int getReuseCycle() {
        return this.reuseCycle;
    }

    public List<Integer> getBuffs() {
        return this.buffs;
    }

    public String getActionName() {
        return this.actionName;
    }

    public int getSkillPointForLearning() {
        return this.skillPointForLearning;
    }

    public boolean isPromptForLearning() {
        return this.isPromptForLearning;
    }

    public int getPcLevelForLearning() {
        return this.pcLevelForLearning;
    }

    public int getSkillId() {
        return this.skillId;
    }

    public int getLevel() {
        return this.level;
    }

    public ESkillType getSkillType() {
        return this.skillType;
    }

    public int getRequireSP() {
        return this.requireSP;
    }

    public int getRequireMP() {
        return this.requireMP;
    }

    public int getUiDisplayType() {
        return this.uiDisplayType;
    }

    public boolean canUse(final EClassType classType) {
        return this.classPermissions[classType.getId()] == 1;
    }

    public boolean canLearn(final Player player) {
        return this.isPromptForLearning() && (this.needSkillNo0ForLearning <= 0 || player.getSkillList().containsSkill(this.needSkillNo0ForLearning)) && (this.needSkillNo1ForLearning <= 0 || player.getSkillList().containsSkill(this.needSkillNo1ForLearning)) && this.canUse(player.getClassType()) && player.getLevel() >= this.getPcLevelForLearning();
    }

    public boolean isSettableQuickSlot() {
        return this.isSettableQuickSlot;
    }

    public int getNeedSkillNo0ForLearning() {
        return this.needSkillNo0ForLearning;
    }

    public int getNeedSkillNo1ForLearning() {
        return this.needSkillNo1ForLearning;
    }

    public HashMap<StatEnum, Element> getStats() {
        return this.stats;
    }

    public int getNeedItemId() {
        return this.needItemId;
    }

    public int getNeedItemCount() {
        return this.needItemCount;
    }

    public int getVariedHit() {
        return this.variedHit;
    }

    public boolean isDoCheckHit() {
        return this.doCheckHit;
    }

    public boolean isUsableInCoolTime() {
        return this.usableInCoolTime;
    }

    public int getWeaponEnduranceDecreaseRate() {
        return this.weaponEnduranceDecreaseRate;
    }

    public int getMaxTargetsCount() {
        return this.maxTargetsCount;
    }

    public Set<Integer> getContentsGroupKey() {
        return this.contentsGroupKey;
    }
}
