// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMContactNpc;
import com.bdoemu.gameserver.dataholders.CreatureData;
import com.bdoemu.gameserver.dataholders.NpcRelationData;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.templates.NpcRelationT;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.functions.IFunctionHandler;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CMContactNpc extends ReceivablePacket<GameClient> {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) CMContactNpc.class);
    }

    private int gameObjId;
    private long staticId;

    public CMContactNpc(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.staticId = this.readQ();
        this.gameObjId = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            int dialogType = 2;
            int discoverDialog = -1;
            Npc npc = null;
            CreatureTemplate template;
            int npcId;
            int dialogIndex;
            if (this.staticId == 0L) {
                npc = KnowList.getObject(player, ECharKind.Npc, this.gameObjId);
                if (npc == null) {
                    return;
                }
                template = npc.getTemplate();
                npcId = npc.getCreatureId();
                dialogIndex = npc.getDialogIndex();
            } else {
                final SpawnPlacementT spawnTemplate = SpawnService.getInstance().getSpawnStatic(this.staticId);
                if (spawnTemplate == null) {
                    CMContactNpc.log.warn("Not found static npc with key {}", (Object) this.staticId);
                    return;
                }
                npcId = spawnTemplate.getCreatureId();
                dialogIndex = spawnTemplate.getDialogIndex();
                template = CreatureData.getInstance().getTemplate(npcId);
            }
            if (!ConditionService.checkCondition(template.getInteractionConditions(), player)) {
                return;
            }
            final NpcRelationT relationTemplate = NpcRelationData.getInstance().getTemplate(npcId);
            if (!player.getIntimacyHandler().contains(npcId) && relationTemplate != null) {
                final int dialog = relationTemplate.getDiscoveredDialog(player);
                if (dialog != -2) {
                    dialogType = 1;
                    discoverDialog = dialog;
                    final List<IFunctionHandler> functions = relationTemplate.getFunctionHandler();
                    if (!functions.isEmpty()) {
                        for (final IFunctionHandler function : functions) {
                            function.doFunction(player, npc, 1L, template, dialogIndex);
                        }
                    }
                    player.getIntimacyHandler().checkOnContactIntimacy(npcId, this.gameObjId);
                } else {
                    dialogType = 0;
                }
            }
            if (relationTemplate != null && dialogType == 0) {
                this.sendPacket((SendablePacket) new SMContactNpc(this.staticId, this.gameObjId, dialogType, discoverDialog));
                return;
            }
            player.getObserveController().notifyObserver(EObserveType.meet, npcId, dialogIndex);
            this.sendPacket((SendablePacket) new SMContactNpc(this.staticId, this.gameObjId, dialogType, discoverDialog));
            if (npc != null) {
                npc.onContact(player);
            }
        }
    }
}
