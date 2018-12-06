package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.GetShopItemsEvent;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMGetShopItems extends ReceivablePacket<GameClient> {
    private int npcSession;
    private int tradeShopType;
    private int itemId;
    private int itemEnchant;
    private int unk1,unk2;

    public CMGetShopItems(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.tradeShopType = this.readC(); // type: 0=showList 2=showItemMarketPrices
        this.readD(); // after 408
        this.npcSession = this.readD();
        this.unk1 = this.readH();
        this.itemId = this.readHD();
        this.itemEnchant = this.readH();
        this.unk2 = this.readC();
    }

    public void runImpl() {
        final Player player = this.getClient().getPlayer();
        if (player != null) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcSession);
            if (npc != null) {
                player.getPlayerBag().onEvent(new GetShopItemsEvent(player, npc, itemId, tradeShopType));
                
                //System.out.println("===CMGetShopItems===");
                //System.out.println("Type: " + tradeShopType);
                //System.out.println("NPC Session: " + npcSession);
                //System.out.println("Unk H1: " + unk1);
                //System.out.println("Item Id: " + itemId);
                //System.out.println("Item Enchant: " + itemEnchant);
                //System.out.println("Unk C2: " + unk2);
            }
        }
    }
}
