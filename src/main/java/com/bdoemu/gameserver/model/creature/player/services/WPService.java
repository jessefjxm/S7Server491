package com.bdoemu.gameserver.model.creature.player.services;

import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Service")
public class WPService extends APeriodicTaskService {
    private static class Holder {
        static final WPService INSTANCE = new WPService();
    }

    private WPService() {
        super(3L, TimeUnit.MINUTES);
    }

    public static WPService getInstance() {
        return Holder.INSTANCE;
    }

    public void run() {
        for (final Player player : World.getInstance().getPlayers()) {
            player.addWp(player.getGameStats().getWPRegenStat().getIntMaxValue());
        }
    }
}
