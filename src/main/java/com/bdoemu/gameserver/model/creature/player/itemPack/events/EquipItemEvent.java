//
// Decompiled by Procyon v0.5.30
//

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMEquipItem;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.enums.EEquipSlot;
import com.bdoemu.gameserver.model.creature.player.enums.EquipType;
import com.bdoemu.gameserver.model.creature.player.itemPack.ADBItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.CashInventory;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerBag;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerInventory;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.service.GameTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EquipItemEvent implements IBagEvent {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) EquipItemEvent.class);
    }

    private Player player;
    private Playable target;
    private int invSlot;
    private int equipSlot;
    private EItemStorageLocation storageType;
    private PlayerBag playerBag;
    private CashInventory cashInventory;
    private PlayerInventory inventory;
    private ADBItemPack pack;
    private Item nextItem;
    private UnequipItemTask switchItemTask;
    private ConcurrentLinkedQueue<UnequipItemTask> itemTasks;
    private long unk;

    public EquipItemEvent(final Player player, final Playable target, final int invSlot, final int equipSlot, final EItemStorageLocation storageType, final long unk) {
        this.itemTasks = new ConcurrentLinkedQueue<UnequipItemTask>();
        this.player = player;
        this.target = target;
        this.invSlot = invSlot;
        this.equipSlot = equipSlot;
        this.storageType = storageType;
        this.playerBag = player.getPlayerBag();
        this.cashInventory = this.playerBag.getCashInventory();
        this.inventory = this.playerBag.getInventory();
        this.unk = unk;
    }

    @Override
    public void onEvent() {
        for (final UnequipItemTask task : this.itemTasks) {
            if (task == this.switchItemTask) {
                continue;
            }
            final Item item = task.getItem();
            final ADBItemPack addItemPack = task.getLocationType().isInventory() ? this.inventory : this.cashInventory;
            for (int slotIndex = addItemPack.getDefaultSlotIndex(); slotIndex < addItemPack.getExpandSize(); ++slotIndex) {
                if (addItemPack.getItem(slotIndex) == null) {
                    this.target.getEquipments().removeItem(task.getEquipSlot());
                    this.target.getEquipments().unequip(item);
                    task.setUnequipSlot(slotIndex);
                    addItemPack.addItem(item, slotIndex);
                    item.setSlotIndex(slotIndex);
                    item.setStorageLocation(addItemPack.getLocationType());
                    break;
                }
            }
        }
        this.pack.removeItem(this.invSlot);
        if (this.switchItemTask != null) {
            final Item oldItem = this.switchItemTask.getItem();
            this.target.getEquipments().removeItem(this.switchItemTask.getEquipSlot());
            this.target.getEquipments().unequip(oldItem);
            this.pack.addItem(oldItem, this.switchItemTask.getUnequipSlot());
        }
        if (!this.nextItem.isVested() && this.nextItem.getTemplate().getVestedType().isOnEquip()) {
            this.nextItem.setIsVested(true);
        }
        this.nextItem.setSlotIndex(this.equipSlot);
        this.nextItem.setStorageLocation(this.target.getEquipments().getLocationType());
        this.target.getEquipments().addItem(this.nextItem, this.equipSlot);
        this.target.getEquipments().equip(this.nextItem);
        this.target.recalculateEquipSlotCacheCount();
        if (this.target.isPlayer()) {
            this.player.sendPacket(new SMSetCharacterStats(this.player));
            this.player.recalculateActionStorage();
        }
        this.player.sendPacket(new SMSetCharacterRelatedPoints(this.target, 0));
        this.target.sendBroadcastItSelfPacket(new SMSetCharacterPublicPoints(this.target, 0.0f));
        if (this.target.isVehicle()) {
            this.player.sendPacket(new SMSetMyServantPoints(this.player));
        }
        this.target.sendBroadcastItSelfPacket(new SMSetCharacterStatPoint(this.target));
        if (target.isPlayable())
            this.target.sendPacket(new SMSetCharacterSpeeds(this.target.getGameStats()));
        this.player.getObserveController().notifyObserver(EObserveType.useItem, this.nextItem.getItemId(), this.nextItem.getEnchantLevel());
        this.player.sendBroadcastItSelfPacket(new SMEquipItem(this.player, this.target, this.nextItem, this.invSlot, this.equipSlot, this.storageType, this.itemTasks, this.unk));
    }

    private void addUnequipItem(final Item uneqItem, final int eqSlot, final int invSlot) {
        if (uneqItem != null) {
            final UnequipItemTask task = new UnequipItemTask(uneqItem, eqSlot, uneqItem.getTemplate().isCash() ? EItemStorageLocation.CashInventory : EItemStorageLocation.Inventory);
            if (this.switchItemTask == null && this.pack.getLocationType() == task.getLocationType()) {
                task.setUnequipSlot(invSlot);
                this.switchItemTask = task;
            }
            this.itemTasks.add(task);
        }
    }

    @Override
    public boolean canAct() {
        if (this.player.hasTrade()) {
            return false;
        }
        this.pack = ((this.storageType == EItemStorageLocation.Inventory) ? this.playerBag.getInventory() : this.playerBag.getCashInventory());
        if (this.pack == null || this.invSlot > this.pack.getExpandSize()) {
            return false;
        }
        if (this.invSlot < this.pack.getDefaultSlotIndex() || this.equipSlot < 0) {
            return false;
        }
        final EEquipSlot equipSlotType = EEquipSlot.valueOf(this.equipSlot);
        if (equipSlotType == null) {
            return false;
        }
        this.nextItem = this.pack.getItem(this.invSlot);
        if (this.nextItem == null || !this.nextItem.getTemplate().getItemType().isEquip()) {
            return false;
        }
        if (this.nextItem.getTemplate().getEndurance() > 0 && this.nextItem.getEndurance() == 0) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoItemIsBroken, CMEquipItem.class));
            return false;
        }
        if (this.nextItem.getExpirationPeriod() > 0L && this.nextItem.getExpirationPeriod() < GameTimeService.getServerTimeInSecond()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoItemOverExpirationPeriod, CMEquipItem.class));
            return false;
        }
        final ItemTemplate nextItemTemplate = this.nextItem.getTemplate();
        if (this.target.isPlayer()) {
            if (!nextItemTemplate.canUse(((Player) this.target).getClassType())) {
                return false;
            }
        } else if (this.target.isVehicle() && !nextItemTemplate.canUse(((Servant) this.target).getServantSetTemplate().getServantKind())) {
            return false;
        }
        if (nextItemTemplate.getLifeMinLevel() > 0 && this.player.getLifeExperienceStorage().getLifeExperience(nextItemTemplate.getLifeExpType()).getLevel() < nextItemTemplate.getLifeMinLevel()) {
            switch (nextItemTemplate.getLifeExpType()) {
                case Gather: {
                    this.player.sendPacket(new SMNak(EStringTable.eErrNoItemCollectLevelIsRestricted, CMEquipItem.class));
                    break;
                }
                case Trading: {
                    this.player.sendPacket(new SMNak(EStringTable.eErrNoItemTradeLevelIsRestricted, CMEquipItem.class));
                    break;
                }
                case Fishing: {
                    this.player.sendPacket(new SMNak(EStringTable.eErrNoItemFishingLevelIsRestricted, CMEquipItem.class));
                    break;
                }
                case Taming: {
                    this.player.sendPacket(new SMNak(EStringTable.eErrNoItemTrainingLevelIsRestricted, CMEquipItem.class));
                    break;
                }
                case Alchemy: {
                    this.player.sendPacket(new SMNak(EStringTable.eErrNoItemAlchemyLevelIsRestricted, CMEquipItem.class));
                    break;
                }
                case Cook: {
                    this.player.sendPacket(new SMNak(EStringTable.eErrNoItemCookingLevelIsRestricted, CMEquipItem.class));
                    break;
                }
                case Process: {
                    this.player.sendPacket(new SMNak(EStringTable.eErrNoItemManufactureLevelIsRestricted, CMEquipItem.class));
                    break;
                }
                case Hunting: {
                    this.player.sendPacket(new SMNak(EStringTable.eErrNoItemHuntingLevelIsRestricted, CMEquipItem.class));
                    break;
                }
                case Farming: {
                    this.player.sendPacket(new SMNak(EStringTable.eErrNoItemHarvestLevelIsRestricted, CMEquipItem.class));
                    break;
                }
            }
            return false;
        }
        final Item oldItem = this.target.getEquipments().getItem(this.equipSlot);
        this.addUnequipItem(oldItem, this.equipSlot, this.invSlot);
        final List<EquipType> occupiedEquipNoList = nextItemTemplate.getOccupiedEquipNoList();
        for (final Integer slotId : this.target.getEquipments().getItemMap().keySet()) {
            final Item equipedItem = this.target.getEquipments().getItem(slotId);
            if (equipedItem == oldItem) {
                continue;
            }
            final List<EquipType> _occupiedEquipNoList = equipedItem.getTemplate().getOccupiedEquipNoList();
            if (_occupiedEquipNoList != null && _occupiedEquipNoList.contains(nextItemTemplate.getEquipType())) {
                this.addUnequipItem(equipedItem, slotId, this.invSlot);
            } else {
                if (occupiedEquipNoList == null || !occupiedEquipNoList.contains(equipedItem.getTemplate().getEquipType())) {
                    continue;
                }
                this.addUnequipItem(equipedItem, slotId, this.invSlot);
            }
        }
        int cashUnequipCount = 0;
        int invUnequipCount = 0;
        for (final UnequipItemTask task : this.itemTasks) {
            if (task == this.switchItemTask) {
                continue;
            }
            if (task.getLocationType().isInventory()) {
                ++invUnequipCount;
            } else {
                ++cashUnequipCount;
            }
        }
        if (cashUnequipCount > 0 && cashUnequipCount > this.cashInventory.freeSlotCount()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoInventoryNotExistFreeSlot, CMEquipItem.class));
            return false;
        }
        if (invUnequipCount > 0 && invUnequipCount > this.inventory.freeSlotCount()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoInventoryNotExistFreeSlot, CMEquipItem.class));
            return false;
        }
        final EquipType nextEquipType = nextItemTemplate.getEquipType();
        return nextEquipType != null && nextEquipType.canEquip(equipSlotType);
    }
}
