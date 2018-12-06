// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.SellItemInShopEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.worldInstance.World;

public class CMSellItemInShop extends ReceivablePacket<GameClient> {
    private int slotIndex;
    private int itemId;
    private int itemEnchant;
    private int npcSessionId;
    private int sellerSessionId;
    private long moneyObjId;
    private long count;
    private EItemStorageLocation sourceStorageType, moneyStorageType;
    private boolean isTradeShop;
    private long unk1,unk2;

    public CMSellItemInShop(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcSessionId = this.readD();
        isTradeShop = this.readC() == 1;
        this.unk1 = this.readC(); // 3 (max sold?)
        this.unk2 = this.readC(); // 8 (max can sell?)
        this.slotIndex = this.readCD();
        this.itemId = this.readH();
        this.itemEnchant = this.readH();
        this.count = this.readQ();
        this.sourceStorageType = EItemStorageLocation.valueOf(this.readC()); // where to take trade item
        this.sellerSessionId = this.readD(); // player, horse, wagon
        this.moneyStorageType = EItemStorageLocation.valueOf(this.readC()); // where to send money from pay
        this.moneyObjId = this.readQ(); // money uid
        this.readQ(); // after 408
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null) {
            final Creature spawn = World.getInstance().getObjectById(this.npcSessionId);
            if (spawn == null || !spawn.isNpc())
                return;
            final Npc npc = (Npc) spawn;
            player.getPlayerBag().onEvent(new SellItemInShopEvent(player, this.moneyStorageType, this.slotIndex, this.count, npc, isTradeShop));
            
            //System.out.println("===CMSellItemInShop===");
            //System.out.println("NPC SessionId: " + npcSessionId);
            //System.out.println("is Trade Shop? : " + isTradeShop);
            //System.out.println("UNK C1: " + unk1);
            //System.out.println("UNK C2: " + unk2);
            //System.out.println("Inv Slot: " + slotIndex);
            //System.out.println("Item Id: " + itemId);
            //System.out.println("Item Enchant: " + itemEnchant);
            //System.out.println("Item Count: " + count);
            //System.out.println("SRC Storage Type: " + sourceStorageType);
            //System.out.println("Seller Session Id: " + sellerSessionId);
            //System.out.println("DST Storage Type: " + moneyStorageType);
            //System.out.println("Seller Obj Id: " + moneyObjId);
        }
    }
}
