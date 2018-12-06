// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.EtcOptionConfig;
import com.bdoemu.core.network.receivable.CMDetectCharacter;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class DetectCharacterItemEvent extends AItemEvent {
    public DetectCharacterItemEvent(final Player player) {
        super(player, player, player, EStringTable.eErrNoDetectCharacter, EStringTable.eErrNoDetectCharacter, 0);
    }

    @Override
    public void onEvent() {
        super.onEvent();
    }

    @Override
    public boolean canAct() {
        final Item money = this.playerInventory.getItem(0);
        if (money == null || money.getCount() < EtcOptionConfig.DETECT_PLAYER_PRICE) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoItemNotExist, CMDetectCharacter.class));
            return false;
        }
        this.decreaseItem(0, EtcOptionConfig.DETECT_PLAYER_PRICE, EItemStorageLocation.Inventory);
        return super.canAct();
    }
}
