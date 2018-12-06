// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.team.guild;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.network.sendable.SMGuildSkillList;
import com.bdoemu.core.network.sendable.SMGuildSkillPoint;
import com.bdoemu.core.network.sendable.SMLearnGuildSkill;
import com.bdoemu.gameserver.dataholders.GuildSkillPointEXPData;
import com.bdoemu.gameserver.dataholders.SkillData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.team.guild.templates.GuildSkillPointEXPT;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GuildSkillList extends JSONable {
    private final Guild guild;
    private final ConcurrentHashMap<Integer, SkillT> skills;
    private int totalSkillPoints;
    private int currentSkillPointsExp;
    private int availableSkillPoints;
    private int skillExpLevel;
    private GuildSkillPointEXPT template;

    public GuildSkillList(final Guild guild) {
        this.skills = new ConcurrentHashMap<Integer, SkillT>();
        this.guild = guild;
        this.template = GuildSkillPointEXPData.getInstance().getTemplate(this.skillExpLevel);
    }

    public GuildSkillList(final BasicDBObject basicDBObject, final Guild guild) {
        this.skills = new ConcurrentHashMap<Integer, SkillT>();
        this.guild = guild;
        this.skillExpLevel = basicDBObject.getInt("skillExpLevel");
        this.totalSkillPoints = basicDBObject.getInt("totalSkillPoints");
        this.availableSkillPoints = basicDBObject.getInt("availableSkillPoints");
        this.currentSkillPointsExp = basicDBObject.getInt("currentSkillPointsExp");
        this.template = GuildSkillPointEXPData.getInstance().getTemplate(this.skillExpLevel);
        final BasicDBList skillsDb = (BasicDBList) basicDBObject.get("skills");
        for (final Object o : skillsDb) {
            final SkillT skillT = SkillData.getInstance().getTemplate((int) o);
            this.skills.put(skillT.getSkillId(), skillT);
        }
    }

    public void addSkillExp(double exp) {
        if (exp <= 0.0 || this.template == null) {
            return;
        }
        synchronized (this.skills) {
            final float limit = this.template.getRequireSkillExpLimit();
            if (exp > limit) {
                exp = limit;
            }
            final double ratedExp = exp * RateConfig.RATE_GUILD_SKILL_EXP / 100.0;
            this.currentSkillPointsExp += (int) ratedExp;
            while (this.template != null && this.currentSkillPointsExp > this.template.getRequireExp()) {
                this.currentSkillPointsExp -= this.template.getRequireExp();
                this.totalSkillPoints += this.template.getAquiredPoint();
                this.availableSkillPoints += this.template.getAquiredPoint();
                this.template = GuildSkillPointEXPData.getInstance().getTemplate(++this.skillExpLevel);
            }
            this.guild.sendBroadcastPacket(new SMGuildSkillPoint(this, 0));
        }
    }

    public Collection<SkillT> getSkills() {
        return this.skills.values();
    }

    public SkillT getSkill(final int skillId) {
        return this.skills.get(skillId);
    }

    public boolean addSkillPoint(final int skillPoints) {
        synchronized (this.skills) {
            if (this.availableSkillPoints < skillPoints) {
                return false;
            }
            this.availableSkillPoints -= skillPoints;
            return true;
        }
    }

    public boolean addSkill(final Integer skillId) {
        final SkillT skillT = SkillData.getInstance().getTemplate(skillId);
        if (skillT == null || !skillT.getSkillOwnerType().isGuild()) {
            return false;
        }
        if (skillT.getNeedSkillNo0ForLearning() > 0 && !this.skills.containsKey(skillT.getNeedSkillNo0ForLearning())) {
            return false;
        }
        if (skillT.getNeedSkillNo1ForLearning() > 0 && !this.skills.containsKey(skillT.getNeedSkillNo1ForLearning())) {
            return false;
        }
        final int points = skillT.getSkillPointForLearning();
        synchronized (this.skills) {
            if (this.availableSkillPoints < points) {
                return false;
            }
            this.availableSkillPoints -= points;
            this.skills.put(skillT.getSkillId(), skillT);
            this.guild.sendBroadcastPacket(new SMLearnGuildSkill(skillId, 1, 1));
            this.guild.sendBroadcastPacket(new SMGuildSkillList(this.guild));
            this.guild.sendBroadcastPacket(new SMGuildSkillPoint(this, 0));
            if (skillT.getSkillType().isPassive()) {
                for (final Player player : this.guild.getMembersOnline()) {
                    player.getGuildMember().addActiveBuffs(SkillService.useSkill(player, skillT, null, Collections.singletonList(player)));
                }
            }
        }
        return true;
    }

    public int getSkillExpLevel() {
        return this.skillExpLevel;
    }

    public int getTotalSkillPoints() {
        return this.totalSkillPoints;
    }

    public int getAvailableSkillPoints() {
        return this.availableSkillPoints;
    }

    public int getCurrentSkillPointsExp() {
        return this.currentSkillPointsExp;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
        builder.append("skillExpLevel", this.skillExpLevel);
        builder.append("totalSkillPoints", this.totalSkillPoints);
        builder.append("availableSkillPoints", this.availableSkillPoints);
        builder.append("currentSkillPointsExp", this.currentSkillPointsExp);
        final BasicDBList skillListDb = this.skills.values().stream().map((Function<? super SkillT, ?>) SkillT::getSkillId).collect(Collectors.toCollection(BasicDBList::new));
        builder.append("skills", skillListDb);
        return builder.get();
    }
}
