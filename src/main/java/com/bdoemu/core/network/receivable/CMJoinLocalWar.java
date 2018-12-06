package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.pvp.LocalWarStatus;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.LocalWarService;
import com.bdoemu.gameserver.worldInstance.World;

public class CMJoinLocalWar extends ReceivablePacket<GameClient> {
    public CMJoinLocalWar(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
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
            final LocalWarStatus localWarStatus = LocalWarService.getInstance().getLocalWarStatus();
            if (!localWarStatus.getLocalWarStatusType().isWaiting() && localWarStatus.getRemainingLocalWarTime() <= 60 * 7) {
                player.sendPacket(new SMNak(EStringTable.eErrNoCantInviteCompetitionWhileLocalWar, this.opCode));
                return;
            }

            // Let player enter RBF now
            localWarStatus.enterOnLocalWar(player);
            player.sendPacketNoFlush(new SMUpdateLocalWar(player));
            player.sendPacketNoFlush(new SMJoinLocalWar(player));
            player.sendPacket(new SMLoadField());
            player.setReadyToPlay(false);
            switch (player.getPVPController().getLocalWarTeamType()) {
                case YellowTeam:
                    World.getInstance().teleport(player, new Location(326171.0, 613313.0, -1815.0));
                    break;
                case RedTeam:
                    World.getInstance().teleport(player, new Location(344161.0, 614107.0, -1715.0));
                    break;
            }
            player.sendPacket(new SMLoadFieldComplete());
        }
    }
}
