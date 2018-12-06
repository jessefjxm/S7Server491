package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMLoadField;
import com.bdoemu.core.network.sendable.SMLoadFieldComplete;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.worldInstance.World;

public class CMUnJoinPvpBattleGround extends ReceivablePacket<GameClient> {
    public CMUnJoinPvpBattleGround(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null) {
            Location returnLoc = player.getPVPController().getReturnPosition();
            if (returnLoc != null) {
                player.sendPacket(new SMLoadField());
                player.setReadyToPlay(false);
                World.getInstance().teleport(player, new Location(returnLoc.getX(), returnLoc.getY(), returnLoc.getZ()));
                player.sendPacket(new SMLoadFieldComplete());
                player.getPVPController().setReturnPosition(null);
            }
        }
    }
}