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
import com.bdoemu.gameserver.model.team.guild.Guild;

import java.util.Collection;

public class ExperienceEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        if (owner.isPlayer()) {
            final Player player = (Player) owner;
            if (player.hasGuild()) {
                final Integer[] params = buffTemplate.getParams();
                final int value = params[0];
                final Integer type = params[1];
                switch (type) {
                    case 0: {
                        player.addExp(value);
                        break;
                    }
                    case 1: {
                        final Guild guild = player.getGuild();
                        if (guild != null) {
                            guild.getGuildSkillList().addSkillExp(value);
                            break;
                        }
                        break;
                    }
                    case 2: {
                        player.getSkillList().addSkillExp(value);
                        break;
                    }
                }
            }
        }
        return null;
    }
}
