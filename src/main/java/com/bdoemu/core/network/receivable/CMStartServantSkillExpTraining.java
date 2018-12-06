// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.StartServantSkillExpTrainingItemEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMStartServantSkillExpTraining extends ReceivablePacket<GameClient> {
    private int npcGameObjId;
    private int skillId;
    private int slotIndex;
    private long servantObjId;
    private EItemStorageLocation storageLocation;

    public CMStartServantSkillExpTraining(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcGameObjId = this.readD();
        this.servantObjId = this.readQ();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.skillId = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Servant servant = player.getServantController().getServant(this.servantObjId);
            if (servant != null && servant.getServantState().isStable()) {
                player.getPlayerBag().onEvent(new StartServantSkillExpTrainingItemEvent(player, servant, this.storageLocation, this.slotIndex, this.skillId));
            }
        }
    }
}
