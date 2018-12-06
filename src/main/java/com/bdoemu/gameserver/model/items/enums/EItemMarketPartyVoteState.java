// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.enums;

public enum EItemMarketPartyVoteState {
    None,
    Dice,
    Buy,
    Cancel;

    public boolean isNone() {
        return this == EItemMarketPartyVoteState.None;
    }

    public boolean isDice() {
        return this == EItemMarketPartyVoteState.Dice;
    }
}
