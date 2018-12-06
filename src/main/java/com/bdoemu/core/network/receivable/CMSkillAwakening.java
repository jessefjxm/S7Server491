// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMSkillAwakening extends ReceivablePacket<GameClient> {
    private int skillId;
    private int slotIndex;
    private int abillity;
    private int npcGameObjId;
    private byte type;
    private EItemStorageLocation storageLocation;

    public CMSkillAwakening(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.skillId = this.readH();
        this.type = this.readC();
        this.slotIndex = this.readCD();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.abillity = this.readD();
        this.npcGameObjId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcGameObjId);
            if (npc != null && npc.getTemplate().getCreatureFunctionT().isAwakenSkill() && this.type == 0 && Integer.bitCount(this.abillity) == 2) {
                player.getSkillList().learnAwakeningSkill(this.skillId, this.abillity);
            }
        }
    }
}
