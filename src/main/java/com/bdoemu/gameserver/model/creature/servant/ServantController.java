package com.bdoemu.gameserver.model.creature.servant;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.core.network.sendable.SMActivePetPrivateInfo;
import com.bdoemu.core.network.sendable.SMDeactivePetInfo;
import com.bdoemu.core.network.sendable.SMListServantInfo;
import com.bdoemu.core.network.sendable.SMUnregisterPet;
import com.bdoemu.gameserver.databaseCollections.ServantsDBCollection;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.enums.ERidingSlotType;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;
import com.bdoemu.gameserver.model.houses.House;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ServantController {
    private final ConcurrentHashMap<Long, Servant> servants;
    private Servant currentVehicle;
    private Player owner;
    private Servant tameServant;
    private ServantEquipOnOff servantEquipOnOff;
    private ERidingSlotType ridingSlotType;

    public ServantController(final Player owner) {
        this.servantEquipOnOff = new ServantEquipOnOff();
        this.ridingSlotType = ERidingSlotType.None;
        this.owner = owner;
        this.servants = ServantsDBCollection.getInstance().load(owner);
        for (final ServantItemMarket servantItemMarket : AuctionGoodService.getInstance().getMyRegisteredServants(owner.getAccountId())) {
            this.servants.put(servantItemMarket.getServant().getObjectId(), servantItemMarket.getServant());
        }
        this.servants.values().forEach(servant -> servant.setOwner(owner));
    }

    public void add(final Servant servant) {
        this.servants.put(servant.getObjectId(), servant);
        servant.setOwner(this.owner);
        ServantsDBCollection.getInstance().save(servant);
    }

    public boolean delete(final Servant servant) {
        final boolean result = this.servants.values().remove(servant);
        if (result) {
            if (servant.getServantType() == EServantType.Pet) {
                this.owner.sendPacket(new SMUnregisterPet(servant.getObjectId()));
            }
            ServantsDBCollection.getInstance().delete(servant.getObjectId());
        }
        return result;
    }

    public ERidingSlotType getRidingSlotType() {
        return this.ridingSlotType;
    }

    public void setCurrentVehicle(final Servant currentVehicle, final ERidingSlotType ridingSlotType) {
        this.currentVehicle = currentVehicle;
        this.ridingSlotType = ridingSlotType;
    }

    public Servant getCurrentVehicle() {
        return this.currentVehicle;
    }

    public Servant getServant(final EServantType servantType, final long servantObjectId, final int townId) {
        if (this.servants.containsKey(servantObjectId)) {
            final Servant servant = this.servants.get(servantObjectId);
            if (servant.getServantType() == servantType && servant.getRegionId() == townId) {
                return servant;
            }
        }
        return null;
    }

    public Servant getServant(final long servantObjectId) {
        return this.servants.get(servantObjectId);
    }

    public Servant getServant(final int servantSessionId) {
        return this.servants.values().stream().filter(servant -> servant.getGameObjectId() == servantSessionId).findFirst().orElse(null);
    }

    public List<Servant> getServants(final EServantType servantType) {
        return this.servants.values().stream().filter(servant -> servant.getServantType() == servantType).collect(Collectors.toList());
    }

    public List<Servant> getServants(final EServantState servantState) {
        return this.servants.values().stream().filter(servant -> servant.getServantState() == servantState).collect(Collectors.toList());
    }

    public List<Servant> getServants(final EServantState servantState, final EServantType... servantType) {
        return this.servants.values().stream().filter(servant -> Arrays.asList(servantType).contains(servant.getServantType()) && servant.getServantState() == servantState).collect(Collectors.toList());
    }

    public boolean isStableFull(final int townId, final EServantType servantType) {
        return this.getStable(townId, servantType).size() >= this.getExpandSlots(townId);
    }

    private List<Servant> getStable(final int townId, final EServantType servantType) {
        return this.servants.values().stream().filter(servant -> servant.getRegionId() == townId && servant.getServantType() == servantType && servant.getServantState().isStable()).collect(Collectors.toList());
    }

    private int getExpandSlots(final int townId) {
        int slots = 0;
        for (final House house : this.owner.getHouseStorage().getHouseList()) {
            if (house.getHouseInfoT().getAffiliatedTown() == townId && house.getHouseReceipeType().isRanch()) {
                slots += house.getLevel();
            }
        }
        return slots + ServantConfig.STABLE_SLOT_LIMIT;
    }

    public void save() {
        ServantsDBCollection.getInstance().update(this.servants.values().stream().filter(servant -> servant.getAuctionRegisterType().isNone()).collect(Collectors.toList()));
    }

    public void onLogin() {
        final List<Servant> pets = this.servants.values().stream().filter(servant -> servant.getServantType().isPet()).collect(Collectors.toList());
        if (pets.size() > 0) {
            List<Servant> activePets = pets.stream().filter(servant -> servant.getServantState().isField()).collect(Collectors.toList());
            if (!activePets.isEmpty()) {
                int totalPetsOutside = 0;
                for (Iterator<Servant> iterator = activePets.iterator(); iterator.hasNext(); ) {
                    ++totalPetsOutside;
                    Servant srv = iterator.next();

                    if (totalPetsOutside > 4) {
                        srv.setServantState(EServantState.Stable);
                        iterator.remove();
                    } else
                        srv.unSeal(null, false, this.owner);
                }

                this.owner.sendPacket(new SMActivePetPrivateInfo(activePets));
            }
            final List<Servant> deactivatedPets = pets.stream().filter(servant -> servant.getServantState().isStable()).collect(Collectors.toList());
            if (!deactivatedPets.isEmpty()) {
                this.owner.sendPacket(new SMDeactivePetInfo(deactivatedPets, EPacketTaskType.Update));
            }
        }
        final List<Servant> vehicles = this.servants.values().stream().filter(servant -> !servant.getServantType().isPet()).collect(Collectors.toList());
        vehicles.stream().filter(servant -> servant.getCarriageObjectId() > 0L && this.getServant(servant.getCarriageObjectId()) == null).forEach(servant -> servant.setCarriageObjectId(0L));
        final ListSplitter<Servant> splitter = new ListSplitter<>(vehicles, 4);
        while (!splitter.isLast()) {
            this.owner.sendPacket(new SMListServantInfo(splitter.getNext(), EPacketTaskType.Update, splitter.isFirst()));
        }
        vehicles.stream().filter(servant -> servant.getServantState().isField()).forEach(servant -> servant.unSeal(servant.getLocation(), false, this.owner));
    }

    public ServantEquipOnOff getServantEquipOnOff() {
        return this.servantEquipOnOff;
    }

    public Servant getTameServant() {
        return this.tameServant;
    }

    public void setTameServant(final Servant tameServant) {
        this.tameServant = tameServant;
    }
}
