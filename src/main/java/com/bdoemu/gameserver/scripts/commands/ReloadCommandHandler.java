package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.commons.reload.ReloadService;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.creature.player.Player;
import org.apache.commons.lang3.text.StrBuilder;

@CommandHandler(prefix = "reload", accessLevel = EAccessLevel.ADMIN)
public class ReloadCommandHandler extends AbstractCommandHandler {
    private static StrBuilder helpBuilder;

    static {
        (ReloadCommandHandler.helpBuilder = new StrBuilder()).appendln("Available commands:");
        ReloadCommandHandler.helpBuilder.appendln("name [reloadableName] (ex: classbalance)");
        ReloadCommandHandler.helpBuilder.appendln("group [reloadableGroup] (ex: config/sqlite)");
    }

    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        return AbstractCommandHandler.getAcceptResult(ReloadCommandHandler.helpBuilder.toString());
    }

    @CommandHandlerMethod
    public static Object[] name(final Player player, final String... params) {
        if (params.length <= 0) {
            return AbstractCommandHandler.getAcceptResult("Incorrect parameters count.");
        }
        final int reloadedCount = ReloadService.getInstance().reloadByName(params[0]);
        if (reloadedCount > 0) {
            return AbstractCommandHandler.getAcceptResult("Reloaded [" + reloadedCount + "] for name [" + params[0] + "].");
        }
        return AbstractCommandHandler.getRejectResult("Didn't exist reloadable instances for name [" + params[0] + "].");
    }

    @CommandHandlerMethod
    public static Object[] group(final Player player, final String... params) {
        if (params.length <= 0) {
            return AbstractCommandHandler.getAcceptResult("Incorrect parameters count.");
        }
        final int reloadedCount = ReloadService.getInstance().reloadByGroup(params[0]);
        if (reloadedCount > 0) {
            return AbstractCommandHandler.getAcceptResult("Reloaded [" + reloadedCount + "] for group [" + params[0] + "].");
        }
        return AbstractCommandHandler.getRejectResult("Didn't exist reloadable instances for group [" + params[0] + "].");
    }
}
