// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.dataholders.CreatureData;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMDeliverTalk extends ReceivablePacket<GameClient> {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) CMDeliverTalk.class);
    }

    private long npcHash;
    private long applyCount;
    private int npcSessionId;
    private int index;

    public CMDeliverTalk(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.npcHash = this.readQ();
        this.npcSessionId = this.readD();
        this.index = this.readD();
        this.applyCount = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            Npc npc = null;
            int dialogIndex;
            CreatureTemplate template;
            if (this.npcHash > 0L) {
                final SpawnPlacementT spawnTemplate = SpawnService.getInstance().getSpawnStatic(this.npcHash);
                if (spawnTemplate == null) {
                    CMDeliverTalk.log.warn("Not found static npc with key {}", (Object) this.npcHash);
                    return;
                }
                final int npcId = spawnTemplate.getCreatureId();
                dialogIndex = spawnTemplate.getDialogIndex();
                template = CreatureData.getInstance().getTemplate(npcId);
            } else {
                npc = KnowList.getObject(player, ECharKind.Npc, this.npcSessionId);
                if (npc == null) {
                    return;
                }
                template = npc.getTemplate();
                dialogIndex = npc.getDialogIndex();
            }
            if (!ConditionService.checkCondition(template.getInteractionConditions(), player)) {
                return;
            }
            if (npc != null) {
                npc.onDeliverTalk(player, dialogIndex, this.index, this.applyCount, template);
            } else {
                Npc.checkDialog(player, dialogIndex, this.index, npc, this.applyCount, template);
            }
        }
    }
}
