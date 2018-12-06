package com.bdoemu.gameserver.model.functions;

import com.bdoemu.core.network.sendable.SMSetEscort;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

public class EscortCompleteFunction implements IFunctionHandler {
    private int creatureId;

    @Override
    public void load(final int dialogIndex, final String functionData) {
        this.creatureId = Integer.parseInt(functionData);
    }

    @Override
    public boolean isDisabledByContentsGroup() {
        return false;
    }

    @Override
    public void doFunction(final Player player, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate, final int dialogIndex) {
        final Creature escort = player.getEscort();
        if (escort != null && escort.getCreatureId() == this.creatureId) {
            escort.getAggroList().addCreature(player);
            if (!escort.getAi().HandleDead(player, null).isChangeState()) {
                escort.onDie(player, 2544805566L);
            }
            player.getObserveController().notifyObserver(EObserveType.killMonster, escort.getCreatureId());
            player.setEscort(null);
            player.sendPacket(new SMSetEscort(-1024));
        }
    }
}
