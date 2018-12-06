package com.bdoemu.gameserver.model.creature.templates;

import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;
import com.bdoemu.gameserver.model.creature.npc.enums.EShopType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreatureFunctionT {
    private int creatureId;
    // Normal
    private int buyItemMainGroup;
    private int sellItemMainGroup;
    private int tradeItemMainGroup;
    // Guild
    private int buyGuildItemMainGroup;
    private int sellGuildItemMainGroup;
    private boolean isGuild;

    private boolean isRepair;
    private boolean isWarehouse;
    private boolean isAwakenSkill;
    private EShopType shopType;
    private Integer territoryKeyForItemMarket;
    private Integer auctionKey;
    private Integer matingKey;
    private IAcceptConditionHandler[][] conditionForBuyingFromNpc;
    private IAcceptConditionHandler[][] conditionForTrading;
    private IAcceptConditionHandler[][] conditionForSellingToNpc;

    public CreatureFunctionT(final ResultSet rs) throws SQLException {
        this.creatureId = rs.getInt("CharacterKey");

        // Normal
        this.buyItemMainGroup = rs.getInt("ItemMainGroupKeyForBuyingFromNpc");
        this.sellItemMainGroup = rs.getInt("ItemMainGroupKeyForSellingToNpc");
        this.tradeItemMainGroup = rs.getInt("ItemMainGroupKeyForTrading");
        // Guild
        this.buyGuildItemMainGroup = rs.getInt("ItemMainGroupKeyForBuyingFromGuildShopNpc");
        this.sellGuildItemMainGroup = rs.getInt("ItemMainGroupKeyForSellingToGuildShopNpc");

        this.conditionForBuyingFromNpc = ConditionService.getAcceptConditions(rs.getString("ConditionForBuyingFromNpc"));
        this.conditionForSellingToNpc = ConditionService.getAcceptConditions(rs.getString("ConditionForSellingToNpc"));
        this.conditionForTrading = ConditionService.getAcceptConditions(rs.getString("ConditionForTrading"));
        this.isGuild = (rs.getByte("isGuild") == 1);
        this.isRepair = (rs.getByte("isRepair") == 1);
        this.isWarehouse = (rs.getByte("isWarehouse") == 1);
        this.isAwakenSkill = (rs.getByte("isAwakenSkill") == 1);
        this.shopType = EShopType.valueOf(rs.getInt("ShopType"));
        if (rs.getString("TerritoryKeyForItemMarket") != null) {
            this.territoryKeyForItemMarket = rs.getInt("TerritoryKeyForItemMarket");
        }
        if (rs.getString("MatingKey") != null) {
            this.matingKey = rs.getInt("MatingKey");
        }
        if (rs.getString("AuctionKey") != null) {
            this.auctionKey = rs.getInt("AuctionKey");
        }
    }

    public Integer getMatingKey() {
        return this.matingKey;
    }

    public Integer getAuctionKey() {
        return this.auctionKey;
    }

    public Integer getTerritoryKeyForItemMarket() {
        return this.territoryKeyForItemMarket;
    }

    public EShopType getShopType() {
        return this.shopType;
    }

    public boolean isWarehouse() {
        return this.isWarehouse;
    }

    public boolean isRepair() {
        return this.isRepair;
    }

    public boolean isGuild() {
        return this.isGuild;
    }

    public boolean isAwakenSkill() {
        return this.isAwakenSkill;
    }

    public int getCreatureId() {
        return this.creatureId;
    }

    public int getBuyItemMainGroup() {
        return isGuild ? this.buyGuildItemMainGroup : this.buyItemMainGroup;
    }

    public int getSellItemMainGroup() {
        return isGuild ? this.sellGuildItemMainGroup : this.sellItemMainGroup;
    }

    public int getTradeItemMainGroup() {
        return this.tradeItemMainGroup;
    }

    public IAcceptConditionHandler[][] getConditionForBuyingFromNpc() {
        return this.conditionForBuyingFromNpc;
    }

    public IAcceptConditionHandler[][] getConditionForSellingToNpc() {
        return this.conditionForSellingToNpc;
    }

    public IAcceptConditionHandler[][] getConditionForTrading() {
        return this.conditionForTrading;
    }
}
