// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.UnequipItemEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.knowlist.KnowList;

import java.util.Collection;

public class CMUnequipItem extends ReceivablePacket<GameClient> {
    private int equipSlot;
    private int gameObjId;

    public CMUnequipItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.gameObjId = this.readD();
        this.equipSlot = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Creature targetSpawn = KnowList.getObject(player, this.gameObjId);
            if (targetSpawn != null) {
                if (!targetSpawn.isPlayable()) {
                    return;
                }
                final Playable target = (Playable) targetSpawn;
                if (target.isVehicle()) {
                    final Collection<Servant> servants = player.getServantController().getServants(EServantState.Field);
                    if (!servants.contains(target)) {
                        return;
                    }
                }
                if (target.isPlayer() && player != target) {
                    return;
                }
                player.getPlayerBag().onEvent(new UnequipItemEvent(player, target, this.equipSlot));
            }
        }
    }
}
