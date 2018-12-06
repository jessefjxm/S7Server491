package com.bdoemu.gameserver.model.stats.elements;

import com.bdoemu.gameserver.model.stats.enums.ElementEnum;

public class BaseElement extends Element {
    public BaseElement(final float value) {
        super(ElementEnum.BASE, value);
    }

    public BaseElement(final int[] dValue) {
        super(ElementEnum.BASE, dValue);
    }
}