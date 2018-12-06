package com.bdoemu.gameserver.model.houses.events;

import com.bdoemu.core.network.sendable.SMListHouseLargeCraft;
import com.bdoemu.gameserver.dataholders.ItemExchangeSourceData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ItemNpcWorkToWarehouseEvent;
import com.bdoemu.gameserver.model.houses.HouseLargeCraft;
import com.bdoemu.gameserver.model.houses.HouseStorage;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemExchangeSourceT;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;

import java.util.Collections;
import java.util.List;

public class UpdateHouseLargeCraftEvent implements IHouseEvent {
    private Player player;
    private int houseId;
    private int itemIndex;
    private int regionId;
    private long workerObjId;
    private HouseLargeCraft largeCraft;
    private HouseStorage houseStorage;

    public UpdateHouseLargeCraftEvent(final Player player, final int houseId, final int itemIndex, final int regionId, final long workerObjId) {
        this.player = player;
        this.houseId = houseId;
        this.itemIndex = itemIndex;
        this.regionId = regionId;
        this.workerObjId = workerObjId;
        this.houseStorage = player.getHouseStorage();
    }

    @Override
    public void onEvent() {
        if (this.largeCraft.isFinish() && this.houseStorage.removeHouseLargeCraft(this.houseId) != null) {
        	//System.out.println("===========UpdateHouseLargeCraftEvent IF===========");
            final ItemExchangeSourceT itemExchangeSourceT = ItemExchangeSourceData.getInstance().getTemplate(this.largeCraft.getCraftId());
            final List<ItemSubGroupT> itemsSubGroup = ItemMainGroupService.getItems(this.player, itemExchangeSourceT.getItemDropId());
            this.player.getPlayerBag().onEvent(new ItemNpcWorkToWarehouseEvent(this.player, this.regionId, itemsSubGroup, this.workerObjId));
            this.player.sendPacket(new SMListHouseLargeCraft(Collections.singletonList(this.largeCraft)));
        }
        else
        {
        	//System.out.println("===========UpdateHouseLargeCraftEvent ELSE===========");
        	this.player.sendPacket(new SMListHouseLargeCraft(Collections.singletonList(this.largeCraft)));
        }
    }

    @Override
    public boolean canAct() {
        this.largeCraft = this.houseStorage.getHouseLargerCraft(this.houseId);
        return this.largeCraft != null && this.largeCraft.updateItem(this.itemIndex);
    }
}
