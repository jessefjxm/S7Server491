package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.core.network.sendable.SMActivePetPublicInfo;
import com.bdoemu.core.network.sendable.SMAddAppliedActiveBuffs;
import com.bdoemu.core.network.sendable.SMAddPlayers;
import com.bdoemu.core.network.sendable.SMRemoveActor;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.chat.enums.EChatResponseType;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;

import java.util.Collection;
import java.util.Collections;

@CommandHandler(prefix = "invis", accessLevel = EAccessLevel.ADMIN)
public class InvisCommandHandler extends AbstractCommandHandler {
    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        player.setVisible(!player.isVisible());
        if (!player.isVisible()) {
            player.sendBroadcastPacket(new SMRemoveActor(player, ERemoveActorType.OutOfRange));
        } else {
            player.sendBroadcastPacket(new SMAddPlayers(Collections.singleton(player), player, true, false));
            final Collection<Servant> activePets = player.getServantController().getServants(EServantState.Field, EServantType.Pet);
            if (!activePets.isEmpty()) {
                player.sendBroadcastPacket(new SMActivePetPublicInfo(activePets, EPacketTaskType.Add));
            }
            final Collection<ActiveBuff> buffs = player.getBuffList().getBuffs();
            if (!buffs.isEmpty()) {
                player.sendBroadcastPacket(new SMAddAppliedActiveBuffs(buffs));
            }
        }
        return new Object[]{EChatResponseType.Accepted, player.isVisible() ? "You are visible." : " You are invisible."};
    }
}
