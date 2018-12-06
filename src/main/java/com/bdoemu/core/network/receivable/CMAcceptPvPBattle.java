package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.duel.services.PvPBattleService;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.service.LocalWarService;

public class CMAcceptPvPBattle extends ReceivablePacket<GameClient> {
    private int gameObjectId;
    private boolean result;
    private boolean unk;

    public CMAcceptPvPBattle(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.gameObjectId = this.readD();
        this.result = this.readCB();
        this.unk = this.readCB();
        this.readC();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Player opponent = KnowList.getObject(player, ECharKind.Player, this.gameObjectId);
            if (opponent != null) {

                // You cannot challenge to a duel if you're already in a duel.
                if (player.hasPvpMatch()) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoPvPMatchCantAcceptDuringPvPMatch, CMAcceptPvPBattle.class));
                    return;
                }

                // The opponent is in a duel.
                if (opponent.hasPvpMatch()) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoPvPMatchCantRequestAlreadyPlayingUser, CMAcceptPvPBattle.class));
                    return;
                }

                // The opponent is in a duel.
                if (opponent.hasPvpMatch()) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoPvPMatchCantRequestAlreadyPlayingUser, CMAcceptPvPBattle.class));
                    return;
                }

                // You cannot enter the match while in a party.
                if (player.hasPvpMatch() || opponent.hasPvpMatch()) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoCantInviteCompetitionWhileParty, CMAcceptPvPBattle.class));
                    return;
                }

                // Not in a duel mode.
                if (LocalWarService.getInstance().hasParticipant(player)) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoPVPMatchCantAcceptDeny, CMAcceptPvPBattle.class));
                    return;
                }

                PvPBattleService.getInstance().acceptDuel(player, opponent, this.result);
            }
        }
    }
}
