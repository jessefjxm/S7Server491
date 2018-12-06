// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.creature.Creature;

import java.util.Collection;

public abstract class FrameEvent {
    protected EFrameEventType frameEventType;
    protected float frame;
    protected int frameTime;
    private String conditions;

    public FrameEvent(final EFrameEventType frameEventType) {
        this.frameEventType = frameEventType;
    }

    public EFrameEventType getFrameEventType() {
        return this.frameEventType;
    }

    public void read(final FileBinaryReader reader) {
        this.frame = reader.readF();
        this.conditions = reader.readS();
    }

    public int getFrameTime() {
        return this.frameTime;
    }

    public void setFrameTime(final int frameTime) {
        this.frameTime = frameTime;
    }

    public int getDelay() {
        return 0;
    }

    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        return true;
    }

    public boolean doFrame(final IAction action, final Creature target) {
        return true;
    }

    public float getFrame() {
        return this.frame;
    }
}
