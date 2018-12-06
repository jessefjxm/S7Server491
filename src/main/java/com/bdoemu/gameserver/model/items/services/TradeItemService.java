package com.bdoemu.gameserver.model.items.services;

import com.bdoemu.commons.thread.APeriodicTaskService;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Nullbyte
 */
@StartupComponent("Service")
public class TradeItemService extends APeriodicTaskService {
    private static class Holder { static final TradeItemService INSTANCE = new TradeItemService(); }
    private static Logger log = LoggerFactory.getLogger(TradeItemService.class);

    public TradeItemService() {
        super(30L, TimeUnit.MINUTES, true);
    }

    @Override
    public void run() {
        //
    }

    public void startTradeGame(Player player, int npc) {

    }

    public static TradeItemService getInstance() {
        return Holder.INSTANCE;
    }
}
