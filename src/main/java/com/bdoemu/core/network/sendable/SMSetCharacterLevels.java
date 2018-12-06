// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMSetCharacterLevels extends SendablePacket<GameClient> {
    private final Player player;
    private final boolean showAnimation;

    public SMSetCharacterLevels(final Player player) {
        this(player, false);
    }

    public SMSetCharacterLevels(final Player player, final boolean showAnimation) {
        this.player = player;
        this.showAnimation = showAnimation;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.player.getPartyCache());
        buffer.writeQ(this.player.getGuildCache());
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeD(this.player.getLevel());
        buffer.writeQ(this.player.getExp());
        buffer.writeQ((long) this.player.getPCTemplate().getRequireExp());
        buffer.writeF(0);
        buffer.writeD(0);
        buffer.writeC(this.showAnimation);
    }
}
