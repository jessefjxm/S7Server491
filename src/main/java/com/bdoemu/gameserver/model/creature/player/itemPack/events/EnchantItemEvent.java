package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.EtcOptionConfig;
import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.network.sendable.SMBroadcastGetItem;
import com.bdoemu.core.network.sendable.SMEnchantItem;
import com.bdoemu.core.network.sendable.SMUpdateEnchantFailCount;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerBag;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.MasterItemMarket;
import com.bdoemu.gameserver.model.items.enums.EEnchantType;
import com.bdoemu.gameserver.model.items.enums.EItemGetType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.items.templates.ItemEnchantT;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalEntryType;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.utils.PARand;
import com.bdoemu.gameserver.worldInstance.World;

public class EnchantItemEvent implements IBagEvent {
    private final PlayerBag playerBag;
    private Player player;
    private EItemStorageLocation itemStorageType;
    private EItemStorageLocation stoneStorageType;
    private EItemStorageLocation croneStorageType;
    private int itemSlot;
    private int stoneSlot;
    private int croneSlotIndex;
    private boolean isPerfect;
    private Item stone;
    private Item item;
    private Item crone;
    private ItemPack stonePack;
    private ItemPack itemPack;
    private ItemPack croneItemPack;
    private ItemEnchantT enchantTemplate;
    private int enchantRate;
    private int enchantResultType;
    private int itemId;
    private int maxEndurance;
    private int enchantLevel;
    private EStringTable msg;
    private Long stoneCount;
    private boolean type;
    private EEnchantType enchantType;
    private long croneStoneCount;

    public EnchantItemEvent(final Player player, final EItemStorageLocation itemStorageType, final int itemSlot, final EItemStorageLocation stoneStorageType, final int stoneSlot, final EItemStorageLocation croneStorageType, final int croneSlotIndex, final boolean isPerfect) {
        this.msg = EStringTable.NONE;
        this.stoneCount = 1L;
        this.type = false;
        this.croneStoneCount = 0L;
        this.player = player;
        this.itemStorageType = itemStorageType;
        this.stoneStorageType = stoneStorageType;
        this.croneStorageType = croneStorageType;
        this.itemSlot = itemSlot;
        this.stoneSlot = stoneSlot;
        this.croneSlotIndex = croneSlotIndex;
        this.isPerfect = isPerfect;
        this.playerBag = player.getPlayerBag();
    }

    @Override
    public void onEvent() {
        final boolean isSuccess = PARand.PARollChance(this.enchantRate + RateConfig.ENCHANT_FAIL_RATE * player.getEnchantFailCount());
        this.type = true;
        this.enchantLevel = this.item.getEnchantLevel();
        this.itemId = this.item.getItemId();
        final JournalEntry journalEntry = new JournalEntry();
        journalEntry.setDate(GameTimeService.getServerTimeInMillis());
        journalEntry.setParam0((short) this.itemId);
        journalEntry.setParam1((short) this.enchantLevel);
        if (this.crone != null) {
            this.crone.addCount(-this.croneStoneCount);
            if (this.crone.getCount() <= 0L) {
                this.croneItemPack.removeItem(this.croneSlotIndex);
            }
            this.player.getGameStats().getWeight().addWeight(-this.crone.getTemplate().getWeight());
        }
        if (isSuccess) {
            ++this.enchantLevel;
            if (this.enchantRate < 1000000) {
                this.player.setEnchantFailCount(0);
                this.player.setEnchantSuccessCount(this.player.getEnchantSuccessCount() + 1);
            }
            if (this.enchantTemplate.isNotifyWorld()) {
                World.getInstance().broadcastWorldPacket(new SMBroadcastGetItem(EItemGetType.ENCHANT, this.player.getName(), this.itemId, this.item.getEnchantLevel(), this.enchantLevel));
            }
            if (this.isPerfect) {
                this.item.addMaxEndurance(-this.enchantTemplate.getReduceMaxEnduranceAtPerfectEnchant());
            }
            journalEntry.setParam4((short) this.enchantLevel);
            journalEntry.setType(EJournalEntryType.ItemEnchantSuccess);
        } else {
            if (this.enchantTemplate.isNotifyWorld()) {
                World.getInstance().broadcastWorldPacket(
                        new SMBroadcastGetItem(
                                EItemGetType.ENCHANT_FAIL,
                                this.player.getName(),
                                this.itemId,
                                this.enchantLevel,
                                this.item.getEnchantLevel()
                        )
                );
            }

            if (this.enchantType.isAccessoryEnchant()) {
                if (this.crone == null) {
                    this.itemId = 0;
                    this.enchantLevel = 0;
                    this.item.addCount(-1L);
                    this.enchantResultType = 1;
                } else {
                    if (this.enchantLevel > 0) {
                        --this.enchantLevel;
                    }
                    this.enchantResultType = 2;
                }
            } else {
                this.enchantResultType = 3;
                this.item.addMaxEndurance(-this.enchantTemplate.getReduceMaxEnduranceAtFail());
            }
            int enchantFailCount = 1;
            switch (this.item.getEnchantLevel()) {
                case 15: {
                    enchantFailCount = 2;
                    break;
                }
                case 16: {
                    enchantFailCount = 3;
                    break;
                }
                case 17: {
                    enchantFailCount = 4;
                    break;
                }
                case 18: {
                    enchantFailCount = 5;
                    break;
                }
                case 19: {
                    enchantFailCount = 6;
                    break;
                }
            }
            if (this.crone == null) {
                if (this.enchantType.isUnsafeRiskEnchant()) {
                    --this.enchantLevel;
                }
                this.player.setEnchantFailCount(this.player.getEnchantFailCount() + enchantFailCount);
            }
            this.player.setEnchantSuccessCount(0);
            journalEntry.setParam4((short) this.enchantLevel);
            journalEntry.setType(EJournalEntryType.ItemEnchantFail);
        }
        this.item.setEnchantLevel(this.enchantLevel);
        this.maxEndurance = this.item.getMaxEndurance();
        this.stone.addCount(-this.stoneCount);
        this.player.getGameStats().getWeight().addWeight(-this.stone.getTemplate().getWeight() * this.stoneCount);
        if (this.stone.getCount() <= 0L) {
            this.stonePack.removeItem(this.stoneSlot);
        }
        if (this.item.getCount() <= 0L) {
            this.itemPack.removeItem(this.itemSlot);
            this.player.getGameStats().getWeight().addWeight(-this.item.getTemplate().getWeight());
        }
        this.player.addJournalEntryAndNotify(journalEntry);
        this.player.getObserveController().notifyObserver(EObserveType.enchantItem, (int) (isSuccess ? 1 : 0), isSuccess ? this.player.getEnchantSuccessCount() : this.player.getEnchantFailCount());
        this.player.sendPacket(new SMUpdateEnchantFailCount(this.player));
        this.player.sendPacket(new SMEnchantItem(this));
    }

    @Override
    public boolean canAct() {
        if (!this.stoneStorageType.isPlayerInventories() || !this.itemStorageType.isPlayerInventories()) {
            return false;
        }
        this.stonePack = this.playerBag.getItemPack(this.stoneStorageType);
        this.itemPack = this.playerBag.getItemPack(this.itemStorageType);
        this.item = this.itemPack.getItem(this.itemSlot);
        this.stone = this.stonePack.getItem(this.stoneSlot);
        if (this.item == null || this.stone == null) {
            return false;
        }
        this.enchantTemplate = this.item.getTemplate().getEnchantTemplates().get(this.item.getEnchantLevel() + 1);
        if (this.enchantTemplate == null) {
            return false;
        }
        if (this.croneStorageType.isPlayerInventories()) {
            this.croneItemPack = this.playerBag.getItemPack(this.croneStorageType);
            this.crone = this.croneItemPack.getItem(this.croneSlotIndex);
            if (this.crone != null) {
                if (this.crone.getItemId() != EtcOptionConfig.PREVENT_DOWNGRADE_ITEM_KEY) {
                    return false;
                }
                if (!EtcOptionConfig.ENABLE_ENCHANT_CRONE_STONE) {
                    this.enchantResultType = 5;
                    this.msg = EStringTable.eErrNoItemDontEnchantItem;
                    this.player.sendPacket(new SMEnchantItem(this));
                    return false;
                }
                final MasterItemMarket currentMasterItemMarket = ItemMarketService.getInstance().getMasterItemMarket(this.item.getItemId(), this.item.getEnchantLevel());
                final MasterItemMarket nextMasterItemMarket = ItemMarketService.getInstance().getMasterItemMarket(this.item.getItemId(), this.item.getEnchantLevel() + 1);
                final long diffMarketPrice = nextMasterItemMarket.getItemMaxPrice() - currentMasterItemMarket.getItemMaxPrice();
                this.croneStoneCount = 1L;
                if (diffMarketPrice >= 4000000L) {
                    this.croneStoneCount = (long) Math.floor(diffMarketPrice / 2000000.0f);
                }
                if (this.crone.getCount() < this.croneStoneCount) {
                    return false;
                }
            }
        }
        if (this.stone.getTemplate().getGradeType().isWhite()) {
            return false;
        }
        if (this.item.getEnchantLevel() >= EtcOptionConfig.MAX_ENCHANT_LEVEL) {
            this.enchantResultType = 5;
            this.msg = EStringTable.eErrNoItemEnchantLevelIsMax;
            this.player.sendPacket(new SMEnchantItem(this));
            return false;
        }
        if (this.item.getTemplate().getEndurance() * this.item.getMaxEndurance() / 100 <= 20) {
            this.enchantResultType = 5;
            this.msg = EStringTable.eErrNoItemMaxEnduranceIsLack;
            this.player.sendPacket(new SMEnchantItem(this));
            return false;
        }
        this.enchantType = this.enchantTemplate.getEnchantType();
        this.stoneCount = (this.isPerfect ? this.enchantTemplate.getPerfectEnchantItemCount() : this.enchantTemplate.getReqEnchantItemCount());
        if (this.stoneCount == null || this.enchantTemplate.getReqEnchantItemId() != this.stone.getItemId() || this.stone.getCount() < this.stoneCount) {
            return false;
        }
        if (this.isPerfect) {
            this.enchantRate = 1000000;
        } else {
            switch (this.enchantType) {
                case SafeEnchant:
                case UnsafeEnchant:
                case UnsafeRiskEnchant: {
                    if (this.item.getItemClassify().isArmor()) {
                        this.enchantRate = RateConfig.ENCHANT_ARMOR_RATES[this.item.getEnchantLevel() + 1];
                        break;
                    }
                    if (this.item.getItemClassify().isWeapon()) {
                        this.enchantRate = RateConfig.ENCHANT_WEAPON_RATES[this.item.getEnchantLevel() + 1];
                        break;
                    }
                    break;
                }
                case AccessoryEnchant: {
                    if (this.item.getItemClassify().isAccessory()) {
                        this.enchantRate = RateConfig.ENCHANT_ACCESSORY_RATES[this.item.getEnchantLevel() + 1];
                        break;
                    }
                    if (this.item.getItemClassify().isArmor()) {
                        this.enchantRate = RateConfig.ENCHANT_CLOTHES_RATES[this.item.getEnchantLevel() + 1];
                        break;
                    }
                    break;
                }
            }
        }
        return true;
    }

    public long getCroneStoneCount() {
        return croneStoneCount;
    }

    public Item getCrone() {
        return crone;
    }

    public EItemStorageLocation getCroneStorageType() {
        return croneStorageType;
    }

    public int getCroneSlotIndex() {
        return croneSlotIndex;
    }

    public boolean isType() {
        return type;
    }

    public int getMaxEndurance() {
        return maxEndurance;
    }

    public int getItemId() {
        return itemId;
    }

    public int getEnchantLevel() {
        return enchantLevel;
    }

    public EStringTable getMsg() {
        return msg;
    }

    public Long getStoneCount() {
        return stoneCount;
    }

    public int getStoneSlot() {
        return stoneSlot;
    }

    public EItemStorageLocation getStoneStorageType() {
        return stoneStorageType;
    }

    public int getItemSlot() {
        return itemSlot;
    }

    public EItemStorageLocation getItemStorageType() {
        return itemStorageType;
    }

    public int getEnchantType() {
        return enchantResultType;
    }
}