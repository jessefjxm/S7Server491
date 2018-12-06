package com.bdoemu.gameserver.model.actions.enums;

public enum EAttachTerrainType {
    Ground,
    Air,
    OnWater,
    UnderWater,
    OnWaterNonDynamic,
    ToAir;

    public boolean isUnderWater() {
        return this == EAttachTerrainType.UnderWater;
    }
}