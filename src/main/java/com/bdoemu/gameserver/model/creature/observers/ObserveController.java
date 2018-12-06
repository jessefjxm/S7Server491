package com.bdoemu.gameserver.model.creature.observers;

import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.observers.map.ObserveMap;

public class ObserveController {
    private final ObserveMap[] observeMaps;

    public ObserveController() {
        this.observeMaps = new ObserveMap[EObserveType.values().length];
        for (final EObserveType type : EObserveType.values()) {
            this.observeMaps[type.ordinal()] = type.newMapObserveInstance();
        }
    }

    public void putObserver(final Observer observer) {
        this.observeMaps[observer.getObserveType().ordinal()].put(observer);
    }

    public void removeObserver(final Observer observer) {
        this.observeMaps[observer.getObserveType().ordinal()].remove(observer);
    }

    public void notifyObserver(final EObserveType observeType, final Object... objects) {
        this.observeMaps[observeType.ordinal()].notify(observeType, objects);
    }
}
