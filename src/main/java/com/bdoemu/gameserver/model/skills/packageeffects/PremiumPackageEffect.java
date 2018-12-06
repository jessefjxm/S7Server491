// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.packageeffects;

import com.bdoemu.core.network.sendable.SMInventorySlotCount;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;

public class PremiumPackageEffect extends AChargeUserEffect {
    public PremiumPackageEffect(final long effectEndTime, final EChargeUserType chargeUserType) {
        super(effectEndTime, chargeUserType);
    }

    @Override
    public void initEffect(final Player owner) {
        owner.sendPacket(new SMInventorySlotCount(owner.getPlayerBag().getInventory().getExpandSize()));
    }

    @Override
    public void endEffect(final Player owner) {
        owner.sendPacket(new SMInventorySlotCount(owner.getPlayerBag().getInventory().getExpandSize()));
    }
}
