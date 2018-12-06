// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMAddDeliveryItem extends ReceivablePacket<GameClient> {
    private int originTownId;
    private int destTownId;
    private int carriageId;
    private int itemId;
    private int enchantLevel;
    private long itemObjId;
    private long itemCount;
    private long silverObjId;

    public CMAddDeliveryItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.originTownId = this.readHD();
        this.destTownId = this.readHD();
        this.carriageId = this.readHD();
        this.itemObjId = this.readQ();
        this.itemId = this.readHD();
        this.enchantLevel = this.readHD();
        this.itemCount = this.readQ();
        this.silverObjId = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
        }
    }
}
