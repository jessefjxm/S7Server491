package com.bdoemu.gameserver.model.creature.player.contribution;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.ContributionEXPData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.Collection;
import java.util.HashMap;

public class ExplorePointHandler extends JSONable {
    private HashMap<Integer, ExplorePoint> territories;
    private Player player;

    public ExplorePointHandler(final Player player) {
        this.territories = new HashMap<Integer, ExplorePoint>();
        this.player = player;
        this.territories.put(0, new ExplorePoint(player, 0));
    }

    public ExplorePointHandler(final Player player, final BasicDBObject dbObject) {
        this(player);
        final BasicDBList exploreListDB = (BasicDBList) dbObject.get("explorePointList");
        for (int i = 0; i < exploreListDB.size(); ++i) {
            final BasicDBObject explorePointDB = (BasicDBObject) exploreListDB.get(i);
            final ExplorePoint explorePoint = new ExplorePoint(player, explorePointDB);
            this.territories.put(explorePoint.getTerritoryKey(), explorePoint);
        }
    }

    public ExplorePoint ExplorePoint(final int territoryKey) {
        return this.territories.get(territoryKey);
    }

    public ExplorePoint getMainExplorePoint() {
        return this.territories.get(0);
    }

    public void addExp(final int territoryKey, final int exp) {
        if (ContributionEXPData.getInstance().getTemplates(territoryKey) == null) {
            return;
        }
        this.territories.putIfAbsent(territoryKey, new ExplorePoint(this.player, territoryKey));
        final ExplorePoint ep = this.territories.get(territoryKey);
        ep.addExp(this.player, exp);
    }

    public Collection<ExplorePoint> getTerritories() {
        return this.territories.values();
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        final BasicDBList explorePointDB = new BasicDBList();
        for (final ExplorePoint explorePoint : this.territories.values()) {
            explorePointDB.add(explorePoint.toDBObject());
        }
        builder.append("explorePointList", explorePointDB);
        return builder.get();
    }
}
