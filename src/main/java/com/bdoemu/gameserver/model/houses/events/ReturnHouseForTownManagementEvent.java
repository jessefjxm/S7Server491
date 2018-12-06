// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMReturnHouseForTownManagement;
import com.bdoemu.core.network.sendable.SMDelMaidInfoByHouseholdNo;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMReturnHouseForTownManagement;
import com.bdoemu.core.network.sendable.SMUnoccupyHouseholdFixed;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorkerController;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.Warehouse;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ReturnHouseItemsToWarehouseEvent;
import com.bdoemu.gameserver.model.houses.House;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseStorage;
import com.bdoemu.gameserver.model.houses.enums.EFixedHouseType;
import com.bdoemu.gameserver.model.houses.services.FixedHouseService;

import java.util.Optional;

public class ReturnHouseForTownManagementEvent implements IHouseEvent {
    private Player player;
    private int houseId;
    private HouseStorage houseStorage;
    private House house;

    public ReturnHouseForTownManagementEvent(final Player player, final int houseId) {
        this.player = player;
        this.houseId = houseId;
        this.houseStorage = player.getHouseStorage();
    }

    @Override
    public void onEvent() {
        this.houseStorage.removeHouse(this.houseId);
        if (this.house.getHouseReceipeType().isEmpty()) {
            final Optional<HouseHold> result = this.player.getHouseholdController().getHouseHolds(EFixedHouseType.House).stream().filter(fh -> fh.getCreatureId() == this.houseId).findFirst();
            final HouseHold houseHold = result.get();
            this.player.getHouseholdController().removeHouseHold(houseHold.getObjectId());
            FixedHouseService.getInstance().removeFromTop(houseHold);
            final int regionId = this.house.getHouseInfoT().getAffiliatedTown();
            this.player.sendPacket(new SMDelMaidInfoByHouseholdNo(regionId, houseHold.getObjectId()));
            if (houseHold.getInstallationMap().size() > 0) {
                this.player.getPlayerBag().onEvent(new ReturnHouseItemsToWarehouseEvent(this.player, regionId, houseHold.getInstallationMap().values()));
            }
            this.player.sendPacket(new SMUnoccupyHouseholdFixed(houseHold));
        }
        this.player.sendPacket(new SMReturnHouseForTownManagement(this.houseId));
    }

    @Override
    public boolean canAct() {
        if (this.houseStorage.getHouseLargerCraft(this.houseId) != null) {
            return false;
        }
        this.house = this.houseStorage.getHouse(this.houseId);
        if (this.house == null) {
            return false;
        }
        if (this.house.getHouseReceipeType().isEmpty()) {
            final Optional<HouseHold> result = this.player.getHouseholdController().getHouseHolds(EFixedHouseType.House).stream().filter(fixedHouse -> fixedHouse.getCreatureId() == this.houseId).findFirst();
            if (!result.isPresent()) {
                return false;
            }
            final HouseHold houseHold = result.get();
            if (this.player.getHouseVisit().isInHouse(houseHold.getCreatureId(), houseHold.getObjectId())) {
                return false;
            }
        }
        if (this.house.getHouseReceipeType().isLodging()) {
            final int regionId = this.house.getHouseInfoT().getAffiliatedTown();
            final int maxSlots = this.player.getNpcWorkerController().getWorkerSlotsByRegionId(this.house.getHouseInfoT().getAffiliatedTown()) - NpcWorkerController.getSlotsByLodgingLevel(this.house.getLevel());
            if (this.player.getNpcWorkerController().size(regionId) > maxSlots) {
                return false;
            }
        }
        if (this.house.getReceipeForTownT().getHouseReceipeType().isDepot()) {
            final Warehouse warehouse = this.player.getPlayerBag().getWarehouse(this.house.getHouseInfoT().getAffiliatedTown());
            if (warehouse.getItemSize() > warehouse.getExpandSize() + warehouse.getAffiliatedHouseSlots()) {
                this.player.sendPacket(new SMNak(EStringTable.eErrNoWarehouseIsntFull, CMReturnHouseForTownManagement.class));
                return false;
            }
        }
        return this.player.getExplorePointHandler().getMainExplorePoint().addPoints(this.house.getHouseInfoT().getNeedExplorePoint());
    }
}
