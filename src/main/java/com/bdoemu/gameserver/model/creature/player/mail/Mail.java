package com.bdoemu.gameserver.model.creature.player.mail;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.model.items.BuyCashItem;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class Mail extends JSONable {
    private final long id;
    private final long accountId;
    private final long senderAccountId;
    private final long receivedTime;
    private final String name;
    private final String mailSubject;
    private final String mailMessage;
    private int type;
    private Item item;
    private BuyCashItem buyCashItem;

    public Mail(final long id, final long accountId, final long senderAccountId, final String name, final String mailSubject, final String mailMessage, final Item item, final BuyCashItem buyCashItem) {
        this.id = id;
        this.accountId = accountId;
        this.senderAccountId = senderAccountId;
        this.name = name;
        this.mailSubject = mailSubject;
        this.mailMessage = mailMessage;
        this.receivedTime = GameTimeService.getServerTimeInMillis();
        this.item = item;
        this.buyCashItem = buyCashItem;
        this.type = ((buyCashItem != null) ? 1 : 0);
    }

    public Mail(final BasicDBObject basicDBObject) {
        this.id = basicDBObject.getLong("_id");
        this.accountId = basicDBObject.getLong("accountId");
        this.senderAccountId = basicDBObject.getLong("senderAccountId");
        this.name = basicDBObject.getString("name");
        this.mailSubject = basicDBObject.getString("mailSubject");
        this.mailMessage = basicDBObject.getString("mailMessage");
        this.receivedTime = basicDBObject.getLong("receivedTime");
        final BasicDBObject itemDB = (BasicDBObject) basicDBObject.get("item");
        this.item = ((itemDB != null) ? new Item(itemDB) : null);
        final BasicDBObject buyCashItemDB = (BasicDBObject) basicDBObject.get("buyCashItem");
        this.buyCashItem = ((buyCashItemDB != null) ? new BuyCashItem(buyCashItemDB) : null);
        this.type = basicDBObject.getInt("type", 0);
    }

    public static Mail newMail(final long accountId, final long senderAccountId, final String name, final String mailSubject, final String mailMessage, final Item item, final BuyCashItem buyCashItem) {
        return new Mail(GameServerIDFactory.getInstance().nextId(GSIDStorageType.Mail), accountId, senderAccountId, name, mailSubject, mailMessage, item, buyCashItem);
    }

    public long getReceivedTime() {
        return this.receivedTime;
    }

    public long getReceivedTimeSeconds() {
        return this.receivedTime / 1000L;
    }

    public void unsetItem() {
        if (this.item != null && this.item.getCount() <= 0L) {
            this.item = null;
        }
        if (this.buyCashItem != null && this.buyCashItem.getCount() <= 0L) {
            this.buyCashItem = null;
        }
    }

    public BuyCashItem getBuyCashItem() {
        return this.buyCashItem;
    }

    public Item getItem() {
        return this.item;
    }

    public int getItemId() {
        return (this.item != null) ? this.item.getItemId() : 0;
    }

    public int getItemType() {
        return this.type;
    }

    public int getProductNr() {
        return (this.buyCashItem != null) ? this.buyCashItem.getProductNr() : 0;
    }

    public int getEnchantLevel() {
        return (this.item != null) ? this.item.getEnchantLevel() : 0;
    }

    public long getCount() {
        if (this.item != null) {
            return this.item.getCount();
        }
        if (this.buyCashItem != null) {
            return this.buyCashItem.getCount();
        }
        return 0L;
    }

    public long getObjectId() {
        return this.id;
    }

    public long getSenderAccountId() {
        return this.senderAccountId;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public String getMailSubject() {
        return this.mailSubject;
    }

    public String getName() {
        return this.name;
    }

    public String getMailMessage() {
        return this.mailMessage;
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("_id", this.id);
        builder.append("accountId", this.accountId);
        builder.append("senderAccountId", this.senderAccountId);
        builder.append("name", this.name);
        builder.append("mailSubject", this.mailSubject);
        builder.append("mailMessage", this.mailMessage);
        builder.append("receivedTime", this.receivedTime);
        builder.append("item", ((this.item == null) ? null : this.item.toDBObject()));
        builder.append("buyCashItem", ((this.buyCashItem == null) ? null : this.buyCashItem.toDBObject()));
        builder.append("type", this.type);
        return builder.get();
    }
}
