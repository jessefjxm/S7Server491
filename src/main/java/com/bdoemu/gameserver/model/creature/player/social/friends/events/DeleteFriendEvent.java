package com.bdoemu.gameserver.model.creature.player.social.friends.events;

import com.bdoemu.core.network.sendable.SMDeleteFriend;
import com.bdoemu.gameserver.databaseCollections.FriendsDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.social.friends.Friend;
import com.bdoemu.gameserver.model.creature.player.social.friends.FriendHandler;
import com.bdoemu.gameserver.worldInstance.World;

public class DeleteFriendEvent implements IFriendEvent {
    private Player _player;
    private long _accountId;
    private Friend _myFriend;
    private FriendHandler _handler;

    public DeleteFriendEvent(final Player player, final long accountId) {
        _player = player;
        _accountId = accountId;
        _handler = player.getFriendHandler();
    }

    @Override
    public void onEvent() {
        FriendsDBCollection.getInstance().delete(_myFriend.getObjectId());
        _player.sendPacket(new SMDeleteFriend(_accountId));

        Friend friend = FriendsDBCollection.getInstance().loadFriend(_accountId, _player.getAccountId());
        if (friend != null) {
            FriendsDBCollection.getInstance().delete(friend.getObjectId());

            Player friendPlayer = World.getInstance().getPlayerByAccount(_accountId);
            if (friendPlayer != null)
                friendPlayer.sendPacketNoFlush(new SMDeleteFriend(_player.getAccountId()));
        }
    }

    @Override
    public boolean canAct() {
        _myFriend = _handler.getFriendMap().remove(_accountId);
        return _myFriend != null;
    }
}
