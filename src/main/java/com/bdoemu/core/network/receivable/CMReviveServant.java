// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.RecoveryServantEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.worldInstance.World;

public class CMReviveServant extends ReceivablePacket<GameClient> {
    private int npcSessionId;
    private long servantObjectId;
    private float hp;
    private EItemStorageLocation storageLocation;
    private long moneyObjectId;

    public CMReviveServant(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcSessionId = this.readD();
        this.servantObjectId = this.readQ();
        this.readH();
        this.hp = this.readF();
        this.readD();
        this.readD();
        this.readC();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.moneyObjectId = this.readQ();
    }

    public void runImpl() {
        if (this.hp == 0.0f) {
            final Player player = ((GameClient) this.getClient()).getPlayer();
            final Creature npcObject = World.getInstance().getObjectById(this.npcSessionId);
            if (npcObject != null && npcObject.isNpc()) {
                final Npc npc = (Npc) npcObject;
                final Servant servant = player.getServantController().getServant(this.servantObjectId);
                player.getPlayerBag().onEvent(new RecoveryServantEvent(player, npc, servant, 2, this.storageLocation));
            }
        }
    }
}
