package com.bdoemu.gameserver.model.stats;

import com.bdoemu.commons.concurrent.CloseableReentrantLock;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.configs.BattleOptionConfig;
import com.bdoemu.core.network.sendable.SMUpdateGuardGauge;
import com.bdoemu.gameserver.model.creature.Creature;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Nullbyte
 */
public class StunGaugeStat extends Stat {
    // Task to update our block gauge.
    private ScheduledFuture<?> _blockGaugeUpdateTask;
    // Keeper for current stun gauge types
    private Double _stunGaugeCurrentData;
    // Keeper for maximum stun gauge types
    private Double _stunGaugeMaxData;
    // Keeper for monitoring max health.
    private double _savedMaxHealth;

    /**
     * Default constructor
     *
     * @param owner Owner whos stun gauge is being updated.
     */
    public StunGaugeStat(final Creature owner) {
        super(owner);
        onMaxHealthChanged();
    }

    /**
     * An monitored event is executed to update these values each time you exit or enter the stun or other.
     */
    public void onMaxHealthChanged() {
        // Update max health
        _savedMaxHealth = owner.getGameStats().getHp().getMaxValue();

        // Update Maximum Data
        _stunGaugeMaxData = 1000.0 + _savedMaxHealth * BattleOptionConfig.BLOCK_GAUGE_RATE;
        _stunGaugeCurrentData = _stunGaugeMaxData;
    }

    /**
     * Updates stun gauge.
     *
     * @param stunGauge How much should we reduce or add when a player hits you.
     * @return false if gauge was broken, otherwise true.
     */
    public boolean updateStunGauge(double stunGauge) {
        return addStunGauge(stunGauge) > 0;
    }

    /**
     * An updater task when stun gauge has been updated.
     *
     * @param stunGauge How much should we reduce or add when a player hits you.
     */
    private double addStunGauge(final double stunGauge) {
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            double newValue = _stunGaugeCurrentData + stunGauge;

            // If value is more than maximum value, the regeneration has stopped.
            if (newValue > _stunGaugeMaxData) {
                // Update storage.
                _stunGaugeCurrentData = _stunGaugeMaxData;

                // A Block Gauge task is not always completed manually, so we just complete it now.
                stopUpdateStaminaTask();

                // Indicate that something reached max value.
                return 1.0;
            } else { // The gauge has not been filled, so keep updating..

                // Update stun gauge.
                _stunGaugeCurrentData = newValue > 0 ? newValue : 0;

                // Update stun gauge, only if it is a negative influence.
                updateClientStunGauge(GaugeExecutionType.DISPLAY);

                // Will return negative, to indicate, that block or stun has been broken.
                return newValue;
            }
        }
    }

    /**
     * Checks both, BLOCK and STUN if needs updating. BLOCK > STUN!
     *
     * @return true if gaugeType needs updating, false otherwise.
     */
    private boolean needsStaminaUpdateTask() {
        return (double) _stunGaugeMaxData != (double) _stunGaugeCurrentData;
    }

    /**
     * Returns regeneration size for our gauges.
     *
     * @return HP Regeneration * BattleOption multiplied sizes.
     */
    private double getRegenerationSize() {
        return 150 * BattleOptionConfig.GAUGE_REGEN_MULTIPLIER;
    }

    /**
     * Updates client state depending on stun gauge type.
     * This is useful to display to user what chances they got ;)
     */
    private void updateClientStunGauge(GaugeExecutionType guardExecutionType) {
        owner.sendPacket(new SMUpdateGuardGauge(guardExecutionType.ordinal(), _stunGaugeCurrentData, _stunGaugeMaxData, getRegenerationSize()));
    }

    /**
     * Starts stamina update task.
     */
    public void startUpdateStaminaTask() { // IF - NOT - BLOCKING
        // Cancel an existing task.
        if (_blockGaugeUpdateTask != null && !_blockGaugeUpdateTask.isDone())
            _blockGaugeUpdateTask.cancel(true);

        // Monitoring health changes.
        if (owner.getGameStats().getHp().getMaxValue() != _savedMaxHealth)
            onMaxHealthChanged();

        // Add the task.
        if (needsStaminaUpdateTask()) {
            long regenerationTime = (long) ((getRegenerationSize() * 2) + 100);
            _blockGaugeUpdateTask = ThreadPool.getInstance().scheduleEffectAtFixedRate(() -> this.addStunGauge(getRegenerationSize()), regenerationTime, regenerationTime, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Stops stamina update task.
     */
    public void stopUpdateStaminaTask() { // IF BLOCKING
        // Check for existing task and cancel it.
        if (_blockGaugeUpdateTask != null && !_blockGaugeUpdateTask.isDone())
            _blockGaugeUpdateTask.cancel(true);

        updateClientStunGauge(GaugeExecutionType.DISPLAY);
    }

    public enum GaugeExecutionType {
        DISPLAY,    // 0
        REGENERATE    // 1
    }
}