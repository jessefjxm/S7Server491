// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.enums.ETribeType;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.stats.elements.BuffElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TribeDamageVariationAmountEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final List<ActiveBuff> buffs = new ArrayList<ActiveBuff>();
        final Integer[] params = buffTemplate.getParams();
        final ETribeType tribe = ETribeType.values()[params[0]];
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
        final ETribeType tribe = ETribeType.values()[params[0]];
        switch (tribe) {
            case Human: {
                target.getGameStats().getHumanAttackDamageStat().addElement(element);
                break;
            }
            case Ain: {
                target.getGameStats().getAinAttackDamageStat().addElement(element);
                break;
            }
            case Etc: {
                target.getGameStats().getEtcAttackDamageStat().addElement(element);
                break;
            }
            case Boss: {
                target.getGameStats().getBossAttackDamageStat().addElement(element);
                break;
            }
            case Reptile: {
                target.getGameStats().getReptileAttackDamageStat().addElement(element);
                break;
            }
            case Untribe: {
                target.getGameStats().getUntribeAttackDamageStat().addElement(element);
                break;
            }
            case Hunting: {
                target.getGameStats().getHuntingAttackDamageStat().addElement(element);
                break;
            }
            case Elephant: {
                target.getGameStats().getElephantAttackDamageStat().addElement(element);
                break;
            }
            case Cannon: {
                target.getGameStats().getCannonAttackDamageStat().addElement(element);
                break;
            }
            case Siege: {
                target.getGameStats().getSiegeAttackDamageStat().addElement(element);
                break;
            }
        }
    }

    @Override
    public void endEffect(final ActiveBuff activeBuff) {
        final Integer[] params = activeBuff.getTemplate().getParams();
        final ETribeType tribe = ETribeType.values()[params[0]];
        final BuffElement element = activeBuff.getElement();
        final Creature target = activeBuff.getTarget();
        switch (tribe) {
            case Human: {
                target.getGameStats().getHumanAttackDamageStat().removeElement(element);
                break;
            }
            case Ain: {
                target.getGameStats().getAinAttackDamageStat().removeElement(element);
                break;
            }
            case Etc: {
                target.getGameStats().getEtcAttackDamageStat().removeElement(element);
                break;
            }
            case Boss: {
                target.getGameStats().getBossAttackDamageStat().removeElement(element);
                break;
            }
            case Reptile: {
                target.getGameStats().getReptileAttackDamageStat().removeElement(element);
                break;
            }
            case Untribe: {
                target.getGameStats().getUntribeAttackDamageStat().removeElement(element);
                break;
            }
            case Hunting: {
                target.getGameStats().getHuntingAttackDamageStat().removeElement(element);
                break;
            }
            case Elephant: {
                target.getGameStats().getElephantAttackDamageStat().removeElement(element);
                break;
            }
            case Cannon: {
                target.getGameStats().getCannonAttackDamageStat().removeElement(element);
                break;
            }
            case Siege: {
                target.getGameStats().getSiegeAttackDamageStat().removeElement(element);
                break;
            }
        }
    }
}
