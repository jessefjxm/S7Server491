// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.core.network.sendable.SMTentHarvestInformation;
import com.bdoemu.core.network.sendable.SMTentInformation;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.enums.EFixedHouseType;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.utils.MathUtils;

import java.util.Collection;
import java.util.Collections;

public class TentHavestGrowCorrectionEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final Integer[] params = buffTemplate.getParams();
        final int unk = params[2];
        final int type = params[1];
        final int percentage = params[2];
        HouseHold tent = null;
        for (final HouseHold houseHold : ((Player) owner).getHouseholdController().getHouseHolds(EFixedHouseType.Tent)) {
            if (tent == null) {
                tent = houseHold;
            } else {
                if (MathUtils.get3DDistance(owner.getLocation(), houseHold.getLocation()) >= MathUtils.get3DDistance(owner.getLocation(), tent.getLocation())) {
                    continue;
                }
                tent = houseHold;
            }
        }
        if (tent != null) {
            switch (type) {
                case 0: {
                    tent.addFertilizer(percentage);
                }
            }
            tent.sendBroadcastPacket(new SMTentHarvestInformation(Collections.singleton(tent)));
            tent.sendBroadcastPacket(new SMTentInformation(Collections.singleton(tent)));
        }
        return null;
    }
}
