// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ItemTransferLifeExperienceEvent;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMItemTransferLifeExperience extends ReceivablePacket<GameClient> {
    private EItemStorageLocation storageLocation;
    private int slotIndex;
    private long objectId;
    private ELifeExpType lifeExpType;

    public CMItemTransferLifeExperience(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.objectId = this.readQ();
        this.lifeExpType = ELifeExpType.valueOf(this.readC());
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new ItemTransferLifeExperienceEvent(player, this.storageLocation, this.slotIndex, this.objectId, this.lifeExpType));
        }
    }
}
