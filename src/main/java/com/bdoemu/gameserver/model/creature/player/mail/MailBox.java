package com.bdoemu.gameserver.model.creature.player.mail;

import com.bdoemu.gameserver.databaseCollections.MailsDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class MailBox {
    private final ConcurrentHashMap<Long, Mail> mails;
    private boolean hasNewMails;
    private Player owner;

    public MailBox(final Player owner) {
        this.hasNewMails = false;
        this.owner = owner;
        this.mails = MailsDBCollection.getInstance().loadMailsByAccountId(owner.getAccountId());
        this.hasNewMails = this.mails.values().stream().anyMatch(mail -> mail.getReceivedTime() > owner.getAccountData().getLastLogout());
    }

    public boolean hasNewMails() {
        return this.hasNewMails;
    }

    public void setHasNewMails(final boolean result) {
        this.hasNewMails = result;
    }

    public ConcurrentHashMap<Long, Mail> getMailMap() {
        return this.mails;
    }

    public void addMail(final Mail mail) {
        this.mails.put(mail.getObjectId(), mail);
    }

    public Mail removeMail(final long id) {
        return this.mails.remove(id);
    }

    public Collection<Mail> getMails() {
        if (this.mails.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(this.mails.values());
    }
}
