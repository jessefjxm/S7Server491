// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMLinkServant;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.worldInstance.World;

public class CMLinkServant extends ReceivablePacket<GameClient> {
    private long servantObjectId;
    private int npcSessionId;
    private long carriageObjectId;
    private int type;

    public CMLinkServant(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.servantObjectId = this.readQ();
        this.npcSessionId = this.readD();
        this.carriageObjectId = this.readQ();
        this.type = this.readC();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        final Creature carriageNpcObject = World.getInstance().getObjectById(this.npcSessionId);
        if (carriageNpcObject != null && carriageNpcObject.isNpc()) {
            final Servant servant = player.getServantController().getServant(this.servantObjectId);
            if (servant != null) {
                final ItemPack inventory = servant.getInventory();
                if (inventory != null && !inventory.isEmpty()) {
                    this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoExistItemInVehicle, this.opCode));
                    return;
                }
                final Servant carriage = player.getServantController().getServant(this.carriageObjectId);
                if (carriage != null) {
                    if (this.type == 1) {
                        if (carriage.getLinkedServants().size() < carriage.getServantSetTemplate().getLinkCount()) {
                            servant.setCarriageObjectId(this.carriageObjectId);
                            player.sendPacket(new SMLinkServant(servant));
                        }
                    } else if (this.type == 0) {
                        servant.setCarriageObjectId(0L);
                        player.sendPacket(new SMLinkServant(servant));
                    }
                }
            }
        }
    }
}
