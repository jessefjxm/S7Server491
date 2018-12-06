// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMUpdateExplorePoint;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.contribution.ExplorePoint;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;

public class RestoreItemByPointEvent extends AItemEvent {
    private int itemId;
    private int enchantLevel;
    private Integer needContribute;
    private ExplorePoint explorePoint;

    public RestoreItemByPointEvent(final Player player, final int itemId, final int enchantLevel) {
        super(player, player, player, EStringTable.eErrNoScriptReward, EStringTable.eErrNoScriptReward, 0);
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.explorePoint = player.getExplorePointHandler().getMainExplorePoint();
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMUpdateExplorePoint(this.explorePoint));
    }

    @Override
    public boolean canAct() {
        final ItemTemplate template = ItemData.getInstance().getItemTemplate(this.itemId);
        this.needContribute = template.getNeedContribute();
        if (this.needContribute == null) {
            return false;
        }
        final ItemPack pack = template.isCash() ? this.cashInventory : this.playerInventory;
        Integer slotIndex = null;
        for (final int index : pack.getItemMap().keySet()) {
            final Item item = pack.getItem(index);
            if (item.getItemId() == this.itemId && item.getEnchantLevel() == this.enchantLevel) {
                if (item.getCount() < 1L) {
                    continue;
                }
                slotIndex = index;
                break;
            }
        }
        if (slotIndex == null) {
            return false;
        }
        this.decreaseItem(slotIndex, 1L, pack.getLocationType());
        return super.canAct() && this.explorePoint.addPoints(this.needContribute);
    }
}
