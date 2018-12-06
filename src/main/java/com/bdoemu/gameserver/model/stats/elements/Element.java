package com.bdoemu.gameserver.model.stats.elements;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.stats.enums.ElementEnum;

public abstract class Element extends JSONable {
    private ElementEnum type;
    private float value;
    private int[] dValue;
    private boolean isMaxValue;

    public Element(final ElementEnum type, final float value) {
        this(type, value, null);
    }

    public Element(final ElementEnum type, final float value, final boolean isMaxValue) {
        this(type, value, null);
        this.isMaxValue = isMaxValue;
    }

    public Element(final ElementEnum type, final int[] dValue) {
        this(type, 0.0f, dValue);
    }

    public Element(final ElementEnum type, final float value, final int[] dValue) {
        this.value = 0.0f;
        this.isMaxValue = true;
        this.type = type;
        this.value = value;
        this.dValue = dValue;
    }

    public boolean isMaxValue() {
        return this.isMaxValue;
    }

    public ElementEnum getType() {
        return this.type;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(final float value) {
        this.value = value;
    }

    public int getIntValue() {
        return (int) this.value;
    }

    public int[] getDValue() {
        return this.dValue;
    }
}