package com.bdoemu.gameserver.model.creature.player.services;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.configs.LocalizingOptionConfig;
import com.bdoemu.core.network.sendable.SMBeginDelayedLogout;
import com.bdoemu.gameserver.model.creature.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class PlayerLeaveWorldService {
    private static final Logger log = LoggerFactory.getLogger(PlayerLeaveWorldService.class);
    private static final HashMap<Long, Future<?>> tasks = new HashMap<>();

    public static void leaveWorld(final Player player, final boolean isSwitch) {
        long time = 0L;
        if (player == null) {
            return;
        }
        if (player.getLocation().getRegion() == null) {
            PlayerLeaveWorldService.log.warn("Region is null while leaveWorld(): ClientState={}", player.getClient().getState());
            return;
        }
        if (!player.getRegion().getTemplate().isSafe()) {
            time = LocalizingOptionConfig.LOGOUT_WAITING_TIME;
        }
        final long objectId = player.getObjectId();
        synchronized (PlayerLeaveWorldService.tasks) {
            if (isInLeaveWorldState(objectId)) {
                return;
            }
            player.sendPacket(new SMBeginDelayedLogout(time));
            final Future<?> task = ThreadPool.getInstance().scheduleGeneral(new PlayerLeaveWorldTask(player), time, TimeUnit.MILLISECONDS);
            PlayerLeaveWorldService.tasks.put(objectId, task);
        }
    }

    public static void cancelLeaveWorld(final Player player) {
        final long objectId = player.getObjectId();
        if (!isInLeaveWorldState(objectId)) {
            return;
        }
        synchronized (PlayerLeaveWorldService.tasks) {
            final Future<?> task = PlayerLeaveWorldService.tasks.remove(objectId);
            if (task != null) {
                task.cancel(true);
            }
        }
    }

    public static boolean isInLeaveWorldState(final long objectId) {
        return PlayerLeaveWorldService.tasks.containsKey(objectId);
    }

    private static final class PlayerLeaveWorldTask implements Runnable {
        private final Player player;

        public PlayerLeaveWorldTask(final Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            PlayerLeaveWorldService.tasks.remove(this.player.getObjectId());
            this.player.getClient().closeForce();
        }
    }
}
