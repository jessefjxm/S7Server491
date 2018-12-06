package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.EGamePointType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HaveGamePointCCondition implements ICompleteConditionHandler {
    private static final Logger log = LoggerFactory.getLogger(HaveGamePointCCondition.class);
    private EGamePointType gamePointType;
    private int gamePointCount;

    @Override
    public void load(final String... function) {
        this.gamePointType = EGamePointType.values()[Integer.parseInt(function[0].trim())];
        this.gamePointCount = Integer.parseInt(function[1].trim());
    }

    @Override
    public int checkCondition(final Object... params) {
        return ((int) params[0] == this.gamePointType.ordinal() && (int) params[1] >= this.gamePointCount) ? 1 : 0;
    }

    @Override
    public int getStepCount() {
        return 1;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.acquirePoint;
    }

    @Override
    public boolean checkStep(final Player player) {
        switch (this.gamePointType) {
            case CONTRIBUTION: {
                if (player.getExplorePointHandler().getMainExplorePoint().getMaxExplorePoints() >= this.gamePointCount) {
                    return true;
                }
                break;
            }
            case WP: {
                if (player.getMaxWp() >= this.gamePointCount) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    @Override
    public Object getKey() {
        return this.gamePointType.ordinal();
    }

    @Override
    public boolean canInteractForQuest(final Creature target) {
        return false;
    }
}
