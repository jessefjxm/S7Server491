package com.bdoemu.gameserver.model.functions;

import com.bdoemu.core.network.sendable.SMSetEscort;
import com.bdoemu.gameserver.model.ai.deprecated.CreatureAI;
import com.bdoemu.gameserver.model.conditions.accept.CheckEscortACondition;
import com.bdoemu.gameserver.model.conditions.complete.ICompleteConditionHandler;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.quests.Quest;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.skills.services.SkillService;

import java.util.Collections;

public class ExecuteHandlerFunction implements IFunctionHandler {
    private String handler;

    @Override
    public void load(final int dialogIndex, final String functionData) {
        this.handler = functionData.toLowerCase();
    }

    @Override
    public boolean isDisabledByContentsGroup() {
        return false;
    }

    @Override
    public void doFunction(final Player player, final Npc npc, final long applyCount, final CreatureTemplate creatureTemplate, final int dialogIndex) {
        final String handler = this.handler;
        switch (handler) {
            case "handlertalktodie": {
                if (npc == null) {
                    break;
                }
                final CreatureAI ai = npc.getAi();
                if (ai == null) {
                    break;
                }
                ai.HandlerTalkToDie(player, null);
                if (CheckEscortACondition.escortList.contains(npc.getCreatureId())) {
                    for (final Quest quest : player.getPlayerQuestHandler().getProgressQuestList()) {
                        for (final ICompleteConditionHandler condition : quest.getTemplate().getCompleteConditions()) {
                            if (condition.canInteractForQuest(npc)) {
                                player.setEscort(npc);
                                player.sendPacket(new SMSetEscort(npc.getGameObjectId()));
                                SkillService.useSkill(player, 40194, null, Collections.singletonList(player));
                                break;
                            }
                        }
                    }
                    break;
                }
                break;
            }
        }
    }
}
