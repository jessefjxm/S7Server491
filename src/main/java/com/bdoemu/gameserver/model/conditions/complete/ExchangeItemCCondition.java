package com.bdoemu.gameserver.model.conditions.complete;

import com.bdoemu.gameserver.dataholders.ItemMainGroupData;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.templates.ItemMainGroupT;
import com.bdoemu.gameserver.model.items.templates.MainItemSubGroupT;

import java.util.Iterator;

public class ExchangeItemCCondition implements ICompleteConditionHandler {
    private int itemId;
    private int enchantLevel;
    private Integer npcId;
    private Integer dialogIndex;
    private long count;

    @Override
    public void load(final String... function) {
        itemId = Integer.parseInt(function[0]);
        enchantLevel = Integer.parseInt(function[1].trim());
        count = Long.parseLong(function[2].trim());
        if (function.length > 3) {
            npcId = Integer.parseInt(function[3].trim());
            dialogIndex = Integer.parseInt(function[4].trim());
        }
    }

    @Override
    public int checkCondition(Object... params) {
        if (npcId != null) {
            if (npcId != (int) params[3] || dialogIndex != (int) params[4]) {
                return 0;
            }
        }
        return itemId == (int) params[0] && enchantLevel == (int) params[1] && count == (long) params[2] ? ((Long) params[2]).intValue() : 0;
    }

    @Override
    public int getStepCount() {
        return 1;
    }

    @Override
    public EObserveType getObserveType() {
        return EObserveType.exchangeItem;
    }

    @Override
    public boolean checkStep(final Player player) {
        return false;
    }

    @Override
    public Object getKey() {
        return this.itemId;
    }

    @Override
    public boolean canInteractForQuest(final Creature target) {
        if (target.isNpc()) {
            final Integer dropId = target.getTemplate().getDropId();
            if (dropId != null) {
                final ItemMainGroupT itemMainGroupT = ItemMainGroupData.getInstance().getTemplate(dropId);
                if (itemMainGroupT != null) {
                    final Iterator<MainItemSubGroupT> iterator = itemMainGroupT.getMainItemSubGroups().iterator();
                    if (iterator.hasNext()) {
                        final MainItemSubGroupT mainItemSubGroupT = iterator.next();
                        return mainItemSubGroupT.getItemSubGroup(this.itemId, this.enchantLevel) != null;
                    }
                }
            }
        }
        return false;
    }
}
