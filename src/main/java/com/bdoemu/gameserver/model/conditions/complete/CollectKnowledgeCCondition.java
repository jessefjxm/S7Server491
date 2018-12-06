package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.card.enums.ECardGradeType;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CollectKnowledgeCCondition implements ICompleteConditionHandler {
    private int cardId;

    @Override
    public void load(final String... function) {
        this.cardId = Integer.parseInt(function[0]);
    }

    @Override
    public int checkCondition(final Object... params) {
        return ((int) params[0] == this.cardId) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return 1;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.collectKnowledge;
    }

    @Override
    public boolean checkStep(final Player player) {
        player.getMentalCardHandler().updateMentalCard(cardId, ECardGradeType.S);
        return true;
        //return player.getMentalCardHandler().containsCard(this.cardId);
    }

    @Override
    public Object getKey() {
        return this.cardId;
    }

    @Override
    public boolean canInteractForQuest(final Creature target) {
        return false;
    }
}
