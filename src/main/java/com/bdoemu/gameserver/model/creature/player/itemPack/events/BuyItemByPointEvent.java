// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMUpdateExplorePoint;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.contribution.ExplorePoint;

public class BuyItemByPointEvent extends AItemEvent {
    private final int itemId;
    private final int enchantLevel;
    private final int type;
    private final int points;
    private final long count;
    private final ExplorePoint explorePoint;

    public BuyItemByPointEvent(final Player player, final int itemId, final int enchantLevel, final int type, final int points, final long count) {
        super(player, player, player, EStringTable.eErrNoScriptReward, EStringTable.eErrNoScriptReward, 0);
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.type = type;
        this.points = points;
        this.count = count;
        this.explorePoint = player.getExplorePointHandler().getMainExplorePoint();
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMUpdateExplorePoint(this.explorePoint));
    }

    @Override
    public boolean canAct() {
        this.addItem(this.itemId, this.count, this.enchantLevel);
        return super.canAct() && this.explorePoint.addPoints(-this.points);
    }
}
