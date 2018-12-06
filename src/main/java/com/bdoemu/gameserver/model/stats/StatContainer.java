package com.bdoemu.gameserver.model.stats;

import com.bdoemu.gameserver.model.stats.enums.StatEnum;

import java.util.HashMap;
import java.util.Map;

public class StatContainer {
    private Map<StatEnum, Stat> container;

    public StatContainer() {
        this.container = new HashMap<>();
    }

    public void addStat(final StatEnum type, final Stat stat) {
        this.container.put(type, stat);
    }

    public Stat getStat(final StatEnum type) {
        return this.container.get(type);
    }
}