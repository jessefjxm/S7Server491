package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.stats.containers.GameStats;

public class SMSetCharacterSpeeds extends SendablePacket<GameClient> {
    private final GameStats stats;

    public SMSetCharacterSpeeds(final GameStats stats) {
        this.stats = stats;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.stats.getMoveSpeedRate().getMoveSpeedRate());
        buffer.writeD(this.stats.getAttackSpeedRate().getAttackSpeedRate());
        buffer.writeD(this.stats.getCastingSpeedRate().getCastingSpeedRate());
    }
}
