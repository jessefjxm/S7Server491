// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.creature.Creature;

public class FrameEventSpeed extends FrameEvent {
    private float speed;
    private float targetSpeed;

    public FrameEventSpeed(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public void read(final FileBinaryReader reader) {
        super.read(reader);
        this.speed = reader.readF();
        this.targetSpeed = reader.readF();
    }

    @Override
    public boolean doFrame(final IAction action, final Creature target) {
        action.getOwner().getActionStorage().setMoveSpeed(this.speed);
        return super.doFrame(action, target);
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getTargetSpeed() {
        return this.targetSpeed;
    }
}
