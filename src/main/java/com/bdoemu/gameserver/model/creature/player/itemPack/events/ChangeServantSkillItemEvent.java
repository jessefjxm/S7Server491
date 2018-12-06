// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMChangeServantSkill;
import com.bdoemu.gameserver.dataholders.VehicleSkillData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.skills.ServantSkill;
import com.bdoemu.gameserver.model.skills.templates.VehicleSkillOwnerT;

public class ChangeServantSkillItemEvent extends AItemEvent {
    private int removedSkillId;
    private int hopeSkillId;
    private int slotIndex;
    private EItemStorageLocation storageLocation;
    private Servant servant;

    public ChangeServantSkillItemEvent(final Player player, final Servant servant, final EItemStorageLocation storageLocation, final int slotIndex, final int removedSkillId, final int hopeSkillId) {
        super(player, player, player, EStringTable.eErrNoItemIsRemovedToServantChangeSkill, EStringTable.eErrNoItemIsRemovedToServantChangeSkill, 0);
        this.servant = servant;
        this.storageLocation = storageLocation;
        this.slotIndex = slotIndex;
        this.removedSkillId = removedSkillId;
        this.hopeSkillId = hopeSkillId;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        final ServantSkill servantSkill = this.servant.getServantSkillList().changeSkill(this.hopeSkillId);
        this.servant.getServantSkillList().removeSkill(this.removedSkillId);
        if (servantSkill.getSkillId() == this.hopeSkillId) {
            this.servant.setHope(0);
        } else {
            this.servant.setHope(this.servant.getHope() + 1);
        }
        this.player.sendPacket(new SMChangeServantSkill(this.servant, this.removedSkillId, servantSkill.getSkillId()));
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
        if (contentsEventType == null || !contentsEventType.isChangeServantSkill()) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.storageLocation);
        final VehicleSkillOwnerT vehicleSkillOwnerT = VehicleSkillData.getInstance().getTemplate(this.servant.getCreatureId());
        return vehicleSkillOwnerT != null && this.hopeSkillId >= 1 && this.hopeSkillId <= vehicleSkillOwnerT.getIsLearn().length && vehicleSkillOwnerT.getIsLearn()[this.hopeSkillId - 1] != 0 && super.canAct() && this.servant.getServantSkillList().containsSkill(this.removedSkillId);
    }
}
