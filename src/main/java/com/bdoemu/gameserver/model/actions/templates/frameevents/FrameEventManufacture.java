// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.gameserver.model.actions.AInventoryAction;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ManufactureItemEvent;

import java.util.Collection;

public class FrameEventManufacture extends FrameEvent {
    public FrameEventManufacture(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        final AInventoryAction inventoryAction = (AInventoryAction) action;
        final Player player = (Player) action.getOwner();
        player.getPlayerBag().onEvent(new ManufactureItemEvent(player, action.getActionName(), inventoryAction.getManufactures()));
        return true;
    }
}
