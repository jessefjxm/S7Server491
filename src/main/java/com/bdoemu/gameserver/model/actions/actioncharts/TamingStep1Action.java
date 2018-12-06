package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.gameserver.model.actions.ADefaultAction;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class TamingStep1Action extends ADefaultAction {
    public TamingStep1Action(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void init() {
        super.init();
        final Player player = (Player) this.getOwner();
        final Servant vehicle = player.getServantController().getTameServant();
        if (vehicle != null && vehicle.getOwner() == null) {
            vehicle.getAi().HandleTamingStep1(this.getOwner(), null);
        }
    }
}
