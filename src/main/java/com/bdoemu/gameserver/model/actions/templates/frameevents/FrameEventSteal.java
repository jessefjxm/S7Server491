// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.network.receivable.CMDoAction;
import com.bdoemu.core.network.sendable.SMGetDroppedItems;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMSetNpcActionForClientSpawn;
import com.bdoemu.gameserver.dataholders.CreatureData;
import com.bdoemu.gameserver.dataholders.NpcRelationData;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.DropBag;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.templates.NpcRelationT;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.items.enums.EDropBagType;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.knowlist.KnowList;

import java.util.Collection;

public class FrameEventSteal extends FrameEvent {
    public FrameEventSteal(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        final Player player = (Player) action.getOwner();
        if (player.getLevel() < 20) {
            player.sendPacket(new SMNak(EStringTable.eErrNoItemTooLowLevel, CMDoAction.class));
            return true;
        }
        Npc npc = null;
        int npcId;
        if (staticObjectId > 0L) {
            final SpawnPlacementT spawnTemplate = SpawnService.getInstance().getSpawnStatic(staticObjectId);
            if (spawnTemplate == null) {
                return false;
            }
            npcId = spawnTemplate.getCreatureId();
        } else {
            npc = KnowList.getObject(player, ECharKind.Npc, npcGameObjId);
            if (npc == null) {
                return false;
            }
            npcId = npc.getCreatureId();
        }
        if (npcId == 0) {
            return false;
        }
        final CreatureTemplate template = CreatureData.getInstance().getTemplate(npcId);
        final Integer stealDropId = template.getStealDropId();
        if (stealDropId == null || !player.addWp(-12)) {
            return false;
        }
        final DropBag dropBag = ItemMainGroupService.getDropBag(stealDropId, player, npcGameObjId, npcId, EDropBagType.Steal, RateConfig.STEAL_DROP_RATE);
        if (dropBag != null) {
            player.getPlayerBag().setDropBag(dropBag);
            player.sendPacket(new SMGetDroppedItems(npcGameObjId, dropBag));
        } else {
            final NpcRelationT relationTemplate = NpcRelationData.getInstance().getTemplate(npcId);
            if (relationTemplate != null) {
                player.getIntimacyHandler().updateIntimacy(npcId, npcGameObjId, -100);
            }
            player.addTendency(-1000.0);
            player.sendPacket(new SMSetNpcActionForClientSpawn(0, npcGameObjId));
            player.sendPacket(new SMNak(EStringTable.eErrNoCantStealItem, CMDoAction.class));
            if (npc != null && npc.getAi() != null) {
                npc.getAi().HandleFailSteal(player, null);
            }
            player.getAi().HandleFailSteal(player, null);
        }
        return true;
    }
}
