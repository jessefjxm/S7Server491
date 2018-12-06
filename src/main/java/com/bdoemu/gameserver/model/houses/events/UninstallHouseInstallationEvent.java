// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.events;

import com.bdoemu.core.network.sendable.SMTentInformation;
import com.bdoemu.core.network.sendable.SMUnoccupyFixedHouseInstallation;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.houses.services.FixedHouseService;
import com.bdoemu.gameserver.model.items.Item;

import java.util.Collections;

public class UninstallHouseInstallationEvent implements IHouseEvent {
    private Player player;
    private HouseInstallation installation;
    private HouseHold houseHold;
    private Item addItem;

    public UninstallHouseInstallationEvent(final Player player, final HouseHold houseHold, final HouseInstallation installation, final Item addItem) {
        this.player = player;
        this.houseHold = houseHold;
        this.installation = installation;
        this.addItem = addItem;
    }

    @Override
    public void onEvent() {
        switch (this.houseHold.getFixedHouseType()) {
            case Tent: {
                this.houseHold.refreshLifeTime();
                this.houseHold.sendBroadcastPacket(new SMTentInformation(Collections.singleton(this.houseHold)));
                break;
            }
            case House: {
                this.houseHold.recalculatePoints();
                this.addItem.setEndurance(this.installation.getEndurance());
                FixedHouseService.getInstance().updateToTop(this.houseHold);
                break;
            }
        }
        this.player.sendBroadcastItSelfPacket(new SMUnoccupyFixedHouseInstallation(this.houseHold, this.installation));
    }

    @Override
    public boolean canAct() {
        switch (this.houseHold.getFixedHouseType()) {
            case House: {
                if (!this.player.getHouseVisit().isInHouse(this.houseHold.getCreatureId(), this.houseHold.getObjectId())) {
                    return false;
                }
                break;
            }
        }
        return this.houseHold.removeHouseInstallation(this.installation);
    }
}
