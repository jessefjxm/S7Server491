// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMUseSummonItemNak;
import com.bdoemu.gameserver.model.actions.AInventoryAction;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.InstanceSummon;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.UseItemEvent;
import com.bdoemu.gameserver.model.creature.services.InstanceSummonService;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.team.party.IParty;

import java.util.Collection;

public class FrameEventSummonCharacter extends FrameEvent {
    public FrameEventSummonCharacter(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        final AInventoryAction inventoryAction = (AInventoryAction) action;
        final Player player = (Player) action.getOwner();
        final InstanceSummon currentSummon = InstanceSummonService.getInstance().getSummon(player.getAccountId());
        if (currentSummon != null) {
            player.sendPacket(new SMUseSummonItemNak(2816, EStringTable.eErrNoSummonCantMonster, currentSummon.getSummon()));
            return false;
        }
        final IParty<Player> party = player.getParty();
        if (party != null && !party.isPartyLeader(player)) {
            player.sendPacket(new SMUseSummonItemNak(3840, EStringTable.eErrNoDontSummonMonsterByNotPartyLeader, null));
            return false;
        }
        final EItemStorageLocation eStorageType = EItemStorageLocation.valueOf(inventoryAction.getStorageType());
        player.getPlayerBag().onEvent(new UseItemEvent(player, inventoryAction.getSlotIndex(), eStorageType, player));
        return true;
    }
}
