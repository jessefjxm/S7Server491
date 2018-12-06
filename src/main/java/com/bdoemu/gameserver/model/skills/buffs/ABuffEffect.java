package com.bdoemu.gameserver.model.skills.buffs;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;

import java.util.Collection;

public abstract class ABuffEffect {
    public abstract Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> p1, final SkillT skillTemplate, final BuffTemplate buffTemplate);

    public void applyEffect(final ActiveBuff activeBuff) {
    }

    public void endEffect(final ActiveBuff activeBuff) {
    }
}
