// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.rmi.model.Macros;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.ArrayList;
import java.util.List;

public class CMSaveChatMacro extends ReceivablePacket<GameClient> {
    private short size;
    private List<Macros> macroses;

    public CMSaveChatMacro(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.size = this.readH();
        if (this.size > 0 && this.size <= 10) {
            this.macroses = new ArrayList<>();
            for (int i = 0; i < this.size; ++i) {
                final int index = this.readD();
                final byte type = this.readC();
                final String macrosData = this.readS(702);
                this.macroses.add(new Macros(macrosData, type, index));
            }
        }
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null && this.macroses != null) {
            for (final Macros macros : this.macroses) {
                if (player.getAccountData() != null && player.getAccountData().getMacroses() != null)
                    player.getAccountData().getMacroses()[macros.getIndex()] = macros;
            }
        }
    }
}
