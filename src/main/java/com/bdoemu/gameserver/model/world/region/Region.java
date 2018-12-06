package com.bdoemu.gameserver.model.world.region;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.world.region.enums.ERegionType;
import com.bdoemu.gameserver.model.world.region.templates.RegionTemplate;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class Region {
    private RegionTemplate template;
    private ConcurrentHashMap<Long, Player> players;

    public Region(final RegionTemplate template) {
        this.players = new ConcurrentHashMap<>();
        this.template = template;
    }

    public void onEnter(final Creature creature) {
        if (creature.isPlayer()) {
            this.players.put(creature.getObjectId(), (Player) creature);
        }
    }

    public void onExit(final Creature creature) {
        if (creature.isPlayer()) {
            this.players.remove(creature.getObjectId());
        }
    }

    public int getSize() {
        return players.size();
    }

    public void applySkills() {
        for (final Player player : this.players.values()) {
            if (this.template.getSkillNo() != null) {
                if (!GameTimeService.getInstance().isNight()) {
                    if (Rnd.getChance((this.template.getSkillApplyRate()) / 10000)) {
                        SkillService.applySkill(player, this.template.getSkillNo(), Collections.singleton(player));
                    }
                } else if (Rnd.getChance((this.template.getNightApplyRate()) / 10000)) {
                    SkillService.applySkill(player, this.template.getNightSkillNo(), Collections.singleton(player));
                }
            }
            if (this.template.getSkillNo2() != null && Rnd.getChance(this.template.getSkill2ApplyRate() / 10000)) {
                SkillService.applySkill(player, this.template.getSkillNo2(), Collections.singleton(player));
            }
            if (this.template.getSkillNo3() != null && Rnd.getChance(this.template.getSkill3ApplyRate() / 10000)) {
                SkillService.applySkill(player, this.template.getSkillNo3(), Collections.singleton(player));
            }
        }
    }

    public ERegionType getRegionType() {
        return this.template.getRegionType();
    }

    public RegionTemplate getTemplate() {
        return this.template;
    }
}
