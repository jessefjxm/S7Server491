// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;

import java.util.Collection;
import java.util.Collections;

public class SMSetMyServantPoints extends SendablePacket<GameClient> {
    private final Collection<Servant> servants;

    public SMSetMyServantPoints(final Servant servant) {
        this.servants = Collections.singleton(servant);
    }

    public SMSetMyServantPoints(final Player owner) {
        this.servants = owner.getServantController().getServants(EServantState.Field, EServantType.Ship, EServantType.Vehicle);
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.servants.size());
        for (final Servant servant : this.servants) {
            buffer.writeD(servant.getGameObjectId());
            buffer.writeD(servant.getGameStats().getHp().getIntMaxValue());
            buffer.writeD(servant.getGameStats().getHp().getIntValue());
            buffer.writeF(servant.getLocation().getX());
            buffer.writeF(servant.getLocation().getZ());
            buffer.writeF(servant.getLocation().getY());
        }
    }
}
