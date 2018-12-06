package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.LocalizingOptionConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMRemoveCharacterFromField;
import com.bdoemu.core.network.sendable.SMRemoveCharacterFromFieldNak;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.databaseCollections.ServantsDBCollection;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class CMRemoveCharacterFromField extends ReceivablePacket<GameClient> {
    private long objectId;

    public CMRemoveCharacterFromField(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.objectId = this.readQ();
        readC();
    }

    public void runImpl() {
        final BasicDBObject removed = ((GameClient) this.getClient()).getLoginAccountInfo().getPlayer(this.objectId);
        if (removed != null) {
            long deletionDate = removed.getLong("deletionDate");
            if (deletionDate > 0L) {
                return;
            }
            final BasicDBObject playerBag = (BasicDBObject) removed.get("playerBag");
            final BasicDBObject equipment = (BasicDBObject) playerBag.get("Equipments");
            final BasicDBObject inventory = (BasicDBObject) playerBag.get("Inventory");
            final BasicDBList equipmentDB = (BasicDBList) equipment.get("items");
            for (final Object anEquipmentDB : equipmentDB) {
                final BasicDBObject itemDB = (BasicDBObject) anEquipmentDB;
                final int itemId = itemDB.getInt("itemId");
                final ItemTemplate template = ItemData.getInstance().getItemTemplate(itemId);
                if (template.getNeedContribute() != null) {
                    this.sendPacket((SendablePacket) new SMRemoveCharacterFromFieldNak(this.objectId, EStringTable.eErrNoRemoveCharacterByExporePointItem));
                    return;
                }
            }
            final BasicDBList inventoryDB = (BasicDBList) inventory.get("items");
            for (final Object anInventoryDB : inventoryDB) {
                final BasicDBObject itemDB2 = (BasicDBObject) anInventoryDB;
                final int itemId2 = itemDB2.getInt("itemId");
                final ItemTemplate template2 = ItemData.getInstance().getItemTemplate(itemId2);
                if (template2.getNeedContribute() != null) {
                    this.sendPacket((SendablePacket) new SMRemoveCharacterFromFieldNak(this.objectId, EStringTable.eErrNoRemoveCharacterByExporePointItem));
                    return;
                }
            }
            final long playerObjectId = removed.getLong("_id");
            if (ServantsDBCollection.getInstance().isSummonedByPlayer(playerObjectId)) {
                this.sendPacket((SendablePacket) new SMRemoveCharacterFromFieldNak(this.objectId, EStringTable.eErrNoRemoveCharacterByServant));
                return;
            }
            final int level = removed.getInt("level");
            deletionDate = GameTimeService.getServerTimeInSecond() + ((level < LocalizingOptionConfig.CHARACTER_REMOVE_TIME_CHECK_LEVEL) ? LocalizingOptionConfig.LOW_LEVEL_CHARACTER_REMOVE_TIME : LocalizingOptionConfig.CHARACTER_REMOVE_TIME);
            if (GameTimeService.getServerTimeInSecond() >= deletionDate) {
                ((GameClient) this.getClient()).getLoginAccountInfo().getPlayers().remove(removed);
                PlayersDBCollection.getInstance().delete(this.objectId);
                ((GameClient) this.getClient()).sendPacket((SendablePacket) new SMRemoveCharacterFromField(this.objectId, deletionDate, 0));
            } else {
                removed.append("deletionDate", (Object) deletionDate);
                PlayersDBCollection.getInstance().updateDeletionDate(this.objectId, deletionDate);
                ((GameClient) this.getClient()).sendPacket((SendablePacket) new SMRemoveCharacterFromField(this.objectId, deletionDate, 1));
            }
        }
    }
}
