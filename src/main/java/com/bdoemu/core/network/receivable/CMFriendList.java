// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMFriendGroupList;
import com.bdoemu.core.network.sendable.SMFriendList;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.Friend;

import java.util.Collection;

public class CMFriendList extends ReceivablePacket<GameClient> {
    public CMFriendList(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            this.sendPacket((SendablePacket) new SMFriendGroupList(player.getFriendHandler().getGroupsList()));
            final ListSplitter<Friend> friends = (ListSplitter<Friend>) new ListSplitter((Collection) player.getFriendHandler().getFriends(), 80);
            while (friends.hasNext()) {
                this.sendPacket((SendablePacket) new SMFriendList(friends.getNext()));
            }
        }
    }
}
