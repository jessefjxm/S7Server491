package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;

import java.util.Collection;

// TODO: Need initializer.
public class VaryHeatstrokeResistanceEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        return null;
        //final List<ActiveBuff> buffs = new ArrayList<ActiveBuff>();
        //final Integer[] params= buffTemplate.getParams();
        //final int effectType  = params[0];
        //final int effectValue = params[1];
        //final BuffElement element = new BuffElement(new int[] { effectType, effectValue });
        //
        //for (final Creature target : targets)
        //    buffs.add(new ActiveBuff(skillT, buffTemplate, owner, target, element));
        //return buffs;
    }

    //@Override
    //public void applyEffect(final ActiveBuff activeBuff) {
    //    final Creature target 		= activeBuff.getTarget();
    //    final Integer[] params 		= activeBuff.getTemplate().getParams();
    //    final BuffElement element 	= activeBuff.getElement();
    //
    //	// 0 = HeatStroke/Sunstroke, 1 = Cold / Hypothermia
    //	target.getGameStats().getStat(StatEnum.HeatstrokeResistance).addElement(element);
    //}
    //
    //@Override
    //public void endEffect(final ActiveBuff activeBuff) {
    //    final Creature target 	  = activeBuff.getTarget();
    //    final Integer[] params 	  = activeBuff.getTemplate().getParams();
    //    final BuffElement element = activeBuff.getElement();
    //
    //	target.getGameStats().getStat(StatEnum.HeatstrokeResistance).removeElement(element);
    //}
}
