package com.bdoemu.gameserver.model.manufactures.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManufactureConditionT {
    private String actionName;

    public ManufactureConditionT(final ResultSet rs) throws SQLException {
        this.actionName = rs.getString("ActionName");
    }

    public String getActionName() {
        return this.actionName;
    }
}
