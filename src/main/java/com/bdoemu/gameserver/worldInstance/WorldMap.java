package com.bdoemu.gameserver.worldInstance;

import com.bdoemu.core.configs.RegionConfig;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.model.world.region.GameSector;
import com.bdoemu.gameserver.model.world.region.Region;
import com.bdoemu.gameserver.model.world.storages.ObjectStorage;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.service.RegionService;
import com.bdoemu.gameserver.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class WorldMap {
    public static final int MAP_SUBSECTOR_SIZE;
    public static final int SEE_RANGE;
    public static final int EXIT_RANGE;
    public static final int MAP_MIN_X;
    public static final int MAP_MIN_Y;
    public static final int MAP_MAX_X;
    public static final int MAP_MAX_Y;
    public static final int MAP_MIN_SECTOR_X;
    public static final int MAP_MIN_SECTOR_Y;
    public static final int MAP_MAX_SECTOR_X;
    public static final int MAP_MAX_SECTOR_Y;
    private static final Logger log;
    private static final ECharKind[] KIND_PLAYER;
    private static final ECharKind[] KIND_ALL;

    static {
        log = LoggerFactory.getLogger(WorldMap.class);
        MAP_SUBSECTOR_SIZE = RegionConfig.MAP_SUBSECTOR_SIZE;
        SEE_RANGE = RegionConfig.SEE_RANGE;
        EXIT_RANGE = RegionConfig.EXIT_RANGE;
        MAP_MIN_X = RegionConfig.MAP_SECTOR_START_X * RegionConfig.MAP_SECTOR_SIZE;
        MAP_MIN_Y = RegionConfig.MAP_SECTOR_START_Y * RegionConfig.MAP_SECTOR_SIZE;
        MAP_MAX_X = RegionConfig.MAP_SECTOR_END_X * RegionConfig.MAP_SECTOR_SIZE + RegionConfig.MAP_SECTOR_SIZE;
        MAP_MAX_Y = RegionConfig.MAP_SECTOR_END_Y * RegionConfig.MAP_SECTOR_SIZE + RegionConfig.MAP_SECTOR_SIZE;
        MAP_MIN_SECTOR_X = WorldMap.MAP_MIN_X / RegionConfig.MAP_SUBSECTOR_SIZE;
        MAP_MIN_SECTOR_Y = WorldMap.MAP_MIN_Y / RegionConfig.MAP_SUBSECTOR_SIZE;
        MAP_MAX_SECTOR_X = WorldMap.MAP_MAX_X / RegionConfig.MAP_SUBSECTOR_SIZE;
        MAP_MAX_SECTOR_Y = WorldMap.MAP_MAX_Y / RegionConfig.MAP_SUBSECTOR_SIZE;
        KIND_PLAYER = new ECharKind[]{ECharKind.Player};
        KIND_ALL = new ECharKind[]{ECharKind.Npc, ECharKind.Player, ECharKind.Monster, ECharKind.Deadbody, ECharKind.Vehicle, ECharKind.Collect, ECharKind.Household};
    }

    private final ObjectStorage objects;
    private final GameSector[][] gameSectors;
    private int SUB_SECTOR_X_OFFSET;
    private int SUB_SECTOR_Y_OFFSET;

    public WorldMap() {
        this.SUB_SECTOR_X_OFFSET = RegionConfig.SUB_SECTOR_X_OFFSET;
        this.SUB_SECTOR_Y_OFFSET = RegionConfig.SUB_SECTOR_Y_OFFSET;
        this.objects = new ObjectStorage();
        this.gameSectors = new GameSector[WorldMap.MAP_MAX_SECTOR_X - WorldMap.MAP_MIN_SECTOR_X + 1][WorldMap.MAP_MAX_SECTOR_Y - WorldMap.MAP_MIN_SECTOR_Y + 1];
        int subSectorX = this.SUB_SECTOR_X_OFFSET;
        for (int x = WorldMap.MAP_MIN_SECTOR_X; x < WorldMap.MAP_MAX_SECTOR_X; ++x) {
            int subSectorY = this.SUB_SECTOR_Y_OFFSET;
            for (int y = WorldMap.MAP_MIN_SECTOR_Y; y < WorldMap.MAP_MAX_SECTOR_Y; ++y) {
                this.gameSectors[x - WorldMap.MAP_MIN_SECTOR_X][y - WorldMap.MAP_MIN_SECTOR_Y] = new GameSector(x * WorldMap.MAP_SUBSECTOR_SIZE, y * WorldMap.MAP_SUBSECTOR_SIZE, subSectorX, subSectorY++);
            }
            ++subSectorX;
        }
    }

    protected boolean spawn(final Creature object, final boolean isNewSpawn, final boolean isRespawn) {
        final Location loc = object.getLocation();
        final GameSector gameSector = this.getGameSectorByCoords(loc.getX(), loc.getY());
        if (gameSector == null) {
            WorldMap.log.warn("Missing GameSector by x: {} y: {}", loc.getX(), loc.getY());
            return false;
        }
        if (RegionService.getInstance().getRegion(loc.getX(), loc.getY()) == null) {
            return false;
        }
        this.objects.add(object);
        gameSector.add(object);
        loc.setGameSector(gameSector);
        object.setSpawnTime(GameTimeService.getServerTimeInMillis());
        object.setSpawned(true);
        this.notifyEnter(object, gameSector, isNewSpawn, isRespawn);
        this.switchRegion(object, loc.getX(), loc.getY());
        return true;
    }

    protected boolean despawn(final Creature object, final ERemoveActorType type) {
        if (!object.isSpawned()) {
            return false;
        }
        final Location loc = object.getLocation();
        final GameSector gameSector = loc.getGameSector();
        gameSector.remove(object);
        this.notifyExit(object, gameSector, type);
        if (object.getRegion() != null)
            object.getRegion().onExit(object);
        this.objects.remove(object);
        loc.setGameSector(null);
        return true;
    }

    public GameSector getGameSector(final int sectorX, final int sectorY) {
        if (sectorX >= this.gameSectors.length || sectorX < 0 || sectorY >= this.gameSectors[sectorX].length || sectorY < 0) {
            return null;
        }
        return this.gameSectors[sectorX][sectorY];
    }

    public GameSector getGameSectorBySubXY(final int subSectorX, final int subSectorY) {
        final int sectorX = subSectorX - this.SUB_SECTOR_X_OFFSET;
        final int sectorY = subSectorY - this.SUB_SECTOR_Y_OFFSET;
        return this.getGameSector(sectorX, sectorY);
    }

    public GameSector getGameSectorByCoords(final double x, final double y) {
        final int subSectorX = (int) Math.floor(x / WorldMap.MAP_SUBSECTOR_SIZE);
        final int subSectorY = (int) Math.floor(y / WorldMap.MAP_SUBSECTOR_SIZE);
        final int sectorX = subSectorX - WorldMap.MAP_MIN_SECTOR_X;
        final int sectorY = subSectorY - WorldMap.MAP_MIN_SECTOR_Y;
        return this.getGameSector(sectorX, sectorY);
    }

    public boolean updateLocation(final Creature object, final double x, final double y) {
        if (!object.isSpawned() || !object.getMovementController().canMove(x, y)) {
            return false;
        }
        this.switchRegion(object, x, y);
        this.switchGameSector(object, x, y);
        return true;
    }

    private void switchRegion(final Creature object, final double x, final double y) {
        try {
            final Region oldRegion = object.getRegion();
            final Region newRegion = RegionService.getInstance().getRegion(x, y);
            if (newRegion != null) {
                if (oldRegion != newRegion) {
                    if (oldRegion != null) {
                        oldRegion.onExit(object);
                    }
                    object.setRegion(newRegion);
                    newRegion.onEnter(object);
                }
            } else {
                WorldMap.log.error("New region is null for object {} in coords x:{} y:{} z:{}", object, x, y, object.getLocation().getZ());
            }
        } catch (Exception ex) {
            WorldMap.log.error("Error while getting region by x: " + x + " y: " + y + " creature: " + object.toString() + " cos: " + object.getLocation().getCos() + " sin: " + object.getLocation().getSin() + " aiState: " + object.getAi().getState(), ex);
            World.getInstance().teleport(object, object.getSpawnPlacement().getLocation());
        }
    }

    private void switchGameSector(final Creature object, final double x, final double y) {
        final Location loc = object.getLocation();
        final GameSector oldSector = loc.getGameSector();
        final GameSector newSector = this.getGameSectorByCoords(x, y);
        if (newSector == null) {
            WorldMap.log.error("new Sector is null by: {} {}", x, y);
            return;
        }
        if (newSector == oldSector || !oldSector.contains(object)) {
            return;
        }
        newSector.add(object);
        loc.setGameSector(newSector);
        oldSector.remove(object);
        final ECharKind charKind = object.getCharKind();
        final int nx = newSector.getX();
        final int ny = newSector.getY();
        final int ox = oldSector.getX();
        final int oy = oldSector.getY();
        for (int xx = ox - WorldMap.EXIT_RANGE; xx <= ox + WorldMap.EXIT_RANGE; xx += WorldMap.EXIT_RANGE) {
            for (int yy = oy - WorldMap.EXIT_RANGE; yy <= oy + WorldMap.EXIT_RANGE; yy += WorldMap.EXIT_RANGE) {
                if (!MathUtils.isInRangeInclude(nx - WorldMap.EXIT_RANGE, nx + WorldMap.EXIT_RANGE, xx) || !MathUtils.isInRangeInclude(ny - WorldMap.EXIT_RANGE, ny + WorldMap.EXIT_RANGE, yy)) {
                    final GameSector gameSector = this.getGameSectorByCoords(xx, yy);
                    if (gameSector != null) {
                        this.notifyExit(object, gameSector.getObjectsCopyByType(charKind), charKind, charKind.getOutOfRangeRemoveActorType());
                    }
                }
            }
        }
        for (final ECharKind kind : object.isPlayer() ? WorldMap.KIND_ALL : WorldMap.KIND_PLAYER) {
            for (int xx2 = nx - WorldMap.SEE_RANGE; xx2 <= nx + WorldMap.SEE_RANGE; xx2 += WorldMap.MAP_SUBSECTOR_SIZE) {
                for (int yy2 = ny - WorldMap.SEE_RANGE; yy2 <= ny + WorldMap.SEE_RANGE; yy2 += WorldMap.MAP_SUBSECTOR_SIZE) {
                    final GameSector gameSector2 = this.getGameSectorByCoords(xx2, yy2);
                    if (gameSector2 != null) {
                        if (!MathUtils.isInRangeInclude(ox - WorldMap.SEE_RANGE, ox + WorldMap.SEE_RANGE, xx2) || !MathUtils.isInRangeInclude(oy - WorldMap.SEE_RANGE, oy + WorldMap.SEE_RANGE, yy2)) {
                            this.notifyEnter(object, gameSector2.getObjectsCopyByType(kind), gameSector2.getSubSectorX(), gameSector2.getSubSectorY(), kind, false, false);
                        }
                    }
                }
            }
        }
    }

    private void notifyEnter(final Creature creature, final GameSector newSector, final boolean isNewSpawn, final boolean isRespawn) {
        final int nx = newSector.getX();
        final int ny = newSector.getY();
        for (final ECharKind kind : creature.isPlayer() ? WorldMap.KIND_ALL : WorldMap.KIND_PLAYER) {
            for (int xx = nx - WorldMap.SEE_RANGE; xx <= nx + WorldMap.SEE_RANGE; xx += WorldMap.MAP_SUBSECTOR_SIZE) {
                for (int yy = ny - WorldMap.SEE_RANGE; yy <= ny + WorldMap.SEE_RANGE; yy += WorldMap.MAP_SUBSECTOR_SIZE) {
                    final GameSector gameSector = this.getGameSectorByCoords(xx, yy);
                    if (gameSector != null) {
                        this.notifyEnter(creature, gameSector.getObjectsCopyByType(kind), gameSector.getSubSectorX(), gameSector.getSubSectorY(), kind, isNewSpawn, isRespawn);
                    }
                }
            }
        }
    }

    private void notifyEnter(final Creature creature, final List<Creature> objects, final int subSectorX, final int subSectorY, final ECharKind type, final boolean isNewSpawn, final boolean isRespawn) {
        if (objects == null || objects.isEmpty()) {
            return;
        }
        if (creature.isPlayer()) {
            creature.see(objects, subSectorX, subSectorY, type);
        }

        if (type.isPlayer()) {
            for (final Creature object : objects) {
                final Player player = (Player) object;
                player.see(creature, subSectorX, subSectorY, isNewSpawn, isRespawn);
            }
        }

        // Start the AI that is disabled.
        /*if (creature.isPlayer() && (type.isMonster() || type.isNpc())) {
            for (final Creature object : objects) {
                if (object.isSpawned()
                        && !object.isDead()
                        && object.getAi() != null
                        && object.getAi().isStopped())
                    object.getAi().notifyStart();
            }
        }*/
    }

    private void notifyExit(final Creature creature, GameSector gameSector, final ERemoveActorType removeType) {
        final int nx = gameSector.getX();
        final int ny = gameSector.getY();
        for (int xx = nx - WorldMap.EXIT_RANGE; xx <= nx + WorldMap.EXIT_RANGE; xx += WorldMap.MAP_SUBSECTOR_SIZE) {
            for (int yy = ny - WorldMap.EXIT_RANGE; yy <= ny + WorldMap.EXIT_RANGE; yy += WorldMap.MAP_SUBSECTOR_SIZE) {
                gameSector = this.getGameSectorByCoords(xx, yy);
                if (gameSector != null) {
                    this.notifyExit(creature, gameSector.getObjectsByType(ECharKind.Player), ECharKind.Player, removeType);
                    if (creature.isPlayer()) {
                        this.notifyExit(creature, gameSector.getObjectsByType(ECharKind.Npc), ECharKind.Npc, removeType);
                        this.notifyExit(creature, gameSector.getObjectsByType(ECharKind.Monster), ECharKind.Monster, removeType);
                        this.notifyExit(creature, gameSector.getObjectsByType(ECharKind.Deadbody), ECharKind.Deadbody, removeType);
                        this.notifyExit(creature, gameSector.getObjectsByType(ECharKind.Vehicle), ECharKind.Vehicle, removeType);
                        this.notifyExit(creature, gameSector.getObjectsByType(ECharKind.Collect), ECharKind.Collect, removeType);
                        this.notifyExit(creature, gameSector.getObjectsByType(ECharKind.Household), ECharKind.Household, removeType);
                    }
                }
            }
        }
    }

    private void notifyExit(final Creature creature, final Collection<Creature> objects, final ECharKind type, final ERemoveActorType removeType) {
        if (objects.isEmpty()) {
            return;
        }
        if (type.isPlayer()) {
            for (final Creature object : objects) {
                final Player player = (Player) object;
                player.notSee(creature, removeType, true);
            }
        }

        // Player exits the region and target is NPC or Monster and there are no players within my region.
        /*if (creature.isPlayer() && (type.isNpc() || type.isMonster())) {
            for (final Creature object : objects) {
                if (object.getAi() != null && !object.getAi().isStopped() && object.getLocation().getRegion().getSize() < 1)
                    object.getAi().notifyStop();
            }
        }*/
    }
}