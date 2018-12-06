// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMUpdateWaitComment;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMUpdateWaitComment extends ReceivablePacket<GameClient> {
    private byte type;
    private String message;

    public CMUpdateWaitComment(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.type = this.readC();
        this.message = this.readS(702);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            int updateNr;
            if (this.type == 0) {
                player.getAccountData().setComment(this.message);
                updateNr = player.getAccountData().getUserBasicCacheCount();
            } else {
                player.setNonSavedComment(this.message);
                updateNr = player.getPcNonSavedCacheCount();
            }
            player.sendBroadcastItSelfPacket(new SMUpdateWaitComment(player.getGameObjectId(), player.getAccountId(), updateNr, this.type, this.message));
        }
    }
}
