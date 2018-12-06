// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.databaseCollections.ServantsDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Collection;

public class SMGetPetListByHouse extends SendablePacket<GameClient> {
    private final long houseObjId;
    private Collection<Servant> pets;

    public SMGetPetListByHouse(final long houseObjId, final long accountId) {
        this.houseObjId = houseObjId;
        final Player player = World.getInstance().getPlayerByAccount(accountId);
        if (player != null) {
            this.pets = player.getServantController().getServants(EServantState.Stable, EServantType.Pet);
        } else {
            this.pets = ServantsDBCollection.getInstance().loadPets(accountId).values();
        }
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.houseObjId);
        buffer.writeH(this.pets.size());
        for (final Servant pet : this.pets) {
            buffer.writeQ(pet.getObjectId());
            buffer.writeH(pet.getCreatureId());
            buffer.writeS((CharSequence) pet.getName(), 62);
            buffer.writeD(pet.getActionIndex());
        }
    }
}
