// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.events.AddFriendEvent;

public class CMAddFriend extends ReceivablePacket<GameClient> {
    private String nameOrFamily;
    private boolean isName;

    public CMAddFriend(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.nameOrFamily = this.readS(62);
        this.isName = this.readCB();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getFriendHandler().onEvent(new AddFriendEvent(player, this.nameOrFamily, this.isName));
        }
    }
}
