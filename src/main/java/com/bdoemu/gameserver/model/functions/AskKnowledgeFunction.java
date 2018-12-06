package com.bdoemu.gameserver.model.functions;

import com.bdoemu.core.network.sendable.SMStartAskKnowledge;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

public class AskKnowledgeFunction implements IFunctionHandler {
    private int knowlegdeId;
    private int wp;

    @Override
    public void load(final int dialogIndex, final String functionData) {
        final String[] function = functionData.split(",");
        this.knowlegdeId = Integer.parseInt(function[0]);
        if (function.length > 1) {
            this.wp = Integer.parseInt(function[1].toLowerCase().replace("wp", "").trim());
        }
    }

    @Override
    public void doFunction(final Player player, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate, final int dialogIndex) {
        if (this.wp > 0 && !player.addWp(-this.wp)) {
            return;
        }
        player.sendPacket(new SMStartAskKnowledge(this.knowlegdeId));
    }

    @Override
    public boolean isDisabledByContentsGroup() {
        return false;
    }
}
