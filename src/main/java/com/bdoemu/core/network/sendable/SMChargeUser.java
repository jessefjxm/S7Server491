// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.AccountData;
import com.bdoemu.gameserver.model.creature.player.ChargeUserStorage;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;

public class SMChargeUser extends SendablePacket<GameClient> {
    private AccountData accountData;

    public SMChargeUser(final AccountData accountData) {
        this.accountData = accountData;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        if (this.accountData != null) {
            final ChargeUserStorage chargeUserStorage = this.accountData.getChargeUserStorage();
            buffer.writeQ(chargeUserStorage.getChargeUserEffectEndTime(EChargeUserType.PearlPackage));
            buffer.writeQ(chargeUserStorage.getChargeUserEffectEndTime(EChargeUserType.StarterPackage));
            buffer.writeQ(chargeUserStorage.getChargeUserEffectEndTime(EChargeUserType.CustomizationPackage));
            buffer.writeQ(chargeUserStorage.getChargeUserEffectEndTime(EChargeUserType.PremiumPackage));
            buffer.writeQ(chargeUserStorage.getChargeUserEffectEndTime(EChargeUserType.DyeingPackage));
            buffer.writeQ(chargeUserStorage.getChargeUserEffectEndTime(EChargeUserType.Kamasilve));
            buffer.writeQ(chargeUserStorage.getChargeUserEffectEndTime(EChargeUserType.UnlimitedSkillChange));
            buffer.writeQ(chargeUserStorage.getChargeUserEffectEndTime(EChargeUserType.UnlimitedSkillAwakening));
        } else {
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
        }
        buffer.writeQ(0L);
        buffer.writeQ(0L);
        buffer.writeQ(0L);
    }
}
