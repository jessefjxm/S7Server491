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

public class CurrentMpVariationAmountEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        List<ActiveBuff> buffs = null;
        final Integer[] params = buffTemplate.getParams();
        int addMp = params[0];
        final Integer addPercentage = params[2];
        if (buffTemplate.getValidityTime() > 0L) {
            buffs = new ArrayList<ActiveBuff>();
            for (final Creature target : targets) {
                buffs.add(new ActiveBuff(skillT, buffTemplate, owner, target));
            }
        } else {
            for (final Creature target : targets) {
                target.getGameStats().getMp().addMP(addMp);
                if (addPercentage != null) {
                    addMp = (int) (target.getGameStats().getMp().getMaxMp() * (addPercentage / 10000.0f) / 100.0f);
                    target.getGameStats().getMp().addMP(addMp);
                }
            }
        }
        return buffs;
    }

    @Override
    public void applyEffect(final ActiveBuff activeBuff) {
        final Creature target = activeBuff.getTarget();
        final Integer[] params = activeBuff.getTemplate().getParams();
        int addMp = params[0];
        final Integer addPercentage = params[2];
        target.getGameStats().getMp().addMP(addMp);
        if (addPercentage != null) {
            addMp = (int) (target.getGameStats().getMp().getMaxMp() * (addPercentage / 10000.0f) / 100.0f);
            target.getGameStats().getMp().addMP(addMp);
        }
    }
}
