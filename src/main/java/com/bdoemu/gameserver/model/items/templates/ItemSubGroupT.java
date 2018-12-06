// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import com.bdoemu.commons.utils.DatabaseUtils;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;

public class ItemSubGroupT {
    private int itemId;
    private int enchantLevel;
    private int selectRate;
    private int minCount;
    private int maxCount;
    private int intimacyVariation;
    private int explorationPoint;
    private int expRate;
    private boolean isSellingItem;
    private boolean isBuyItem;
    private int maxSellCount;
    private int needAmountForStock;
    private int minRemainAmountForStock;
    private int maxRemainAmountForStock;
    private int variedAmountPerTickForStock;
    private int reverseAmountForStock;
    private int minSteadyAmountForStock;
    private int maxSteadyAmountForStock;
    private IAcceptConditionHandler[][] conditions;

    public ItemSubGroupT(final ResultSet rs, final ResultSetMetaData rsMeta) throws SQLException {
        this.minCount = 1;
        this.maxCount = 1;
        this.itemId = rs.getInt("ItemKey");
        this.enchantLevel = rs.getInt("EnchantLevel");
        this.intimacyVariation = rs.getInt("IntimacyVariation");
        this.explorationPoint = rs.getInt("ExplorationPoint");
        if (DatabaseUtils.hasColumn(rsMeta, "SelectRate_0")) {
            this.selectRate = rs.getInt("SelectRate_0");
            if (rs.getInt("MinCount_0") > 0) {
                this.minCount = rs.getInt("MinCount_0");
            }
            if (rs.getInt("MaxCount_0") > 0) {
                this.maxCount = rs.getInt("MaxCount_0");
            }
        } else {
            this.selectRate = rs.getInt("SelectRate");
            if (rs.getInt("MinCount") > 0) {
                this.minCount = rs.getInt("MinCount");
            }
            if (rs.getInt("MaxCount") > 0) {
                this.maxCount = rs.getInt("MaxCount");
            }
        }
        if (DatabaseUtils.hasColumn(rsMeta, "Condition")) {
            final String conditionData = rs.getString("Condition");
            if (conditionData != null) {
                this.conditions = ConditionService.getAcceptConditions(conditionData);
            }
            this.expRate = rs.getInt("GetExpRate");
            this.isSellingItem = (rs.getInt("isSellingItem") == 1);
            this.maxSellCount = rs.getInt("MaxSellCount");
            this.isBuyItem = (rs.getInt("isBuyItem") == 1);
            this.needAmountForStock = rs.getInt("NeedAmountForStock");
            this.minRemainAmountForStock = rs.getInt("MinRemainAmountForStock");
            this.maxRemainAmountForStock = rs.getInt("MaxRemainAmountForStock");
            this.variedAmountPerTickForStock = rs.getInt("VariedAmountPerTickForStock");
            this.reverseAmountForStock = rs.getInt("ReverseAmountForStock");
            this.minSteadyAmountForStock = rs.getInt("MinSteadyAmountForStock");
            this.maxSteadyAmountForStock = rs.getInt("MaxSteadyAmountForStock");
        }
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(final int itemId) {
        this.itemId = itemId;
    }

    public int getEnchantLevel() {
        return this.enchantLevel;
    }

    public void setEnchantLevel(final int enchantLevel) {
        this.enchantLevel = enchantLevel;
    }

    public int getSelectRate() {
        return this.selectRate;
    }

    public void setSelectRate(final int selectRate) {
        this.selectRate = selectRate;
    }

    public int getMinCount() {
        return this.minCount;
    }

    public void setMinCount(final int minCount) {
        this.minCount = minCount;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public void setMaxCount(final int maxCount) {
        this.maxCount = maxCount;
    }

    public int getIntimacyVariation() {
        return this.intimacyVariation;
    }

    public void setIntimacyVariation(final int intimacyVariation) {
        this.intimacyVariation = intimacyVariation;
    }

    public int getExplorationPoint() {
        return this.explorationPoint;
    }

    public void setExplorationPoint(final int explorationPoint) {
        this.explorationPoint = explorationPoint;
    }

    public int getExpRate() {
        return this.expRate;
    }

    public void setExpRate(final int expRate) {
        this.expRate = expRate;
    }

    public boolean isSellingItem() {
        return this.isSellingItem;
    }

    public void setSellingItem(final boolean isSellingItem) {
        this.isSellingItem = isSellingItem;
    }

    public boolean isBuyItem() {
        return this.isBuyItem;
    }

    public void setBuyItem(final boolean isBuyItem) {
        this.isBuyItem = isBuyItem;
    }

    public int getMaxSellCount() {
        return this.maxSellCount;
    }

    public void setMaxSellCount(final int maxSellCount) {
        this.maxSellCount = maxSellCount;
    }

    public int getNeedAmountForStock() {
        return this.needAmountForStock;
    }

    public void setNeedAmountForStock(final int needAmountForStock) {
        this.needAmountForStock = needAmountForStock;
    }

    public int getMinRemainAmountForStock() {
        return this.minRemainAmountForStock;
    }

    public void setMinRemainAmountForStock(final int minRemainAmountForStock) {
        this.minRemainAmountForStock = minRemainAmountForStock;
    }

    public int getMaxRemainAmountForStock() {
        return this.maxRemainAmountForStock;
    }

    public void setMaxRemainAmountForStock(final int maxRemainAmountForStock) {
        this.maxRemainAmountForStock = maxRemainAmountForStock;
    }

    public int getVariedAmountPerTickForStock() {
        return this.variedAmountPerTickForStock;
    }

    public void setVariedAmountPerTickForStock(final int variedAmountPerTickForStock) {
        this.variedAmountPerTickForStock = variedAmountPerTickForStock;
    }

    public int getReverseAmountForStock() {
        return this.reverseAmountForStock;
    }

    public void setReverseAmountForStock(final int reverseAmountForStock) {
        this.reverseAmountForStock = reverseAmountForStock;
    }

    public int getMinSteadyAmountForStock() {
        return this.minSteadyAmountForStock;
    }

    public void setMinSteadyAmountForStock(final int minSteadyAmountForStock) {
        this.minSteadyAmountForStock = minSteadyAmountForStock;
    }

    public int getMaxSteadyAmountForStock() {
        return this.maxSteadyAmountForStock;
    }

    public void setMaxSteadyAmountForStock(final int maxSteadyAmountForStock) {
        this.maxSteadyAmountForStock = maxSteadyAmountForStock;
    }

    public IAcceptConditionHandler[][] getConditions() {
        return this.conditions;
    }

    public void setConditions(final IAcceptConditionHandler[][] conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemSubGroupT)) {
            return false;
        }
        final ItemSubGroupT other = (ItemSubGroupT) o;
        return other.canEqual(this) && this.getItemId() == other.getItemId() && this.getEnchantLevel() == other.getEnchantLevel() && this.getSelectRate() == other.getSelectRate() && this.getMinCount() == other.getMinCount() && this.getMaxCount() == other.getMaxCount() && this.getIntimacyVariation() == other.getIntimacyVariation() && this.getExplorationPoint() == other.getExplorationPoint() && this.getExpRate() == other.getExpRate() && this.isSellingItem() == other.isSellingItem() && this.isBuyItem() == other.isBuyItem() && this.getMaxSellCount() == other.getMaxSellCount() && this.getNeedAmountForStock() == other.getNeedAmountForStock() && this.getMinRemainAmountForStock() == other.getMinRemainAmountForStock() && this.getMaxRemainAmountForStock() == other.getMaxRemainAmountForStock() && this.getVariedAmountPerTickForStock() == other.getVariedAmountPerTickForStock() && this.getReverseAmountForStock() == other.getReverseAmountForStock() && this.getMinSteadyAmountForStock() == other.getMinSteadyAmountForStock() && this.getMaxSteadyAmountForStock() == other.getMaxSteadyAmountForStock() && Arrays.deepEquals(this.getConditions(), other.getConditions());
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ItemSubGroupT;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getItemId();
        result = result * 59 + this.getEnchantLevel();
        result = result * 59 + this.getSelectRate();
        result = result * 59 + this.getMinCount();
        result = result * 59 + this.getMaxCount();
        result = result * 59 + this.getIntimacyVariation();
        result = result * 59 + this.getExplorationPoint();
        result = result * 59 + this.getExpRate();
        result = result * 59 + (this.isSellingItem() ? 79 : 97);
        result = result * 59 + (this.isBuyItem() ? 79 : 97);
        result = result * 59 + this.getMaxSellCount();
        result = result * 59 + this.getNeedAmountForStock();
        result = result * 59 + this.getMinRemainAmountForStock();
        result = result * 59 + this.getMaxRemainAmountForStock();
        result = result * 59 + this.getVariedAmountPerTickForStock();
        result = result * 59 + this.getReverseAmountForStock();
        result = result * 59 + this.getMinSteadyAmountForStock();
        result = result * 59 + this.getMaxSteadyAmountForStock();
        result = result * 59 + Arrays.deepHashCode(this.getConditions());
        return result;
    }

    @Override
    public String toString() {
        return "ItemSubGroupT(itemId=" + this.getItemId() + ", enchantLevel=" + this.getEnchantLevel() + ", selectRate=" + this.getSelectRate() + ", minCount=" + this.getMinCount() + ", maxCount=" + this.getMaxCount() + ", intimacyVariation=" + this.getIntimacyVariation() + ", explorationPoint=" + this.getExplorationPoint() + ", expRate=" + this.getExpRate() + ", isSellingItem=" + this.isSellingItem() + ", isBuyItem=" + this.isBuyItem() + ", maxSellCount=" + this.getMaxSellCount() + ", needAmountForStock=" + this.getNeedAmountForStock() + ", minRemainAmountForStock=" + this.getMinRemainAmountForStock() + ", maxRemainAmountForStock=" + this.getMaxRemainAmountForStock() + ", variedAmountPerTickForStock=" + this.getVariedAmountPerTickForStock() + ", reverseAmountForStock=" + this.getReverseAmountForStock() + ", minSteadyAmountForStock=" + this.getMinSteadyAmountForStock() + ", maxSteadyAmountForStock=" + this.getMaxSteadyAmountForStock() + ", conditions=" + Arrays.deepToString(this.getConditions()) + ")";
    }
}
