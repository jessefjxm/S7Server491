package com.bdoemu.gameserver.model.creature.player.exploration.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantZoneWorkingT {
    private int maxCapacityLevel;
    private PlantExchangeGroupT template;

    public PlantZoneWorkingT(final ResultSet rs, final PlantExchangeGroupT template) throws SQLException {
        this.template = template;
        this.maxCapacityLevel = rs.getInt("MaxCapacityLevel");
    }

    public int getMaxCapacityLevel() {
        return this.maxCapacityLevel;
    }

    public PlantExchangeGroupT getTemplate() {
        return this.template;
    }
}
