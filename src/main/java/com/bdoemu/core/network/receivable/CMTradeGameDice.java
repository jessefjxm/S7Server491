package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMTradeGameDice;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.ETradeGameResult;
import com.bdoemu.gameserver.model.items.enums.ETradeGameSwitchType;
import com.bdoemu.gameserver.utils.MathUtils;

public class CMTradeGameDice extends ReceivablePacket<GameClient> {
    private ETradeGameSwitchType _tradeGameSwitchType;

    public CMTradeGameDice(final short opcode) {
        super(opcode);
    }

    protected void read() {
        _tradeGameSwitchType = ETradeGameSwitchType.values()[readC()];
    }

    public void runImpl() {
        Player player = getClient().getPlayer();
        if (player != null) {
            if (player.getTradeShopBargain() != null)
                player.getTradeShopBargain().dice(_tradeGameSwitchType);
        }
    }
}