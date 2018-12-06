package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.model.creature.player.Player;

public interface IAcceptConditionHandler {
    void load(final String p0, final String p1, final String p2, final boolean p3);

    boolean checkCondition(final Player p0);
}
