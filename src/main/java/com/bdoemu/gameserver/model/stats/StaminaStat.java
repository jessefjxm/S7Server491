package com.bdoemu.gameserver.model.stats;

import com.bdoemu.commons.concurrent.CloseableReentrantLock;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.network.sendable.SMUpdateStamina;
import com.bdoemu.gameserver.model.actions.enums.ESpUseType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.stats.elements.Element;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class StaminaStat extends Stat {
    private ScheduledFuture<?> staminaUpdateTask;

    public StaminaStat(final Creature owner) {
        super(owner);
    }

    public void stopUpdateStaminaTask() {
        if (this.staminaUpdateTask != null && !this.staminaUpdateTask.isDone()) {
            this.staminaUpdateTask.cancel(true);
        }
    }

    public void startUpdateStaminaTask(final ESpUseType spUseType, final int speed) {
        this.stopUpdateStaminaTask();
        this.staminaUpdateTask = ThreadPool.getInstance().scheduleEffectAtFixedRate(() -> this.addStamina(spUseType, speed / 10.0f), 100L, 100L, TimeUnit.MILLISECONDS);
    }

    public void updateStaminaV2(final ESpUseType spUseType, final int value) {
        switch (spUseType) {
            case Recover: {
                if (this.value < this.maxValue) {
                    this.owner.sendPacket(new SMUpdateStamina(spUseType, value, this.owner));
                    this.startUpdateStaminaTask(spUseType, 200);
                    break;
                }
                break;
            }
            case Continue: {
                if (this.value <= 200.0) {
                    final long actionHash = this.owner.getActionStorage().getWeaponType().isNone() ? 2514775444L : 2524986171L;
                    this.owner.getActionStorage().onActionError(actionHash, EStringTable.eErrNoActorSpIsLack);
                    return;
                }
                this.startUpdateStaminaTask(spUseType, value);
                this.owner.sendPacket(new SMUpdateStamina(spUseType, value, this.owner));
                break;
            }
            case Stop: {
                this.stopUpdateStaminaTask();
                this.addStamina(spUseType, value);
                this.owner.sendPacket(new SMUpdateStamina(spUseType, value, this.owner));
                break;
            }
            case Once: {
                this.addStamina(spUseType, value);
                this.owner.sendPacket(new SMUpdateStamina(ESpUseType.Recover, 0, this.owner));
                this.startUpdateStaminaTask(ESpUseType.Recover, value);
                break;
            }
            case Reset: {
                this.stopUpdateStaminaTask();
                this.addStamina(spUseType, value);
                this.owner.sendPacket(new SMUpdateStamina(ESpUseType.Stop, 0, this.owner));
                break;
            }
        }
    }

    public int addStamina(final ESpUseType spUseType, float stamina) {
        switch (spUseType) {
            case Continue:
            case Stop:
            case Once: {
                stamina = -stamina;
                break;
            }
        }
        double staminaDiff = stamina;
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            final double newValue = staminaDiff + this.value;
            if (newValue > this.maxValue) {
                if (this.value == this.maxValue) {
                    return 0;
                }
                staminaDiff = this.maxValue - this.value;
            } else if (newValue < 0.0) {
                final long actionHash = this.owner.getActionStorage().getWeaponType().isNone() ? 2514775444L : 2524986171L;
                this.owner.getActionStorage().onActionError(actionHash, EStringTable.eErrNoActorSpIsLack);
                return 0;
            }
            this.value += staminaDiff;
        }
        return (int) staminaDiff;
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
}