// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.events;

import com.bdoemu.core.network.sendable.SMListFixedHouseInstallations;
import com.bdoemu.core.network.sendable.SMRequestJukebox;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collections;

public class RequestJukeboxEvent implements IHouseEvent {
    private Player player;
    private long installationObjId;
    private int id;
    private int type;
    private HouseInstallation houseInstallation;
    private HouseHold houseHold;

    public RequestJukeboxEvent(final Player player, final long installationObjId, final int id, final int type) {
        this.player = player;
        this.installationObjId = installationObjId;
        this.id = id;
        this.type = type;
    }

    @Override
    public void onEvent() {
        if (!ItemData.getInstance().getItemTemplate(this.houseInstallation.getItemId()).isCash()) {
            this.houseInstallation.setEndurance(this.houseInstallation.getEndurance() - 1);
            this.player.sendBroadcastItSelfPacket(new SMListFixedHouseInstallations(this.houseHold, Collections.singleton(this.houseInstallation), EPacketTaskType.Add));
        }
        this.player.sendBroadcastItSelfPacket(new SMRequestJukebox(this.houseInstallation, this.id, this.type));
    }

    @Override
    public boolean canAct() {
        this.houseHold = this.player.getHouseholdController().getHouseHold(this.player.getHouseVisit().getHouseObjectId());
        if (this.houseHold == null || this.houseHold.getAccountId() != this.player.getAccountId()) {
            return false;
        }
        this.houseInstallation = this.houseHold.getHouseInstallation(this.installationObjId);
        return this.houseInstallation != null && this.houseInstallation.getObjectTemplate().getInstallationType().isJukebox() && this.houseInstallation.getEndurance() > 0 && this.player.getHouseVisit().isInHouse(this.houseHold.getCreatureId(), this.houseHold.getObjectId());
    }
}
