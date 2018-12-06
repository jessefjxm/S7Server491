package com.bdoemu.gameserver.model.functions;

import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ExchangeItemEvent;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

public class ExchangeItemFunction implements IFunctionHandler {
    private int receiveItemId;
    private int receiveEnchantLevel;
    private int giveItemId;
    private int giveEnchantLevel;
    private int wp;
    private long receiveCount;
    private long giveCount;

    public ExchangeItemFunction() {
        this.receiveCount = 1L;
    }

    @Override
    public void load(final int dialogIndex, final String functionData) {
        final String[] function = functionData.split(",");
        this.giveItemId = Integer.parseInt(function[0].trim());
        this.giveEnchantLevel = Integer.parseInt(function[1].trim());
        this.giveCount = Long.parseLong(function[2].trim());
        this.receiveItemId = Integer.parseInt(function[3].trim());
        this.receiveEnchantLevel = Integer.parseInt(function[4].trim());
        if (function.length > 5) {
            this.receiveCount = Long.parseLong(function[5].trim());
        }
        if (function.length > 6) {
            this.wp = Integer.parseInt(function[6].toLowerCase().replace("wp", "").trim());
        }
    }

    @Override
    public boolean isDisabledByContentsGroup() {
        return ItemData.getInstance().getItemTemplate(this.giveItemId) == null;
    }

    @Override
    public void doFunction(final Player player, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate, final int dialogIndex) {
        if (this.wp > 0 && !player.addWp(-this.wp)) {
            return;
        }
        if (applyCount > 0L && player.getPlayerBag().onEvent(new ExchangeItemEvent(player, this.giveItemId, this.giveEnchantLevel, this.giveCount * applyCount, this.receiveItemId, this.receiveEnchantLevel, this.receiveCount * applyCount))) {
            player.getObserveController().notifyObserver(EObserveType.exchangeItem, this.giveItemId, this.giveEnchantLevel, this.giveCount * applyCount, creatureTemplate.getCreatureId(), dialogIndex);
        }
    }
}
