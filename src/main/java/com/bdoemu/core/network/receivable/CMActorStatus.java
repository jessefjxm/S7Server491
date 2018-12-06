package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.Gate;
import com.bdoemu.gameserver.model.creature.monster.Monster;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class CMActorStatus extends ReceivablePacket<GameClient> {
    private static final Logger log = LoggerFactory.getLogger(CMActorStatus.class);
    private int sessionId;

    public CMActorStatus(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.sessionId = this.readD();
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        final Creature creature = World.getInstance().getObjectById(this.sessionId);
        if (creature != null && player.canSee(creature)) {
            if (creature.isMonster())
                player.sendPacket(new SMAddMonsters(Collections.singletonList((Monster) creature)));
            else if (creature.isGate())
                player.sendPacket(new SMAddGate(Collections.singletonList((Gate) creature)));
            else if (creature.isNpc())
                player.sendPacket(new SMAddNpcs(Collections.singletonList((Npc) creature)));
            else if (creature.isPlayer())
                player.sendPacket(new SMAddPlayers(Collections.singletonList((Player) creature), player, false, false));
            else if (creature.isVehicle())
                player.sendPacket(new SMAddVehicles(Collections.singletonList((Servant) creature)));
            else
                CMActorStatus.log.warn("Can't find actorStatus packet for creature {}", creature);
        } else {
            player.sendPacket(new SMActorStatusNak(this.sessionId));
        }
    }
}
