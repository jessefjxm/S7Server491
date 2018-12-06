package com.bdoemu.gameserver.model.creature.player.duel;

import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.duel.enums.EPvpMatchType;

import java.util.concurrent.ConcurrentHashMap;

public class PvpMatch {
    private final int matchObjectId;
    private final EPvpMatchType pvpType;
    private final ConcurrentHashMap<Integer, Player> participants;

    public PvpMatch(final EPvpMatchType pvpType) {
        this.participants = new ConcurrentHashMap<>();
        this.pvpType = pvpType;
        this.matchObjectId = (int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.PvpMatches);
    }

    public void addParticipant(final Player participant) {
        this.participants.put(participant.getGameObjectId(), participant);
    }

    public boolean removeParticipant(final Player participant) {
        return this.participants.remove(participant.getGameObjectId()) != null;
    }

    public boolean hasParticipant(final int gameObjectId) {
        return this.participants.containsKey(gameObjectId);
    }

    public ConcurrentHashMap<Integer, Player> getParticipants() {
        return this.participants;
    }

    public EPvpMatchType getPvpType() {
        return this.pvpType;
    }

    public int getMatchObjectId() {
        return this.matchObjectId;
    }
}
