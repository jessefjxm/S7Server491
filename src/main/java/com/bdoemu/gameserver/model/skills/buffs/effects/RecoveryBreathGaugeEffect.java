// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.core.network.sendable.SMVaryBreathGage;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;

import java.util.Collection;

public class RecoveryBreathGaugeEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final Integer[] params = buffTemplate.getParams();
        final int recoveryPercent = params[0] / 1000;
        if (recoveryPercent > 0) {
            targets.stream().filter(Creature::isPlayer).forEach(target -> {
                target.getGameStats().getBreathGauge().fill(recoveryPercent);
                target.sendPacket(new SMVaryBreathGage(target.getGameStats().getBreathGauge().getIntMaxValue()));
            });
        }
        return null;
    }
}
