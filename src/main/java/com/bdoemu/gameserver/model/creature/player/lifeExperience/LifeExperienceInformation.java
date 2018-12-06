package com.bdoemu.gameserver.model.creature.player.lifeExperience;

import com.bdoemu.core.configs.GuildConfig;
import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.network.sendable.SMBroadcastLifeExperience;
import com.bdoemu.core.network.sendable.SMLifeExperienceInformation;
import com.bdoemu.core.network.sendable.SMNotifyGuildInfo;
import com.bdoemu.gameserver.dataholders.LifeEXPData;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.enums.EGuildNotifyType;
import com.bdoemu.gameserver.worldInstance.World;

public class LifeExperienceInformation {
    private long exp;
    private int level;
    private ELifeExpType type;
    private int rankLevel;

    public LifeExperienceInformation(final ELifeExpType type) {
        this.level = 1;
        this.type = type;
    }

    public void setValues(final long exp, final int level) {
        this.exp = exp;
        this.level = level;
    }

    public long getExp() {
        return this.exp;
    }

    public int getLevel() {
        return this.level;
    }

    public int getRankLevel() {
        return this.rankLevel;
    }

    public void setRankLevel(final int rankLevel) {
        this.rankLevel = rankLevel;
    }

    public ELifeExpType getType() {
        return this.type;
    }

    public Double getMaxExp() {
        return LifeEXPData.getInstance().getMaxExp(this.type, this.level);
    }

    public synchronized void addExp(final Player player, final int exp) {
        if (exp <= 0 || this.level + 1 >= 100) {
            return;
        }
        int ratedLifeExp = (int) (exp * RateConfig.RATE_LIFE_EXP / 100.0f);
        final int lifeExpStat = player.getGameStats().getLifeExpRate().getIntMaxValue() / 10000;
        if (lifeExpStat > 0) {
            ratedLifeExp += (int) (ratedLifeExp * lifeExpStat / 100.0f);
        }
        this.exp += ratedLifeExp;
        if (this.exp >= this.getMaxExp()) {
            ++this.level;
            this.exp = 0L;
            player.getObserveController().notifyObserver(EObserveType.lifeExp, this.type.ordinal(), this.level);
            if (this.level > 50) {
                World.getInstance().broadcastWorldPacket(new SMBroadcastLifeExperience(player, this.type));
            }
            final Guild guild = player.getGuild();
            if (guild != null) {
                final int activityPoints = GuildConfig.INCREASE_ACTIVITY_MEMBER_LEVEL_UP * this.level * 2;
                player.addActivityPoints(activityPoints);
                guild.sendBroadcastPacket(new SMNotifyGuildInfo(EGuildNotifyType.MEMBER_LEVEL_UP, guild, player.getAccountId(), activityPoints));
                guild.getGuildSkillList().addSkillExp(GuildConfig.INCREASE_EXP_MEMBER_LEVEL_UP * this.level * 2);
            }
        }
        player.sendPacket(new SMLifeExperienceInformation(this));
    }
}
