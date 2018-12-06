package com.bdoemu.gameserver.model.creature.servant;

public class ServantEquipOnOff {
    private int vehicleEquipOnOff;
    private int camelEquipOnOff;
    private int unk1EquipOnOff;
    private int unk2EquipOnOff;
    private int shipEquipOnOff;

    public int getCamelEquipOnOff() {
        return this.camelEquipOnOff;
    }

    public void setCamelEquipOnOff(final int camelEquipOnOff) {
        this.camelEquipOnOff = camelEquipOnOff;
    }

    public int getShipEquipOnOff() {
        return this.shipEquipOnOff;
    }

    public void setShipEquipOnOff(final int shipEquipOnOff) {
        this.shipEquipOnOff = shipEquipOnOff;
    }

    public int getUnk1EquipOnOff() {
        return this.unk1EquipOnOff;
    }

    public void setUnk1EquipOnOff(final int unk1EquipOnOff) {
        this.unk1EquipOnOff = unk1EquipOnOff;
    }

    public int getUnk2EquipOnOff() {
        return this.unk2EquipOnOff;
    }

    public void setUnk2EquipOnOff(final int unk2EquipOnOff) {
        this.unk2EquipOnOff = unk2EquipOnOff;
    }

    public int getVehicleEquipOnOff() {
        return this.vehicleEquipOnOff;
    }

    public void setVehicleEquipOnOff(final int vehicleEquipOnOff) {
        this.vehicleEquipOnOff = vehicleEquipOnOff;
    }
}
