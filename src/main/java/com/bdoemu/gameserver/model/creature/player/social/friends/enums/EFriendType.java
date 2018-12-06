package com.bdoemu.gameserver.model.creature.player.social.friends.enums;

public enum EFriendType {
    MyFriend(0),
    Party(1);

    private byte id;

    EFriendType(final int id) {
        this.id = (byte) id;
    }

    public byte getId() {
        return this.id;
    }

    public boolean isMyFriend() {
        return this == EFriendType.MyFriend;
    }
}
