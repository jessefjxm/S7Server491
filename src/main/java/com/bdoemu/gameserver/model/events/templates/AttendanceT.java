package com.bdoemu.gameserver.model.events.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceT {
    private int[][] rewards;
    private int eventId;

    public AttendanceT(final ResultSet rs) throws SQLException {
        this.rewards = new int[60][2];
        this.eventId = rs.getInt("Key");
        for (int i = 0; i < this.rewards.length; ++i) {
            final String rewardData = rs.getString("reward" + (i + 1));
            if (rewardData != null) {
                final String[] reward = rewardData.split(",");
                this.rewards[i][0] = Integer.parseInt(reward[0].trim());
                this.rewards[i][1] = Integer.parseInt(reward[1].trim());
            }
        }
    }

    public int getEventId() {
        return this.eventId;
    }

    public int[][] getRewards() {
        return this.rewards;
    }
}
