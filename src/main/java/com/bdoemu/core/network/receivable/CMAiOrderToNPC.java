// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMAiOrderToNPC extends ReceivablePacket<GameClient> {
    private long handlerHash;
    private int targetGameObjId;

    public CMAiOrderToNPC(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.handlerHash = this.readDQ();
        this.readF();
        this.readF();
        this.readF();
        this.targetGameObjId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            if (this.targetGameObjId > 0) {
                final Creature target = KnowList.getObject(player, this.targetGameObjId);
                if (target != null && player.isEnemy(target)) {
                    player.getAggroList().setTarget(target);
                }
            }
            for (final Creature summon : player.getSummonStorage().getSummons()) {
                summon.getAi().executeHandler(this.handlerHash, player, null);
            }
        }
    }
}
