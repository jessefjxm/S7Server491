package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMTradeGameStart;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.trade.Bargain;

public class CMTradeGameStart extends ReceivablePacket<GameClient> {
    private int _npcSessionId;

    public CMTradeGameStart(final short opcode) {
        super(opcode);
    }

    protected void read() {
        _npcSessionId = readD();
    }

    public void runImpl() {
        Player player = getClient().getPlayer();
        if (player != null) {
            if (player.getCurrentWp() >= 5) {
                Bargain bargain = new Bargain(player, -3, 3, 3);
                bargain.createDice();
                player.setTradeShopBargain(bargain);
                player.addWp(-5);
            } else
                player.sendPacket(new SMNak(EStringTable.eErrNoMentalNotEnoughWp, CMTradeGameStart.class));
        }
    }
}
