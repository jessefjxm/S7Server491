// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.events.AddFriendGroupEvent;

public class CMAddFriendGroup extends ReceivablePacket<GameClient> {
    private String name;

    public CMAddFriendGroup(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.name = this.readS(62);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getFriendHandler().onEvent(new AddFriendGroupEvent(player, this.name));
        }
    }
}
