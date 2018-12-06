package com.bdoemu.gameserver.model.knowlist;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.world.region.GameSector;

public final class KnowList {
    public static boolean knowObject(final Creature object, final Creature spawnObject) {
        final GameSector gameSector = object.getLocation().getGameSector();
        return gameSector != null && gameSector.getObject(spawnObject.getCharKind(), spawnObject.getGameObjectId()) != null;
    }

    public static Creature getObject(final Creature object, final int gameObjectId) {
        final GameSector gameSector = object.getLocation().getGameSector();
        return (gameSector != null) ? gameSector.getObject(gameObjectId) : null;
    }

    public static <T extends Creature> T getObject(final Creature object, final ECharKind type, final int gameObjectId) {
        final GameSector gameSector = object.getLocation().getGameSector();
        return (T) ((gameSector != null) ? gameSector.getObject(type, gameObjectId) : null);
    }
}
