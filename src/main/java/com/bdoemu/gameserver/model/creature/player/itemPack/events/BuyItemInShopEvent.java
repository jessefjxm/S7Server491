package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMNotifyGuildInfo;
import com.bdoemu.core.network.sendable.SMVariedItemCountInShop;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.dataholders.ItemMainGroupData;
import com.bdoemu.gameserver.dataholders.LifeActionEXPData;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.enums.EShopType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.ShopItem;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemMainGroupT;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.items.templates.MainItemSubGroupT;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;

import java.util.Collections;

public class BuyItemInShopEvent extends AItemEvent {
    private int itemId;
    private int enchantLevel;
    private long count;
    private ItemSubGroupT subGroupT;
    private Npc npc;
    private EItemStorageLocation srcLocation;
    private long buyPrice;
    private Item item;

    public BuyItemInShopEvent(final Player player, final EItemStorageLocation srcLocation, final int itemId, final int enchantLevel, final long count, final Npc npc) {
        super(player, player, player, EStringTable.eErrNoItemBuyFromShop, EStringTable.eErrNoItemBuyFromShop, npc.getRegionId());
        this.srcLocation = srcLocation;
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.count = count;
        this.npc = npc;
        this.buyPrice = 0;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        final Integer intimacyVariation = this.subGroupT.getIntimacyVariation();
        if (intimacyVariation != 0) {
            this.player.getIntimacyHandler().updateIntimacy(this.npc.getCreatureId(), this.npc.getGameObjectId(), intimacyVariation);
        }

        if (this.srcLocation.isGuildWarehouse()) {
            if (this.player.getGuild() != null)
                this.player.getGuild().sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.USE_GUILD_FUNDS, this.player.getGuild(), this.player.getObjectId(), this.player.getFamily(), 0, this.buyPrice));
        }

        if (npc.getTemplate().getCreatureFunctionT().getShopType() == EShopType.Shop && item != null) {
            player.sendPacket(new SMVariedItemCountInShop(player, Collections.singletonList(item)));
            LifeActionEXPData.getInstance().reward(player, itemId, ELifeExpType.Trading);
        }
    }

    @Override
    public boolean canAct() {
        if (!this.srcLocation.isInventory() && !this.srcLocation.isWarehouse() && !this.srcLocation.isGuildWarehouse())
            return false;

        if (this.srcLocation.isGuildWarehouse() && this.player.getGuild() == null)
            return false;

        final CreatureFunctionT functionTemplate = this.npc.getTemplate().getCreatureFunctionT();
        if (functionTemplate == null) {
            return false;
        }
        if (!ConditionService.checkCondition(functionTemplate.getConditionForBuyingFromNpc(), this.player)) {
            return false;
        }
        if (functionTemplate.getShopType().isRandomShop() || functionTemplate.getShopType().isRandomNpcWorkerShop()) {
            final ShopItem shopItem = this.playerBag.getRandomShopItem();
            if (shopItem == null || shopItem.getItemSubGroup().getItemId() != this.itemId || shopItem.getItemSubGroup().getEnchantLevel() != this.enchantLevel || this.count != 1L) {
                return false;
            }
        }
        final ItemMainGroupT itemMainGroupT = ItemMainGroupData.getInstance().getTemplate(functionTemplate.getBuyItemMainGroup());
        if (itemMainGroupT == null) {
            return false;
        }
        for (final MainItemSubGroupT subGroup : itemMainGroupT.getMainItemSubGroups()) {
            if (subGroup != null) {
                final IAcceptConditionHandler[][] handlers = subGroup.getConditions();
                if (handlers != null && !ConditionService.checkCondition(handlers, this.player))
                    continue;

                this.subGroupT = subGroup.getItemSubGroup(this.itemId, this.enchantLevel);
                if (this.subGroupT != null)
                    break;
            }
        }
        if (this.subGroupT == null) {
            return false;
        }
        final ItemTemplate template = ItemData.getInstance().getItemTemplate(this.subGroupT.getItemId());
        if (template == null) {
            return false;
        } else {
            if (npc.getTemplate().getCreatureFunctionT().getShopType() == EShopType.Shop)
                item = new Item(template, count, 0);
        }
        this.buyPrice = template.getOriginalPrice() * this.count;
        this.addItem(template, this.count, this.subGroupT.getEnchantLevel());
        this.decreaseItem(0, template.getOriginalPrice() * this.count, this.srcLocation);
        return super.canAct();
    }
}