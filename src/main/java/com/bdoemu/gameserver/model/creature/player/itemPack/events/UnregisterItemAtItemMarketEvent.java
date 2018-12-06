package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.ItemMarketConfig;
import com.bdoemu.core.network.receivable.CMUnregisterItemAtItemMarket;
import com.bdoemu.core.network.sendable.SMListItemHeaderAtItemMarket;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMNotifyItemMarketInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.ItemMarket;
import com.bdoemu.gameserver.model.items.MasterItemMarket;
import com.bdoemu.gameserver.model.items.services.ItemMarketService;
import com.bdoemu.gameserver.model.misc.enums.ENotifyItemMarketInfoType;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.service.ShutdownService;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Collections;

public class UnregisterItemAtItemMarketEvent extends AItemEvent {
    private long objectId;
    private ItemMarket itemMarket;
    private MasterItemMarket masterItemMarket;
    private int territoryKey;

    public UnregisterItemAtItemMarketEvent(final Player player, final long objectId, final int territoryKey) {
        super(player, player, player, EStringTable.eErrNoItemIsCreatedOtPopFromAuction, EStringTable.eErrNoItemIsCreatedOtPopFromAuction, 0);
        this.objectId = objectId;
        this.territoryKey = territoryKey;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMNotifyItemMarketInfo(this.itemMarket, ENotifyItemMarketInfoType.UNREGISTER));
        World.getInstance().broadcastWorldPacket(new SMListItemHeaderAtItemMarket(Collections.singletonList(this.masterItemMarket), EPacketTaskType.Update));
    }

    @Override
    public boolean canAct() {
        if (ShutdownService.getInstance().isShutdownInProgress()) {
            player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoFailedUpdateCharacterInformation, CMUnregisterItemAtItemMarket.class));
            return false;
        }
        this.itemMarket = ItemMarketService.getInstance().getItem(this.objectId);
        if (this.itemMarket == null) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoItemNotExist, CMUnregisterItemAtItemMarket.class));
            return false;
        }
        if (this.itemMarket.getItemPrice() >= 3000000L && this.itemMarket.getRegisteredDate() + ItemMarketConfig.UNREGISTER_RESTRICTED_TICK / 1000L > GameTimeService.getServerTimeInSecond()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoItemUnregisterRestrictAtItemMarket, CMUnregisterItemAtItemMarket.class));
            return false;
        }
        if (this.itemMarket.getTerritoryKey() != this.territoryKey || this.itemMarket.getAccountId() != this.player.getAccountId()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoTerritoryKeyNotInvalid, CMUnregisterItemAtItemMarket.class));
            return false;
        }
        this.masterItemMarket = ItemMarketService.getInstance().getMasterItemMarket(this.itemMarket.getItemId(), this.itemMarket.getEnchantLevel());
        this.addItem(new Item(this.itemMarket.getItem(), this.itemMarket.getCount()));
        if (!super.canAct()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoItemInvalidCount, CMUnregisterItemAtItemMarket.class));
            return false;
        }
        final boolean isWaitingUnregistred = ItemMarketService.getInstance().removeWaitingItem(this.itemMarket);
        final boolean isMasterItemUnregistred = this.masterItemMarket.unregisterItem(this.itemMarket);
        if (!this.itemMarket.isExpired() && !isWaitingUnregistred && !isMasterItemUnregistred) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoItemNotExist, CMUnregisterItemAtItemMarket.class));
            return false;
        }
        return ItemMarketService.getInstance().removeItem(this.objectId);
    }
}
