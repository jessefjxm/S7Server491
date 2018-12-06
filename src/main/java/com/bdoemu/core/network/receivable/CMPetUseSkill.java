// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMPetUseSkill;
import com.bdoemu.gameserver.model.creature.DeadBody;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMPetUseSkill extends ReceivablePacket<GameClient> {
    private long petObjId;
    private int bodySessionId;
    private int unk0;
    private int unk1;
    private int unk2;
    private int unk3;

    public CMPetUseSkill(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.petObjId = this.readQ();
        this.unk0 = this.readD();
        this.bodySessionId = this.readD();
        this.unk1 = this.readH();
        this.unk2 = this.readH();
        this.unk3 = this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && this.bodySessionId > 0) {
            final Servant pet = player.getServantController().getServant(this.petObjId);
            if (pet != null && pet.getServantState() == EServantState.Field) {
                final DeadBody deadBody = KnowList.getObject(player, ECharKind.Deadbody, this.bodySessionId);
                if (deadBody != null) {
                    deadBody.pickupDrop(player);
                    player.sendPacket(new SMPetUseSkill(this.petObjId, this.bodySessionId));
                }
            }
        }
    }
}
