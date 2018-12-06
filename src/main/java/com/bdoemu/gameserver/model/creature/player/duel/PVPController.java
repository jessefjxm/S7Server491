package com.bdoemu.gameserver.model.creature.player.duel;

import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.duel.enums.EPvpMatchState;
import com.bdoemu.gameserver.model.pvp.ELocalWarTeamType;
import com.bdoemu.gameserver.model.world.Location;

public class PVPController {
    private final Player owner;
    private EPvpMatchState pvpMatchState;
    private ELocalWarTeamType localWarTeamType;
    private PvpMatch pvpMatch;
    private long objectId;
    private boolean pvpEnable;
    private int localWarPoints;
    private Location returnPosition;

    public PVPController(final Player owner) {
        this.pvpMatchState = EPvpMatchState.Normal;
        this.localWarTeamType = ELocalWarTeamType.None;
        this.objectId = 0L;
        this.pvpEnable = false;
        this.localWarPoints = 0;
        this.owner = owner;
        returnPosition = null;
    }

    public void setReturnPosition(Location loc) {
        returnPosition = loc;
    }

    public Location getReturnPosition() {
        return returnPosition;
    }

    public long getObjectId() {
        return this.objectId;
    }

    public PvpMatch getPvpMatch() {
        return this.pvpMatch;
    }

    public EPvpMatchState getPvpMatchState() {
        return this.pvpMatchState;
    }

    public boolean isPvpEnable() {
        return this.pvpEnable;
    }

    public void setPvpEnable(final boolean pvpEnable) {
        this.pvpEnable = pvpEnable;
    }

    public void initDuel(final PvpMatch pvpMatch, final EPvpMatchState pvpMatchState) {
        this.pvpMatch = pvpMatch;
        this.pvpMatchState = pvpMatchState;
        this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.PvpPlayer);
    }

    public void resetDuel() {
        this.pvpMatch = null;
        this.objectId = 0L;
        this.pvpMatchState = EPvpMatchState.Normal;
    }

    public synchronized void addLocalWarPoints(final int points) {
        if (this.localWarPoints + points < 1) {
            this.localWarPoints = 1;
        } else {
            this.localWarPoints += points;
        }
    }

    public ELocalWarTeamType getLocalWarTeamType() {
        return this.localWarTeamType;
    }

    public void setLocalWarTeamType(final ELocalWarTeamType localWarTeamType) {
        this.localWarTeamType = localWarTeamType;
    }

    public int getLocalWarPoints() {
        return this.localWarPoints;
    }

    public void setLocalWarPoints(final int localWarPoints) {
        this.localWarPoints = localWarPoints;
    }
}