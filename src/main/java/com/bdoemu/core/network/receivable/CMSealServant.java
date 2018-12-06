// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantSealType;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.utils.MathUtils;
import com.bdoemu.gameserver.worldInstance.World;

public class CMSealServant extends ReceivablePacket<GameClient> {
    private long servantObjectId;
    private int npcSessionId;
    private int type;

    public CMSealServant(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.servantObjectId = this.readQ();
        this.npcSessionId = this.readD();
        this.type = this.readC();
        this.readH();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        final Creature sealNpcObject = World.getInstance().getObjectById(this.npcSessionId);
        if (sealNpcObject != null && sealNpcObject.isNpc()) {
            final Npc sealNpc = (Npc) sealNpcObject;
            final Servant servant = player.getServantController().getServant(this.servantObjectId);
            if (servant != null && servant.getServantState() == EServantState.Field) {
                if (this.type == 0) {
                    if (player.getServantController().isStableFull(sealNpc.getRegionId(), servant.getServantType())) {
                        player.sendPacket(new SMNak(EStringTable.eErrNoStableIsFull, this.opCode));
                        return;
                    }
                    if (MathUtils.getDistance(sealNpc.getLocation(), servant.getLocation()) <= ServantConfig.VEHICLE_SEAL_DISTANCE) {
                        servant.setRegionId(sealNpc.getRegionId());
                        servant.seal(EServantSealType.NORMAL);
                    } else {
                        player.sendPacket(new SMNak(EStringTable.eErrNoDistanceIsFar, this.opCode));
                    }
                } else {
                    servant.getGameStats().getHp().setCurrentHp(1.0);
                    servant.setRegionId(sealNpc.getRegionId());
                    servant.seal(EServantSealType.NORMAL);
                }
            }
        }
    }
}
