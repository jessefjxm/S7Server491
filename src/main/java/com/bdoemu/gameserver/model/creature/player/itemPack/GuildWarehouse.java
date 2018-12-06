package com.bdoemu.gameserver.model.creature.player.itemPack;

import com.bdoemu.core.network.sendable.SMAddItemToWarehouse;
import com.bdoemu.core.network.sendable.SMEraseWarehouseItem;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.DecreaseItemTask;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.stats.Stat;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.mongodb.BasicDBObject;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GuildWarehouse extends ADBItemPack {
    private final Guild guild;

    public GuildWarehouse(final BasicDBObject dbObject, final Guild guild) {
        super(EItemStorageLocation.GuildWarehouse, dbObject, null);
        this.guild = guild;
    }

    public GuildWarehouse(final Guild guild) {
        super(EItemStorageLocation.GuildWarehouse, new Stat(null, new BaseElement(50)), null);
        this.guild = guild;
    }

    @Override
    public int getMaxExpandSize() {
        return 50;
    }

    public void addItems(final ConcurrentLinkedQueue<Item> addTasks, final Player owner) {
        for (final Item task : addTasks) {
            final int itemId = task.getItemId();
            final long count = task.getCount();
            final ItemTemplate template = task.getTemplate();
            if (template != null) {
                if (GuildWarehouse.staticItems.containsKey(itemId)) {
                    final int index = GuildWarehouse.staticItems.get(itemId);
                    final Item item = this.getItem(index);
                    if (item == null) {
                        this.addItem(task, index);
                    } else {
                        item.addCount(count);
                        task.setObjectId(item.getObjectId());
                    }
                    task.setSlotIndex(index);
                    task.setStorageLocation(this.getLocationType());
                } else if (template.isDoApplyDirectly()) {
                    if (template.getItemType().isSkill()) {
                        SkillService.useSkill(owner, template.getSkillId(), null, Collections.singletonList(owner));
                    }
                    addTasks.remove(task);
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
                    int currentIndex = this.getItemSize() + 2;
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

        //System.out.println("AddTasks null: " + (addTasks == null ? "Yes" : "No"));
        //System.out.println("LocationType null: " + (getLocationType() == null ? "Yes" : "No"));
        //System.out.println("Guild null: " + (this.guild == null ? "Yes" : "No"));

        this.guild.sendBroadcastPacket(new SMAddItemToWarehouse(addTasks, getLocationType(), 0, EPacketTaskType.Update, 0, 0L));
    }

    public void decreaseItem(int index, final long count) {
        final Item item = this.getItem(index);
        item.addCount(-count);
        if (item.getCount() <= 0L) {
            this.removeItem(index);
            if (index >= 2) {
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
        this.guild.sendBroadcastPacket(new SMEraseWarehouseItem(getLocationType(), 0, item.getObjectId(), count));
    }

    public boolean canDecreaseItem(final int index, final long count) {
        final Item item = this.getItem(index);
        return item != null && item.getCount() + count >= 0L;
    }

    public boolean canDecreaseItems(final List<DecreaseItemTask> decreaseTasks) {
        for (final DecreaseItemTask itemTask : decreaseTasks) {
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
            if (task.getTemplate().isDoApplyDirectly()) {
                --taskSize;
            } else {
                if (!task.getTemplate().isStack()) {
                    continue;
                }
                if (GuildWarehouse.staticItems.containsKey(task.getItemId())) {
                    final int slotIndex = GuildWarehouse.staticItems.get(task.getItemId());
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
        }
        System.out.println("FreeSlots=" + freeSlotCount() + ", taskSize=" + taskSize + ", slots=" + slots);

        final boolean result = this.freeSlotCount() >= taskSize - slots;
        if (!result) {
            //if (this.getOwner() != null)
            //	this.getOwner().sendPacket(new SMNak(EStringTable.eErrNoInventoryNotExistFreeSlot, CMListWarehouseItem.class));
        }
        return result;
    }
}
