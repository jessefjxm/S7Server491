// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.actions.enums.EStatType;
import com.bdoemu.gameserver.model.creature.Creature;

import java.util.Collection;

public class FrameEventVaryStat extends FrameEvent {
    private EStatType statType;
    private int amount;
    private boolean useCharacterStat;

    public FrameEventVaryStat(final EFrameEventType frameEventType) {
        super(frameEventType);
        this.useCharacterStat = false;
    }

    @Override
    public void read(final FileBinaryReader reader) {
        super.read(reader);
        this.statType = EStatType.valueof(reader.readC());
        this.amount = reader.readD();
        this.useCharacterStat = (reader.readC() == 1);
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        final Creature owner = action.getOwner();
        switch (this.statType) {
            case FP:
            case MP: {
                owner.getGameStats().getMp().addMP(this.amount);
                break;
            }
            case HP: {
                owner.getGameStats().getHp().updateHp(this.amount, owner);
                break;
            }
            case SubResourcePoint: {
                owner.getGameStats().getSubResourcePointStat().addSubResourcePoints(this.amount);
                break;
            }
        }
        return true;
    }
}
