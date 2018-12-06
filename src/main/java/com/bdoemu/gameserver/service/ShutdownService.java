package com.bdoemu.gameserver.service;

import com.bdoemu.MainServer;
import com.bdoemu.commons.database.DatabaseFactory;
import com.bdoemu.commons.model.enums.EServerBusyState;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.network.GameNetworkThread;
import com.bdoemu.gameserver.model.chat.services.ChatService;
import com.bdoemu.gameserver.model.creature.player.services.PlayerSaveService;
import com.bdoemu.gameserver.model.misc.enums.EServerShutdownType;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownService extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ShutdownService.class);
    private ShutdownService _counterInstance;
    private int secondsToShutdown;
    private EServerShutdownType shutdownType;
    private ShutdownService() {
        this._counterInstance = null;
        this.secondsToShutdown = -1;
        this.shutdownType = EServerShutdownType.SIGTERM;
    }

    private ShutdownService(int seconds, final EServerShutdownType shutdownType) {
        this._counterInstance = null;
        if (seconds < 0) {
            seconds = 0;
        }
        this.secondsToShutdown = seconds;
        this.shutdownType = shutdownType;
        MainServer.getRmi().setBusyState(EServerBusyState.INSPECTION);
    }

    public static ShutdownService getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void run() {
        this.countdown();
        if (this.shutdownType == EServerShutdownType.ABORT)
            return;

        // Close threads
        try {
            ThreadPool.getInstance().shutdown();
            ShutdownService.log.warn("Thread Pool Manager: Manager has been closed.");
        } catch (Exception e) {
            log.error("Thread Pool Manager: Exception occured while closing.", e);
        }

        // Save all players
        try {
            PlayerSaveService.getInstance().saveAllPlayers(true);
            log.warn("PlayerSaveService: All players disconnected and saved.");
        } catch (Exception e) {
            log.error("PlayerSaveService: Exception occured while saving players.", e);
        }

        // Save all guilds
        try {
            GuildService.getInstance().run();
            log.warn("GuildService: All guilds have been saved.");
        } catch (Exception e) {
            log.error("GuildService: Exception occured while saving guilds.", e);
        }

        // Kill game network
        try {
            GameNetworkThread.getInstance().shutdown();
            log.warn("GameNetworkThread: Game client connection selector closed.");
        } catch (Exception e) {
            log.error("GameNetworkThread: Exception occured while shutting down game network.", e);
        }

        // Database factory
        try {
            DatabaseFactory.getInstance().shutdown();
            log.warn("DatabaseFactory: Connection has been closed.");
        } catch (Exception e) {
            log.error("DatabaseFactory: Error while closing DatabaseFactory connection!", e);
        }

        log.warn("GM shutdown countdown is over. " + this.shutdownType.toString() + " NOW!");
        switch (this.shutdownType) {
            case SHUTDOWN: {
                Holder.INSTANCE.setMode(EServerShutdownType.SHUTDOWN);
                System.exit(0);
                break;
            }
            case RESTART: {
                Holder.INSTANCE.setMode(EServerShutdownType.RESTART);
                System.exit(2);
                break;
            }
        }
    }

    private void setMode(final EServerShutdownType shutdownType) {
        this.shutdownType = shutdownType;
    }

    private void abort() {
        this.shutdownType = EServerShutdownType.ABORT;
        MainServer.getRmi().setBusyState(EServerBusyState.SMOOTH);
    }

    public void abort(final String charName) {
        ShutdownService.log.warn("GM: " + charName + " issued shutdown ABORT. " + this.shutdownType.toString() + " has been stopped!");
        if (this._counterInstance != null) {
            this._counterInstance.abort();
            this._counterInstance = null;
            ChatService.getInstance().sendWorldMessage("Server " + (shutdownType == EServerShutdownType.RESTART ? "restart" : "maintenance") + " has been cancelled.");
            ChatService.getInstance().sendWorldMessage("Server returning to normal operation.");
        }
    }

    public void startShutdown(final String charName, final int seconds, final EServerShutdownType shutdownType) {
        this.shutdownType = shutdownType;
        ShutdownService.log.warn("GM: {} issued shutdown command. {} in {} seconds!", charName, shutdownType.toString(), seconds);
        if (this.shutdownType != null && !showTimer(seconds))
            this.sendServerQuit(seconds);

        if (this._counterInstance != null) {
            this._counterInstance.abort();
        }
        (this._counterInstance = new ShutdownService(seconds, shutdownType)).start();
    }

    private void sendServerQuit(final int seconds) {
        if (shutdownType == EServerShutdownType.SHUTDOWN) {
            ChatService.getInstance().sendWorldMessage("Server will be entering maintenance in " + seconds + " seconds.");
        } else {
            ChatService.getInstance().sendWorldMessage("Server will be restarted in " + seconds + " seconds.");
        }
    }

    private boolean showTimer(int seconds) {
        return seconds % 60 == 0 ||
                seconds < 60 && seconds % 30 == 0 ||
                seconds < 30 && seconds % 10 == 0 ||
                seconds < 10;
    }

    private void countdown() {
        try {
            while (this.secondsToShutdown > 0) {
                if (showTimer(this.secondsToShutdown))
                    sendServerQuit(secondsToShutdown);

                --this.secondsToShutdown;
                final int delay = 1000;
                Thread.sleep(delay);
                if (this.shutdownType == EServerShutdownType.ABORT)
                    break;
            }
        } catch (InterruptedException ex) {
            //
        }
    }

    public boolean isShutdownInProgress() {
        return this._counterInstance != null;
    }

    private static class Holder {
        static final ShutdownService INSTANCE = new ShutdownService();
    }
}
