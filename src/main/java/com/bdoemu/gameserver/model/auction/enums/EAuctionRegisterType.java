package com.bdoemu.gameserver.model.auction.enums;

public enum EAuctionRegisterType {
    None,
    NpcWorkerMarket,
    GuildAuctionMarket,
    ServantMatingMarket,
    ServantMarket;

    public static EAuctionRegisterType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }

    public boolean isNone() {
        return this == EAuctionRegisterType.None;
    }

    public boolean isServantMatingMarket() {
        return this == EAuctionRegisterType.ServantMatingMarket;
    }

    public boolean isServantMarket() {
        return this == EAuctionRegisterType.ServantMarket;
    }

    public boolean isGuildAuction() {
        return this == EAuctionRegisterType.GuildAuctionMarket;
    }
}
