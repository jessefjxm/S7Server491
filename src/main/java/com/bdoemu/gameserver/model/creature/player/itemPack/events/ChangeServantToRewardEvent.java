// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMChangeServantToReward;
import com.bdoemu.core.network.sendable.SMChangeServantToReward;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.player.itemPack.ServantEquipments;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.misc.enums.EServantToRewardType;

public class ChangeServantToRewardEvent extends AItemEvent {
    private Servant servant;
    private long servantObjectId;
    private EServantToRewardType servantToRewardType;
    private Npc npc;

    public ChangeServantToRewardEvent(final Player player, final long servantObjectId, final EServantToRewardType servantToRewardType, final Npc npc) {
        super(player, player, player, EStringTable.eErrNoServantMatingGetMoney, EStringTable.eErrNoServantMatingGetMoney, npc.getRegionId());
        this.servantObjectId = servantObjectId;
        this.servantToRewardType = servantToRewardType;
        this.npc = npc;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        if (this.servantToRewardType == EServantToRewardType.Common) {
            this.player.addExp(1745 * this.servant.getLevel());
        }
        this.player.sendPacket(new SMChangeServantToReward(this.servant));
    }

    @Override
    public boolean canAct() {
        this.servant = this.player.getServantController().getServant(this.servantObjectId);
        if (this.servant == null || this.servant.getRegionId() != this.npc.getRegionId() || this.servant.getServantState() != EServantState.Stable) {
            return false;
        }
        if (!this.servant.getLinkedServants().isEmpty()) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoServantLink, CMChangeServantToReward.class));
            return false;
        }
        final ServantEquipments equipments = this.servant.getEquipments();
        final ItemPack inventory = this.servant.getInventory();
        if ((equipments != null && !equipments.getItemMap().isEmpty()) || (inventory != null && !inventory.getItemMap().isEmpty())) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoExistItemInVehicle, CMChangeServantToReward.class));
            return false;
        }
        float rate = 1.0f;
        final int tir = this.servant.getServantSetTemplate().getTier();
        switch (tir) {
            case 0: {
                rate = 0.5f;
                break;
            }
            case 1: {
                rate = 2.0f;
                break;
            }
            case 2: {
                rate = 2.0f;
                break;
            }
            case 3: {
                rate = 1.0f;
                break;
            }
            case 4: {
                rate = 0.8f;
                break;
            }
            case 5: {
                rate = 0.7f;
                break;
            }
            case 6: {
                rate = 0.6f;
                break;
            }
            case 7: {
                rate = 0.5f;
                break;
            }
            case 8: {
                rate = 0.5f;
                break;
            }
        }
        final long moneyCount = (long) ((this.servant.getServantSetTemplate().getBasePrice() + this.servant.getServantSetTemplate().getPricePerLevel() * this.servant.getLevel()) * rate);
        this.addWHItem(new Item(1, moneyCount, 0));
        if (this.servantToRewardType == EServantToRewardType.Imperial) {
            if (tir == 0) {
                return false;
            }
            this.addItem(new Item(459, tir, 0));
        }
        return super.canAct() && this.player.getServantController().delete(this.servant);
    }
}
