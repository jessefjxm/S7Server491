package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.world.Location;

import java.util.Collection;

public class SMAddInstanceObject extends SendablePacket<GameClient> {
    private final Collection<Servant> _objects;
    private final Player _owner;


    public SMAddInstanceObject(final Collection<Servant> objects, Player owner) {
        _objects = objects;
        _owner = owner;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeH(0);
        buffer.writeH(_objects.size());

        for (Servant vehicle : _objects) {
            final Player owner = vehicle.getOwner();
            final Location location = vehicle.getLocation();
            buffer.writeD(vehicle.getGameObjectId());
            buffer.writeD(-1024);
            buffer.writeF(location.getX());
            buffer.writeF(location.getZ());
            buffer.writeF(location.getY());
            buffer.writeF(location.getX());
            buffer.writeF(location.getZ());
            buffer.writeF(location.getY());
            buffer.writeF(location.getCos());
            buffer.writeD(0);
            buffer.writeF(location.getSin());
            buffer.writeH(vehicle.getCreatureId());
            buffer.writeH(vehicle.getCharacterGroup());
            buffer.writeF(vehicle.getGameStats().getHp().getCurrentHp());
            buffer.writeF(vehicle.getGameStats().getHp().getMaxHp());
            buffer.writeD(0);
            buffer.writeQ(_owner.getGuildCache());
            buffer.writeQ(0);
            buffer.writeH(0);
            buffer.writeQ(_owner.getPartyCache());
            buffer.writeC(0);
            buffer.writeQ(_owner.getActionStorage().getActionHash());
            buffer.writeQ(_owner.getAccountId());
            buffer.writeQ(_owner.getObjectId());
            buffer.writeD(100); // weight? mp?
            buffer.writeD(100); // weight? max mp?
            buffer.writeQ(0);
            buffer.writeD(-1024);
        }
    }
}




















