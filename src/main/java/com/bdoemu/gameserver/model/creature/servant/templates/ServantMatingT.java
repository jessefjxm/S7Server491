package com.bdoemu.gameserver.model.creature.servant.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServantMatingT {
    private int index;
    private int averageGrade;
    private int resultMinGrade;
    private int resultMaxGrade;

    public ServantMatingT(final ResultSet rs) throws SQLException {
        this.index = rs.getInt("Index");
        this.averageGrade = rs.getInt("AverageGrade");
        this.resultMinGrade = rs.getInt("ResultMinGrade");
        this.resultMaxGrade = rs.getInt("ResultMaxGrade");
    }

    public int getIndex() {
        return this.index;
    }

    public int getAverageGrade() {
        return this.averageGrade;
    }

    public int getResultMaxGrade() {
        return this.resultMaxGrade;
    }

    public int getResultMinGrade() {
        return this.resultMinGrade;
    }
}
