package com.bdoemu.gameserver.model.creature.templates;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteriorRelationPointT {
    private int onDresser;
    private int onBedSideCabinet;
    private int onWardrobe;
    private int onDiningTable;
    private int onTable;
    private int onChair;
    private int onSofa;
    private int onBed;
    private int onBookCase;
    private int isEmptyInFront;
    private int isEmptyInBack;
    private int isEmptyInLeft;
    private int isEmptyInRight;
    private int matchingCharacterKey;

    public InteriorRelationPointT(final ResultSet rs) throws SQLException {
        this.onDresser = rs.getInt("OnDresser");
        this.onBedSideCabinet = rs.getInt("OnBedSideCabinet");
        this.onWardrobe = rs.getInt("OnWardrobe");
        this.onDiningTable = rs.getInt("OnDiningTable");
        this.onTable = rs.getInt("OnTable");
        this.onChair = rs.getInt("OnChair");
        this.onSofa = rs.getInt("OnSofa");
        this.onBed = rs.getInt("OnBed");
        this.onBookCase = rs.getInt("OnBookCase");
        this.isEmptyInFront = rs.getInt("IsEmptyInFront");
        this.isEmptyInBack = rs.getInt("IsEmptyInBack");
        this.isEmptyInLeft = rs.getInt("IsEmptyInLeft");
        this.isEmptyInRight = rs.getInt("IsEmptyInRight");
        this.matchingCharacterKey = rs.getInt("MatchingCharacterKey");
    }

    public int getOnDresser() {
        return this.onDresser;
    }

    public int getOnBedSideCabinet() {
        return this.onBedSideCabinet;
    }

    public int getOnWardrobe() {
        return this.onWardrobe;
    }

    public int getOnDiningTable() {
        return this.onDiningTable;
    }

    public int getOnTable() {
        return this.onTable;
    }

    public int getOnChair() {
        return this.onChair;
    }

    public int getOnSofa() {
        return this.onSofa;
    }

    public int getOnBed() {
        return this.onBed;
    }

    public int getOnBookCase() {
        return this.onBookCase;
    }

    public int getIsEmptyInFront() {
        return this.isEmptyInFront;
    }

    public int getIsEmptyInBack() {
        return this.isEmptyInBack;
    }

    public int getIsEmptyInLeft() {
        return this.isEmptyInLeft;
    }

    public int getIsEmptyInRight() {
        return this.isEmptyInRight;
    }

    public int getMatchingCharacterKey() {
        return this.matchingCharacterKey;
    }
}
