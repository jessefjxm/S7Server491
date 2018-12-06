package com.bdoemu.gameserver.model.creature.player.cooltimes;

import com.bdoemu.gameserver.service.GameTimeService;

public class CoolTime {
    private int reuseGroup;
    private int skillId;
    private int type;
    private long endTime;

    public CoolTime(final int coolTime, final int reuseGroup, final int skillId, final int type) {
        this.reuseGroup = reuseGroup;
        this.skillId = skillId;
        this.type = type;
        this.endTime = GameTimeService.getServerTimeInMillis() + coolTime;
    }

    public int getReuseGroup() {
        return this.reuseGroup;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public int getSkillId() {
        return this.skillId;
    }

    public int getType() {
        return this.type;
    }

    public long getRemainigTime() {
        final long result = this.endTime - GameTimeService.getServerTimeInMillis();
        return (result < 0L) ? 0L : result;
    }
}
