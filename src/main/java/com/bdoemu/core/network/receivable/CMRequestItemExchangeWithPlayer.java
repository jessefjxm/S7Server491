// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMCancelItemExchangeWithPlayer;
import com.bdoemu.core.network.sendable.SMRequestItemExchangeWithPlayer;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.worldInstance.World;

public class CMRequestItemExchangeWithPlayer extends ReceivablePacket<GameClient> {
    private int gameObjId;

    public CMRequestItemExchangeWithPlayer(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.gameObjId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Player invited = World.getInstance().getPlayer(this.gameObjId);
            if (invited != null) {
                if (invited.hasTrade()) {
                    player.sendPacket(new SMCancelItemExchangeWithPlayer(EStringTable.eErrNoItemExchangeTargetIsDoing));
                    return;
                }
                if (player.hasTrade()) {
                    player.sendPacket(new SMCancelItemExchangeWithPlayer(EStringTable.eErrNoItemExchangeAlreadyIsDoing));
                    return;
                }
                invited.sendPacket(new SMRequestItemExchangeWithPlayer(player.getGameObjectId()));
            }
        }
    }
}
