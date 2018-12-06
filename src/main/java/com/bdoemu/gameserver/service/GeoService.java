package com.bdoemu.gameserver.service;

import com.bdoemu.commons.collection.SetBlockingQueue;
import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.commons.utils.ZipUtils;
import com.bdoemu.core.configs.GeoDataConfig;
import com.bdoemu.core.configs.RegionConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.geo.GeoSector;
import com.bdoemu.gameserver.model.geo.GeoSectorType;
import com.sun.nio.zipfs.ZipPath;
import lzma.sdk.lzma.Decoder;
import lzma.streams.LzmaInputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@StartupComponent(value = "Service")
public class GeoService {
    private static final Logger log = LoggerFactory.getLogger(GeoService.class);
    private static final int MAP_MIN_X = RegionConfig.MAP_SECTOR_START_X * RegionConfig.MAP_SECTOR_SIZE;
    private static final int MAP_MIN_Y = RegionConfig.MAP_SECTOR_START_Y * RegionConfig.MAP_SECTOR_SIZE;
    private static final int MAP_MAX_X = RegionConfig.MAP_SECTOR_END_X * RegionConfig.MAP_SECTOR_SIZE + RegionConfig.MAP_SECTOR_SIZE;
    private static final int MAP_MAX_Y = RegionConfig.MAP_SECTOR_END_Y * RegionConfig.MAP_SECTOR_SIZE + RegionConfig.MAP_SECTOR_SIZE;
    private static final int MAP_MIN_SECTOR_X = MAP_MIN_X / RegionConfig.MAP_SECTOR_SIZE;
    private static final int MAP_MIN_SECTOR_Y = MAP_MIN_Y / RegionConfig.MAP_SECTOR_SIZE;
    private static final int MAP_MAX_SECTOR_X = MAP_MAX_X / RegionConfig.MAP_SECTOR_SIZE;
    private static final int MAP_MAX_SECTOR_Y = MAP_MAX_Y / RegionConfig.MAP_SECTOR_SIZE;
    private static final GeoSector[][] geoSectors = new GeoSector[MAP_MAX_SECTOR_X - MAP_MIN_SECTOR_X + 1][MAP_MAX_SECTOR_Y - MAP_MIN_SECTOR_Y + 1];
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private ScheduledFuture<?> loadProgressTask;
    private int allFilesCount;
    private int loadedFilesCount;
    private FileSystem zipFileSystem;
    private Path rootPath;
    private Set<Pair<Integer, Integer>> cachedGeoFiles;
    private Map<Pair<Integer, Integer>, ZipPath> zipEntries;
    private SetBlockingQueue<Path> queue;
    private Future<?> queueTask;
    private boolean isLoading;
    private long lastTimeUsedQueue;
    private GeoService() {
        this.cachedGeoFiles = new HashSet<>();
        this.zipEntries = new HashMap<>();
        this.queue = new SetBlockingQueue<>();
        this.isLoading = false;
        if (!GeoDataConfig.ENABLE) {
            log.info("Geodata disabled by config.");
            return;
        }
        if (GeoDataConfig.LOAD_FROM_ZIP) {
            try {
                this.zipFileSystem = ZipUtils.createZipFileSystem(GeoDataConfig.GEO_ZIP_FILE_PATH, false);
            } catch (IOException e) {
                log.error("Error while initializing ZIP file system", e);
            }
        }
        this.rootPath = GeoDataConfig.LOAD_FROM_ZIP ? this.zipFileSystem.getPath("/") : Paths.get(GeoDataConfig.GEO_FILE_PATH);
        try {
            Files.walk(this.rootPath, new FileVisitOption[0]).filter(item -> !Files.isDirectory(item) && FilenameUtils.getExtension(item.toString()).equals("bin")).forEach(filePath -> {
                        String[] regionCoordData = FilenameUtils.getBaseName(filePath.toString()).split("_");
                        int sectorX = Integer.parseInt(regionCoordData[0]);
                        int sectorY = Integer.parseInt(regionCoordData[1]);
                        Pair<Integer, Integer> pair = Pair.of(sectorX, sectorY);
                        this.cachedGeoFiles.add(pair);
                        if (GeoDataConfig.LOAD_FROM_ZIP) {
                            this.zipEntries.put(pair, (ZipPath) filePath);
                        }
                    }
            );
        } catch (IOException e) {
            log.error("Error while creating geo cache", e);
        } finally {
            log.info("Cached {} geo files to cache", this.cachedGeoFiles.size());
        }
        if (!GeoDataConfig.GEO_DYNAMIC_LOAD) {
            this.loadProgressTask = ThreadPool.getInstance().scheduleGeneralAtFixedRate(new ProgressReporterTask(), 0, 5, TimeUnit.SECONDS);
            this.load();
        }
    }

    public static GeoService getInstance() {
        return Holder.INSTANCE;
    }

    private void load() {
        try {
            List<Path> files = Files.walk(this.rootPath).filter(item -> !Files.isDirectory(item) && FilenameUtils.getExtension(item.toString()).equals("bin")).collect(Collectors.toList());
            this.allFilesCount = files.size();
            for (Path file : files) {
                executorService.submit(() -> this.loadGeoFile(file));
            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
            } catch (InterruptedException e) {
                log.error("Error while awaiting", e);
            }
        } catch (Exception e) {
            log.error("Error while loading geodata!", e);
        }
        this.loadProgressTask.cancel(true);
        log.info("Loaded {} geo files.", this.loadedFilesCount);
    }

    private void load(int sectorX, int sectorY) {
        Pair pair = Pair.of((Object) sectorX, (Object) sectorY);
        if (this.cachedGeoFiles.contains(pair)) {
            if (GeoDataConfig.LOAD_FROM_ZIP) {
                this.loadGeoFile(this.zipEntries.get(pair));
            } else {
                this.loadGeoFile(Paths.get(this.rootPath.toString(), "" + sectorX + "_" + sectorY + ".bin"));
            }
        }
    }

    private synchronized void loadGeoFiles() {
        if (this.queueTask != null && !this.queueTask.isCancelled()) {
            return;
        }
        this.lastTimeUsedQueue = System.currentTimeMillis();
        this.queueTask = ThreadPool.getInstance().scheduleAiAtFixedRate(() -> {
                    if (this.queue.isEmpty() && this.lastTimeUsedQueue + 3000000 < System.currentTimeMillis()) {
                        this.queueTask.cancel(true);
                    } else if (!this.queue.isEmpty() && !this.isLoading) {
                        int regionY;
                        int sectorY;
                        Path path = this.queue.take();
                        String[] regionCoordData = FilenameUtils.getBaseName(path.toString()).split("_");
                        int sectorX = Integer.parseInt(regionCoordData[0]);
                        int regionX = sectorX - RegionConfig.MAP_SECTOR_START_X;
                        if (geoSectors[regionX][regionY = (sectorY = Integer.parseInt(regionCoordData[1])) - RegionConfig.MAP_SECTOR_START_Y] != null) {
                            return;
                        }
                        this.isLoading = true;
                        Decoder decoder = new Decoder();
                        try {
                            FileBinaryReader reader = new FileBinaryReader(path);
                            Throwable throwable = null;
                            try {
                                GeoSector geoSector = new GeoSector(regionX, regionY);
                                short geometryCount = reader.readH();
                                for (short geometryIndex = 0; geometryIndex < geometryCount; geometryIndex = (short) (geometryIndex + 1)) {
                                    GeoSectorType geometryType = GeoSectorType.values()[reader.readH()];
                                    int lodLevel = geometryType == GeoSectorType.COLLISION ? GeoDataConfig.GEO_COLLISION_LOD_LEVEL : GeoDataConfig.GEO_TERRAIN_LOD_LEVEL;
                                    short geoZ = reader.readH();
                                    int compressedMeshSize = reader.readD();
                                    if (!GeoDataConfig.ALLOWED_GEO_SECTOR_TYPES.contains(geometryType)) {
                                        reader.skip(compressedMeshSize);
                                        continue;
                                    }
                                    ByteArrayInputStream stream = new ByteArrayInputStream(reader.readB(compressedMeshSize));
                                    Throwable throwable2 = null;
                                    try {
                                        LzmaInputStream decompressedInputStream = new LzmaInputStream(stream, decoder);
                                        Throwable throwable3 = null;
                                        try {
                                            DataInputStream dataStream = new DataInputStream(decompressedInputStream);
                                            Throwable throwable4 = null;
                                            try {
                                                int verticlesCount = dataStream.readInt();
                                                float[] verticles = new float[verticlesCount];
                                                for (int verticleIndex = 0; verticleIndex < verticles.length; ++verticleIndex) {
                                                    verticles[verticleIndex] = dataStream.readFloat();
                                                }
                                                int[] indexes = new int[dataStream.readInt()];
                                                for (int index = 0; index < indexes.length; ++index) {
                                                    indexes[index] = dataStream.readInt();
                                                }
                                                geoSector.attachCollision(verticles, indexes, geoZ);
                                            } catch (Throwable verticlesCount) {
                                                throwable4 = verticlesCount;
                                                throw verticlesCount;
                                            } finally {
                                                if (dataStream != null) {
                                                    if (throwable4 != null) {
                                                        try {
                                                            dataStream.close();
                                                        } catch (Throwable verticlesCount) {
                                                            throwable4.addSuppressed(verticlesCount);
                                                        }
                                                    } else {
                                                        dataStream.close();
                                                    }
                                                }
                                            }
                                        } catch (Throwable dataStream) {
                                            throwable3 = dataStream;
                                            throw dataStream;
                                        } finally {
                                            if (decompressedInputStream != null) {
                                                if (throwable3 != null) {
                                                    try {
                                                        decompressedInputStream.close();
                                                    } catch (Throwable dataStream) {
                                                        throwable3.addSuppressed(dataStream);
                                                    }
                                                } else {
                                                    decompressedInputStream.close();
                                                }
                                            }
                                        }
                                    } catch (Throwable decompressedInputStream) {
                                        throwable2 = decompressedInputStream;
                                        throw decompressedInputStream;
                                    } finally {
                                        if (stream != null) {
                                            if (throwable2 != null) {
                                                try {
                                                    stream.close();
                                                } catch (Throwable decompressedInputStream) {
                                                    throwable2.addSuppressed(decompressedInputStream);
                                                }
                                            } else {
                                                stream.close();
                                            }
                                        }
                                    }
                                }
                                GeoService.geoSectors[regionX][regionY] = geoSector;
                                ++this.loadedFilesCount;
                            } catch (Throwable geoSector) {
                                throwable = geoSector;
                                throw geoSector;
                            } finally {
                                if (reader != null) {
                                    if (throwable != null) {
                                        try {
                                            reader.close();
                                        } catch (Throwable geoSector) {
                                            throwable.addSuppressed(geoSector);
                                        }
                                    } else {
                                        reader.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            log.error("Error while loadFile [{}]", path.toString(), e);
                        }
                        this.isLoading = false;
                    }
                }
                , 100, 100, TimeUnit.MILLISECONDS);
    }

    private void loadGeoFile(Path path) {
        this.queue.add(path);
        this.loadGeoFiles();
    }

    private GeoSector getGeoSector(double x, double y) {
        int sectorX = (int) Math.floor(x / (double) RegionConfig.MAP_SECTOR_SIZE);
        int sectorY = (int) Math.floor(y / (double) RegionConfig.MAP_SECTOR_SIZE);
        int regionX = sectorX - RegionConfig.MAP_SECTOR_START_X;
        int regionY = sectorY - RegionConfig.MAP_SECTOR_START_Y;
        if (GeoDataConfig.GEO_DYNAMIC_LOAD && geoSectors[regionX][regionY] == null) {
            this.load(sectorX, sectorY);
        }
        return geoSectors[regionX][regionY];
    }

    public double validateZ(double x, double y, double z) {
        if (!GeoDataConfig.ENABLE) {
            return z;
        }
        GeoSector geoSector = this.getGeoSector(x, y);
        if (geoSector != null) {
            return geoSector.validateZ(x, y, z);
        }
        return Double.MIN_VALUE;
    }

    public boolean canSee(Creature actor, Creature target) {
        if (!GeoDataConfig.ENABLE || !GeoDataConfig.ENABLE_CAN_SEE) {
            return true;
        }
        GeoSector geoSector = this.getGeoSector(actor.getLocation().getX(), actor.getLocation().getY());
        return geoSector == null || geoSector.canSee(actor.getLocation(), target.getLocation(), actor.getTemplate().getBodyHeight(), target.getTemplate().getBodyHeight());
    }

    private static class Holder {
        static final GeoService INSTANCE = new GeoService();
    }

    private class ProgressReporterTask implements Runnable {
        private ProgressReporterTask() {
        }

        @Override
        public void run() {
            if (GeoService.this.loadedFilesCount > 0 && GeoService.this.allFilesCount > 0) {
                log.info("Loaded {}/{} geo files.", GeoService.this.loadedFilesCount, GeoService.this.allFilesCount);
            }
        }
    }
}

