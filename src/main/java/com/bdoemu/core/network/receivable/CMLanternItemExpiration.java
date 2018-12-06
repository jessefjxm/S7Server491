// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMLanternItemExpiration;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.enums.EEquipSlot;
import com.bdoemu.gameserver.model.items.Item;

public class CMLanternItemExpiration extends ReceivablePacket<GameClient> {
    private int slotIndex;

    public CMLanternItemExpiration(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.slotIndex = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Item lantern = player.getPlayerBag().getEquipments().getItem(EEquipSlot.lantern.ordinal());
            if (lantern != null && lantern.getRemainingExpirationPeriod() == 0L) {
                player.sendBroadcastItSelfPacket(new SMLanternItemExpiration(player.getGameObjectId(), this.slotIndex));
            }
        }
    }
}
