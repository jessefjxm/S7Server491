// 
// Decompiled by Procyon v0.5.30
// 

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

public class AddGuardTypeEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final List<ActiveBuff> buffs = new ArrayList<ActiveBuff>();
        final Integer[] params = buffTemplate.getParams();
        final int resistType = params[0];
        final int resistPercents = params[1];
        final BuffElement element = new BuffElement(resistPercents);
        for (final Creature target : targets) {
            buffs.add(new ActiveBuff(skillT, buffTemplate, owner, target, element));
        }
        return buffs;
    }

    @Override
    public void applyEffect(final ActiveBuff activeBuff) {
        final Integer[] params = activeBuff.getTemplate().getParams();
        final BuffElement element = activeBuff.getElement();
        final Creature target = activeBuff.getTarget();
        final int resistType = params[0];
        switch (resistType) {
            case 0: {
                target.getGameStats().getResistKnockBack().addElement(element);
                break;
            }
            case 1: {
                target.getGameStats().getResistKnockDown().addElement(element);
                break;
            }
            case 2: {
                target.getGameStats().getResistCapture().addElement(element);
                break;
            }
            case 3: {
                target.getGameStats().getResistStun().addElement(element);
                break;
            }
            case 5: {
                target.getGameStats().getResistRigid().addElement(element);
                break;
            }
            case 6: {
                target.getGameStats().getResistBound().addElement(element);
                break;
            }
            case 7: {
                target.getGameStats().getResistHorror().addElement(element);
                break;
            }
            case 8: {
                target.getGameStats().getResistKnockBack().addElement(element);
                target.getGameStats().getResistKnockDown().addElement(element);
                target.getGameStats().getResistCapture().addElement(element);
                target.getGameStats().getResistStun().addElement(element);
                target.getGameStats().getResistRigid().addElement(element);
                target.getGameStats().getResistBound().addElement(element);
                target.getGameStats().getResistHorror().addElement(element);
                break;
            }
        }
    }

    @Override
    public void endEffect(final ActiveBuff activeBuff) {
        final Creature target = activeBuff.getTarget();
        final Integer[] params = activeBuff.getTemplate().getParams();
        final BuffElement element = activeBuff.getElement();
        final int resistType = params[0];
        switch (resistType) {
            case 0: {
                target.getGameStats().getResistKnockBack().removeElement(element);
                break;
            }
            case 1: {
                target.getGameStats().getResistKnockDown().removeElement(element);
                break;
            }
            case 2: {
                target.getGameStats().getResistCapture().removeElement(element);
                break;
            }
            case 3: {
                target.getGameStats().getResistStun().removeElement(element);
                break;
            }
            case 5: {
                target.getGameStats().getResistRigid().removeElement(element);
                break;
            }
            case 6: {
                target.getGameStats().getResistBound().removeElement(element);
                break;
            }
            case 7: {
                target.getGameStats().getResistHorror().removeElement(element);
                break;
            }
            case 8: {
                target.getGameStats().getResistKnockBack().removeElement(element);
                target.getGameStats().getResistKnockDown().removeElement(element);
                target.getGameStats().getResistCapture().removeElement(element);
                target.getGameStats().getResistStun().removeElement(element);
                target.getGameStats().getResistRigid().removeElement(element);
                target.getGameStats().getResistBound().removeElement(element);
                target.getGameStats().getResistHorror().removeElement(element);
                break;
            }
        }
    }
}
