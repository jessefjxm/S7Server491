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

public class AdditionalDamageVariationEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final List<ActiveBuff> buffs = new ArrayList<ActiveBuff>();
        final Integer[] params = buffTemplate.getParams();
        final int rateValue = params[1];
        final BuffElement element = new BuffElement(rateValue);
        for (final Creature target : targets) {
            buffs.add(new ActiveBuff(skillT, buffTemplate, owner, target, element));
        }
        return buffs;
    }

    @Override
    public void applyEffect(final ActiveBuff activeBuff) {
        final Integer[] params = activeBuff.getTemplate().getParams();
        final Creature target = activeBuff.getTarget();
        final int type = params[0];
        switch (type) {
            case 0: {
                target.getGameStats().getBackAttackRate().addElement(activeBuff.getElement());
                target.getGameStats().getDownAttackRate().addElement(activeBuff.getElement());
                target.getGameStats().getAirAttackRate().addElement(activeBuff.getElement());
                target.getGameStats().getCriticalAttackRate().addElement(activeBuff.getElement());
                target.getGameStats().getSpeedAttackRate().addElement(activeBuff.getElement());
                target.getGameStats().getCounterAttackRate().addElement(activeBuff.getElement());
                break;
            }
            case 1: {
                target.getGameStats().getBackAttackRate().addElement(activeBuff.getElement());
                break;
            }
            case 2: {
                target.getGameStats().getDownAttackRate().addElement(activeBuff.getElement());
                break;
            }
            case 3: {
                target.getGameStats().getAirAttackRate().addElement(activeBuff.getElement());
                break;
            }
            case 4: {
                target.getGameStats().getCriticalAttackRate().addElement(activeBuff.getElement());
                break;
            }
            case 5: {
                target.getGameStats().getSpeedAttackRate().addElement(activeBuff.getElement());
                break;
            }
            case 6: {
                target.getGameStats().getCounterAttackRate().addElement(activeBuff.getElement());
                break;
            }
        }
    }

    @Override
    public void endEffect(final ActiveBuff activeBuff) {
        final Integer[] params = activeBuff.getTemplate().getParams();
        final int type = params[0];
        final Creature target = activeBuff.getTarget();
        switch (type) {
            case 0: {
                target.getGameStats().getBackAttackRate().removeElement(activeBuff.getElement());
                target.getGameStats().getDownAttackRate().removeElement(activeBuff.getElement());
                target.getGameStats().getAirAttackRate().removeElement(activeBuff.getElement());
                target.getGameStats().getCriticalAttackRate().removeElement(activeBuff.getElement());
                target.getGameStats().getSpeedAttackRate().removeElement(activeBuff.getElement());
                target.getGameStats().getCounterAttackRate().removeElement(activeBuff.getElement());
                break;
            }
            case 1: {
                target.getGameStats().getBackAttackRate().removeElement(activeBuff.getElement());
                break;
            }
            case 2: {
                target.getGameStats().getDownAttackRate().removeElement(activeBuff.getElement());
                break;
            }
            case 3: {
                target.getGameStats().getAirAttackRate().removeElement(activeBuff.getElement());
                break;
            }
            case 4: {
                target.getGameStats().getCriticalAttackRate().removeElement(activeBuff.getElement());
                break;
            }
            case 5: {
                target.getGameStats().getSpeedAttackRate().removeElement(activeBuff.getElement());
                break;
            }
            case 6: {
                target.getGameStats().getCounterAttackRate().removeElement(activeBuff.getElement());
                break;
            }
        }
    }
}
