// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class UnequipItemTask {
    private Item item;
    private EItemStorageLocation locationType;
    private int equipSlot;
    private int unequipSlot;

    public UnequipItemTask(final Item item, final int equipSlot, final EItemStorageLocation locationType) {
        this.item = item;
        this.equipSlot = equipSlot;
        this.locationType = locationType;
    }

    public Item getItem() {
        return this.item;
    }

    public int getEquipSlot() {
        return this.equipSlot;
    }

    public void setEquipSlot(final int equipSlot) {
        this.equipSlot = equipSlot;
    }

    public int getUnequipSlot() {
        return this.unequipSlot;
    }

    public void setUnequipSlot(final int unequipSlot) {
        this.unequipSlot = unequipSlot;
    }

    public EItemStorageLocation getLocationType() {
        return this.locationType;
    }

    public void setLocationType(final EItemStorageLocation locationType) {
        this.locationType = locationType;
    }
}
