// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class RevivalEffect extends ABuffEffect {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(RevivalEffect.class);
    }

    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final Integer[] params = buffTemplate.getParams();
        final int healPercentsHp = params[0] / 10000;
        final int healPercentsMp = params[1] / 10000;
        if (healPercentsHp != 0 && healPercentsMp != 0) {
            targets.stream().filter(Creature::isPlayer).forEach(target -> {
                ((Player) target).revive(healPercentsHp, healPercentsMp);
            });
        } else {
            RevivalEffect.log.error("ReviveEffect has zero heal percent value: buffId={}", (Object) buffTemplate.getBuffId());
        }
        return null;
    }
}
