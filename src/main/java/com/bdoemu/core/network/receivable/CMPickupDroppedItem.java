// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.DeadBody;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.PickupDroppedItemEvent;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMPickupDroppedItem extends ReceivablePacket<GameClient> {
    private int ownerSessionId;
    private int bodySessionId;
    private int slotIndex;
    private long count;

    public CMPickupDroppedItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.readC();
        this.ownerSessionId = this.readD();
        this.readC();
        this.bodySessionId = this.readD();
        this.readC();
        this.readC();
        this.readC();
        this.slotIndex = this.readCD();
        this.count = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            if (this.bodySessionId > 0) {
                final DeadBody deadBody = KnowList.getObject(player, ECharKind.Deadbody, this.bodySessionId);
                if (deadBody != null) {
                    deadBody.pickupDrop(player, this.slotIndex, this.count);
                }
            } else {
                player.getPlayerBag().onEvent(new PickupDroppedItemEvent(player, player.getPlayerBag().getDropBag(), this.slotIndex, 1, this.count));
            }
        }
    }
}
