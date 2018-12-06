// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.dataholders.PersonalityData;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.templates.PersonalityT;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMReadyMentalGame extends ReceivablePacket<GameClient> {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) CMReadyMentalGame.class);
    }

    private int session;
    private long hash;

    public CMReadyMentalGame(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.session = this.readD();
        this.hash = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            int npcId;
            if (this.hash == 0L) {
                final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.session);
                if (npc == null) {
                    return;
                }
                npcId = npc.getCreatureId();
            } else {
                final SpawnPlacementT spawnTemplate = SpawnService.getInstance().getSpawnStatic(this.hash);
                if (spawnTemplate == null) {
                    CMReadyMentalGame.log.warn("Not found static npc key" + this.hash);
                    return;
                }
                npcId = spawnTemplate.getCreatureId();
            }
            final PersonalityT personalityT = PersonalityData.getInstance().getTemplate(npcId);
            if (personalityT != null) {
                player.getMentalCardHandler().newGame(personalityT, npcId, this.session);
            }
        }
    }
}
