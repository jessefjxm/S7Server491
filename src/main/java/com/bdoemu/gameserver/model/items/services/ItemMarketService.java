package com.bdoemu.gameserver.model.items.services;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.core.network.sendable.SMGetAuctionTime;
import com.bdoemu.core.network.sendable.SMListItemHeaderAtItemMarket;
import com.bdoemu.core.network.sendable.SMListItemMasterAtItemMarket;
import com.bdoemu.core.network.sendable.SMNotifyVariableTradeItem;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.databaseCollections.ItemMarketDBCollection;
import com.bdoemu.gameserver.databaseCollections.ItemMarketReservationsDBCollection;
import com.bdoemu.gameserver.databaseCollections.MasterItemMarketDBCollection;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.ItemMarket;
import com.bdoemu.gameserver.model.items.MasterItemMarket;
import com.bdoemu.gameserver.model.items.PartyItemMarket;
import com.bdoemu.gameserver.model.items.ReservationItemMarket;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.world.enums.ETerritoryKeyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@StartupComponent("Service")
public class ItemMarketService extends APeriodicTaskService {
    private static class Holder {
        static final ItemMarketService INSTANCE = new ItemMarketService();
    }

    private static final Logger log = LoggerFactory.getLogger(ItemMarketService.class);
    private final ConcurrentHashMap<Long, ItemMarket> items;
    private final ConcurrentHashMap<Long, PartyItemMarket> partyItems;
    private final ConcurrentHashMap<Long, ItemMarket> waitingItems;
    private final ConcurrentHashMap<Long, PartyItemMarket> waitingPartyItems;
    private final ConcurrentHashMap<Long, List<ReservationItemMarket>> rservationItemMarketMap;
    private ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, MasterItemMarket>> itemsSorted;

    private ItemMarketService() {
        super(3L, TimeUnit.SECONDS);
        this.partyItems = new ConcurrentHashMap<>();
        this.waitingItems = new ConcurrentHashMap<>();
        this.waitingPartyItems = new ConcurrentHashMap<>();
        this.itemsSorted = new ConcurrentHashMap<>();

        Collection<MasterItemMarket> m = MasterItemMarketDBCollection.getInstance().load().values();
        Supplier<Stream<MasterItemMarket>> master = m::stream;

        ItemMarketService.log.info("Loaded {} master marketplace items.", m.size());
        for (final ItemTemplate t : ItemData.getInstance().getTemplates()) {
            if (t.getBasePriceForItemMarket() > 0) {
                final int itemId = t.getItemId();
                for (int i = 0; i < t.getEnchantSize(); ++i) {
                    int finalI = i;
                    Optional<MasterItemMarket> existingItem = master.get().filter(x -> x.getItemId() == itemId && x.getEnchantLevel() == finalI).findFirst();
                    if (existingItem.isPresent()) {
                        itemsSorted.computeIfAbsent(itemId, k -> new ConcurrentHashMap<>()).put(i, existingItem.get());
                    } else {
                        itemsSorted.computeIfAbsent(itemId, k -> new ConcurrentHashMap<>()).put(i, new MasterItemMarket(t, i));
                    }
                }
            }
        }

        this.items = ItemMarketDBCollection.getInstance().load();
        ItemMarketService.log.info("Loaded {} Items in market", this.items.size());
        for (final ItemMarket itemMarket : this.items.values()) {
            if (!itemMarket.isExpired() && itemMarket.getCount() > 0L) {
                if (!this.itemsSorted.containsKey(itemMarket.getItemId())) {
                    ItemMarketService.log.warn("ItemMarketService found an null item {} enchantLevel {} object {} in market", itemMarket.getItemId(), itemMarket.getEnchantLevel(), itemMarket.getObjectId());
                } else if (itemMarket.isWaiting()) {
                    this.waitingItems.put(itemMarket.getObjectId(), itemMarket);
                } else {
                    this.itemsSorted.get(itemMarket.getItemId()).get(itemMarket.getEnchantLevel()).addItem(itemMarket, true);
                }
            }
        }
        this.rservationItemMarketMap = ItemMarketReservationsDBCollection.getInstance().loadReservationItemMarket();
        for (final Map.Entry<Long, List<ReservationItemMarket>> entry : this.rservationItemMarketMap.entrySet()) {
            for (final ReservationItemMarket reservationItemMarket : entry.getValue()) {
                this.itemsSorted.get(reservationItemMarket.getItemId()).get(reservationItemMarket.getEnchantLevel()).setReservationItemMarket(reservationItemMarket);
            }
        }
        ItemMarketService.log.info("Loaded {} Reservation Items in market", this.rservationItemMarketMap.size());
    }

    public static ItemMarketService getInstance() {
        return Holder.INSTANCE;
    }

    public void run() {
        this.waitingItems.values().stream().filter(itemMarket -> !itemMarket.isWaiting() && this.removeWaitingItem(itemMarket)).forEach(itemMarket -> this.itemsSorted.get(itemMarket.getItemId()).get(itemMarket.getEnchantLevel()).addItem(itemMarket, false));
        this.waitingPartyItems.values().stream().filter(partyItemMarket -> !partyItemMarket.isWaiting() && this.removeWaitingPartyItem(partyItemMarket)).forEach(partyItemMarket -> this.partyItems.put(partyItemMarket.getMarketObjectId(), partyItemMarket));
    }

    public boolean removeWaitingItem(final ItemMarket itemMarket) {
        return this.waitingItems.values().remove(itemMarket);
    }

    public boolean removeWaitingPartyItem(final PartyItemMarket itemMarket) {
        return this.waitingPartyItems.values().remove(itemMarket);
    }

    public void putPartyItem(final PartyItemMarket itemMarket) {
        this.waitingPartyItems.put(itemMarket.getMarketObjectId(), itemMarket);
    }

    public void putItem(final ItemMarket itemMarket) {
        ItemMarketDBCollection.getInstance().save(itemMarket);
        this.items.put(itemMarket.getObjectId(), itemMarket);
        final MasterItemMarket masterItemMarket = this.getMasterItemMarket(itemMarket.getItemId(), itemMarket.getEnchantLevel());
        masterItemMarket.checkReservationItem(itemMarket);
        if (itemMarket.getCount() > 0L) {
            this.waitingItems.put(itemMarket.getObjectId(), itemMarket);
        }
    }

    public ItemMarket getItem(final long objectId) {
        return this.items.get(objectId);
    }

    public PartyItemMarket getPartyItem(final long objectId) {
        return this.partyItems.get(objectId);
    }

    public PartyItemMarket removePartyItem(final long objectId) {
        return this.partyItems.remove(objectId);
    }

    public boolean removeItem(final long objectId) {
        final ItemMarket itemMarket = this.items.remove(objectId);
        if (itemMarket != null) {
            ItemMarketDBCollection.getInstance().delete(objectId);
            return true;
        }
        return false;
    }

    public Collection<ItemMarket> getItems(final int itemId, final int enchantLevel) {
        final MasterItemMarket itemMarket = this.getMasterItemMarket(itemId, enchantLevel);
        if (itemMarket == null)
            return Collections.emptyList();
        return itemMarket.getItems();
    }

    public MasterItemMarket getMasterItemMarket(final int itemId, final int enchantLevel) {
        return this.itemsSorted.get(itemId).get(enchantLevel);
    }

    public Collection<MasterItemMarket> getMasterItems() {
        final ArrayList<MasterItemMarket> masterItems = new ArrayList<>();
        for (final ConcurrentHashMap<Integer, MasterItemMarket> values : this.itemsSorted.values()) {
            masterItems.addAll(values.values());
        }
        return masterItems;
    }

    public void onLogin(final Player player) {
        player.sendPacketNoFlush(new SMNotifyVariableTradeItem());
        player.sendPacketNoFlush(new SMGetAuctionTime());

        // Send all items to client with the current managed prices.
        final Collection<MasterItemMarket> masterItems = this.getMasterItems();
        final ListSplitter<MasterItemMarket> firstSplit = new ListSplitter<>(masterItems, 813);
        while (!firstSplit.isLast())
            player.sendPacketNoFlush(new SMListItemMasterAtItemMarket(firstSplit.getNext(), firstSplit.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update));

        // Send all items that are registered at marketplace.
        final ListSplitter<MasterItemMarket> secSplit = new ListSplitter<>(masterItems, 198);
        while (!secSplit.isLast())
            player.sendPacketNoFlush(new SMListItemHeaderAtItemMarket(secSplit.getNext(), secSplit.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update));
    }

    public Collection<ItemMarket> getRegisteredItems(final long accountId) {
        return this.items.values().stream().filter(itemMarket -> itemMarket.getAccountId() == accountId).collect(Collectors.toList());
    }

    public long getRegisteredItemSize(final long accountId) {
        return this.items.values().stream().filter(itemMarket -> itemMarket.getAccountId() == accountId).count();
    }

    public Collection<ItemMarket> getItems() {
        return this.items.values();
    }

    public Collection<PartyItemMarket> getSellByPartyItems() {
        return this.partyItems.values();
    }

    public List<ReservationItemMarket> getReservationItemsMarket(final long accountId) {
        final List<ReservationItemMarket> reservationItemsMarket = this.rservationItemMarketMap.get(accountId);
        return (reservationItemsMarket == null) ? Collections.emptyList() : reservationItemsMarket;
    }

    public ConcurrentHashMap<Long, List<ReservationItemMarket>> getRservationItemMarketMap() {
        return this.rservationItemMarketMap;
    }

    public boolean registerReservationAtItemMarket(final Player player, final int itemId, final int enchantLevel, final ETerritoryKeyType territoryKeyType, final long money, final long count) {
        final MasterItemMarket masterItemMarket = this.getMasterItemMarket(itemId, enchantLevel);
        return masterItemMarket != null && masterItemMarket.registerReservationAtItemMarket(player, itemId, enchantLevel, territoryKeyType, money, count);
    }

    public boolean cancelReservationPurchaseAtItemMarket(final Player player, final int itemId, final int enchantLevel) {
        final MasterItemMarket masterItemMarket = this.getMasterItemMarket(itemId, enchantLevel);
        return masterItemMarket != null && masterItemMarket.cancelReservationPurchaseAtItemMarket(player);
    }
}
