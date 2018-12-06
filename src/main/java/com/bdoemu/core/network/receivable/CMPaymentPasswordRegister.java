// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMPaymentPassword;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMPaymentPasswordRegister extends ReceivablePacket<GameClient> {
    private int index;
    private int zeroNumbers;
    private long pin;

    public CMPaymentPasswordRegister(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.index = this.readC();
        this.zeroNumbers = this.readC();
        final int skip = this.index * 8;
        this.skip(skip);
        this.pin = this.readQ();
        this.skipAll();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final char[] pinTable = player.getAccountData().getPaymentPinTable().toCharArray();
            final String pinToLong = Long.toString(this.pin);
            final char[] pinCharArray = pinToLong.toCharArray();
            final StringBuilder builder = new StringBuilder();
            while (this.zeroNumbers > 0) {
                --this.zeroNumbers;
                builder.append(pinTable[0]);
            }
            for (int i = 0; i < pinCharArray.length; ++i) {
                final char pinCharIndex = pinCharArray[i];
                final int pinIndex = Integer.parseInt(String.valueOf(pinCharIndex));
                builder.append(pinTable[pinIndex]);
            }
            if (player.getAccountData().setPaymentPin(builder.toString())) {
                player.sendPacket(new SMPaymentPassword(player));
            }
        }
    }
}
