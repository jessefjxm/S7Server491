package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMAcceptQuest;
import com.bdoemu.core.network.sendable.SMAcceptQuest;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMUpdateQuest;
import com.bdoemu.gameserver.dataholders.QuestData;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AddItemEvent;
import com.bdoemu.gameserver.model.creature.player.quests.Quest;
import com.bdoemu.gameserver.model.creature.player.quests.templates.QuestTemplate;
import com.bdoemu.gameserver.model.items.Item;
import org.apache.commons.lang3.text.StrBuilder;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

@CommandHandler(prefix = "quest", accessLevel = EAccessLevel.ADMIN)
public class QuestCommandHandler extends AbstractCommandHandler {
    private static StrBuilder helpBuilder;

    static {
        (QuestCommandHandler.helpBuilder = new StrBuilder()).appendln("Available commands:");
        QuestCommandHandler.helpBuilder.appendln("quest start [groupId] [questId]");
        QuestCommandHandler.helpBuilder.appendln("quest complete [groupId] [questId]");
        QuestCommandHandler.helpBuilder.appendln("quest update [groupId] [questId] [stepIndex] [stepCount]");
    }

    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        return AbstractCommandHandler.getAcceptResult(QuestCommandHandler.helpBuilder.toString());
    }

    @CommandHandlerMethod
    public static Object[] start(final Player player, final String... params) {
        if (params.length >= 2) {
            try {
                final int groupId = Integer.parseInt(params[0]);
                final int questId = Integer.parseInt(params[1]);
                final QuestTemplate template = QuestData.getInstance().getTemplate(groupId, questId);
                if (template == null) {
                    return AbstractCommandHandler.getRejectResult("QuestTemplate null.");
                }
                final Optional<Quest> q = player.getPlayerQuestHandler().getProgressQuestList().stream().filter(quest -> quest.getQuestGroupId() == groupId && quest.getQuestId() == questId).findAny();
                if (q.isPresent()) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoQuestCantAquire, CMAcceptQuest.class));
                    return AbstractCommandHandler.getRejectResult();
                }
                final int size = player.getPlayerQuestHandler().getProgressQuestList().size();
                if (size >= 30) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoQuestNotMoreAquire, CMAcceptQuest.class));
                    return AbstractCommandHandler.getRejectResult();
                }
                final Integer acceptPushItem = template.getAcceptPushItem();
                if (acceptPushItem != null) {
                    final ConcurrentLinkedQueue<Item> addTasks = new ConcurrentLinkedQueue<Item>();
                    addTasks.add(new Item(acceptPushItem, 1L, 0));
                    if (!player.getPlayerBag().onEvent(new AddItemEvent(player, addTasks, EStringTable.eErrNoItemTakeFromQuest))) {
                        return AbstractCommandHandler.getRejectResult("Missed AcceptPushItem: " + acceptPushItem);
                    }
                }
                final Quest quest2 = new Quest(player, template);
                player.getPlayerQuestHandler().putToProgressList(quest2);
                player.sendPacket(new SMAcceptQuest(groupId, questId));
                quest2.onAcceptQuest();
                return AbstractCommandHandler.getAcceptResult();
            } catch (NumberFormatException ex) {
                return AbstractCommandHandler.getRejectResult("Number format error.");
            }
        }
        return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
    }

    @CommandHandlerMethod
    public static Object[] complete(final Player player, final String... params) {
        return AbstractCommandHandler.getAcceptResult();
    }

    @CommandHandlerMethod
    public static Object[] update(final Player player, final String... params) {
        if (params.length >= 4) {
            try {
                final int groupId = Integer.parseInt(params[0]);
                final int questId = Integer.parseInt(params[1]);
                final int step = Integer.parseInt(params[2]);
                final int count = Integer.parseInt(params[3]);
                final QuestTemplate template = QuestData.getInstance().getTemplate(groupId, questId);
                if (template == null) {
                    return AbstractCommandHandler.getRejectResult("QuestTemplate null.");
                }
                final Quest quest = player.getPlayerQuestHandler().getProgressQuest(groupId, questId);
                if (quest == null) {
                    return AbstractCommandHandler.getRejectResult("Quest is not in progress.");
                }
                if (step < 0 || step > quest.getSteps().length - 1) {
                    return AbstractCommandHandler.getRejectResult("Invalid step index.");
                }
                if (count < 0) {
                    return AbstractCommandHandler.getRejectResult("Invalid step count.");
                }
                quest.getSteps()[step] = count;
                player.sendPacket(new SMUpdateQuest(quest));
                return AbstractCommandHandler.getAcceptResult();
            } catch (NumberFormatException ex) {
                return AbstractCommandHandler.getRejectResult("Number format error.");
            }
        }
        return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
    }
}
