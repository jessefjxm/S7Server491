// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMGetServantMatingChildInfo;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMGetServantMatingChildInfo extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private long servantObjId;

    public CMGetServantMatingChildInfo(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.servantObjId = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcGameObjId);
            if (npc == null) {
                return;
            }
            final Servant femaleServant = player.getServantController().getServant(this.servantObjId);
            if (femaleServant != null && femaleServant.getServantState().isMating() && femaleServant.getRegionId() == npc.getRegionId()) {
                player.sendPacket(new SMGetServantMatingChildInfo(femaleServant.getMatingChildId()));
            }
        }
    }
}
