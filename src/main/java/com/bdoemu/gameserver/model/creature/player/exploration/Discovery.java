package com.bdoemu.gameserver.model.creature.player.exploration;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.dataholders.NodeEXPData;
import com.bdoemu.gameserver.dataholders.xml.ExploreData;
import com.bdoemu.gameserver.model.creature.player.exploration.templates.ExplorationTemplate;
import com.bdoemu.gameserver.model.creature.player.exploration.templates.NodeEXPT;
import com.bdoemu.gameserver.model.creature.player.exploration.templates.WaypointTemplate;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class Discovery extends JSONable {
    private ExplorationTemplate exploreTemplate;
    private int contribution;
    private int level;
    private int exp;

    public Discovery(final ExplorationTemplate exploreTemplate) {
        this.exploreTemplate = exploreTemplate;
        if (this.isActive()) {
            this.level = 1;
        }
    }

    public Discovery(final BasicDBObject dbObj) {
        this.exploreTemplate = ExploreData.getInstance().getTemplate(dbObj.getInt("waypointId"));
        this.level = dbObj.getInt("level");
        this.exp = dbObj.getInt("exp");
        this.contribution = dbObj.getInt("contribution");
    }

    public static Discovery newDiscovery(final int waypointKey) {
        final ExplorationTemplate exploreTemplate = ExploreData.getInstance().getTemplate(waypointKey);
        if (exploreTemplate == null) {
            return null;
        }
        return new Discovery(exploreTemplate);
    }

    public static Discovery newDiscovery(final ExplorationTemplate exploreTemplate) {
        return new Discovery(exploreTemplate);
    }

    public ExplorationTemplate getExploreTemplate() {
        return this.exploreTemplate;
    }

    public WaypointTemplate getWaypointTemplate() {
        return this.exploreTemplate.getWaypointTemplate();
    }

    public int getWaypointId() {
        return this.exploreTemplate.getExplorationId();
    }

    public int getContribution() {
        return this.contribution;
    }

    public void setContribution(final int contribution) {
        this.contribution = contribution;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public int getExp() {
        return this.exp;
    }

    public void setExp(final int exp) {
        this.exp = exp;
    }

    public synchronized boolean addExp(final int exp) {
        if (exp < 0 || this.level == NodeEXPData.getInstance().getSize() - 1) {
            return false;
        }
        final NodeEXPT nodeEXPT = this.getNodeEXPT();
        this.exp += exp;
        if (this.exp >= nodeEXPT.getRequireExp()) {
            ++this.level;
            this.exp = 0;
        }
        return true;
    }

    public NodeEXPT getNodeEXPT() {
        return NodeEXPData.getInstance().getTemplate(this.getLevel());
    }

    public int getNeedExplorePoint() {
        return this.getWaypointTemplate().getNeedExplorePoint();
    }

    public boolean isActive() {
        return this.getNeedExplorePoint() == this.contribution;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("waypointId", this.getWaypointId());
        builder.append("level", this.getLevel());
        builder.append("exp", this.getExp());
        builder.append("contribution", this.getContribution());
        return builder.get();
    }
}
