// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.services;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.databaseCollections.HouseHoldDBCollection;
import com.bdoemu.gameserver.dataholders.HouseData;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseTopRank;
import com.bdoemu.gameserver.model.houses.enums.EFixedHouseType;
import com.bdoemu.gameserver.model.houses.templates.HouseInfoT;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Service")
public class FixedHouseService extends APeriodicTaskService {
    private static final AtomicReference<Object> instance;

    static {
        instance = new AtomicReference<Object>();
    }

    private final ConcurrentHashMap<Integer, HouseTopRank> fixedhouses;

    private FixedHouseService() {
        super(5L, TimeUnit.MINUTES);
        this.fixedhouses = new ConcurrentHashMap<Integer, HouseTopRank>();
        for (final HouseInfoT houseInfoT : HouseData.getInstance().getHouses()) {
            final int houseId = houseInfoT.getHouseId();
            this.fixedhouses.put(houseId, new HouseTopRank(houseId, HouseHoldDBCollection.getInstance().loadTopFixedHouses(houseId)));
        }
    }

    public static FixedHouseService getInstance() {
        Object value = FixedHouseService.instance.get();
        if (value == null) {
            synchronized (FixedHouseService.instance) {
                value = FixedHouseService.instance.get();
                if (value == null) {
                    final FixedHouseService actualValue = new FixedHouseService();
                    value = ((actualValue == null) ? FixedHouseService.instance : actualValue);
                    FixedHouseService.instance.set(value);
                }
            }
        }
        return (FixedHouseService) ((value == FixedHouseService.instance) ? null : value);
    }

    public void run() {
        World.getInstance().getTents().forEach(HouseHold::onUpdate);
    }

    public void updateToTop(final HouseHold houseHold) {
        final HouseTopRank houseTopRank = this.fixedhouses.get(houseHold.getCreatureId());
        houseTopRank.updateToTop(houseHold);
    }

    public void removeFromTop(final HouseHold houseHold) {
        final HouseTopRank houseTopRank = this.fixedhouses.get(houseHold.getCreatureId());
        houseTopRank.removeFixedHouse(houseHold.getObjectId());
    }

    public void onLogout(final Player player) {
        final Collection<HouseHold> tents = player.getHouseholdController().getHouseHolds(EFixedHouseType.Tent);
        if (!tents.isEmpty()) {
            for (final HouseHold tent : tents) {
                World.getInstance().deSpawn(tent, ERemoveActorType.DespawnTent);
            }
        }
    }

    public void onLogin(final Player player) {
        final Collection<HouseHold> tents = player.getHouseholdController().getHouseHolds(EFixedHouseType.Tent);
        if (!tents.isEmpty()) {
            for (final HouseHold tent : tents) {
                World.getInstance().spawn(tent, true, false);
            }
            player.sendPacketNoFlush(new SMTentInformation(tents));
        }
        final Collection<HouseHold> houseHolds = player.getHouseholdController().getHouseHolds(EFixedHouseType.House);
        if (!houseHolds.isEmpty()) {
            player.sendPacketNoFlush(new SMListFixedHouseInfoOwnerBeing(player, houseHolds, (byte) 2));
        }
        final ListSplitter<HouseTopRank> splitter = new ListSplitter<>(this.fixedhouses.values(), 193);
        while (!splitter.isLast()) {
            player.sendPacketNoFlush(new SMTopRankerHouseList(splitter.getNext(), splitter.isFirst() ? EPacketTaskType.Update : EPacketTaskType.Add));
        }
        if (!houseHolds.isEmpty()) {
            player.sendPacketNoFlush(new SMSetMyHouseForTownManagement(player.getHouseholdController().getFixedHouseIds(), false, true));
        }

        // We don't have maid support, but send anyway.
        player.sendPacketNoFlush(new SMMaidInfoTotal());
    }

    public boolean hasFixedHouse(final int houseId, final long houseObjectId) {
        return this.getTopRank(houseId).getTopHouse(houseObjectId) != null;
    }

    public HouseTopRank getTopRank(final int houseId) {
        return this.fixedhouses.get(houseId);
    }
}
