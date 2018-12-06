package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMAskInviteParty;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.services.InstanceSummonService;
import com.bdoemu.gameserver.service.LocalWarService;
import com.bdoemu.gameserver.worldInstance.World;

public class CMInviteParty extends ReceivablePacket<GameClient> {
    private int invitedGameObjId;
    private String invitedtName;

    public CMInviteParty(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.invitedGameObjId = this.readD();
        this.invitedtName = this.readS(62);
        readC();
    }

    public void runImpl() {
        final Player ownerMember = this.getClient().getPlayer();
        if (ownerMember != null) {
            if (LocalWarService.getInstance().hasParticipant(ownerMember)) {
                ownerMember.sendPacket(new SMNak(EStringTable.eErrNoLocalwarCantJoinParty, this.opCode));
                return;
            }

            Player invitedMember = World.getInstance().getPlayer(this.invitedGameObjId);
            if (invitedMember == null) {
                invitedMember = World.getInstance().getPlayer(this.invitedtName);
            }
            if (invitedMember != null) {
                if (InstanceSummonService.getInstance().contains(ownerMember.getAccountId()) || InstanceSummonService.getInstance().contains(invitedMember.getAccountId())) {
                    this.sendPacket(new SMNak(EStringTable.eErrNoDontInvitePartyBySummonMonter, this.opCode));
                    return;
                }

                if (invitedMember.hasPvpMatch()) {
                    this.sendPacket(new SMNak(EStringTable.eErrNoPvPMatchDontInviteParty, this.opCode));
                    return;
                }
                invitedMember.sendPacket(new SMAskInviteParty(ownerMember.getName(), ownerMember.getGameObjectId()));
            }
        }
    }
}
