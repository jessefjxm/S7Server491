// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ChangeServantToRewardEvent;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.misc.enums.EServantToRewardType;

public class CMChangeServantToReward extends ReceivablePacket<GameClient> {
    private int npcSessionId;
    private long servantObjectId;
    private int servantNpcId;
    private long itemObjectId;
    private EServantToRewardType type;

    public CMChangeServantToReward(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcSessionId = this.readD();
        this.servantObjectId = this.readQ();
        this.servantNpcId = this.readH();
        this.readD();
        this.itemObjectId = this.readQ();
        this.type = EServantToRewardType.values()[this.readC()];
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcSessionId);
            if (npc == null) {
                return;
            }
            player.getPlayerBag().onEvent(new ChangeServantToRewardEvent(player, this.servantObjectId, this.type, npc));
        }
    }
}
