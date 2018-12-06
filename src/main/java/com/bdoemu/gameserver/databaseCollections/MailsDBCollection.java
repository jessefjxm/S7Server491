package com.bdoemu.gameserver.databaseCollections;

import com.bdoemu.commons.database.mongo.ADatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseCollection;
import com.bdoemu.commons.database.mongo.DatabaseLockInfo;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.creature.player.mail.Mail;
import com.mongodb.BasicDBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

@DatabaseCollection
public class MailsDBCollection extends ADatabaseCollection<Mail, GameServerIDFactory> {
    private static final Logger log = LoggerFactory.getLogger((Class) MailsDBCollection.class);

    private MailsDBCollection(final Class<Mail> clazz) {
        super(clazz, "mails");
        this.addLockInfo(new DatabaseLockInfo(GSIDStorageType.Mail, "_id"));
    }

    public static MailsDBCollection getInstance() {
        return Holder.INSTANCE;
    }

    public ConcurrentHashMap<Long, Mail> loadMailsByAccountId(final long accountId) {
        final BasicDBObject filter = new BasicDBObject();
        filter.put("accountId", accountId);
        return super.load(filter);
    }

    private static class Holder {
        static final MailsDBCollection INSTANCE = new MailsDBCollection(Mail.class);
    }
}
