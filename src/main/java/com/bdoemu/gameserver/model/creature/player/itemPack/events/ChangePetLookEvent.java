// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMChangeLookPet;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class ChangePetLookEvent extends AItemEvent {
    private long petObjectId;
    private int actionIndex;
    private EItemStorageLocation storageLocation;
    private int slotIndex;
    private Servant pet;

    public ChangePetLookEvent(final Player player, final long petObjectId, final int actionIndex, final EItemStorageLocation storageLocation, final int slotIndex) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.petObjectId = petObjectId;
        this.actionIndex = actionIndex;
        this.storageLocation = storageLocation;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.pet.setFormIndex(this.actionIndex);
        this.player.sendBroadcastItSelfPacket(new SMChangeLookPet(this.pet));
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isPlayerInventories()) {
            return false;
        }
        final ItemPack pack = this.playerBag.getItemPack(this.storageLocation);
        final Item item = pack.getItem(this.slotIndex);
        if (item == null) {
            return false;
        }
        this.pet = this.player.getServantController().getServant(this.petObjectId);
        if (this.pet == null) {
            return false;
        }
        if (this.pet.getServantState() != EServantState.Stable) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.storageLocation);
        return super.canAct();
    }
}
