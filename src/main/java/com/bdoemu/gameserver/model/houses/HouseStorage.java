package com.bdoemu.gameserver.model.houses;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.concurrent.CloseableReentrantLock;
import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMListHouseForTownManagement;
import com.bdoemu.core.network.sendable.SMListHouseLargeCraft;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.events.IHouseEvent;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class HouseStorage extends JSONable {
    private final CloseableReentrantLock lock;
    private final ConcurrentHashMap<Integer, HouseLargeCraft> houseLargeCrafts;
    private final ConcurrentHashMap<Integer, House> houses;
    private final Player player;

    public HouseStorage(final Player player, final BasicDBObject dbObject) {
        this.lock = new CloseableReentrantLock();
        this.houseLargeCrafts = new ConcurrentHashMap<>();
        this.houses = new ConcurrentHashMap<>();
        this.player = player;
        final BasicDBList houseLargeCraftsDB = (BasicDBList) dbObject.get("houseLargeCrafts");
        for (final Object aHouseLargeCraftsDB : houseLargeCraftsDB) {
            final BasicDBObject houseLargeCraftDB = (BasicDBObject) aHouseLargeCraftsDB;
            final HouseLargeCraft houseLargeCraft = new HouseLargeCraft(houseLargeCraftDB);
            this.houseLargeCrafts.put(houseLargeCraft.getHouseId(), houseLargeCraft);
        }
        final BasicDBList housesDB = (BasicDBList) dbObject.get("houses");
        for (final Object aHousesDB : housesDB) {
            this.putHouse(new House((BasicDBObject) aHousesDB));
        }
    }

    public HouseStorage(final Player player) {
        this.lock = new CloseableReentrantLock();
        this.houseLargeCrafts = new ConcurrentHashMap<>();
        this.houses = new ConcurrentHashMap<>();
        this.player = player;
    }

    public ConcurrentHashMap<Integer, HouseLargeCraft> getHouseLargeCrafts() {
        return this.houseLargeCrafts;
    }

    public final House getHouse(final int houseId) {
        return this.houses.get(houseId);
    }

    public final HouseLargeCraft getHouseLargerCraft(final int houseId) {
        return this.houseLargeCrafts.get(houseId);
    }

    public final void putHouse(final House house) {
        this.houses.put(house.getHouseId(), house);
    }

    public final HouseLargeCraft removeHouseLargeCraft(final int houseId) {
        return this.houseLargeCrafts.remove(houseId);
    }

    public final House removeHouse(final int houseId) {
        return this.houses.remove(houseId);
    }

    public boolean contains(final int houseId) {
        return this.houses.containsKey(houseId);
    }

    public Collection<House> getHouseList() {
        return this.houses.values();
    }

    public void onLogin() {
        final Collection<House> houseList = this.getHouseList();
        if (!houseList.isEmpty()) {
            this.player.sendPacket(new SMListHouseForTownManagement(houseList));
        }

        if (!this.houseLargeCrafts.isEmpty()) {
            final ListSplitter<HouseLargeCraft> largeCraftItems = new ListSplitter<>(houseLargeCrafts.values(), 32);
            while (!largeCraftItems.isLast())
                player.sendPacketNoFlush(new SMListHouseLargeCraft(largeCraftItems.getNext()));
        }
    }

    public boolean onEvent(final IHouseEvent event) {
        boolean result = false;
        try (final CloseableReentrantLock tempLock = this.lock.open()) {
            if (event.canAct()) {
                result = true;
                event.onEvent();
            }
        }
        return result;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList housesDBList = new BasicDBList();
        for (final House house : this.houses.values())
            housesDBList.add(house.toDBObject());
        builder.append("houses", housesDBList);
        final BasicDBList houseLargeCraftDBList = new BasicDBList();
        for (final HouseLargeCraft largeCraft : this.houseLargeCrafts.values())
            houseLargeCraftDBList.add(largeCraft.toDBObject());
        builder.append("houseLargeCrafts", houseLargeCraftDBList);
        return builder.get();
    }
}
