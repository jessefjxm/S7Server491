// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.BuyCashItem;
import com.bdoemu.gameserver.model.items.services.CashItemService;

import java.util.ArrayList;
import java.util.List;

public class CMBuyCashItemToUserNo extends ReceivablePacket<GameClient> {
    private int index;
    private int zeroNumbers;
    private long pin;
    private List<BuyCashItem> buyCashItemList;

    public CMBuyCashItemToUserNo(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.index = this.readC();
        this.zeroNumbers = this.readC();
        final int skip = this.index * 8;
        this.skip(skip);
        this.pin = this.readQ();
        this.skip(160 - (skip + 8));
        int size = this.readH();
        if (size > 0 && size <= 20) {
            this.buyCashItemList = new ArrayList<BuyCashItem>();
            while (size > 0) {
                final int productNr = this.readHD();
                final long count = this.readQ();
                final long price = this.readQ();
                final String name = this.readS(62);
                final String family = this.readS(62);
                this.readQ();
                this.readQ();
                this.readQ();
                this.buyCashItemList.add(new BuyCashItem(productNr, count, price, name, family));
                --size;
            }
        }
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
                if (!builder.toString().equals(player.getAccountData().getPaymentPin())) {
                    return;
                }
            }
            CashItemService.getInstance().buyItem(player, this.buyCashItemList, true);
        }
        this.buyCashItemList = null;
    }
}
