// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.commons.utils.HashUtil;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;

public class SMAiWorldNotifier extends SendablePacket<GameClient> {
    private String name;
    private EChatNoticeType chatNoticeType;
    private long sheetHash;
    private long messageHash;
    private int type;

    public SMAiWorldNotifier(final String name, final EChatNoticeType chatNoticeType, final String sheet, final String message, final int type) {
        this.name = name;
        this.chatNoticeType = chatNoticeType;
        this.sheetHash = HashUtil.generateHashC(sheet, "UTF-16LE");
        this.messageHash = HashUtil.generateHashC(message, "UTF-16LE");
        this.type = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeS((CharSequence) this.name, 62);
        buffer.writeC((int) this.chatNoticeType.getId());
        buffer.writeD(this.type);
        buffer.writeD(this.sheetHash);
        buffer.writeD(this.messageHash);
    }
}
