// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMBuyHouseForTownManagement;
import com.bdoemu.core.network.sendable.SMBuyHouseForTownManagement;
import com.bdoemu.core.network.sendable.SMListFixedHouseInfoOwnerBeing;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMSetMyHouseForTownManagement;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.BuyHouseItemEvent;
import com.bdoemu.gameserver.model.houses.House;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseStorage;
import com.bdoemu.gameserver.model.houses.HouseholdController;
import com.bdoemu.gameserver.model.houses.enums.EFixedHouseType;
import com.bdoemu.gameserver.model.houses.services.FixedHouseService;
import com.bdoemu.gameserver.model.houses.templates.HouseInfoT;
import com.bdoemu.gameserver.model.houses.templates.HouseTransferT;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;

public class BuyHouseForTownManagementEvent implements IHouseEvent {
    private Player player;
    private int houseId;
    private int receipeKey;
    private HouseStorage houseStorage;
    private HouseholdController householdController;
    private House house;

    public BuyHouseForTownManagementEvent(final Player player, final int houseId, final int receipeKey) {
        this.player = player;
        this.houseId = houseId;
        this.receipeKey = receipeKey;
        this.houseStorage = player.getHouseStorage();
        this.householdController = player.getHouseholdController();
    }

    @Override
    public void onEvent() {
        this.house.setCraftDate(GameTimeService.getServerTimeInSecond() + this.house.getTransferT().getTime() + 3L);
        this.houseStorage.putHouse(this.house);
        final int[] houses = new int[5];
        houses[0] = this.houseId;
        if (this.house.getHouseReceipeType().isEmpty()) {
            final HouseHold houseHold = new HouseHold(this.player, this.houseId, 0, new SpawnPlacementT(new Location()));
            this.householdController.addHouseHold(houseHold);
            FixedHouseService.getInstance().updateToTop(houseHold);
            this.player.sendPacket(new SMListFixedHouseInfoOwnerBeing(this.player, this.householdController.getHouseHolds(EFixedHouseType.House), (byte) 2));
            this.player.sendPacket(new SMSetMyHouseForTownManagement(houses, true, false));
        }
        this.player.sendPacket(new SMBuyHouseForTownManagement(this.house));
    }

    @Override
    public boolean canAct() {
        if (this.receipeKey == 0 && this.householdController.getHouseHolds(EFixedHouseType.House).size() >= 5) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoCanNotRentDwellingAnyMore, CMBuyHouseForTownManagement.class));
            return false;
        }
        this.house = House.newHouse(this.houseId, this.receipeKey);
        if (this.house == null || this.houseStorage.contains(this.houseId)) {
            return false;
        }
        final HouseInfoT houseInfoT = this.house.getHouseInfoT();
        final Integer needHouseId = houseInfoT.getNeedHouseId();
        if (!this.house.getHouseReceipeType().isEmpty() && needHouseId != null && !this.houseStorage.contains(needHouseId)) {
            return false;
        }
        final HouseTransferT houseTransferT = this.house.getTransferT();
        return this.player.getPlayerBag().onEvent(new BuyHouseItemEvent(this.player, houseInfoT.getNeedExplorePoint(), houseTransferT.getItemKey(), houseTransferT.getCount()));
    }
}
