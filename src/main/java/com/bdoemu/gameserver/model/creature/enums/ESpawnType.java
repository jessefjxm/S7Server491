package com.bdoemu.gameserver.model.creature.enums;

public enum ESpawnType {
    NormalNpc(0),
    SkillTrainer(1),
    ItemRepairer(2),
    ShopMerchant(3),
    ImportantNpc(4),
    TradeMerchant(5),
    WareHouse(6),
    Stable(7),
    Wharf(8),
    Transfer(9),
    Intimacy(10),
    Guild(11),
    Explorer(12),
    Inn(13),
    Auction(14),
    Mating(15),
    Potion(16),
    Weapon(17),
    Jewel(18),
    Furniture(19),
    Collect(20),
    Fish(21),
    Worker(22),
    Alchemy(23),
    GuildShop(24),
    ItemMarket(25),
    TerritorySupply(26),
    TerritoryTrad(27),
    Smuggle(28),
    Cook(29),
    PC(30),
    Grocery(31),
    RandomShop(32),
    SupplyShop(33),
    RandomShopDay(34),
    FishSupplyShop(35),
    GuildSupplyShop(36),
    GuildStable(37),
    GuildWharf(38);

    private byte id;

    ESpawnType(final int id) {
        this.id = (byte) id;
    }

    public static ESpawnType valueOf(final int reqType) {
        if (reqType < 0 || reqType > values().length - 1) {
            return null;
        }
        return values()[reqType];
    }
}
