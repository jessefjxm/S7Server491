// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.core.network.sendable.SMUpdateEnchantFailCount;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;

import java.util.Collection;

public class EnchantFailCountEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        if (owner.isPlayer()) {
            final Player player = (Player) owner;
            final Integer[] params = buffTemplate.getParams();
            final int enchantFailCount = params[0];
            final int maxEnchantFailCount = params[1];
            if (player.getEnchantFailCount() + enchantFailCount <= maxEnchantFailCount) {
                player.setEnchantFailCount(player.getEnchantFailCount() + enchantFailCount);
                owner.sendPacket(new SMUpdateEnchantFailCount(player));
            }
        }
        return null;
    }
}
