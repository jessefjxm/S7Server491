// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMTentInformation;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.enums.EFixedHouseType;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Collection;

public class TakeDownTentItemEvent extends AItemEvent {
    private HouseHold tent;

    public TakeDownTentItemEvent(final Player player, final HouseHold tent) {
        super(player, player, player, EStringTable.eErrNoHousingTentRemoveByUser, EStringTable.eErrNoHousingTentRemoveByUser, player.getRegionId());
        this.tent = tent;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        final Collection<HouseHold> tents = this.player.getHouseholdController().getHouseHolds(EFixedHouseType.Tent);
        this.player.sendPacket(new SMTentInformation(tents));
    }

    @Override
    public boolean canAct() {
        if (this.tent.getAccountId() != this.player.getAccountId() || this.tent.getInstallations().size() > 0) {
            return false;
        }
        this.addItem(this.tent.getItemId(), 1L, 0);
        return super.canAct() && this.player.getHouseholdController().removeHouseHold(this.tent.getObjectId()) && World.getInstance().deSpawn(this.tent, ERemoveActorType.DespawnTent);
    }
}
