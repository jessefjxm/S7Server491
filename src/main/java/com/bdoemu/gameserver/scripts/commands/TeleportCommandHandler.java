package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.core.network.sendable.SMLoadField;
import com.bdoemu.core.network.sendable.SMLoadFieldComplete;
import com.bdoemu.gameserver.dataholders.RegionData;
import com.bdoemu.gameserver.dataholders.xml.WaypointData;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.services.SpawnService;
import com.bdoemu.gameserver.model.waypoints.WaypointT;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.model.world.region.templates.RegionTemplate;
import com.bdoemu.gameserver.service.RegionService;
import com.bdoemu.gameserver.worldInstance.World;
import org.apache.commons.lang3.text.StrBuilder;

@CommandHandler(prefix = "tp", accessLevel = EAccessLevel.MODERATOR)
public class TeleportCommandHandler extends AbstractCommandHandler {
    private static StrBuilder helpBuilder;

    static {
        (TeleportCommandHandler.helpBuilder = new StrBuilder()).appendln("Available commands:");
        TeleportCommandHandler.helpBuilder.appendln("tp monster [npcId] (Optional: [dialogIndex])");
        TeleportCommandHandler.helpBuilder.appendln("tp player [playerName]");
        TeleportCommandHandler.helpBuilder.appendln("tp recall [playerName]");
        TeleportCommandHandler.helpBuilder.appendln("tp coord [x] [y] [z]");
        TeleportCommandHandler.helpBuilder.appendln("tp town (Optional: [townId])");
    }

    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        return AbstractCommandHandler.getAcceptResult(TeleportCommandHandler.helpBuilder.toString());
    }

    @CommandHandlerMethod
    public static Object[] monster(final Player player, final String... params) {
        Integer dialogIndex = null;
        int creatureId;
        try {
            creatureId = Integer.parseInt(params[0]);
            if (params.length == 2) {
                dialogIndex = Integer.parseInt(params[1]);
            }
        } catch (NumberFormatException ex) {
            return AbstractCommandHandler.getRejectResult("Invalid Parameters. Integer Required.");
        } catch (ArrayIndexOutOfBoundsException ex2) {
            return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
        }
        final Location loc = getLocationForCreatureId(creatureId, dialogIndex);
        if (loc == null) {
            return AbstractCommandHandler.getRejectResult("Creature not found");
        }
        player.sendPacket(new SMLoadField());
        player.setReadyToPlay(false);
        World.getInstance().teleport(player, loc);
        player.sendPacket(new SMLoadFieldComplete());
        return AbstractCommandHandler.getAcceptResult("Teleported to " + loc.toString() + " successfully.");
    }

    @CommandHandlerMethod
    public static Object[] player(final Player player, final String... params) {
        final String name = params[0];
        final Player targetPlayer = World.getInstance().getPlayer(name);
        if (targetPlayer != null && targetPlayer != player) {
            player.sendPacket(new SMLoadField());
            player.setReadyToPlay(false);
            World.getInstance().teleport(player, targetPlayer.getLocation());
            player.sendPacket(new SMLoadFieldComplete());
            return AbstractCommandHandler.getAcceptResult("Teleported to " + name + " successfully!");
        }
        return AbstractCommandHandler.getRejectResult("Player " + name + " doesn't exist in world.");
    }

    @CommandHandlerMethod
    public static Object[] recall(final Player player, final String... params) {
        final String name = params[0];
        final Player targetPlayer = World.getInstance().getPlayer(name);
        if (targetPlayer != null && targetPlayer != player) {
            targetPlayer.sendPacket(new SMLoadField());
            targetPlayer.setReadyToPlay(false);
            World.getInstance().teleport(targetPlayer, player.getLocation());
            targetPlayer.sendPacket(new SMLoadFieldComplete());
            return AbstractCommandHandler.getAcceptResult("Teleported to " + name + " successfully!");
        }
        return AbstractCommandHandler.getRejectResult("Player " + name + " doesn't exist in world.");
    }

    @CommandHandlerMethod
    public static Object[] coord(final Player player, final String... params) {
        final Location playerLoc = player.getLocation();
        Location loc;
        try {
            loc = new Location(Float.parseFloat(params[0]), Float.parseFloat(params[1]), Float.parseFloat(params[2]), playerLoc.getCos(), playerLoc.getSin());
        } catch (NumberFormatException ex) {
            return AbstractCommandHandler.getRejectResult("Invalid Parameters. Float Required.");
        } catch (ArrayIndexOutOfBoundsException ex2) {
            return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
        }
        if (RegionService.getInstance().getRegion(loc.getX(), loc.getY()) == null) {
            return AbstractCommandHandler.getAcceptResult("Region didn't exist in coords: " + loc.toString());
        }
        player.sendPacket(new SMLoadField());
        player.setReadyToPlay(false);
        World.getInstance().teleport(player, loc);
        player.sendPacket(new SMLoadFieldComplete());
        return AbstractCommandHandler.getAcceptResult("Successfully teleported to " + loc.toString());
    }

    @CommandHandlerMethod
    public static Object[] town(final Player player, final String... params) {
        final Location playerLoc = player.getLocation();
        Location loc = null;
        if (params.length == 0) {
            final RegionTemplate regionTemplate = RegionData.getInstance().getTemplate(player.getRegionId());
            loc = new Location(regionTemplate.getReturnX(), regionTemplate.getReturnY(), regionTemplate.getReturnZ(), playerLoc.getCos(), playerLoc.getSin());
        } else {
            int townId;
            try {
                townId = Integer.parseInt(params[0]);
            } catch (NumberFormatException ex) {
                return AbstractCommandHandler.getRejectResult("Town id parameter must be integer.");
            } catch (ArrayIndexOutOfBoundsException ex2) {
                return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
            }
            if (townId > 0) {
                final RegionTemplate regionTemplate2 = RegionData.getInstance().getTemplate(townId);
                if (regionTemplate2 == null) {
                    return AbstractCommandHandler.getRejectResult("Town with specified id not exist.");
                }
                final WaypointT waypointData = WaypointData.getInstance().getWaypoint("explore2", regionTemplate2.getWaypointKey());
                if (waypointData != null) {
                    loc = new Location(waypointData.getPosX(), waypointData.getPosY(), waypointData.getPosZ(), playerLoc.getCos(), playerLoc.getSin());
                } else {
                    loc = new Location(regionTemplate2.getReturnX(), regionTemplate2.getReturnY(), regionTemplate2.getReturnZ(), playerLoc.getCos(), playerLoc.getSin());
                }
            }
        }
        if (loc == null) {
            return AbstractCommandHandler.getRejectResult("Location not found. Unknown error.");
        }
        if (RegionService.getInstance().getRegion(loc.getX(), loc.getY()) == null) {
            return AbstractCommandHandler.getAcceptResult("Region didn't exist in coords: " + loc.toString());
        }
        player.sendPacket(new SMLoadField());
        player.setReadyToPlay(false);
        World.getInstance().teleport(player, loc);
        player.sendPacket(new SMLoadFieldComplete());
        return AbstractCommandHandler.getRejectResult("Successfully teleported to: " + loc.toString());
    }

    private static Location getLocationForCreatureId(final int creatureId, final Integer dialogIndex) {
        for (final Creature creature : World.getInstance().getObjects()) {
            if (dialogIndex != null && creature.getSpawnPlacement().getDialogIndex() != dialogIndex) {
                continue;
            }
            if (creature.getCreatureId() == creatureId) {
                return creature.getLocation();
            }
        }
        for (final SpawnPlacementT template : SpawnService.getInstance().getSpawns()) {
            if (dialogIndex != null && template.getDialogIndex() != dialogIndex) {
                continue;
            }
            if (template.getCreatureId() == creatureId) {
                return template.getLocation();
            }
        }
        for (final SpawnPlacementT template : SpawnService.getInstance().getSpawnsStatic().values()) {
            if (dialogIndex != null && template.getDialogIndex() != dialogIndex) {
                continue;
            }
            if (template.getCreatureId() == creatureId) {
                return template.getLocation();
            }
        }
        return null;
    }
}
