// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;

import java.util.Collection;

public class ActiveBuffDispellingEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final Integer[] params = buffTemplate.getParams();
        final int groupId = params[0];
        if (groupId == 0) {
            return null;
        }
        final Integer level = params[1];
        final Integer unk = params[2];
        for (final Creature creature : targets) {
            creature.getBuffList().dispelBuff(groupId, level);
        }
        return null;
    }
}
