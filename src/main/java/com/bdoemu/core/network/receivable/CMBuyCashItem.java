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

public class CMBuyCashItem extends ReceivablePacket<GameClient> {
    private int index;
    private int zeroNumbers;
    private int type;
    private long pin;
    private List<BuyCashItem> buyCashItemList;

    public CMBuyCashItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.type = this.readC();
        this.index = this.readC();
        this.zeroNumbers = this.readC();
        final int skip = 0;
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
                this.readQ();
                this.readH();
                this.buyCashItemList.add(new BuyCashItem(productNr, count, price, name, ""));
                --size;
            }
        }
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            CashItemService.getInstance().buyItem(player, this.buyCashItemList, false);
        }
        this.buyCashItemList = null;
    }
}
