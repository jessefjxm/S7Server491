// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.gameserver.model.chat.enums.EChatResponseType;

abstract class AbstractCommandHandler {
    private static final Object[] rejectResult;
    private static final Object[] acceptResult;

    static {
        rejectResult = new Object[]{EChatResponseType.Rejected};
        acceptResult = new Object[]{EChatResponseType.Accepted};
    }

    protected static Object[] getAcceptResult() {
        return AbstractCommandHandler.acceptResult;
    }

    protected static Object[] getAcceptResult(final String message) {
        return new Object[]{AbstractCommandHandler.acceptResult[0], message};
    }

    protected static Object[] getRejectResult() {
        return AbstractCommandHandler.rejectResult;
    }

    protected static Object[] getRejectResult(final String message) {
        return new Object[]{AbstractCommandHandler.rejectResult[0], message};
    }
}
