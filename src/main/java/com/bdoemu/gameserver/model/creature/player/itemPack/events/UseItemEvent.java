package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.HashUtil;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.core.network.receivable.CMUseItem;
import com.bdoemu.core.network.sendable.SMAskUseItem;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMUseItemNak;
import com.bdoemu.gameserver.dataholders.SkillData;
import com.bdoemu.gameserver.model.actions.ActionStorage;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collections;
import java.util.List;

public class UseItemEvent extends AItemEvent {
    private int slotIndex;
    private EItemStorageLocation storageType;
    private ItemPack pack;
    private Item item;
    private SkillT template;
    private Creature target;

    public UseItemEvent(final Player player, final int slotIndex, final EItemStorageLocation storageType, final Creature target) {
        super(player, player, player, EStringTable.eErrNoServantCantUpdateToMemory, EStringTable.eErrNoServantCantUpdateToMemory, 0);
        this.slotIndex = slotIndex;
        this.storageType = storageType;
        this.target = target;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        if (ServantConfig.TAMING_ITEM_KEY == this.item.getItemId() || (ServantConfig.ELEPHANT_TAMING_ITEM_KEY == this.item.getItemId() && this.target.isVehicle()))
            ((Servant) this.target).setTamingSugar(true);
        else
            SkillService.useSkill(this.player, this.template, null, Collections.singletonList(this.target));

        final String actionName = this.template.getActionName();
        if (actionName != null) {
            final long hash = HashUtil.generateHashA(actionName);
            final ActionStorage actionStorage = this.player.getActionStorage();
            if (actionStorage.getCurrentPackageMap() != null) {
                final IAction action = actionStorage.getNewAction(hash);
                if (action != null) {
                    action.setOwner(this.player);
                    action.setNewLocation(this.player.getLocation());
                    action.setType((byte) 1);
                    action.setCallAction(true);
                    action.setSkillLevel(this.template.getLevel());
                    action.setSkillId(this.template.getSkillId());
                    this.player.setAction(action);
                }
            }
        }
        this.player.getObserveController().notifyObserver(EObserveType.useItem, this.item.getItemId(), this.item.getEnchantLevel());
    }

    @Override
    public boolean canAct() {
        this.pack = this.playerBag.getItemPack(this.storageType);
        if (this.pack == null || this.slotIndex > this.pack.getExpandSize()) {
            return false;
        }
        if (this.slotIndex < this.pack.getDefaultSlotIndex()) {
            return false;
        }
        this.item = this.pack.getItem(this.slotIndex);
        if (this.item == null) {
            return false;
        }
        final ItemTemplate itemTemplate = this.item.getTemplate();
        final int minLevel = itemTemplate.getMinLevel();
        final int maxLevel = itemTemplate.getMaxLevel();
        if ((minLevel > 0 && minLevel > this.player.getLevel()) || (maxLevel > 0 && maxLevel < this.player.getLevel())) {
            return false;
        }
        if (!ConditionService.checkCondition(itemTemplate.getUseConditions(), this.player)) {
            this.player.sendPacket(new SMUseItemNak(this.storageType, this.slotIndex, EStringTable.eErrNoItemNotUseCondition));
            return false;
        }
        final EContentsEventType eventType = itemTemplate.getContentsEventType();
        if (eventType != null &&(eventType == EContentsEventType.ConvertEnchantFailCountToItem
                || eventType == EContentsEventType.ConvertEnchantFailItemToCount)) {
            this.player.sendPacket(new SMUseItemNak(this.storageType, this.slotIndex, EStringTable.eErrNoDevelopLog));
            return false;
        }

        if (eventType == null || !eventType.isSummonCharacter()) {
            if (!this.item.getTemplate().isTargetAlive() && this.target.isPlayer() && this.target != this.player) {
                this.target.sendPacket(new SMAskUseItem(this.storageType, this.slotIndex, this.player.getGameObjectId()));
                return false;
            }

            switch (itemTemplate.getItemType()) {
                case Skill: {
                    if (this.target != this.player) {
                        return false;
                    }
                    break;
                }
                case Equip: {
                    if (itemTemplate.getEquipType().isAlchemyStone()) {
                        int coolTime = 0;
                        int alchemyStoneType = itemTemplate.getContentsEventParam1();
                        if (0 == alchemyStoneType || 3 == alchemyStoneType)
                            coolTime = 182;
                        else if (1 == alchemyStoneType || 4 == alchemyStoneType)
                            coolTime = 182;
                        else if (2 == alchemyStoneType || 5 == alchemyStoneType)
                            coolTime = 602;

                        if (player.getLastAlchemyStoneUsedTime() + coolTime > GameTimeService.getServerTimeInSecond()) {
                            player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoSkillIsGroupReuseCycle, CMUseItem.class));
                            return false;
                        } else
                            player.setLastUsedAlchemyStoneTime((int) GameTimeService.getServerTimeInSecond());
                    }
                    break;
                }
                case ToVehicle: {
                    break;
                }
                default: {
                    return false;
                }
            }
        }
        if (this.item.getTemplate().isTargetAlive() && this.target.isDead()) {
            return false;
        }
        this.template = SkillData.getInstance().getTemplate(this.item.getTemplate().getSkillId());
        if (itemTemplate.isTargetAlive() && itemTemplate.isRemovable()) {
            this.decreaseItem(this.slotIndex, 1L, this.storageType);
        }
        if (this.template != null && this.template.getSkillId() == 50314 && this.player.getServantController().getTameServant() != null) {
            this.player.sendPacket(new SMUseItemNak(this.storageType, this.slotIndex, EStringTable.eErrNoServantTamingExist));
            return false;
        }
        return super.canAct() && this.template != null && !this.player.getCoolTimeList().hasItemCoolTime(this.template.getReuseGroup());
    }
}
