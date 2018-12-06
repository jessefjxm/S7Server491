// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.social.friends.Friend;

import java.util.Collection;

public class SMFriendList extends SendablePacket<GameClient> {
    public static final int MAX_SIZE = 80;
    private final Collection<Friend> friends;

    public SMFriendList(final Collection<Friend> friends) {
        this.friends = friends;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.friends.size());
        for (final Friend friend : this.friends) {
            buffer.writeQ(friend.getLoginDate());
            buffer.writeQ(friend.getLogoutDate());
            buffer.writeQ(friend.getFriendAccountId());
            buffer.writeS((CharSequence) friend.getFamilyName(), 62);
            buffer.writeS((CharSequence) friend.getName(), 62);
            buffer.writeH(friend.getGroupId());
            buffer.writeH(friend.getMaxWp());
            buffer.writeH(friend.getMaxContribution());
            buffer.writeD(friend.getLevel());
            buffer.writeD((int) friend.getFriendType().getId());
        }
    }
}
