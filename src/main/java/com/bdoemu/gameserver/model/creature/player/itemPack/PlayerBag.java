package com.bdoemu.gameserver.model.creature.player.itemPack;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.concurrent.CloseableReentrantLock;
import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMAddItemToInventory;
import com.bdoemu.core.network.sendable.SMGetAllEquipSlot;
import com.bdoemu.core.network.sendable.SMInventorySlotCount;
import com.bdoemu.core.network.sendable.SMWarehouseSlotCount;
import com.bdoemu.gameserver.model.creature.DropBag;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.IBagEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.ShopItem;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.stats.elements.Element;
import com.bdoemu.gameserver.model.stats.elements.WeightElement;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class PlayerBag extends JSONable {
    private final CloseableReentrantLock lock;
    private final ADBItemPack[] itemPacks;
    private final List<Item> repurchaseList;
    private final CashInventory cashInventory;
    private final HashMap<Integer, Warehouse> warehouses;
    private ShopItem randomShopItem;
    private Player owner;
    private DropBag dropBag;

    public PlayerBag(final Player player, final AccountBag accountBag) {
        this.lock = new CloseableReentrantLock();
        this.repurchaseList = new ArrayList<>();
        this.owner = player;
        (this.itemPacks = new ADBItemPack[2])[0] = new PlayerInventory(player);
        this.itemPacks[1] = new PlayerEquipments(player);
        this.cashInventory = accountBag.getCashInventory();
        this.warehouses = accountBag.getWarehouses();
    }

    public PlayerBag(final BasicDBObject dbObject, final Player player, final AccountBag accountBag) {
        this.lock = new CloseableReentrantLock();
        this.repurchaseList = new ArrayList<>();
        this.owner = player;
        this.itemPacks = new ADBItemPack[2];
        final BasicDBObject invDB = (BasicDBObject) dbObject.get(EItemStorageLocation.Inventory.toString());
        this.itemPacks[0] = new PlayerInventory(invDB, player);
        this.itemPacks[1] = new PlayerEquipments((BasicDBObject) dbObject.get(EItemStorageLocation.Equipments.toString()), player);
        this.cashInventory = accountBag.getCashInventory();
        this.warehouses = accountBag.getWarehouses();
        if (dbObject.containsField("weightExpands")) {
            final BasicDBList weightBasicDBList = (BasicDBList) dbObject.get("weightExpands");
            for (final Object aWeightBasicDBList : weightBasicDBList) {
                this.owner.getGameStats().getWeight().addElement(new WeightElement((BasicDBObject) aWeightBasicDBList));
            }
        }
    }

    public boolean onEvent(final IBagEvent event) {
        try (final CloseableReentrantLock tempLock = this.lock.open()) {
            if (event.canAct()) {
                event.onEvent();
                return true;
            }
        }
        return false;
    }

    public long getAllMoney() {
        long allMoney = this.getInventory().getItemCount(0);
        for (final Warehouse warehouse : this.warehouses.values()) {
            allMoney += warehouse.getItemCount(0);
        }
        return allMoney;
    }

    public DropBag getDropBag() {
        return this.dropBag;
    }

    public void setDropBag(final DropBag dropBag) {
        this.dropBag = dropBag;
    }

    public ShopItem getRandomShopItem() {
        return this.randomShopItem;
    }

    public void setRandomShopItem(final ShopItem randomShopItem) {
        this.randomShopItem = randomShopItem;
    }

    public Warehouse getWarehouse(final int townId) {
        return this.warehouses.get(townId);
    }

    public Collection<Warehouse> getWarehouses() {
        return this.warehouses.values();
    }

    public List<Item> getRepurchaseList() {
        return this.repurchaseList;
    }

    public PlayerEquipments getEquipments() {
        return (PlayerEquipments) this.itemPacks[EItemStorageLocation.Equipments.ordinal()];
    }

    public PlayerInventory getInventory() {
        return (PlayerInventory) this.itemPacks[EItemStorageLocation.Inventory.ordinal()];
    }

    public CashInventory getCashInventory() {
        return this.cashInventory;
    }

    public ItemPack getItemPack(final EItemStorageLocation storageType) {
        return this.getItemPack(storageType, -1, 1);
    }

    public ItemPack getItemPack(final EItemStorageLocation storageType, final int servantSession, final int townId) {
        switch (storageType) {
            case Inventory: {
                return this.getInventory();
            }
            case Equipments: {
                return this.getEquipments();
            }
            case CashInventory: {
                return this.getCashInventory();
            }
            case GuildWarehouse: {
                final Guild guild = this.owner.getGuild();
                return (guild != null) ? guild.getGuildWarehouse() : null;
            }
            case Warehouse: {
                return this.warehouses.get(townId);
            }
            case ServantInventory: {
                final Servant servant = this.getOwner().getServantController().getServant(servantSession);
                if (servant != null) {
                    return servant.getInventory();
                }
                return null;
            }
            case ServantEquip: {
                final Servant servant = this.getOwner().getServantController().getServant(servantSession);
                if (servant != null) {
                    return servant.getEquipments();
                }
                break;
            }
        }
        return null;
    }

    public Player getOwner() {
        return this.owner;
    }

    public void onLogin() {
        final PlayerInventory inventory = this.owner.getPlayerBag().getInventory();
        final ListSplitter<Item> inventorySplitter = new ListSplitter<>(inventory.getItemMap().values(), 150);
        while (inventorySplitter.hasNext()) {
            this.owner.sendPacket(new SMAddItemToInventory(this.owner.getGameObjectId(), inventorySplitter.getNext(), inventorySplitter.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update));
        }
        final CashInventory cashInventory = this.owner.getPlayerBag().getCashInventory();
        final ListSplitter<Item> cashInventorySplitter = new ListSplitter<>(cashInventory.getItemMap().values(), 150);
        while (cashInventorySplitter.hasNext()) {
            this.owner.sendPacket(new SMAddItemToInventory(this.owner.getGameObjectId(), cashInventorySplitter.getNext(), cashInventorySplitter.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update));
        }
        this.owner.sendPacket(new SMGetAllEquipSlot(this.owner.getPlayerBag().getEquipments()));
        this.owner.sendPacket(new SMInventorySlotCount(inventory.getExpandSize()));
        for (final Warehouse warehouse : this.warehouses.values()) {
            this.owner.sendPacket(new SMWarehouseSlotCount(warehouse));
        }
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList weightExpands = new BasicDBList();
        for (final Element element : this.owner.getGameStats().getWeight().getElements()) {
            if (element instanceof WeightElement) {
                final WeightElement weightElement = (WeightElement) element;
                weightExpands.add(weightElement.toDBObject());
            }
        }
        builder.append("weightExpands", weightExpands);
        for (final ADBItemPack itemPack : this.itemPacks) {
            builder.append(itemPack.getLocationType().toString(), itemPack.toDBObject());
        }
        return builder.get();
    }
}
