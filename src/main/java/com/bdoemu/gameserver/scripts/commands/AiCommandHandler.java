package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.gameserver.model.ai.services.AIServiceProvider;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.creature.player.Player;

@CommandHandler(prefix = "ai", accessLevel = EAccessLevel.ADMIN)
public class AiCommandHandler extends AbstractCommandHandler {
    @CommandHandlerMethod
    public static Object[] tryLoad(final Player player, final String... params) {
        AIServiceProvider.getInstance();
        return AbstractCommandHandler.getRejectResult("Accepted.");
    }
}
