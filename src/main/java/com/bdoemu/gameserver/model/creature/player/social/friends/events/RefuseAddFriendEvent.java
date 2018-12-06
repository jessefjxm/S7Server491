package com.bdoemu.gameserver.model.creature.player.social.friends.events;

import com.bdoemu.core.network.sendable.SMRefuseAddFriend;
import com.bdoemu.gameserver.databaseCollections.FriendsRequestDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendHandler;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendReq;

public class RefuseAddFriendEvent implements IFriendEvent {
    private Player player;
    private long accountId;
    private FriendHandler handler;
    private FriendReq req;

    public RefuseAddFriendEvent(final Player player, final long accountId) {
        this.player = player;
        this.accountId = accountId;
        this.handler = player.getFriendHandler();
    }

    @Override
    public void onEvent() {
        FriendsRequestDBCollection.getInstance().delete(this.req.getObjectId());
        this.player.sendPacket(new SMRefuseAddFriend(this.accountId));
    }

    @Override
    public boolean canAct() {
        this.req = this.handler.getFriendsReqMap().remove(this.accountId);
        return this.req != null;
    }
}
