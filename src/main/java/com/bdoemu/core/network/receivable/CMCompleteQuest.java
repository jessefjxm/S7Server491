// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMCompleteQuest extends ReceivablePacket<GameClient> {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) CMCompleteQuest.class);
    }

    private int groupId;
    private int npcSessionId;
    private int selectRewardIndex;
    private int questId;
    private long npcHash;

    public CMCompleteQuest(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcSessionId = this.readD();
        this.npcHash = this.readQ();
        this.groupId = this.readHD();
        this.questId = this.readHD();
        this.readD();
        this.selectRewardIndex = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            int npcId = 0;
            if (this.npcSessionId > 0) {
                if (this.npcHash > 0L) {
                    final SpawnPlacementT spawnTemplate = SpawnService.getInstance().getSpawnStatic(this.npcHash);
                    if (spawnTemplate == null) {
                        CMCompleteQuest.log.warn("Not found static npc key" + this.npcHash);
                        return;
                    }
                    npcId = spawnTemplate.getCreatureId();
                } else {
                    final Npc spawn = KnowList.getObject(player, ECharKind.Npc, this.npcSessionId);
                    if (spawn != null) {
                        npcId = spawn.getCreatureId();
                    }
                }
            }
            player.getPlayerQuestHandler().completeQuest(npcId, this.npcSessionId, this.groupId, this.questId, this.selectRewardIndex);
        }
    }
}
