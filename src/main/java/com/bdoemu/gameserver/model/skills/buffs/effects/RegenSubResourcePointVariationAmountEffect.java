package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.stats.elements.BuffElement;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Nullbyte
 */
public class RegenSubResourcePointVariationAmountEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        return Collections.singletonList(new ActiveBuff(skillT, buffTemplate, owner, owner, new BuffElement(buffTemplate.getParams()[0])));
    }

    @Override
    public void applyEffect(final ActiveBuff activeBuff) {
        if (activeBuff.getTarget() != null)
            activeBuff.getTarget().getGameStats().getSubResourcePointStat().addSubResourcePoints(activeBuff.getElement().getIntValue());
    }
}