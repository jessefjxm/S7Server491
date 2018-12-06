// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;

public class SMActivePetPublicInfo extends SendablePacket<GameClient> {
    private final Collection<Servant> pets;
    private final EPacketTaskType type;

    public SMActivePetPublicInfo(final Collection<Servant> pets, final EPacketTaskType type) {
        this.pets = pets;
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.type.ordinal());
        buffer.writeH(this.pets.size());
        buffer.writeC(0);
        for (final Servant pet : this.pets) {
            buffer.writeD(pet.getGameObjectId());
            buffer.writeQ(pet.getObjectId());
            buffer.writeH(pet.getCreatureId());
            buffer.writeD(pet.getHunger());
            buffer.writeD(pet.getActionIndex());
            buffer.writeC(0);
            buffer.writeQ(pet.getActionsBitMask().getMask());
            buffer.writeQ(pet.getEquipSkillsBitMask().getMask());
        }
    }
}
