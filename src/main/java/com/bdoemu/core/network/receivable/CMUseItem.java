// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.UseItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMUseItem extends ReceivablePacket<GameClient> {
    private int slot;
    private int targetGameObjId;
    private EItemStorageLocation storageType;

    public CMUseItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.storageType = EItemStorageLocation.valueOf(this.readC());
        this.slot = this.readCD();
        this.targetGameObjId = this.readD();
        this.readF();
        this.readD();
        this.readF();
        this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Creature target = KnowList.getObject(player, this.targetGameObjId);
            if (target != null) {
                player.getPlayerBag().onEvent(new UseItemEvent(player, this.slot, this.storageType, target));
            }
        }
    }
}
