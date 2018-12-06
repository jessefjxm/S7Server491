// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.RepurchaseItemInShopEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.worldInstance.World;

public class CMRepurchaseItemInShop extends ReceivablePacket<GameClient> {
    private long itemObjectId;
    private long count;
    private int itemId;
    private int npcSessionId;
    private int enchantLevel;
    private int endurance;
    private EItemStorageLocation srcStorageLocation;

    public CMRepurchaseItemInShop(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcSessionId = this.readD();
        this.itemObjectId = this.readQ();
        this.itemId = this.readHD();
        this.enchantLevel = this.readHD();
        this.count = this.readQ();
        this.readQ();
        this.endurance = this.readHD();
        this.srcStorageLocation = EItemStorageLocation.valueOf(this.readC());
        this.readQ(); // after 408
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Creature spawn = World.getInstance().getObjectById(this.npcSessionId);
            if (spawn == null || !spawn.isNpc()) {
                return;
            }
            final Npc npc = (Npc) spawn;
            player.getPlayerBag().onEvent(new RepurchaseItemInShopEvent(player, this.srcStorageLocation, this.itemId, this.enchantLevel, (this.endurance == 32767) ? 0 : this.endurance, this.count, npc));
        }
    }
}
