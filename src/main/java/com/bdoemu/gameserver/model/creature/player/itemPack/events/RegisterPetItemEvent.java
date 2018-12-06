// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMDeactivePetInfo;
import com.bdoemu.gameserver.dataholders.PetData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.templates.PetTemplate;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collections;

public class RegisterPetItemEvent extends AItemEvent {
    private String petName;
    private EItemStorageLocation petItemStorageType;
    private int petItemSlot;
    private Servant pet;

    public RegisterPetItemEvent(final Player player, final String petName, final EItemStorageLocation petItemStorageType, final int petItemSlot) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.petName = petName;
        this.petItemStorageType = petItemStorageType;
        this.petItemSlot = petItemSlot;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.getServantController().add(this.pet);
        this.player.sendPacket(new SMDeactivePetInfo(Collections.singletonList(this.pet), EPacketTaskType.Add));
    }

    @Override
    public boolean canAct() {
        if (!this.petItemStorageType.isPlayerInventories()) {
            return false;
        }
        if (this.petName.isEmpty() || this.petName.length() > 10) {
            return false;
        }
        final ItemPack itemPack = this.playerBag.getItemPack(this.petItemStorageType);
        if (itemPack == null || this.petItemSlot > itemPack.getExpandSize()) {
            return false;
        }
        if (this.petItemSlot < itemPack.getDefaultSlotIndex()) {
            return false;
        }
        final Item item = itemPack.getItem(this.petItemSlot);
        if (item == null) {
            return false;
        }
        final ItemTemplate template = item.getTemplate();
        if (template.getContentsEventType() == null || !template.getContentsEventType().isPetRegister()) {
            return false;
        }
        final PetTemplate petTemplate = PetData.getInstance().getTemplate(template.getContentsEventParam1());
        if (petTemplate != null) {
            this.pet = new Servant(petTemplate, this.player, this.petName);
        }
        this.decreaseItem(this.petItemSlot, 1L, this.petItemStorageType);
        return this.pet != null;
    }
}
