package com.bdoemu.gameserver.model.creature.observers.map;

import com.bdoemu.gameserver.model.creature.observers.Observer;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;

public interface ObserveMap {
    void put(final Observer p0);

    void remove(final Observer p0);

    void notify(final EObserveType p0, final Object... p1);
}
