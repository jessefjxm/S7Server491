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

public class VaryWpEffect extends ABuffEffect {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) VaryWpEffect.class);
    }

    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        if (owner.isPlayer()) {
            final Player player = (Player) owner;
            final Integer[] params = buffTemplate.getParams();
            final int value = params[0];
            if (value > 0) {
                player.addWp(value);
            } else {
                VaryWpEffect.log.warn("Buff template [{}] hasn't WP value in Param0!", (Object) buffTemplate.getBuffId());
            }
        }
        return null;
    }
}
