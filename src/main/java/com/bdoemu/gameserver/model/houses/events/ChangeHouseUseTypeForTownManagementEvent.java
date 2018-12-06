// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMChangeHouseUseTypeForTownManagement;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.dataholders.HouseData;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorkerController;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.Warehouse;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.BuyHouseItemEvent;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ReturnHouseItemsToWarehouseEvent;
import com.bdoemu.gameserver.model.houses.House;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseStorage;
import com.bdoemu.gameserver.model.houses.HouseholdController;
import com.bdoemu.gameserver.model.houses.enums.EFixedHouseType;
import com.bdoemu.gameserver.model.houses.services.FixedHouseService;
import com.bdoemu.gameserver.model.houses.templates.HouseInfoT;
import com.bdoemu.gameserver.model.houses.templates.HouseTransferT;
import com.bdoemu.gameserver.model.houses.templates.ReceipeForTownT;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collection;
import java.util.Optional;

public class ChangeHouseUseTypeForTownManagementEvent implements IHouseEvent {
    private Player player;
    private int houseId;
    private int recipeKey;
    private HouseStorage houseStorage;
    private HouseholdController householdController;
    private ReceipeForTownT receipeForTownT;
    private House house;
    private int nextLevel;

    public ChangeHouseUseTypeForTownManagementEvent(final Player player, final int houseId, final int recipeKey) {
        this.player = player;
        this.houseId = houseId;
        this.recipeKey = recipeKey;
        this.houseStorage = player.getHouseStorage();
        this.householdController = player.getHouseholdController();
    }

    @Override
    public void onEvent() {
        this.house.setLevel(this.nextLevel);
        final int[] houses = new int[5];
        houses[0] = this.houseId;
        if (this.house.getReciepeKey() != this.recipeKey) {
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
            this.house.setReceipeForTownT(this.receipeForTownT);
            this.player.sendPacket(new SMSetMyHouseForTownManagement(houses, false, false));
            if (this.house.getHouseReceipeType().isEmpty()) {
                final HouseHold houseHold2 = new HouseHold(this.player, this.houseId, 0, new SpawnPlacementT(new Location()));
                this.householdController.addHouseHold(houseHold2);
                FixedHouseService.getInstance().updateToTop(houseHold2);
                final Collection<HouseHold> houseHolds = this.householdController.getHouseHolds(EFixedHouseType.House);
                this.player.sendPacket(new SMListFixedHouseInfoOwnerBeing(this.player, houseHolds, (byte) 2));
                this.player.sendPacket(new SMSetMyHouseForTownManagement(houses, true, false));
            }
        }
        this.house.setCraftDate(GameTimeService.getServerTimeInSecond() + this.house.getTransferT().getTime() + 3L);
        this.player.sendPacket(new SMChangeHouseUseTypeForTownManagement(this.house));
    }

    @Override
    public boolean canAct() {
        this.house = this.houseStorage.getHouse(this.houseId);
        if (this.house == null || !this.house.getHouseInfoT().containsReceipe(this.recipeKey)) {
            return false;
        }
        this.receipeForTownT = HouseData.getInstance().getRecipe(this.recipeKey);
        final HouseInfoT houseInfoT = this.house.getHouseInfoT();
        final Integer needHouseId = houseInfoT.getNeedHouseId();
        if (!this.receipeForTownT.getHouseReceipeType().isEmpty() && needHouseId != null && !this.houseStorage.contains(needHouseId)) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoCantUseHouseFailedCheckCondition, CMChangeHouseUseTypeForTownManagement.class));
            return false;
        }
        if (this.house.getReciepeKey() == this.recipeKey) {
            if (this.house.getLevel() >= this.house.getMaxLevel()) {
                return false;
            }
            this.nextLevel = this.house.getLevel() + 1;
        } else {
            if (this.house.getReceipeForTownT().getHouseReceipeType().isDepot()) {
                final Warehouse warehouse = this.player.getPlayerBag().getWarehouse(this.house.getHouseInfoT().getAffiliatedTown());
                if (warehouse.getItemSize() > warehouse.getExpandSize() + warehouse.getAffiliatedHouseSlots()) {
                    this.player.sendPacket(new SMNak(EStringTable.eErrNoWarehouseIsntFull, CMChangeHouseUseTypeForTownManagement.class));
                    return false;
                }
            }
            if (this.recipeKey == 0 && this.householdController.getHouseHolds(EFixedHouseType.House).size() >= 5) {
                this.player.sendPacket(new SMNak(EStringTable.eErrNoCanNotRentDwellingAnyMore, CMChangeHouseUseTypeForTownManagement.class));
                return false;
            }
            if (this.house.getHouseReceipeType().isLodging()) {
                final int regionId = this.house.getHouseInfoT().getAffiliatedTown();
                final int maxSlots = this.player.getNpcWorkerController().getWorkerSlotsByRegionId(this.house.getHouseInfoT().getAffiliatedTown()) - NpcWorkerController.getSlotsByLodgingLevel(this.house.getLevel());
                if (this.player.getNpcWorkerController().size(regionId) > maxSlots) {
                    return false;
                }
            }
            this.nextLevel = 1;
        }
        final HouseTransferT houseTransferT = HouseData.getInstance().getHouseTransfer(this.receipeForTownT.getTransferKey(), this.nextLevel);
        return this.player.getPlayerBag().onEvent(new BuyHouseItemEvent(this.player, 0, houseTransferT.getItemKey(), houseTransferT.getCount()));
    }
}
