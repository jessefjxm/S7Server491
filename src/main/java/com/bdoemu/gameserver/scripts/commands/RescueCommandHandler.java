package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.BattleOptionConfig;
import com.bdoemu.core.network.receivable.CMRescue;
import com.bdoemu.core.network.sendable.SMLoadField;
import com.bdoemu.core.network.sendable.SMLoadFieldComplete;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.dataholders.RegionData;
import com.bdoemu.gameserver.dataholders.TeleportData;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.chat.enums.EChatResponseType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.ETeleportType;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.model.world.region.templates.RegionTemplate;
import com.bdoemu.gameserver.service.LocalWarService;
import com.bdoemu.gameserver.worldInstance.World;

@CommandHandler(prefix = "rescue", accessLevel = EAccessLevel.USER)
public class RescueCommandHandler extends AbstractCommandHandler {
    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        if (System.currentTimeMillis() < player.getRescueCoolTime()) {
            player.sendPacket(new SMNak(EStringTable.eErrNoRescueTime, CMRescue.class));
            return new Object[]{EChatResponseType.Rejected};
        }
        player.setRescueCoolTime(System.currentTimeMillis() + BattleOptionConfig.RESCUE_REUSE_DELAY);
        final RegionTemplate regionTemplate = RegionData.getInstance().getTemplate(88);
        Location loc = player.getLocation();
        if (player.getBanController().isInJail()) {
            final Location jailLoc = TeleportData.getInstance().getTeleportLocation(ETeleportType.JAIL, 1);
            loc = new Location(jailLoc.getX(), jailLoc.getY(), jailLoc.getZ(), loc.getCos(), loc.getSin());
        } else {
            if (LocalWarService.getInstance().hasParticipant(player)) {
                switch (player.getPVPController().getLocalWarTeamType()) {
                    case YellowTeam:
                        loc = new Location(326171.0, 613313.0, -1815.0);
                        break;
                    case RedTeam:
                        loc = new Location(344161.0, 614107.0, -1715.0);
                        break;
                }
            } else {
                loc = new Location(regionTemplate.getReturnX(), regionTemplate.getReturnY(), regionTemplate.getReturnZ(), player.getLocation().getCos(), player.getLocation().getSin());
            }
        }
        player.sendPacket(new SMLoadField());
        player.setReadyToPlay(false);
        World.getInstance().teleport(player, loc);
        player.sendPacket(new SMLoadFieldComplete());
        return new Object[]{EChatResponseType.Accepted, "睁开眼睛，你已回到了奥尔比亚村里."};
    }
}
