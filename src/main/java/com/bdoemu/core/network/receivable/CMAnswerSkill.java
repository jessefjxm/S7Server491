// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.answer.SkillAnswer;

public class CMAnswerSkill extends ReceivablePacket<GameClient> {
    private int gameObjId;
    private boolean answer;

    public CMAnswerSkill(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.gameObjId = this.readD();
        this.answer = this.readCB();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final SkillAnswer skillAnswer = player.getAnswerStorage().getSkillAnswer();
            if (skillAnswer != null) {
                player.getAnswerStorage().setSkillAnswer(null);
                if (this.answer) {
                    skillAnswer.getActiveBuff().applyEffect();
                }
            }
        }
    }
}
