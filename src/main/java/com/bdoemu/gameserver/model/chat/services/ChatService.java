package com.bdoemu.gameserver.model.chat.services;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.utils.ClassUtils;
import com.bdoemu.core.configs.ChatConfig;
import com.bdoemu.core.configs.CommandsConfig;
import com.bdoemu.core.configs.FilteringConfig;
import com.bdoemu.core.configs.LocalizingOptionConfig;
import com.bdoemu.core.network.sendable.SMChat;
import com.bdoemu.core.network.sendable.SMChatWithItem;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.dataholders.xml.ObsceneFilterData;
import com.bdoemu.gameserver.model.chat.ChatChannelController;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.chat.enums.EChatResponseType;
import com.bdoemu.gameserver.model.chat.enums.EChatType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.WorldChatWithItemEvent;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.party.IParty;
import com.bdoemu.gameserver.service.LocalWarService;
import com.bdoemu.gameserver.service.database.DatabaseLogFactory;
import com.bdoemu.gameserver.worldInstance.World;
import org.atteo.classindex.ClassIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@StartupComponent("Service")
public class ChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private final Map<String, Map<String, Method>> handlerCommands = new HashMap<>();
    private final String[] EMPTY_PARAMS = new String[0];
    private ChatService() {
        for (final Class<?> clazz : ClassIndex.getAnnotated(CommandHandler.class)) {
            final CommandHandler commandHandler = clazz.getAnnotation(CommandHandler.class);
            if (commandHandler != null) {
                final List<Method> handlerMethods = ClassUtils.getMethodsAnnotatedWith(clazz, CommandHandlerMethod.class);
                if (handlerMethods.isEmpty())
                    continue;

                this.handlerCommands.computeIfAbsent(commandHandler.prefix(), k -> new HashMap<>());
                for (final Method handlerMethod : handlerMethods)
                    this.handlerCommands.get(commandHandler.prefix()).put(handlerMethod.getName().toLowerCase(), handlerMethod);
            }
        }
        ChatService.log.info("Loaded {} command handlers with {} methods.", this.handlerCommands.size(), this.handlerCommands.values().stream().flatMap(item -> item.values().stream()).collect(Collectors.toList()).size());
    }

    public static ChatService getInstance() {
        return Holder.INSTANCE;
    }

    private boolean executeHandlerCommand(final Player player, final String message) {
        if (!message.startsWith("/"))
            return false;

        final String msgParams = message.substring(1);
        final String[] params = msgParams.split(" ");
        if (params.length > 0) {
            final String handlerName = params[0].toLowerCase();
            if (this.handlerCommands.containsKey(handlerName)) {
                String methodName = null;
                if (params.length > 1)
                    methodName = params[1].toLowerCase();

                Method method = this.handlerCommands.get(handlerName).get(methodName);
                if (method == null)
                    method = this.handlerCommands.get(handlerName).get("index");

                if (method != null) {
                    boolean hasAccess = true;
                    final CommandHandler commandHandler = method.getDeclaringClass().getAnnotation(CommandHandler.class);
                    if (commandHandler != null) {
                        int handlerAccessLevel = commandHandler.accessLevel().getAccessId();
                        if (player.getAccessLevel().getAccessId() >= handlerAccessLevel) {
                            final CommandHandlerMethod handlerMethod = method.getAnnotation(CommandHandlerMethod.class);
                            if (handlerMethod != null) {
                                if (handlerMethod.accessLevel() != EAccessLevel.BANNED) {
                                    handlerAccessLevel = handlerMethod.accessLevel().getAccessId();
                                }
                                if (player.getAccessLevel().getAccessId() < handlerAccessLevel) {
                                    hasAccess = false;
                                }
                            }
                        } else {
                            hasAccess = false;
                        }
                    }
                    if (CommandsConfig.COMMAND_ACCESS.containsKey(handlerName + "." + methodName)) {
                        hasAccess = (player.getAccessLevel().getAccessId() >= CommandsConfig.COMMAND_ACCESS.get(handlerName + "." + methodName).getAccessId());
                    }
                    if (hasAccess) {
                        try {
                            final String[] paramsForInvoke = (params.length == 1) ? this.EMPTY_PARAMS : Arrays.copyOfRange(params, (methodName != null) ? 2 : 1, params.length);
                            final Object[] result = (Object[]) method.invoke(this, player, paramsForInvoke);
                            if (result != null) {
                                final EChatResponseType responseType = (EChatResponseType) result[0];
                                //if (params.length > 1) {
                                //    player.sendMessage(responseType.isAccepted() ? "Command accepted" : "Command rejected", true);
                                //}
                                if (result.length > 1) {
                                    final String messageText = result[1].toString();
                                    if (messageText.contains("\n")) {
                                        final String[] messageLines = messageText.split("[\\r\\n]+");
                                        for (final String messageLine : messageLines) {
                                            player.sendMessage(messageLine, true);
                                        }
                                    } else {
                                        player.sendMessage(result[1].toString(), true);
                                    }
                                }
                                if (responseType.isAccepted()) {
                                    DatabaseLogFactory.getInstance().logGmAudit(player.getName(), msgParams);
                                }
                                return true;
                            }
                            return false;
                        } catch (Exception e) {
                            ChatService.log.error("Error while processing command handler: " + handlerName + "." + methodName, e);
                            return false;
                        }
                    }
                    //player.sendMessage("You haven't rights for this command!", true);
                    return false;
                }
                //player.sendMessage("Unknown command: " + message, true);
            }
        }
        return false;
    }

    public void sendMessage(final Player player, String msg, final EChatType chatType, final EChatNoticeType notice, final int slotIndex, final int itemId, final int itemEnchant, final int[] jewelData, final byte[] colorData, final Class<? extends ReceivablePacket> packetClass) {
        if (FilteringConfig.CHAT_ENABLE) {
            msg = ObsceneFilterData.getInstance().filterWord(msg);
        }

        if (player.getBanController().isMuted()) {
            player.sendPacket(new SMChat(player.getName(), player.getFamily(), player.getGameObjectId(), EChatType.Notice, EChatNoticeType.Emergency, "HOOOOOOO! It appears that you are muted for " + player.getBanController().getMutedUntilString()));
            return;
        }

        if (!player.getBanController().checkChat()) {
            player.sendPacket(new SMChat(player.getName(), player.getFamily(), player.getGameObjectId(), EChatType.Notice, EChatNoticeType.Emergency, "HOOOOOOO! Cool down boy! Don't spam our chats!"));
            return;
        }

        switch (chatType) {
            case RolePlay: {
                // How do we implement that?
                break;
            }
            case LocalWar: {
                if (this.executeHandlerCommand(player, msg) || msg.startsWith("/")) {
                    return;
                }
                if (LocalWarService.getInstance().hasParticipant(player)) {
                    if (LocalWarService.getInstance().getLocalWarStatus() != null)
                        LocalWarService.getInstance().getLocalWarStatus().sendBroadcastPacket(new SMChat(player.getName(), player.getFamily(), player.getGameObjectId(), chatType, notice, msg));
                }
                break;
            }
            case WorldWithItem: {
                if (this.executeHandlerCommand(player, msg) || msg.startsWith("/")) {
                    return;
                }
                if (!player.getPlayerBag().onEvent(new WorldChatWithItemEvent(player, slotIndex))) {
                    break;
                }
                if (jewelData == null) {
                    World.getInstance().broadcastWorldPacket(new SMChat(player.getName(), player.getFamily(), player.getGameObjectId(), chatType, notice, msg));
                    break;
                }
                World.getInstance().broadcastWorldPacket(new SMChatWithItem(player, msg, chatType, notice, itemId, itemEnchant, jewelData, colorData));
                break;
            }
            case World: {
                if (this.executeHandlerCommand(player, msg) || msg.startsWith("/")) {
                    return;
                }
                if (!player.addWp(-LocalizingOptionConfig.CHAT_CONSUME_WP)) {
                    player.sendPacket(new SMNak(EStringTable.eErrNoMentalNotEnoughWp, packetClass));
                    return;
                }
                if (jewelData == null) {
                    ChatChannelController.dispatchMessage(player, msg, chatType, notice, false, -1, -1, null, null);
                    break;
                }
                ChatChannelController.dispatchMessage(player, msg, chatType, notice, true, itemId, itemEnchant, jewelData, colorData);
                break;
            }
            case Public: {
                if (this.executeHandlerCommand(player, msg) || msg.startsWith("/")) {
                    return;
                }
                if (jewelData == null) {
                    player.sendBroadcastItSelfPacket(new SMChat(player.getName(), player.getFamily(), player.getGameObjectId(), chatType, notice, msg));
                    break;
                }
                player.sendBroadcastItSelfPacket(new SMChatWithItem(player, msg, chatType, notice, itemId, itemEnchant, jewelData, colorData));
                break;
            }
            case Party: {
                if (this.executeHandlerCommand(player, msg) || msg.startsWith("/")) {
                    return;
                }
                final IParty<Player> party = player.getParty();
                if (party == null) {
                    break;
                }
                if (jewelData == null) {
                    party.sendBroadcastPacket(new SMChat(player.getName(), player.getFamily(), player.getGameObjectId(), chatType, notice, msg));
                    break;
                }
                party.sendBroadcastPacket(new SMChatWithItem(player, msg, chatType, notice, itemId, itemEnchant, jewelData, colorData));
                break;
            }
            case Guild: {
                if (this.executeHandlerCommand(player, msg) || msg.startsWith("/")) {
                    return;
                }
                final Guild guild = player.getGuild();
                if (guild == null) {
                    break;
                }
                if (jewelData == null) {
                    guild.sendBroadcastPacket(new SMChat(player.getName(), player.getFamily(), player.getGameObjectId(), chatType, notice, msg));
                    break;
                }
                guild.sendBroadcastPacket(new SMChatWithItem(player, msg, chatType, notice, itemId, itemEnchant, jewelData, colorData));
                break;
            }
        }
        DatabaseLogFactory.getInstance().logChat(chatType, player, null, msg);
    }

    public void sendPrivateMessage(final Player player, final Player target, String msg) {
        if (!player.getBanController().checkChat()) {
            player.sendPacket(new SMChat(player.getName(), player.getFamily(), player.getGameObjectId(), EChatType.Notice, EChatNoticeType.Emergency, "HOOOOOOO! Cool down boy! Don't spam our chats!"));
            return;
        }

        if (FilteringConfig.CHAT_ENABLE) {
            msg = ObsceneFilterData.getInstance().filterWord(msg);
        }
        player.sendPacket(new SMChat(target.getName(), target.getFamily(), player.getGameObjectId(), EChatType.Private, EChatNoticeType.GuildBoss, msg));
        target.sendPacket(new SMChat(player.getName(), player.getFamily(), -1024, EChatType.Private, EChatNoticeType.GuildBoss, msg));
        DatabaseLogFactory.getInstance().logChat(EChatType.Private, player, target, msg);
    }

    public void sendWorldMessage(final String message) {
        final SMChat messagePacket = new SMChat(EChatType.Notice, EChatNoticeType.None, message);
        World.getInstance().broadcastWorldPacket(messagePacket);
    }

    public Map<String, Map<String, Method>> getHandlerCommands() {
        return this.handlerCommands;
    }

    public void onLogin(Player player) {
        if (ChatConfig.MOTD_ENABLE) {
            for (String s : ChatConfig.MOTD_TEXT.split("\n")) {
                player.sendMessage(s, true);
            }
        }
        ChatChannelController.onPlayerLogin(player);
    }

    private static class Holder {
        static final ChatService INSTANCE = new ChatService();
    }
}
