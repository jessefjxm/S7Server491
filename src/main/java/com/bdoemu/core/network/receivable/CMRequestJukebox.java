// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.events.RequestJukeboxEvent;

public class CMRequestJukebox extends ReceivablePacket<GameClient> {
    private long installationObjId;
    private int id;
    private int type;

    public CMRequestJukebox(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.id = this.readHD();
        this.installationObjId = this.readQ();
        this.type = this.readC();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getHouseStorage().onEvent(new RequestJukeboxEvent(player, this.installationObjId, this.id, this.type));
        }
    }
}
