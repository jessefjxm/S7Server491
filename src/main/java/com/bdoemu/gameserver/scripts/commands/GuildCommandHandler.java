package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.events.DisjoinMemberFromGuildEvent;
import org.apache.commons.lang3.text.StrBuilder;

@CommandHandler(prefix = "guild", accessLevel = EAccessLevel.USER)
public class GuildCommandHandler extends AbstractCommandHandler {
    private static StrBuilder helpBuilder;

    static {
        (GuildCommandHandler.helpBuilder = new StrBuilder()).appendln("Available commands:");
        GuildCommandHandler.helpBuilder.appendln("guild emblem");
        GuildCommandHandler.helpBuilder.appendln("guild abandon");
    }

    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        final Guild guild = player.getGuild();
        if (guild != null) {
            return AbstractCommandHandler.getAcceptResult(GuildCommandHandler.helpBuilder.toString());
        }
        return AbstractCommandHandler.getRejectResult("You are not a guild member.");
        //return AbstractCommandHandler.getRejectResult(ChatConfig.SERVER_CUSTOM_MESSAGE_LANGUAGE.equals("BR") ? "Voce nao e membro de guilda." : "You are not a guild member.");
    }

    @CommandHandlerMethod
    public static Object[] emblem(final Player player, final String... params) {
        return AbstractCommandHandler.getRejectResult("This command is no longer used. Please do Guild Quests instead.");
        ////TODO: add cooldown to avoid spam?
        //final Guild guild = player.getGuild();
        //if (guild != null)
        //{
        //	if (guild.getLeaderAccountId() == player.getAccountId())
        //	{
        //		if (!player.getPlayerBag().onEvent(new AddItemEvent(player, 65013, 0, 1))) {
        //            return AbstractCommandHandler.getRejectResult("Failed adding Item.");
        //        }
        //        return AbstractCommandHandler.getAcceptResult("Item successfully added.");
        //	}
        //	return AbstractCommandHandler.getRejectResult("You are not a guild master.");
        //
        //}
        //return AbstractCommandHandler.getRejectResult("You are not a guild member.");
    }

    @CommandHandlerMethod
    public static Object[] abandon(final Player player, final String... params) {
        final Guild guild = player.getGuild();
        if (guild != null) {
            if (!(guild.getLeaderAccountId() == player.getAccountId())) {
                guild.onEvent(new DisjoinMemberFromGuildEvent(guild, player));
                return AbstractCommandHandler.getAcceptResult("Guild left successfully.");
            }
            return AbstractCommandHandler.getRejectResult("Guild master cannot abandon.");
        }
        return AbstractCommandHandler.getRejectResult("You are not a guild member.");
    }
}
