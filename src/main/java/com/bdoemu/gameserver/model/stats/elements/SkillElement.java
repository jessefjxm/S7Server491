package com.bdoemu.gameserver.model.stats.elements;

import com.bdoemu.gameserver.model.stats.enums.ElementEnum;

public class SkillElement extends Element {
    public SkillElement(final float value) {
        super(ElementEnum.SKILL, value);
    }

    public SkillElement(final int[] dValue) {
        super(ElementEnum.SKILL, dValue);
    }
}