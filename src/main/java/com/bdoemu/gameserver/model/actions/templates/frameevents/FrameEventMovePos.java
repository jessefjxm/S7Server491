// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.core.network.sendable.SMTurnNonPlayer;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.utils.MathUtils;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Collection;

public class FrameEventMovePos extends FrameEvent {
    private float x;
    private float y;
    private float z;
    private boolean baseTarget;
    private float interpolate;
    private float height;

    public FrameEventMovePos(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public void read(final FileBinaryReader reader) {
        super.read(reader);
        this.interpolate = reader.readF();
        this.x = reader.readF();
        this.y = reader.readF();
        this.z = reader.readF();
        this.height = reader.readF();
        this.baseTarget = reader.readCB();
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        return super.doFrame(action, npcGameObjId, staticObjectId, targets, playerTargets);
    }

    @Override
    public boolean doFrame(final IAction action, final Creature target) {
        final Creature owner = action.getOwner();
        double angleDiff = 0.0;
        if (this.interpolate == 350.0f) {
            angleDiff = -1.5707963267948966;
        } else if (this.interpolate == -350.0f) {
            angleDiff = 1.5707963267948966;
        }
        if (angleDiff != 0.0 && target != null) {
            final Location actorLoc = owner.getLocation();
            final Location targetLoc = target.getLocation();
            final double distance = MathUtils.getDistance(targetLoc, actorLoc);
            double angleFromTarget = Math.atan2((actorLoc.getY() - targetLoc.getY()) / distance, (actorLoc.getX() - targetLoc.getX()) / distance);
            final double angle = angleFromTarget + angleDiff;
            if (angle > 0.0) {
                angleFromTarget = (angle + 3.141592653589793) % 6.283185307179586 - 3.141592653589793;
            } else {
                angleFromTarget = (angle - 3.141592653589793) % 6.283185307179586 + 3.141592653589793;
            }
            final double nx = actorLoc.getX() + Math.cos(angleFromTarget) * distance;
            final double ny = actorLoc.getY() + Math.sin(angleFromTarget) * distance;
            final double angleToTarget = Math.atan2((targetLoc.getY() - ny) / distance, (targetLoc.getX() - nx) / distance);
            final double cos = Math.cos(angleToTarget);
            final double sin = Math.sin(angleToTarget);
            if (World.getInstance().getWorldMap().updateLocation(owner, nx, ny)) {
                actorLoc.setLocation(nx, ny, actorLoc.getZ(), cos, sin);
            }
            owner.sendBroadcastPacket(new SMTurnNonPlayer(owner));
        }
        return true;
    }
}
