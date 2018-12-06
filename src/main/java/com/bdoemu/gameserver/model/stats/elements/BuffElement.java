package com.bdoemu.gameserver.model.stats.elements;

import com.bdoemu.gameserver.model.stats.enums.ElementEnum;

public class BuffElement extends Element {
    public BuffElement(final float value) {
        super(ElementEnum.BUFF, value);
    }

    public BuffElement(final float value, final boolean isMaxValue) {
        super(ElementEnum.BUFF, value, isMaxValue);
    }

    public BuffElement(final int[] dValue) {
        super(ElementEnum.BUFF, dValue);
    }

    public BuffElement(final float value, final int[] dValue) {
        super(ElementEnum.BUFF, value, dValue);
    }
}