// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.core.network.sendable.SMActionRefresh;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.creature.Creature;

public class FrameEventRetryActionMove extends FrameEvent {
    public FrameEventRetryActionMove(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public boolean doFrame(final IAction action, final Creature target) {
        action.getOwner().sendBroadcastItSelfPacket(new SMActionRefresh(action));
        return super.doFrame(action, target);
    }
}
