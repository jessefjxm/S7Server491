// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.misc.enums.EAttackType;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.stats.elements.BuffElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefenceByScrollEffect extends ABuffEffect {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) DefenceByScrollEffect.class);
    }

    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final List<ActiveBuff> buffs = new ArrayList<ActiveBuff>();
        final Integer[] params = buffTemplate.getParams();
        final int type = params[0];
        final int value = params[1];
        final BuffElement element = new BuffElement(value, false);
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
        final EAttackType type = EAttackType.values()[params[0]];
        final int value = params[1];
        switch (type) {
            case MELEE: {
            }
        }
    }

    @Override
    public void endEffect(final ActiveBuff activeBuff) {
        final Integer[] params = activeBuff.getTemplate().getParams();
        final EAttackType type = EAttackType.values()[params[0]];
        final BuffElement element = activeBuff.getElement();
        final Creature target = activeBuff.getTarget();
        switch (type) {
            case MELEE: {
            }
        }
    }
}
