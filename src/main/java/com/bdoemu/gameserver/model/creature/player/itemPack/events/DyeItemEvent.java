// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.core.network.sendable.SMDyeItem;
import com.bdoemu.gameserver.dataholders.DyeingItemData;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerBag;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.items.templates.DyeingItemT;

public class DyeItemEvent implements IBagEvent {
    private int slotIndex;
    private int itemDyePart;
    private int itemDyeId;
    private int type;
    private Playable target;
    private Item mainItem;
    private DyeingItemT dyeingItemT;
    private EItemStorageLocation storageType;
    private Player player;
    private PlayerBag playerBag;

    public DyeItemEvent(final Player player, final int slotIndex, final int itemDyePart, final int itemDyeId, final EItemStorageLocation storageType, final Playable target, final int type) {
        this.player = player;
        this.slotIndex = slotIndex;
        this.itemDyePart = itemDyePart;
        this.itemDyeId = itemDyeId;
        this.storageType = storageType;
        this.target = target;
        this.playerBag = player.getPlayerBag();
        this.type = type;
    }

    @Override
    public void onEvent() {
        this.target.recalculateEquipSlotCacheCount();
        this.mainItem.setColorPaletteType(this.type);
        this.mainItem.setColorPalette(this.itemDyePart, this.dyeingItemT);
        this.player.sendBroadcastItSelfPacket(new SMDyeItem(this.target, this.mainItem, this.itemDyePart, this.dyeingItemT));
    }

    @Override
    public boolean canAct() {
        if (this.itemDyePart < 0 || this.itemDyePart > 11 || (!this.storageType.isPlayerInventories() && !this.storageType.isEquipments() && !this.storageType.isServantEquip())) {
            return false;
        }
        final ItemPack itemPack = this.playerBag.getItemPack(this.storageType, this.target.getGameObjectId(), 0);
        this.mainItem = itemPack.getItem(this.slotIndex);
        if (this.mainItem == null || !this.mainItem.getTemplate().isDyeable()) {
            return false;
        }
        this.dyeingItemT = DyeingItemData.getInstance().getTemplate(this.itemDyeId);
        return this.dyeingItemT != null && (this.type == 1 || this.player.getDyeStorage().removeDye(this.itemDyeId, 1L));
    }
}
