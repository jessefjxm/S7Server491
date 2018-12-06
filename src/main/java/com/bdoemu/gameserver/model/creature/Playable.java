// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature;

import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.itemPack.AbstractAddItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.EquipmentsBag;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

public abstract class Playable extends Creature {
    protected int equipSlotCacheCount;
    protected int basicCacheCount;

    public Playable(final int gameObjectId, final CreatureTemplate template, final SpawnPlacementT spawnPlacement) {
        super(gameObjectId, template, spawnPlacement);
        this.equipSlotCacheCount = 1;
        this.basicCacheCount = 1;
    }

    public abstract AbstractAddItemPack getInventory();

    public abstract EquipmentsBag getEquipments();

    public abstract void onLevelChange(boolean sendPacket);

    public void setLevel(final int level) {
        this.level = level;
    }

    public void recalculateBasicCacheCount() {
        ++this.basicCacheCount;
    }

    public int getBasicCacheCount() {
        return this.basicCacheCount;
    }

    public void recalculateEquipSlotCacheCount() {
        ++this.equipSlotCacheCount;
    }

    public int getEquipSlotCacheCount() {
        return (this.getEquipments() != null) ? ((this.getEquipments().getItemSize() == 0) ? 0 : this.equipSlotCacheCount) : 0;
    }
}
