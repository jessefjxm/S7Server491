/*
 * Decompiled with CFR 0_121.
 * 
 * Could not load the following classes:
 *  com.bdoemu.commons.model.enums.EStringTable
 *  com.bdoemu.commons.network.SendablePacket
 *  com.bdoemu.commons.utils.Rnd
 */
package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.core.network.sendable.SMListServantInfo;
import com.bdoemu.core.network.sendable.SMMixServant;
import com.bdoemu.gameserver.dataholders.ServantData;
import com.bdoemu.gameserver.dataholders.ServantMatingData;
import com.bdoemu.gameserver.dataholders.ServantSetData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.model.ServantTemplate;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantMatingT;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantSetTemplate;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

import java.util.List;
import java.util.stream.Collectors;

public class MixServantItemEvent
        extends AItemEvent {
    private EItemStorageLocation storageLocation;
    private Servant mixServant1;
    private Servant mixServant2;
    private Servant servant;
    private String servantName;
    private ServantMatingT gradeTemplate;

    public MixServantItemEvent(Player player, Servant mixServant1, Servant mixServant2, String servantName, EItemStorageLocation storageLocation, int regionId) {
        super(player, player, player, EStringTable.eErrNoServantMix, EStringTable.eErrNoServantMix, regionId);
        this.mixServant1 = mixServant1;
        this.mixServant2 = mixServant2;
        this.storageLocation = storageLocation;
        this.servantName = servantName;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.servant.setRegionId(this.regionId);
        this.servant.setAccountId(this.player.getAccountId());
        this.player.getServantController().add(this.servant);
        this.player.sendPacket(new SMListServantInfo(this.servant, EPacketTaskType.Update));
        this.player.sendPacket(new SMMixServant(this.mixServant1.getObjectId(), this.mixServant2.getObjectId()));
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isInventory() && !this.storageLocation.isWarehouse()) {
            return false;
        }
        double averageGrade = ((this.mixServant1.getServantSetTemplate().getGrade() + this.mixServant1.getLevel() * ServantConfig.MATING_BY_LEVEL_RATE) * (100 - this.mixServant1.getDeathCount() * ServantConfig.MATING_BY_DEAD_COUNT_RATE / 10000) / 100 + (this.mixServant2.getServantSetTemplate().getGrade() + this.mixServant2.getLevel() * ServantConfig.MATING_BY_LEVEL_RATE) * (100 - this.mixServant2.getDeathCount() * ServantConfig.MATING_BY_DEAD_COUNT_RATE / 10000) / 100) / 2;
        ServantMatingData.getInstance().getTemplates().stream().filter(servantMatingT -> averageGrade >= (double) servantMatingT.getAverageGrade()).forEach(servantMatingT -> {
                    if (this.gradeTemplate == null) {
                        this.gradeTemplate = servantMatingT;
                    } else if (this.gradeTemplate.getAverageGrade() < servantMatingT.getAverageGrade()) {
                        this.gradeTemplate = servantMatingT;
                    }
                }
        );
        if (this.gradeTemplate == null) {
            return false;
        }
        int maxMatingBonus = 0;
        List servantSetTemplateList = ServantSetData.getInstance().getTemplates().stream().filter(sst -> sst.isMatingResult() && sst.getGrade() >= this.gradeTemplate.getResultMinGrade() && sst.getGrade() <= this.gradeTemplate.getResultMaxGrade() + maxMatingBonus).collect(Collectors.toList());
        if (servantSetTemplateList.isEmpty()) {
            return false;
        }
        ServantSetTemplate childTemplate = (ServantSetTemplate) servantSetTemplateList.get(Rnd.get((int) 0, (int) (servantSetTemplateList.size() - 1)));
        ServantTemplate template = ServantData.getInstance().getTemplate(childTemplate.getCharacterKey(), 1);
        if (template != null) {
            this.servant = new Servant(template, this.player, this.servantName);
        }
        this.decreaseItem(0, ServantConfig.VEHICLE_SELF_MATING_PRICE, this.storageLocation);
        return super.canAct() && this.servant != null && this.player.getServantController().delete(this.mixServant1) && this.player.getServantController().delete(this.mixServant2);
    }
}

