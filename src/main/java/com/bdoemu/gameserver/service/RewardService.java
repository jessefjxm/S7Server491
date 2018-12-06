package com.bdoemu.gameserver.service;

import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.core.configs.EventsConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.mail.services.MailService;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.concurrent.TimeUnit;

@StartupComponent("Service")
public class RewardService extends APeriodicTaskService {
    private RewardService() {
        super(EventsConfig.AUTO_REWARD_TIME, TimeUnit.MINUTES);
    }

    public static RewardService getInstance() {
        return Holder.INSTANCE;
    }

    public void run() {
        if (EventsConfig.AUTO_REWARD_ENABLE) {
            for (final Player player : World.getInstance().getPlayers()) {
                for (final String itemReward : EventsConfig.AUTO_REWARD_ITEMS) {
                    final String[] params = itemReward.split(";");
                    final int itemId = Integer.parseInt(params[0]);
                    final int enchantLevel = Integer.parseInt(params[1]);
                    final long count = Long.parseLong(params[2]);
                    final Item item = new Item(itemId, count, enchantLevel);
                    MailService.getInstance().sendMail(player.getAccountId(), 0L, EventsConfig.AUTO_REWARD_MAIL_NAME, EventsConfig.AUTO_REWARD_MAIL_SUBJECT, EventsConfig.AUTO_REWARD_MAIL_MESSAGE, item);
                }
            }
        }
    }

    private static class Holder {
        static final RewardService INSTANCE = new RewardService();
    }
}
