package com.bdoemu.gameserver.service;

import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.databaseCollections.AccountsDBCollection;
import com.bdoemu.gameserver.dataholders.FamilyPointRewardData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.family.templates.FamilyPointRewardT;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.creature.player.mail.services.MailService;
import com.bdoemu.gameserver.model.items.Item;
import com.mongodb.BasicDBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

@StartupComponent("Service")
public class FamilyService {
    private static final Logger log = LoggerFactory.getLogger(FamilyService.class);
    private final ConcurrentHashMap<Long, String> families;
    private FamilyService() {
        this.families = AccountsDBCollection.getInstance().loadFamilies();
        FamilyService.log.info("Loaded {} families from database.", this.families.size());
    }

    public static FamilyService getInstance() {
        return Holder.INSTANCE;
    }

    public void putFamily(final long accountId, final String family) {
        this.families.put(accountId, family);
    }

    public String getFamily(final long accountId) {
        return this.families.get(accountId);
    }

    public void onPlayerLogin(final Player player) {
        int battlePoints = 0;
        int lifePoints = 1;
        int specialPoints = 0;
        long moneyReward = 0L;
        for (final BasicDBObject accountPlayer : player.getClient().getLoginAccountInfo().getPlayers().values()) {
            int level = accountPlayer.getInt("level");
            if (level <= 55) {
                level += level;
            } else {
                level += 55;
            }
            if (level >= 56 && level <= 59) {
                battlePoints += (level - 55) * 2;
            } else {
                battlePoints += 8;
            }
            if (level >= 60) {
                battlePoints += (level - 60) * 5;
            }
        }
        for (final ELifeExpType lifeExpType : ELifeExpType.values()) {
            if (!lifeExpType.isNone()) {
                final int level2 = player.getLifeExperienceStorage().getLifeExperience(lifeExpType).getLevel();
                if (level2 >= 31 && level2 <= 80) {
                    lifePoints += level2 / 2;
                } else if (level2 >= 81) {
                    lifePoints += level2;
                }
            }
        }
        specialPoints += player.getMentalCardHandler().getCardsCount() / 10;
        specialPoints += player.getExplorePointHandler().getMainExplorePoint().getMaxExplorePoints();
        player.setBattlePoints(battlePoints);
        player.setLifePoints(lifePoints);
        player.setSpecialPoints(specialPoints);
        final FamilyPointRewardT battleTemplate = FamilyPointRewardData.getInstance().getTemplate(battlePoints);
        if (battleTemplate != null) {
            moneyReward += battleTemplate.getBattlePoint();
        }
        final FamilyPointRewardT lifeTemplate = FamilyPointRewardData.getInstance().getTemplate(lifePoints);
        if (lifeTemplate != null) {
            moneyReward += lifeTemplate.getLifePoint();
        }
        final FamilyPointRewardT specialTemplate = FamilyPointRewardData.getInstance().getTemplate(specialPoints);
        if (specialTemplate != null) {
            moneyReward += specialTemplate.getEtcPoint();
        }
        if (moneyReward > 0L && player.getAccountData().getFamilyRewardCoolTime() <= GameTimeService.getServerTimeInMillis()) {
            player.getAccountData().setFamilyRewardCoolTime(GameTimeService.getServerTimeInMillis() + 86400000L);
            MailService.getInstance().sendMail(player.getAccountId(), -1L, "{3183609639|844105699}", "{3183609639|1657584658}", "{3183609639|2667200644}", new Item(1, moneyReward, 0));
        }
    }

    private static class Holder {
        static final FamilyService INSTANCE = new FamilyService();
    }
}
