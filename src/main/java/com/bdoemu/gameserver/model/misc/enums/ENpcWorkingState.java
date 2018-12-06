package com.bdoemu.gameserver.model.misc.enums;

public enum ENpcWorkingState {
    Undefined(-1),
    HarvestWorking_MoveTo(900),
    HarvestWorking_Working(901),
    HarvestWorking_Return(902);

    private int id;

    ENpcWorkingState(final int id) {
        this.id = id;
    }
}