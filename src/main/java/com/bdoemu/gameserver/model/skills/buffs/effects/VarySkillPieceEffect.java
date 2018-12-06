package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.skills.templates.SkillT;

import java.util.Collection;
import java.util.Collections;

/**
 * @author H1X4
 */
public class VarySkillPieceEffect extends ABuffEffect {
    // param0 - 50407    (Skill ID) -> 39
    // param1 - 1,2,3,10 (Part index active) could be EItemClassify, EItemType or something more, read description of item.
    @Override
    public Collection<ActiveBuff> initEffect(Creature p0, Collection<? extends Creature> p1, SkillT p2, BuffTemplate p3) {
        return null; // TODO: Implement
        //List<ActiveBuff> buffs = new ArrayList<>();
        //for (Creature target : p1)
        //    buffs.add(new ActiveBuff(p2, p3, p0, target));
        //return buffs;
    }

    @Override
    public void applyEffect(final ActiveBuff activeBuff) {
        SkillService.applySkill(activeBuff.getTarget(), activeBuff.getSkillT(), Collections.singletonList(activeBuff.getTarget()));
    }

    @Override
    public void endEffect(final ActiveBuff activeBuff) {
        activeBuff.getTarget().getBuffList().dispelBuffs(activeBuff.getTemplate().getParams()[0]);
    }
}
