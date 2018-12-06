// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.actions.enums.ESpUseType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.stats.StaminaStat;

public class SMUpdateStamina extends SendablePacket<GameClient> {
    private ESpUseType spUseType;
    private int diffStamina;
    private StaminaStat staminaStat;
    private Creature owner;

    public SMUpdateStamina(final ESpUseType spUseType, final int diffStamina, final Creature owner) {
        this.spUseType = spUseType;
        this.diffStamina = diffStamina;
        this.owner = owner;
        this.staminaStat = owner.getGameStats().getStamina();
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.spUseType.getId());
        buffer.writeD(this.staminaStat.getIntValue());
        buffer.writeD(this.diffStamina);
        buffer.writeD(this.staminaStat.getIntMaxValue());
        buffer.writeF(this.owner.getActionStorage().getAction().getActionChartActionT().getStaminaSpeed());
    }
}
