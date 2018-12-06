package com.bdoemu.gameserver.model.stats;

import com.bdoemu.commons.concurrent.CloseableReentrantLock;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.network.sendable.SMSetCharacterRelatedPoints;
import com.bdoemu.core.network.sendable.SMSetMyServantPoints;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.stats.elements.Element;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MpStat extends Stat {
    private long mpCacheCount;
    private ScheduledFuture<?> mpRegenTask;

    public MpStat(final Creature owner) {
        super(owner);
    }

    public void startMpRegenTask() {
        if (this.owner.isDead() || (this.mpRegenTask != null && !this.mpRegenTask.isDone()) || this.owner.getGameStats().getMPRegen().getIntMaxValue() <= 0) {
            return;
        }
        this.mpRegenTask = ThreadPool.getInstance().scheduleEffectAtFixedRate(() -> {
            if (this.owner.isDead()) {
                this.stopMpRegenTask();
            } else {
                this.addMP(this.owner.getGameStats().getMPRegen().getIntMaxValue());
            }
        }, 5L, 5L, TimeUnit.SECONDS);
    }

    public void stopMpRegenTask() {
        if (this.mpRegenTask != null) {
            this.mpRegenTask.cancel(true);
        }
    }

    @Override
    public boolean removeElement(final Element element) {
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            this.maxValue -= element.getValue();
            if (this.value > this.maxValue) {
                this.value = this.maxValue;
            }
            return this.elements.remove(element);
        }
    }

    public boolean isFull() {
        return this.value >= this.maxValue;
    }

    @Override
    public double fill() {
        double diff;
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            diff = this.getMaxMp() - this.getCurrentMp();
            this.value = this.maxValue;
            this.broadcastMPPackets((int) diff);
        }
        return diff;
    }

    @Override
    public void fill(int percentage) {
        percentage = Math.min(percentage, 100);
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            this.value = this.getMaxValue() * percentage / 100.0;
            if (this.value == this.getMaxValue()) {
                this.stopMpRegenTask();
            } else {
                this.startMpRegenTask();
            }
            ++this.mpCacheCount;
            this.broadcastMPPackets(0);
        }
    }

    public int getMpPercentage() {
        return (int) (this.value / this.maxValue * 100.0);
    }

    public long getMpCacheCount() {
        return this.mpCacheCount;
    }

    public int getCurrentMp() {
        return this.getIntValue();
    }

    public void setCurrentMp(final double value) {
        this.value = value;
    }

    public int getMaxMp() {
        return this.getIntMaxValue();
    }

    public boolean addMP(final int addMp) {
        final int mpDiff = this.owner.getGameStats().getMp().updateMp(addMp);
        return mpDiff != 0;
    }

    public int updateMp(final double mp) {
        double mpDiff = mp;
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            if (this.owner.isDead()) {
                return 0;
            }
            final double newValue = mpDiff + this.value;
            if (newValue < 0.0) {
                return 0;
            }
            if (newValue > this.maxValue) {
                mpDiff = this.maxValue - this.value;
            }
            this.value += mpDiff;
            ++this.mpCacheCount;
            if (mpDiff != 0.0) {
                this.broadcastMPPackets((int) mpDiff);
            }
            if (this.value == this.getMaxValue()) {
                this.stopMpRegenTask();
            } else {
                this.startMpRegenTask();
            }
        }
        return (int) mpDiff;
    }

    private void broadcastMPPackets(final int mpDiff) {
        if (this.owner.isPlayer()) {
            this.owner.sendPacket(new SMSetCharacterRelatedPoints(this.owner, mpDiff));
        } else {
            this.owner.sendBroadcastPacket(new SMSetCharacterRelatedPoints(this.owner, mpDiff));
            if (this.owner.isVehicle()) {
                final Player masterOwner = (Player) this.owner.getOwner();
                if (masterOwner != null && this.owner.isSpawned()) {
                    masterOwner.sendPacket(new SMSetMyServantPoints(masterOwner));
                }
            }
        }
    }
}