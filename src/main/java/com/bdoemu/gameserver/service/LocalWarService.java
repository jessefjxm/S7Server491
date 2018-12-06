package com.bdoemu.gameserver.service;

import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.pvp.LocalWarStatus;

import java.util.ArrayList;
import java.util.List;

@StartupComponent("Service")
public class LocalWarService {
    private List<LocalWarStatus> localWars;

    private LocalWarService() {
        (this.localWars = new ArrayList<>()).add(new LocalWarStatus(ServerConfig.SERVER_CHANNEL_ID));
    }

    public static LocalWarService getInstance() {
        return Holder.INSTANCE;
    }

    public List<LocalWarStatus> getLocalWars() {
        return this.localWars;
    }

    public LocalWarStatus getLocalWarStatus() {
        return this.localWars.get(0);
    }

    public void reward() {
        //?
    }

    public boolean hasParticipant(final Player player) {
        return this.getLocalWarStatus().hasParticipant(player);
    }

    private static class Holder {
        static final LocalWarService INSTANCE = new LocalWarService();
    }
}
