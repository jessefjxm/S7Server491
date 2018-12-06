package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMGetDroppedItems;
import com.bdoemu.gameserver.dataholders.ActionEXPData;
import com.bdoemu.gameserver.dataholders.EncyclopediaData;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.dataholders.LifeActionEXPData;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.DropBag;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.action.ActionEXPT;
import com.bdoemu.gameserver.model.creature.player.encyclopedia.templates.EncyclopediaT;
import com.bdoemu.gameserver.model.creature.player.enums.EEquipSlot;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AddItemEvent;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantType;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EDropBagType;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.service.GameTimeService;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FrameEventFishing extends FrameEvent {
    public FrameEventFishing(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        final Player player = (Player) action.getOwner();
        final DropBag dropBag = player.getPlayerBag().getDropBag();
        if (dropBag != null && dropBag.getDropBagType() == EDropBagType.Fishing) {
            if (GameTimeService.getServerTimeInMillis() < dropBag.getValidityTime()) {
                return false;
            }
            for (final Item itemSubGroupT : dropBag.getDropMap().values()) {
                player.getObserveController().notifyObserver(EObserveType.gatherItem, itemSubGroupT.getItemId(), itemSubGroupT.getEnchantLevel(), itemSubGroupT.getCount());
                LifeActionEXPData.getInstance().reward(player, itemSubGroupT.getItemId(), ELifeExpType.Fishing);
                final EncyclopediaT encyclopediaT = EncyclopediaData.getInstance().getTemplate(itemSubGroupT.getItemId());
                if (encyclopediaT != null) {
                    player.getEncyclopediaStorage().updateEncyclopedia(player, encyclopediaT);
                    final Collection<Servant> servants = player.getServantController().getServants(EServantState.Field, EServantType.Ship);
                    if (servants.isEmpty()) {
                        continue;
                    }
                    for (final Servant servant : servants) {
                        if (!KnowList.knowObject(player, servant)) {
                            continue;
                        }
                        final Item alchemyStone = servant.getEquipments().getItem(EEquipSlot.glove.getId());
                        if (alchemyStone == null) {
                            continue;
                        }
                        final EContentsEventType contentsEventType = alchemyStone.getTemplate().getContentsEventType();
                        if (contentsEventType == null || !contentsEventType.isRubbing() || encyclopediaT.getKey() != alchemyStone.getTemplate().getContentsEventParam1()) {
                            continue;
                        }
                        alchemyStone.upgradeAlchemyStone(servant, 5000L);
                    }
                }
            }
            if (action.getActionName().equals("FISHING_HOOK_SUCCESS_Auto")) {
                final ConcurrentLinkedQueue<Item> addItems = new ConcurrentLinkedQueue<Item>();
                for (final Item item : dropBag.getDropMap().values()) {
                    final ItemTemplate template = ItemData.getInstance().getItemTemplate(item.getItemId());
                    if (npcGameObjId == 257 && template.getItemClassify().isEtc() && template.getGradeType().isWhite()) {
                        continue;
                    }
                    addItems.add(item);
                }
                player.getPlayerBag().onEvent(new AddItemEvent(player, addItems, EStringTable.eErrNoAcquireCollectItem));
                player.getPlayerBag().setDropBag(null);
            } else {
                player.sendPacket(new SMGetDroppedItems(-1024, dropBag));
            }
            final ActionEXPT actionEXPT = ActionEXPData.getInstance().getExpTemplate(player, player.getLifeExperienceStorage().getLifeExperience(ELifeExpType.Fishing).getLevel());
            if (actionEXPT != null) {
                player.addExp(actionEXPT.getFishing());
            }
        }
        return true;
    }
}
