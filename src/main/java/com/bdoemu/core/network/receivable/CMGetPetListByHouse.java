// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMGetPetListByHouse;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMGetPetListByHouse extends ReceivablePacket<GameClient> {
    private long houseObjectId;
    private long accountId;
    private int houseId;

    public CMGetPetListByHouse(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.houseObjectId = this.readQ();
        this.houseId = this.readHD();
        this.accountId = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.sendPacket(new SMGetPetListByHouse(this.houseObjectId, this.accountId));
        }
    }
}
