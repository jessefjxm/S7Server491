// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMEndServantSkillExpTraining;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;

public class CMEndServantSkillExpTraining extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private long servantObjId;

    public CMEndServantSkillExpTraining(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.servantObjId = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Servant servant = player.getServantController().getServant(this.servantObjId);
            if (servant != null && servant.getServantState().skillTraining() && servant.getSkillTrainingTime() < 0L) {
                servant.setServantState(EServantState.Stable);
                player.sendPacket(new SMEndServantSkillExpTraining(this.servantObjId));
            }
        }
    }
}
