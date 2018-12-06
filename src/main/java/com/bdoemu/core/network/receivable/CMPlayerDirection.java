package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMPlayerDirection;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMPlayerDirection extends ReceivablePacket<GameClient> {
    private float _x;
    private float _y;
    private float _z;
    private float _cos;
    private float _sin;
    private int _carrierGameObjId;

    public CMPlayerDirection(final short opcode) {
        super(opcode);
    }

    protected void read() {
        _cos = readF();
        readD();
        _sin = readF();
        _x = readF();
        _z = readF();
        _y = readF();
        _carrierGameObjId = readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) getClient()).getPlayer();
        if (player != null)
            player.sendBroadcastPacket(new SMPlayerDirection(player.getGameObjectId(), _x, _y, _z, _cos, _sin, _carrierGameObjId));
    }
}
