package com.bdoemu.gameserver.model.functions;

import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ExchangeItemToGroupEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

public class ExchangeItemToGroupFunction implements IFunctionHandler {
    private int itemId;
    private int enchantLevel;
    private int mainGroup;
    private int wp;
    private long count;

    @Override
    public void load(final int dialogIndex, final String functionData) {
        final String[] function = functionData.split(",");
        this.itemId = Integer.parseInt(function[0].trim());
        this.enchantLevel = Integer.parseInt(function[1].trim());
        this.count = Long.parseLong(function[2].trim());
        this.mainGroup = Integer.parseInt(function[3].trim());
        if (function.length > 4) {
            this.wp = Integer.parseInt(function[4].toLowerCase().replace("wp", "").trim());
        }
    }

    @Override
    public boolean isDisabledByContentsGroup() {
        return ItemData.getInstance().getItemTemplate(this.itemId) == null;
    }

    @Override
    public void doFunction(final Player player, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate, final int dialogIndex) {
        if (this.wp > 0 && !player.addWp(-this.wp)) {
            return;
        }
        if (player.getPlayerBag().onEvent(new ExchangeItemToGroupEvent(player, this.itemId, this.enchantLevel, this.count, this.mainGroup))) {
            player.getObserveController().notifyObserver(EObserveType.exchangeItem, this.itemId, this.enchantLevel, this.count, creatureTemplate.getCreatureId(), dialogIndex);
        }
    }
}
