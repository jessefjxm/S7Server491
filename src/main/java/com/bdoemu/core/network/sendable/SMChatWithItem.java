// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.chat.enums.EChatType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class SMChatWithItem extends SendablePacket<GameClient> {
    private final Player player;
    private final EChatType chatType;
    private final EChatNoticeType chatNoticeType;
    private final int itemId;
    private final int itemEnchant;
    private final int[] jewelData;
    private final byte[] colorData;
    private final String message;

    public SMChatWithItem(final Player player, final String msg, final EChatType chatType, final EChatNoticeType chatNoticeType, final int itemId, final int itemEnchant, final int[] jewelData, final byte[] colorData) {
        this.player = player;
        this.chatType = chatType;
        this.chatNoticeType = chatNoticeType;
        this.itemId = itemId;
        this.itemEnchant = itemEnchant;
        this.jewelData = jewelData;
        this.colorData = colorData;
        this.message = msg;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC((int) this.chatType.getId());
        buffer.writeC((int) this.chatNoticeType.getId());
        buffer.writeC(13);
        buffer.writeD(this.player.getGameObjectId());
        buffer.writeS(this.player.getName(), 62);
        buffer.writes(this.player.getFamily(), 62);
        buffer.writeC(1);
        buffer.writeC(0);
        buffer.writeH(this.itemId);
        buffer.writeH(this.itemEnchant);
        buffer.writeD(this.jewelData[0]);
        buffer.writeD(this.jewelData[1]);
        buffer.writeD(this.jewelData[2]);
        buffer.writeD(this.jewelData[3]);
        buffer.writeB(this.colorData);
        buffer.writeSS(this.message);
    }
}
