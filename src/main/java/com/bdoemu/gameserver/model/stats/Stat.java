package com.bdoemu.gameserver.model.stats;

import com.bdoemu.commons.concurrent.CloseableReentrantLock;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.stats.elements.Element;

import java.util.ArrayList;
import java.util.List;

public class Stat {
    protected final CloseableReentrantLock lock;
    protected final Creature owner;
    protected List<Element> elements;
    protected BaseElement base;
    protected double value;
    protected double maxValue;
    protected double bonus;

    public Stat(final Creature owner) {
        this.lock = new CloseableReentrantLock();
        this.elements = new ArrayList<>();
        this.maxValue = 0.0;
        this.owner = owner;
    }

    public Stat(final Creature owner, final BaseElement baseElement) {
        this.lock = new CloseableReentrantLock();
        this.elements = new ArrayList<>();
        this.maxValue = 0.0;
        this.owner = owner;
        this.setBase(baseElement);
    }

    public double fill() {
        double diff;
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            diff = this.getMaxValue() - this.getValue();
            this.value = this.maxValue;
        }
        return diff;
    }

    public void fill(int percentage) {
        percentage = Math.min(percentage, 100);
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            this.value = this.getMaxValue() * percentage / 100.0;
        }
    }

    public boolean increaseElement(final Element element, final float incValue) {
        if (element != null) {
            try (final CloseableReentrantLock closeableLock = this.lock.open()) {
                if (!this.elements.contains(element) && element != this.base) {
                    return false;
                }
                if (element.isMaxValue()) {
                    this.maxValue += incValue;
                } else {
                    this.value += incValue;
                }
                element.setValue(element.getValue() + incValue);
            }
            return true;
        }
        return false;
    }

    public boolean addElement(final Element element) {
        if (element != null) {
            try (final CloseableReentrantLock closeableLock = this.lock.open()) {
                if (element.getDValue() != null) {
                    this.value += element.getDValue()[0];
                    this.maxValue += element.getDValue()[1];
                } else if (element.isMaxValue()) {
                    this.maxValue += element.getValue();
                } else {
                    this.value += element.getValue();
                }
                return this.elements.add(element);
            }
        }
        return false;
    }

    public boolean removeElement(final Element element) {
        if (element != null) {
            try (final CloseableReentrantLock closeableLock = this.lock.open()) {
                if (element.getDValue() != null) {
                    this.value -= element.getDValue()[0];
                    this.maxValue -= element.getDValue()[1];
                } else if (element.isMaxValue()) {
                    this.maxValue -= element.getValue();
                } else {
                    this.value -= element.getValue();
                }
                return this.elements.remove(element);
            }
        }
        return false;
    }

    public BaseElement getBase() {
        return this.base;
    }

    public final void setBase(final BaseElement base) {
        try (final CloseableReentrantLock closeableLock = this.lock.open()) {
            if (this.getBase() != null) {
                if (this.getBase().getDValue() != null) {
                    this.value += base.getDValue()[0] - this.getBase().getDValue()[0];
                    this.maxValue += base.getDValue()[1] - this.getBase().getDValue()[1];
                } else {
                    this.maxValue += base.getValue() - this.getBase().getValue();
                }
            } else if (base.getDValue() != null) {
                this.value += base.getDValue()[0];
                this.maxValue += base.getDValue()[1];
            } else {
                this.maxValue = base.getValue();
            }
            this.base = base;
        }
    }

    public void addBonus(final double bonus) {
        this.bonus += bonus;
        this.maxValue += bonus;
    }

    public int getIntBonus() {
        return (int) this.bonus;
    }

    public float getFloatBonus() {
        return (int) this.bonus;
    }

    public double getBonus() {
        return this.bonus;
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    public int getIntMaxValue() {
        return (int) this.getMaxValue();
    }

    public double getValue() {
        return this.value;
    }

    public int getIntValue() {
        return (int) this.getValue();
    }

    public long getLongValue() {
        return (long) this.getValue();
    }

    public long getLongMaxValue() {
        return (long) this.getMaxValue();
    }

    public List<Element> getElements() {
        return this.elements;
    }
}