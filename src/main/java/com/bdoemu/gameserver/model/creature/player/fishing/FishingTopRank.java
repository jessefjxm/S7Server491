package com.bdoemu.gameserver.model.creature.player.fishing;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMListFishTopRankingBody;
import com.bdoemu.gameserver.databaseCollections.FishingDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.worldInstance.World;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FishingTopRank extends JSONable {
    private ConcurrentHashMap<Long, FishingTopRankBody> fishingTopRankBodies;
    private int key;

    public FishingTopRank(final int key) {
        this.fishingTopRankBodies = new ConcurrentHashMap<>();
        this.key = key;
    }

    public FishingTopRank(final BasicDBList basicDBList, final int key) {
        this.fishingTopRankBodies = new ConcurrentHashMap<>();
        this.key = key;
        for (Object aBasicDBList : basicDBList) {
            final FishingTopRankBody fishingTopRankBody = new FishingTopRankBody((BasicDBObject) aBasicDBList);
            this.fishingTopRankBodies.put(fishingTopRankBody.getAccountId(), fishingTopRankBody);
        }
    }

    public void updateTop(final Player player, final int currentFishSize) {
        FishingTopRankBody fishingTopRankBody = null;
        synchronized (this.fishingTopRankBodies) {
            if (this.fishingTopRankBodies.containsKey(player.getAccountId())) {
                final FishingTopRankBody ownerFishingTopRankBody = this.fishingTopRankBodies.get(player.getAccountId());
                if (currentFishSize > ownerFishingTopRankBody.getMaxFishSize()) {
                    fishingTopRankBody = ownerFishingTopRankBody;
                    fishingTopRankBody.update(player, currentFishSize);
                }
            } else if (this.fishingTopRankBodies.size() < 10) {
                fishingTopRankBody = new FishingTopRankBody(player, currentFishSize, this.key);
                this.fishingTopRankBodies.put(player.getAccountId(), fishingTopRankBody);
            } else {
                final FishingTopRankBody lowFishingTopRankBody = this.getLowFishingTopRankBody();
                if (currentFishSize > lowFishingTopRankBody.getMaxFishSize()) {
                    fishingTopRankBody = lowFishingTopRankBody;
                    fishingTopRankBody.update(player, currentFishSize);
                }
            }
        }
        if (fishingTopRankBody != null) {
            FishingDBCollection.updateFishingRanking(this);
            World.getInstance().broadcastWorldPacket(new SMListFishTopRankingBody(Collections.singletonList(fishingTopRankBody), this.key, EPacketTaskType.Update));
        }
    }

    public Collection<FishingTopRankBody> getFishingTopRankBodies() {
        return new ArrayList<FishingTopRankBody>(this.fishingTopRankBodies.values());
    }

    public FishingTopRankBody getLowFishingTopRankBody() {
        final Optional<FishingTopRankBody> fishingTopRank = this.fishingTopRankBodies.values().stream().min((o1, o2) -> Integer.compare(o1.getMaxFishSize(), o2.getMaxFishSize()));
        return fishingTopRank.orElse(null);
    }

    public FishingTopRankBody getMaxFishingTopRankBody() {
        final Optional<FishingTopRankBody> fishingTopRank = this.fishingTopRankBodies.values().stream().max((o1, o2) -> Integer.compare(o1.getMaxFishSize(), o2.getMaxFishSize()));
        return fishingTopRank.orElse(null);
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList basicDBList = new BasicDBList();
        for (final FishingTopRankBody fishingTopRankBody : this.fishingTopRankBodies.values()) {
            basicDBList.add((Object) fishingTopRankBody.toDBObject());
        }
        builder.append(Integer.toString(this.key), (Object) basicDBList);
        return builder.get();
    }
}
