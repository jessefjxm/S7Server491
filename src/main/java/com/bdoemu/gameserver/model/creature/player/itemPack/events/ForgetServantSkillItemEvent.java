// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMForgetServantSkill;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class ForgetServantSkillItemEvent extends AItemEvent {
    private int skillId;
    private int slotIndex;
    private EItemStorageLocation storageLocation;
    private Servant servant;

    public ForgetServantSkillItemEvent(final Player player, final Servant servant, final EItemStorageLocation storageLocation, final int slotIndex, final int skillId) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.servant = servant;
        this.storageLocation = storageLocation;
        this.slotIndex = slotIndex;
        this.skillId = skillId;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.player.sendPacket(new SMForgetServantSkill(this.servant.getObjectId(), this.skillId));
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isPlayerInventories()) {
            return false;
        }
        final ItemPack itemPack = this.playerBag.getItemPack(this.storageLocation);
        final Item item = itemPack.getItem(this.slotIndex);
        if (item == null) {
            return false;
        }
        final EContentsEventType contentsEventType = item.getTemplate().getContentsEventType();
        if (contentsEventType == null || !contentsEventType.isForgetServantSkill()) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.storageLocation);
        return super.canAct() && !this.servant.getServantSkillList().isCannotChange(this.skillId) && this.servant.getServantSkillList().removeSkill(this.skillId) != null;
    }
}
