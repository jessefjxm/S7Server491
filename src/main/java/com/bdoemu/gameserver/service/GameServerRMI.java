package com.bdoemu.gameserver.service;

import com.bdoemu.commons.model.enums.EBanCriteriaType;
import com.bdoemu.commons.model.enums.EBanType;
import com.bdoemu.commons.model.enums.EServerBusyState;
import com.bdoemu.commons.rmi.IGameServerRMI;
import com.bdoemu.commons.rmi.ILoginServerRMI;
import com.bdoemu.commons.rmi.model.*;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.core.configs.NetworkConfig;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMForciblyLogout;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.model.creature.player.AccountData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GameServerRMI extends UnicastRemoteObject implements IGameServerRMI {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(GameServerRMI.class);
    }

    private final Map<Long, GameClient> accountsInGame;
    private ILoginServerRMI connection;
    private GameChannelInfo gameChannelInfo;
    private transient ScheduledFuture<?> reconnectTask;

    public GameServerRMI() throws RemoteException {
        this.accountsInGame = new ConcurrentHashMap<>();
        (this.gameChannelInfo = new GameChannelInfo((int) ServerConfig.SERVER_ID, (int) ServerConfig.SERVER_CHANNEL_ID)).setServerBusyState(EServerBusyState.SMOOTH);
        this.gameChannelInfo.setIp(ServerConfig.SERVER_IP);
        this.gameChannelInfo.setPort(NetworkConfig.PORT);
        this.gameChannelInfo.setPassword(NetworkConfig.RMI_PASSWORD);
        this.connectToLoginServer();
        ThreadPool.getInstance().scheduleGeneralAtFixedRate(new LoginServerStatusWatcher(), 0L, 1L, TimeUnit.SECONDS);
    }

    private void connectToLoginServer() {
        try {
            final Registry registry = LocateRegistry.getRegistry(NetworkConfig.RMI_HOST, NetworkConfig.RMI_PORT);
            this.connection = (ILoginServerRMI) registry.lookup("bdo_login_server");
            final ChannelRegisterResult registerResult = this.connection.registerChannel(this, this.gameChannelInfo);
            switch (registerResult) {
                case SUCCESS: {
                    GameServerRMI.log.info("Connected to login server successfully.");
                    break;
                }
                default: {
                    GameServerRMI.log.warn("Connection to login server failed. Reason: {}", registerResult.toString());
                    break;
                }
            }
        } catch (ConnectException e2) {
            GameServerRMI.log.warn("Loginserver isn't available. Make sure it's up and running. {}", e2);
        } catch (Exception e) {
            GameServerRMI.log.error("Connection to login server failed", e);
        } finally {
            this.reconnectTask = null;
            if (this.connection == null) {
                this.onConnectionLost();
            }
        }
    }

    public void updateChannel() {
        try {
            this.connection.updateChannel(this, this.gameChannelInfo);
        } catch (RemoteException e) {
            GameServerRMI.log.error("Error while updateChannel", e);
            this.onConnectionLost();
        }
    }

    public LoginAccountInfo getAccountInfo(final long accountId, final int cookie) {
        try {
            return this.connection.getAccountInfo(accountId, cookie);
        } catch (RemoteException e) {
            GameServerRMI.log.error("Error while checkCookie", e);
            this.onConnectionLost();
            return null;
        }
    }

    public void updateCookie(final long accountId, final int cookie) {
        try {
            this.connection.updateCookie(accountId, cookie);
        } catch (RemoteException e) {
            GameServerRMI.log.error("Error while updateCookie", e);
            this.onConnectionLost();
        }
    }

    public void saveAccountData(final AccountData accountData) {
        try {
            this.connection.saveAccountData(accountData.getObjectId(), accountData.getUiData(), accountData.getSaveGameOptions(), accountData.getMacroses());
        } catch (RemoteException e) {
            GameServerRMI.log.error("Error while saveAccountData", e);
            this.onConnectionLost();
        }
    }

    public long getCash(final long accountId) {
        try {
            return this.connection.getCash(accountId);
        } catch (RemoteException e) {
            GameServerRMI.log.error("Error while getCash", e);
            this.onConnectionLost();
            return 0L;
        }
    }

    public boolean addCash(final long accountId, final long value) {
        try {
            return this.connection.addCash(accountId, value);
        } catch (RemoteException e) {
            GameServerRMI.log.error("Error while addCash", e);
            this.onConnectionLost();
            return false;
        }
    }

    public void updateCharacterSlots(final long accountId, final int characterSlots) {
        try {
            this.connection.updateCharacterSlots(accountId, characterSlots);
        } catch (RemoteException e) {
            GameServerRMI.log.error("Error while updateCharacterSlots", e);
            this.onConnectionLost();
        }
    }

    public boolean changeFamilyName(final long accountId, final String familyName) {
        try {
            return this.connection.changeFamilyName(accountId, familyName);
        } catch (RemoteException e) {
            GameServerRMI.log.error("Error while updateCharacterSlots", e);
            this.onConnectionLost();
            return false;
        }
    }

    public String ban(final int serverId, final EBanCriteriaType banCriteriaType, final String banCriteriaValue, final EBanType banType, final String banInitiator, final String comment, final long banEndTime) {
        try {
            return this.connection.ban(serverId, banCriteriaType, banCriteriaValue, banType, banInitiator, comment, banEndTime);
        } catch (RemoteException e) {
            GameServerRMI.log.error("Error while ban", e);
            this.onConnectionLost();
            return "Error while ban.";
        }
    }

    public String unBan(final int serverId, final EBanCriteriaType banCriteriaType, final String banCriteriaValue, final EBanType banType) {
        try {
            return this.connection.unBan(serverId, banCriteriaType, banCriteriaValue, banType);
        } catch (RemoteException e) {
            GameServerRMI.log.error("Error while unBan", e);
            this.onConnectionLost();
            return "Error while unBan.";
        }
    }

    public long getBanEndTime(final int serverId, final EBanCriteriaType banCriteriaType, final String banCriteriaValue, final EBanType banType) {
        try {
            return this.connection.getBanEndTime(serverId, banCriteriaType, banCriteriaValue, banType);
        } catch (RemoteException e) {
            GameServerRMI.log.error("Error while unBan", e);
            this.onConnectionLost();
            return -1L;
        }
    }

    public List<BanInfo> getBans(final int serverId, final EBanCriteriaType banCriteriaType, final String banCriteriaValue) {
        try {
            return this.connection.getBans(serverId, banCriteriaType, banCriteriaValue);
        } catch (RemoteException e) {
            GameServerRMI.log.error("Error while unBan", e);
            this.onConnectionLost();
            return Collections.emptyList();
        }
    }

    public boolean testConnection() throws RemoteException {
        return true;
    }

    public boolean isAccountOnServer(final long accountId) throws RemoteException {
        return this.isAccountInGame(accountId);
    }

    public void kickByAccountId(final long accountId) throws RemoteException {
        if (this.accountsInGame.containsKey(accountId)) {
            final GameClient client = this.accountsInGame.get(accountId);
            if (client != null) {
                client.close(new SMForciblyLogout(0));
            }
        }
    }

    public GameAccountInfo getGameAccountInfo(final long accountId) throws RemoteException {
        final Map<Long, Long> deletionInfo = PlayersDBCollection.getInstance().loadDeletionInfo(accountId);
        final int markedForDeleteCount = (int) deletionInfo.values().stream().filter(time -> time > System.currentTimeMillis()).count();
        return new GameAccountInfo(deletionInfo.size() - markedForDeleteCount, markedForDeleteCount);
    }

    public boolean isAccountInGame(final long accountId) {
        return this.accountsInGame.containsKey(accountId);
    }

    public void addAccountInGame(final long accountId, final GameClient client) {
        this.accountsInGame.put(accountId, client);
    }

    public GameClient getAccountInGame(final long accountId) {
        return this.accountsInGame.get(accountId);
    }

    public void removeAccountInGame(final long accountId) {
        this.accountsInGame.remove(accountId);
    }

    public void setBusyState(final EServerBusyState busyState) {
        this.gameChannelInfo.setServerBusyState(busyState);
        this.updateChannel();
    }

    private void onConnectionLost() {
        if (this.reconnectTask != null) {
            return;
        }
        GameServerRMI.log.info("Connection with login server lost.");
        this.connection = null;
        this.reconnectTask = ThreadPool.getInstance().scheduleGeneral(() -> {
            GameServerRMI.log.info("Reconnecting to login server...");
            this.connectToLoginServer();
        }, 2L, TimeUnit.SECONDS);
    }

    private class LoginServerStatusWatcher implements Runnable {
        @Override
        public void run() {
            try {
                GameServerRMI.this.connection.testConnection();
            } catch (Exception e) {
                if (GameServerRMI.this.reconnectTask == null) {
                    GameServerRMI.this.onConnectionLost();
                }
            }
        }
    }
}
