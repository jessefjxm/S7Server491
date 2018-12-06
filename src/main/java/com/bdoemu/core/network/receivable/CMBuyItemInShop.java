package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.BuyItemInShopEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.worldInstance.World;

public class CMBuyItemInShop extends ReceivablePacket<GameClient> {
    private int itemId;
    private int enchantLevel;
    private int npcSessionId;
    private int destinationOwnerSessionId;
    private long moneyObjId;
    private long count;
    private EItemStorageLocation moneyStorageType, destinationStorageType;
    private long unk1,itemShopIndex;

    public CMBuyItemInShop(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcSessionId = this.readD();
        this.unk1 = this.readC(); // slotIndex?
        this.itemShopIndex = this.readH();
        this.itemId = this.readHD();
        this.enchantLevel = this.readH();
        this.count = this.readQ();
        this.moneyStorageType = EItemStorageLocation.valueOf(this.readC()); // where to get money to pay from
        this.moneyObjId = this.readQ(); // money uid
        this.destinationStorageType = EItemStorageLocation.valueOf(this.readC()); // where to put trade item
        this.destinationOwnerSessionId = this.readD(); // player, horse, wagon
        this.readQ(); // after 408
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null) {
            final Creature spawn = World.getInstance().getObjectById(this.npcSessionId);
            if (spawn == null || !spawn.isNpc()) {
                return;
            }
            final Npc npc = (Npc) spawn;
            player.getPlayerBag().onEvent(new BuyItemInShopEvent(player, this.moneyStorageType, this.itemId, this.enchantLevel, this.count, npc));
            
            //System.out.println("===CMBuyItemInShop===");
            //System.out.println("NPC Session: " + npcSessionId);
            //System.out.println("UNK C1: " + unk1);
            //System.out.println("Item Shop Index: " + itemShopIndex);
            //System.out.println("Item Id: " + itemId);
            //System.out.println("Item Enchant: " + enchantLevel);
            //System.out.println("Item Count: " + count);
            //System.out.println("Money SRC Storage Type: " + moneyStorageType);
            //System.out.println("Buyer Obj Id: " + moneyObjId);
            //System.out.println("Item DST Storage Type: " + destinationStorageType);
            //System.out.println("Owner Session Id: " + destinationOwnerSessionId);
        }
    }
}
