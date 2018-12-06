// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMAvataEquipOnOff;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMAvataEquipOnOff extends ReceivablePacket<GameClient> {
    private int avatarEquip;

    public CMAvataEquipOnOff(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.avatarEquip = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.setAvatarEquip(this.avatarEquip);
            player.sendBroadcastItSelfPacket(new SMAvataEquipOnOff(player));
        }
    }
}
