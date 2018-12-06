package com.bdoemu.gameserver.model.stats.containers;

import com.bdoemu.gameserver.dataholders.ServantData;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.model.ServantTemplate;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;

public class ServantGameStats extends GameStats<Servant> {
    public ServantGameStats(final Servant owner) {
        super(owner);
    }

    @Override
    public BaseElement getBaseElementForStat(final StatEnum type) {
        final ServantTemplate servantTemplate = ServantData.getInstance().getTemplate(this.owner.getCreatureId(), this.owner.getLevel());
        final BaseElement creatureBase = super.getBaseElementForStat(type);
        if (servantTemplate != null) {
            final BaseElement servantBase = servantTemplate.getGameStatsTemplate().getBaseElement(type);
            if (creatureBase != null) {
                return new BaseElement((servantBase != null) ? servantBase.getValue() : (0.0f + creatureBase.getValue()));
            }
            return servantBase;
        } else {
            switch (type) {
                case AccelerationRate:
                case BrakeSpeedRate:
                case MaxMoveSpeedRate:
                case CorneringSpeedRate: {
                    return new BaseElement(1000000.0f);
                }
                default: {
                    return creatureBase;
                }
            }
        }
    }
}
