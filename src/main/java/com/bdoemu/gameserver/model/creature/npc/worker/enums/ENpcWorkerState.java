// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc.worker.enums;

public enum ENpcWorkerState {
    WorkSupervisor,
    WorkerMarket;

    public boolean isWorkSupervisor() {
        return this == ENpcWorkerState.WorkSupervisor;
    }

    public boolean isWorkerMarket() {
        return this == ENpcWorkerState.WorkerMarket;
    }
}
