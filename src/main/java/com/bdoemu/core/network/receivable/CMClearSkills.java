// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;

public class CMClearSkills extends ReceivablePacket<GameClient> {
    public CMClearSkills(final short opcode) {
        super(opcode);
    }

    protected void read() {
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && (player.getAccountData().getChargeUserStorage().isActiveChargeUserEffect(EChargeUserType.UnlimitedSkillAwakening) || player.getLevel() < 56)) {
            player.getSkillList().clearLearnedSkills();
        }
    }
}
