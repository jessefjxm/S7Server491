// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListAlchemyRecord;
import com.bdoemu.gameserver.model.alchemy.AlchemyRecord;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.List;

public class CMListAlchemyRecord extends ReceivablePacket<GameClient> {
    private int cardKey;

    public CMListAlchemyRecord(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.cardKey = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final List<AlchemyRecord> alchemyRecords = player.getAlchemyRecordStorage().getAlchemyRecords(this.cardKey);
            if (alchemyRecords != null) {
                player.sendPacket(new SMListAlchemyRecord(this.cardKey, 1, alchemyRecords));
            }
        }
    }
}
