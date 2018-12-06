package com.bdoemu.gameserver.model.items.templates;

import com.bdoemu.gameserver.model.items.enums.EEnchantType;
import com.bdoemu.gameserver.model.stats.elements.Element;
import com.bdoemu.gameserver.model.stats.elements.ItemElement;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;
import com.bdoemu.gameserver.utils.DiceUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ItemEnchantT {
    private int enchantLevel;
    private int socket;
    private int endurance;
    private int skillNo;
    private int blackCountForItemMarket;
    private int itemMarketAddedPrice;
    private int reduceMaxEnduranceAtFail;
    private int reduceMaxEnduranceAtPerfectEnchant;
    private int recoverMaxEndurance;
    private Integer reqEnchantItemId;
    private Long reqEnchantItemCount;
    private Long perfectEnchantItemCount;
    private HashMap<StatEnum, Element> stats;
    private Set<Integer> contentsGroupKey;
    private EEnchantType enchantType;
    private boolean isNotifyWorld;

    public ItemEnchantT(final ResultSet rs) throws SQLException {
        this.stats = new HashMap<>();
        this.contentsGroupKey = new HashSet<>();
        this.enchantLevel = rs.getInt("Enchant");
        if (rs.getString("NeedEnchantItemID") != null) {
            this.reqEnchantItemId = rs.getInt("NeedEnchantItemID");
        }
        if (rs.getString("NeedEnchantItemCount") != null) {
            this.reqEnchantItemCount = rs.getLong("NeedEnchantItemCount");
        }
        if (rs.getString("NeedPerfectEnchantItemCount") != null) {
            this.perfectEnchantItemCount = rs.getLong("NeedPerfectEnchantItemCount");
        }
        this.blackCountForItemMarket = rs.getInt("BlackCountForItemMarket");
        this.reduceMaxEnduranceAtFail = rs.getInt("ReduceMaxEnduranceAtFail");
        this.reduceMaxEnduranceAtPerfectEnchant = rs.getInt("ReduceMaxEnduranceAtPerfectEnchant");
        this.recoverMaxEndurance = rs.getInt("RecoverMaxEndurance");
        this.socket = rs.getInt("Socket");
        this.endurance = rs.getInt("EnduranceLimit");
        final String[] contentGroupKeyData = rs.getString("ContentsGroupKey").split(",");
        for (final String contentGroupData : contentGroupKeyData)
            this.contentsGroupKey.add(Integer.parseInt(contentGroupData.trim()));
        this.stats.put(StatEnum.STR, new ItemElement(rs.getInt("VariedStr")));
        this.stats.put(StatEnum.VIT, new ItemElement(rs.getInt("VariedVit")));
        this.stats.put(StatEnum.WIS, new ItemElement(rs.getInt("VariedWis")));
        this.stats.put(StatEnum.INT, new ItemElement(rs.getInt("VariedInt")));
        this.stats.put(StatEnum.DEX, new ItemElement(rs.getInt("VariedDex")));
        this.stats.put(StatEnum.HP, new ItemElement(rs.getInt("VariedMaxHP")));
        this.stats.put(StatEnum.HPRegen, new ItemElement(rs.getInt("VariedRecovHP")));
        this.stats.put(StatEnum.MP, new ItemElement(rs.getInt("VariedMaxMP")));
        this.stats.put(StatEnum.MPRegen, new ItemElement(rs.getInt("VariedRecovMP")));
        this.stats.put(StatEnum.Weight, new ItemElement(rs.getInt("VariedPossessableWeight")));
        this.stats.put(StatEnum.CriticalRate, new ItemElement(rs.getInt("VariedCriticalRate"), false));
        this.stats.put(StatEnum.MoveSpeedRate, new ItemElement(rs.getInt("VariedMoveSpeedRate"), false));
        this.stats.put(StatEnum.AttackSpeedRate, new ItemElement(rs.getInt("VariedAttackSpeedRate"), false));
        this.stats.put(StatEnum.CastingSpeedRate, new ItemElement(rs.getInt("VariedCastingSpeedRate"), false));
        this.stats.put(StatEnum.CollectionLuck, new ItemElement(rs.getInt("VariedCollectionSpeedRate"), false));
        this.stats.put(StatEnum.FishingLuck, new ItemElement(rs.getInt("VariedFishingSpeedRate"), false));
        this.stats.put(StatEnum.DropRate, new ItemElement(rs.getInt("VariedDropItemRate"), false));
        this.stats.put(StatEnum.HumanAttackDamageStat, new ItemElement(rs.getInt("tribe0"), false));
        this.stats.put(StatEnum.AinAttackDamageStat, new ItemElement(rs.getInt("tribe1"), false));
        this.stats.put(StatEnum.EtcAttackDamageStat, new ItemElement(rs.getInt("tribe2"), false));
        this.stats.put(StatEnum.BossAttackDamageStat, new ItemElement(rs.getInt("tribe3"), false));
        this.stats.put(StatEnum.ReptileAttackDamageStat, new ItemElement(rs.getInt("tribe4"), false));
        this.stats.put(StatEnum.UntribeAttackDamageStat, new ItemElement(rs.getInt("tribe5"), false));
        this.stats.put(StatEnum.HuntingAttackDamageStat, new ItemElement(rs.getInt("tribe6"), false));
        this.stats.put(StatEnum.ElephantAttackDamageStat, new ItemElement(rs.getInt("tribe7"), false));
        this.stats.put(StatEnum.CannonAttackDamageStat, new ItemElement(rs.getInt("tribe8"), false));
        this.stats.put(StatEnum.SiegeAttackDamageStat, new ItemElement(rs.getInt("tribe9"), false));

        // Melee
        this.stats.put(StatEnum.AddedDDD, new ItemElement(rs.getInt("AddedDDD")));
        this.stats.put(StatEnum.DDD, new ItemElement(DiceUtils.getDiceRnd(rs.getString("DDD"))));
        this.stats.put(StatEnum.DHIT, new ItemElement(rs.getInt("DHIT")));
        this.stats.put(StatEnum.DDV, new ItemElement(rs.getInt("DDV")));
        this.stats.put(StatEnum.HDDV, new ItemElement(rs.getInt("HDDV")));
        this.stats.put(StatEnum.DPV, new ItemElement(rs.getInt("DPV")));
        this.stats.put(StatEnum.HDPV, new ItemElement(rs.getInt("HDPV")));

        // Ranged
        this.stats.put(StatEnum.AddedRDD, new ItemElement(rs.getInt("AddedRDD")));
        this.stats.put(StatEnum.RDD, new ItemElement(DiceUtils.getDiceRnd(rs.getString("RDD"))));
        this.stats.put(StatEnum.RHIT, new ItemElement(rs.getInt("RHIT")));
        this.stats.put(StatEnum.RDV, new ItemElement(rs.getInt("RDV")));
        this.stats.put(StatEnum.HRDV, new ItemElement(rs.getInt("HRDV")));
        this.stats.put(StatEnum.RPV, new ItemElement(rs.getInt("RPV")));
        this.stats.put(StatEnum.HRPV, new ItemElement(rs.getInt("HRPV")));
        this.stats.put(StatEnum.MHIT, new ItemElement(rs.getInt("MHIT")));

        // Magic
        this.stats.put(StatEnum.AddedMDD, new ItemElement(rs.getInt("AddedMDD")));
        this.stats.put(StatEnum.MDD, new ItemElement(DiceUtils.getDiceRnd(rs.getString("MDD"))));
        this.stats.put(StatEnum.MDV, new ItemElement(rs.getInt("MDV")));
        this.stats.put(StatEnum.HMDV, new ItemElement(rs.getInt("HMDV")));
        this.stats.put(StatEnum.MPV, new ItemElement(rs.getInt("MPV")));
        this.stats.put(StatEnum.HMPV, new ItemElement(rs.getInt("HMPV")));

        this.skillNo = rs.getInt("SkillNo");
        this.itemMarketAddedPrice = rs.getInt("ItemMarketAddedPrice");
        this.enchantType = EEnchantType.valueOf(rs.getInt("EnchantType"));
        this.isNotifyWorld = (rs.getInt("IsNotifyWorld") == 1);
    }

    public boolean isNotifyWorld() {
        return this.isNotifyWorld;
    }

    public EEnchantType getEnchantType() {
        return this.enchantType;
    }

    public int getItemMarketAddedPrice() {
        return this.itemMarketAddedPrice;
    }

    public int getBlackCountForItemMarket() {
        return this.blackCountForItemMarket;
    }

    public int getSkillNo() {
        return this.skillNo;
    }

    public int getRecoverMaxEndurance() {
        return this.recoverMaxEndurance;
    }

    public int getReduceMaxEnduranceAtPerfectEnchant() {
        return this.reduceMaxEnduranceAtPerfectEnchant;
    }

    public int getReduceMaxEnduranceAtFail() {
        return this.reduceMaxEnduranceAtFail;
    }

    public int getEndurance() {
        return this.endurance;
    }

    public int getEnchantLevel() {
        return this.enchantLevel;
    }

    public Integer getReqEnchantItemId() {
        return this.reqEnchantItemId;
    }

    public Long getReqEnchantItemCount() {
        return this.reqEnchantItemCount;
    }

    public Long getPerfectEnchantItemCount() {
        return this.perfectEnchantItemCount;
    }

    public int getSocket() {
        return this.socket;
    }

    public HashMap<StatEnum, Element> getStats() {
        return this.stats;
    }

    public Set<Integer> getContentsGroupKey() {
        return this.contentsGroupKey;
    }
}
