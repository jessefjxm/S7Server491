// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.actioncharts.ReadBookAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AddItemEvent;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.services.ItemMainGroupService;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FrameEventReadBook extends FrameEvent {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) FrameEventReadBook.class);
    }

    public FrameEventReadBook(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        final ReadBookAction readBookAction = (ReadBookAction) action;
        final Player player = (Player) action.getOwner();
        if (!player.getHouseVisit().isInHouse(readBookAction.getHouseObjId())) {
            return false;
        }
        final HouseHold houseHold = player.getHouseholdController().getHouseHold(readBookAction.getHouseObjId());
        if (houseHold == null) {
            return false;
        }
        final HouseInstallation houseInstallation = houseHold.getHouseInstallation(readBookAction.getInstallationObjId());
        if (houseInstallation == null || houseInstallation.getEndurance() <= 0) {
            return false;
        }
        final Long usingTime = houseInstallation.getObjectTemplate().getUsingTime();
        if (usingTime != null && System.currentTimeMillis() + 2000L < readBookAction.getStartTime() + usingTime) {
            return false;
        }
        final Integer mainGroupId = houseInstallation.getObjectTemplate().getRoomServiceDropId();
        if (mainGroupId == null) {
            return false;
        }
        houseInstallation.setEndurance(houseInstallation.getEndurance() - 1);
        final List<ItemSubGroupT> drops = ItemMainGroupService.getItems(player, mainGroupId);
        if (drops.isEmpty()) {
            FrameEventReadBook.log.warn("FrameEventReadBook dropIsEmpty for characterKey: " + houseInstallation.getCharacterKey());
            return true;
        }
        final ConcurrentLinkedQueue<Item> items = new ConcurrentLinkedQueue<Item>();
        for (final ItemSubGroupT itemSubGroupT : drops) {
            items.add(new Item(itemSubGroupT.getItemId(), Rnd.get(itemSubGroupT.getMinCount(), itemSubGroupT.getMaxCount()), itemSubGroupT.getEnchantLevel()));
        }
        player.getPlayerBag().onEvent(new AddItemEvent(player, items));
        return true;
    }
}
