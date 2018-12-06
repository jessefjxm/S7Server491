package com.bdoemu.gameserver.model.minigame.template;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MiniGameT {
    private int gameId;
    private int successWp;
    private int failWp;
    private int dropGroup;
    private int checkVehicle;

    public MiniGameT(final ResultSet rs) throws SQLException {
        this.gameId = rs.getInt("MiniGameKey");
        this.successWp = rs.getInt("SuccessWp");
        this.failWp = rs.getInt("FailWp");
        this.dropGroup = rs.getInt("DropGroup");
        this.checkVehicle = rs.getInt("CheckVehicle");
    }

    public int getSuccessWp() {
        return this.successWp;
    }

    public int getGameId() {
        return this.gameId;
    }

    public int getFailWp() {
        return this.failWp;
    }

    public int getDropGroup() {
        return this.dropGroup;
    }

    public int getCheckVehicle() {
        return this.checkVehicle;
    }
}
