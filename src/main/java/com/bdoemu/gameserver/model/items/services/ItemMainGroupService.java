package com.bdoemu.gameserver.model.items.services;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.dataholders.ItemData;
import com.bdoemu.gameserver.dataholders.ItemMainGroupData;
import com.bdoemu.gameserver.model.conditions.ConditionService;
import com.bdoemu.gameserver.model.conditions.accept.IAcceptConditionHandler;
import com.bdoemu.gameserver.model.creature.DropBag;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.exploration.Discovery;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EDropBagType;
import com.bdoemu.gameserver.model.items.templates.ItemMainGroupT;
import com.bdoemu.gameserver.model.items.templates.ItemSubGroupT;
import com.bdoemu.gameserver.model.items.templates.ItemTemplate;
import com.bdoemu.gameserver.model.items.templates.MainItemSubGroupT;
import com.bdoemu.gameserver.utils.PARand;
import com.sun.javafx.binding.StringFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemMainGroupService {
    public static List<ItemSubGroupT> getItems(final Player player, final int mainGroupId) {
        return getItems(player, mainGroupId, 100);
    }

    public static List<ItemSubGroupT> getItems(final Player player, final int mainGroupId, final int bonusRate) {
        final ItemMainGroupT itemMainGroupT = ItemMainGroupData.getInstance().getTemplate(mainGroupId);
        if (itemMainGroupT == null)
            return Collections.emptyList();

        Discovery discovery             = player.getExploration().getDiscovery(player.getRegion().getTemplate().getWaypointKey());
        final int addedDiscoveryRate    = discovery != null ? discovery.getLevel() * 2 : 0;
        final boolean onlyOneItem       = itemMainGroupT.isSelectOnlyOne();
        final List<ItemSubGroupT> items = new ArrayList<>();
        final int dropRate              = player.getGameStats().getDropItemLuck().getDropItemRate() / 10000 + bonusRate + addedDiscoveryRate;

        //Loop through all subgroups
        for (final MainItemSubGroupT subGroupT : itemMainGroupT.getMainItemSubGroups()) {

            // Send verification message to administrators for debugging
            if (player.getAccessLevel().isAdmin())
                player.sendMessage(StringFormatter.format("DoSelectOnlyOne ItemMainGroup: %d ItemSubGroup: %d size: %d node: %d bonus: %d luck: %d", itemMainGroupT.getMainGroupId(), subGroupT.getSubGroupId(), subGroupT.getItemSubGroups().size(), addedDiscoveryRate, dropRate, player.getGameStats().getDropItemLuck().getDropItemRate()).getValue(), true);

            // Check if conditions are valid
            final IAcceptConditionHandler[][] handlers = subGroupT.getConditions();
            if (handlers != null && !ConditionService.checkCondition(handlers, player))
                continue;

            // Check if we can select this main group
            if (!PARand.PARollChance(subGroupT.getSelectRate()))
                continue;

            // Generate items for us to use
            for (final ItemSubGroupT itemSubGroupT : subGroupT.getItemSubGroups()) {
                if (ItemData.getInstance().getItemTemplate(itemSubGroupT.getItemId()) == null)
                    continue;

                // 75000 * (105 / 100) = 78750
                if (!PARand.PARollChance(itemSubGroupT.getSelectRate() * ((double) dropRate / 100.0)))
                    continue;

                items.add(itemSubGroupT);
            }
        }
        if (onlyOneItem && !items.isEmpty())
            return Collections.singletonList(PARand.PARollList(items));
        return items;
    }

    public static DropBag getDropBag(final int dropId, final Player killer, final int sourceGameObjectId, final int sourceCreatureId, final EDropBagType dropBagType, final int dropRate) {
        final List<Item> items = new ArrayList<>();
        for (final ItemSubGroupT itemSubGroupT : getItems(killer, dropId, dropRate)) {
            final ItemTemplate itemTemplate = ItemData.getInstance().getItemTemplate(itemSubGroupT.getItemId());
            int itemCount = Rnd.get(itemSubGroupT.getMinCount(), itemSubGroupT.getMaxCount());
            if (itemTemplate.isStack() && Rnd.getChance(killer.getGameStats().getDoubleDropItemLuck().getIntValue() / 10000)) {
                itemCount *= 2;
            }
            items.add(new Item(itemTemplate, itemCount, itemSubGroupT.getEnchantLevel()));
        }
        if (!items.isEmpty()) {
            return new DropBag(items, sourceGameObjectId, sourceCreatureId, dropBagType);
        }
        return null;
    }
}
