package com.bdoemu.gameserver.model.world.region.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegionGroupTemplate {
    private int regionGroupKey;
    private boolean corpProductivity;
    private boolean fishingProductivity;
    private boolean loyalty;

    public RegionGroupTemplate(final ResultSet rs) throws SQLException {
        this.regionGroupKey = rs.getInt("RegionGroupKey");
        this.corpProductivity = rs.getBoolean("CorpProductivity");
        this.fishingProductivity = rs.getBoolean("FishingProductivity");
        this.loyalty = rs.getBoolean("Loyalty");
    }

    public int getRegionGroupKey() {
        return this.regionGroupKey;
    }

    public boolean isCorpProductivity() {
        return this.corpProductivity;
    }

    public boolean isFishingProductivity() {
        return this.fishingProductivity;
    }

    public boolean isLoyalty() {
        return this.loyalty;
    }
}
