// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.ServantEquipOnOff;
import com.bdoemu.gameserver.model.creature.servant.enums.ERidingSlotType;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collection;
import java.util.List;

public class SMAddVehicles extends SendablePacket<GameClient> {
    private static final int maximum = 9;
    private final Collection<Servant> vehicles;

    public SMAddVehicles(final Collection<Servant> vehicles) {
        if (vehicles.size() > 9) {
            throw new IllegalArgumentException("Maximum size 9");
        }
        this.vehicles = vehicles;
    }

    public static int getMaximum() {
        return 9;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(1);
        buffer.writeC(0);
        buffer.writeC(0);
        buffer.writeH(this.vehicles.size());
        for (final Servant vehicle : this.vehicles) {
            final Location location = vehicle.getLocation();
            buffer.writeD(vehicle.getGameObjectId());
            buffer.writeD(-1024);
            buffer.writeF(location.getX());
            buffer.writeF(location.getZ());
            buffer.writeF(location.getY());
            buffer.writeF(location.getX());
            buffer.writeF(location.getZ());
            buffer.writeF(location.getY());
            buffer.writeF(location.getCos());
            buffer.writeD(0);
            buffer.writeF(location.getSin());
            buffer.writeH(vehicle.getCreatureId());
            buffer.writeH(vehicle.getCharacterGroup());
            buffer.writeF(vehicle.getGameStats().getHp().getCurrentHp());
            buffer.writeF(vehicle.getGameStats().getHp().getMaxHp());
            buffer.writeD(0);
            buffer.writeQ(vehicle.getGuildCache());
            buffer.writeQ(0L);
            buffer.writeH(0);
            buffer.writeQ(vehicle.getPartyCache());
            buffer.writeC(-1);
            buffer.writeD(vehicle.getActionStorage().getActionHash());
            buffer.writeD(vehicle.getActionIndex());
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeH(0);
            buffer.writeC(vehicle.getActionStorage().getAction().getActionChartActionT().getApplySpeedBuffType().ordinal());
            buffer.writeD(vehicle.getActionStorage().getAction().getSpeedRate());
            buffer.writeD(vehicle.getActionStorage().getAction().getSlowSpeedRate());
            buffer.writeD(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeB(new byte[600]);
            buffer.writeQ(vehicle.getChargeUserEffectEndTime());
            buffer.writeC(vehicle.getMovementController().isMoving());
            final Location destination = vehicle.getMovementController().getDestination();
            buffer.writeF(destination.getX());
            buffer.writeF(destination.getZ());
            buffer.writeF(destination.getY());
            final Location origin = vehicle.getMovementController().getOrigin();
            buffer.writeF(origin.getX());
            buffer.writeF(origin.getZ());
            buffer.writeF(origin.getY());
            buffer.writeB(new byte[60]);
            final Player owner = vehicle.getOwner();
            buffer.writeD((owner != null) ? 159 : 0);
            buffer.writeD(vehicle.getOwnerGameObjId());
            buffer.writeQ(456427921L);
            buffer.writeQ(456427921L);
            buffer.writeQ(GameTimeService.getServerTimeInMillis());
            buffer.writeQ((vehicle.getObjectId() < 0L) ? 0L : vehicle.getObjectId());
            buffer.writeD((vehicle.getOwner() != null) ? vehicle.getBasicCacheCount() : 0);
            buffer.writeD(vehicle.getEquipSlotCacheCount());
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeQ((long) vehicle.getGameStats().getWeight().getIntMaxValue());
            buffer.writeD(vehicle.getGameStats().getWeight().getIntValue());
            buffer.writeD(-1);
            buffer.writeD(-1);
            buffer.writeD(-1);
            buffer.writeD(-1);
            buffer.writeD(-1);
            buffer.writeD(-1);
            for (final ERidingSlotType ridingSlotType : ERidingSlotType.values()) {
                if (!ridingSlotType.isNone()) {
                    final Player rider = vehicle.getCurrentRiders().get(ridingSlotType);
                    buffer.writeD((rider == null) ? -2048 : rider.getGameObjectId());
                }
            }
            buffer.writeB(new byte[384]);
            buffer.writeD(vehicle.getGameStats().getAccelerationRate().getIntMaxValue());
            buffer.writeD(vehicle.getGameStats().getMaxMoveSpeedRate().getIntMaxValue());
            buffer.writeD(vehicle.getGameStats().getCorneringSpeedRate().getIntMaxValue());
            buffer.writeD(vehicle.getGameStats().getBrakeSpeedRate().getIntMaxValue());
            final List<Servant> linkedServants = vehicle.getLinkedServants();
            Servant linkedServant1 = null;
            Servant linkedServant2 = null;
            Servant linkedServant3 = null;
            Servant linkedServant4 = null;
            if (!linkedServants.isEmpty()) {
                linkedServant1 = linkedServants.get(0);
                if (linkedServants.size() > 1) {
                    linkedServant2 = linkedServants.get(1);
                }
                if (linkedServants.size() > 2) {
                    linkedServant3 = linkedServants.get(2);
                }
                if (linkedServants.size() > 3) {
                    linkedServant4 = linkedServants.get(3);
                }
            }
            buffer.writeQ((linkedServant1 != null) ? linkedServant1.getObjectId() : 0L);
            buffer.writeQ((linkedServant2 != null) ? linkedServant2.getObjectId() : 0L);
            buffer.writeQ((linkedServant3 != null) ? linkedServant3.getObjectId() : 0L);
            buffer.writeQ((linkedServant4 != null) ? linkedServant4.getObjectId() : 0L);
            buffer.writeH((linkedServant1 != null) ? linkedServant1.getCreatureId() : 0);
            buffer.writeH((linkedServant2 != null) ? linkedServant2.getCreatureId() : 0);
            buffer.writeH((linkedServant3 != null) ? linkedServant3.getCreatureId() : 0);
            buffer.writeH((linkedServant4 != null) ? linkedServant4.getCreatureId() : 0);
            buffer.writeD((linkedServant1 != null) ? linkedServant1.getEquipSlotCacheCount() : 0);
            buffer.writeD((linkedServant2 != null) ? linkedServant2.getEquipSlotCacheCount() : 0);
            buffer.writeD((linkedServant3 != null) ? linkedServant3.getEquipSlotCacheCount() : 0);
            buffer.writeD((linkedServant4 != null) ? linkedServant4.getEquipSlotCacheCount() : 0);
            buffer.writeD((linkedServant1 != null) ? linkedServant1.getActionIndex() : 0);
            buffer.writeD((linkedServant2 != null) ? linkedServant2.getActionIndex() : 0);
            buffer.writeD((linkedServant3 != null) ? linkedServant3.getActionIndex() : 0);
            buffer.writeD((linkedServant4 != null) ? linkedServant4.getActionIndex() : 0);
            buffer.writeF(vehicle.getGameStats().getMp().getValue());
            buffer.writeF(vehicle.getGameStats().getMp().getMaxValue());
            ServantEquipOnOff servantEquipOnOff = null;
            if (owner != null) {
                servantEquipOnOff = owner.getServantController().getServantEquipOnOff();
            }
            buffer.writeD((servantEquipOnOff != null) ? servantEquipOnOff.getVehicleEquipOnOff() : 0);
            buffer.writeD((servantEquipOnOff != null) ? servantEquipOnOff.getCamelEquipOnOff() : 0);
            buffer.writeD((servantEquipOnOff != null) ? servantEquipOnOff.getUnk1EquipOnOff() : 0);
            buffer.writeD((servantEquipOnOff != null) ? servantEquipOnOff.getUnk2EquipOnOff() : 0);
            buffer.writeD((servantEquipOnOff != null) ? servantEquipOnOff.getShipEquipOnOff() : 0);
            buffer.writeQ(vehicle.getInteractedCoolTime());
            buffer.writeB(new byte[28]);
            buffer.writeD(-1024);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeC(0);
            buffer.writeD(-1024);
            buffer.writeC(vehicle.hasTwoSeater());
        }
    }
}
