package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Nullbyte
 */
public class AddedMaxWpEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final List<ActiveBuff> buffs = new ArrayList<>();
        for (final Creature target : targets)
            buffs.add(new ActiveBuff(skillT, buffTemplate, owner, target));
        return buffs;
    }

    @Override
    public void applyEffect(final ActiveBuff activeBuff) {
        final Integer[] params = activeBuff.getTemplate().getParams();
        final Creature target = activeBuff.getTarget();

        if (target.isPlayer())
            ((Player) target).addWp(params[0]);
    }

    @Override
    public void endEffect(final ActiveBuff activeBuff) {
        final Integer[] params = activeBuff.getTemplate().getParams();
        final Creature target = activeBuff.getTarget();

        if (target.isPlayer())
            ((Player) target).addWp(-params[0]);
    }
}
