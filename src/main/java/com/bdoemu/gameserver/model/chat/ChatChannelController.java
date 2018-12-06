package com.bdoemu.gameserver.model.chat;

import com.bdoemu.core.network.sendable.SMChat;
import com.bdoemu.core.network.sendable.SMChatWithItem;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.chat.enums.EChatType;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.HashMap;
import java.util.Map;

public class ChatChannelController {
    /**
     * Storage for all existing channels.
     */
    private static Map<Integer, ChatChannel> _registeredChannels = new HashMap<>();
    private static Map<Long, Player> _readonlyPlayers = new HashMap<>();
    private static boolean _isInit = false;

    /**
     * Dispatches a message to channel chat.
     *
     * @param player  Player that sent the message.
     * @param message Message that player has sent.
     * @param type    Chat Type
     * @param notice  Notice Type
     */
    public static void dispatchMessage(Player player, String message, EChatType type, EChatNoticeType notice, boolean isChatWithItem, int itemId, int itemEnchant, int[] itemJewelData, byte[] itemColorData) {
        ChatChannel channel = _registeredChannels.get(player.getJoinedChatChannelId());
        if (channel != null) {
            channel.dispatchMessage(player, message, type, notice, isChatWithItem, itemId, itemEnchant, itemJewelData, itemColorData);

            try {
                for (Player receiver : _readonlyPlayers.values()) {
                    if (receiver != null && receiver.getGameObjectId() != player.getGameObjectId() && receiver.getJoinedChatChannelId() != player.getJoinedChatChannelId()) {
                        if (isChatWithItem)
                            receiver.sendPacketNoFlush(new SMChatWithItem(player, "[" + channel.getShortName() + "] " + message, type, notice, itemId, itemEnchant, itemJewelData, itemColorData));
                        else
                            receiver.sendPacketNoFlush(new SMChat(player.getName(), player.getFamily(), player.getGameObjectId(), type, notice, "[" + channel.getShortName() + "] " + message));
                    }
                }
            } catch (Exception e) {
                //
            }
        } else {
            player.dispatchSystemMessage("You must join a chat channel in order to talk in `Channel` chat !", EChatType.Notice, EChatNoticeType.Emergency);
            player.dispatchSystemMessage("Type `/channel show` to view all available channels and `/channel` to view all available commands.", EChatType.Notice, EChatNoticeType.Emergency);
        }
    }

    public static void toggleReadonly(Player player) {
        if (_readonlyPlayers.get(player.getAccountId()) != null)
            _readonlyPlayers.remove(player.getAccountId());
        else
            _readonlyPlayers.put(player.getAccountId(), player);
    }

    public static void reassignPlayer(Player player, int newChannelId) {
        disposePlayer(player, false);

        ChatChannel channel = _registeredChannels.get(newChannelId);
        if (channel != null) {
            channel.addMember(player);
            player.setJoinedChannelId(newChannelId);
        }
    }

    public static void assignPlayer(Player player, int channelId) {
        disposePlayer(player, false);
        ChatChannel channel = _registeredChannels.get(channelId);
        if (channel != null) {
            channel.addMember(player);
            player.setJoinedChannelId(channelId);
        } else {
            player.dispatchSystemMessage("Channel with id `" + channelId + "` does not exist.", EChatType.Notice, EChatNoticeType.Emergency);
        }
    }

    public static void disposePlayer(Player player, boolean isLogout) {
        ChatChannel channel = _registeredChannels.get(player.getJoinedChatChannelId());
        if (channel != null)
            channel.removeMember(player, isLogout);
    }

    public static void displayChannels(Player player) {
        player.dispatchSystemMessage("Available chat channels:", EChatType.RolePlay, EChatNoticeType.Normal);

        for (Map.Entry<Integer, ChatChannel> channel : _registeredChannels.entrySet()) {
            ChatChannel chx = channel.getValue();
            if (chx != null)
                player.dispatchSystemMessage(channel.getKey() + ". " + chx.getName() + " (Connected: " + chx.getMemberCount() + ")", EChatType.RolePlay, EChatNoticeType.Normal);
        }

        player.dispatchSystemMessage(" ", EChatType.RolePlay, EChatNoticeType.Normal);
        player.dispatchSystemMessage("Type /SUKA NAHUY WROT EBAL! <CHANNEL_ID> to join a channel.", EChatType.RolePlay, EChatNoticeType.Normal);
        player.dispatchSystemMessage("Example: /channel join 1", EChatType.RolePlay, EChatNoticeType.Normal);
    }

    public static void init() {
        _isInit = true;
        _registeredChannels.put(1, new ChatChannel("English", "EN"));
        _registeredChannels.put(2, new ChatChannel("Deutsch", "DE"));
        _registeredChannels.put(3, new ChatChannel("\u0420\u0443\u0441\u0441\u043a\u0438\u0439", "RU"));
        _registeredChannels.put(4, new ChatChannel("Tagalog", "PH"));
        _registeredChannels.put(5, new ChatChannel("Portugu\u00eas/Espa\u00f1ol", "PT"));
    }

    public static void onPlayerLogin(Player player) {
        if (!_isInit)
            init();

        ChatChannel chx = _registeredChannels.get(player.getJoinedChatChannelId());
        if (chx == null)
            displayChannels(player);
        else
            chx.addMember(player);
    }
}