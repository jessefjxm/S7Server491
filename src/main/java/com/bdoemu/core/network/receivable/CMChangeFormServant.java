// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ChangeFormServantEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMChangeFormServant extends ReceivablePacket<GameClient> {
    private long servantObjectId;
    private int npcSessionId;
    private int formIndex;
    private EItemStorageLocation itemStorageLocation;
    private int itemSlot;

    public CMChangeFormServant(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.servantObjectId = this.readQ();
        this.npcSessionId = this.readD();
        this.formIndex = this.readH();
        this.itemStorageLocation = EItemStorageLocation.valueOf(this.readC());
        this.itemSlot = this.readCD();
        this.readC();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcSessionId);
            if (npc == null) {
                return;
            }
            final Servant servant = player.getServantController().getServant(this.servantObjectId);
            if (servant != null && servant.getServantState().isStable() && servant.getRegionId() == npc.getRegionId()) {
                player.getPlayerBag().onEvent(new ChangeFormServantEvent(player, servant, this.formIndex, this.itemStorageLocation, this.itemSlot));
            }
        }
    }
}
