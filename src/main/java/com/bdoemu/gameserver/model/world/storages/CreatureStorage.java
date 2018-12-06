package com.bdoemu.gameserver.model.world.storages;

import com.bdoemu.gameserver.model.creature.Creature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class CreatureStorage<T extends Creature> {
    protected final Map<Integer, T> collection;

    public CreatureStorage() {
        this(50, 0.85f);
    }

    public CreatureStorage(final int capacity, final float loadFactor) {
        this.collection = new ConcurrentHashMap<Integer, T>(capacity, loadFactor);
    }

    public boolean put(final T object) {
        return this.collection.put(object.getGameObjectId(), object) != null;
    }

    public boolean remove(final T object) {
        return this.collection.remove(object.getGameObjectId()) != null;
    }

    public boolean contains(final T object) {
        return this.collection.containsKey(object.getGameObjectId());
    }

    public Collection<T> getValues() {
        return this.collection.values();
    }

    public List<T> getValuesCopy() {
        return new ArrayList<T>((Collection<? extends T>) this.collection.values());
    }

    public T getById(final int gameObjectId) {
        return this.collection.get(gameObjectId);
    }

    public int size() {
        return this.collection.size();
    }

    public void clear() {
        this.collection.clear();
    }

    public void forEach(final BiConsumer<Integer, T> consumer) {
        this.collection.forEach(consumer);
    }
}
