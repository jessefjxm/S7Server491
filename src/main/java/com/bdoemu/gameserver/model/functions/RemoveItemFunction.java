package com.bdoemu.gameserver.model.functions;

import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.RemoveItemEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

public class RemoveItemFunction implements IFunctionHandler {
    private int itemId;
    private int enchantLevel;
    private long count;
    private int dialogIndex;

    @Override
    public void load(final int dialogIndex, final String functionData) {
        final String[] function = functionData.split(",");
        this.itemId = Integer.parseInt(function[0]);
        this.enchantLevel = Integer.parseInt(function[1]);
        this.count = Long.parseLong(function[2]);
        this.dialogIndex = dialogIndex;
    }

    @Override
    public boolean isDisabledByContentsGroup() {
        return ItemData.getInstance().getItemTemplate(this.itemId) == null;
    }

    @Override
    public void doFunction(final Player player, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate, final int dialogIndex) {
        if (player.getPlayerBag().onEvent(new RemoveItemEvent(player, this.itemId, this.enchantLevel, this.count))) {
            player.getObserveController().notifyObserver(EObserveType.exchangeItem, this.itemId, this.enchantLevel, this.count, creatureTemplate.getCreatureId(), this.dialogIndex);
        }
    }
}
