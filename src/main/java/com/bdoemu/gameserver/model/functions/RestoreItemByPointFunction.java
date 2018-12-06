package com.bdoemu.gameserver.model.functions;

import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.RestoreItemByPointEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

public class RestoreItemByPointFunction implements IFunctionHandler {
    private int itemId;
    private int enchantLevel;

    @Override
    public void load(final int dialogIndex, final String functionData) {
        final String[] function = functionData.split(",");
        this.itemId = Integer.parseInt(function[0]);
        this.enchantLevel = Integer.parseInt(function[1]);
    }

    @Override
    public boolean isDisabledByContentsGroup() {
        return false;
    }

    @Override
    public void doFunction(final Player player, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate, final int dialogIndex) {
        player.getPlayerBag().onEvent(new RestoreItemByPointEvent(player, this.itemId, this.enchantLevel));
    }
}
