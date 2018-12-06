// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMClearSkillPartly extends ReceivablePacket<GameClient> {
    private int skillId;
    private int skillLevel;

    public CMClearSkillPartly(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.skillLevel = this.readH();
        this.skillId = this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getSkillList().clearLearnedSkill(this.skillId);
        }
    }
}
