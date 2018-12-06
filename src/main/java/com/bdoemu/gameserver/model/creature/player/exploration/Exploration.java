package com.bdoemu.gameserver.model.creature.player.exploration;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.receivable.CMLaunchTriggerOfExplorationNodeFirstVisit;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.dataholders.xml.ExploreData;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.exploration.templates.ExplorationTemplate;
import com.bdoemu.gameserver.model.creature.player.exploration.templates.WaypointTemplate;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AddItemEvent;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.utils.MathUtils;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Exploration extends JSONable {
    private HashMap<Integer, Discovery> discoveryList;
    private Player player;
    private static List<Integer> disabledWaypointIds;

    static {
        disabledWaypointIds = new ArrayList<>();

        // Altinova:
        disabledWaypointIds.add(1190); // Gulabi Invesment Bank
        disabledWaypointIds.add(1192); // Neruda Shen Invesment Bank
        disabledWaypointIds.add(1191); // Quina nvesment Bank
        disabledWaypointIds.add(1189); // Zigmund Invesment Bank

        //Shakatu:
        disabledWaypointIds.add(1549); // Taphtar Invesment Bank
        disabledWaypointIds.add(1550); // Valgon Invesment Bank

        //Sand Grain Bazaar:
        disabledWaypointIds.add(1547); // Atui Balacs Invesment Bank

        // Valencia:
        disabledWaypointIds.add(1552); // Zahad Invesment Bank
        disabledWaypointIds.add(1551); // Yis Kunjamin Invesment Bank)
    }

    public Exploration(final Player player) {
        this.discoveryList = new HashMap<>();
        this.player = player;
    }

    public Exploration(final Player player, final BasicDBObject dbObject) {
        this(player);
        final BasicDBList discoveryDB = (BasicDBList) dbObject.get("exploration");
        for (Object aDiscoveryDB : discoveryDB) {
            final Discovery discovery = new Discovery((BasicDBObject) aDiscoveryDB);
            if (disabledWaypointIds.contains(discovery.getWaypointId()))
                continue;
            this.discoveryList.put(discovery.getWaypointId(), discovery);
        }
    }

    public Collection<Discovery> getDiscoveryList() {
        return this.discoveryList.values();
    }

    public boolean learnDiscovery(final int waypointKey, final boolean isAutoDiscovery) {
        final ExplorationTemplate exploreTemplate = ExploreData.getInstance().getTemplate(waypointKey);
        if (exploreTemplate == null)
            return false;

        final WaypointTemplate waypoint = exploreTemplate.getWaypointTemplate();
        if (waypoint == null)
            return false;

        if (disabledWaypointIds.contains(waypoint.getWaypointKey()))
            return false;

        final Location loc = this.player.getLocation();
        if (isAutoDiscovery && waypoint.isAutoDiscovery() && !MathUtils.isInRange(loc.getX(), loc.getY(), exploreTemplate.getX(), exploreTemplate.getY(), waypoint.getDiscoveryRange() + 300)) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoDistanceIsFar, CMLaunchTriggerOfExplorationNodeFirstVisit.class, waypointKey));
            return false;
        }
        if (!this.addDiscovery(waypointKey)) {
            return false;
        }
        if (isAutoDiscovery) {
            final Integer itemGroup = waypoint.getItemKeyOnDiscovery();
            if (itemGroup != null) {
                final List<ItemSubGroupT> drops = ItemMainGroupService.getItems(this.player, itemGroup);
                final ConcurrentLinkedQueue<Item> addTasks = new ConcurrentLinkedQueue<Item>();
                for (final ItemSubGroupT itemSubGroupT : drops) {
                    final ItemTemplate itemTemplate = ItemData.getInstance().getItemTemplate(itemSubGroupT.getItemId());
                    if (itemTemplate == null) {
                        continue;
                    }
                    addTasks.add(new Item(itemTemplate, Rnd.get(itemSubGroupT.getMinCount(), itemSubGroupT.getMaxCount()), itemSubGroupT.getEnchantLevel()));
                }
                this.player.getPlayerBag().onEvent(new AddItemEvent(this.player, addTasks));
            }
        }
        this.player.getObserveController().notifyObserver(EObserveType.explore, waypointKey, this.player.getExploration().getDiscoveryList().size());
        this.player.sendPacket(new SMRegisterExplorationNode(waypointKey));
        final Discovery discovery = this.discoveryList.get(waypointKey);
        if (discovery.isActive()) {
            this.player.sendPacket(new SMUpgradeExplorationNode(discovery));
        }
        return true;
    }

    public boolean contains(final int waypointKey) {
        return this.discoveryList.containsKey(waypointKey);
    }

    public boolean addDiscovery(final int waypointKey) {
        if (disabledWaypointIds.contains(waypointKey))
            return false;
        return this.discoveryList.putIfAbsent(waypointKey, Discovery.newDiscovery(waypointKey)) == null;
    }

    public Discovery getDiscovery(final int waypointKey) {
        return this.discoveryList.get(waypointKey);
    }

    public void upgradeExplorationNode(final int waypointKey) {
        if (disabledWaypointIds.contains(waypointKey))
            return;
        synchronized (this.discoveryList) {
            final Discovery discovery = this.discoveryList.get(waypointKey);
            if (discovery != null && !discovery.isActive() && this.player.getExplorePointHandler().getMainExplorePoint().addPoints(-discovery.getNeedExplorePoint())) {
                discovery.setContribution(discovery.getNeedExplorePoint());
                discovery.setLevel(1);
                this.player.sendPacket(new SMUpgradeExplorationNode(discovery));
                this.player.getObserveController().notifyObserver(EObserveType.contribute, discovery.getNeedExplorePoint());
            }
        }
    }

    public void plantWithdraw(final int waypointKey) {
        if (disabledWaypointIds.contains(waypointKey))
            return;
        synchronized (this.discoveryList) {
            final Discovery discovery = this.discoveryList.get(waypointKey);
            if (discovery != null && discovery.isActive() && this.player.getExplorePointHandler().getMainExplorePoint().addPoints(discovery.getContribution())) {
                discovery.setContribution(0);
                discovery.setLevel(0);
                discovery.setExp(0);
                this.player.sendPacket(new SMPlantWithdraw(waypointKey));
            }
        }
    }

    public void increaseExperienceToExplorationNode(final int waypointKey, final int wp) {
        if (disabledWaypointIds.contains(waypointKey))
            return;
        synchronized (this.discoveryList) {
            final Discovery discovery = this.discoveryList.get(waypointKey);
            if (wp >= 10 && discovery != null && discovery.isActive()) {
                final int count = wp / 10;
                if (this.player.addWp(-(10 * count))) {
                    discovery.addExp(1000 * count);
                    this.player.sendPacket(new SMIncreaseExperienceToExplorationNode(discovery));
                }
            }
        }
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList discoveryDB = new BasicDBList();
        for (final Discovery discovery : this.discoveryList.values()) {
            discoveryDB.add(discovery.toDBObject());
        }
        builder.append("exploration", discoveryDB);
        return builder.get();
    }
}
