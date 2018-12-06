package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.EtcOptionConfig;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class RespawnPlayerItemEvent extends AItemEvent {
    private int slotIndex;
    private EItemStorageLocation storageLocation;
    private int hpPercentage;
    private int mpPercentage;

    public RespawnPlayerItemEvent(final Player player, final EItemStorageLocation storageLocation, final int slotIndex) {
        super(player, player, player, EStringTable.eErrNoItemUseRevivalItemWell, EStringTable.eErrNoItemUseRevivalItemWell, 0);
        this.hpPercentage = 10;
        this.mpPercentage = 0;
        this.slotIndex = slotIndex;
        this.storageLocation = storageLocation;
    }

    @Override
    public void onEvent() {
        this.player.getAggroList().clear(true);
        super.onEvent();
        this.player.getAi().notifyStart();
        this.player.revive(this.hpPercentage, this.mpPercentage);
        this.player.addExpRaw(this.player.getLostExperienceOnDeath());
        this.player.setLostExperienceOnDeath(0);
    }

    @Override
    public boolean canAct() {
        if (this.player.getLevel() > EtcOptionConfig.FREE_REVIVAL_LEVEL) {
            if (!this.storageLocation.isPlayerInventories()) {
                return false;
            }
            final ItemPack itemPack = this.playerBag.getItemPack(this.storageLocation);
            if (itemPack == null) {
                return false;
            }
            final Item item = itemPack.getItem(this.slotIndex);
            if (item == null) {
                return false;
            }
            final EContentsEventType contentsEventType = item.getTemplate().getContentsEventType();
            if (contentsEventType == null || !contentsEventType.isRespawn()) {
                return false;
            }
            this.hpPercentage = item.getTemplate().getContentsEventParam1() / 10000;
            this.mpPercentage = item.getTemplate().getContentsEventParam2() / 10000;
            this.decreaseItem(this.slotIndex, 1L, this.storageLocation);
        }
        return super.canAct();
    }
}
