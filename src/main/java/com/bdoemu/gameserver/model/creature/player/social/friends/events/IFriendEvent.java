package com.bdoemu.gameserver.model.creature.player.social.friends.events;

public interface IFriendEvent {
    void onEvent();

    boolean canAct();
}
