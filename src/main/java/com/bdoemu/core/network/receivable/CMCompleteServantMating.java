// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMCompleteServantMating;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.InstantCashItemEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.misc.enums.EInstantCashType;
import com.bdoemu.gameserver.service.GameTimeService;

public class CMCompleteServantMating extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private long servantObjId;
    private long count;

    public CMCompleteServantMating(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.servantObjId = this.readQ();
        this.npcGameObjId = this.readD();
        this.count = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcGameObjId);
            if (npc == null) {
                return;
            }
            final Servant servant = player.getServantController().getServant(this.servantObjId);
            if (servant != null) {
                final long remainingTime = GameTimeService.getServerTimeInSecond() - servant.getMatingTime();
                if (!player.getPlayerBag().onEvent(new InstantCashItemEvent(player, EInstantCashType.CompleteServantMating, (int) remainingTime, 0L, CMCompleteServantMating.class))) {
                    return;
                }
                servant.setMatingTime(0L);
                player.sendPacket(new SMCompleteServantMating(this.servantObjId, this.count));
            }
        }
    }
}
