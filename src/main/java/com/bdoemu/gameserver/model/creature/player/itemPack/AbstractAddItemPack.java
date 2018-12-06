package com.bdoemu.gameserver.model.creature.player.itemPack;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMEraseInventoryItem;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.DecreaseItemTask;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.stats.Stat;
import com.mongodb.BasicDBObject;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AbstractAddItemPack extends ADBItemPack {
    public AbstractAddItemPack(final EItemStorageLocation locationType, final Stat expandStat, final Playable playable) {
        super(locationType, expandStat, playable);
    }

    public AbstractAddItemPack(final EItemStorageLocation locationType, final BasicDBObject dbObject, final Playable playable) {
        super(locationType, dbObject, playable);
    }

    public void decrease(final int slotIndex, final long count) {
        this.decrease(slotIndex, count, EStringTable.NONE);
    }

    public void decrease(final int slotIndex, final long count, final EStringTable functionId) {
        final Item item = this.getItem(slotIndex);
        item.addCount(-count);
        if (item.getCount() <= 0L) {
            this.removeItem(slotIndex);
            if (item.getTemplate().getItemActionNumber() > 0) {
                this.backpack.remove(item);
            }
        }
        final DecreaseItemTask task = new DecreaseItemTask(slotIndex, count, this.getLocationType());
        task.setObjectId(item.getObjectId());
        final List<DecreaseItemTask> decreaseTasks = Collections.singletonList(task);
        this.getOwner().sendPacket(new SMEraseInventoryItem(this.getOwner().getGameObjectId(), decreaseTasks, functionId));
    }

    public void decreaseItems(final List<DecreaseItemTask> decreaseTasks) {
        for (final DecreaseItemTask task : decreaseTasks) {
            if (task.getStorageLocation() != this.getLocationType()) {
                continue;
            }
            final int slotIndex = task.getSlotIndex();
            final Item item = this.getItem(slotIndex);
            if (item != null) {
                item.addCount(-task.getCount());
                this.getOwner().getGameStats().getWeight().addWeight(-item.getTemplate().getWeight() * task.getCount());
                if (item.getCount() <= 0L) {
                    this.removeItem(slotIndex);
                    if (item.getTemplate().getItemActionNumber() > 0) {
                        this.backpack.remove(item);
                    }
                }
                task.setObjectId(item.getObjectId());
            }
        }
    }

    public boolean canDecreaseItems(final List<DecreaseItemTask> decreaseTasks) {
        for (final DecreaseItemTask task : decreaseTasks) {
            if (task.getCount() <= 0L) {
                return false;
            }
            if (task.getStorageLocation() != this.getLocationType()) {
                continue;
            }
            final int slotIndex = task.getSlotIndex();
            if (slotIndex > this.getExpandSize()) {
                return false;
            }
            final Item item = this.getItem(slotIndex);
            if (item == null || item.getCount() < task.getCount()) {
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
            if (task.getTemplate().isCash() && !this.getLocationType().isCashInventory()) {
                --taskSize;
            } else if (!task.getTemplate().isCash() && this.getLocationType().isCashInventory()) {
                --taskSize;
            } else if (task.getTemplate().isDoApplyDirectly()) {
                --taskSize;
            } else {
                if (!task.getTemplate().isStack()) {
                    continue;
                }
                if (AbstractAddItemPack.staticItems.containsKey(task.getItemId())) {
                    final int slotIndex = AbstractAddItemPack.staticItems.get(task.getItemId());
                    final Item item = this.getItem(slotIndex);
                    if (item != null && item.getCount() + task.getCount() < 0L) {
                        return false;
                    }
                    --taskSize;
                } else {
                    for (int slotIndex = this.getDefaultSlotIndex(); slotIndex < this.getExpandSize(); ++slotIndex) {
                        final Item item = this.getItem(slotIndex);
                        if (item != null && item.getItemId() == task.getItemId()) {
                            ++slots;
                            break;
                        }
                    }
                }
            }
        }
        final boolean result = this.freeSlotCount() >= taskSize - slots;
        if (!result) {
            this.getOwner().sendPacket(new SMNak(EStringTable.eErrNoInventoryNotExistFreeSlot, 3064));
        }
        return result;
    }

    public void addItems(final ConcurrentLinkedQueue<Item> addTasks) {
        for (final Item task : addTasks) {
            if (task.getTemplate().isCash() && !this.getLocationType().isCashInventory()) {
                continue;
            }
            if (!task.getTemplate().isCash() && this.getLocationType().isCashInventory()) {
                continue;
            }
            final int itemId = task.getItemId();
            final long count = task.getCount();
            if (this.getOwner().isPlayer()) {
                ((Player) this.getOwner()).getObserveController().notifyObserver(EObserveType.acquireItem, itemId, count);
            }
            final ItemTemplate template = task.getTemplate();
            if (template == null) {
                continue;
            }
            if (AbstractAddItemPack.staticItems.containsKey(itemId)) {
                final int index = AbstractAddItemPack.staticItems.get(itemId);
                final Item item = this.getItem(index);
                if (item == null) {
                    this.addItem(task, index);
                } else {
                    item.addCount(count);
                    task.setObjectId(item.getObjectId());
                }
                task.setSlotIndex(index);
                task.setStorageLocation(this.getLocationType());
                this.getOwner().getGameStats().getWeight().addWeight(template.getWeight() * count);
            } else if (template.isDoApplyDirectly()) {
                if (template.getItemType().isSkill()) {
                    SkillService.useSkill(this.getOwner(), template.getSkillId(), null, Collections.singletonList(this.getOwner()));
                }
                addTasks.remove(task);
            } else {
                if (task.getTemplate().isStack()) {
                    boolean found = false;
                    for (final int slotIndex : this.getItemMap().keySet()) {
                        final Item stackItem = this.getItem(slotIndex);
                        if (stackItem != null && stackItem.getItemId() == itemId) {
                            stackItem.addCount(count);
                            task.setObjectId(stackItem.getObjectId());
                            task.setSlotIndex(slotIndex);
                            task.setStorageLocation(this.getLocationType());
                            this.getOwner().getGameStats().getWeight().addWeight(template.getWeight() * count);
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        continue;
                    }
                }
                for (int slotIndex2 = this.getDefaultSlotIndex(); slotIndex2 < this.getExpandSize(); ++slotIndex2) {
                    if (this.getItem(slotIndex2) == null) {
                        task.setSlotIndex(slotIndex2);
                        task.setStorageLocation(this.getLocationType());
                        if (task.getTemplate().getItemActionNumber() > 0) {
                            this.backpack.add(task);
                        }
                        if (task.getTemplate().isForTrade() && task.getRegionId() == 1) {
                            task.setRegionId(this.getOwner().getRegionId());
                        }
                        this.addItem(task, slotIndex2);
                        this.getOwner().getGameStats().getWeight().addWeight(template.getWeight() * count);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public int getDefaultSlotIndex() {
        return 2;
    }

    @Override
    public int freeSlotCount() {
        final int maxSize = this.getExpandSize() - 2;
        int mapSize = this.itemMap.size();
        if (this.itemMap.containsKey(0)) {
            --mapSize;
        }
        if (this.itemMap.containsKey(1)) {
            --mapSize;
        }
        return maxSize - mapSize;
    }
}
