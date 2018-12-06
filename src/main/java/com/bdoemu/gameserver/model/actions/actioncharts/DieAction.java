package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.gameserver.model.actions.ADefaultAction;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.Creature;

public class DieAction extends ADefaultAction {
    public DieAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void doAction(final Creature target) {
        this.owner.onDie(target, this.getActionHash());
        super.doAction(target);
    }
}
