package com.bdoemu.gameserver.model.creature.servant.itemPack;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.creature.player.itemPack.ServantEquipments;
import com.bdoemu.gameserver.model.creature.player.itemPack.ServantInventory;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class ServantBag extends JSONable {
    private Servant owner;
    private ServantInventory inventory;
    private ServantEquipments equipments;

    public ServantBag(final Servant servant) {
        this.owner = servant;
        this.inventory = new ServantInventory(servant);
        this.equipments = new ServantEquipments(servant);
    }

    public ServantBag(final BasicDBObject dbObject, final Servant servant) {
        this.owner = servant;
        if (dbObject != null) {
            this.inventory = new ServantInventory((BasicDBObject) dbObject.get(EItemStorageLocation.ServantInventory.toString()), servant);
            this.equipments = new ServantEquipments((BasicDBObject) dbObject.get(EItemStorageLocation.ServantEquip.toString()), servant);
        }
    }

    public ServantEquipments getEquipments() {
        return this.equipments;
    }

    public ServantInventory getInventory() {
        return this.inventory;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append(EItemStorageLocation.ServantInventory.toString(), this.inventory.toDBObject());
        builder.append(EItemStorageLocation.ServantEquip.toString(), this.equipments.toDBObject());
        return builder.get();
    }
}
