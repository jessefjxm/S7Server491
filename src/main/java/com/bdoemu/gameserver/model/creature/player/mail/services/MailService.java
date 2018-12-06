package com.bdoemu.gameserver.model.creature.player.mail.services;

import com.bdoemu.core.network.sendable.SMNewMail;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.databaseCollections.MailsDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.mail.Mail;
import com.bdoemu.gameserver.model.items.BuyCashItem;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Service")
public class MailService {
    private static class Holder {
        static final MailService INSTANCE = new MailService();
    }

    public static MailService getInstance() {
        return Holder.INSTANCE;
    }

    public Mail sendMail(final long accountId, final long senderAccountId, final String name, final String mailSubject, final String mailMessage) {
        return this.sendMail(accountId, senderAccountId, name, mailSubject, mailMessage, null, null);
    }

    public Mail sendMail(final long accountId, final long senderAccountId, final String name, final String mailSubject, final String mailMessage, final BuyCashItem buyCashItem) {
        return this.sendMail(accountId, senderAccountId, name, mailSubject, mailMessage, null, buyCashItem);
    }

    public Mail sendMail(final long accountId, final long senderAccountId, final String name, final String mailSubject, final String mailMessage, final Item item) {
        return this.sendMail(accountId, senderAccountId, name, mailSubject, mailMessage, item, null);
    }

    public Mail sendMail(final long accountId, final long senderAccountId, final String name, final String mailSubject, final String mailMessage, final Item item, final BuyCashItem buyCashItem) {
        final Mail mail = Mail.newMail(accountId, senderAccountId, name, mailSubject, mailMessage, item, buyCashItem);
        if (MailsDBCollection.getInstance().save(mail)) {
            final Player recipient = World.getInstance().getPlayerByAccount(accountId);
            if (recipient != null) {
                recipient.getMailBox().addMail(mail);
                recipient.sendPacket(new SMNewMail());
            }
            return mail;
        }
        return null;
    }
}
