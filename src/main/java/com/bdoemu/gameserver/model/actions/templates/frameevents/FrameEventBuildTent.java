// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMUseTentItem;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.actions.ATentAction;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.BuildTentItemEvent;
import com.bdoemu.gameserver.model.houses.enums.EFixedHouseType;
import com.bdoemu.gameserver.model.world.Location;

import java.util.Collection;

public class FrameEventBuildTent extends FrameEvent {
    public FrameEventBuildTent(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        final ATentAction tentAction = (ATentAction) action;
        final Player player = (Player) tentAction.getOwner();
        if (player.getHouseholdController().getHouseHolds(EFixedHouseType.Tent).size() >= 10) {
            player.sendPacket(new SMNak(EStringTable.eErrNoPersonalTentCountOver, CMUseTentItem.class));
            return true;
        }
        final Location spawnLoc = new Location(tentAction.getFenceX(), tentAction.getFenceY(), tentAction.getFenceZ(), tentAction.getFenceCos(), tentAction.getFenceSin());
        player.getPlayerBag().onEvent(new BuildTentItemEvent(player, tentAction.getStorageLocation(), tentAction.getSlotIndex(), spawnLoc));
        return true;
    }
}
