package com.bdoemu.gameserver.model.stats;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.gameserver.model.creature.Creature;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Nullbyte
 */
public class StunPointsStat extends Stat {
    private ScheduledFuture<?> _stunPointsTask;
    private int _stunPoints;

    public StunPointsStat(final Creature owner) {
        super(owner);
        _stunPoints = 100;
    }

    public void startTask() {
        if (owner.isDead() || (_stunPointsTask != null && !_stunPointsTask.isDone()))
            return;

        _stunPointsTask = (ScheduledFuture<?>) ThreadPool.getInstance().scheduleEffectAtFixedRate(() -> {
            if (owner.isDead())
                stopTask();
            else
                addStunPoints(1);
        }, 10L, 10L, TimeUnit.SECONDS);
    }

    public void stopTask() {
        if (_stunPointsTask != null) {
            _stunPointsTask.cancel(true);
        }
    }

    public int getStunPoints() {
        return _stunPoints;
    }

    public void addStunPoints(int points) {
        _stunPoints += 1;
        updateClient();
    }

    public void resetStunPoints() {
        _stunPoints = 0;
    }

    private void updateClient() {
        //owner.sendPacketNoFlush(new SMSetStunPoints((Player) owner);
    }
}