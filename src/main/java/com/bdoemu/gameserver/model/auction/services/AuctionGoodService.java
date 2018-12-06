package com.bdoemu.gameserver.model.auction.services;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMBuyItNowServantAuctionGoodsVer2;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.databaseCollections.NpcWorkerAuctionDBCollection;
import com.bdoemu.gameserver.databaseCollections.ServantAuctionDBCollection;
import com.bdoemu.gameserver.model.auction.NpcWorkerItemMarket;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.bdoemu.gameserver.model.creature.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@StartupComponent("Service")
public class AuctionGoodService {
    private static final Logger log = LoggerFactory.getLogger(AuctionGoodService.class);
    private final ConcurrentHashMap<Long, ServantItemMarket> servants;
    private final ConcurrentLinkedQueue<ServantItemMarket> servantsForSale;
    private final ConcurrentLinkedQueue<ServantItemMarket> matingServants;
    private final ConcurrentHashMap<Long, NpcWorkerItemMarket> npcWorkers;
    private final ConcurrentLinkedQueue<NpcWorkerItemMarket> npcWorkersForSale;
    private AuctionGoodService() {
        this.servantsForSale = new ConcurrentLinkedQueue<>();
        this.matingServants = new ConcurrentLinkedQueue<>();
        this.npcWorkersForSale = new ConcurrentLinkedQueue<>();
        this.servants = ServantAuctionDBCollection.getInstance().load();
        AuctionGoodService.log.info("Loaded {} Servant in auction of goods", this.servants.size());
        this.servants.values().stream().filter(servantItemMarket -> !servantItemMarket.isSold()).forEach(servantItemMarket -> {
            if (servantItemMarket.getAuctionRegisterType().isServantMatingMarket()) {
                this.matingServants.add(servantItemMarket);
            } else if (servantItemMarket.getAuctionRegisterType().isServantMarket()) {
                this.servantsForSale.add(servantItemMarket);
            }
        });
        this.npcWorkers = NpcWorkerAuctionDBCollection.getInstance().load();
        AuctionGoodService.log.info("Loaded {} Npc Workers in auction of goods", this.npcWorkers.size());
        this.npcWorkers.values().stream().filter(npcWorkerItemMarket -> !npcWorkerItemMarket.isSold()).forEach(this.npcWorkersForSale::add);
    }

    public static AuctionGoodService getInstance() {
        return Holder.INSTANCE;
    }

    public HashMap<Long, ServantItemMarket> getRegisteredServants(long accountId) {
        return this.servants.values().stream().filter(servantItemMarket -> servantItemMarket.getAccountId() == accountId).collect(HashMap::new, (m, c) -> m.put(c.getServant().getObjectId(), c), (m, u) -> {
        });
    }

    public Collection<NpcWorkerItemMarket> getRegisteredNpcWorkers(final long accountId) {
        return this.npcWorkers.values().stream().filter(npcWorkerItem -> !npcWorkerItem.isSold() && npcWorkerItem.getAccountId() == accountId).collect(Collectors.toList());
    }

    public ConcurrentLinkedQueue<ServantItemMarket> getMatingServants() {
        return this.matingServants;
    }

    public ServantItemMarket getServantItemMarket(final long itemMarketObject) {
        return this.servants.get(itemMarketObject);
    }

    public boolean removeServant(final ServantItemMarket servantItemMarket) {
        return this.servants.values().remove(servantItemMarket);
    }

    public boolean removeNpcWorker(final NpcWorkerItemMarket npcWorkerItemMarket) {
        return this.npcWorkers.values().remove(npcWorkerItemMarket);
    }

    public NpcWorkerItemMarket getNpcWorkerItemMarket(final long itemMarketObject) {
        return this.npcWorkers.get(itemMarketObject);
    }

    public boolean buyServantItemMarket(final ServantItemMarket servantItemMarket, final Player player) {
        final boolean result = this.servantsForSale.remove(servantItemMarket);
        if (!result) {
            player.sendPacket(new SMNak(EStringTable.eErrNoBuyItNowServantFailed, CMBuyItNowServantAuctionGoodsVer2.class));
        }
        return result;
    }

    public boolean buyNpcWorkerItemMarket(final NpcWorkerItemMarket npcWorkerItemMarket, final Player player) {
        final boolean result = this.npcWorkersForSale.remove(npcWorkerItemMarket);
        if (!result) {
            player.sendPacket(new SMNak(EStringTable.eErrNoNpcWorkerBuyItNowFailed, CMBuyItNowServantAuctionGoodsVer2.class));
        }
        return result;
    }

    public boolean registerServant(final ServantItemMarket servant) {
        return this.servants.putIfAbsent(servant.getObjectId(), servant) == null && this.servantsForSale.add(servant);
    }

    public boolean registerMatingServant(final ServantItemMarket servant) {
        return this.servants.putIfAbsent(servant.getObjectId(), servant) == null && this.matingServants.add(servant);
    }

    public boolean registerNpcWorker(final NpcWorkerItemMarket npcWorker) {
        return this.npcWorkers.putIfAbsent(npcWorker.getObjectId(), npcWorker) == null && this.npcWorkersForSale.add(npcWorker);
    }

    public Collection<ServantItemMarket> getMyRegisteredMarketServants(final int pageIndex) {
        return this.servantsForSale.stream().skip(pageIndex * 4).limit(4L).collect(Collectors.toList());
    }

    public Collection<ServantItemMarket> getMatingServantsByPage(final int pageIndex, final long accountId) {
        return this.matingServants.stream().filter(servantItemMarket -> !servantItemMarket.isSelfOnly() || servantItemMarket.getAccountId() == accountId).skip(pageIndex * 4).limit(4L).collect(Collectors.toList());
    }

    public Collection<NpcWorkerItemMarket> getNpcWorkersByPage(final int pageIndex) {
        return this.npcWorkers.values().stream().skip(pageIndex * 4).limit(4L).collect(Collectors.toList());
    }

    public Collection<ServantItemMarket> getMyRegisteredServants(final long accountId) {
        return this.servants.values().stream().filter(servant -> servant.getAccountId() == accountId && (!servant.isSold() || servant.getAuctionRegisterType().isServantMatingMarket())).collect(Collectors.toList());
    }

    public Collection<ServantItemMarket> getMyRegisteredMarketServants(final long accountId) {
        return this.servants.values().stream().filter(servant -> servant.getAuctionRegisterType().isServantMarket() && servant.getAccountId() == accountId).collect(Collectors.toList());
    }

    public Collection<ServantItemMarket> getMyRegisteredMatingServants(final long accountId) {
        return this.servants.values().stream().filter(servant -> servant.getAuctionRegisterType().isServantMatingMarket() && servant.getAccountId() == accountId).collect(Collectors.toList());
    }

    public Collection<NpcWorkerItemMarket> getMyRegisteredNpcWorkers(final long accountId) {
        return this.npcWorkers.values().stream().filter(servant -> servant.getAccountId() == accountId).collect(Collectors.toList());
    }

    public ServantItemMarket removeServant(final long accountId, final long objectId) {
        final ServantItemMarket servantItemMarket = this.servants.get(objectId);
        if (servantItemMarket == null || servantItemMarket.getAccountId() != accountId || !this.servantsForSale.remove(servantItemMarket)) {
            return null;
        }
        return this.servants.remove(objectId);
    }

    public ServantItemMarket removeMatingServant(final long accountId, final long objectId) {
        final ServantItemMarket servantItemMarket = this.servants.get(objectId);
        if (servantItemMarket == null || servantItemMarket.getAccountId() != accountId || !this.matingServants.remove(servantItemMarket)) {
            return null;
        }
        return this.servants.remove(objectId);
    }

    public NpcWorkerItemMarket removeNpcWorker(final long accountId, final long objectId) {
        final NpcWorkerItemMarket npcWorker = this.npcWorkers.get(objectId);
        if (npcWorker == null || npcWorker.getAccountId() != accountId || !this.npcWorkersForSale.remove(npcWorker)) {
            return null;
        }
        return this.npcWorkers.remove(objectId);
    }

    private static class Holder {
        static final AuctionGoodService INSTANCE = new AuctionGoodService();
    }
}
