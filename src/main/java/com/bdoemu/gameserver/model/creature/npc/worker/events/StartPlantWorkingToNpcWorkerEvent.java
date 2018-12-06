package com.bdoemu.gameserver.model.creature.npc.worker.events;

import com.bdoemu.core.network.sendable.SMAddNpcWorkerWorking;
import com.bdoemu.gameserver.dataholders.ItemExchangeSourceData;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorkerController;
import com.bdoemu.gameserver.model.creature.npc.worker.enums.ENpcWorkingType;
import com.bdoemu.gameserver.model.creature.npc.worker.works.ANpcWork;
import com.bdoemu.gameserver.model.creature.npc.worker.works.PlantRentHouseLargeCraftWork;
import com.bdoemu.gameserver.model.creature.npc.worker.works.PlantRentHouseWork;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemExchangeSourceT;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collections;
import java.util.List;

public class StartPlantWorkingToNpcWorkerEvent implements INpcWorkerEvent {
    private Player player;
    private long objectId;
    private int count;
    private ENpcWorkingType npcWorkingType;
    private NpcWorkerController npcWorkerController;
    private NpcWorker npcWorker;
    private ANpcWork npcWork;

    public StartPlantWorkingToNpcWorkerEvent(final Player player, final long objectId, final int count, final ANpcWork npcWork) {
        this.player = player;
        this.objectId = objectId;
        this.count = count;
        this.npcWork = npcWork;
        this.npcWorkingType = npcWork.getWorkType();
        this.npcWorkerController = player.getNpcWorkerController();
    }

    @Override
    public void onEvent() {
        this.npcWorker.setStartTime(GameTimeService.getServerTimeInSecond());
        this.npcWorker.setNpcWork(this.npcWork);
        switch (this.npcWorkingType) {
            case Upgrade: {
                this.npcWorker.setWorkingState(-1);
                this.npcWorker.setUpdateTime(0L);
                break;
            }
            case PlantZone:
            case PlantRentHouse:
            case PlantRentHouseLargeCraft: {
                this.npcWorker.setWorkingState(-1);
                this.npcWorker.setUpdateTime(0L);
                this.npcWorker.setCount(--this.count);
                this.npcWorker.setActionPoints(this.npcWorker.getActionPoints() - 1);
                if (this.npcWorkingType == ENpcWorkingType.PlantRentHouse) {
                    final PlantRentHouseWork plantRentHouseWork = (PlantRentHouseWork) this.npcWork;
                    this.player.getObserveController().notifyObserver(EObserveType.craftItem, plantRentHouseWork.getHouseId());
                    player.getObserveController().notifyObserver(EObserveType.npcworkermaking, plantRentHouseWork.getCraftId(), count + 1);
                } else if (this.npcWorkingType == ENpcWorkingType.PlantRentHouseLargeCraft) {
                    final PlantRentHouseLargeCraftWork plantRentHouseLargeCraftWork = (PlantRentHouseLargeCraftWork) this.npcWork;
                    this.player.getObserveController().notifyObserver(EObserveType.craftItem, plantRentHouseLargeCraftWork.getHouseId());
                }
                break;
            }
            case HarvestWorking: {
                this.npcWorker.setCount(--this.count);
                this.npcWorker.setWorkingState(900);
                this.npcWorker.setActionPoints(this.npcWorker.getActionPoints() - 1);
                this.npcWorker.setUpdateTime(GameTimeService.getServerTimeInSecond());
                break;
            }
        }
        this.player.sendPacket(new SMAddNpcWorkerWorking(Collections.singletonList(this.npcWorker), false, 0));
    }

    @Override
    public boolean canAct() {
        this.npcWorker = this.npcWorkerController.getNpcWorker(this.objectId);
        return this.npcWorker != null && this.npcWorker.getStartTime() <= 0L && (this.npcWorkingType == ENpcWorkingType.Upgrade || (this.count >= 1 && this.count <= this.npcWorker.getActionPoints())) && this.npcWork != null && this.npcWork.canAct(this.npcWorker, this.player);
    }
}
