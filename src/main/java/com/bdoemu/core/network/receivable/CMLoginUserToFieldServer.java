package com.bdoemu.core.network.receivable;

import com.bdoemu.MainServer;
import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.commons.model.enums.EGameServiceType;
import com.bdoemu.commons.model.enums.EServiceResourceType;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ClientState;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.rmi.model.LoginAccountInfo;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.databaseCollections.AccountsDBCollection;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.model.creature.player.Memo;
import com.bdoemu.gameserver.service.FamilyService;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.service.database.DatabaseLogFactory;
import com.mongodb.BasicDBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class CMLoginUserToFieldServer extends ReceivablePacket<GameClient> {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger("Authentication");
    }

    private String id;
    private String mac;
    private int cookie;
    private EGameServiceType gameServiceType;
    private EServiceResourceType serviceResourceType;

    public CMLoginUserToFieldServer(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.id = this.readS();
        this.skip(62 - (this.id.length() * 2 + 2));
        this.cookie = this.readD();
        this.gameServiceType = EGameServiceType.values()[this.readD()];
        this.serviceResourceType = EServiceResourceType.values()[this.readD()];
        readC(); // reconnect?
        this.mac = this.reads(18);
        this.skip(15);
    }

    public void runImpl() {
        final GameClient client = this.getClient();
        if (client != null) {
            final long accountId = Long.parseLong(this.id);
            final GameClient alreadyInGame = MainServer.getRmi().getAccountInGame(accountId);
            if (alreadyInGame != null) {
                alreadyInGame.close(new SMNak(EStringTable.eErrNoUserAlreadyLogin, CMLoginUserToFieldServer.class));
                client.closeForce();
                CMLoginUserToFieldServer.log.warn("EStringTable.eErrNoUserAlreadyLogin IP: {}, accountId: {} cookie: {}", client.getHostAddress(), accountId, this.cookie);
                return;
            }
            if (this.gameServiceType != ServerConfig.GAME_SERVICE_TYPE) {
                CMLoginUserToFieldServer.log.warn("Client IP: {}, accountId: {} cookie: {} trying to login with different client type (Client: {} Server: {})", ((GameClient) this.getClient()).getHostAddress(), accountId, this.cookie, this.serviceResourceType, ServerConfig.GAME_SERVICE_TYPE);
                client.close(new SMNak(EStringTable.eErrNoUserAuthenticKeyIsInvalid, CMLoginUserToFieldServer.class));
                return;
            }
            final ConcurrentHashMap<Long, BasicDBObject> dbPlayers = PlayersDBCollection.getInstance().loadCharacterDataByAccountId(accountId);
            if (dbPlayers != null) {
                final LoginAccountInfo loginAccountInfo = MainServer.getRmi().getAccountInfo(accountId, this.cookie);
                if (loginAccountInfo == null) {
                    CMLoginUserToFieldServer.log.warn("Client IP: {}, accountId: {} has incorrect cookie: {}", this.getClient().getHostAddress(), accountId, this.cookie);
                    client.close(new SMNak(EStringTable.eErrNoUserAuthenticKeyIsInvalid, CMLoginUserToFieldServer.class));
                    return;
                }
                if (ServerConfig.SERVER_ADMIN_ONLY) {
                    if (loginAccountInfo.getAccessLevel().getAccessId() == EAccessLevel.USER.getAccessId()) {
                        client.close(new SMNak(EStringTable.eErrNoServerIsQaServer, CMLoginUserToFieldServer.class));
                        return;
                    }
                }
                if (client.compareAndSetState(ClientState.CONNECTED, ClientState.AUTHED)) {
                    FamilyService.getInstance().putFamily(accountId, loginAccountInfo.getFamily());
                    DatabaseLogFactory.getInstance().logAuth(accountId, this.getClient().getHostAddress());
                    client.sendPacket(new SMGetContentServiceInfo());
                    for (final BasicDBObject dbPlayer : dbPlayers.values()) {
                        final long deleteTime = dbPlayer.getLong("deletionDate");
                        if (deleteTime > 0L && deleteTime < GameTimeService.getServerTimeInSecond()) {
                            final long objId = dbPlayer.getLong("_id");
                            dbPlayers.remove(objId);
                            PlayersDBCollection.getInstance().delete(objId);
                        }
                    }
                    client.sendPacket(new SMChargeUser(AccountsDBCollection.getInstance().load(accountId)));
                    loginAccountInfo.setPlayers(dbPlayers);
                    int packetNr = 0;
                    final ListSplitter<BasicDBObject> characters = new ListSplitter<>(dbPlayers.values(), 6);
                    do {
                        client.sendPacket(new SMLoginUserToFieldServer(client, loginAccountInfo, characters.getNext(), packetNr));
                        ++packetNr;
                    } while (!characters.isLast());
                    dbPlayers.values().stream().filter(playerDB -> playerDB.containsField("memo")).forEach(playerDB -> getClient().sendPacket(new SMSetPlayerCharacterMemo(new Memo((BasicDBObject) playerDB.get("memo")))));
                    client.sendPacket(new SMPcRoomAuth());
                    client.sendPacket(new SMFixedCharge());
                    client.setLoginAccountInfo(loginAccountInfo);
                    MainServer.getRmi().addAccountInGame(accountId, client);
                    return;
                }
            }
            client.close(new SMNak(EStringTable.eErrNoUserIpIsInvalid, CMLoginUserToFieldServer.class));
        }
    }
}
