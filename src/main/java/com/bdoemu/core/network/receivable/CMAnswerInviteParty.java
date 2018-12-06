package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMRefuseInviteParty;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.party.service.PartyService;
import com.bdoemu.gameserver.worldInstance.World;

public class CMAnswerInviteParty extends ReceivablePacket<GameClient> {
    private int gameObjId;
    private boolean answer;

    public CMAnswerInviteParty(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.gameObjId = this.readD();
        this.answer = this.readCB();
        this.readD();
        readC();
    }

    public void runImpl() {
        final Player invitedMember = this.getClient().getPlayer();
        if (invitedMember != null) {
            final Player ownerMember = World.getInstance().getPlayer(this.gameObjId);
            if (ownerMember != null) {
                if (this.answer) {
                    PartyService.getInstance().createParty(ownerMember, invitedMember);
                } else {
                    ownerMember.sendPacket(new SMRefuseInviteParty(invitedMember.getName(), EStringTable.eErrNoRequestRefuse));
                }
            }
        }
    }
}
