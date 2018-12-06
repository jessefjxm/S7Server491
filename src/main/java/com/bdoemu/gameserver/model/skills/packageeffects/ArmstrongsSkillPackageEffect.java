// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.packageeffects;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;

public class ArmstrongsSkillPackageEffect extends AChargeUserEffect {
    public ArmstrongsSkillPackageEffect(final long effectEndTime, final EChargeUserType chargeUserType) {
        super(effectEndTime, chargeUserType);
    }

    @Override
    public void initEffect(final Player owner) {
    }

    @Override
    public void endEffect(final Player owner) {
    }
}
