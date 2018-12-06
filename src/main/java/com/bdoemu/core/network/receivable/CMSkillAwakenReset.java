// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.SkillAwakenResetItemEvent;

public class CMSkillAwakenReset extends ReceivablePacket<GameClient> {
    private int skillId;
    private int slotIndex;

    public CMSkillAwakenReset(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.skillId = this.readH();
        this.slotIndex = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new SkillAwakenResetItemEvent(player, this.skillId, this.slotIndex));
        }
    }
}
