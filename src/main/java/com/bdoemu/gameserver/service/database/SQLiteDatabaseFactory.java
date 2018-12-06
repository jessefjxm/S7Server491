package com.bdoemu.gameserver.service.database;

import com.bdoemu.commons.database.sqlite.MiniConnectionPoolManager;
import com.bdoemu.commons.database.sqlite.SQLiteConnectionPoolDataSource;
import com.bdoemu.core.startup.StartupComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.SQLException;

@StartupComponent("Database")
public class SQLiteDatabaseFactory {
    private static final Logger log = LoggerFactory.getLogger(SQLiteDatabaseFactory.class);
    private static MiniConnectionPoolManager poolManager;
    private SQLiteDatabaseFactory() {
        SQLiteDatabaseFactory.log.info("Initializing SQLiteDatabaseFactory...");
        try {
            Class.forName(JDBC.class.getName());
            final SQLiteConnectionPoolDataSource dataSourceOcean = new SQLiteConnectionPoolDataSource();
            dataSourceOcean.setEncoding("UTF-8");
            dataSourceOcean.setUrl("jdbc:sqlite:data/sqlite3/bdo.sqlite3");
            SQLiteDatabaseFactory.poolManager = new MiniConnectionPoolManager(dataSourceOcean, 1000);
        } catch (Exception e) {
            SQLiteDatabaseFactory.log.error("Error while initializing SQLite connection!", e);
        }
        SQLiteDatabaseFactory.log.info("SQLiteDatabaseFactory initialized.");
    }

    public static SQLiteDatabaseFactory getInstance() {
        return Holder.INSTANCE;
    }

    public Connection getCon() throws SQLException {
        return SQLiteDatabaseFactory.poolManager.getConnection();
    }

    private static class Holder {
        static final SQLiteDatabaseFactory INSTANCE = new SQLiteDatabaseFactory();
    }
}
