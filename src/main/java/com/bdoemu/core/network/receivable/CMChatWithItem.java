package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.chat.enums.EChatType;
import com.bdoemu.gameserver.model.chat.services.ChatService;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMChatWithItem extends ReceivablePacket<GameClient> {
    private EChatType chatType;
    private EChatNoticeType chatNoticeType;
    private int itemId;
    private int itemEnchant;
    private int slotIndex;
    private int[] jewelData;
    private byte[] colorData;
    private String message;

    public CMChatWithItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.chatType = EChatType.valueOf(this.readC());
        this.chatNoticeType = EChatNoticeType.valueOf(this.readC());
        this.readC();
        readC();
        this.slotIndex = this.readCD();
        this.readD();
        this.itemId = this.readH();
        this.itemEnchant = this.readH();
        (this.jewelData = new int[4])[0] = this.readD();
        this.jewelData[1] = this.readD();
        this.jewelData[2] = this.readD();
        this.jewelData[3] = this.readD();
        this.colorData = this.readB(25);
        this.readC();
        this.readB(62);
        this.message = this.readSS();
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null) {
            ChatService.getInstance().sendMessage(player, this.message, this.chatType, this.chatNoticeType, this.slotIndex, this.itemId, this.itemEnchant, this.jewelData, this.colorData, this.getClass());
        }
    }
}
