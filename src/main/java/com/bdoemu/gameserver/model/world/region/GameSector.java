package com.bdoemu.gameserver.model.world.region;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.world.storages.ObjectStorage;
import com.bdoemu.gameserver.worldInstance.World;
import com.bdoemu.gameserver.worldInstance.WorldMap;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

public class GameSector {
    private final int x;
    private final int y;
    private final int subSectorX;
    private final int subSectorY;
    private ObjectStorage storage;

    public GameSector(final int x, final int y, final int subSectorX, final int subSectorY) {
        this.x = x;
        this.y = y;
        this.subSectorX = subSectorX;
        this.subSectorY = subSectorY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSubSectorX() {
        return this.subSectorX;
    }

    public int getSubSectorY() {
        return this.subSectorY;
    }

    private ObjectStorage getStorage() {
        return (this.storage == null) ? (this.storage = new ObjectStorage()) : this.storage;
    }

    public void add(final Creature object) {
        this.getStorage().add(object);
    }

    public boolean remove(final Creature object) {
        return this.getStorage().remove(object);
    }

    public boolean contains(final Creature object) {
        return this.getStorage().contains(object);
    }

    public void forEachObject(final BiConsumer<Integer, Creature> consumer) {
        for (int xx = this.x - WorldMap.MAP_SUBSECTOR_SIZE; xx <= this.x + WorldMap.MAP_SUBSECTOR_SIZE; xx += WorldMap.MAP_SUBSECTOR_SIZE) {
            for (int yy = this.y - WorldMap.MAP_SUBSECTOR_SIZE; yy <= this.y + WorldMap.MAP_SUBSECTOR_SIZE; yy += WorldMap.MAP_SUBSECTOR_SIZE) {
                final GameSector region = World.getInstance().getWorldMap().getGameSectorByCoords(xx, yy);
                if (region != null) {
                    region.forEachObject0(consumer);
                }
            }
        }
    }

    private void forEachObject0(final BiConsumer<Integer, Creature> consumer) {
        this.getStorage().forEachObject(consumer);
    }

    public <T extends Creature> void forEachObject(final ECharKind type, final BiConsumer<Integer, T> consumer) {
        for (int xx = this.x - WorldMap.MAP_SUBSECTOR_SIZE; xx <= this.x + WorldMap.MAP_SUBSECTOR_SIZE; xx += WorldMap.MAP_SUBSECTOR_SIZE) {
            for (int yy = this.y - WorldMap.MAP_SUBSECTOR_SIZE; yy <= this.y + WorldMap.MAP_SUBSECTOR_SIZE; yy += WorldMap.MAP_SUBSECTOR_SIZE) {
                final GameSector region = World.getInstance().getWorldMap().getGameSectorByCoords(xx, yy);
                if (region != null) {
                    region.forEachObject0(type, consumer);
                }
            }
        }
    }

    private <T extends Creature> void forEachObject0(final ECharKind type, final BiConsumer<Integer, T> consumer) {
        this.getStorage().forEachByType(type, consumer);
    }

    public Creature getObject(final int gameObjectId) {
        for (int xx = this.x - WorldMap.MAP_SUBSECTOR_SIZE; xx <= this.x + WorldMap.MAP_SUBSECTOR_SIZE; xx += WorldMap.MAP_SUBSECTOR_SIZE) {
            for (int yy = this.y - WorldMap.MAP_SUBSECTOR_SIZE; yy <= this.y + WorldMap.MAP_SUBSECTOR_SIZE; yy += WorldMap.MAP_SUBSECTOR_SIZE) {
                final GameSector region = World.getInstance().getWorldMap().getGameSectorByCoords(xx, yy);
                if (region != null) {
                    final Creature object = region.getObject0(gameObjectId);
                    if (object != null) {
                        return object;
                    }
                }
            }
        }
        return null;
    }

    public <T extends Creature> T getCreature0(final int gameObjectId, final ECharKind kind) {
        return this.getStorage().getCreature0(gameObjectId, kind);
    }

    public Creature getObject0(final int gameObjectId) {
        return this.getStorage().getObject(gameObjectId);
    }

    public <T extends Creature> T getObject(final ECharKind type, final int gameObjectId) {
        for (int xx = this.x - WorldMap.MAP_SUBSECTOR_SIZE; xx <= this.x + WorldMap.MAP_SUBSECTOR_SIZE; xx += WorldMap.MAP_SUBSECTOR_SIZE) {
            for (int yy = this.y - WorldMap.MAP_SUBSECTOR_SIZE; yy <= this.y + WorldMap.MAP_SUBSECTOR_SIZE; yy += WorldMap.MAP_SUBSECTOR_SIZE) {
                final GameSector region = World.getInstance().getWorldMap().getGameSectorByCoords(xx, yy);
                if (region != null) {
                    final T object = region.getObject0(type, gameObjectId);
                    if (object != null) {
                        return object;
                    }
                }
            }
        }
        return null;
    }

    private <T extends Creature> T getObject0(final ECharKind type, final int gameObjectId) {
        return this.getStorage().getObjectByType(type, gameObjectId);
    }

    public List<Creature> getObjectsCopy() {
        return this.getStorage().getObjectsCopy();
    }

    public <T extends Creature> List<T> getObjectsCopyByType(final ECharKind type) {
        return this.getStorage().getObjectsCopyByType(type);
    }

    public Collection<Creature> getObjects() {
        return this.getStorage().getObjects();
    }

    public <T extends Creature> Collection<T> getObjectsByType(final ECharKind type) {
        return this.getStorage().getObjectsByType(type);
    }

    public boolean isActive() {
        return this.getStorage().getSizeByType(ECharKind.Player) > 0;
    }

    public boolean hasActiveNeighbours() {
        for (int xx = this.x - WorldMap.MAP_SUBSECTOR_SIZE; xx <= this.x + WorldMap.MAP_SUBSECTOR_SIZE; xx += WorldMap.MAP_SUBSECTOR_SIZE) {
            for (int yy = this.y - WorldMap.MAP_SUBSECTOR_SIZE; yy <= this.y + WorldMap.MAP_SUBSECTOR_SIZE; yy += WorldMap.MAP_SUBSECTOR_SIZE) {
                final GameSector region = World.getInstance().getWorldMap().getGameSectorByCoords(xx, yy);
                if (region != null) {
                    if (region.isActive()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int size() {
        return this.getStorage().size();
    }

    @Override
    public String toString() {
        return "Game region: " + this.getX() + " " + this.getY() + ", objects: " + this.size();
    }
}
