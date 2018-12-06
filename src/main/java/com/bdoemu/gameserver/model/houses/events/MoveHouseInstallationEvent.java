// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMMoveInstallation;
import com.bdoemu.core.network.sendable.SMListFixedHouseInstallations;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.houses.HouseStorage;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.world.Location;

import java.util.Collections;

public class MoveHouseInstallationEvent implements IHouseEvent {
    private Player player;
    private long objectId;
    private long installationObjId;
    private Location loc;
    private HouseInstallation houseInstallation;
    private HouseStorage houseStorage;
    private HouseHold houseHold;

    public MoveHouseInstallationEvent(final Player player, final long objectId, final long installationObjId, final Location loc) {
        this.player = player;
        this.objectId = objectId;
        this.installationObjId = installationObjId;
        this.loc = loc;
        this.houseStorage = player.getHouseStorage();
    }

    @Override
    public void onEvent() {
        this.houseInstallation.setLoc(this.loc);
        this.player.sendBroadcastItSelfPacket(new SMListFixedHouseInstallations(this.houseHold, Collections.singleton(this.houseInstallation), EPacketTaskType.Add));
    }

    @Override
    public boolean canAct() {
        this.houseHold = this.player.getHouseholdController().getHouseHold(this.objectId);
        if (this.houseHold == null) {
            return false;
        }
        this.houseInstallation = this.houseHold.getHouseInstallation(this.installationObjId);
        if (this.houseInstallation == null) {
            return false;
        }
        if (this.houseInstallation.getObjectTemplate().getInstallationType().isHavest()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoHarvestCanNotBeMoved, CMMoveInstallation.class));
            return false;
        }
        switch (this.houseHold.getFixedHouseType()) {
            case House: {
                if (!this.player.getHouseVisit().isInHouse(this.houseHold.getCreatureId(), this.objectId)) {
                    return false;
                }
                break;
            }
        }
        return true;
    }
}
