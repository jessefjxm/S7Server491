package com.bdoemu.core.network;

import com.bdoemu.MainServer;
import com.bdoemu.commons.network.Client;
import com.bdoemu.commons.network.Connection;
import com.bdoemu.commons.network.ICipher;
import com.bdoemu.commons.network.impl.crypt.BDOCrypt;
import com.bdoemu.commons.rmi.model.LoginAccountInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.sendable.SMSetFrameworkInformation;
import com.bdoemu.gameserver.model.creature.player.Player;

public class GameClient extends Client<GameClient> {
    private LoginAccountInfo loginAccountInfo;
    private Player player;
    private int objectId;

    public GameClient(final Connection<GameClient> connection) {
        super(connection);
        this.objectId = (int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.ONLINE);
    }

    protected void onOpen() {
        super.onOpen();
        final ICipher crypt = new BDOCrypt();
        this.getConnection().setCipher(crypt);
        this.sendPacket(new SMSetFrameworkInformation(crypt));
    }

    protected void onClose() {
        super.onClose();
        try {
            if (this.player != null) {
                this.player.onDisconnect();
            }
        } finally {
            if (this.loginAccountInfo != null) {
                MainServer.getRmi().removeAccountInGame(this.loginAccountInfo.getAccountId());
            }
        }
    }

    public String toString() {
        try {
            switch (this.getState()) {
                case CONNECTED: {
                    return super.toString();
                }
                case AUTHED:
                case AUTHED_GG: {
                    return "[AccountId: " + this.loginAccountInfo.getAccountId() + " - IP: " + this.getHostAddress() + "]";
                }
                case ENTERED: {
                    return "[Character: " + ((this.player == null) ? "disconnected" : (this.player.getName() + "[" + this.player.getObjectId() + "]")) + " - Account: " + this.getLoginAccountInfo().getAccountId() + " - IP: " + this.getHostAddress() + "]";
                }
                default: {
                    return super.toString();
                }
            }
        } catch (NullPointerException e) {
            return super.toString();
        }
    }

    public LoginAccountInfo getLoginAccountInfo() {
        return this.loginAccountInfo;
    }

    public void setLoginAccountInfo(final LoginAccountInfo loginAccountInfo) {
        this.loginAccountInfo = loginAccountInfo;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public int getObjectId() {
        return this.objectId;
    }

    public void setObjectId(final int objectId) {
        this.objectId = objectId;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GameClient)) {
            return false;
        }
        final GameClient other = (GameClient) o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$loginAccountInfo = this.getLoginAccountInfo();
        final Object other$loginAccountInfo = other.getLoginAccountInfo();
        Label_0065:
        {
            if (this$loginAccountInfo == null) {
                if (other$loginAccountInfo == null) {
                    break Label_0065;
                }
            } else if (this$loginAccountInfo.equals(other$loginAccountInfo)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$player = this.getPlayer();
        final Object other$player = other.getPlayer();
        if (this$player == null) {
            if (other$player == null) {
                return this.getObjectId() == other.getObjectId();
            }
        } else if (this$player.equals(other$player)) {
            return this.getObjectId() == other.getObjectId();
        }
        return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GameClient;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $loginAccountInfo = this.getLoginAccountInfo();
        result = result * 59 + (($loginAccountInfo == null) ? 43 : $loginAccountInfo.hashCode());
        final Object $player = this.getPlayer();
        result = result * 59 + (($player == null) ? 43 : $player.hashCode());
        result = result * 59 + this.getObjectId();
        return result;
    }
}
