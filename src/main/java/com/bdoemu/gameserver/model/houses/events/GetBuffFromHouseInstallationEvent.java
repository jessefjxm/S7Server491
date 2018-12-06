// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.houses.events;

import com.bdoemu.core.network.sendable.SMListFixedHouseInstallations;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.houses.HouseStorage;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.skills.services.SkillService;

import java.util.Collections;

public class GetBuffFromHouseInstallationEvent implements IHouseEvent {
    private Player player;
    private long houseObjId;
    private long installationObjId;
    private HouseInstallation houseInstallation;
    private HouseStorage houseStorage;
    private HouseHold houseHold;

    public GetBuffFromHouseInstallationEvent(final Player player, final long houseObjId, final long installationObjId) {
        this.player = player;
        this.houseObjId = houseObjId;
        this.installationObjId = installationObjId;
        this.houseStorage = player.getHouseStorage();
    }

    @Override
    public void onEvent() {
        this.houseInstallation.setEndurance(this.houseInstallation.getEndurance() - 1);
        this.player.sendBroadcastItSelfPacket(new SMListFixedHouseInstallations(this.houseHold, Collections.singleton(this.houseInstallation), EPacketTaskType.Add));
        final ItemTemplate template = ItemData.getInstance().getItemTemplate(this.houseInstallation.getItemId());
        SkillService.useSkill(this.player, template.getSkillId(), null, Collections.singletonList(this.player));
    }

    @Override
    public boolean canAct() {
        this.houseHold = this.player.getHouseholdController().getHouseHold(this.houseObjId);
        if (this.houseHold == null) {
            return false;
        }
        this.houseInstallation = this.houseHold.getHouseInstallation(this.installationObjId);
        return this.houseInstallation != null && this.houseInstallation.getEndurance() > 0 && this.player.getHouseVisit().isInHouse(this.houseHold.getCreatureId(), this.houseHold.getObjectId());
    }
}
