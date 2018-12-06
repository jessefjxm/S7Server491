// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.worker;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.concurrent.CloseableReentrantLock;
import com.bdoemu.core.network.sendable.SMAddMyNpcWorker;
import com.bdoemu.core.network.sendable.SMAddNpcWorkerWorking;
import com.bdoemu.gameserver.databaseCollections.NpcWorkersDBCollection;
import com.bdoemu.gameserver.model.auction.NpcWorkerItemMarket;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.npc.worker.events.INpcWorkerEvent;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.House;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class NpcWorkerController {
    protected final CloseableReentrantLock lock;
    private final ConcurrentHashMap<Long, NpcWorker> npcWorkers;
    private final Player player;

    public NpcWorkerController(final Player player) {
        this.lock = new CloseableReentrantLock();
        this.player = player;
        this.npcWorkers = NpcWorkersDBCollection.getInstance().load(player);
        for (final NpcWorkerItemMarket npcWorkerItemMarket : AuctionGoodService.getInstance().getRegisteredNpcWorkers(player.getAccountId())) {
            this.npcWorkers.put(npcWorkerItemMarket.getNpcWorker().getObjectId(), npcWorkerItemMarket.getNpcWorker());
        }
    }

    public static int getSlotsByLodgingLevel(final int level) {
        switch (level) {
            case 5: {
                return 4;
            }
            case 4: {
                return 4;
            }
            case 3: {
                return 4;
            }
            case 2: {
                return 2;
            }
            default: {
                return 1;
            }
        }
    }

    public boolean onEvent(final INpcWorkerEvent event) {
        boolean result = false;
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            if (event.canAct()) {
                result = true;
                event.onEvent();
            }
        }
        return result;
    }

    public int getWorkerSlotsByRegionId(final int regionId) {
        int slots = 1;
        for (final House house : this.player.getHouseStorage().getHouseList()) {
            if (house.getHouseInfoT().getAffiliatedTown() == regionId && house.getHouseReceipeType().isLodging()) {
                slots += getSlotsByLodgingLevel(house.getLevel());
            }
        }
        return slots;
    }

    public NpcWorker addNpcWorker(final int characterKey, final int regionId) {
        if (this.size(regionId) >= this.getWorkerSlotsByRegionId(regionId)) {
            return null;
        }
        final NpcWorker npcWorker = NpcWorker.newNpcWorker(characterKey, regionId, this.player.getAccountId());
        this.addNpcWorker(npcWorker);
        return npcWorker;
    }

    public Collection<NpcWorker> getNpcWorkers() {
        return this.npcWorkers.values();
    }

    public void save() {
        NpcWorkersDBCollection.getInstance().update(this.npcWorkers.values().stream().filter(npcWorker -> npcWorker.getAuctionRegisterType().isNone()).collect(Collectors.toList()));
    }

    public int size(final int regionId) {
        return (int) this.npcWorkers.values().stream().filter(worker -> worker.getRegionId() == regionId).count();
    }

    public final void addNpcWorker(final NpcWorker npcWorker) {
        this.npcWorkers.put(npcWorker.getObjectId(), npcWorker);
        NpcWorkersDBCollection.getInstance().save(npcWorker);
    }

    public final boolean removeNpcWorker(final NpcWorker worker) {
        final boolean result = this.npcWorkers.values().remove(worker);
        if (result) {
            NpcWorkersDBCollection.getInstance().delete(worker.getObjectId());
        }
        return result;
    }

    public final NpcWorker getNpcWorker(final long objectId) {
        return this.npcWorkers.get(objectId);
    }

    public void onLogin() {
        if (!this.npcWorkers.isEmpty()) {
            final ListSplitter<NpcWorker> splitter = new ListSplitter<>(this.npcWorkers.values(), 27);
            while (!splitter.isLast()) {
                this.player.sendPacket(new SMAddMyNpcWorker(splitter.getNext(), splitter.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update));
            }
            for (final NpcWorker npcWorker : this.npcWorkers.values()) {
                if (npcWorker.getStartTime() > 0L) {
                    this.player.sendPacket(new SMAddNpcWorkerWorking(Collections.singletonList(npcWorker), false, 1));
                }
            }
        }
    }
}
