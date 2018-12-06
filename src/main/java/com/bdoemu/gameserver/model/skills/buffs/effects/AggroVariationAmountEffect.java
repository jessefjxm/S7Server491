package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.agrolist.AggroInfo;
import com.bdoemu.gameserver.model.creature.agrolist.IAggroList;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;

import java.util.Collection;

public class AggroVariationAmountEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final Integer[] params = buffTemplate.getParams();
        Integer aggroVariationType = params[1];
        double aggroVariation = normalizePercentage(aggroVariationType == 1 ? params[3] : params[0]);

        for (Creature target : targets) {
            if (target == null)
                continue;

            IAggroList aggroList = target.getAggroList();
            if (aggroList != null) {
                AggroInfo aggroInfo = aggroList.getAggroInfo(owner);
                if (aggroInfo != null) {
                    aggroInfo.addHate(100 * aggroVariation);
                } else {
                    aggroList.addDmg(owner, 100 * aggroVariation);
                    aggroList.setTarget(owner);
                }
            }
        }

        //if(owner.isPlayer())
        //	System.out.println("Player: " + owner.getName() + ", Aggro variation: " + aggroVariation + ", Type: " + aggroVariationType);
        return null;
    }

    private double normalizePercentage(int percentage) {
        if (percentage < 1)
            return 1;
        return percentage / 1_000_000.0;
    }
}
