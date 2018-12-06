package com.bdoemu.gameserver.model.functions;

import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ExchangeSilverItemEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

public class ExchangeSilverFunction implements IFunctionHandler {
    private int itemId;
    private int enchantLevel;
    private long count;
    private long receiveCount;

    @Override
    public void load(final int dialogIndex, final String functionData) {
        final String[] function = functionData.split(",");
        this.itemId = Integer.parseInt(function[0]);
        this.enchantLevel = Integer.parseInt(function[1]);
        this.count = Long.parseLong(function[2]);
        this.receiveCount = Long.parseLong(function[3]);
    }

    @Override
    public boolean isDisabledByContentsGroup() {
        return false;
    }

    @Override
    public void doFunction(final Player player, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate, final int dialogIndex) {
        if (applyCount <= 0L || player.getPlayerBag().onEvent(new ExchangeSilverItemEvent(player, this.itemId, this.enchantLevel, this.count * applyCount, this.receiveCount * applyCount))) {
        }
    }
}
