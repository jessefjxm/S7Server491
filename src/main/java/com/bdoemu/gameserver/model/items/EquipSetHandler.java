// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.items;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.items.templates.EquipSetOptionT;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.services.SkillService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EquipSetHandler {
    private final EquipSetOptionT template;
    private HashMap<Integer, List<ActiveBuff>> buffs;
    private int index;

    public EquipSetHandler(final EquipSetOptionT template) {
        this.buffs = new HashMap<Integer, List<ActiveBuff>>();
        this.template = template;
    }

    public int getIndex() {
        return this.template.getIndex();
    }

    public int getItemSize() {
        return this.index;
    }

    public void applyEffects(final Creature creature) {
        ++this.index;
        final Integer skillId = this.template.getBuffs().get(this.index);
        if (skillId != null) {
            final List<ActiveBuff> invBuffs = SkillService.useSkill(creature, skillId, null, Collections.singletonList(creature));
            if (invBuffs != null) {
                this.buffs.put(this.index, invBuffs);
            }
        }
    }

    public void endEffects() {
        final List<ActiveBuff> activeBuffs = this.buffs.remove(this.index);
        if (activeBuffs != null) {
            activeBuffs.forEach(ActiveBuff::endEffect);
        }
        --this.index;
    }
}
