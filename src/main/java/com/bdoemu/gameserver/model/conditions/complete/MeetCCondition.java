package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;

public class MeetCCondition implements ICompleteConditionHandler {
    private int npcId;
    private Integer dialogIndex;

    @Override
    public void load(final String... function) {
        this.npcId = Integer.parseInt(function[0].trim());
        if (function.length > 1) {
            this.dialogIndex = Integer.parseInt(function[1].trim());
        }
    }

    @Override
    public int checkCondition(final Object... params) {
        return ((this.dialogIndex == null || (int) params[1] == this.dialogIndex) && (int) params[0] == this.npcId) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return 1;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.meet;
    }

    @Override
    public boolean checkStep(final Player player) {
        return false;
    }

    @Override
    public Object getKey() {
        return this.npcId;
    }

    @Override
    public boolean canInteractForQuest(final Creature target) {
        return false;
    }
}
