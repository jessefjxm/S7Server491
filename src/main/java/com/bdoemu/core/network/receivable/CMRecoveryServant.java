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

public class CMRecoveryServant extends ReceivablePacket<GameClient> {
    private int npcSessionId;
    private int type;
    private int servantSessionId;
    private long servantObjectId;
    private EItemStorageLocation storageLocation;
    private long moneyObjectId;

    public CMRecoveryServant(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.type = this.readCD();
        this.npcSessionId = this.readD();
        if (this.type == 1) {
            this.storageLocation = EItemStorageLocation.valueOf(this.readC());
            this.moneyObjectId = this.readQ();
            this.servantObjectId = this.readQ();
            this.readH();
            this.readD();
            this.readF();
            this.readD();
            this.readF();
            this.readD();
            this.readD();
            this.readC();
            this.readH();
        } else {
            this.storageLocation = EItemStorageLocation.valueOf(this.readC());
            this.moneyObjectId = this.readQ();
            this.servantSessionId = this.readD();
            this.readB(33);
        }
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Creature npcObject = World.getInstance().getObjectById(this.npcSessionId);
            if (npcObject != null && npcObject.isNpc()) {
                final Npc npc = (Npc) npcObject;
                final Servant servant = (this.type == 1) ? player.getServantController().getServant(this.servantObjectId) : player.getServantController().getServant(this.servantSessionId);
                player.getPlayerBag().onEvent(new RecoveryServantEvent(player, npc, servant, this.type, this.storageLocation));
            }
        }
    }
}
