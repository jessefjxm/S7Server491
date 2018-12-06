// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.MakeThemeBookEvent;

public class CMMakeThemeBook extends ReceivablePacket<GameClient> {
    private int themeId;

    public CMMakeThemeBook(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.themeId = this.readHD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new MakeThemeBookEvent(player, this.themeId));
        }
    }
}
