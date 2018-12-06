// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.events.MoveFriendGroupEvent;

public class CMMoveFriendGroup extends ReceivablePacket<GameClient> {
    private long friendObjectId;
    private int groupId;

    public CMMoveFriendGroup(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.friendObjectId = this.readQ();
        this.groupId = this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getFriendHandler().onEvent(new MoveFriendGroupEvent(player, this.friendObjectId, this.groupId));
        }
    }
}
