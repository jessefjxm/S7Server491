package com.bdoemu.gameserver.service.database;

import com.bdoemu.commons.database.DatabaseFactory;
import com.bdoemu.core.configs.DatabaseConfig;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.chat.enums.EChatType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.Item;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@StartupComponent("Database")
public class DatabaseLogFactory {
    private static final Logger log = LoggerFactory.getLogger((Class) DatabaseLogFactory.class);
    private DBCollection gmAuditLog;
    private DBCollection chatLog;
    private DBCollection authLog;
    private DBCollection itemLog;
    private DBCollection onlineTrackingLog;
    private int currentMonth;
    private DatabaseLogFactory() {
        this.currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        this.init();
    }

    public static DatabaseLogFactory getInstance() {
        return Holder.INSTANCE;
    }

    private void init() {
        if (!DatabaseConfig.ALLOW_DATABASE_LOGGING) {
            DatabaseLogFactory.log.info("Database logging is disabled.");
            return;
        }
        while (this.isDbAvailable()) {
            DatabaseLogFactory.log.error("MongoDB server isn't available. Please start `mongod` or disable database logging in config.");
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException ex) {
                //
            }
        }
        DatabaseLogFactory.log.info("Database logging connection tested and working.");
        final String dbName = String.format("log_s%s_c%s_%s", ServerConfig.SERVER_ID, ServerConfig.SERVER_CHANNEL_ID, this.currentMonth);
        final DB db = DatabaseFactory.getInstance().getDatabase(dbName);
        try {
            if (db.collectionExists("gm_audit_log")) {
                this.gmAuditLog = db.getCollection("gm_audit_log");
            } else {
                final BasicDBObject tableSettings = new BasicDBObject("size", 1000000);
                tableSettings.append("capped", Boolean.TRUE);
                this.gmAuditLog = db.createCollection("gm_audit_log", tableSettings);
            }
            if (db.collectionExists("chat_log")) {
                this.chatLog = db.getCollection("chat_log");
            } else {
                final BasicDBObject tableSettings = new BasicDBObject("size", 1000000);
                tableSettings.append("capped", Boolean.TRUE);
                this.chatLog = db.createCollection("chat_log", tableSettings);
            }
            if (db.collectionExists("auth_log")) {
                this.authLog = db.getCollection("auth_log");
            } else {
                final BasicDBObject tableSettings = new BasicDBObject("size", 1000000);
                tableSettings.append("capped", Boolean.TRUE);
                this.authLog = db.createCollection("auth_log", tableSettings);
            }
            if (db.collectionExists("item_log")) {
                this.itemLog = db.getCollection("item_log");
            } else {
                final BasicDBObject tableSettings = new BasicDBObject("size", 1000000);
                tableSettings.append("capped", Boolean.TRUE);
                this.itemLog = db.createCollection("item_log", tableSettings);
            }
            if (db.collectionExists("onlinetracking_log")) {
                this.onlineTrackingLog = db.getCollection("onlinetracking_log");
            } else {
                final BasicDBObject tableSettings = new BasicDBObject("size", 1000000);
                tableSettings.append("capped", Boolean.TRUE);
                this.onlineTrackingLog = db.createCollection("onlinetracking_log", tableSettings);
            }
        } catch (Exception e) {
            DatabaseLogFactory.log.error("Error while init connection to log server: " + e.getMessage(), e);
        } finally {
            DatabaseLogFactory.log.info("Item log: {} entries.", this.itemLog.count());
            DatabaseLogFactory.log.info("GM Audit log: {} entries.", this.gmAuditLog.count());
            DatabaseLogFactory.log.info("Chat log: {} entries.", this.chatLog.count());
            DatabaseLogFactory.log.info("Auth log: {} entries.", this.authLog.count());
            DatabaseLogFactory.log.info("Online log: {} entries.", this.onlineTrackingLog.count());
        }
    }

    public void logItems(final Player player, final String operationType, final Collection<Item> items) {
        if (DatabaseConfig.ALLOW_DATABASE_LOGGING) {
            try {
                final List<DBObject> itemsDb = items.stream().map(item -> new BasicDBObject().append("date", (int) (System.currentTimeMillis() / 1000L)).append("account_id", player.getAccountId()).append("operation_type", operationType).append("item_id", item.getItemId()).append("count", item.getCount()).append("enchant_level", item.getEnchantLevel()).append("storage_location", item.getStorageLocation().toString())).collect(Collectors.toList());
                this.itemLog.insert(itemsDb);
            } catch (Exception ex) {
                //
            }
        }
    }

    public void logGmAudit(final String playerName, final String command) {
        if (DatabaseConfig.ALLOW_DATABASE_LOGGING) {
            try {
                this.gmAuditLog.insert(new BasicDBObject().append("date", (int) (System.currentTimeMillis() / 1000L)).append("player_name", playerName).append("command", command));
            } catch (Exception ex) {
                //
            }
        }
    }

    public void logChat(final EChatType chatType, final Player teller, final Player listener, final String message) {
        if (DatabaseConfig.ALLOW_DATABASE_LOGGING) {
            try {
                this.chatLog.insert(new BasicDBObject().append("date", (int) (System.currentTimeMillis() / 1000L)).append("chat_type", chatType.ordinal()).append("teller_object_id", teller.getObjectId()).append("teller_name", teller.getName()).append("listener_object_id", ((listener != null) ? listener.getObjectId() : -1L)).append("listener_name", ((listener != null) ? listener.getName() : "")).append("message", message));
            } catch (Exception ex) {
                //
            }
        }
    }

    public void logAuth(final Long accountId, final String ip) {
        if (DatabaseConfig.ALLOW_DATABASE_LOGGING) {
            try {
                this.authLog.insert(new BasicDBObject().append("date", (int) (System.currentTimeMillis() / 1000L)).append("accountId", accountId).append("ip", ip.replaceAll("/", "")));
            } catch (Exception ex) {
                //
            }
        }
    }

    public void logOnline(final int online) {
        if (DatabaseConfig.ALLOW_DATABASE_LOGGING) {
            try {
                this.onlineTrackingLog.insert(new BasicDBObject().append("date", (int) (System.currentTimeMillis() / 1000L)).append("online", online));
            } catch (Exception ex) {
                //
            }
        }
    }

    private boolean isDbAvailable() {
        boolean portTaken = false;
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(DatabaseConfig.PORT);
        } catch (IOException e) {
            portTaken = true;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    //
                }
            }
        }
        return !portTaken;
    }

    private static class Holder {
        static final DatabaseLogFactory INSTANCE = new DatabaseLogFactory();
    }
}
