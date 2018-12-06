package com.bdoemu.gameserver.model.trade.events;

public interface ITradeEvent {
    void onEvent();

    boolean canAct();
}
