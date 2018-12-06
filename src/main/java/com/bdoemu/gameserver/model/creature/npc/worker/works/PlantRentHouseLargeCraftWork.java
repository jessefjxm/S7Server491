// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.worker.works;

import com.bdoemu.commons.utils.BuffReader;
import com.bdoemu.gameserver.dataholders.ItemExchangeSourceData;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkingType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.DecreaseWarehouseItemsEvent;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.WarehouseDecreaseTask;
import com.bdoemu.gameserver.model.houses.House;
import com.bdoemu.gameserver.model.items.templates.ItemExchangeSourceT;
import com.bdoemu.gameserver.model.items.templates.ItemExchangeT;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

public class PlantRentHouseLargeCraftWork extends ANpcWork {
    private int houseId;
    private int craftId;
    private int itemId;
    private int enchantLevel;
    private int index;

    public PlantRentHouseLargeCraftWork(final ENpcWorkingType workType) {
        super(workType);
    }

    @Override
    public void load(final BasicDBObject dbObject) {
        this.houseId = dbObject.getInt("houseId");
        this.craftId = dbObject.getInt("craftId");
        this.itemId = dbObject.getInt("itemId");
        this.enchantLevel = dbObject.getInt("enchantLevel");
        this.index = dbObject.getInt("index");
    }

    @Override
    public void read(final BuffReader buffReader) {
        this.houseId = buffReader.readHD();
        this.craftId = buffReader.readHD();
        this.itemId = buffReader.readHD();
        this.enchantLevel = buffReader.readHD();
        buffReader.readC();
        buffReader.readD();
        buffReader.readD();
    }

    @Override
    public boolean canAct(final NpcWorker npcWorker, final Player player) {
        final House house = player.getHouseStorage().getHouse(this.houseId);
        if (house == null) {
            return false;
        }
        final List<Integer> exchangeKeys = house.getExchangeKeys(this.craftId);
        if (exchangeKeys == null) {
            return false;
        }
        final ItemExchangeSourceT itemExchangeSourceT = ItemExchangeSourceData.getInstance().getTemplate(this.craftId);
        final ItemExchangeT exchangeT = itemExchangeSourceT.getExchangeItem(this.itemId, this.enchantLevel);
        if (exchangeT == null) {
            return false;
        }
        this.index = exchangeT.getIndex();
        final List<WarehouseDecreaseTask> decreaseTasks = new ArrayList<WarehouseDecreaseTask>();
        decreaseTasks.add(new WarehouseDecreaseTask(exchangeT.getItemId(), exchangeT.getEnchantLevel(), 1L));
        return player.getPlayerBag().onEvent(new DecreaseWarehouseItemsEvent(player, decreaseTasks, npcWorker.getRegionId()));
    }

    public int getHouseId() {
        return this.houseId;
    }

    public int getIndex() {
        return this.index;
    }

    public int getCraftId() {
        return this.craftId;
    }

    @Override
    public boolean canStopWork() {
        return true;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("workType", (Object) this.workType.name());
        builder.append("houseId", (Object) this.houseId);
        builder.append("craftId", (Object) this.craftId);
        builder.append("itemId", (Object) this.itemId);
        builder.append("enchantLevel", (Object) this.enchantLevel);
        builder.append("index", (Object) this.index);
        return builder.get();
    }
}
