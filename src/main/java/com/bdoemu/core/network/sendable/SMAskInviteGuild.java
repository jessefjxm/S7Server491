package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMAskInviteGuild extends SendablePacket<GameClient> {
    private final String invitorName;
    private final String familyName;
    private final String guildName;
    private final int gameObjectId;
    private final int term;
    private final long dailyPay;
    private final long penalty;
    private final long guildObjectId;

    public SMAskInviteGuild(final String invitorName, final String familyName, final String guildName, final int gameObjectId, final int term, final long dailyPay, final long penalty, final long guildObjectId) {
        this.invitorName = invitorName;
        this.familyName = familyName;
        this.guildName = guildName;
        this.gameObjectId = gameObjectId;
        this.term = term;
        this.dailyPay = dailyPay;
        this.penalty = penalty;
        this.guildObjectId = guildObjectId;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeS(this.familyName, 62);
        buffer.writeS(this.invitorName, 62);
        buffer.writeQ(this.guildObjectId);
        buffer.writeS(this.guildName, 62);
        buffer.writeD(this.gameObjectId);
        buffer.writeC(1);
        buffer.writeD(this.term);
        buffer.writeQ(this.dailyPay);
        buffer.writeQ(this.penalty);
    }
}
