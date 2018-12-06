package com.bdoemu.gameserver.model.creature.player.quests;

import com.bdoemu.commons.collection.ListSplitter;
import com.bdoemu.commons.database.mongo.JSONable;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMAcceptQuest;
import com.bdoemu.core.network.receivable.CMCompleteQuest;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.dataholders.QuestData;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.complete.ICompleteConditionHandler;
import com.bdoemu.gameserver.model.conditions.complete.KillMonsterCCondition;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AddItemEvent;
import com.bdoemu.gameserver.model.creature.player.quests.templates.QuestTemplate;
import com.bdoemu.gameserver.model.creature.player.rewards.templates.RewardTemplate;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.service.GameTimeService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PlayerQuestHandler extends JSONable {
    private Map<Integer, HashMap<Integer, ClearedQuest>> clearedQuestList;
    private Map<Integer, HashMap<Integer, Quest>> progressQuestList;
    private byte[] checkedQuestData;
    private byte[] questSelectType;
    private byte[] questSortType;
    private Player player;
    private int searchQuestCharacterId;

    public PlayerQuestHandler(final Player player) {
        this.clearedQuestList = new ConcurrentHashMap<>();
        this.progressQuestList = new ConcurrentHashMap<>();
        this.player = player;
        this.checkedQuestData = new byte[301];
        this.questSelectType = new byte[25];
        this.questSortType = new byte[10];
    }

    public PlayerQuestHandler(final Player player, final BasicDBObject dbObject) {
        this.clearedQuestList = new ConcurrentHashMap<>();
        this.progressQuestList = new ConcurrentHashMap<>();
        this.player = player;
        this.checkedQuestData = (byte[]) dbObject.get("checkedQuestData");
        this.questSelectType = (byte[]) dbObject.get("questSelectType");
        this.questSortType = (byte[]) dbObject.get("questSortType");
        final BasicDBList progressQuestListDB = (BasicDBList) dbObject.get("progressQuestList");
        for (int i = 0; i < progressQuestListDB.size(); ++i) {
            final BasicDBObject questDB = (BasicDBObject) progressQuestListDB.get(i);
            final Quest quest = Quest.newQuest(player, questDB);
            if (quest != null) {
                this.putToProgressList(quest);
            }
        }
        final BasicDBList clearedQuestListDB = (BasicDBList) dbObject.get("clearedQuestList");
        for (int j = 0; j < clearedQuestListDB.size(); ++j) {
            final BasicDBObject questDB2 = (BasicDBObject) clearedQuestListDB.get(j);
            final ClearedQuest clearedQuest = new ClearedQuest(questDB2);
            if (clearedQuest.getTemplate() != null) {
                this.putToClearedList(clearedQuest);
            }
        }
    }

    private boolean baseReward(final int questNpcId, final int npcSession, final Quest quest, final int selectRewardIndex) {
        final RewardTemplate baseReward = quest.getTemplate().getBaseRewardT();
        final RewardTemplate selectReward = quest.getTemplate().getSelectRewardT();
        final ConcurrentLinkedQueue<Item> addItemTasks = new ConcurrentLinkedQueue<Item>();
        if (selectReward != null) {
            selectReward.getRewardItems(selectRewardIndex, addItemTasks);
        }
        if (baseReward != null) {
            baseReward.getRewardItems(-1, addItemTasks);
        }
        if (!addItemTasks.isEmpty() && !this.player.getPlayerBag().onEvent(new AddItemEvent(this.player, addItemTasks, EStringTable.eErrNoItemTakeFromQuest))) {
            return false;
        }
        if (baseReward != null) {
            baseReward.rewardPlayer(this.player, questNpcId, npcSession);
        }
        return true;
    }

    public int getSearchQuestCharacterId() {
        return this.searchQuestCharacterId;
    }

    public void setSearchQuestCharacterId(final int searchQuestCharacterId) {
        this.searchQuestCharacterId = searchQuestCharacterId;
    }

    public boolean isClearedQuest(final int groupId, final int questId) {
        final HashMap<Integer, ClearedQuest> questGroup = this.clearedQuestList.get(groupId);
        if (questGroup == null) {
            return false;
        }
        final ClearedQuest clearedQuest = questGroup.get(questId);
        if (clearedQuest == null) {
            return false;
        }
        final int repeatTime = clearedQuest.getTemplate().getRepeatTime();
        return repeatTime != 9999 && (repeatTime <= 0 || GameTimeService.getServerTimeInMillis() <= clearedQuest.getRepeatTime());
    }

    public boolean isProgressQuest(final int groupId, final int questId) {
        final HashMap<Integer, Quest> questGroup = this.progressQuestList.get(groupId);
        return questGroup != null && questGroup.containsKey(questId);
    }

    public boolean isCompleteStep(final int groupId, final int questId, final int step) {
        final HashMap<Integer, Quest> questGroup = this.progressQuestList.get(groupId);
        if (questGroup == null) {
            return false;
        }
        final Quest quest = questGroup.get(questId);
        return quest != null && quest.isCompleteStep(step);
    }

    public boolean acceptQuest(final int groupId, final int questId) {
        synchronized (this) {
            final HashMap<Integer, ClearedQuest> clearedGroup = this.clearedQuestList.get(groupId);
            if (clearedGroup != null) {
                final ClearedQuest clearedQuest = clearedGroup.get(questId);
                if (clearedQuest != null && clearedQuest.getTemplate().getRepeatTime() != 9999 && (clearedQuest.getTemplate().getRepeatTime() == 0 || GameTimeService.getServerTimeInMillis() < clearedQuest.getRepeatTime())) {
                    return false;
                }
            }
            final HashMap<Integer, Quest> progressGroup = this.progressQuestList.get(groupId);
            if (progressGroup != null && progressGroup.containsKey(questId)) {
                this.player.sendPacket(new SMNak(EStringTable.eErrNoQuestCantAquire, CMAcceptQuest.class));
                return false;
            }
            final int size = this.progressQuestList.values().stream().mapToInt(HashMap::size).sum();
            if (size >= 30) {
                this.player.sendPacket(new SMNak(EStringTable.eErrNoQuestNotMoreAquire, CMAcceptQuest.class));
                return false;
            }
            final QuestTemplate template = QuestData.getInstance().getTemplate(groupId, questId);
            if (template == null) {
                return false;
            }
            if (!ConditionService.checkCondition(template.getAcceptConditions(), this.player)) {
                this.player.sendPacket(new SMNak(EStringTable.eErrNoConditionIsWrong, CMAcceptQuest.class));
                return false;
            }
            final Integer acceptPushItem = template.getAcceptPushItem();
            if (acceptPushItem != null && acceptPushItem != 0) {
                final ConcurrentLinkedQueue<Item> addTasks = new ConcurrentLinkedQueue<>();
                addTasks.add(new Item(acceptPushItem, 1L, 0));
                if (!this.player.getPlayerBag().onEvent(new AddItemEvent(this.player, addTasks, EStringTable.eErrNoItemTakeFromQuest))) {
                    return false;
                }
            }
            final Quest quest = new Quest(this.player, template);
            this.putToProgressList(quest);
            this.player.sendPacket(new SMAcceptQuest(groupId, questId));
            quest.onAcceptQuest();
            return true;
        }
    }

    public boolean completeQuest(final int npcId, final int npcSession, final int groupId, final int questId, final int selectRewardIndex) {
        synchronized (this) {
            final HashMap<Integer, Quest> questGroup = this.progressQuestList.get(groupId);
            Quest quest = null;
            if (questGroup != null) {
                quest = questGroup.get(questId);
            }
            if (quest == null) {
                this.player.sendPacket(new SMNak(EStringTable.eErrNoQuestAlreadyClear, CMCompleteQuest.class));
                return false;
            }
            if (!quest.isCompleteQuest() || !this.baseReward(npcId, npcSession, quest, selectRewardIndex)) {
                return false;
            }
            quest.clearObservers();
            questGroup.remove(questId);
            if (questGroup.isEmpty()) {
                this.progressQuestList.remove(groupId);
            }
            this.putToClearedList(new ClearedQuest(quest.getTemplate()));
            this.player.sendPacket(new SMCompleteQuest(quest));
            this.player.getObserveController().notifyObserver(EObserveType.questComplete, groupId, questId);
            return true;
        }
    }

    public boolean giveupQuest(final int groupId, final int questId) {
        synchronized (this) {
            final HashMap<Integer, Quest> questGroup = this.progressQuestList.get(groupId);
            Quest quest = null;
            if (questGroup != null) {
                quest = questGroup.get(questId);
            }
            if (quest != null) {
                quest.clearObservers();
                questGroup.remove(questId);
                if (questGroup.isEmpty()) {
                    this.progressQuestList.remove(groupId);
                }
                this.player.sendPacket(new SMGiveupQuest(quest));
                final Creature escort = this.player.getEscort();
                if (escort != null) {
                    for (final ICompleteConditionHandler condition : quest.getTemplate().getCompleteConditions()) {
                        if (condition instanceof KillMonsterCCondition && ((KillMonsterCCondition) condition).getMonsterId() == escort.getCreatureId()) {
                            this.player.setEscort(null);
                            escort.getAi().HandlerEscort_Reset(this.player, null);
                            break;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void putToProgressList(final Quest quest) {
        final int groupId = quest.getQuestGroupId();
        this.progressQuestList.computeIfAbsent(groupId, integer -> new HashMap<>()).put(quest.getQuestId(), quest);
    }

    public void putToClearedList(final ClearedQuest clearedQuest) {
        final int groupId = clearedQuest.getQuestGroupId();
        this.clearedQuestList.computeIfAbsent(groupId, integer -> new HashMap<>()).put(clearedQuest.getQuestId(), clearedQuest);
    }

    public Collection<ClearedQuest> getClearedQuestList() {
        final List<ClearedQuest> quests = new ArrayList<ClearedQuest>();
        for (final Map.Entry<Integer, HashMap<Integer, ClearedQuest>> entry : this.clearedQuestList.entrySet()) {
            quests.addAll(entry.getValue().values());
        }
        return quests;
    }

    public Collection<Quest> getProgressQuestList() {
        final List<Quest> quests = new ArrayList<Quest>();
        for (final Map.Entry<Integer, HashMap<Integer, Quest>> entry : this.progressQuestList.entrySet()) {
            quests.addAll(entry.getValue().values());
        }
        return quests;
    }

    public Quest getProgressQuest(final int groupId, final int questId) {
        final HashMap<Integer, Quest> progressQuestMap = this.progressQuestList.get(groupId);
        return (progressQuestMap == null) ? null : progressQuestMap.get(questId);
    }

    public byte[] getCheckedQuestData() {
        return this.checkedQuestData;
    }

    public void setCheckedQuestData(final byte[] checkedQuestData) {
        this.checkedQuestData = checkedQuestData;
    }

    public byte[] getQuestSelectType() {
        return this.questSelectType;
    }

    public void setQuestSelectType(final byte[] questSelectType) {
        this.questSelectType = questSelectType;
    }

    public byte[] getQuestSortType() {
        return this.questSortType;
    }

    public void setQuestSortType(final byte[] questSortType) {
        this.questSortType = questSortType;
    }

    public void onLogin() {
        this.player.sendPacket(new SMListQuestSelectType(this.getQuestSelectType()));
        this.player.sendPacket(new SMListQuestSortType(this.getQuestSortType()));
        this.player.sendPacket(new SMSupportPointList());
        final Collection<Quest> progressingQuestList = this.getProgressQuestList();
        if (!progressingQuestList.isEmpty()) {
            this.player.sendPacket(new SMProgressingQuestList(progressingQuestList));
        }
        final Collection<ClearedQuest> clearedQuestList = this.getClearedQuestList();
        if (!clearedQuestList.isEmpty()) {
            final ListSplitter<ClearedQuest> clearedSplit = new ListSplitter<>(clearedQuestList, 1000);
            while (clearedSplit.hasNext()) {
                this.player.sendPacket(new SMClearedQuestList(clearedSplit.getNext()));
            }
        }
        this.player.sendPacket(new SMListCheckedQuest(this.player.getPlayerQuestHandler().getCheckedQuestData()));
    }

    public DBObject toDBObject() {
        final BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        builder.append("checkedQuestData", this.checkedQuestData);
        builder.append("questSelectType", this.questSelectType);
        builder.append("questSortType", this.questSortType);
        final BasicDBList progressQuestListDB = new BasicDBList();
        for (final Map.Entry<Integer, HashMap<Integer, Quest>> entry : this.progressQuestList.entrySet()) {
            progressQuestListDB.addAll(entry.getValue().values().stream().filter(progressQuest -> !progressQuest.getTemplate().isUserBaseQuest()).map(Quest::toDBObject).collect(Collectors.toList()));
        }
        builder.append("progressQuestList", progressQuestListDB);
        final BasicDBList clearedQuestListDB = new BasicDBList();
        for (final Map.Entry<Integer, HashMap<Integer, ClearedQuest>> entry2 : this.clearedQuestList.entrySet()) {
            clearedQuestListDB.addAll(entry2.getValue().values().stream().filter(clearedQuest -> !clearedQuest.getTemplate().isUserBaseQuest()).map(ClearedQuest::toDBObject).collect(Collectors.toList()));
        }
        builder.append("clearedQuestList", clearedQuestListDB);
        return builder.get();
    }
}
