package com.bdoemu.gameserver.model.world.templates;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegionDropT {
    private Color color;
    private int dropId;

    public RegionDropT(final ResultSet rs) throws SQLException {
        this.color = new Color(rs.getInt("R"), rs.getInt("G"), rs.getInt("B"));
        this.dropId = rs.getInt("DropID");
    }

    public Color getColor() {
        return this.color;
    }

    public int getDropId() {
        return this.dropId;
    }
}
