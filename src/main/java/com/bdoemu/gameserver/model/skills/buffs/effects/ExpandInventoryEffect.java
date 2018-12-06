// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.core.network.sendable.SMInventorySlotCount;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.PlayerInventory;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.stats.elements.ExpandElement;

import java.util.Collection;

public class ExpandInventoryEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        if (owner.isPlayer()) {
            final Player player = (Player) owner;
            final Integer[] params = buffTemplate.getParams();
            final int size = params[0];
            final int type = params[1];
            final PlayerInventory inv = player.getPlayerBag().getInventory();
            if (type == 0) {
                inv.expandBase(size);
            } else if (type == 1) {
                final ExpandElement expandElement = new ExpandElement(size, System.currentTimeMillis() + buffTemplate.getValidityTime());
                inv.addExpandElement(expandElement);
            }
            player.sendPacket(new SMInventorySlotCount(inv.getExpandSize()));
        }
        return null;
    }
}
