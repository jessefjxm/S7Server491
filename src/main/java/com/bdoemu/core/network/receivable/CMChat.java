package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.chat.enums.EChatType;
import com.bdoemu.gameserver.model.chat.services.ChatService;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMChat extends ReceivablePacket<GameClient> {
    private byte type;
    private int slotIndex;
    private String msg;

    public CMChat(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.type = this.readC();
        readC();
        this.slotIndex = this.readCD();
        this.readD();
        this.readC();
        this.msg = this.readSS();
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null) {
            final EChatType chatType = EChatType.valueOf(this.type);
            if (chatType != null)
                ChatService.getInstance().sendMessage(player, this.msg, chatType, EChatNoticeType.Normal, this.slotIndex, 0, 0, null, null, this.getClass());
        }
    }
}
