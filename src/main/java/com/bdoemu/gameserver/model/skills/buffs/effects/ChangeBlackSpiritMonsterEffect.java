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
import com.bdoemu.gameserver.model.team.party.IParty;

import java.util.Collection;

public class ChangeBlackSpiritMonsterEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        if (owner.isPlayer()) {
            final Player player = (Player) owner;
            final IParty<Player> party = (IParty<Player>) owner.getParty();
            if (party == null || !party.isPartyLeader(player)) {
                return null;
            }
            for (final Creature creature : targets) {
                creature.setDarkSpiritMonster(true);
            }
        }
        return null;
    }
}
