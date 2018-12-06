package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMNotifyGuildInfo;
import com.bdoemu.core.network.sendable.SMRepurchaseItems;
import com.bdoemu.core.network.sendable.SMVariedItemCountInShop;
import com.bdoemu.gameserver.dataholders.LifeActionEXPData;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.creature.templates.CreatureFunctionT;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;

import java.util.Collections;
import java.util.List;

public class SellItemInShopEvent extends AItemEvent {
    private int slotIndex;
    private Npc npc;
    private long count;
    private Item item;
    private EItemStorageLocation dstStorageLocation;
    private boolean isTradeShop;

    public SellItemInShopEvent(final Player player, final EItemStorageLocation dstStorageLocation, final int slotIndex, final long count, final Npc npc, boolean isTradeShop) {
        super(player, player, player, EStringTable.eErrNoItemSellToShop, EStringTable.eErrNoItemSellToShop, npc.getRegionId());
        this.slotIndex = slotIndex;
        this.count = count;
        this.npc = npc;
        this.dstStorageLocation = dstStorageLocation;
        this.isTradeShop = isTradeShop;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        if (isTradeShop) {
            player.sendPacket(new SMVariedItemCountInShop(player, Collections.singletonList(item)));
            LifeActionEXPData.getInstance().reward(player, item.getItemId(), ELifeExpType.Trading);
            player.getObserveController().notifyObserver(EObserveType.trade, item.getItemId(), item.getTemplate().getSellPriceToNpc());
        } else {
            final List<Item> repurchaseList = this.playerBag.getRepurchaseList();
            if (repurchaseList.size() >= 10) {
                repurchaseList.remove(0);
            }
            this.player.getObserveController().notifyObserver(EObserveType.trade, this.item.getItemId(), this.item.getTemplate().getSellPriceToNpc());
            final Item rItem = new Item(this.item, this.count, this.item.getTemplate().getSellPriceToNpc() * 110L / 100L);
            repurchaseList.add(rItem);
            if (repurchaseList.size() >= 10) {
                this.player.sendPacket(new SMRepurchaseItems(repurchaseList, EPacketTaskType.Update, this.npc.getGameObjectId()));
            } else {
                this.player.sendPacket(new SMRepurchaseItems(Collections.singletonList(rItem), EPacketTaskType.Add, this.npc.getGameObjectId()));
            }

            if (this.dstStorageLocation.isGuildWarehouse()) {
                if (this.player.getGuild() != null)
                    this.player.getGuild().sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.USE_GUILD_FUNDS, this.player.getGuild(), this.player.getObjectId(), this.player.getFamily(), 1, this.item.getTemplate().getSellPriceToNpc() * 110L / 100L));
            }
        }
    }

    @Override
    public boolean canAct() {
        if (this.slotIndex < 2) {
            return false;
        }
        this.item = this.srcActor.getInventory().getItem(this.slotIndex);
        if (this.item == null) {
            return false;
        }
        final CreatureFunctionT functionTemplate = this.npc.getTemplate().getCreatureFunctionT();
        if (functionTemplate != null && !ConditionService.checkCondition(functionTemplate.getConditionForSellingToNpc(), this.player)) {
            return false;
        }
        if (this.item.getTemplate().getNeedContribute() != null) {
            return false;
        }
        this.decreaseItem(this.slotIndex, this.count, EItemStorageLocation.Inventory);
        if (this.dstStorageLocation.isWarehouse()) {
            this.addWHItem(new Item(1, (isTradeShop ? item.getTemplate().getOriginalPrice() : this.item.getTemplate().getSellPriceToNpc()) * this.count, 0));
        } else {
            this.addItem(1, (isTradeShop ? item.getTemplate().getOriginalPrice() : this.item.getTemplate().getSellPriceToNpc()) * this.count, 0);
        }
        return super.canAct();
    }
}
