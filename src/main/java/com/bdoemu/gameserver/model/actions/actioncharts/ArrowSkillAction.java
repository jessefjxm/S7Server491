package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.gameserver.model.actions.ADefaultAction;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.services.SkillService;

public class ArrowSkillAction extends ADefaultAction {
    public ArrowSkillAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public boolean canAct() {
        if (this.owner.isPlayer()) {
            final int skillId = this.actionChartActionT.getSkillId();
            if (skillId != 0) {
                final Player player = (Player) this.owner;
                if (!player.getSkillList().containsSkill(skillId)) {
                    return false;
                }
                if (!SkillService.executeSkill(player, skillId)) {
                    return false;
                }
            }
        }
        return super.canAct();
    }
}
