package com.bdoemu.gameserver.model.conditions.accept;

import com.bdoemu.gameserver.dataholders.ContentsGroupOptionData;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.HashSet;
import java.util.Set;

public class IsContentsGroupOpenACondition implements IAcceptConditionHandler {
    private Set<Integer> contentGroupList;

    public IsContentsGroupOpenACondition() {
        this.contentGroupList = new HashSet<Integer>();
    }

    @Override
    public void load(final String conditionValue, final String operator, final String operatorValue, final boolean hasExclamation) {
        final String[] contentGroupData = conditionValue.trim().split(",");
        for (final String contentGroup : contentGroupData) {
            this.contentGroupList.add(Integer.valueOf(contentGroup));
        }
    }

    @Override
    public boolean checkCondition(final Player player) {
        return ContentsGroupOptionData.getInstance().isContentsGroupOpen(this.contentGroupList);
    }
}
