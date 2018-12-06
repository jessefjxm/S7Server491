// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.core.network.sendable.SMStartServantSkillExpTraining;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.skills.ServantSkill;
import com.bdoemu.gameserver.service.GameTimeService;

public class StartServantSkillExpTrainingItemEvent extends AItemEvent {
    private int skillId;
    private int slotIndex;
    private EItemStorageLocation storageLocation;
    private Servant servant;
    private ServantSkill servantSkill;

    public StartServantSkillExpTrainingItemEvent(final Player player, final Servant servant, final EItemStorageLocation storageLocation, final int slotIndex, final int skillId) {
        super(player, player, player, EStringTable.eErrNoItemIsRemovedToSkillExpTrainingServant, EStringTable.eErrNoItemIsRemovedToSkillExpTrainingServant, 0);
        this.servant = servant;
        this.storageLocation = storageLocation;
        this.slotIndex = slotIndex;
        this.skillId = skillId;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.servant.setSkillTrainingTime(GameTimeService.getServerTimeInSecond() + ServantConfig.SKILL_EXP_TRAINING_TIME / 1000);
        this.servant.setServantState(EServantState.SkillTraining);
        this.servantSkill.addExp(this.servant, this.servantSkill.getVehicleSkillT().getMaxExp());
        this.player.sendPacket(new SMStartServantSkillExpTraining(this.servant, this.skillId));
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
        if (contentsEventType == null || !contentsEventType.isServantSkillExpTraining()) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.storageLocation);
        this.servantSkill = this.servant.getServantSkillList().getSkill(this.skillId);
        return super.canAct() && this.servantSkill != null;
    }
}
