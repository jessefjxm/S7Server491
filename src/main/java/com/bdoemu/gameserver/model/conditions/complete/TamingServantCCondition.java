package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.ETamingServantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TamingServantCCondition implements ICompleteConditionHandler {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) TamingServantCCondition.class);
    }

    private ETamingServantType tamingType;
    private int itemId;
    private int enchantLevel;
    private int count;

    @Override
    public void load(final String... function) {
        this.tamingType = ETamingServantType.values()[Integer.parseInt(function[0])];
        this.itemId = Integer.parseInt(function[1]);
        this.enchantLevel = Integer.parseInt(function[2]);
        this.count = Integer.parseInt(function[3]);
    }

    @Override
    public int checkCondition(final Object... params) {
        switch (this.tamingType) {
            case WILD: {
                return ((int) params[0] == this.itemId) ? 1 : 0;
            }
            case ITEM: {
                return ((int) params[0] == this.itemId) ? 1 : 0;
            }
            default: {
                TamingServantCCondition.log.warn("Unhandled ETamingServantType: {}", (Object) this.tamingType.toString());
                return 0;
            }
        }
    }

    @Override
    public int getStepCount() {
        return this.count;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.tamingServant;
    }

    @Override
    public boolean checkStep(final Player player) {
        return false;
    }

    @Override
    public Object getKey() {
        return this.itemId;
    }

    @Override
    public boolean canInteractForQuest(final Creature target) {
        return false;
    }
}
