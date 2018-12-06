// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SMPaymentPassword extends SendablePacket<GameClient> {
    private final Player player;

    public SMPaymentPassword(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.player.getAccountData().getPaymentPin().isEmpty());
        buffer.writeQ(this.player.getAccountData().getPaymentPinUpdatedTime() / 1000L);
        final int tableIndex = Rnd.get(20);
        buffer.writeC(tableIndex);
        for (int i = 0; i < 20; ++i) {
            final List<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
            final StringBuilder builder = new StringBuilder();
            while (!list.isEmpty()) {
                final int index = Rnd.get(list.size());
                final Integer nr = list.remove(index);
                builder.append(nr);
            }
            if (tableIndex == i) {
                this.player.getAccountData().setPaymentPinTable(builder.toString());
            }
            final Long l = Long.parseLong(builder.toString());
            buffer.writeQ((long) l);
        }
    }
}
