package com.bdoemu.gameserver.model.creature.player.fishing.services;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMListFishTopRankingHead;
import com.bdoemu.core.network.sendable.SMUpdateFloatFishing;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.databaseCollections.FishingDBCollection;
import com.bdoemu.gameserver.dataholders.EncyclopediaData;
import com.bdoemu.gameserver.dataholders.FloatFishingData;
import com.bdoemu.gameserver.model.creature.FloatFish;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.encyclopedia.templates.EncyclopediaT;
import com.bdoemu.gameserver.model.creature.player.fishing.FishingTopRank;
import com.bdoemu.gameserver.model.creature.player.fishing.FishingTopRankBody;
import com.bdoemu.gameserver.model.fishing.templates.FloatFishingT;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.utils.MathUtils;
import com.bdoemu.gameserver.worldInstance.World;
import com.mongodb.BasicDBList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@StartupComponent(value = "Service")
public class FishingService {
    private static class Holder {
        static final FishingService INSTANCE = new FishingService();
    }
    private static final Logger log = LoggerFactory.getLogger(FishingService.class);
    private final ConcurrentHashMap<Integer, FishingTopRank> fishingTopRankings = new ConcurrentHashMap<>();
    private final Map<Integer, FloatFish> floatFishes = new ConcurrentHashMap<>();
    private ScheduledFuture<?> updateFloatFishesTask = null;

    private FishingService() {
        FishingDBCollection.init("FishingRanking");
        for (EncyclopediaT template : EncyclopediaData.getInstance().getTemplates().values()) {
            BasicDBList basicDBList = FishingDBCollection.loadFishingTopRanking(template.getKey());
            FishingTopRank fishingTopRank = basicDBList != null ? new FishingTopRank(basicDBList, template.getKey()) : new FishingTopRank(template.getKey());
            this.fishingTopRankings.put(template.getKey(), fishingTopRank);
        }
        log.info("Loaded {} fish rank's.", this.fishingTopRankings.values().size());
        Collection<FloatFishingT> templates = FloatFishingData.getInstance().getTemplates();
        for (FloatFishingT template : templates) {
            double x = Rnd.get(template.getStartPositionX(), template.getEndPositionX());
            double y = Rnd.get(template.getStartPositionY(), template.getEndPositionY());
            FloatFish floatFish = new FloatFish(template, new Location(x, y, template.getStartPositionZ()));
            floatFish.setDespawnTime(GameTimeService.getServerTimeInMillis() + (long) template.getPointRemainTime());
            this.floatFishes.put(template.getFishingGroupKey(), floatFish);
        }
        this.updateFloatFishesTask = ThreadPool.getInstance().scheduleGeneralAtFixedRate(() -> {
                    ArrayList<FloatFish> updateList = new ArrayList<>();
                    this.floatFishes.entrySet().stream().filter(entry -> entry.getValue().getDespawnTime() < GameTimeService.getServerTimeInMillis()).forEach(entry -> {
                                ((FloatFish) entry.getValue()).setDespawnTime(GameTimeService.getServerTimeInMillis() + (long) ((FloatFish) entry.getValue()).getTemplate().getPointRemainTime());
                                updateList.add(entry.getValue());
                            }
                    );
                    if (!updateList.isEmpty()) {
                        World.getInstance().broadcastWorldPacket(new SMUpdateFloatFishing(updateList, EPacketTaskType.Update));
                    }
                }
                , 5, 5, TimeUnit.MINUTES);
        log.info("Spawned {} float fishes.", this.floatFishes.size());
    }

    public static FishingService getInstance() {
        return Holder.INSTANCE;
    }

    public FishingTopRank getTopRank(int key) {
        return this.fishingTopRankings.get(key);
    }

    private List<FishingTopRankBody> getFishingTopRankHead() {
        ArrayList<FishingTopRankBody> list = new ArrayList<>();
        for (FishingTopRank fishingTopRank : this.fishingTopRankings.values()) {
            FishingTopRankBody headBody = fishingTopRank.getMaxFishingTopRankBody();
            if (headBody == null) continue;
            list.add(headBody);
        }
        return list;
    }

    public void updateToTop(Player player, int topKey, int maxFishSize) {
        this.fishingTopRankings.get(topKey).updateTop(player, maxFishSize);
    }

    public FloatFish getFloatFish(Player player) {
        for (FloatFish fish : this.floatFishes.values()) {
            if (GameTimeService.getServerTimeInMillis() > fish.getDespawnTime() || !MathUtils.isInRange(player.getLocation(), fish.getLocation(), fish.getTemplate().getPointSize()))
                continue;
            return fish;
        }
        return null;
    }

    public void onFloatFishCatch(FloatFish fish) {
        FloatFishingT template = fish.getTemplate();
        double x = Rnd.get(template.getStartPositionX(), template.getEndPositionX());
        double y = Rnd.get(template.getStartPositionY(), template.getEndPositionY());
        fish.setLocation(new Location(x, y, template.getStartPositionZ()));
        fish.setDespawnTime(GameTimeService.getServerTimeInMillis() + (long) template.getPointRemainTime());
        this.floatFishes.put(fish.getTemplate().getFishingGroupKey(), fish);
        World.getInstance().broadcastWorldPacket(new SMUpdateFloatFishing(Collections.singletonList(fish), EPacketTaskType.Update));
    }

    public void onLogin(Player player) {
        ListSplitter<FishingTopRankBody> listSplitter = new ListSplitter<>(this.getFishingTopRankHead(), 98);
        while (listSplitter.hasNext()) {
            player.sendPacket(new SMListFishTopRankingHead(listSplitter.getNext(), listSplitter.isFirst() ? EPacketTaskType.Add : EPacketTaskType.Update));
        }
        player.sendPacket(new SMUpdateFloatFishing(this.floatFishes.values(), EPacketTaskType.Add));
    }
}