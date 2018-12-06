// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.RemoveCardItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMRemoveCard extends ReceivablePacket<GameClient> {
    private int cardId;
    private EItemStorageLocation storageLocation;
    private int slotIndex;

    public CMRemoveCard(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.cardId = this.readD();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            if (this.storageLocation == EItemStorageLocation.None) {
                if (!player.addWp(-10)) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoMentalNotEnoughWp, this.opCode));
                    return;
                }
                player.getMentalCardHandler().removeMentalCard(this.cardId);
            } else {
                player.getPlayerBag().onEvent(new RemoveCardItemEvent(player, this.cardId, this.storageLocation, this.slotIndex));
            }
        }
    }
}
