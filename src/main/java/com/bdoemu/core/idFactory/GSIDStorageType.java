package com.bdoemu.core.idFactory;

public enum GSIDStorageType {
    Collect(16906240L, 18648064L),
    ONLINE(2097152L, 2212864L, 1024),
    NpcWorkerSession(0L, 20480L, 1024),
    Creatures(226492416L, 226662400L, 1024),
    DeadBody(41943040L, 42113024L, 1024),
    GUILD(10000000000000000L, 9223372036854775806L),
    Mail(10000000000000000L, 9223372036854775806L),
    ItemMarket(10000000000000000L, 9223372036854775806L),
    MasterItemMarket(10000000000000000L, 9223372036854775806L),
    PLAYER(10000000000000000L, 9223372036854775806L),
    Vehicle(10000000000000000L, 9223372036854775806L),
    ITEM(10000000000000000L, 9223372036854775806L),
    NpcWorker(10000000000000000L, 9223372036854775806L),
    Household(10000000000000000L, 9223372036854775806L),
    HouseInstallation(10000000000000000L, 9223372036854775806L),
    PvpPlayer(1L, 9223372036854775806L),
    PARTY(256L, 2147483646L),
    CACHE(255L, -1L),
    Friend(1L, 9223372036854775806L),
    Buff(31457280L, 2147483646L),
    PvpMatches(1L, -1L),
    GuildComments(1L, 2147483646L);

    private long minId;
    private long maxId;
    private int segment;

    private GSIDStorageType(final long minId, final long maxId) {
        this.minId = minId;
        this.maxId = maxId;
    }

    private GSIDStorageType(final long minId, final long maxId, final int segment) {
        this.minId = minId;
        this.maxId = maxId;
        this.segment = segment;
    }

    public long getMinId() {
        return this.minId;
    }

    public long getMaxId() {
        return this.maxId;
    }

    public int getSegment() {
        return this.segment;
    }
}
