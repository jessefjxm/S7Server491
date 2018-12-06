// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.theme.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeTemplate {
    private int themeId;
    private int parentId;
    private int increaseWP;
    private int increaseWP2;
    private int needCount;
    private int needCount2;
    private Integer bookCase;

    public ThemeTemplate(final ResultSet rs) throws SQLException {
        this.themeId = rs.getInt("Theme");
        this.parentId = rs.getInt("Parent");
        this.increaseWP = rs.getInt("IncreaseWP");
        this.increaseWP2 = rs.getInt("increaseWP2");
        this.needCount = rs.getInt("NeedCount");
        this.needCount2 = rs.getInt("NeedCount2");
        if (rs.getString("BookCase") != null) {
            this.bookCase = rs.getInt("BookCase");
        }
    }

    public Integer getBookCase() {
        return this.bookCase;
    }

    public int getThemeId() {
        return this.themeId;
    }

    public int getParentId() {
        return this.parentId;
    }

    public int getNeedCount2() {
        return this.needCount2;
    }

    public int getNeedCount() {
        return this.needCount;
    }

    public int getIncreaseWP2() {
        return this.increaseWP2;
    }

    public int getIncreaseWP() {
        return this.increaseWP;
    }
}
