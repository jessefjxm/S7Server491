// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class BuyHouseItemEvent extends AItemEvent {
    private int needExplorePoint;
    private Integer itemId;
    private Long count;

    public BuyHouseItemEvent(final Player player, final int needExplorePoint, final Integer itemId, final Long count) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.needExplorePoint = needExplorePoint;
        this.itemId = itemId;
        this.count = count;
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        if (this.itemId != null) {
            this.decreaseItem(0, this.count, EItemStorageLocation.Inventory);
        }
        return super.canAct() && (this.needExplorePoint <= 0 || this.player.getExplorePointHandler().getMainExplorePoint().addPoints(-this.needExplorePoint));
    }
}
