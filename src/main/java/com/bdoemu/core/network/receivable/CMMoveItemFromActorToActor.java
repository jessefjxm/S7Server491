// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.MoveItemActorToActorEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;

import java.util.Collection;

public class CMMoveItemFromActorToActor extends ReceivablePacket<GameClient> {
    private int srcSession;
    private int dstSession;
    private EItemStorageLocation srcStorage;
    private EItemStorageLocation dstStorage;
    private int srcSlot;
    private long itemCount;

    public CMMoveItemFromActorToActor(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.srcStorage = EItemStorageLocation.valueOf(this.readC());
        this.srcSession = this.readD();
        this.dstStorage = EItemStorageLocation.valueOf(this.readC());
        this.dstSession = this.readD();
        this.srcSlot = this.readCD();
        this.itemCount = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Creature srcSpawn = KnowList.getObject(player, this.srcSession);
            final Creature dstSpawn = KnowList.getObject(player, this.dstSession);
            if (srcSpawn != null && dstSpawn != null) {
                if (!srcSpawn.isPlayable() || !dstSpawn.isPlayable() || dstSpawn == srcSpawn) {
                    return;
                }
                final Playable srcActor = (Playable) srcSpawn;
                final Playable destActor = (Playable) dstSpawn;
                final Collection<Servant> servants = player.getServantController().getServants(EServantState.Field);
                if ((srcActor.isVehicle() && !servants.contains(srcActor)) || (destActor.isVehicle() && !servants.contains(destActor))) {
                    return;
                }
                if ((srcActor.isPlayer() && player != srcActor) || (destActor.isPlayer() && player != destActor)) {
                    return;
                }
                player.getPlayerBag().onEvent(new MoveItemActorToActorEvent(player, srcActor, destActor, this.srcStorage, this.dstStorage, this.srcSlot, this.itemCount));
            }
        }
    }
}
