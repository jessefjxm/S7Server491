package com.bdoemu.gameserver.model.chat;

import com.bdoemu.core.network.sendable.SMChat;
import com.bdoemu.core.network.sendable.SMChatWithItem;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.chat.enums.EChatType;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatChannel {
    private String _name;
    private String _shortName;
    private ConcurrentHashMap<Long, Player> _members;

    /**
     * Create a new ChatChannel instance.
     * ChatChannels are groups of users that receive messages within the same ChatChannel.
     * In order to actually be able to send a message to a ChatChannel, it's instance must be registered.
     *
     * @param name      The long (friendly) name of the ChatChannel to be created.
     * @param shortName Short name of the chat, it is used to identify chat messages.
     */
    public ChatChannel(String name, String shortName) {
        _name = name;
        _shortName = shortName;
        _members = new ConcurrentHashMap<>();
    }

    /**
     * Retrieves current channel name.
     *
     * @return Channel Name
     */
    public String getName() {
        return _name;
    }

    /**
     * Retrieves welcome message for current channel.
     *
     * @return Welcome Message
     */
    public String getShortName() {
        return _shortName;
    }

    /**
     * Retrieves member count in current channel.
     *
     * @return Member Count
     */
    public int getMemberCount() {
        return _members.size() > 50 ? ((int) (_members.size() * 1.09)) : _members.size();
    }

    /**
     * Dispatches a message to channel chat.
     *
     * @param player  Player  that sent the message.
     * @param message Message that player has sent.
     * @param type    Chat 	Type
     * @param notice  Notice 	Type
     */
    public void dispatchMessage(Player player, String message, EChatType type, EChatNoticeType notice, boolean isChatWithItem, int itemId, int itemEnchant, int[] itemJewelData, byte[] itemColorData) {
        for (Player receiver : _members.values()) {
            if (receiver != null) {
                if (isChatWithItem)
                    receiver.sendPacketNoFlush(new SMChatWithItem(player, message, type, notice, itemId, itemEnchant, itemJewelData, itemColorData));
                else
                    receiver.sendPacketNoFlush(new SMChat(player.getName(), player.getFamily(), player.getGameObjectId(), type, notice, message));
            }
        }
    }

    /**
     * Adds a member to current channel.
     *
     * @param player Member to add to current channel.
     */
    public void addMember(Player player) {
        _members.put(player.getAccountId(), player);
        player.dispatchSystemMessage("You have joined `" + getName() + "` chat channel!", EChatType.Notice, EChatNoticeType.Campaign);
    }

    /**
     * Removes a member from current channel.
     *
     * @param player Member to remove from current channel.
     * @param isLogout Is Logging out from world, save his stuff!
     */
    public void removeMember(Player player, boolean isLogout) {
        _members.remove(player.getAccountId());
        if (!isLogout) {
            player.dispatchSystemMessage("You have left `" + getName() + "` chat channel!", EChatType.Notice, EChatNoticeType.Campaign);
            player.setJoinedChannelId(-1);
        }
    }
}