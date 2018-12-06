/*
 * Decompiled with CFR 0_121.
 * 
 * Could not load the following classes:
 *  com.bdoemu.commons.database.mongo.JSONable
 *  com.bdoemu.commons.model.enums.EStringTable
 *  com.bdoemu.commons.network.SendablePacket
 *  com.bdoemu.commons.utils.Rnd
 */
package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.ServantConfig;
import com.bdoemu.core.network.sendable.SMStartServantMating;
import com.bdoemu.gameserver.databaseCollections.ServantAuctionDBCollection;
import com.bdoemu.gameserver.dataholders.ServantMatingData;
import com.bdoemu.gameserver.dataholders.ServantSetData;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.bdoemu.gameserver.model.auction.services.AuctionGoodService;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantMatingT;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantSetTemplate;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.List;
import java.util.stream.Collectors;

public class StartServantMatingItemEvent
        extends AItemEvent {
    private EItemStorageLocation storageLocation;
    private Servant female;
    private Servant male;
    private boolean isSelfOnly;
    private ServantItemMarket servantItemMarket;
    private ServantMatingT gradeTemplate;
    private ServantSetTemplate childTemplate;

    public StartServantMatingItemEvent(Player player, Servant female, ServantItemMarket servantItemMarket, EItemStorageLocation storageLocation, boolean isSelfOnly, int regionId) {
        super(player, player, player, EStringTable.eErrNoItemIsRemovedToStartMating, EStringTable.eErrNoItemIsRemovedToStartMating, regionId);
        this.storageLocation = storageLocation;
        this.female = female;
        this.servantItemMarket = servantItemMarket;
        this.male = servantItemMarket.getServant();
        this.isSelfOnly = isSelfOnly;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.servantItemMarket.setSold(true);
        long endTime = GameTimeService.getServerTimeInSecond() + (long) (ServantConfig.MATING_TIME / 1000);
        this.female.setServantState(EServantState.Mating);
        this.female.setMatingTime(endTime);
        this.female.setMatingChildId(this.childTemplate.getCharacterKey());
        this.male.setServantState(EServantState.Mating);
        this.male.setMatingTime(endTime);
        this.male.getGameStats().getMp().setCurrentMp(0.0);
        this.female.getGameStats().getMp().setCurrentMp(0.0);
        this.player.sendPacket(new SMStartServantMating(this.female.getObjectId(), this.male.getObjectId(), endTime));
        Player owner = World.getInstance().getPlayerByAccount(this.servantItemMarket.getAccountId());
        if (owner != null) {
            owner.sendPacket(new SMStartServantMating(this.male.getObjectId(), this.female.getObjectId(), endTime));
        }
        ServantAuctionDBCollection.getInstance().update(this.servantItemMarket);
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isInventory() && !this.storageLocation.isWarehouse()) {
            return false;
        }
        if (this.isSelfOnly && this.player.getAccountId() != this.servantItemMarket.getAccountId()) {
            return false;
        }
        double averageGrade = ((this.male.getServantSetTemplate().getGrade() + this.male.getLevel() * ServantConfig.MATING_BY_LEVEL_RATE) * (100 - this.male.getDeathCount() * ServantConfig.MATING_BY_DEAD_COUNT_RATE / 10000) / 100 + (this.female.getServantSetTemplate().getGrade() + this.female.getLevel() * ServantConfig.MATING_BY_LEVEL_RATE) * (100 - this.female.getDeathCount() * ServantConfig.MATING_BY_DEAD_COUNT_RATE / 10000) / 100) / 2;
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
        this.childTemplate = (ServantSetTemplate) servantSetTemplateList.get(Rnd.get(0, (servantSetTemplateList.size() - 1)));
        this.decreaseItem(0, this.servantItemMarket.getPrice(), this.storageLocation);
        return super.canAct() && AuctionGoodService.getInstance().getMatingServants().remove(this.servantItemMarket) && this.male.addMatingCount(-1) && this.female.addMatingCount(-1);
    }
}

