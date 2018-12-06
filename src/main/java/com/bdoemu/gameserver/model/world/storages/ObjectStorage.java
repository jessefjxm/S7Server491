package com.bdoemu.gameserver.model.world.storages;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BiConsumer;

public class ObjectStorage {
    private final EnumMap<ECharKind, CreatureStorage<? extends Creature>> types;
    private final CreatureStorage<Creature> creatureStorage;

    public ObjectStorage() {
        this.types = new EnumMap<>(ECharKind.class);
        this.creatureStorage = new CreatureStorage<>();
        this.types.put(ECharKind.Player, new CreatureStorage<>(1, 0.8f));
        this.types.put(ECharKind.Monster, new CreatureStorage<>(1, 0.9f));
        this.types.put(ECharKind.Npc, new CreatureStorage<>(1, 0.9f));
        this.types.put(ECharKind.Deadbody, new CreatureStorage<>(1, 0.9f));
        this.types.put(ECharKind.Vehicle, new CreatureStorage<>(1, 0.9f));
        this.types.put(ECharKind.Collect, new CollectionStorage(1, 0.9f));
        this.types.put(ECharKind.Household, new CreatureStorage<>(1, 0.9f));
    }

    public boolean add(final Creature object) {
        final boolean already = this.creatureStorage.put(object);
        if (!already) {
            final CreatureStorage<Creature> storage = (CreatureStorage<Creature>) this.types.get(object.getCharKind());
            if (storage == null) {
                return false;
            }
            storage.put(object);
        }
        return already;
    }

    public boolean remove(final Creature object) {
        final boolean already = this.creatureStorage.remove(object);
        if (already) {
            final CreatureStorage<Creature> storage = (CreatureStorage<Creature>) this.types.get(object.getCharKind());
            if (storage == null) {
                return true;
            }
            storage.remove(object);
        }
        return already;
    }

    public boolean contains(final Creature object) {
        return this.creatureStorage.contains(object);
    }

    public Collection<Creature> getObjects() {
        return this.creatureStorage.getValues();
    }

    public <T extends Creature> Collection<T> getObjectsByType(final ECharKind type) {
        final CreatureStorage<Creature> storage = (CreatureStorage<Creature>) this.types.get(type);
        if (storage == null) {
            return Collections.emptyList();
        }
        return (Collection<T>) storage.getValues();
    }

    public List<Creature> getObjectsCopy() {
        return this.creatureStorage.getValuesCopy();
    }

    public <T extends Creature> List<T> getObjectsCopyByType(final ECharKind type) {
        final CreatureStorage<Creature> storage = (CreatureStorage<Creature>) this.types.get(type);
        if (storage == null) {
            return Collections.emptyList();
        }
        return (List<T>) storage.getValuesCopy();
    }

    public int getSizeByType(final ECharKind type) {
        final CreatureStorage<? extends Creature> storage = this.types.get(type);
        if (storage == null) {
            return 0;
        }
        return storage.size();
    }

    public void forEachObject(final BiConsumer<Integer, Creature> consumer) {
        this.creatureStorage.forEach(consumer);
    }

    public <T extends Creature> void forEachByType(final ECharKind type, final BiConsumer<Integer, T> consumer) {
        final CreatureStorage<T> storage = (CreatureStorage<T>) this.types.get(type);
        if (storage == null) {
            return;
        }
        storage.forEach(consumer);
    }

    public Creature getObject(final int gameObjectId) {
        return this.creatureStorage.getById(gameObjectId);
    }

    public <T extends Creature> T getCreature0(final int gameObjectId, final ECharKind kind) {
        final CreatureStorage<T> storage = (CreatureStorage<T>) this.types.get(kind);
        if (storage == null) {
            return null;
        }
        return storage.getById(gameObjectId);
    }

    public <T extends Creature> T getObjectByType(final ECharKind type, final int gameObjectId) {
        final CreatureStorage<Creature> storage = (CreatureStorage<Creature>) this.types.get(type);
        if (storage == null) {
            return null;
        }
        return (T) storage.getById(gameObjectId);
    }

    public void clear() {
        this.creatureStorage.clear();
        this.types.forEach((kind, storage) -> storage.clear());
    }

    public int size() {
        return this.creatureStorage.size();
    }
}
