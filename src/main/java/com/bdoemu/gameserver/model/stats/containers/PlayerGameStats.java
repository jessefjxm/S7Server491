package com.bdoemu.gameserver.model.stats.containers;

import com.bdoemu.gameserver.dataholders.PCData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;

public class PlayerGameStats extends GameStats<Player> {
    public PlayerGameStats(final Player owner) {
        super(owner);
    }

    @Override
    public BaseElement getBaseElementForStat(final StatEnum type) {
        return PCData.getInstance().getTemplate(((Player) this.owner).getClassType().getId(), ((Player) this.owner).getLevel()).getGameStatsTemplate().getBaseElement(type);
    }
}