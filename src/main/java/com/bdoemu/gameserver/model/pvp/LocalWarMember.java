package com.bdoemu.gameserver.model.pvp;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.world.Location;

public class LocalWarMember {
    private final Player _player;
    private Location _returnLoc;
    private int _joinTime;
    private int _gearScore;

    public LocalWarMember(final Player player) {
        _player = player;
        _returnLoc = new Location(player.getLocation());
        _joinTime = (int) (System.currentTimeMillis() / 1000L);
        _gearScore = player.getGearScore();
    }

    public Player getPlayer() {
        return _player;
    }

    public long getObjectId() {
        return _player.getObjectId();
    }

    public int getGearScore() {
        return _gearScore;
    }

    public Location getReturnLoc() {
        return _returnLoc;
    }

    public int getJoinTime() {
        return _joinTime;
    }
}
