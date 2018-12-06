package com.bdoemu.gameserver.model.security.services;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.network.sendable.SMXigncodeSecurityData;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@StartupComponent("Service")
public class XigncodeService {
    private static final Logger log = LoggerFactory.getLogger(XigncodeService.class);

    private XigncodeService() {
        /*
        final SMXigncodeSecurityData xign1 = new SMXigncodeSecurityData(1);
        final SMXigncodeSecurityData xign2 = new SMXigncodeSecurityData(2);
        final SMXigncodeSecurityData xign3 = new SMXigncodeSecurityData(3);
        final SMXigncodeSecurityData xign4 = new SMXigncodeSecurityData(4);
        final SMXigncodeSecurityData xign5 = new SMXigncodeSecurityData(5);
        ThreadPool.getInstance().scheduleGeneralAtFixedRate(() -> {
            for (Player player : World.getInstance().getPlayers()) {
                player.sendPacket(xign1);
                player.sendPacket(xign2);
                player.sendPacket(xign3);
                player.sendPacket(xign4);
                player.sendPacket(xign5);
            }
        }, 0L, 15L, TimeUnit.MINUTES);*/
    }

    public static XigncodeService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        static final XigncodeService INSTANCE = new XigncodeService();
    }
}
