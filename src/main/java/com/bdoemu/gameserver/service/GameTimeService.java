package com.bdoemu.gameserver.service;

import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import com.bdoemu.gameserver.model.world.enums.EGameTimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@StartupComponent("Service")
public class GameTimeService extends Thread {
    private static final Logger log = LoggerFactory.getLogger((Class) GameTimeService.class);
    private final long serverStartTime;
    private int gameHour;
    private int gameMinute;
    private GameTimeService() {
        super("GameTimeService");
        super.setDaemon(true);
        super.setPriority(10);
        this.serverStartTime = System.currentTimeMillis() / 1000L;
        super.start();
    }

    private static EGameTimeType calculateTimeType() {
        final int serverMinute = getServerMinute();
        final int serverHourMod = getServerHour() % 4;
        int result;
        if (serverHourMod >= 1) {
            if (serverHourMod >= 2) {
                if (serverHourMod >= 3) {
                    result = ((serverMinute >= 40) ? 0 : 3);
                } else {
                    result = ((serverMinute >= 10) ? 1 : 0) + 2;
                }
            } else {
                result = 2;
            }
        } else {
            result = ((serverMinute >= 20) ? 1 : 0) + 1;
        }
        return EGameTimeType.values()[result];
    }

    private static int getServerSecond() {
        return (int) (getServerTimeInMillis() / 1000L) % 60;
    }

    private static int getServerMinute() {
        return (int) (getServerTimeInMillis() / 60000L % 60L);
    }

    private static int getServerHour() {
        return (int) (getServerTimeInMillis() / 3600000L % 24L);
    }

    public static long getServerTimeInSecond() {
        return System.currentTimeMillis() / 1000L;
    }

    public static long getServerTimeInMillis() {
        return System.currentTimeMillis();
    }

    public static GameTimeService getInstance() {
        return Holder.INSTANCE;
    }

    public long getServerStartTime() {
        return this.serverStartTime;
    }

    @Override
    public final void run() {
        int startHour = this.gameHour;
        while (true) {
            try {
                Thread.sleep(1000L);
            } catch (Exception ex) {
                //
            }
            final int _seconds = getServerHour() % 4 * 60 * 60 + getServerMinute() * 60 + getServerSecond();
            float gameMinutes = 0.0f;
            final EGameTimeType type = calculateTimeType();
            switch (type) {
                case MORNING: {
                    gameMinutes += 150.0f;
                    gameMinutes += _seconds * 13.5f / 60.0f;
                    break;
                }
                case DAY: {
                    gameMinutes += 420.0f;
                    gameMinutes += (_seconds - 1200) * 4.5f / 60.0f;
                    break;
                }
                case EVENING: {
                    gameMinutes += 420.0f;
                    gameMinutes += 495.0f;
                    gameMinutes += (_seconds - 1200 - 6600) * 4.5f / 60.0f;
                    break;
                }
                case MIDNIGHT: {
                    gameMinutes += 420.0f;
                    gameMinutes += 900.0f;
                    gameMinutes += (_seconds - 1200 - 6600 - 5400) * 13.5f / 60.0f;
                    break;
                }
            }
            this.gameHour = (int) (gameMinutes / 60.0f);
            this.gameMinute = (int) (gameMinutes % 60.0f);
            if (startHour != this.gameHour) {
                startHour = this.gameHour;
                SpawnService.getInstance().notifyHourChanged();
            }
        }
    }

    public boolean isNight() {
        final EGameTimeType timeType = calculateTimeType();
        return timeType == EGameTimeType.MIDNIGHT || timeType == EGameTimeType.MORNING;
    }

    public boolean isCurrentHourBetween(final int startHour, final int endHour) {
        if (endHour > startHour) {
            return this.gameHour > startHour && this.gameHour < endHour;
        }
        return this.gameHour > startHour || this.gameHour < endHour;
    }

    public int getGameHour() {
        return this.gameHour;
    }

    public int getGameMinute() {
        return this.gameMinute;
    }

    private static class Holder {
        static final GameTimeService INSTANCE = new GameTimeService();
    }
}
