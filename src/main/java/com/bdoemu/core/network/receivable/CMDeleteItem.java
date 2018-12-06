// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.DeleteItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMDeleteItem extends ReceivablePacket<GameClient> {
    private int sessionId;
    private long count;
    private long itemObjId;
    private int itemId;
    private int inventorySlot;
    private EItemStorageLocation storageType;
    private int index;
    private int zeroNumbers;
    private long pin;

    public CMDeleteItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.sessionId = this.readD();
        this.storageType = EItemStorageLocation.valueOf(this.readC());
        this.itemObjId = this.readQ();
        this.itemId = this.readD();
        this.count = this.readQ();
        this.index = this.readC();
        this.zeroNumbers = this.readC();
        final int skip = this.index * 8;
        this.skip(skip);
        this.pin = this.readQ();
        this.skip(160 - (skip + 8));
        this.inventorySlot = this.readCD();
        this.skip(18);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final StringBuilder builder = new StringBuilder();
            if (this.index > 0) {
                final char[] pinTable = player.getAccountData().getPaymentPinTable().toCharArray();
                final String pinToLong = Long.toString(this.pin);
                final char[] pinCharArray = pinToLong.toCharArray();
                while (this.zeroNumbers > 0) {
                    --this.zeroNumbers;
                    builder.append(pinTable[0]);
                }
                for (int i = 0; i < pinCharArray.length; ++i) {
                    final char pinCharIndex = pinCharArray[i];
                    final int pinIndex = Integer.parseInt(String.valueOf(pinCharIndex));
                    builder.append(pinTable[pinIndex]);
                }
            }
            player.getPlayerBag().onEvent(new DeleteItemEvent(player, this.inventorySlot, this.count, this.storageType, builder.toString()));
        }
    }
}
