package com.bdoemu.gameserver.model.stats.templates;

import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;

import java.util.HashMap;
import java.util.Map;

public abstract class StatsTemplate {
    protected Map<StatEnum, BaseElement> baseContainer;

    public StatsTemplate() {
        this.baseContainer = new HashMap<StatEnum, BaseElement>();
    }

    public BaseElement getBaseElement(final StatEnum type) {
        return this.baseContainer.get(type);
    }
}
