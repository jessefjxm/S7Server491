package com.bdoemu.gameserver.model.creature.player.itemPack;

import com.bdoemu.gameserver.dataholders.EquipSetOptionData;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.enums.EEquipSlot;
import com.bdoemu.gameserver.model.items.EquipSetHandler;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.Jewel;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.EquipSetOptionT;
import com.bdoemu.gameserver.model.items.templates.ItemEnchantT;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.stats.Stat;
import com.bdoemu.gameserver.model.stats.elements.Element;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;
import com.mongodb.BasicDBObject;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class EquipmentsBag extends ADBItemPack {
    private final ConcurrentHashMap<Integer, EquipSetHandler> equipSetBuffs;

    public EquipmentsBag(final EItemStorageLocation locationType, final BasicDBObject dbObject, final Playable playable) {
        super(locationType, dbObject, playable);
        this.equipSetBuffs = new ConcurrentHashMap<>();
    }

    public EquipmentsBag(final EItemStorageLocation locationType, final Stat expandStat, final Playable playable) {
        super(locationType, expandStat, playable);
        this.equipSetBuffs = new ConcurrentHashMap<>();
    }

    public void equipItems() {
        this.getItemList().forEach(this::equip);
    }

    public void unequip(final Item item) {
        final ItemEnchantT itemEnchantT = item.getEnchantTemplate();
        if (itemEnchantT != null) {
            for (final StatEnum type : itemEnchantT.getStats().keySet()) {
                if (!canAddStats(item, type))
                    continue;

                final Element element = itemEnchantT.getStats().get(type);
                final Stat stat = this.getOwner().getGameStats().getStat(type);
                stat.removeElement(element);
            }
            final List<ActiveBuff> buffs = item.getBuffs();
            if (buffs != null) {
                buffs.forEach(ActiveBuff::endEffect);
                item.setBuffs(null);
            }
        }
        final Jewel[] jewels = item.getJewels();
        if (jewels != null) {
            for (final Jewel jewel : jewels) {
                if (jewel != null) {
                    jewel.endEffects();
                }
            }
        }
        final EquipSetOptionT equipSetOptionT = EquipSetOptionData.getInstance().getTemplate(item.getItemId());
        if (equipSetOptionT != null) {
            final EquipSetHandler handler = this.equipSetBuffs.get(equipSetOptionT.getIndex());
            if (handler != null) {
                handler.endEffects();
                if (handler.getItemSize() <= 0) {
                    this.equipSetBuffs.values().remove(handler);
                }
            }
        }
    }

    // we want to disable left and right hand AP bonus
    private boolean canAddStats(final Item item, final StatEnum type) {
        if (type == StatEnum.DDD || type == StatEnum.RDD || type == StatEnum.MDD)
        {
            EEquipSlot slot = item.getTemplate().getEquipType().getDefaultSlot();
            if (slot != null && (slot == EEquipSlot.rightHand || slot == EEquipSlot.awakenWeapon))
                return false;
        }
        return true;
    }

    public void equip(final Item item) {
        final ItemEnchantT itemEnchantT = item.getEnchantTemplate();
        if (itemEnchantT != null) {
            for (final StatEnum type : itemEnchantT.getStats().keySet()) {
                if (!canAddStats(item, type))
                    continue;

                final Element element = itemEnchantT.getStats().get(type);
                final Stat stat = this.getOwner().getGameStats().getStat(type);
                stat.addElement(element);
            }
        }
        final Jewel[] jewels = item.getJewels();
        if (jewels != null) {
            for (final Jewel jewel : jewels) {
                if (jewel != null) {
                    jewel.applyEffects(this.getOwner());
                }
            }
        }
        final EquipSetOptionT equipSetOptionT = EquipSetOptionData.getInstance().getTemplate(item.getItemId());
        if (equipSetOptionT != null) {
            final EquipSetHandler handler = this.equipSetBuffs.computeIfAbsent(equipSetOptionT.getIndex(), m -> new EquipSetHandler(equipSetOptionT));
            handler.applyEffects(this.getOwner());
        }
    }
}