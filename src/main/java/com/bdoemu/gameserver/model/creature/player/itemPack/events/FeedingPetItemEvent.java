// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMActivePetPrivateInfo;
import com.bdoemu.core.network.sendable.SMActivePetPublicInfo;
import com.bdoemu.core.network.sendable.SMUpdatePetHungry;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collections;

public class FeedingPetItemEvent extends AItemEvent {
    private long petObjId;
    private EItemStorageLocation feedItemStorageType;
    private int feedItemSlot;
    private ItemTemplate template;
    private Servant pet;

    public FeedingPetItemEvent(final Player player, final long petObjId, final EItemStorageLocation feedItemStorageType, final int feedItemSlot) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.petObjId = petObjId;
        this.feedItemStorageType = feedItemStorageType;
        this.feedItemSlot = feedItemSlot;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        final int hunger = this.template.getContentsEventParam1();
        this.pet.addExp(hunger);
        this.pet.setHunger(Math.min(this.pet.getHunger() + hunger, this.pet.getMaxHunger()));
        this.player.sendBroadcastItSelfPacket(new SMActivePetPublicInfo(Collections.singletonList(this.pet), EPacketTaskType.Update));
        this.player.sendPacket(new SMActivePetPrivateInfo(Collections.singletonList(this.pet)));
        this.player.sendPacket(new SMUpdatePetHungry());
    }

    @Override
    public boolean canAct() {
        if (!this.feedItemStorageType.isPlayerInventories()) {
            return false;
        }
        final ItemPack itemPack = this.playerBag.getItemPack(this.feedItemStorageType);
        if (itemPack == null || this.feedItemSlot > itemPack.getExpandSize()) {
            return false;
        }
        if (this.feedItemSlot < itemPack.getDefaultSlotIndex()) {
            return false;
        }
        final Item item = itemPack.getItem(this.feedItemSlot);
        if (item == null) {
            return false;
        }
        this.template = item.getTemplate();
        this.pet = this.player.getServantController().getServant(this.petObjId);
        this.decreaseItem(this.feedItemSlot, 1L, this.feedItemStorageType);
        return super.canAct() && this.pet != null && this.pet.isHungry() && this.template.getContentsEventType() != null && this.template.getContentsEventType().isPetFood();
    }
}
