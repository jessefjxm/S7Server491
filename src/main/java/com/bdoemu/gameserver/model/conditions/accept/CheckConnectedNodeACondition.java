package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.exploration.Discovery;

public class CheckConnectedNodeACondition implements IAcceptConditionHandler {
    private int wayPointId;
    private int unk;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] values = conditionValue.split(",");
        this.unk = Integer.parseInt(values[0].trim());
        this.wayPointId = Integer.parseInt(values[1].trim());
    }

    @Override
    public boolean checkCondition(final Player player) {
        final Discovery discovery = player.getExploration().getDiscovery(this.wayPointId);
        return discovery != null && discovery.getContribution() > 0;
    }
}
