// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.FeedingPetItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMFeedingPet extends ReceivablePacket<GameClient> {
    private long petObjId;
    private EItemStorageLocation feedItemStorageType;
    private int feedItemSlot;

    public CMFeedingPet(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.petObjId = this.readQ();
        this.feedItemStorageType = EItemStorageLocation.valueOf(this.readC());
        this.feedItemSlot = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new FeedingPetItemEvent(player, this.petObjId, this.feedItemStorageType, this.feedItemSlot));
        }
    }
}
