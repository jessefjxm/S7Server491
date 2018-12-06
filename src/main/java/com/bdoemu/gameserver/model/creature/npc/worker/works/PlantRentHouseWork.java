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

public class PlantRentHouseWork extends ANpcWork {
    private int craftId;
    private int houseId;

    public PlantRentHouseWork(final ENpcWorkingType workType) {
        super(workType);
    }

    @Override
    public void load(final BasicDBObject dbObject) {
        this.craftId = dbObject.getInt("craftId");
        this.houseId = dbObject.getInt("houseId");
    }

    @Override
    public void read(final BuffReader buffReader) {
        this.craftId = buffReader.readHD();
        this.houseId = buffReader.readHD();
        buffReader.readH();
        buffReader.readH();
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
        final List<WarehouseDecreaseTask> decreaseTasks = new ArrayList<WarehouseDecreaseTask>();
        for (final ItemExchangeT exchangeT : itemExchangeSourceT.getExchangeItems()) {
            decreaseTasks.add(new WarehouseDecreaseTask(exchangeT.getItemId(), exchangeT.getEnchantLevel(), exchangeT.getCount()));
        }
        return player.getPlayerBag().onEvent(new DecreaseWarehouseItemsEvent(player, decreaseTasks, npcWorker.getRegionId()));
    }

    public int getCraftId() {
        return this.craftId;
    }

    public int getHouseId() {
        return this.houseId;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("workType", (Object) this.workType.name());
        builder.append("craftId", (Object) this.craftId);
        builder.append("houseId", (Object) this.houseId);
        return builder.get();
    }
}
