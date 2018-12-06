// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items.templates;

import com.bdoemu.commons.utils.XMLNode;
import com.bdoemu.gameserver.model.creature.player.enums.EClassType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class OpenBox31T extends XMLNode {
    private int selectRate;
    private int itemKey;
    private int minCount;
    private int maxCount;
    private int enchantLevel;
    private EClassType classType;

    public OpenBox31T(final Node node) {
        this.selectRate = 1000000;
        this.minCount = 1;
        this.maxCount = 1;
        final NamedNodeMap attributes = node.getAttributes();
        this.itemKey = this.readD(attributes, "itemKey");
        this.minCount = this.readD(attributes, "minCount", this.minCount);
        this.maxCount = this.readD(attributes, "maxCount", this.maxCount);
        this.enchantLevel = this.readD(attributes, "enchantLevel");
        this.selectRate = this.readD(attributes, "selectRate", this.selectRate);
        this.classType = EClassType.valueOf(this.readD(attributes, "classId"));
    }

    public EClassType getClassType() {
        return this.classType;
    }

    public int getEnchantLevel() {
        return this.enchantLevel;
    }

    public int getItemKey() {
        return this.itemKey;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public int getMinCount() {
        return this.minCount;
    }

    public int getSelectRate() {
        return this.selectRate;
    }
}
