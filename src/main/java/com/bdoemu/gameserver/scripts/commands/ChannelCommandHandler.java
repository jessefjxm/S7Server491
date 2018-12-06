package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.gameserver.model.chat.ChatChannelController;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.worldInstance.World;
import org.apache.commons.lang3.text.StrBuilder;

@CommandHandler(prefix = "channel", accessLevel = EAccessLevel.USER)
public class ChannelCommandHandler extends AbstractCommandHandler {
    private static StrBuilder helpBuilder;

    static {
        (ChannelCommandHandler.helpBuilder = new StrBuilder()).appendln("Available commands:");
        ChannelCommandHandler.helpBuilder.appendln("channel join");
        ChannelCommandHandler.helpBuilder.appendln("channel leave");
        ChannelCommandHandler.helpBuilder.appendln("channel show");
    }

    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        return AbstractCommandHandler.getAcceptResult(ChannelCommandHandler.helpBuilder.toString());
    }

    @CommandHandlerMethod
    public static Object[] join(final Player player, final String... params) {
        Integer channelId;
        try {
            channelId = Integer.parseInt(params[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            return AbstractCommandHandler.getRejectResult("Invalid parameters. The command is `/channel join <CHANNEL_ID>`, for example: `/channel join 1`.");
        }

        ChatChannelController.assignPlayer(player, channelId);
        return AbstractCommandHandler.getAcceptResult();
    }

    @CommandHandlerMethod
    public static Object[] leave(final Player player, final String... params) {
        ChatChannelController.disposePlayer(player, false);
        return AbstractCommandHandler.getAcceptResult();
    }

    @CommandHandlerMethod
    public static Object[] show(final Player player, final String... params) {
        ChatChannelController.displayChannels(player);
        return AbstractCommandHandler.getAcceptResult();
    }

    @CommandHandlerMethod
    public static Object[] readonly(final Player player, final String... params) {
        ChatChannelController.toggleReadonly(player);
        return AbstractCommandHandler.getRejectResult("Channel readonly status has been toggled.");
    }

    @CommandHandlerMethod
    public static Object[] moveto(final Player player, final String... params) {
        Integer channelId;
        String targetPlayerName;
        try {
            channelId = Integer.parseInt(params[0]);
            targetPlayerName = params[1];
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            return AbstractCommandHandler.getRejectResult("Invalid parameters.");
        }
        final Player playerToMove = World.getInstance().getPlayer(targetPlayerName);
        if (playerToMove == null)
            return AbstractCommandHandler.getRejectResult("Player does not exist.");

        ChatChannelController.reassignPlayer(playerToMove, channelId);
        return AbstractCommandHandler.getAcceptResult();
    }

    @CommandHandlerMethod
    public static Object[] ban(final Player player, final String... params) {
        // <CHANNEL_ID> <PLAYER_NAME>
        // bans a player from specific channel.
        return AbstractCommandHandler.getAcceptResult("Not implemented");
    }
}
