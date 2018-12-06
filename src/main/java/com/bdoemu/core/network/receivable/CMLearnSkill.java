// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMLearnSkill extends ReceivablePacket<GameClient> {
    private int skillUnk;
    private int skillLevel;
    private int skillId;

    public CMLearnSkill(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.skillUnk = this.readD();
        this.skillLevel = this.readH();
        this.skillId = this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getSkillList().learnSkill(this.skillId, false);
        }
    }
}
