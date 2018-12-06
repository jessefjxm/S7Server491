package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMListWarehouseItem;
import com.bdoemu.core.network.sendable.SMAddItemToInventory;
import com.bdoemu.core.network.sendable.SMEraseInventoryItem;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.*;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.service.database.DatabaseLogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AItemEvent implements IBagEvent {
    protected final Player player;
    protected final Playable srcActor;
    protected final Playable dstActor;
    protected final PlayerBag playerBag;
    protected final PlayerInventory playerInventory;
    protected final CashInventory cashInventory;
    protected ConcurrentLinkedQueue<Item> addTasks;
    protected ConcurrentLinkedQueue<Item> addWHTasks;
    protected ConcurrentLinkedQueue<Item> addGuildWHTasks;
    protected List<DecreaseItemTask> decreaseTasks;
    protected List<DecreaseItemTask> decreaseWHTasks;
    protected List<DecreaseItemTask> decreaseGuildWHTasks;
    protected int regionId;
    protected EStringTable addMessageId;
    protected EStringTable removeMessageId;
    protected Warehouse warehouse;
    protected GuildWarehouse guildWarehouse;
    protected int whAddType;
    protected long whSenderObjId;

    public AItemEvent(final Player player, final Playable srcActor, final Playable dstActor, final EStringTable addMessageId, final EStringTable removeMessageId, final int regionId) {
        this.whAddType = 0;
        this.player = player;
        this.srcActor = srcActor;
        this.dstActor = dstActor;
        this.addMessageId = addMessageId;
        this.removeMessageId = removeMessageId;
        this.playerBag = player.getPlayerBag();
        this.playerInventory = this.playerBag.getInventory();
        this.cashInventory = this.playerBag.getCashInventory();
        this.regionId = regionId;
        this.warehouse = this.playerBag.getWarehouse(regionId);
        this.guildWarehouse = player.getGuild() != null ? player.getGuild().getGuildWarehouse() : null;
    }

    public Item addItem(final int itemId, final long count, final int enchantLevel) {
        final ItemTemplate template = ItemData.getInstance().getItemTemplate(itemId);
        return this.addItem(template, count, enchantLevel);
    }

    public Item addItem(final ItemTemplate template, final long count, final int enchantLevel) {
        final Item newItem = new Item(template, count, enchantLevel);
        this.addItem(newItem);
        return newItem;
    }

    public void addItem(final Item addItem) {
        if (this.addTasks == null) {
            this.addTasks = new ConcurrentLinkedQueue<Item>();
        }
        this.addTasks.add(addItem);
    }

    public void addWHItem(final Item addItem) {
        if (this.addWHTasks == null) {
            this.addWHTasks = new ConcurrentLinkedQueue<Item>();
        }
        this.addWHTasks.add(addItem);
    }

    public void addGuildWHItem(final Item addItem) {
        if (this.guildWarehouse != null) {
            if (this.addGuildWHTasks == null) {
                this.addGuildWHTasks = new ConcurrentLinkedQueue<Item>();
            }
            this.addGuildWHTasks.add(addItem);
        }
    }

    public DecreaseItemTask decreaseItem(final int slotIndex, final long count, final EItemStorageLocation storageLocation) {
        final DecreaseItemTask decreaseItemTask = new DecreaseItemTask(slotIndex, count, storageLocation);
        this.decreaseItem(decreaseItemTask);
        return decreaseItemTask;
    }

    public void decreaseItem(final DecreaseItemTask removeItem) {
        switch (removeItem.getStorageLocation()) {
            case CashInventory:
            case Inventory:
            case ServantInventory: {
                if (this.decreaseTasks == null) {
                    this.decreaseTasks = new ArrayList<DecreaseItemTask>();
                }
                this.decreaseTasks.add(removeItem);
                break;
            }
            case Warehouse: {
                if (this.decreaseWHTasks == null) {
                    this.decreaseWHTasks = new ArrayList<DecreaseItemTask>();
                }
                this.decreaseWHTasks.add(removeItem);
                break;
            }
            case GuildWarehouse: {
                if (this.guildWarehouse != null) {
                    if (this.decreaseGuildWHTasks == null) {
                        this.decreaseGuildWHTasks = new ArrayList<DecreaseItemTask>();
                    }
                    this.decreaseGuildWHTasks.add(removeItem);
                }
                break;
            }
        }
    }

    @Override
    public void onEvent() {
        if (this.decreaseWHTasks != null) {
            for (final DecreaseItemTask decreaseItemTask : this.decreaseWHTasks) {
                this.warehouse.decreaseItem(decreaseItemTask.getSlotIndex(), decreaseItemTask.getCount());
            }
        }
        if (this.guildWarehouse != null) {
            if (this.decreaseGuildWHTasks != null) {
                for (final DecreaseItemTask decreaseItemTask : this.decreaseGuildWHTasks) {
                    this.guildWarehouse.decreaseItem(decreaseItemTask.getSlotIndex(), decreaseItemTask.getCount());
                }
            }
        }
        if (this.decreaseTasks != null) {
            if (this.srcActor.isPlayer()) {
                this.cashInventory.decreaseItems(this.decreaseTasks);
                this.playerInventory.decreaseItems(this.decreaseTasks);
            } else {
                this.srcActor.getInventory().decreaseItems(this.decreaseTasks);
            }
            if (!this.decreaseTasks.isEmpty()) {
                this.player.sendPacket(new SMEraseInventoryItem(this.srcActor.getGameObjectId(), this.decreaseTasks, this.removeMessageId));
            }
        }
        if (this.addTasks != null) {
            if (this.dstActor.isPlayer()) {
                this.cashInventory.addItems(this.addTasks);
                this.playerInventory.addItems(this.addTasks);
            } else {
                this.dstActor.getInventory().addItems(this.addTasks);
            }
            if (!this.addTasks.isEmpty()) {
                this.player.sendPacket(new SMAddItemToInventory(this.dstActor.getGameObjectId(), this.addTasks, this.addMessageId, EPacketTaskType.Update));
                if (this.isLoggable()) {
                    DatabaseLogFactory.getInstance().logItems(this.player, this.getClass().getSimpleName(), this.addTasks);
                }
            }
        }
        if (this.addWHTasks != null) {
            this.warehouse.addItems(this.addWHTasks, this.whSenderObjId, this.whAddType);
            if (this.isLoggable()) {
                DatabaseLogFactory.getInstance().logItems(this.player, this.getClass().getSimpleName(), this.addWHTasks);
            }
        }
        if (this.guildWarehouse != null) {
            if (this.addGuildWHTasks != null) {
                System.out.println("nullGuild: " + (guildWarehouse == null ? "Yes" : "No"));
                this.guildWarehouse.addItems(this.addGuildWHTasks, this.player);
                if (this.isLoggable()) {
                    DatabaseLogFactory.getInstance().logItems(this.player, this.getClass().getSimpleName(), this.addGuildWHTasks);
                }
            }
        }
    }

    @Override
    public boolean canAct() {
        //System.out.println("AItemEvent#1");
        if (this.decreaseTasks != null) {
            if (this.srcActor.isPlayer()) {
                if (!this.cashInventory.canDecreaseItems(this.decreaseTasks) || !this.playerInventory.canDecreaseItems(this.decreaseTasks)) {
                    return false;
                }
            } else if (this.srcActor.isVehicle() && !this.srcActor.getInventory().canDecreaseItems(this.decreaseTasks)) {
                return false;
            }
        }
        //System.out.println("AItemEvent#2");
        if (this.decreaseWHTasks != null) {
            if (this.warehouse == null) {
                this.player.sendPacket(new SMNak(EStringTable.eErrNoCantFindWarehouseInRegion, CMListWarehouseItem.class));
                return false;
            }
            if (!this.warehouse.canDecreaseItems(this.decreaseWHTasks)) {
                return false;
            }
        }
        //System.out.println("AItemEvent#3");
        if (this.decreaseGuildWHTasks != null) {
            if (this.guildWarehouse == null) {
                this.player.sendPacket(new SMNak(EStringTable.eErrNoCantFindWarehouseInRegion, CMListWarehouseItem.class));
                return false;
            }
            if (!this.guildWarehouse.canDecreaseItems(this.decreaseGuildWHTasks)) {
                return false;
            }
        }
        //System.out.println("AItemEvent#4");
        if (this.addWHTasks != null) {
            if (this.warehouse == null) {
                this.player.sendPacket(new SMNak(EStringTable.eErrNoCantFindWarehouseInRegion, CMListWarehouseItem.class));
                return false;
            }
            if (!this.warehouse.canAddItems(this.addWHTasks)) {
                return false;
            }
        }
        if (this.guildWarehouse != null) {
            //System.out.println("AItemEvent#5");
            if (this.addGuildWHTasks != null) {
                //System.out.println("AItemEvent#5=1");
                if (this.guildWarehouse == null) {
                    //System.out.println("AItemEvent#5=1=1");
                    this.player.sendPacket(new SMNak(EStringTable.eErrNoCantFindWarehouseInRegion, CMListWarehouseItem.class));
                    return false;
                }
                //System.out.println("AItemEvent#5=2");
                if (!this.guildWarehouse.canAddItems(this.addGuildWHTasks)) {
                    //System.out.println("AItemEvent#5=1");
                    return false;
                }
                //System.out.println("AItemEvent#5=3");
            }
        }
        //System.out.println("AItemEvent#6");
        if (this.addTasks != null) {
            if (this.dstActor.isPlayer()) {
                if ((this.addTasks != null && !this.playerInventory.canAddItems(this.addTasks)) || !this.cashInventory.canAddItems(this.addTasks)) {
                    return false;
                }
            } else if (this.dstActor.isVehicle() && this.addTasks != null && !this.dstActor.getInventory().canAddItems(this.addTasks)) {
                return false;
            }
        }
        //System.out.println("AItemEvent#7");
        return true;
    }

    public boolean isLoggable() {
        return true;
    }
}
