package com.bdoemu.gameserver.model.functions;

import com.bdoemu.gameserver.dataholders.CardData;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGradeType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;

public class PushKnowledgeFunction implements IFunctionHandler {
    private int cardId;
    private int unk;

    @Override
    public void load(final int dialogIndex, final String functionData) {
        final String[] function = functionData.split(",");
        this.cardId = Integer.parseInt(function[0]);
        this.unk = Integer.parseInt(function[1]);
    }

    @Override
    public boolean isDisabledByContentsGroup() {
        return CardData.getInstance().getTemplate(this.cardId) == null;
    }

    @Override
    public void doFunction(final Player player, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate, final int dialogIndex) {
        player.getMentalCardHandler().updateMentalCard(this.cardId, ECardGradeType.C);
    }
}
