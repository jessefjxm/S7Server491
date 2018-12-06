// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.gameserver.dataholders.NpcRelationData;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.templates.NpcRelationT;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import com.bdoemu.gameserver.model.knowlist.KnowList;

import java.util.Collection;

public class FrameEventGreet extends FrameEvent {
    public FrameEventGreet(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        final Player player = (Player) action.getOwner();
        int npcId;
        if (staticObjectId > 0L) {
            final SpawnPlacementT spawnTemplate = SpawnService.getInstance().getSpawnStatic(staticObjectId);
            if (spawnTemplate == null) {
                return false;
            }
            npcId = spawnTemplate.getCreatureId();
        } else {
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, npcGameObjId);
            if (npc == null) {
                return false;
            }
            npcId = npc.getCreatureId();
        }
        if (npcId == 0) {
            return false;
        }
        final NpcRelationT relationTemplate = NpcRelationData.getInstance().getTemplate(npcId);
        if (relationTemplate != null && player.addWp(-3)) {
            player.getIntimacyHandler().updateIntimacy(npcId, npcGameObjId, 3);
        }
        return true;
    }
}
