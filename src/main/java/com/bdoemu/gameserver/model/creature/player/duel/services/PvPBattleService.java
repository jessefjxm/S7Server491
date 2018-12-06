package com.bdoemu.gameserver.model.creature.player.duel.services;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMAcceptPvPBattle;
import com.bdoemu.core.network.sendable.SMAskPvPBattle;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMStartMatch;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.duel.PvpMatch;
import com.bdoemu.gameserver.model.creature.player.duel.enums.EPvpMatchState;
import com.bdoemu.gameserver.model.creature.player.duel.enums.EPvpMatchType;
import com.bdoemu.gameserver.model.team.party.PlayerParty;
import com.bdoemu.gameserver.model.team.party.events.AcceptPvpMatchPartyEvent;
import com.bdoemu.gameserver.model.team.party.events.AskPvpMatchPartyEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Service")
public class PvPBattleService {
    private static class Holder {
        static final PvPBattleService INSTACE = new PvPBattleService();
    }
    private final ConcurrentHashMap<Integer, PvpMatch> matches;

    public PvPBattleService() {
        this.matches = new ConcurrentHashMap<>();
    }

    public static PvPBattleService getInstance() {
        return Holder.INSTACE;
    }

    public void cancelDuel(final Player player) {
        final PvpMatch pvpMatch = player.getPvpMatch();
        if (pvpMatch != null && pvpMatch.removeParticipant(player)) {
            player.getPVPController().resetDuel();
            player.sendBroadcastItSelfPacket(new SMStartMatch(player));
            if (pvpMatch.getParticipants().isEmpty()) {
                this.matches.remove(pvpMatch.getMatchObjectId());
            }
        }
    }

    public void askDuel(final Player player, final Player opponent) {
        final PlayerParty opponentParty = (PlayerParty) opponent.getParty();
        if (opponentParty == null || !opponentParty.onEvent(new AskPvpMatchPartyEvent(opponent, opponentParty, player))) {
            opponent.sendPacket(new SMAskPvPBattle(player.getGameObjectId()));
        }
    }

    public synchronized void acceptDuel(final Player player, final Player opponent, final boolean result) {
        if (result) {
            if (player.hasPvpMatch()) {
                player.sendPacket(new SMNak(EStringTable.eErrNoPvPMatchCantAcceptDuringPvPMatch, CMAcceptPvPBattle.class));
                return;
            }
            if (opponent.hasPvpMatch()) {
                player.sendPacket(new SMNak(EStringTable.eErrNoPvPMatchCantRequestAlreadyPlayingUser, CMAcceptPvPBattle.class));
                return;
            }
            final PvpMatch pvpMatch = new PvpMatch(EPvpMatchType.Duel);
            this.matches.put(pvpMatch.getMatchObjectId(), pvpMatch);
            this.registerMatch(player, pvpMatch);
            this.registerMatch(opponent, pvpMatch);
        }
    }

    private void registerMatch(final Player player, final PvpMatch pvpMatch) {
        final PlayerParty party = (PlayerParty) player.getParty();
        if (party == null || !party.onEvent(new AcceptPvpMatchPartyEvent(player, party, pvpMatch))) {
            pvpMatch.addParticipant(player);
            player.getPVPController().initDuel(pvpMatch, EPvpMatchState.Duel);
            player.sendBroadcastItSelfPacket(new SMStartMatch(player));
        }
    }
}
