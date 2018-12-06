package com.bdoemu.gameserver.model.stats;

import com.bdoemu.commons.concurrent.CloseableReentrantLock;
import com.bdoemu.gameserver.dataholders.TranslateStatData;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.stats.elements.Element;
import com.bdoemu.gameserver.model.stats.templates.TranslateStatT;

import java.util.ArrayList;
import java.util.List;

public class BaseRateStat extends Stat {
    private final int capMaxValue;
    protected int currentStatRate;
    protected List<Element> rateElements;

    public BaseRateStat(final Creature owner) {
        super(owner);
        this.rateElements = new ArrayList<>();
        this.capMaxValue = TranslateStatData.getInstance().size() - 1;
    }

    public boolean addRateElement(final Element element) {
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            if (this.rateElements.contains(element)) {
                return false;
            }
            this.rateElements.add(element);
            this.currentStatRate += element.getIntValue();
        }
        return true;
    }

    public boolean removeRateElement(final Element element) {
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            if (!this.rateElements.remove(element)) {
                return false;
            }
            this.currentStatRate -= element.getIntValue();
        }
        return true;
    }

    public List<Element> getRateElements() {
        return this.rateElements;
    }

    @Override
    public double getValue() {
        final double currentValue = this.value + this.getValueBonus();
        double result = (currentValue < 0.0) ? 0.0 : currentValue;
        final double currentMaxValue = this.getMaxValue();
        if (currentValue > currentMaxValue) {
            result = currentMaxValue;
        }
        return result;
    }

    @Override
    public double getMaxValue() {
        final double currentValue = this.maxValue + this.getMaxValueBonus();
        double result = (currentValue < 0.0) ? 0.0 : currentValue;
        if (currentValue > this.capMaxValue) {
            result = this.capMaxValue;
        }
        return result;
    }

    protected float getValueBonus() {
        return 0.0f;
    }

    protected float getMaxValueBonus() {
        return 0.0f;
    }

    public TranslateStatT getRateTemplate() {
        return TranslateStatData.getInstance().getTemplate((int) this.getValue());
    }
}