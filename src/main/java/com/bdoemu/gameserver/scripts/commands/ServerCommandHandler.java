package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.commons.utils.HeapDumper;
import com.bdoemu.commons.utils.versioning.Version;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.EServerShutdownType;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.service.ShutdownService;
import com.bdoemu.gameserver.worldInstance.World;
import org.apache.commons.lang3.text.StrBuilder;

import java.io.File;

@CommandHandler(prefix = "server", accessLevel = EAccessLevel.USER)
public class ServerCommandHandler extends AbstractCommandHandler {
    private static StrBuilder helpBuilder;

    static {
        (ServerCommandHandler.helpBuilder = new StrBuilder()).appendln("Available commands:");
        ServerCommandHandler.helpBuilder.appendln("server version");
        ServerCommandHandler.helpBuilder.appendln("server online");
        ServerCommandHandler.helpBuilder.appendln("server gametime");
        ServerCommandHandler.helpBuilder.appendln("server shutdown [seconds]");
        ServerCommandHandler.helpBuilder.appendln("server restart [seconds]");
        ServerCommandHandler.helpBuilder.appendln("server abort");
        ServerCommandHandler.helpBuilder.appendln("server stat threadpool");
        ServerCommandHandler.helpBuilder.appendln("server memorydump");
    }

    @CommandHandlerMethod(accessLevel = EAccessLevel.ADMIN)
    public static Object[] index(final Player player, final String... params) {
        return AbstractCommandHandler.getAcceptResult(ServerCommandHandler.helpBuilder.toString());
    }

    @CommandHandlerMethod
    public static Object[] version(final Player player, final String... params) {
        return AbstractCommandHandler.getAcceptResult(Version.getInstance().toString());
    }

    @CommandHandlerMethod
    public static Object[] online(final Player player, final String... params) {
        if (GameTimeService.getInstance().getServerStartTime() + 60 * 45 < (System.currentTimeMillis() / 1000L)) {
            return AbstractCommandHandler.getAcceptResult("Online players: " + ((int) Math.floor(World.getInstance().getPlayers().size() * 1.15)));
        }
        return AbstractCommandHandler.getAcceptResult("Server has not been calibrated to display players online yet, please wait 45 minutes after server start.");
    }

    @CommandHandlerMethod
    public static Object[] gametime(final Player player, final String... params) {
        return AbstractCommandHandler.getAcceptResult(String.format("Current server ingame time: %d:%d", GameTimeService.getInstance().getGameHour(), GameTimeService.getInstance().getGameMinute()));
    }

    @CommandHandlerMethod(accessLevel = EAccessLevel.ADMIN)
    public static Object[] shutdown(final Player player, final String... params) {
        int seconds;
        try {
            seconds = Integer.parseInt(params[0]);
        } catch (NumberFormatException ex) {
            return AbstractCommandHandler.getRejectResult("Number format error.");
        } catch (ArrayIndexOutOfBoundsException ex2) {
            return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
        }
        ShutdownService.getInstance().startShutdown(player.getName(), seconds, EServerShutdownType.SHUTDOWN);
        return AbstractCommandHandler.getAcceptResult("");
    }

    @CommandHandlerMethod(accessLevel = EAccessLevel.ADMIN)
    public static Object[] restart(final Player player, final String... params) {
        int seconds;
        try {
            seconds = Integer.parseInt(params[0]);
        } catch (NumberFormatException ex) {
            return AbstractCommandHandler.getRejectResult("Number format error.");
        } catch (ArrayIndexOutOfBoundsException ex2) {
            return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
        }
        ShutdownService.getInstance().startShutdown(player.getName(), seconds, EServerShutdownType.RESTART);
        return AbstractCommandHandler.getAcceptResult("");
    }

    @CommandHandlerMethod(accessLevel = EAccessLevel.ADMIN)
    public static Object[] abort(final Player player, final String... params) {
        ShutdownService.getInstance().abort(player.getName());
        return AbstractCommandHandler.getAcceptResult("");
    }

    @CommandHandlerMethod(accessLevel = EAccessLevel.ADMIN)
    public static Object[] stat(final Player player, final String... params) {
        if (params.length == 0) {
            return AbstractCommandHandler.getRejectResult("Incorrect number of arguments!");
        }
        final StrBuilder builder = new StrBuilder();
        final String statTarget = params[0];
        switch (statTarget) {
            case "threadpool": {
                builder.append(ThreadPool.getInstance().getStats());
                break;
            }
            // ?
        }
        return AbstractCommandHandler.getAcceptResult(builder.toString());
    }

    @CommandHandlerMethod(accessLevel = EAccessLevel.ADMIN)
    public static Object[] memorydump(final Player player, final String... params) {
        final File dir = new File("./dumpmem");
        if (!dir.exists() && !dir.mkdir()) {
            return AbstractCommandHandler.getRejectResult("Can't create memory dump directory: " + dir.getAbsolutePath());
        }
        final String dumpPath = HeapDumper.dumpHeap(dir.getAbsolutePath(), true);
        return AbstractCommandHandler.getAcceptResult("Memory dump saved to: " + dumpPath);
    }
}
