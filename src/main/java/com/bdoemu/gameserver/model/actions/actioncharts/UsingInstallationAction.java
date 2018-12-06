package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.actions.AInventoryAction;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.skills.services.SkillService;

import java.util.Collections;

public class UsingInstallationAction extends AInventoryAction {
    public UsingInstallationAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void init() {
        super.init();
        final Player player = (Player) this.owner;
        if (!player.getHouseVisit().isInHouse(this.getHouseObjId())) {
            return;
        }
        final HouseHold houseHold = player.getHouseholdController().getHouseHold(this.getHouseObjId());
        if (houseHold == null) {
            return;
        }
        final HouseInstallation houseInstallation = houseHold.getHouseInstallation(this.getInstallationObjId());
        if (houseInstallation == null) {
            return;
        }
        final ItemTemplate template = ItemData.getInstance().getItemTemplate(houseInstallation.getItemId());
        if (template != null && player.getInstallationActiveBuffs() == null) {
            player.setInstallationActiveBuffs(SkillService.useSkill(player, template.getSkillId(), null, Collections.singletonList(player)));
        }
    }
}
