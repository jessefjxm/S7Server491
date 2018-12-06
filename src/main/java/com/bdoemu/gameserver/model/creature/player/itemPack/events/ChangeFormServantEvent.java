// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.core.network.sendable.SMChangeFormServant;
import com.bdoemu.core.network.sendable.SMSetServantSkillExp;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.skills.ServantSkill;

public class ChangeFormServantEvent extends AItemEvent {
    private int formIndex;
    private EItemStorageLocation storageLocation;
    private int slotIndex;
    private Servant servant;
    private Integer eventType;

    public ChangeFormServantEvent(final Player player, final Servant servant, final int formIndex, final EItemStorageLocation storageLocation, final int slotIndex) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.servant = servant;
        this.formIndex = formIndex;
        this.storageLocation = storageLocation;
        this.slotIndex = slotIndex;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.servant.setFormIndex(this.formIndex);
        if (this.eventType != null && this.eventType == 1) {
            if (Rnd.get(1000000) <= ServantConfig.VEHICLE_CHANGE_FORM_WITH_SKILL_RATE) {
                final ServantSkill servantSkill = this.servant.getServantSkillList().learnRndSkill(true);
                if (servantSkill != null) {
                    this.player.sendPacket(new SMSetServantSkillExp(this.servant.getObjectId(), servantSkill));
                }
            } else {
                this.servant.addExp(ServantConfig.VEHICLE_CHANGE_FORM_WITH_SKILL_VARY_EXPERIENCE);
            }
        }
        this.player.sendPacket(new SMChangeFormServant(this.servant));
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isPlayerInventories()) {
            return false;
        }
        final ItemPack pack = this.playerBag.getItemPack(this.storageLocation);
        final Item item = pack.getItem(this.slotIndex);
        if (item == null) {
            return false;
        }
        final EContentsEventType contentsEventType = item.getTemplate().getContentsEventType();
        if (contentsEventType == null || !contentsEventType.isChangeFormServant()) {
            return false;
        }
        this.eventType = item.getTemplate().getContentsEventParam1();
        this.decreaseItem(this.slotIndex, 1L, this.storageLocation);
        return super.canAct();
    }
}
