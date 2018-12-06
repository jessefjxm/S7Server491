// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.social.friends.Friend;

public class SMAcceptAddFriend extends SendablePacket<GameClient> {
    private final Friend friend;

    public SMAcceptAddFriend(final Friend friend) {
        this.friend = friend;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.friend.getFriendAccountId());
        buffer.writeS((CharSequence) this.friend.getFamilyName(), 62);
        buffer.writeS((CharSequence) this.friend.getName(), 62);
        buffer.writeQ(this.friend.getLoginDate());
        buffer.writeQ(this.friend.getLogoutDate());
        buffer.writeH(this.friend.getMaxWp());
        buffer.writeH(this.friend.getMaxContribution());
        buffer.writeD(this.friend.getLevel());
        buffer.writeD(0);
    }
}
