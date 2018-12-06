package com.bdoemu.gameserver.model.creature.player.contribution;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.network.sendable.SMUpdateExplorePoint;
import com.bdoemu.gameserver.dataholders.ContributionEXPData;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.contribution.templates.ContributionEXPT;
import com.bdoemu.gameserver.model.misc.enums.EGamePointType;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class ExplorePoint extends JSONable {
    private final int territoryKey;
    private int maxExplorePoints;
    private int currentExplorePoints;
    private long exp;
    private ContributionEXPT template;
    private Player player;

    public ExplorePoint(final Player player, final int territoryKey) {
        this.player = player;
        this.territoryKey = territoryKey;
        this.template = ContributionEXPData.getInstance().getTemplate(territoryKey, 0);
    }

    public ExplorePoint(final Player player, final BasicDBObject dbObject) {
        this.player = player;
        this.territoryKey = dbObject.getInt("territoryKey");
        this.maxExplorePoints = dbObject.getInt("maxExplorePoints");
        this.currentExplorePoints = dbObject.getInt("currentExplorePoints");
        this.exp = dbObject.getInt("exp");
        this.template = ContributionEXPData.getInstance().getTemplate(this.territoryKey, this.maxExplorePoints);
    }

    public synchronized boolean addPoints(final int points) {
        if (this.currentExplorePoints + points < 0 || this.currentExplorePoints + points > this.maxExplorePoints) {
            return false;
        }
        this.currentExplorePoints += points;
        return true;
    }

    public synchronized void addExp(final Player player, final int exp) {
        if (exp < 0) {
            return;
        }
        final int ratedExp = (int) (exp * RateConfig.RATE_EXPLORE_EXP / 100.0f);
        this.exp += ratedExp;
        if (this.exp >= this.template.getRequireEXP()) {
            final ContributionEXPT newTemplate = ContributionEXPData.getInstance().getTemplate(this.territoryKey, this.maxExplorePoints + 1);
            if (newTemplate == null) {
                this.exp = this.template.getRequireEXP();
                return;
            }
            this.exp -= this.template.getRequireEXP();
            this.template = newTemplate;
            if (this.exp > newTemplate.getRequireEXP()) {
                this.exp = newTemplate.getRequireEXP();
            }
            ++this.currentExplorePoints;
            ++this.maxExplorePoints;
            player.getObserveController().notifyObserver(EObserveType.acquirePoint, EGamePointType.CONTRIBUTION.ordinal(), this.maxExplorePoints);
        }
        player.sendPacket(new SMUpdateExplorePoint(this));
    }

    public long getExp() {
        return this.exp;
    }

    public int getTerritoryKey() {
        return this.territoryKey;
    }

    public int getCurrentExplorePoints() {
        return this.currentExplorePoints;
    }

    public int getMaxExplorePoints() {
        return this.maxExplorePoints;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("territoryKey", this.territoryKey);
        builder.append("maxExplorePoints", this.maxExplorePoints);
        builder.append("currentExplorePoints", this.currentExplorePoints);
        builder.append("exp", this.exp);
        return builder.get();
    }
}
