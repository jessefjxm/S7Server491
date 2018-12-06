package com.bdoemu.gameserver.model.creature.observers;

import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;

public class Observer {
    private final EObserveType observeType;
    private Object key;

    public Observer(final EObserveType observeType) {
        this.observeType = observeType;
    }

    public Observer(final EObserveType observeType, final Object key) {
        this.key = key;
        this.observeType = observeType;
    }

    public void notify(final EObserveType type, final Object... params) {
    }

    public Object getKey() {
        return this.key;
    }

    public EObserveType getObserveType() {
        return this.observeType;
    }
}
