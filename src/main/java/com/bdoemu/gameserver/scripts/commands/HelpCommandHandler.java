package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.chat.services.ChatService;
import com.bdoemu.gameserver.model.creature.player.Player;
import org.apache.commons.lang3.text.StrBuilder;

import java.lang.reflect.Method;
import java.util.Map;

@CommandHandler(prefix = "help", accessLevel = EAccessLevel.ADMIN)
public class HelpCommandHandler extends AbstractCommandHandler {
    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        final StrBuilder builder = new StrBuilder();
        builder.append("List of available commands:");
        for (final Map.Entry<String, Map<String, Method>> entry : ChatService.getInstance().getHandlerCommands().entrySet()) {
            builder.appendln(entry.getKey());
        }
        return AbstractCommandHandler.getAcceptResult(builder.toString());
    }
}
