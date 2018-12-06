// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMGetDroppedItems;
import com.bdoemu.gameserver.model.creature.DeadBody;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMGetDroppedItemList extends ReceivablePacket<GameClient> {
    private int bodySessionId;

    public CMGetDroppedItemList(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.bodySessionId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final DeadBody deadBody = KnowList.getObject(player, ECharKind.Deadbody, this.bodySessionId);
            if (deadBody != null && !deadBody.dropIsEmpty()) {
                player.sendPacket(new SMGetDroppedItems(this.bodySessionId, deadBody.getDropBag()));
            }
        }
    }
}
