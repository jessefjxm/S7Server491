package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.configs.BattleOptionConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMLoadField;
import com.bdoemu.core.network.sendable.SMLoadFieldComplete;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.dataholders.TeleportData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.ETeleportType;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.model.world.region.Region;
import com.bdoemu.gameserver.service.LocalWarService;
import com.bdoemu.gameserver.worldInstance.World;

public class CMRescue extends ReceivablePacket<GameClient> {
    public CMRescue(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            if (LocalWarService.getInstance().hasParticipant(player)) {
                player.sendPacket(new SMNak(EStringTable.eErrNoDontUseInLocalWar, (int) opCode));
                return;
            }

            if (player.getPreemptiveStrikeTime() > 0 && System.currentTimeMillis() < player.getRescueCoolTime()) {
                player.sendPacket(new SMNak(EStringTable.eErrNoRescueTime, CMRescue.class));
                return;
            }

            player.setRescueCoolTime(System.currentTimeMillis() + BattleOptionConfig.RESCUE_REUSE_DELAY);
            final Region region = player.getLocation().getRegion();
            Location location = player.getLocation();
            if (player.getBanController().isInJail()) {
                final Location jaiLoc = TeleportData.getInstance().getTeleportLocation(ETeleportType.JAIL, 1);
                location = new Location(jaiLoc.getX(), jaiLoc.getY(), jaiLoc.getZ(), location.getCos(), location.getSin());
            } else if (region.getTemplate().getReturnX() != 0.0f || region.getTemplate().getReturnY() != 0.0f) {
                location = new Location(region.getTemplate().getReturnX(), region.getTemplate().getReturnY(), region.getTemplate().getReturnZ() + 30.0f, location.getCos(), location.getSin());
            } else {
                player.sendPacket(new SMNak(EStringTable.eErrNoNoExistReturnedTown, CMRescue.class));
                return;
            }
            player.sendPacket(new SMLoadField());
            player.setReadyToPlay(false);
            World.getInstance().teleport(player, location);
            player.sendPacket(new SMLoadFieldComplete());
        }
    }
}