// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.mongodb.BasicDBObject;

import java.util.Collection;

public class SMListEnchantFailCountOfMyCharacter extends SendablePacket<GameClient> {
    private final Player player;

    public SMListEnchantFailCountOfMyCharacter(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        final Collection<BasicDBObject> players = this.player.getLoginAccount().getPlayers().values();
        buffer.writeH(players.size());
        for (final BasicDBObject playerDB : players) {
            final long objId = playerDB.getLong("_id");
            buffer.writeQ(objId);
            if (objId == this.player.getObjectId()) {
                buffer.writeQ((long) this.player.getEnchantFailCount());
            } else {
                buffer.writeQ(playerDB.getLong("enchantFailCount"));
            }
        }
    }
}
