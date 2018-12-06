package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.chat.enums.EChatType;

public class SMChat extends SendablePacket<GameClient> {
    private final EChatNoticeType notice;
    private final EChatType chatType;
    private final String msg;
    private final int playerGameObjectId;
    private final String playerName;
    private final String familyName;

    public SMChat(final String name, final String familyName, final int gameObjId, final EChatType chatType, final EChatNoticeType notice, final String msg) {
        this.playerGameObjectId = gameObjId;
        this.playerName = name;
        this.familyName = familyName;
        this.chatType = chatType;
        this.notice = notice;
        this.msg = msg;
    }

    public SMChat(final EChatType chatType, final EChatNoticeType notice, final String msg) {
        this.playerGameObjectId = -1024;
        this.playerName = "System";
        this.familyName = "";
        this.chatType = chatType;
        this.notice = notice;
        this.msg = msg;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC((int) this.chatType.getId());
        buffer.writeC((int) this.notice.getId());
        buffer.writeD(this.playerGameObjectId);
        buffer.writeS(this.playerName, 62);
        buffer.writeS(this.familyName, 62);
        buffer.writeC(1);
        buffer.writeC(0);
        buffer.writeC(1);
        buffer.writeSS(this.msg);
    }
}
