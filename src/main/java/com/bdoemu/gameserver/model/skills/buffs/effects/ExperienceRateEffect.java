// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.stats.elements.BuffElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExperienceRateEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final List<ActiveBuff> buffs = new ArrayList<ActiveBuff>();
        final Integer[] params = buffTemplate.getParams();
        final int expValue = params[0];
        final int type = params[1];
        final BuffElement element = new BuffElement(expValue);
        for (final Creature target : targets) {
            buffs.add(new ActiveBuff(skillT, buffTemplate, owner, target, element));
        }
        return buffs;
    }

    @Override
    public void applyEffect(final ActiveBuff activeBuff) {
        final Integer[] params = activeBuff.getTemplate().getParams();
        final BuffElement element = activeBuff.getElement();
        final Creature target = activeBuff.getTarget();
        final int type = params[1];
        switch (type) {
            case 0: {
                target.getGameStats().getExpRate().addElement(element);
                break;
            }
            case 1: {
                target.getGameStats().getSkillExpRate().addElement(element);
                break;
            }
            case 2: {
                target.getGameStats().getLifeExpRate().addElement(element);
                break;
            }
        }
    }

    @Override
    public void endEffect(final ActiveBuff activeBuff) {
        final Creature target = activeBuff.getTarget();
        final Integer[] params = activeBuff.getTemplate().getParams();
        final BuffElement element = activeBuff.getElement();
        final int type = params[1];
        switch (type) {
            case 0: {
                target.getGameStats().getExpRate().removeElement(element);
                break;
            }
            case 1: {
                target.getGameStats().getSkillExpRate().removeElement(element);
                break;
            }
            case 2: {
                target.getGameStats().getLifeExpRate().removeElement(element);
                break;
            }
        }
    }
}
