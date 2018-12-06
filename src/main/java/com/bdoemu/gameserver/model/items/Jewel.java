// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items;

import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.services.SkillService;

import java.util.Collections;
import java.util.List;

public class Jewel {
    private final ItemTemplate template;
    private List<ActiveBuff> buffs;

    public Jewel(final ItemTemplate template) {
        this.template = template;
    }

    public Jewel(final int jewelId) {
        this.template = ItemData.getInstance().getItemTemplate(jewelId);
    }

    public int getJewelId() {
        return this.template.getItemId();
    }

    public ItemTemplate getTemplate() {
        return this.template;
    }

    public void applyEffects(final Playable playable) {
        this.buffs = SkillService.useSkill(playable, this.template.getSkillId(), null, Collections.singletonList(playable));
    }

    public void setBuffs(final List<ActiveBuff> buffs) {
        this.buffs = buffs;
    }

    public void endEffects() {
        if (this.buffs != null) {
            this.buffs.forEach(ActiveBuff::endEffect);
            this.buffs = null;
        }
    }
}
