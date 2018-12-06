package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;

import java.util.Collection;

/**
 * @author Nullbyte
 */
public class MaxSubResourcePointVariationAmountEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        // param0 = 10 ~ 30
        // param1 = 0 ~ 40
        // param2 = unused
        // param3 = time, 1860000 = 1860 = 31 minutes.
        // Exceeding a set number of Attacks creates a Fragment of Darkness (All Attacks)
        return null;
    }
}
