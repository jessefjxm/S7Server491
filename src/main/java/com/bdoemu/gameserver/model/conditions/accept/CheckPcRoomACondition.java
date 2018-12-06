package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;

public class CheckPcRoomACondition implements IAcceptConditionHandler {
    private boolean hasExclamation;

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        this.hasExclamation = hasExclamation;
    }

    @Override
    public boolean checkCondition(final Player player) {
        final boolean result = player.getAccountData().getChargeUserStorage().isActiveChargeUserEffect(EChargeUserType.StarterPackage);
        return this.hasExclamation != result;
    }
}
