// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.dataholders.InstantCashItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EInstantCashType;

public class InstantCashItemEvent extends AItemEvent {
    private int remainingTime;
    private EInstantCashType instantCashType;
    private Integer perlCount;
    private long count;
    private Class<? extends ReceivablePacket> callerClass;

    public InstantCashItemEvent(final Player player, final EInstantCashType instantCashType, final int remainingTime, final long count, final Class<? extends ReceivablePacket> callerClass) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.remainingTime = remainingTime;
        this.instantCashType = instantCashType;
        this.count = count;
        this.callerClass = callerClass;
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        final Item item = this.cashInventory.getItem(0);
        if (item == null) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoItemNotExist, this.callerClass));
            return false;
        }
        this.perlCount = InstantCashItemData.getInstance().getPearlCountForInstantEnd(this.instantCashType, this.remainingTime);
        if (this.perlCount == null) {
            return false;
        }
        if (this.count > 0L) {
            this.perlCount = (int) this.count;
        }
        if (item.getCount() < this.perlCount) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoItemNotExist, this.callerClass));
            return false;
        }
        this.decreaseItem(0, this.perlCount, EItemStorageLocation.CashInventory);
        return super.canAct();
    }
}
