package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TransformationEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final Integer[] buffParams = buffTemplate.getParams();
        final int transformationType = buffParams[0];

        //Collection<ActiveBuff> buffs = owner.getBuffList().getBuffs();
        //System.out.println("Player: " + owner.getName() + "; Transformation: " + transformationType + "; Total Buffs: " + buffs.size());
        //for (ActiveBuff bf : buffs)
        //	System.out.println("\tBuff: " + bf.getBuffId());
        //
        //if (transformationType == 0) {
        //	ActiveBuff buff = owner.getBuffList().getBuff(buffTemplate.getBuffId());
        //	if (buff != null) {
        //		System.out.println("Buff " + buffTemplate.getBuffId() + " removed.");
        //		buff.unapplyEffect();
        //		owner.getBuffList().removeActiveBuff(buff);
        //		return null;
        //	}
        //}

        final List<ActiveBuff> buffsx = new ArrayList<>();
        final Integer[] params = buffTemplate.getParams();
        for (final Creature target : targets) {
            buffsx.add(new ActiveBuff(skillT, buffTemplate, owner, target));
        }
        return buffsx;
    }
}
