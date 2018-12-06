// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import com.bdoemu.commons.utils.DatabaseUtils;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;
import com.bdoemu.gameserver.model.creature.collect.enums.ECollectToolType;
import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import com.bdoemu.gameserver.model.creature.player.enums.EJewelEquipType;
import com.bdoemu.gameserver.model.creature.player.enums.EquipType;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantKind;
import com.bdoemu.gameserver.model.items.enums.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class ItemTemplate {
    private Integer contentsEventParam1;
    private Integer contentsEventParam2;
    private Integer repairPrice;
    private Integer characterKey;
    private int itemId;
    private int endurance;
    private int skillId;
    private int expirationPeriod;
    private int itemActionNumber;
    private int weight;
    private long sellPriceToNpc;
    private long originalPrice;
    private long collectTime;
    private EItemClassify itemClassify;
    private EItemType itemType;
    private EquipType equipType;
    private EItemGradeType gradeType;
    private EJewelEquipType jewelEquipType;
    private int[] jewelSubtractionNeedItem;
    private Long jewelSubtractionNeedItemCount;
    private Set<Integer> contentsGroupKey;
    private EContentsEventType contentsEventType;
    private HashMap<Integer, ItemEnchantT> enchantTemplates;
    private List<EquipType> occupiedEquipNoList;
    private boolean isDropable;
    private boolean isStack;
    private boolean isRemovable;
    private boolean isCash;
    private boolean isDyeable;
    private boolean doApplyDirectly;
    private boolean isNotifyWorld;
    private boolean isUserVested;
    private boolean isTargetAlive;
    private boolean isForTrade;
    private boolean isPersonalTrade;
    private int minLevel;
    private int maxLevel;
    private int[] classPermissions;
    private int[] servantPermissions;
    private Integer needContribute;
    private int basePriceForItemMarket;
    private int minestPercentForItemMarket;
    private int maxestPercentForItemMarket;
    private int maxRegisterCountForItemMarket;
    private String itemName;
    private EItemVestedType vestedType;
    private ECollectToolType collectToolType;
    private ELifeExpType lifeExpType;
    private int lifeMinLevel;
    private long expirationPeriodParam2;
    private IAcceptConditionHandler[][] useConditions;

    public ItemTemplate(final ResultSet rs, final ResultSetMetaData rsMeta, final int itemId, final HashMap<Integer, ItemEnchantT> enchantTemplates) throws SQLException {
        this.contentsGroupKey = new HashSet<Integer>();
        this.classPermissions = new int[32];
        this.servantPermissions = new int[16];
        this.itemId = itemId;
        this.itemName = rs.getString("ItemName");
        this.endurance = rs.getInt("EnduranceLimit");
        this.equipType = EquipType.valueOf(rs.getInt("EquipType"));
        if (rs.getString("ItemClassify") != null) {
            this.itemClassify = EItemClassify.valueOf(rs.getInt("ItemClassify"));
        }
        this.gradeType = EItemGradeType.valueOf(rs.getInt("GradeType"));
        this.itemType = EItemType.valueOf(rs.getInt("ItemType"));
        this.vestedType = EItemVestedType.valueOf(rs.getInt("VestedType"));
        this.isUserVested = (rs.getByte("isUserVested") == 1);
        this.weight = rs.getInt("Weight");
        final String occupiedEquipNo = rs.getString("OccupiedEquipNo");
        if (occupiedEquipNo != null) {
            this.occupiedEquipNoList = new ArrayList<EquipType>();
            for (final String slotId : occupiedEquipNo.split(",")) {
                this.occupiedEquipNoList.add(EquipType.valueOf(Integer.parseInt(slotId.trim())));
            }
        }
        this.expirationPeriod = rs.getInt("ExpirationPeriod");
        if (rs.getString("RepairPrice") != null) {
            this.repairPrice = rs.getInt("RepairPrice");
        }
        this.enchantTemplates = enchantTemplates;
        this.doApplyDirectly = (rs.getByte("DoApplyDirectly") == 1);
        this.isStack = (rs.getInt("IsStack") == 1);
        this.isRemovable = (rs.getInt("IsRemovable") == 1);
        if (rs.getString("SkillNo") != null) {
            this.skillId = rs.getInt("SkillNo");
        }
        this.sellPriceToNpc = rs.getLong("SellPriceToNpc");
        this.originalPrice = rs.getLong("OriginalPrice");
        if (rs.getString("Character_Key") != null) {
            this.characterKey = rs.getInt("Character_Key");
        }
        this.minLevel = rs.getInt("MinLevel");
        this.maxLevel = rs.getInt("MaxLevel");
        this.isCash = (rs.getInt("IsCash") == 1);
        this.isDyeable = (rs.getInt("IsDyeable") == 1);
        this.isDropable = (rs.getInt("IsDropable") == 1);
        this.isTargetAlive = (rs.getInt("IsTargetAlive") == 1);
        this.isForTrade = (rs.getInt("IsForTrade") == 1);
        this.isPersonalTrade = (rs.getInt("IsPersonalTrade") == 1);
        if (rs.getString("JewelEquipType") != null) {
            this.jewelEquipType = EJewelEquipType.valueOf(rs.getInt("JewelEquipType"));
        }
        final String jewelSubtractionNeedItemData = rs.getString("JewelSubtractionNeedItem");
        if (jewelSubtractionNeedItemData != null) {
            final String[] subtractionNeedItems = jewelSubtractionNeedItemData.split(",");
            this.jewelSubtractionNeedItem = new int[subtractionNeedItems.length];
            for (int index = 0; index < subtractionNeedItems.length; ++index) {
                this.jewelSubtractionNeedItem[index] = Integer.parseInt(subtractionNeedItems[index]);
            }
        }
        if (rs.getString("JewelSubtractionNeedItemCount") != null) {
            this.jewelSubtractionNeedItemCount = rs.getLong("JewelSubtractionNeedItemCount");
        }
        if (rs.getString("ContentsEventType") != null) {
            this.contentsEventType = EContentsEventType.valueOf(rs.getInt("ContentsEventType"));
        }
        if (rs.getString("ContentsEventParam1") != null) {
            this.contentsEventParam1 = rs.getInt("ContentsEventParam1");
        }
        if (rs.getString("ContentsEventParam2") != null) {
            this.contentsEventParam2 = rs.getInt("ContentsEventParam2");
        }
        final String[] contentGroupKeyData = rs.getString("ContentsGroupKey").split(",");
        for (final String contentGroupData : contentGroupKeyData) {
            this.contentsGroupKey.add(Integer.parseInt(contentGroupData.trim()));
        }
        for (int i = 0; i < this.classPermissions.length; ++i) {
            this.classPermissions[i] = rs.getInt(String.valueOf(i));
        }
        for (int i = 0; i < this.servantPermissions.length; ++i) {
            if (DatabaseUtils.hasColumn(rsMeta, "Pet_" + i)) {
                this.servantPermissions[i] = rs.getInt("Pet_" + i);
            }
        }
        if (rs.getString("NeedContribute") != null) {
            this.needContribute = rs.getInt("NeedContribute");
        }
        this.isNotifyWorld = (rs.getInt("IsNotifyWorld") == 1);
        this.collectTime = rs.getLong("CollectTime");
        this.basePriceForItemMarket = rs.getInt("basePriceForItemMarket");
        this.minestPercentForItemMarket = rs.getInt("minestPercentForItemMarket");
        this.maxestPercentForItemMarket = rs.getInt("maxestPercentForItemMarket");
        this.maxRegisterCountForItemMarket = rs.getInt("maxRegisterCountForItemMarket");
        if (rs.getString("CollectToolType") != null) {
            this.collectToolType = ECollectToolType.valueOf(rs.getInt("collectToolType"));
        }
        this.lifeExpType = ELifeExpType.valueOf(rs.getInt("LifeExpType"));
        this.lifeMinLevel = rs.getInt("LifeMinLevel");
        this.itemActionNumber = rs.getInt("ItemActionNumber");
        this.expirationPeriodParam2 = rs.getLong("ExpirationPeriod Param2");
        final String useCondition = rs.getString("UseCondition");
        if (useCondition != null) {
            this.useConditions = ConditionService.getAcceptConditions(useCondition);
        }
    }

    public int getWeight() {
        return this.weight;
    }

    public boolean isDropable() {
        return this.isDropable;
    }

    public long getExpirationPeriodParam2() {
        return this.expirationPeriodParam2;
    }

    public boolean isPersonalTrade() {
        return this.isPersonalTrade;
    }

    public boolean isForTrade() {
        return this.isForTrade;
    }

    public int getItemActionNumber() {
        return this.itemActionNumber;
    }

    public boolean isTargetAlive() {
        return this.isTargetAlive;
    }

    public long getCollectTime() {
        return this.collectTime;
    }

    public ECollectToolType getCollectToolType() {
        return this.collectToolType;
    }

    public boolean isUserVested() {
        return this.isUserVested;
    }

    public EItemVestedType getVestedType() {
        return this.vestedType;
    }

    public String getItemName() {
        return this.itemName;
    }

    public boolean isNotifyWorld() {
        return this.isNotifyWorld;
    }

    public Integer getNeedContribute() {
        return this.needContribute;
    }

    public List<EquipType> getOccupiedEquipNoList() {
        return this.occupiedEquipNoList;
    }

    public int getItemId() {
        return this.itemId;
    }

    public HashMap<Integer, ItemEnchantT> getEnchantTemplates() {
        return this.enchantTemplates;
    }

    public int getEnchantSize() {
        return (this.enchantTemplates == null) ? 1 : this.enchantTemplates.size();
    }

    public ItemEnchantT getEnchantTemplate(final int enchantLevel) {
        return (this.enchantTemplates == null) ? null : this.enchantTemplates.get(enchantLevel);
    }

    public int getBlackCountForItemMarket(final int enchantLevel) {
        /*if (enchantTemplates != null) {
            Integer maxKey       = Collections.max(enchantTemplates.keySet());
            ItemEnchantT baseTpl = getEnchantTemplate(maxKey);

            if (baseTpl != null) {
                int count = baseTpl.getBlackCountForItemMarket();

                for (int i = maxKey - 1; i >= enchantLevel; --i) {
                    ItemEnchantT tpl = getEnchantTemplate(i);

                    if (tpl != null) {
                        count -= tpl.getBlackCountForItemMarket();
                    }
                }
                return count > 0 ? count : 0;
            }
        }
        return 0;*/
        int count = 0;
        for (int i = 0; i <= enchantLevel; ++i) {
            final ItemEnchantT itemEnchantT = this.getEnchantTemplate(i);
            if (itemEnchantT != null) {
                count += itemEnchantT.getBlackCountForItemMarket();
            }
        }
        return count;
    }

    public int getExpirationPeriod() {
        return this.expirationPeriod;
    }

    public EItemGradeType getGradeType() {
        return this.gradeType;
    }

    public boolean isDoApplyDirectly() {
        return this.doApplyDirectly;
    }

    public int getCharacterKey() {
        return this.characterKey;
    }

    public EContentsEventType getContentsEventType() {
        return this.contentsEventType;
    }

    public EItemClassify getItemClassify() {
        return this.itemClassify;
    }

    public EquipType getEquipType() {
        return this.equipType;
    }

    public EItemType getItemType() {
        return this.itemType;
    }

    public int getEndurance() {
        return this.endurance;
    }

    public Integer getRepairPrice() {
        return this.repairPrice;
    }

    public boolean isStack() {
        return this.isStack;
    }

    public boolean isRemovable() {
        return this.isRemovable;
    }

    public boolean isCash() {
        return this.isCash;
    }

    public int getSkillId() {
        return this.skillId;
    }

    public long getSellPriceToNpc() {
        return this.sellPriceToNpc;
    }

    public long getOriginalPrice() {
        return this.originalPrice;
    }

    public boolean isDyeable() {
        return this.isDyeable;
    }

    public int[] getJewelSubtractionNeedItem() {
        return this.jewelSubtractionNeedItem;
    }

    public Long getJewelSubtractionNeedItemCount() {
        return this.jewelSubtractionNeedItemCount;
    }

    public EJewelEquipType getJewelEquipType() {
        return this.jewelEquipType;
    }

    public Integer getContentsEventParam1() {
        return this.contentsEventParam1;
    }

    public Integer getContentsEventParam2() {
        return this.contentsEventParam2;
    }

    public int getMinLevel() {
        return this.minLevel;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public boolean canUse(final EClassType classType) {
        return this.classPermissions[classType.getId()] == 1;
    }

    public boolean canUse(final EServantKind servantKind) {
        return this.servantPermissions[servantKind.getId()] == 1;
    }

    public int getMinestPercentForItemMarket() {
        return this.minestPercentForItemMarket;
    }

    public int getMaxRegisterCountForItemMarket() {
        return this.maxRegisterCountForItemMarket;
    }

    public int getMaxestPercentForItemMarket() {
        return this.maxestPercentForItemMarket;
    }

    public int getBasePriceForItemMarket() {
        return this.basePriceForItemMarket;
    }

    public ELifeExpType getLifeExpType() {
        return this.lifeExpType;
    }

    public int getLifeMinLevel() {
        return this.lifeMinLevel;
    }

    public Set<Integer> getContentsGroupKey() {
        return this.contentsGroupKey;
    }

    public IAcceptConditionHandler[][] getUseConditions() {
        return this.useConditions;
    }
}
