package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.team.guild.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DatabaseCollection
public class GuildsDBCollection extends ADatabaseCollection<Guild, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger(GuildsDBCollection.class);

    private GuildsDBCollection(final Class<Guild> clazz) {
        super(clazz, "guilds");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.GUILD, "_id"));
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.GuildComments, "comments", "commentId"));
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.ITEM, "guildWarehouse.items", "objectId"));
    }

    public static GuildsDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        static final GuildsDBCollection INSTANCE = new GuildsDBCollection(Guild.class);
    }
}
