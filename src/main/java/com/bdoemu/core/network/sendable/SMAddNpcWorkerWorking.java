package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.npc.worker.NpcWorker;
import com.bdoemu.gameserver.model.creature.npc.worker.works.*;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collection;

public class SMAddNpcWorkerWorking extends SendablePacket<GameClient> {
    private final Collection<NpcWorker> npcWorkers;
    private final boolean isFirs;
    private final int type;

    public SMAddNpcWorkerWorking(final Collection<NpcWorker> npcWorkers, final boolean isFirs, final int type) {
        this.npcWorkers = npcWorkers;
        this.isFirs     = isFirs;
        this.type       = type;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.isFirs);
        buffer.writeC(this.type);
        buffer.writeH(this.npcWorkers.size());
        for (final NpcWorker npcWorker : this.npcWorkers) {
            final ANpcWork npcWork = npcWorker.getNpcWork();
            buffer.writeC(npcWork.getWorkType().getId());
            buffer.writeQ(npcWorker.getObjectId());
            buffer.writeH(npcWorker.getCharacterKey());
            buffer.writeQ(npcWorker.getStartTime());
            buffer.writeH(npcWorker.getCount());
            buffer.writeQ(npcWorker.getAccountId());
            buffer.writeS(npcWorker.getFamily(), 62);
            buffer.writeQ(0);
            buffer.writeD(npcWorker.getWaypointKey());
            buffer.writeD(npcWorker.getLevel());
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            for (final int skill : npcWorker.getArrPassiveSkills()) {
                buffer.writeH(skill);
            }
            buffer.writeD(npcWorker.getWorkingState());
            buffer.writeQ(npcWorker.getUpdateTime());
            switch (npcWork.getWorkType()) {
                case PlantZone: {
                    final PlantZoneWork plantZoneWork = (PlantZoneWork) npcWork;
                    buffer.writeH(plantZoneWork.getPlantExchangeKey());
                    buffer.writeD(plantZoneWork.getNodeId());
                    buffer.writeQ(1000000L);
                    buffer.writeQ(0L);
                    buffer.writeD(0);
                    buffer.writeD(GameTimeService.getServerTimeInSecond()); // -620560446
                    break;
                }
                case Upgrade: {
                    final UpgradeWork upgradeWork = (UpgradeWork) npcWork;
                    buffer.writeD(upgradeWork.getWaypoint());
                    buffer.writeH(0);
                    buffer.writeQ(0L);
                    buffer.writeQ(0L);
                    buffer.writeD(0);
                    buffer.writeD(0);
                    break;
                }
                case PlantRentHouse: {
                    final PlantRentHouseWork plantRentHouseWork = (PlantRentHouseWork) npcWork;
                    buffer.writeH(plantRentHouseWork.getCraftId());
                    buffer.writeD(-plantRentHouseWork.getHouseId());
                    buffer.writeQ(255L);
                    buffer.writeQ(0L);
                    buffer.writeQ(0L);
                    break;
                }
                case PlantRentHouseLargeCraft: {
                    final PlantRentHouseLargeCraftWork plantRentHouseLargeCraftWork = (PlantRentHouseLargeCraftWork) npcWork;
                    buffer.writeH(plantRentHouseLargeCraftWork.getCraftId());
                    buffer.writeD(plantRentHouseLargeCraftWork.getHouseId());
                    buffer.writeD(plantRentHouseLargeCraftWork.getIndex());
                    buffer.writeC(0);
                    buffer.writeD(0);
                    buffer.writeH(0);
                    buffer.writeD(0);
                    buffer.writeD(0);
                    buffer.writeD(0);
                    buffer.writeC(0);
                    break;
                }
                case HarvestWorking: {
                    final HarvestWorkingWork harvestWorkingWork = (HarvestWorkingWork) npcWork;
                    buffer.writeQ(harvestWorkingWork.getTentObjId());
                    buffer.writeH(28025);
                    final Location loc = harvestWorkingWork.getLoc();
                    buffer.writeF(loc.getX());
                    buffer.writeF(loc.getZ());
                    buffer.writeF(loc.getY());
                    buffer.writeD(0);
                    buffer.writeD(0);
                    break;
                }
            }
        }
    }
}
