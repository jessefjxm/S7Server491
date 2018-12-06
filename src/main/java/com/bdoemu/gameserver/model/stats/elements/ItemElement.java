package com.bdoemu.gameserver.model.stats.elements;

import com.bdoemu.gameserver.model.stats.enums.ElementEnum;

public class ItemElement extends Element {
    public ItemElement(final float value, final boolean result) {
        super(ElementEnum.ITEM, value, result);
    }

    public ItemElement(final float value) {
        super(ElementEnum.ITEM, value);
    }

    public ItemElement(final int[] dValue) {
        super(ElementEnum.ITEM, dValue);
    }
}