// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.events.StartAlchemyHouseEvent;

public class CMStartAlchemy extends ReceivablePacket<GameClient> {
    private int size;
    private long[] st1;
    private long[] st2;
    private long[] st3;
    private long[] st4;
    private long[] st5;
    private long[] st6;
    private long[] st7;
    private long[] st8;
    private long houseObjId;
    private long installationObjId;

    public CMStartAlchemy(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.size = this.readHD();
        this.st1 = new long[]{this.readCD(), this.readQ()};
        this.st2 = new long[]{this.readCD(), this.readQ()};
        this.st3 = new long[]{this.readCD(), this.readQ()};
        this.st4 = new long[]{this.readCD(), this.readQ()};
        this.st5 = new long[]{this.readCD(), this.readQ()};
        this.st6 = new long[]{this.readCD(), this.readQ()};
        this.st7 = new long[]{this.readCD(), this.readQ()};
        this.st8 = new long[]{this.readCD(), this.readQ()};
        this.readB(new byte[108]);
        this.houseObjId = this.readQ();
        this.installationObjId = this.readQ();
        this.readH();
        this.readH();
        this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getHouseStorage().onEvent(new StartAlchemyHouseEvent(player, this.houseObjId, this.installationObjId, this.size, new long[][]{this.st1, this.st2, this.st3, this.st4, this.st5, this.st6, this.st7, this.st8}));
        }
    }
}
