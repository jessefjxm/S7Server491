package com.bdoemu.gameserver.model.stats.elements;

import com.bdoemu.gameserver.model.stats.enums.ElementEnum;

public class FitnessElement extends Element {
    public FitnessElement(final float value) {
        super(ElementEnum.FITNESS, value);
    }

    public FitnessElement(final float value, final boolean isMaxValue) {
        super(ElementEnum.FITNESS, value, isMaxValue);
    }

    public FitnessElement(final int[] dValue) {
        super(ElementEnum.FITNESS, dValue);
    }

    public FitnessElement(final float value, final int[] dValue) {
        super(ElementEnum.FITNESS, value, dValue);
    }
}