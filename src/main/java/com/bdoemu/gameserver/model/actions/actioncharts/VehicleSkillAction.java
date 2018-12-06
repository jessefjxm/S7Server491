package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.gameserver.model.actions.ADefaultAction;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.skills.ServantSkill;

public class VehicleSkillAction extends ADefaultAction {
    public VehicleSkillAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void init() {
        final Player player = (Player) this.getOwner();
        final Servant servant = player.getCurrentVehicle();
        if (servant != null) {
            final ServantSkill servantSkill = servant.getServantSkillList().getSkill(this.actionChartActionT.getVehicleSkillKey());
            if (servantSkill != null) {
                int exp = ServantConfig.SUCCESS_SKILL_EXP;
                if (!Rnd.getChance(servantSkill.getExpPercentage())) {
                    this.message = EStringTable.eErrNoVehicleSkillExpLow;
                    exp = ServantConfig.FAIL_SKILL_EXP;
                }
                servantSkill.addExp(servant, exp);
            }
        }
        super.init();
    }
}
