// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.dataholders.xml.ServantSpawnInitData;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.worldInstance.World;

public class CMUnsealServant extends ReceivablePacket<GameClient> {
    private int npcSessionId;
    private long servantObjectId;
    private int servantNpcId;
    private int formId;

    public CMUnsealServant(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcSessionId = this.readD();
        this.servantObjectId = this.readQ();
        this.servantNpcId = this.readH();
        this.formId = this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        final Creature unsealNpcObject = World.getInstance().getObjectById(this.npcSessionId);
        if (unsealNpcObject != null && unsealNpcObject.isNpc()) {
            final Npc unsealNpc = (Npc) unsealNpcObject;
            final Servant servant = player.getServantController().getServant(this.servantObjectId);
            if (servant != null && servant.getCreatureId() == this.servantNpcId && servant.getFormIndex() == this.formId && player.getServantController().getServants(EServantState.Field, servant.getServantType()).isEmpty()) {
                if (servant.getServantState() != EServantState.Stable) {
                    return;
                }
                if (servant.getRegionId() != unsealNpc.getRegionId()) {
                    return;
                }
                if (servant.isDead()) {
                    return;
                }
                if (servant.getCarriageObjectId() > 0L) {
                    return;
                }
                final Location spawnLocation = ServantSpawnInitData.getInstance().getSpawnLocation(unsealNpc.getCreatureId(), servant);
                if (spawnLocation != null) {
                    servant.unSeal(new Location(spawnLocation), true, player);
                }
            }
        }
    }
}
