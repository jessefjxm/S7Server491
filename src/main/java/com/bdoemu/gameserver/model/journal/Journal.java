package com.bdoemu.gameserver.model.journal;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.gameserver.model.journal.enums.EJournalType;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.*;

public class Journal extends JSONable {
    private EJournalType journalType;
    private TreeMap<Long, JournalEntry> journalEntries;

    public Journal(final EJournalType journalType) {
        this.journalType = journalType;
        this.journalEntries = new TreeMap<>();
    }

    public Journal(final BasicDBObject dbObject) {
        this.journalEntries = new TreeMap<>();
        this.journalType = EJournalType.values()[dbObject.getInt("journalType")];
        final BasicDBList journalDbEntries = (BasicDBList) dbObject.get("journalEntries");
        for (int i = 0; i < journalDbEntries.size(); ++i) {
            final JournalEntry entry = new JournalEntry((BasicDBObject) journalDbEntries.get(i));
            this.journalEntries.put(entry.getDate(), entry);
        }
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("journalType", this.journalType.ordinal());
        final BasicDBList entriesDbList = new BasicDBList();
        for (final JournalEntry entryDb : this.journalEntries.values()) {
            entriesDbList.add(entryDb.toDBObject());
        }
        builder.append("journalEntries", entriesDbList);
        return builder.get();
    }

    public Collection<JournalEntry> getJournalEntriesFromDate(final long fromDate) {
        final Long startKey = this.journalEntries.ceilingKey(fromDate * 1000L);
        if (startKey != null) {
            return this.journalEntries.tailMap(startKey).values();
        }
        return Collections.emptyList();
    }

    public Collection<JournalEntry> getRecentEntriesFromJournal(final int recentEntriesCount) {
        final Collection<JournalEntry> result = new ArrayList<>(recentEntriesCount);
        final Iterator<Long> iterator = this.journalEntries.descendingKeySet().iterator();
        for (int index = 0; iterator.hasNext() && index < recentEntriesCount; ++index) {
            result.add(this.journalEntries.get(iterator.next()));
        }
        return result;
    }

    public void addJournalEntry(final JournalEntry entry) {
        this.journalEntries.put(entry.getDate(), entry);
    }

    public EJournalType getJournalType() {
        return this.journalType;
    }

    public void setJournalType(final EJournalType journalType) {
        this.journalType = journalType;
    }
}
