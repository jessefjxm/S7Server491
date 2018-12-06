// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMListDeliveryItem;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

public class CMListDeliveryItem extends ReceivablePacket<GameClient> {
    public CMListDeliveryItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        ((GameClient) this.getClient()).sendPacket((SendablePacket) new SMListDeliveryItem(EPacketTaskType.Add));
    }
}
