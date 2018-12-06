package com.bdoemu.gameserver.model.creature.player.itemPack;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.HashMap;

public class AccountBag extends JSONable {
    private final CashInventory cashInventory;
    private final HashMap<Integer, Warehouse> warehouses;

    public AccountBag(final Player player) {
        this.warehouses = new HashMap<>();
        this.cashInventory = new CashInventory(player);
        this.warehouses.put(5, new Warehouse(5, player));
        this.warehouses.put(32, new Warehouse(32, player));
        this.warehouses.put(52, new Warehouse(52, player));
        this.warehouses.put(77, new Warehouse(77, player));
        this.warehouses.put(88, new Warehouse(88, player));
        this.warehouses.put(107, new Warehouse(107, player));
        this.warehouses.put(120, new Warehouse(120, player));
        this.warehouses.put(126, new Warehouse(126, player));
        this.warehouses.put(182, new Warehouse(182, player));
        this.warehouses.put(202, new Warehouse(202, player));
        this.warehouses.put(221, new Warehouse(221, player));
        this.warehouses.put(229, new Warehouse(229, player));
        this.warehouses.put(601, new Warehouse(601, player));
        this.warehouses.put(605, new Warehouse(605, player));
        this.warehouses.put(619, new Warehouse(619, player));
        this.warehouses.put(693, new Warehouse(693, player));
        this.warehouses.put(694, new Warehouse(694, player));
        this.warehouses.put(821, new Warehouse(821, player));
    }

    public AccountBag(final Player player, final BasicDBObject dbObject) {
        this.warehouses = new HashMap<>();
        this.cashInventory = new CashInventory((BasicDBObject) dbObject.get(EItemStorageLocation.CashInventory.toString()), player);
        final BasicDBObject warehousesDB = (BasicDBObject) dbObject.get(EItemStorageLocation.Warehouse.toString());
        for (final String key : warehousesDB.keySet()) {
            final int townId = Integer.parseInt(key);
            final BasicDBObject warehouseDb = (BasicDBObject) warehousesDB.get(key);
            this.warehouses.put(townId, new Warehouse(warehouseDb, townId, player));
        }

        this.warehouses.computeIfAbsent(821, k -> new Warehouse(821, player));
    }

    public CashInventory getCashInventory() {
        return this.cashInventory;
    }

    public HashMap<Integer, Warehouse> getWarehouses() {
        return this.warehouses;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append(this.cashInventory.getLocationType().toString(), this.cashInventory.toDBObject());
        final BasicDBObject warehousesDB = new BasicDBObject();
        for (final Warehouse warehouse : this.warehouses.values()) {
            warehousesDB.append(Integer.toString(warehouse.getTownId()), warehouse.toDBObject());
        }
        builder.append(EItemStorageLocation.Warehouse.toString(), warehousesDB);
        return builder.get();
    }
}
