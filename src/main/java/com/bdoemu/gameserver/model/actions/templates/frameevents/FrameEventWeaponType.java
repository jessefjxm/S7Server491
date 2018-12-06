// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.actions.enums.EWeaponType;
import com.bdoemu.gameserver.model.creature.Creature;

import java.util.Collection;

public class FrameEventWeaponType extends FrameEvent {
    private EWeaponType weaponType;

    public FrameEventWeaponType(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public void read(final FileBinaryReader reader) {
        super.read(reader);
        this.weaponType = EWeaponType.values()[reader.readD()];
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        action.getOwner().getActionStorage().setWeaponType(this.weaponType);
        return true;
    }
}
