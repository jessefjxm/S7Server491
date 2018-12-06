package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.gameserver.model.actions.ADefaultAction;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.DropBag;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EDropBagType;

public class FishingLandingAction extends ADefaultAction {
    public FishingLandingAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public boolean canAct() {
        final Player player = (Player) this.owner;
        final DropBag dropBag = player.getPlayerBag().getDropBag();
        return dropBag != null && dropBag.getDropBagType() == EDropBagType.Fishing && super.canAct();
    }
}
