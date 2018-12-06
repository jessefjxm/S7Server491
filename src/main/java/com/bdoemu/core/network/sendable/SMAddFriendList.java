// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendReq;

import java.util.Collection;

public class SMAddFriendList extends SendablePacket<GameClient> {
    private final Collection<FriendReq> friendsReq;

    public SMAddFriendList(final Collection<FriendReq> friendsReq) {
        this.friendsReq = friendsReq;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.friendsReq.size());
        for (final FriendReq friendReq : this.friendsReq) {
            buffer.writeQ(friendReq.getReqAccountId());
            buffer.writeS((CharSequence) friendReq.getReqFamily(), 62);
        }
    }
}
