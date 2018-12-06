package com.bdoemu.gameserver.model.creature.player.itemPack;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.PlayerBagConfig;
import com.bdoemu.core.network.receivable.CMListWarehouseItem;
import com.bdoemu.core.network.sendable.SMAddItemToWarehouse;
import com.bdoemu.core.network.sendable.SMEraseWarehouseItem;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.DecreaseItemTask;
import com.bdoemu.gameserver.model.houses.House;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.stats.Stat;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.stats.elements.ExpandElement;
import com.mongodb.BasicDBObject;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Warehouse extends ADBItemPack {
    protected int townId;

    public Warehouse(final int townId, final Player player) {
        super(EItemStorageLocation.Warehouse, new Stat(player, new BaseElement(PlayerBagConfig.WAREHOUSE_BASE_SIZE)), player);
        this.townId = townId;
    }

    public Warehouse(final BasicDBObject dbObject, final int townId, final Player player) {
        super(EItemStorageLocation.Warehouse, dbObject, player);
        this.townId = townId;
    }

    public static int getSlotsByDepotLevel(final int level) {
        switch (level) {
            case 5:
                return 16;
            case 4:
                return 12;
            case 3:
                return 8;
            case 2:
                return 5;
            default:
                return 3;
        }
    }

    public void addItems(final ConcurrentLinkedQueue<Item> addTasks, final long objectId, final int type) {
        this.addItems(addTasks, objectId, type, true);
    }

    public void addItems(final ConcurrentLinkedQueue<Item> addTasks, final long objectId, final int type, final boolean sendPacket) {
        for (final Item task : addTasks) {
            final int itemId = task.getItemId();
            final long count = task.getCount();
            final ItemTemplate template = task.getTemplate();
            if (template != null) {
                if (Warehouse.staticItems.containsKey(itemId)) {
                    final int index = Warehouse.staticItems.get(itemId);
                    final Item item = this.getItem(index);
                    if (item == null) {
                        this.addItem(task, index);
                    } else {
                        item.addCount(count);
                        task.setObjectId(item.getObjectId());
                    }
                    task.setSlotIndex(index);
                    task.setStorageLocation(this.getLocationType());
                } else {
                    if (task.getTemplate().isStack()) {
                        for (final int slotIndex : this.getItemMap().keySet()) {
                            final Item stackItem = this.getItem(slotIndex);
                            if (stackItem != null && stackItem.getItemId() == itemId) {
                                stackItem.addCount(count);
                                task.setObjectId(stackItem.getObjectId());
                                task.setSlotIndex(slotIndex);
                                task.setStorageLocation(this.getLocationType());
                                break;
                            }
                        }
                        if (task.getObjectId() > 0L) {
                            continue;
                        }
                    }
                    int currentIndex = this.getItemSize() + 1;
                    if (this.containsItem(0)) {
                        --currentIndex;
                    }
                    while (this.getItem(++currentIndex) != null) {
                    }
                    task.setSlotIndex(currentIndex);
                    task.setStorageLocation(this.getLocationType());
                    if (task.getTemplate().isForTrade() && task.getRegionId() == 1) {
                        task.setRegionId(this.getOwner().getRegionId());
                    }
                    this.addItem(task, currentIndex);
                }
            }
        }
        if (sendPacket) {
            this.getOwner().sendPacket(new SMAddItemToWarehouse(addTasks, getLocationType(), this.townId, EPacketTaskType.Update, type, objectId));
        }
    }

    public void decreaseItem(int index, final long count) {
        final Item item = this.getItem(index);
        if (item != null) {
            item.addCount(-count);
            if (item.getCount() <= 0L) {
                this.removeItem(index);
                if (index >= 1) {
                    Item orderItem;
                    do {
                        orderItem = this.removeItem(index + 1);
                        if (orderItem != null) {
                            this.addItem(orderItem, index);
                        }
                        ++index;
                    } while (orderItem != null);
                }
            }
            if (getOwner() != null)
                getOwner().sendPacket(new SMEraseWarehouseItem(getLocationType(), this.townId, item.getObjectId(), count));
        } else {
            if (getOwner() != null)
                getOwner().sendPacket(new SMNak(EStringTable.eErrNoLoadFailOperaionManager, CMListWarehouseItem.class));
        }
    }

    public boolean canDecreaseItem(final int index, final long count) {
        final Item item = this.getItem(index);
        return item != null && item.getCount() >= count;
    }

    public boolean canDecreaseItems(final List<DecreaseItemTask> decreaseTasks) {
        for (final DecreaseItemTask itemTask : decreaseTasks) {
            if (itemTask.getCount() <= 0L) {
                return false;
            }
            if (!this.canDecreaseItem(itemTask.getSlotIndex(), itemTask.getCount())) {
                return false;
            }
        }
        return true;
    }

    public boolean canAddItems(final ConcurrentLinkedQueue<Item> addTasks) {
        int slots = 0;
        int taskSize = addTasks.size();
        for (final Item task : addTasks) {
            if (task.getCount() <= 0L) {
                return false;
            }
            if (!task.getTemplate().isStack()) {
                continue;
            }
            if (Warehouse.staticItems.containsKey(task.getItemId())) {
                final int slotIndex = Warehouse.staticItems.get(task.getItemId());
                final Item item = this.getItem(slotIndex);
                if (item != null && item.getCount() + task.getCount() < 0L) {
                    return false;
                }
                --taskSize;
            } else {
                for (final Item item : this.getItemList()) {
                    if (item.getItemId() == task.getItemId()) {
                        ++slots;
                        break;
                    }
                }
            }
        }
        final boolean result = this.freeSlotCount() >= taskSize - slots;
        if (!result) {
            this.getOwner().sendPacket(new SMNak(EStringTable.eErrNoInventoryNotExistFreeSlot, CMListWarehouseItem.class));
        }
        return result;
    }

    @Override
    public int freeSlotCount() {
        final int maxSize = this.getExpandSize() + this.getAffiliatedHouseSlots() - 1;
        int mapSize = this.itemMap.size();
        if (this.itemMap.containsKey(0)) {
            --mapSize;
        }
        if (this.itemMap.containsKey(1)) {
            --mapSize;
        }
        return maxSize - mapSize;
    }

    public int getAffiliatedHouseSlots() {
        int slots = 0;
        for (final House house : this.getOwner().getHouseStorage().getHouseList()) {
            if (house.getHouseInfoT().getAffiliatedTown() == this.townId && house.getHouseReceipeType().isDepot()) {
                slots += getSlotsByDepotLevel(house.getLevel());
            }
        }
        return slots;
    }

    @Override
    public Player getOwner() {
        return (Player) super.getOwner();
    }

    @Override
    public int getDefaultSlotIndex() {
        return 1;
    }

    public int getTownId() {
        return this.townId;
    }

    @Override
    public int getMaxExpandSize() {
        return PlayerBagConfig.WAREHOUSE_MAX_SIZE;
    }

    public void expandBase(final int expandCount) {
        this.expandStat.increaseElement(this.expandStat.getBase(), (this.getBaseExpandSize() + expandCount > this.getMaxExpandSize()) ? ((float) (this.getMaxExpandSize() - this.getBaseExpandSize())) : ((float) expandCount));
    }

    public void addExpandElement(final ExpandElement element) {
        this.expandStat.addElement(element);
    }
}
