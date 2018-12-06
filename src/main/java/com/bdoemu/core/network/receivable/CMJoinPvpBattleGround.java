package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMLoadField;
import com.bdoemu.core.network.sendable.SMLoadFieldComplete;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyPvpBattleGround;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.pvp.LocalWarStatus;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.LocalWarService;
import com.bdoemu.gameserver.worldInstance.World;

public class CMJoinPvpBattleGround extends ReceivablePacket<GameClient> {
    public CMJoinPvpBattleGround(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null) {
            // Check level
            if (player.getLevel() < 50) {
                player.sendPacket(new SMNak(EStringTable.eErrNoCantJoinUserLevelIsTooLow, this.opCode));
                return;
            }

            // Check party
            if (player.getParty() != null) {
                player.sendPacket(new SMNak(EStringTable.eErrNoLocalwarCantJoinParty, this.opCode));
                return;
            }

            // Check PvP match
            if (player.hasPvpMatch()) {
                player.sendPacket(new SMNak(EStringTable.eErrNoCantInviteCompetitionWhilePvP, this.opCode));
                return;
            }

            // Check local war
            if (LocalWarService.getInstance().hasParticipant(player)) {
                player.sendPacket(new SMNak(EStringTable.eErrNoLocalwarCantJoinParty, this.opCode));
                return;
            }

            // Teleport player.
            player.getPVPController().setReturnPosition(new Location(player.getLocation()));
            player.sendPacket(new SMLoadField());
            player.setReadyToPlay(false);
            World.getInstance().teleport(player, new Location(138476, 337361, 42));
            player.sendPacket(new SMLoadFieldComplete());
            player.sendBroadcastItSelfPacket(new SMNotifyPvpBattleGround());
        }
    }
}
