package com.bdoemu.gameserver.model.creature.player.titles.templates;

import com.bdoemu.gameserver.model.creature.player.titles.enums.ETitleType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TitleTemplate {
    private int titleId;
    private ETitleType titleType;
    private int parameter1;
    private int parameter2;
    private int contentGroupKey;

    public TitleTemplate(final ResultSet rs) throws SQLException {
        this.titleId = rs.getInt("Key");
        this.titleType = ETitleType.valueOf(rs.getByte("Type"));
        this.parameter1 = rs.getInt("Parameter1");
        this.parameter2 = rs.getInt("Parameter2");
        this.contentGroupKey = rs.getInt("ContentsGroupKey");
    }

    public ETitleType getTitleType() {
        return this.titleType;
    }

    public int getTitleId() {
        return this.titleId;
    }

    public int getParameter1() {
        return this.parameter1;
    }

    public int getParameter2() {
        return this.parameter2;
    }

    public int getContentGroupKey() {
        return this.contentGroupKey;
    }
}
