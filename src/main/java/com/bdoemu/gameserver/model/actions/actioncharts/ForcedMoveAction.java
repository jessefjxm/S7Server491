package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.gameserver.model.actions.ADefaultAction;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class ForcedMoveAction extends ADefaultAction {
    public ForcedMoveAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void init() {
        super.init();
        if (this.owner.isVehicle()) {
            final Servant servant = (Servant) this.owner;
            servant.unMountAll();
        }
    }
}
