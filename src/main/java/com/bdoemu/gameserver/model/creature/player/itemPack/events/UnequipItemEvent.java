// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ADBItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerBag;
import com.bdoemu.gameserver.model.items.Item;

public class UnequipItemEvent implements IBagEvent {
    private Player player;
    private Playable target;
    private int equipSlot;
    private PlayerBag playerBag;
    private Item equippedItem;
    private ADBItemPack pack;

    public UnequipItemEvent(final Player player, final Playable target, final int equipSlot) {
        this.player = player;
        this.target = target;
        this.equipSlot = equipSlot;
        this.playerBag = player.getPlayerBag();
    }

    @Override
    public void onEvent() {
        for (int slotIndex = this.pack.getDefaultSlotIndex(); slotIndex < this.pack.getExpandSize(); ++slotIndex) {
            if (this.pack.addItem(this.equippedItem, slotIndex)) {
                this.target.getEquipments().removeItem(this.equipSlot);
                this.target.getEquipments().unequip(this.equippedItem);
                this.target.recalculateEquipSlotCacheCount();
                this.equippedItem.setSlotIndex(slotIndex);
                this.equippedItem.setStorageLocation(this.pack.getLocationType());
                if (this.target.isPlayer()) {
                    this.player.recalculateActionStorage();
                    this.player.sendPacket(new SMSetCharacterStats(this.player));
                }
                this.player.sendPacket(new SMSetCharacterRelatedPoints(this.target, 0));
                this.target.sendBroadcastItSelfPacket(new SMSetCharacterPublicPoints(this.target, 0.0f));
                if (this.target.isVehicle()) {
                    this.player.sendPacket(new SMSetMyServantPoints(this.player));
                }
                this.target.sendBroadcastItSelfPacket(new SMSetCharacterStatPoint(this.target));
                if (target.isPlayable())
                    this.target.sendPacket(new SMSetCharacterSpeeds(this.target.getGameStats()));
                this.player.sendBroadcastItSelfPacket(new SMUnequipItem(this.player, this.target, slotIndex, this.equipSlot, this.equippedItem));
                break;
            }
        }
    }

    @Override
    public boolean canAct() {
        if (this.player.hasTrade()) {
            return false;
        }
        this.equippedItem = this.target.getEquipments().getItem(this.equipSlot);
        if (this.equippedItem == null) {
            return false;
        }
        this.pack = (this.equippedItem.getTemplate().isCash() ? this.playerBag.getCashInventory() : this.playerBag.getInventory());
        return this.pack.hasFreeSlots();
    }
}
