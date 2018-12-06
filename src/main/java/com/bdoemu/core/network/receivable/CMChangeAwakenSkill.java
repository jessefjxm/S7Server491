// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.SkillAwakeningItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;

public class CMChangeAwakenSkill extends ReceivablePacket<GameClient> {
    private int oldSkillId;
    private int newSkillId;
    private int abilityId;
    private int slotIndex;
    private int type;
    private int npcGameObjId;
    private EItemStorageLocation storageLocation;

    public CMChangeAwakenSkill(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.oldSkillId = this.readHD();
        this.newSkillId = this.readHD();
        this.abilityId = this.readD();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.npcGameObjId = this.readD();
        this.type = this.readC();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && Integer.bitCount(this.abilityId) == 2) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcGameObjId);
            if (npc != null && npc.getTemplate().getCreatureFunctionT().isAwakenSkill()) {
                if (player.getAccountData().getChargeUserStorage().isActiveChargeUserEffect(EChargeUserType.UnlimitedSkillChange)) {
                    player.getSkillList().changeAweakenSkill(this.oldSkillId, this.newSkillId, this.abilityId);
                } else if (player.getPlayerBag().onEvent(new SkillAwakeningItemEvent(player, this.storageLocation, this.slotIndex))) {
                    player.getSkillList().changeAweakenSkill(this.oldSkillId, this.newSkillId, this.abilityId);
                }
            }
        }
    }
}
