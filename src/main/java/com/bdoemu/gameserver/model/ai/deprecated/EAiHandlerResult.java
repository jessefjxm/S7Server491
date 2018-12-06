// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.ai.deprecated;

//@Deprecated
public enum EAiHandlerResult {
    NONE,
    CHANGE_STATE,
    BYPASS;

    public boolean isBypass() {
        return this == EAiHandlerResult.BYPASS;
    }

    public boolean isChangeState() {
        return this == EAiHandlerResult.CHANGE_STATE;
    }

    public boolean isNone() {
        return this == EAiHandlerResult.NONE;
    }
}
