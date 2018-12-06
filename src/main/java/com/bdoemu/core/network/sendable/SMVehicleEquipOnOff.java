package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.ServantEquipOnOff;

public class SMVehicleEquipOnOff extends SendablePacket<GameClient> {
    private Servant servant;
    private ServantEquipOnOff servantEquipOnOff;

    public SMVehicleEquipOnOff(final Servant servant) {
        this.servant = servant;
        this.servantEquipOnOff = servant.getOwner().getServantController().getServantEquipOnOff();
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.servant.getGameObjectId());
        buffer.writeD(this.servant.getOwner().getGameObjectId());
        buffer.writeD(this.servantEquipOnOff.getVehicleEquipOnOff());
        buffer.writeD(this.servantEquipOnOff.getCamelEquipOnOff());
        buffer.writeD(this.servantEquipOnOff.getUnk1EquipOnOff());
        buffer.writeD(this.servantEquipOnOff.getUnk2EquipOnOff());
        buffer.writeD(this.servantEquipOnOff.getShipEquipOnOff());
        buffer.writeD(0);
    }
}
