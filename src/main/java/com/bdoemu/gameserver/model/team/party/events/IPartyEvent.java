package com.bdoemu.gameserver.model.team.party.events;

public interface IPartyEvent {
    void onEvent();

    boolean canAct();
}