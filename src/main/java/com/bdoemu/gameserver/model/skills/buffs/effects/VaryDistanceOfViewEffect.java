// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.core.network.sendable.SMSetCharacterPrivatePoints;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.stats.elements.BuffElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VaryDistanceOfViewEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final List<ActiveBuff> buffs = new ArrayList<ActiveBuff>();
        final Integer[] params = buffTemplate.getParams();
        final Integer value = params[0];
        if (value != null) {
            final BuffElement element = new BuffElement(value / 100);
            for (final Creature target : targets) {
                buffs.add(new ActiveBuff(skillT, buffTemplate, owner, target, element));
            }
        }
        return buffs;
    }

    @Override
    public void applyEffect(final ActiveBuff activeBuff) {
        final Integer[] params = activeBuff.getTemplate().getParams();
        final Creature target = activeBuff.getTarget();
        if (target.isPlayer()) {
            if (params[0] != null) {
                target.getGameStats().getVisionRange().addElement(activeBuff.getElement());
            }
            final Player player = (Player) target;
            player.sendPacket(new SMSetCharacterPrivatePoints(player));
        }
    }

    @Override
    public void endEffect(final ActiveBuff activeBuff) {
        final Integer[] params = activeBuff.getTemplate().getParams();
        final Creature target = activeBuff.getTarget();
        if (target.isPlayer()) {
            if (params[0] != null) {
                target.getGameStats().getVisionRange().removeElement(activeBuff.getElement());
            }
            final Player player = (Player) target;
            player.sendPacket(new SMSetCharacterPrivatePoints(player));
        }
    }
}
