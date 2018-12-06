package com.bdoemu.gameserver.model.functions;

import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.BuyItemByPointEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

public class BuyItemByPointFunction implements IFunctionHandler {
    private int itemId;
    private int enchantLevel;
    private int type;
    private int points;
    private long count;

    @Override
    public void load(final int dialogIndex, final String functionData) {
        final String[] function = functionData.split(",");
        this.itemId = Integer.parseInt(function[0]);
        this.enchantLevel = Integer.parseInt(function[1]);
        this.count = Long.parseLong(function[2]);
        this.type = Integer.parseInt(function[3]);
        this.points = Integer.parseInt(function[4]);
    }

    @Override
    public boolean isDisabledByContentsGroup() {
        return false;
    }

    @Override
    public void doFunction(final Player player, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate, final int dialogIndex) {
        player.getPlayerBag().onEvent(new BuyItemByPointEvent(player, this.itemId, this.enchantLevel, this.type, this.points, this.count));
    }
}
