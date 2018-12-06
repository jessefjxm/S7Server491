package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;

import java.util.Collection;

public class SMActivePetPrivateInfo extends SendablePacket<GameClient> {
    private final Collection<Servant> pets;

    public SMActivePetPrivateInfo(final Collection<Servant> pets) {
        this.pets = pets;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.pets.size());
        for (final Servant pet : this.pets) {
            buffer.writeQ(pet.getObjectId());
            buffer.writeS(pet.getName(), 62);
            buffer.writeD(pet.getLevel());
            buffer.writeQ(pet.getExp());
            buffer.writeD(0);
            buffer.writeC(0); // Pet Looting Type
        }
    }
}
