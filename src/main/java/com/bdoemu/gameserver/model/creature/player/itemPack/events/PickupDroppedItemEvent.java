// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMBroadcastGetItem;
import com.bdoemu.core.network.sendable.SMPartyMemberPickupItem;
import com.bdoemu.core.network.sendable.SMPickupDroppedItem;
import com.bdoemu.gameserver.model.creature.DropBag;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemGetType;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.worldInstance.World;

public class PickupDroppedItemEvent extends AItemEvent {
    private int slotIndex;
    private int type;
    private Item dropItem;
    private DropBag dropBag;
    private IParty<Player> party;
    private long count;

    public PickupDroppedItemEvent(final Player player, final DropBag dropBag, final int slotIndex, final int type, final long count) {
        super(player, player, player, EStringTable.eErrNoAcquireCollectItem, EStringTable.eErrNoAcquireCollectItem, 0);
        this.dropBag = dropBag;
        this.slotIndex = slotIndex;
        this.type = type;
        this.party = player.getParty();
        this.count = count;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        if (this.type == 0 && this.party != null) {
            this.party.sendBroadcastPacket(new SMPartyMemberPickupItem(this.player.getGameObjectId(), this.dropItem.getItemId(), this.dropItem.getEnchantLevel()));
        }
        this.player.sendBroadcastItSelfPacket(new SMPickupDroppedItem(this.player.getGameObjectId(), (this.type == 1) ? this.player.getGameObjectId() : this.dropBag.getSourceGameObjectId(), this.slotIndex, this.dropBag.isEmpty(), this.type, this.dropItem.getCount()));
        if (this.dropItem.getTemplate().isNotifyWorld()) {
            World.getInstance().broadcastWorldPacket(new SMBroadcastGetItem(EItemGetType.DROP, this.player.getName(), this.dropItem.getItemId(), this.dropItem.getEnchantLevel(), this.dropBag.getSourceCreatureId()));
        }
        if (this.dropBag.isEmpty()) {
            this.player.getPlayerBag().setDropBag(null);
        }
    }

    @Override
    public boolean canAct() {
        if (this.dropBag == null) {
            return false;
        }
        this.dropItem = this.dropBag.getDropMap().get(this.slotIndex);
        if (this.dropItem == null) {
            return false;
        }
        this.addItem(this.dropItem.getTemplate(), this.count, this.dropItem.getEnchantLevel());
        return super.canAct() && this.dropBag.decrease(this.slotIndex, this.count);
    }

    @Override
    public boolean isLoggable() {
        return false;
    }
}
