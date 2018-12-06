// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMChangePartyInventorySlots;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.team.party.IParty;

public class AddPartyItemEvent extends AItemEvent {
    private final IParty<Player> party;
    private final Item item;
    private final int slotIndex;

    public AddPartyItemEvent(final IParty<Player> party, final Player player, final Item item, final int slotIndex) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.party = party;
        this.item = item;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.party.sendBroadcastPacket(new SMChangePartyInventorySlots(this.player.getGameObjectId(), this.item, this.slotIndex));
    }

    @Override
    public boolean canAct() {
        this.addItem(this.item);
        return super.canAct();
    }
}
