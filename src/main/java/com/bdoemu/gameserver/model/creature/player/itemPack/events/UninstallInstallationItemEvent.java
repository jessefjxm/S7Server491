// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.houses.HouseStorage;
import com.bdoemu.gameserver.model.houses.events.UninstallHouseInstallationEvent;
import com.bdoemu.gameserver.model.items.Item;

public class UninstallInstallationItemEvent extends AItemEvent {
    private long houseObjectId;
    private long installationObjId;
    private HouseInstallation installation;
    private HouseStorage houseStorage;
    private HouseHold houseHold;

    public UninstallInstallationItemEvent(final Player player, final long houseObjectId, final long installationObjId) {
        super(player, player, player, EStringTable.eErrNoHousingTentRemoveByUser, EStringTable.eErrNoHousingTentRemoveByUser, 0);
        this.houseObjectId = houseObjectId;
        this.installationObjId = installationObjId;
        this.houseStorage = player.getHouseStorage();
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        this.houseHold = this.player.getHouseholdController().getHouseHold(this.houseObjectId);
        if (this.houseHold == null) {
            return false;
        }
        this.installation = this.houseHold.getHouseInstallation(this.installationObjId);
        if (this.installation == null) {
            return false;
        }
        Item addItem = null;
        if (!this.installation.getObjectTemplate().getInstallationType().isHavest()) {
            addItem = this.addItem(this.installation.getItemId(), 1L, 0);
        }
        return super.canAct() && this.houseStorage.onEvent(new UninstallHouseInstallationEvent(this.player, this.houseHold, this.installation, addItem));
    }
}
