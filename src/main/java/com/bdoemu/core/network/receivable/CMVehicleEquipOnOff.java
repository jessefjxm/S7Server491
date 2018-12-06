// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMVehicleEquipOnOff;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.ServantEquipOnOff;

public class CMVehicleEquipOnOff extends ReceivablePacket<GameClient> {
    private int servantGameObjId;
    private int vehicleEquipOnOff;
    private int camelEquipOnOff;
    private int unk1EquipOnOff;
    private int unk2EquipOnOff;
    private int shipEquipOnOff;

    public CMVehicleEquipOnOff(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.servantGameObjId = this.readD();
        this.vehicleEquipOnOff = this.readD();
        this.camelEquipOnOff = this.readD();
        this.unk1EquipOnOff = this.readD();
        this.unk2EquipOnOff = this.readD();
        this.shipEquipOnOff = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Servant servant = player.getServantController().getServant(this.servantGameObjId);
            if (servant != null) {
                final ServantEquipOnOff servantEquipOnOff = player.getServantController().getServantEquipOnOff();
                servantEquipOnOff.setVehicleEquipOnOff(this.vehicleEquipOnOff);
                servantEquipOnOff.setCamelEquipOnOff(this.camelEquipOnOff);
                servantEquipOnOff.setUnk1EquipOnOff(this.unk1EquipOnOff);
                servantEquipOnOff.setUnk2EquipOnOff(this.unk2EquipOnOff);
                servantEquipOnOff.setShipEquipOnOff(this.shipEquipOnOff);
                servant.sendBroadcastPacket(new SMVehicleEquipOnOff(servant));
            }
        }
    }
}
