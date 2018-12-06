// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.core.network.sendable.SMSetServantStats;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.stats.elements.BuffElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VaryServantStatEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final List<ActiveBuff> buffs = new ArrayList<ActiveBuff>();
        final Integer[] params = buffTemplate.getParams();
        final int moveSpeedRate = params[1];
        final BuffElement element = new BuffElement(moveSpeedRate);
        for (final Creature target : targets) {
            buffs.add(new ActiveBuff(skillT, buffTemplate, owner, target, element));
        }
        return buffs;
    }

    @Override
    public void applyEffect(final ActiveBuff activeBuff) {
        final Creature target = activeBuff.getTarget();
        final BuffElement element = activeBuff.getElement();
        final Integer[] params = activeBuff.getTemplate().getParams();
        final int type = params[0];
        switch (type) {
            case 0: {
                target.getGameStats().getAccelerationRate().addElement(element);
                break;
            }
            case 1: {
                target.getGameStats().getMaxMoveSpeedRate().addElement(element);
                break;
            }
            case 2: {
                target.getGameStats().getBrakeSpeedRate().addElement(element);
                break;
            }
            case 3: {
                target.getGameStats().getCorneringSpeedRate().addElement(element);
                break;
            }
        }
        target.sendBroadcastPacket(new SMSetServantStats((Servant) target));
    }

    @Override
    public void endEffect(final ActiveBuff activeBuff) {
        final Creature target = activeBuff.getTarget();
        final BuffElement element = activeBuff.getElement();
        final Integer[] params = activeBuff.getTemplate().getParams();
        final int type = params[0];
        switch (type) {
            case 0: {
                target.getGameStats().getAccelerationRate().removeElement(element);
                break;
            }
            case 1: {
                target.getGameStats().getMaxMoveSpeedRate().removeElement(element);
                break;
            }
            case 2: {
                target.getGameStats().getBrakeSpeedRate().removeElement(element);
                break;
            }
            case 3: {
                target.getGameStats().getCorneringSpeedRate().removeElement(element);
                break;
            }
        }
        target.sendBroadcastPacket(new SMSetServantStats((Servant) target));
    }
}
