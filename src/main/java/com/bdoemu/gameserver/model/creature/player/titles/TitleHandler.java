package com.bdoemu.gameserver.model.creature.player.titles;

import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.core.network.sendable.SMAcquiredTitle;
import com.bdoemu.core.network.sendable.SMUpdateTitleKey;
import com.bdoemu.gameserver.dataholders.SkillData;
import com.bdoemu.gameserver.dataholders.TitleBuffData;
import com.bdoemu.gameserver.dataholders.TitleData;
import com.bdoemu.gameserver.model.creature.observers.Observer;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.titles.enums.ETitleType;
import com.bdoemu.gameserver.model.creature.player.titles.observers.ATitleObserver;
import com.bdoemu.gameserver.model.creature.player.titles.templates.TitleTemplate;
import com.bdoemu.gameserver.model.journal.JournalEntry;
import com.bdoemu.gameserver.model.journal.enums.EJournalEntryType;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TitleHandler extends JSONable {
    private final Set<Integer> titles;
    private final ConcurrentHashMap<Integer, Observer> observeTitles;
    private final ConcurrentHashMap<Observer, ATitleObserver> observers;
    private int titleId;
    private Player player;

    public TitleHandler(final Player player) {
        this.titles = new HashSet<>();
        this.observeTitles = new ConcurrentHashMap<>();
        this.observers = new ConcurrentHashMap<>();
        this.player = player;
    }

    public TitleHandler(final Player player, final BasicDBObject dbObject) {
        this(player);
        this.titleId = dbObject.getInt("titleId");
        final BasicDBList titleListDB = (BasicDBList) dbObject.get("titleList");
        for (final Object aTitleListDB : titleListDB) {
            final Integer titleId = (Integer) aTitleListDB;
            this.titles.add(titleId);
        }
        if (this.titles.size() == TitleData.getInstance().getTemplates().size()) {
            return;
        }
        final BasicDBList titleObserversDB = (BasicDBList) dbObject.get("titleObservers");
        for (final Object aTitleObserversDB : titleObserversDB) {
            final BasicDBObject dbObjectItem = (BasicDBObject) aTitleObserversDB;
            final int titleId2 = dbObjectItem.getInt("titleId");
            if (!this.titles.contains(titleId2) && !this.observeTitles.containsKey(titleId2)) {
                final TitleTemplate template = TitleData.getInstance().getTemplate(titleId2);
                if (template == null) {
                    continue;
                }
                final ETitleType type = template.getTitleType();
                final EObserveType observeType = type.getObserveType();
                if (observeType == null) {
                    continue;
                }
                final ATitleObserver titleObserver = template.getTitleType().newTitleInstance();
                if (titleObserver == null) {
                    continue;
                }
                titleObserver.load(dbObjectItem, template);
                this.registerObserver(observeType, titleObserver);
            }
        }
        this.registerNewObservers();
    }

    private final void registerObserver(final EObserveType observeType, final ATitleObserver titleObserver) {
        final Observer observer = new Observer(observeType, titleObserver.getKey()) {
            @Override
            public void notify(final EObserveType type, final Object... params) {
                final ATitleObserver titleObserver = TitleHandler.this.observers.get(this);
                if (titleObserver != null) {
                    synchronized (titleObserver) {
                        if (titleObserver.canReward(params)) {
                            TitleHandler.this.addTitle(titleObserver.getTemplate().getTitleId());
                        }
                    }
                }
            }
        };
        this.observeTitles.put(titleObserver.getTemplate().getTitleId(), observer);
        this.observers.put(observer, titleObserver);
        this.player.getObserveController().putObserver(observer);
    }

    private final void registerNewObservers() {
        for (final TitleTemplate template : TitleData.getInstance().getTemplates().values()) {
            final int titleId = template.getTitleId();
            final ETitleType type = template.getTitleType();
            final EObserveType observeType = type.getObserveType();
            if (observeType == null) {
                continue;
            }
            if (this.titles.contains(titleId) || this.observeTitles.containsKey(titleId)) {
                continue;
            }
            final ATitleObserver titleObserver = template.getTitleType().newTitleInstance();
            if (titleObserver == null) {
                continue;
            }
            titleObserver.load(template);
            this.registerObserver(observeType, titleObserver);
        }
    }

    public Set<Integer> getTitles() {
        return this.titles;
    }

    public int getTitleId() {
        return this.titleId;
    }

    public boolean addTitle(final int titleId) {
        final TitleTemplate template = TitleData.getInstance().getTemplate(titleId);
        if (template == null) {
            return false;
        }
        synchronized (this.titles) {
            final EObserveType observeType = template.getTitleType().getObserveType();
            if (observeType != null) {
                final Observer observer = this.observeTitles.remove(titleId);
                if (observer != null) {
                    this.observers.remove(observer);
                    this.player.getObserveController().removeObserver(observer);
                }
            }
            if (this.titles.add(titleId)) {
                this.player.sendPacket(new SMAcquiredTitle(titleId));
                final int skillId = TitleBuffData.getInstance().getSkillIdForTitleCount(this.titles.size());
                if (skillId > 0) {
                    final SkillT skillTemplate = SkillData.getInstance().getTemplate(skillId);
                    if (skillTemplate != null) {
                        SkillService.useSkill(this.player, skillTemplate, null, Collections.singletonList(this.player));
                    }
                }
                final JournalEntry journalEntry = new JournalEntry();
                journalEntry.setDate(GameTimeService.getServerTimeInMillis());
                journalEntry.setType(EJournalEntryType.TitleAcquired);
                journalEntry.setParam0((short) titleId);
                this.player.addJournalEntryAndNotify(journalEntry);
                return true;
            }
        }
        return false;
    }

    public void updateTitle(final int titleId) {
        synchronized (this.titles) {
            if ((titleId != 0 && !this.titles.contains(titleId)) || titleId == this.titleId) {
                return;
            }
            this.titleId = titleId;
            this.player.sendBroadcastItSelfPacket(new SMUpdateTitleKey(titleId, this.player.getGameObjectId()));
        }
    }

    public void onLogin() {
        for (final Map.Entry<Integer, Integer> entry : TitleBuffData.getInstance().getTitleCountBuffs().entrySet()) {
            if (this.titles.size() >= entry.getKey()) {
                final SkillT skillTemplate = SkillData.getInstance().getTemplate(entry.getValue());
                if (skillTemplate == null) {
                    continue;
                }
                SkillService.useSkill(this.player, skillTemplate, null, Collections.singletonList(this.player));
            }
        }
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("titleId", this.titleId);
        builder.append("titleList", this.titles);
        final BasicDBList titleObserverListDB = new BasicDBList();
        for (final ATitleObserver observer : this.observers.values()) {
            final DBObject dbObject = observer.toDBObject();
            if (dbObject != null) {
                titleObserverListDB.add(dbObject);
            }
        }
        builder.append("titleObservers", titleObserverListDB);
        return builder.get();
    }
}
