package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.MainServer;
import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.commons.model.enums.EBanCriteriaType;
import com.bdoemu.commons.model.enums.EBanType;
import com.bdoemu.core.configs.SecurityConfig;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.sendable.SMForciblyLogout;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.chat.services.ChatService;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.worldInstance.World;
import org.apache.commons.lang3.text.StrBuilder;

@CommandHandler(prefix = "ban", accessLevel = EAccessLevel.MODERATOR)
public class BanCommandHandler extends AbstractCommandHandler {
    private static StrBuilder helpBuilder;

    static {
        (BanCommandHandler.helpBuilder = new StrBuilder()).appendln("Available commands:");
        BanCommandHandler.helpBuilder.appendln("ban chat [characterName] [minutes]");
        BanCommandHandler.helpBuilder.appendln("ban account [characterName] [minutes]");
        BanCommandHandler.helpBuilder.appendln("ban player [characterName] [minutes]");
        BanCommandHandler.helpBuilder.appendln("ban ip [characterName] [minutes]");
        BanCommandHandler.helpBuilder.appendln("ban hwid [characterName] [minutes]");
        BanCommandHandler.helpBuilder.appendln("ban jail [characterName] [minutes]");
        BanCommandHandler.helpBuilder.appendln("ban unban [characterName] [chat|jail|account|player|ip|hwid]");
    }

    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        return AbstractCommandHandler.getAcceptResult(BanCommandHandler.helpBuilder.toString());
    }

    @CommandHandlerMethod
    public static Object[] chat(final Player player, final String... params) {
        final String result = process(player, params, EBanType.CHAT, true);
        return AbstractCommandHandler.getAcceptResult(result);
    }

    @CommandHandlerMethod
    public static Object[] account(final Player player, final String... params) {
        final String result = process(player, params, EBanType.ACCOUNT, true);
        return AbstractCommandHandler.getAcceptResult(result);
    }

    @CommandHandlerMethod
    public static Object[] player(final Player player, final String... params) {
        final String result = process(player, params, EBanType.PLAYER, true);
        return AbstractCommandHandler.getAcceptResult(result);
    }

    @CommandHandlerMethod
    public static Object[] ip(final Player player, final String... params) {
        final String result = process(player, params, EBanType.IP, true);
        return AbstractCommandHandler.getAcceptResult(result);
    }

    @CommandHandlerMethod
    public static Object[] hwid(final Player player, final String... params) {
        final String result = process(player, params, EBanType.HWID, true);
        return AbstractCommandHandler.getAcceptResult(result);
    }

    @CommandHandlerMethod
    public static Object[] jail(final Player player, final String... params) {
        final String result = process(player, params, EBanType.JAIL, true);
        return AbstractCommandHandler.getAcceptResult(result);
    }

    @CommandHandlerMethod
    public static Object[] unban(final Player player, final String... params) {
        final EBanType banType = EBanType.valueOf(params[1].toUpperCase());
        final String result = process(player, params, banType, false);
        return AbstractCommandHandler.getAcceptResult(result);
    }

    private static String process(final Player player, final String[] params, final EBanType banType, final boolean isBan) {
        if (!SecurityConfig.BAN_SYSTEM_ENABLE) {
            return "Ban system disabled by config.";
        }
        EBanCriteriaType banCriteriaType = null;
        String banCriteriaValue = null;
        long banEndTime = 0L;
        if (isBan) {
            try {
                banEndTime = GameTimeService.getServerTimeInMillis() + Long.parseLong(params[1]) * 60L * 1000L;
            } catch (Exception ex) {
                return "Invalid params";
            }
        }
        final Player playerToBan = World.getInstance().getPlayer(params[0]);
        long playerId;
        long accountId;
        if (playerToBan != null) {
            playerId = playerToBan.getObjectId();
            accountId = playerToBan.getAccountId();
        } else {
            try {
                playerId = PlayersDBCollection.getInstance().getPlayerId(params[0]);
                accountId = PlayersDBCollection.getInstance().getAccountId(params[0]);
            } catch (Exception e) {
                return "Can't find player with specified name in database!";
            }
        }
        if (playerToBan != null && playerToBan.getObjectId() == player.getObjectId()) {
            return "Can't ban himself!";
        }
        if (playerId == 0L || accountId == 0L) {
            return "Can't find player with specified name in database!";
        }
        switch (banType) {
            case JAIL: {
                banCriteriaType = EBanCriteriaType.PLAYER_ID;
                banCriteriaValue = String.valueOf(playerId);
                break;
            }
            case CHAT: {
                banCriteriaType = EBanCriteriaType.ACCOUNT_ID;
                banCriteriaValue = String.valueOf(accountId);
                break;
            }
            case ACCOUNT: {
                banCriteriaType = EBanCriteriaType.ACCOUNT_ID;
                banCriteriaValue = String.valueOf(accountId);
                break;
            }
            case PLAYER: {
                banCriteriaType = EBanCriteriaType.PLAYER_ID;
                banCriteriaValue = String.valueOf(playerId);
                break;
            }
            case HWID: {
                banCriteriaType = EBanCriteriaType.HWID;
                banCriteriaValue = "TODO";
                break;
            }
            case IP: {
                if (playerToBan != null) {
                    banCriteriaType = EBanCriteriaType.IP;
                    banCriteriaValue = World.getInstance().getPlayer(params[0]).getClient().getHostAddress();
                    break;
                }
                return "Can't ban by IP if player offline!";
            }
        }
        if (isBan) {
            if (playerToBan != null) {
                switch (banType) {
                    case JAIL: {
                        playerToBan.getBanController().setBanJailEndTime(banEndTime);
                        break;
                    }
                    case CHAT: {
                        playerToBan.getBanController().setChatMute((int) (banEndTime / 1000L));
                        break;
                    }
                    case ACCOUNT:
                    case PLAYER:
                    case HWID:
                    case IP: {
                        // You have been disconnected by the administrator
                        playerToBan.getClient().close(new SMForciblyLogout((int) 0x6A37E1AB));
                        break;
                    }
                }
                if (SecurityConfig.BAN_SYSTEM_ANNOUNCE_PUNISHMENT) {
                    ChatService.getInstance().sendWorldMessage(String.format("%s banned %s by %s", player.getName(), playerToBan.getName(), banType.toString()));
                }
            }
            return MainServer.getRmi().ban(ServerConfig.SERVER_ID, banCriteriaType, banCriteriaValue, banType, player.getName(), "Ban from gameserver", banEndTime);
        }
        return MainServer.getRmi().unBan(ServerConfig.SERVER_ID, banCriteriaType, banCriteriaValue, banType);
    }
}
