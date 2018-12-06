package com.bdoemu.gameserver.model.account.boardgame;

import com.bdoemu.commons.database.mongo.JSONable;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class BoardGameData extends JSONable {
    private int startPosition;
    private int nowPosition;
    private int clearBlackMarbleCount;
    private int todayCount;
    private int setFinishCount;
    private int overTime;
    private int overtimeRemain;
    private int todayRemainCount;
    private long lastDate;

    public BoardGameData() {
    }

    public BoardGameData(final BasicDBObject dbObject) {
        this.lastDate = dbObject.getLong("lastDate");
        this.nowPosition = dbObject.getInt("nowPosition");
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("lastDate", this.lastDate);
        builder.append("nowPosition", this.nowPosition);
        return builder.get();
    }

    public int getStartPosition() {
        return this.startPosition;
    }

    public void setStartPosition(final int startPosition) {
        this.startPosition = startPosition;
    }

    public int getNowPosition() {
        return this.nowPosition;
    }

    public void setNowPosition(final int nowPosition) {
        this.nowPosition = nowPosition;
    }

    public int getClearBlackMarbleCount() {
        return this.clearBlackMarbleCount;
    }

    public void setClearBlackMarbleCount(final int clearBlackMarbleCount) {
        this.clearBlackMarbleCount = clearBlackMarbleCount;
    }

    public int getTodayCount() {
        return this.todayCount;
    }

    public void setTodayCount(final int todayCount) {
        this.todayCount = todayCount;
    }

    public int getSetFinishCount() {
        return this.setFinishCount;
    }

    public void setSetFinishCount(final int setFinishCount) {
        this.setFinishCount = setFinishCount;
    }

    public int getOverTime() {
        return this.overTime;
    }

    public void setOverTime(final int overTime) {
        this.overTime = overTime;
    }

    public int getOvertimeRemain() {
        return this.overtimeRemain;
    }

    public void setOvertimeRemain(final int overtimeRemain) {
        this.overtimeRemain = overtimeRemain;
    }

    public int getTodayRemainCount() {
        return this.todayRemainCount;
    }

    public void setTodayRemainCount(final int todayRemainCount) {
        this.todayRemainCount = todayRemainCount;
    }

    public long getLastDate() {
        return this.lastDate;
    }

    public void setLastDate(final long lastDate) {
        this.lastDate = lastDate;
    }

    public String toString() {
        return "BoardGameData(startPosition=" + this.getStartPosition() + ", nowPosition=" + this.getNowPosition() + ", clearBlackMarbleCount=" + this.getClearBlackMarbleCount() + ", todayCount=" + this.getTodayCount() + ", setFinishCount=" + this.getSetFinishCount() + ", overTime=" + this.getOverTime() + ", overtimeRemain=" + this.getOvertimeRemain() + ", todayRemainCount=" + this.getTodayRemainCount() + ", lastDate=" + this.getLastDate() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BoardGameData)) {
            return false;
        }
        final BoardGameData other = (BoardGameData) o;
        return other.canEqual(this) && this.getStartPosition() == other.getStartPosition() && this.getNowPosition() == other.getNowPosition() && this.getClearBlackMarbleCount() == other.getClearBlackMarbleCount() && this.getTodayCount() == other.getTodayCount() && this.getSetFinishCount() == other.getSetFinishCount() && this.getOverTime() == other.getOverTime() && this.getOvertimeRemain() == other.getOvertimeRemain() && this.getTodayRemainCount() == other.getTodayRemainCount() && this.getLastDate() == other.getLastDate();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BoardGameData;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getStartPosition();
        result = result * 59 + this.getNowPosition();
        result = result * 59 + this.getClearBlackMarbleCount();
        result = result * 59 + this.getTodayCount();
        result = result * 59 + this.getSetFinishCount();
        result = result * 59 + this.getOverTime();
        result = result * 59 + this.getOvertimeRemain();
        result = result * 59 + this.getTodayRemainCount();
        final long $lastDate = this.getLastDate();
        result = result * 59 + (int) ($lastDate >>> 32 ^ $lastDate);
        return result;
    }
}
