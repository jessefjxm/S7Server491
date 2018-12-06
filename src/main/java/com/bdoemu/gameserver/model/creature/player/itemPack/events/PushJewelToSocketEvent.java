// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.core.network.sendable.SMPushJewelToSocket;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.enums.EJewelEquipType;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerBag;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.Jewel;
import com.bdoemu.gameserver.model.items.enums.EItemClassify;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemEnchantT;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;

public class PushJewelToSocketEvent implements IBagEvent {
    private final PlayerBag playerBag;
    private Player player;
    private EItemStorageLocation jewelStorageType;
    private EItemStorageLocation itemStorageType;
    private int jewelIndex;
    private int jewelSlot;
    private int itemSlot;
    private Item item;
    private Item jewelItem;
    private ItemPack itemPack;
    private ItemPack jewelPack;

    public PushJewelToSocketEvent(final Player player, final int jewelIndex, final EItemStorageLocation jewelStorageType, final int jewelSlot, final EItemStorageLocation itemStorageType, final int itemSlot) {
        this.player = player;
        this.jewelIndex = jewelIndex;
        this.jewelStorageType = jewelStorageType;
        this.itemStorageType = itemStorageType;
        this.jewelSlot = jewelSlot;
        this.itemSlot = itemSlot;
        this.playerBag = player.getPlayerBag();
    }

    @Override
    public void onEvent() {
        this.jewelItem.addCount(-1L);
        if (this.jewelItem.getCount() <= 0L) {
            this.jewelPack.removeItem(this.jewelSlot);
        }
        this.player.getGameStats().getWeight().addWeight(-this.jewelItem.getTemplate().getWeight());
        this.item.setJewel(this.jewelIndex, new Jewel(this.jewelItem.getItemId()));
        this.player.sendPacket(new SMPushJewelToSocket(this.itemStorageType, this.jewelStorageType, this.jewelIndex, this.itemSlot, this.jewelSlot));
    }

    @Override
    public boolean canAct() {
        if (this.jewelIndex < 0 || this.jewelIndex > 6) {
            return false;
        }
        this.jewelPack = this.playerBag.getItemPack(this.jewelStorageType);
        this.itemPack = this.playerBag.getItemPack(this.itemStorageType);
        if (this.jewelPack == null || this.itemPack == null || this.itemSlot > this.itemPack.getExpandSize() || this.jewelSlot > this.jewelPack.getExpandSize()) {
            return false;
        }
        if (this.itemSlot < this.itemPack.getDefaultSlotIndex() || this.jewelSlot < this.jewelPack.getDefaultSlotIndex()) {
            return false;
        }
        this.item = this.itemPack.getItem(this.itemSlot);
        this.jewelItem = this.jewelPack.getItem(this.jewelSlot);
        if (this.item == null || this.jewelItem == null) {
            return false;
        }
        final ItemEnchantT enchantTemplate = this.item.getEnchantTemplate();
        if (enchantTemplate == null || this.jewelIndex >= enchantTemplate.getSocket()) {
            return false;
        }
        final ItemTemplate jewelTemplate = this.jewelItem.getTemplate();
        final EItemClassify itemClassify = jewelTemplate.getItemClassify();
        final EJewelEquipType jewelEquipType = jewelTemplate.getJewelEquipType();
        return itemClassify != null && jewelEquipType != null && itemClassify.isJewel() && jewelEquipType.canJewelAct(this.item.getTemplate().getEquipType());
    }
}
