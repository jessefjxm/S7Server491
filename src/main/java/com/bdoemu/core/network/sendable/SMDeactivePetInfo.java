package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class SMDeactivePetInfo extends SendablePacket<GameClient> {
    private final Collection<Servant> pets;
    private final EPacketTaskType packetTaskType;

    public SMDeactivePetInfo(final Collection<Servant> pets, final EPacketTaskType type) {
        this.pets = pets;
        this.packetTaskType = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.pets.size());
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeC(0);
        for (final Servant pet : this.pets) {
            buffer.writeQ(pet.getObjectId());
            buffer.writeH(pet.getCreatureId());
            buffer.writeS(pet.getName(), 62);
            buffer.writeD(pet.getLevel());
            buffer.writeD(0);
            buffer.writeD(pet.getHunger());
            buffer.writeD(pet.getActionIndex());
            buffer.writeC(0);
            //buffer.writeC(0);
            buffer.writeQ(pet.getActionsBitMask().getMask());
            buffer.writeQ(pet.getEquipSkillsBitMask().getMask());
            buffer.writeQ(0L);
            buffer.writeC(0);
        }
    }
}
