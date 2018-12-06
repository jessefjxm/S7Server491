package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMCantFindWhisperPlayer;
import com.bdoemu.gameserver.model.chat.services.ChatService;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.worldInstance.World;

public class CMWhisper extends ReceivablePacket<GameClient> {
    private int     _whisperType;
    private String  _name;
    private String  _msg;

    public CMWhisper(final short opcode) {
        super(opcode);
    }

    protected void read() {
        _whisperType = readC();
        _name = readS(62);
        _msg = readSS();
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null) {
            final Player target = _whisperType > 0
                    ? World.getInstance().getPlayerByFamily(_name)
                    : World.getInstance().getPlayer(_name);
            if (target == null) {
                player.sendPacket(new SMCantFindWhisperPlayer(_name));
                return;
            }
            ChatService.getInstance().sendPrivateMessage(player, target, _msg);
        }
    }
}