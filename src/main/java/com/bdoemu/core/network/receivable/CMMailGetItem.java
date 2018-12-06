// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.MailGetItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMMailGetItem extends ReceivablePacket<GameClient> {
    private long id;
    private long count;
    private long expirationDate;
    private EItemStorageLocation storageLocation;

    public CMMailGetItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.id = this.readQ();
        this.count = this.readQ();
        this.expirationDate = this.readQ();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && player.getRegion().getTemplate().isSafe()) {
            player.getPlayerBag().onEvent(new MailGetItemEvent(player, this.id, this.count, this.storageLocation));
        }
    }
}
