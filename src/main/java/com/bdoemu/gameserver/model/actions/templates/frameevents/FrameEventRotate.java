// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.core.network.sendable.SMActionRotate;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.actions.enums.ERotateType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.world.Location;

public class FrameEventRotate extends FrameEvent {
    private int rotationSpeed;
    private float rotateAngle;
    private ERotateType rotateType;

    public FrameEventRotate(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public void read(final FileBinaryReader reader) {
        super.read(reader);
        this.rotationSpeed = reader.readD();
        this.rotateAngle = reader.readF();
        this.rotateType = ERotateType.valueof(reader.readC());
    }

    @Override
    public boolean doFrame(final IAction action, final Creature target) {
        final Creature owner = action.getOwner();
        final Location ownerLoc = owner.getLocation();
        Location targetLocation = null;
        switch (this.rotateType) {
            case ToTarget: {
                if (target != null) {
                    targetLocation = target.getLocation();
                    break;
                }
                break;
            }
            case Original: {
                targetLocation = owner.getSpawnPlacement().getLocation();
            }
            case SelfDir: {
            }
        }
        if (targetLocation != null) {
            final double direction = Math.atan2(-(targetLocation.getX() - ownerLoc.getX()), -(targetLocation.getY() - ownerLoc.getY()));
            ownerLoc.setLocation(-Math.sin(direction), -Math.cos(direction));
            owner.sendBroadcastItSelfPacket(new SMActionRotate(owner, this.rotationSpeed, direction));
        }
        return true;
    }
}
