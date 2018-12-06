// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.sendable.SMAlchemyUpgrade;
import com.bdoemu.gameserver.dataholders.DyeingItemData;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.items.enums.EItemClassify;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.enums.EItemType;
import com.bdoemu.gameserver.model.items.templates.DyeingItemT;
import com.bdoemu.gameserver.model.items.templates.ItemEnchantT;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Item extends JSONable {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) Item.class);
    }

    private long objectId;
    private long count;
    private long price;
    private int endurance;
    private int maxEndurance;
    private int enchantLevel;
    private ItemTemplate template;
    private long expirationPeriod;
    private DyeingItemT[] colorPalettes;
    private Jewel[] jewels;
    private boolean isVested;
    private int regionId;
    private EItemStorageLocation storageLocation;
    private int slotIndex;
    private int alchemyStoneExp;
    private List<ActiveBuff> buffs;
    private int colorPaletteType;

    public Item(final Item item, final long count, final long price) {
        this(item, count);
        this.price = price;
    }

    public Item(final Item item, final long count) {
        this.objectId = -1L;
        this.expirationPeriod = -1L;
        this.regionId = 1;
        this.enchantLevel = item.getEnchantLevel();
        this.regionId = item.getRegionId();
        this.template = item.getTemplate();
        this.count = count;
        this.colorPalettes = item.getColorPalettes();
        this.colorPaletteType = item.getColorPaletteType();
        this.jewels = item.getJewels();
        this.endurance = item.getEndurance();
        this.maxEndurance = item.getMaxEndurance();
        this.isVested = item.isVested();
        this.expirationPeriod = item.getExpirationPeriod();
        this.price = item.getItemPrice();
        this.alchemyStoneExp = item.getAlchemyStoneExp();
    }

    public Item(final ItemTemplate template, final long count) {
        this(template, count, 0);
    }

    public Item(final int itemId, final long count, final int enchantLevel) {
        this(ItemData.getInstance().getItemTemplate(itemId), count, enchantLevel);
    }

    public Item(final ItemTemplate template, final long count, final int enchantLevel) {
        this.objectId = -1L;
        this.expirationPeriod = -1L;
        this.regionId = 1;
        this.count = count;
        this.template = template;
        this.enchantLevel = enchantLevel;
        this.endurance = template.getEndurance();
        this.price = template.getOriginalPrice();
        this.maxEndurance = template.getEndurance();
        if (template.getVestedType().isOnRecive()) {
            this.isVested = true;
        }
        if (template.getExpirationPeriod() > 0 && this.expirationPeriod < 0L) {
            this.expirationPeriod = GameTimeService.getServerTimeInSecond() + template.getExpirationPeriod() * 60;
        }
    }

    public Item(final BasicDBObject dbObject) {
        this.objectId = -1L;
        this.expirationPeriod = -1L;
        this.regionId = 1;
        this.objectId = dbObject.getLong("objectId");
        final int itemId = dbObject.getInt("itemId");
        this.regionId = dbObject.getInt("regionId");
        this.enchantLevel = dbObject.getInt("enchantLevel");
        this.template = ItemData.getInstance().getItemTemplate(itemId);
        this.count = dbObject.getLong("count");
        this.endurance = dbObject.getInt("endurance");
        this.maxEndurance = dbObject.getInt("maxEndurance");
        this.expirationPeriod = dbObject.getLong("expirationPeriod");
        this.isVested = dbObject.getBoolean("isVested");
        this.price = dbObject.getLong("price");
        this.alchemyStoneExp = dbObject.getInt("alchemyStoneExp");
        this.colorPaletteType = dbObject.getInt("colorPaletteType", 0);
        final BasicDBList jewelsDB = (BasicDBList) dbObject.get("jewels");
        if (jewelsDB.size() > 0) {
            this.jewels = new Jewel[6];
            for (int i = 0; i < jewelsDB.size(); ++i) {
                final BasicDBObject obj = (BasicDBObject) jewelsDB.get(i);
                this.jewels[obj.getInt("index")] = new Jewel(obj.getInt("itemId"));
            }
        }
        final BasicDBList palettesDB = (BasicDBList) dbObject.get("colorPalettes");
        if (palettesDB.size() > 0) {
            this.colorPalettes = new DyeingItemT[12];
            for (int j = 0; j < palettesDB.size(); ++j) {
                final BasicDBObject obj2 = (BasicDBObject) palettesDB.get(j);
                this.colorPalettes[obj2.getInt("index")] = DyeingItemData.getInstance().getTemplate(obj2.getInt("itemId"));
            }
        }
    }

    public int getColorPaletteType() {
        return this.colorPaletteType;
    }

    public void setColorPaletteType(final int colorPaletteType) {
        this.colorPaletteType = colorPaletteType;
    }

    public int getAlchemyStoneExp() {
        return this.alchemyStoneExp;
    }

    public void initGameObjectId() {
        if (this.objectId < 0L) {
            this.objectId = GameServerIDFactory.getInstance().nextId(GSIDStorageType.ITEM);
        }
    }

    public synchronized void addMaxEndurance(final int value) {
        if (this.maxEndurance + value < 0) {
            this.maxEndurance = 0;
        } else if (this.maxEndurance + value > this.getTemplate().getEndurance()) {
            this.maxEndurance = this.getTemplate().getEndurance();
        } else {
            this.maxEndurance += value;
        }
        if (this.endurance > this.maxEndurance) {
            this.endurance = this.maxEndurance;
        }
    }

    public synchronized void addEndurance(final int value) {
        if (this.endurance + value < 0) {
            this.endurance = 0;
        } else if (this.endurance + value > this.maxEndurance) {
            this.endurance = this.maxEndurance;
        } else {
            this.endurance += value;
        }
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public void setSlotIndex(final int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public EItemStorageLocation getStorageLocation() {
        return this.storageLocation;
    }

    public void setStorageLocation(final EItemStorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    public int getRegionId() {
        return this.regionId;
    }

    public void setRegionId(final int regionId) {
        this.regionId = regionId;
    }

    public boolean isVested() {
        return this.isVested;
    }

    public ItemTemplate getTemplate() {
        return this.template;
    }

    public int getItemId() {
        return this.template.getItemId();
    }

    public long getObjectId() {
        return this.objectId;
    }

    public void setObjectId(final long objectId) {
        this.objectId = objectId;
    }

    public int getEndurance() {
        return this.endurance;
    }

    public void setEndurance(final int endurance) {
        this.endurance = endurance;
    }

    public int getMaxEndurance() {
        return this.maxEndurance;
    }

    public void setMaxEndurance(final int maxEndurance) {
        this.maxEndurance = maxEndurance;
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(final long count) {
        this.count = count;
    }

    public int getEnchantLevel() {
        return this.enchantLevel;
    }

    public void setEnchantLevel(final int enchantLevel) {
        this.enchantLevel = enchantLevel;
    }

    public long getExpirationPeriod() {
        return this.expirationPeriod;
    }

    public void setExpirationPeriod(final long expirationPeriod) {
        this.expirationPeriod = expirationPeriod;
    }

    public boolean canUse() {
        return (this.expirationPeriod == -1L || this.getRemainingExpirationPeriod() != 0L) && (this.getTemplate().getEndurance() <= 0 || this.endurance != 0);
    }

    public long getRemainingExpirationPeriod() {
        final long diff = this.expirationPeriod - GameTimeService.getServerTimeInSecond();
        return (diff < 0L) ? 0L : diff;
    }

    public void setColorPalette(final int index, final DyeingItemT colorPalette) {
        if (this.colorPalettes == null) {
            this.colorPalettes = new DyeingItemT[12];
        }
        this.colorPalettes[index] = colorPalette;
    }

    public void setJewel(final int index, final Jewel jewel) {
        if (this.jewels == null) {
            this.jewels = new Jewel[6];
        }
        this.jewels[index] = jewel;
    }

    public void removeJewel(final int index) {
        if (this.jewels != null) {
            this.jewels[index] = null;
        }
    }

    public DyeingItemT[] getColorPalettes() {
        return this.colorPalettes;
    }

    public void setColorPalettes(final DyeingItemT[] colorPalettes) {
        this.colorPalettes = colorPalettes;
    }

    public Jewel[] getJewels() {
        return this.jewels;
    }

    public void setJewels(final Jewel[] jewels) {
        this.jewels = jewels;
    }

    public Jewel getJewelsByIndex(final int index) {
        return (this.jewels == null) ? null : this.jewels[index];
    }

    public DyeingItemT getColorPaletteByIndex(final int index) {
        return (this.colorPalettes == null) ? null : this.colorPalettes[index];
    }

    public ItemEnchantT getEnchantTemplate() {
        return this.template.getEnchantTemplate(this.enchantLevel);
    }

    public EItemClassify getItemClassify() {
        return this.template.getItemClassify();
    }

    public EItemType getItemType() {
        return this.template.getItemType();
    }

    public long getItemPrice() {
        return this.price;
    }

    public void setIsVested(final boolean isVested) {
        this.isVested = isVested;
    }

    public synchronized void upgradeAlchemyStone(final Playable owner, final long exp) {
        if (this.alchemyStoneExp + exp > 1500000L) {
            this.alchemyStoneExp = 1500000;
        }
        if (this.alchemyStoneExp + exp < 0L) {
            this.alchemyStoneExp = 0;
        } else {
            this.alchemyStoneExp += (int) exp;
        }
        owner.sendBroadcastItSelfPacket(new SMAlchemyUpgrade(owner.getGameObjectId(), this.alchemyStoneExp, this.slotIndex, this.storageLocation));
    }

    public boolean addCount(final long count) {
        if (this.count + count < 0L) {
            return false;
        }
        this.count += count;
        return true;
    }

    public List<ActiveBuff> getBuffs() {
        return this.buffs;
    }

    public void setBuffs(final List<ActiveBuff> buffs) {
        this.buffs = buffs;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("objectId", (Object) this.objectId);
        builder.append("itemId", (Object) this.getItemId());
        builder.append("regionId", (Object) this.regionId);
        builder.append("enchantLevel", (Object) this.enchantLevel);
        builder.append("count", (Object) this.count);
        builder.append("endurance", (Object) this.endurance);
        builder.append("maxEndurance", (Object) this.maxEndurance);
        builder.append("expirationPeriod", (Object) this.expirationPeriod);
        builder.append("isVested", (Object) this.isVested);
        builder.append("price", (Object) this.price);
        builder.append("alchemyStoneExp", (Object) this.alchemyStoneExp);
        builder.append("colorPaletteType", (Object) this.colorPaletteType);
        final BasicDBList jewelDBList = new BasicDBList();
        if (this.jewels != null) {
            for (int jewelKey = 0; jewelKey < this.jewels.length; ++jewelKey) {
                final Jewel jewel = this.jewels[jewelKey];
                if (jewel != null) {
                    final BasicDBObject jewelObj = new BasicDBObject();
                    jewelObj.put("index", (Object) jewelKey);
                    jewelObj.put("itemId", (Object) jewel.getJewelId());
                    jewelDBList.add((Object) jewelObj);
                }
            }
        }
        builder.append("jewels", (Object) jewelDBList);
        final BasicDBList dbList = new BasicDBList();
        if (this.colorPalettes != null) {
            for (int paletteKey = 0; paletteKey < this.colorPalettes.length; ++paletteKey) {
                final DyeingItemT dyeingItemT = this.colorPalettes[paletteKey];
                if (dyeingItemT != null) {
                    final BasicDBObject paletteObj = new BasicDBObject();
                    paletteObj.put("index", (Object) paletteKey);
                    paletteObj.put("itemId", (Object) dyeingItemT.getItemId());
                    dbList.add((Object) paletteObj);
                }
            }
        }
        builder.append("colorPalettes", (Object) dbList);
        return builder.get();
    }
}
