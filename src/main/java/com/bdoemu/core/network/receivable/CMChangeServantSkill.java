// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ChangeServantSkillItemEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMChangeServantSkill extends ReceivablePacket<GameClient> {
    private long servantObjId;
    private int npcGameObjId;
    private int slotIndex;
    private int removedSkillId;
    private int hopeSkillId;
    private EItemStorageLocation storageLocation;

    public CMChangeServantSkill(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.servantObjId = this.readQ();
        this.npcGameObjId = this.readD();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.removedSkillId = this.readCD();
        this.hopeSkillId = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Servant servant = player.getServantController().getServant(this.servantObjId);
            if (servant != null && servant.getServantState().isStable()) {
                player.getPlayerBag().onEvent(new ChangeServantSkillItemEvent(player, servant, this.storageLocation, this.slotIndex, this.removedSkillId, this.hopeSkillId));
            }
        }
    }
}
