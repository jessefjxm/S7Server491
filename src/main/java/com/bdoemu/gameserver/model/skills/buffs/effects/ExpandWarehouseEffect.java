// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.core.network.sendable.SMWarehouseSlotCount;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.Warehouse;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.stats.elements.ExpandElement;

import java.util.Collection;

public class ExpandWarehouseEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        if (owner.isPlayer()) {
            final Player player = (Player) owner;
            final Integer[] params = buffTemplate.getParams();
            final int townId = params[0];
            final int slotCount = params[1];
            final int type = params[2];
            final Warehouse warehouse = player.getPlayerBag().getWarehouse(townId);
            if (warehouse != null) {
                if (type == 0) {
                    warehouse.expandBase(slotCount);
                } else if (type == 1) {
                    final ExpandElement expandElement = new ExpandElement(slotCount, buffTemplate.getValidityTime());
                    warehouse.addExpandElement(expandElement);
                }
                player.sendPacket(new SMWarehouseSlotCount(warehouse));
            }
        }
        return null;
    }
}
