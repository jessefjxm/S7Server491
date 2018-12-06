package com.bdoemu.gameserver.model.creature.npc.worker.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.EtcOptionConfig;
import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.network.receivable.CMQuickCompleteNpcWork;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.dataholders.ItemExchangeSourceData;
import com.bdoemu.gameserver.dataholders.PlantWorkerData;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorkerController;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkingType;
import com.bdoemu.gameserver.model.creature.npc.worker.templates.PlantWorkerT;
import com.bdoemu.gameserver.model.creature.npc.worker.works.HarvestWorkingWork;
import com.bdoemu.gameserver.model.creature.npc.worker.works.PlantRentHouseLargeCraftWork;
import com.bdoemu.gameserver.model.creature.npc.worker.works.PlantRentHouseWork;
import com.bdoemu.gameserver.model.creature.npc.worker.works.PlantZoneWork;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.InstantCashItemEvent;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ItemNpcWorkToWarehouseEvent;
import com.bdoemu.gameserver.model.creature.player.mail.services.MailService;
import com.bdoemu.gameserver.model.houses.events.UpdateHouseLargeCraftEvent;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemExchangeSourceT;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import com.bdoemu.gameserver.model.misc.enums.EInstantCashType;
import com.bdoemu.gameserver.service.GameTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QuickCompleteNpcWorkEvent implements INpcWorkerEvent {
    private static final Logger log = LoggerFactory.getLogger(QuickCompleteNpcWorkEvent.class);
    private Player player;
    private long objectId;
    private long count;
    private int completeType;
    private NpcWorker npcWorker;
    private NpcWorkerController npcWorkerController;
    private ItemExchangeSourceT itemExchangeSourceT;

    public QuickCompleteNpcWorkEvent(final Player player, final long objectId, final long count, final int completeType) {
        this.player = player;
        this.objectId = objectId;
        this.count = count;
        this.completeType = completeType;
        this.npcWorkerController = player.getNpcWorkerController();
    }

    @Override
    public void onEvent() {
        //System.out.println("onEvent=WorkType=" + npcWorker.getNpcWork().getWorkType().name());
        switch (this.npcWorker.getNpcWork().getWorkType()) {
            case Upgrade: {
                int newWorkerId = 0;
                int newSkillId = 0;
                if (Rnd.getChance(this.npcWorker.getUgradeChance(), 10000)) {
                    newWorkerId = this.npcWorker.getPlantWorkerT().getUpgradeCharacterKey();
                    MailService.getInstance().sendMail(0L, 0L, "{3183609639|3885962852}", "{3183609639|3943559416|3031232925|CharacterInfo:Name_" + Integer.toString(this.npcWorker.getCharacterKey()) + "}", "{3183609639|387948398|3031232925|CharacterInfo:Name_" + Integer.toString(this.npcWorker.getCharacterKey()) + "}");
                    this.npcWorker.setPlantWorkerT(PlantWorkerData.getInstance().getTemplate(newWorkerId));
                    this.npcWorker.setLevel(1);
                    this.npcWorker.getPassiveSkills().clear();
                    final PlantWorkerT plantWorkerT = this.npcWorker.getPlantWorkerT();
                    if (plantWorkerT.getDefaultSkillKey() != null) {
                        newSkillId = plantWorkerT.getDefaultSkillKey();
                        this.npcWorker.getPassiveSkills().add(PlantWorkerData.getInstance().getPassiveSkill(newSkillId));
                    } else {
                        newSkillId = this.npcWorker.getNextSkill().getSkillId();
                    }
                } else {
                    MailService.getInstance().sendMail(0L, 0L, "{3183609639|3885962852}", "{3183609639|3943559416|3031232925|CharacterInfo:Name_" + Integer.toString(this.npcWorker.getCharacterKey()) + "}", "{3183609639|222085747|3031232925|CharacterInfo:Name_" + Integer.toString(this.npcWorker.getCharacterKey()) + "}");
                    this.npcWorker.setUpgradeCount(this.npcWorker.getUpgradeCount() - 1);
                }
                this.npcWorker.stopWorking();
                this.player.sendPacket(new SMNpcWorkerUpgrade(this.objectId, newWorkerId, newSkillId));
                this.player.sendPacket(new SMStopPlantWorkingToNpcWorker(this.objectId, this.player.getGameObjectId(), 0));
                break;
            }
            case PlantZone: {
                final PlantZoneWork plantZoneWork = (PlantZoneWork) this.npcWorker.getNpcWork();
                final PlantWorkerT template = this.npcWorker.getPlantWorkerT();
                double bonusLuck = RateConfig.WORKER_LUCK_RATE / 100.0;
                final int luck = (int) (template.getLuck() + Rnd.get(template.getLuckAddRateMin() * bonusLuck, template.getLuckAddRateMax() * bonusLuck));
                final List<ItemSubGroupT> itemsSubGroup = ItemMainGroupService.getItems(this.player, plantZoneWork.getPlantExchangeKey(), luck);
                this.player.getPlayerBag().onEvent(new ItemNpcWorkToWarehouseEvent(this.player, this.npcWorker.getRegionId(), itemsSubGroup, this.objectId));
                this.npcWorker.addExp(39);
                this.player.sendPacket(new SMChangedWorkerStatus(this.npcWorker));
                if (this.npcWorker.getCount() > 0) {
                    this.npcWorker.setCount(this.npcWorker.getCount() - 1);
                    this.npcWorker.setActionPoints(this.npcWorker.getActionPoints() - 1);
                    this.npcWorker.setStartTime(GameTimeService.getServerTimeInSecond());
                    this.player.sendPacket(new SMRestartPlantWorkingToNpcWorker(this.npcWorker));
                    break;
                }
                this.npcWorker.stopWorking();
                this.player.sendPacket(new SMStopPlantWorkingToNpcWorker(this.objectId, this.player.getGameObjectId(), 0));
                break;
            }
            case PlantRentHouse: {
                this.npcWorker.addExp(39);
                final List<ItemSubGroupT> itemsSubGroup = ItemMainGroupService.getItems(this.player, this.itemExchangeSourceT.getItemDropId());
                this.player.getPlayerBag().onEvent(new ItemNpcWorkToWarehouseEvent(this.player, this.npcWorker.getRegionId(), itemsSubGroup, this.objectId));
                this.player.sendPacket(new SMChangedWorkerStatus(this.npcWorker));
                if (this.npcWorker.getCount() > 0 && this.npcWorker.getNpcWork().canAct(this.npcWorker, this.player)) {
                    this.npcWorker.setCount(this.npcWorker.getCount() - 1);
                    this.npcWorker.setActionPoints(this.npcWorker.getActionPoints() - 1);
                    this.npcWorker.setStartTime(GameTimeService.getServerTimeInSecond());
                    this.player.sendPacket(new SMRestartPlantWorkingToNpcWorker(this.npcWorker));
                    break;
                }
                this.npcWorker.stopWorking();
                this.player.sendPacket(new SMStopPlantWorkingToNpcWorker(this.objectId, this.player.getGameObjectId(), 0));
                break;
            }
            case PlantRentHouseLargeCraft: {
                this.npcWorker.addExp(39);
                this.player.sendPacket(new SMChangedWorkerStatus(this.npcWorker));
                if (this.npcWorker.getCount() > 0) {
                    this.npcWorker.setCount(this.npcWorker.getCount() - 1);
                    this.npcWorker.setActionPoints(this.npcWorker.getActionPoints() - 1);
                    this.npcWorker.setStartTime(GameTimeService.getServerTimeInSecond());
                    this.player.sendPacket(new SMRestartPlantWorkingToNpcWorker(this.npcWorker));
                    break;
                }
                this.npcWorker.stopWorking();
                this.player.sendPacket(new SMStopPlantWorkingToNpcWorker(this.objectId, this.player.getGameObjectId(), 0));
                break;
            }
            case HarvestWorking: {
                this.player.sendPacket(new SMChangedWorkerStatus(this.npcWorker));
                if (this.npcWorker.getCount() > 0) {
                    this.npcWorker.setCount(this.npcWorker.getCount() - 1);
                    this.npcWorker.setStartTime(GameTimeService.getServerTimeInSecond());
                    this.player.sendPacket(new SMRestartPlantWorkingToNpcWorker(this.npcWorker));
                    break;
                }
                this.npcWorker.stopWorking();
                this.player.sendPacket(new SMStopPlantWorkingToNpcWorker(this.objectId, this.player.getGameObjectId(), 0));
                break;
            }
        }
    }

    @Override
    public boolean canAct() {
        this.npcWorker = this.npcWorkerController.getNpcWorker(this.objectId);
        if (npcWorker == null || npcWorker.getStartTime() == 0L)
            return false;

        if (npcWorker.getActionPoints() < 1) {
            npcWorker.stopWorking();
            npcWorker.setStartTime(0);
            npcWorker.setActionPoints(0);
            player.sendPacket(new SMNak(EStringTable.eErrNoNpcWorkerActionPointIsLack, CMQuickCompleteNpcWork.class));
            player.sendPacket(new SMChangedWorkerStatus(npcWorker));
            player.sendPacket(new SMStopPlantWorkingToNpcWorker(objectId, this.player.getGameObjectId(), 0));
            return false;
        }

        //System.out.println("canAct=WorkType: " + npcWorker.getNpcWork().getWorkType().name());
        switch (this.npcWorker.getNpcWork().getWorkType()) {
            case Upgrade: {
                final long completeTime = this.npcWorker.getStartTime() + 86400L;
                if (this.completeType == 1)
                    if (!this.player.getPlayerBag().onEvent(new InstantCashItemEvent(this.player, EInstantCashType.CompleteNpcWorkerUpgrade, (int) (completeTime - GameTimeService.getServerTimeInSecond()), 0L, CMQuickCompleteNpcWork.class))) {
                        return false;
                    } else {
                        if (GameTimeService.getServerTimeInSecond() < completeTime)
                            return false;
                    }
                break;
            }
            case PlantRentHouse: {
                final PlantRentHouseWork plantRentHouseWork = (PlantRentHouseWork) this.npcWorker.getNpcWork();
                this.itemExchangeSourceT = ItemExchangeSourceData.getInstance().getTemplate(plantRentHouseWork.getCraftId());
                if (this.completeType == 1 && !this.player.getPlayerBag().onEvent(new InstantCashItemEvent(this.player, EInstantCashType.CompleteNpcWorking, (int) (GameTimeService.getServerTimeInSecond() - this.npcWorker.getStartTime()), this.count, CMQuickCompleteNpcWork.class)))
                    return false;
                break;
            }
            case PlantRentHouseLargeCraft: {
                final PlantRentHouseLargeCraftWork largeCraftWork = (PlantRentHouseLargeCraftWork) this.npcWorker.getNpcWork();
                this.itemExchangeSourceT = ItemExchangeSourceData.getInstance().getTemplate(largeCraftWork.getCraftId());
                if (!this.player.getHouseStorage().onEvent(new UpdateHouseLargeCraftEvent(this.player, largeCraftWork.getHouseId(), largeCraftWork.getIndex(), this.npcWorker.getRegionId(), this.npcWorker.getObjectId())))
                    return false;
                break;
            }
            case PlantZone: {
                final PlantZoneWork plantZoneWork = (PlantZoneWork) this.npcWorker.getNpcWork();
                this.itemExchangeSourceT = ItemExchangeSourceData.getInstance().getTemplate(plantZoneWork.getPlantExchangeKey());
                break;
            }
        }

        if (this.itemExchangeSourceT != null) {
            int defaultWorkTime  = 600000; // ToClient_getNpcWorkingBaseTime()
            if (npcWorker.getNpcWork().getWorkType() == ENpcWorkingType.PlantRentHouse
                    || npcWorker.getNpcWork().getWorkType() == ENpcWorkingType.PlantRentHouseLargeCraft)
                defaultWorkTime = 300000; // ToClient_getNpcWorkingBaseTimeForHouse()
            double workSpeed     = npcWorker.getWorkerSpeed() + 50;
            int workVolume       = itemExchangeSourceT.getCraftingTime() / 1000;
            double totalWorkTime = Math.ceil(workVolume / workSpeed) * (defaultWorkTime / 1000); // + distance [404] / (worker.getMovementSpeed() / 100) * 2;
            if (Double.isNaN(totalWorkTime) || Double.isInfinite(totalWorkTime))
                totalWorkTime = 60;
            if (System.currentTimeMillis() / 1000 < npcWorker.getStartTime() + totalWorkTime) {
                if (player != null) {
                    log.warn("Player {} tried to potentially skip worker time.", player.getName());
                    log.warn("[{}] Character={}, Speed={}, TotalWorkTime={}, Remaining={}, WorkStartTime={} CraftKey={}",
                            player.getName(),
                            npcWorker.getCharacterKey(),
                            workSpeed,
                            totalWorkTime,
                            (npcWorker.getStartTime() + totalWorkTime - System.currentTimeMillis() / 1000),
                            npcWorker.getStartTime(),
                            itemExchangeSourceT.getIndex());
                    player.sendPacketNoFlush(new SMNak(EStringTable.eErrNoCantSecurityModule, CMQuickCompleteNpcWork.class));
                    player.sendPacket(new SMSetGameTime());
                }
                return false;
            }
        }
        return true;
    }
}