// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.npc;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.gameserver.dataholders.DialogData;
import com.bdoemu.gameserver.model.actions.ActionStorage;
import com.bdoemu.gameserver.model.ai.deprecated.AIService;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.complete.ICompleteConditionHandler;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.agrolist.CreatureAggroList;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.enums.ERemoveActorType;
import com.bdoemu.gameserver.model.creature.npc.dialogs.templates.DialogTemplate;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AddItemEvent;
import com.bdoemu.gameserver.model.creature.player.quests.Quest;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.functions.IFunctionHandler;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import com.bdoemu.gameserver.model.skills.buffs.CreatureBuffList;
import com.bdoemu.gameserver.model.stats.containers.NpcGameStats;
import com.bdoemu.gameserver.model.stats.containers.NpcLifeStats;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Npc extends Creature {
    public Npc(final int gameObjectId, final CreatureTemplate template, final SpawnPlacementT spawnPlacement) {
        super(gameObjectId, template, spawnPlacement);
        this.actionStorage = new ActionStorage(this);
        this.setBuffList(new CreatureBuffList(this));
        this.setGameStats(new NpcGameStats(this));
        this.setLifeStats(new NpcLifeStats(this));
        this.ai = AIService.getInstance().newAIHandler(this, this.getTemplate().getAiScriptClassName());
        this.aggroList = new CreatureAggroList(this);
    }

    public static boolean checkDialog(final Player player, final int dialogIndex, final int index, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate) {
        final DialogTemplate template = DialogData.getInstance().getTemplates(creatureTemplate.getCreatureId(), dialogIndex, index);
        if (template != null) {
            if (!ConditionService.checkCondition(template.getConditions(), player)) {
                return false;
            }
            final List<IFunctionHandler> functions = template.getFunctions();
            if (functions != null) {
                for (final IFunctionHandler handler : functions) {
                    handler.doFunction(player, npc, applyCount, creatureTemplate, dialogIndex);
                }
            }
        }
        return true;
    }

    public synchronized void onContact(final Player player) {
        if (this.getAi() != null) {
            final int talkableType = this.getTemplate().getTalkable();
            if (talkableType == 3) {
                for (final Quest quest : player.getPlayerQuestHandler().getProgressQuestList()) {
                    for (final ICompleteConditionHandler condition : quest.getTemplate().getCompleteConditions()) {
                        if (condition.canInteractForQuest(this)) {
                            if (!this.getAi().HandleDead(player, null).isChangeState()) {
                                this.onDie(player, 2544805566L);
                            }
                            final Integer dropId = this.getTemplate().getDropId();
                            if (dropId != null) {
                                final List<ItemSubGroupT> dropItems = ItemMainGroupService.getItems(player, dropId);
                                if (!dropItems.isEmpty()) {
                                    final ConcurrentLinkedQueue<Item> items = new ConcurrentLinkedQueue<Item>();
                                    for (final ItemSubGroupT itemSubGroupT : dropItems) {
                                        final Item item = new Item(itemSubGroupT.getItemId(), Rnd.get(itemSubGroupT.getMinCount(), itemSubGroupT.getMaxCount()), itemSubGroupT.getEnchantLevel());
                                        items.add(item);
                                        player.getObserveController().notifyObserver(EObserveType.exchangeItem, item.getItemId(), item.getEnchantLevel(), item.getCount(), this.getCreatureId(), this.getDialogIndex());
                                    }
                                    player.getPlayerBag().onEvent(new AddItemEvent(player, items));
                                }
                                break;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public long getPartyCache() {
        final Creature owner = this.getOwner();
        return (owner != null && owner != this) ? owner.getPartyCache() : -2097154L;
    }

    @Override
    public boolean isEnemy(final Creature creature) {
        if (creature == this) {
            return false;
        }
        switch (creature.getCharKind()) {
            case Monster: {
                return true;
            }
            default: {
                return super.isEnemy(creature);
            }
        }
    }

    @Override
    public void onDie(final Creature attacker, final long actionHash) {
        if (!this.isSpawned()) {
            return;
        }
        super.onDie(attacker, actionHash);
        ERemoveActorType removeActorType = ERemoveActorType.DespawnMonster;
        if (attacker != null && attacker.isPlayer() && ((Player) attacker).getEscort() == this) {
            removeActorType = ERemoveActorType.DespawnEscort;
        }
        World.getInstance().deSpawn(this, removeActorType);
    }

    @Override
    public void onDespawn() {
        super.onDespawn();
        GameServerIDFactory.getInstance().releaseId(GSIDStorageType.Creatures, (long) this.getGameObjectId());
    }

    public synchronized void onDeliverTalk(final Player player, final int dialogIndex, final int index, final long applyCount, final CreatureTemplate creatureTemplate) {
        checkDialog(player, dialogIndex, index, this, applyCount, creatureTemplate);
    }

    @Override
    public boolean notSee(final Creature object, final ERemoveActorType type, final boolean outOfRange) {
        return super.notSee(object, type, outOfRange);
    }

    @Override
    public boolean see(final Creature object, final int subSectorX, final int subSectorY, final boolean isNewSpawn, final boolean isRespawn) {
        return true;
    }

    @Override
    public boolean see(final List<? extends Creature> objects, final int subSectorX, final int subSectorY, final ECharKind type) {
        return true;
    }

    @Override
    public NpcGameStats getGameStats() {
        return (NpcGameStats) super.getGameStats();
    }
}
