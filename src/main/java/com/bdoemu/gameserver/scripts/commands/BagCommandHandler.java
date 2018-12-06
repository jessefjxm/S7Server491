package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.AddItemEvent;
import com.bdoemu.gameserver.worldInstance.World;
import org.apache.commons.lang3.text.StrBuilder;

@CommandHandler(prefix = "bag", accessLevel = EAccessLevel.MODERATOR)
public class BagCommandHandler extends AbstractCommandHandler {
    private static StrBuilder helpBuilder;

    static {
        (BagCommandHandler.helpBuilder = new StrBuilder()).appendln("Available commands:");
        BagCommandHandler.helpBuilder.appendln("add [itemId] [count] (Optional: [enchantLevel])");
        BagCommandHandler.helpBuilder.appendln("addtoplayer [playerName] [itemId] [count] (Optional: [enchantLevel])");
    }

    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        return AbstractCommandHandler.getAcceptResult(BagCommandHandler.helpBuilder.toString());
    }

    @CommandHandlerMethod
    public static Object[] add(final Player player, final String[] params) {
        int enchantLevel = 0;
        if (params.length < 2) {
            return index(player, params);
        }
        Integer itemId;
        Long count;
        try {
            itemId = Integer.parseInt(params[0]);
            count = Long.parseLong(params[1]);
            if (params.length >= 3) {
                enchantLevel = Integer.parseInt(params[2]);
            }
        } catch (NumberFormatException ex) {
            return AbstractCommandHandler.getRejectResult("Incorrect number format.");
        } catch (ArrayIndexOutOfBoundsException ex2) {
            return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
        }
        if (!player.getPlayerBag().onEvent(new AddItemEvent(player, itemId, enchantLevel, count))) {
            return AbstractCommandHandler.getRejectResult("Fail while executing add ItemEvent.");
        }
        return AbstractCommandHandler.getAcceptResult("Item successfully added.");
    }

    @CommandHandlerMethod(accessLevel = EAccessLevel.ADMIN)
    public static Object[] addtoplayer(final Player player, final String[] params) {
        int enchantLevel = 0;
        if (params.length < 3) {
            return index(player, params);
        }
        Player target;
        Integer itemId;
        Long count;
        try {
            target = World.getInstance().getPlayer(params[0]);
            if (target == null) {
                return AbstractCommandHandler.getRejectResult("Player " + params[3] + " doesn't exist in world!");
            }
            itemId = Integer.parseInt(params[1]);
            count = Long.parseLong(params[2]);
            if (params.length >= 4) {
                enchantLevel = Integer.parseInt(params[3]);
            }
        } catch (NumberFormatException ex) {
            return AbstractCommandHandler.getRejectResult("Incorrect number format.");
        } catch (ArrayIndexOutOfBoundsException ex2) {
            return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
        }
        if (!target.getPlayerBag().onEvent(new AddItemEvent(target, itemId, enchantLevel, count))) {
            return AbstractCommandHandler.getRejectResult("Fail while executing add ItemEvent.");
        }
        return AbstractCommandHandler.getAcceptResult("Item successfully added.");
    }
}
