package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.pvp.LocalWarStatus;
import com.bdoemu.gameserver.service.LocalWarService;

public class CMUnjoinLocalWar extends ReceivablePacket<GameClient> {
    public CMUnjoinLocalWar(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final LocalWarStatus localWarStatus = LocalWarService.getInstance().getLocalWarStatus();
            if (localWarStatus.canExitFromLocalWar(player)) {
                localWarStatus.exitFromLocalWar(player, false);
            } else {
                player.sendPacket(new SMNak(EStringTable.eErrNoCantUnjoinLocalwarUntilOneMinutePast, this.opCode));
            }
        }
    }
}
