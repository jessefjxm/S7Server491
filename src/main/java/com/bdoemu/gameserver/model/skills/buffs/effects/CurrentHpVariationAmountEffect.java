// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CurrentHpVariationAmountEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        List<ActiveBuff> buffs = null;
        final Integer[] params = buffTemplate.getParams();
        float addHp = params[0];
        final Integer addPercentage = params[2];
        if (buffTemplate.getValidityTime() > 0L) {
            buffs = new ArrayList<ActiveBuff>();
            for (final Creature target : targets) {
                buffs.add(new ActiveBuff(skillT, buffTemplate, owner, target));
            }
        } else {
            for (final Creature target : targets) {
                target.getGameStats().getHp().addHP(addHp, owner);
                if (addPercentage != null) {
                    addHp = target.getGameStats().getHp().getMaxHp() * (addPercentage / 10000.0f) / 100.0f;
                    target.getGameStats().getHp().addHP(addHp, owner);
                }
            }
        }
        return buffs;
    }

    @Override
    public void applyEffect(final ActiveBuff activeBuff) {
        final Creature target = activeBuff.getTarget();
        final Integer[] params = activeBuff.getTemplate().getParams();
        float addHp = params[0];
        final Integer addPercentage = params[2];
        target.getGameStats().getHp().addHP(addHp, activeBuff.getOwner());
        if (addPercentage != null) {
            addHp = target.getGameStats().getHp().getMaxHp() * (addPercentage / 1000.0f) / 100.0f;
            target.getGameStats().getHp().addHP(addHp, activeBuff.getOwner());
        }
    }
}
