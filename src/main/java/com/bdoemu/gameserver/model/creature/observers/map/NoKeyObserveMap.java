package com.bdoemu.gameserver.model.creature.observers.map;

import com.bdoemu.gameserver.model.creature.observers.Observer;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;

import java.util.concurrent.ConcurrentLinkedQueue;

public class NoKeyObserveMap implements ObserveMap {
    private final ConcurrentLinkedQueue<Observer> observers;

    public NoKeyObserveMap() {
        this.observers = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void put(final Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void remove(final Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notify(final EObserveType observeType, final Object... params) {
        for (final Observer observer : this.observers) {
            observer.notify(observeType, params);
        }
    }
}
