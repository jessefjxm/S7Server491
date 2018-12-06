package com.bdoemu.gameserver.scripts.commands;

import com.bdoemu.commons.model.enums.EAccessLevel;
import com.bdoemu.gameserver.model.chat.CommandHandler;
import com.bdoemu.gameserver.model.chat.CommandHandlerMethod;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.utils.MathUtils;
import com.bdoemu.gameserver.worldInstance.World;
import org.apache.commons.lang3.text.StrBuilder;

@CommandHandler(prefix = "spawn", accessLevel = EAccessLevel.ADMIN)
public class SpawnCommandHandler extends AbstractCommandHandler {
    @CommandHandlerMethod
    public static Object[] index(final Player player, final String... params) {
        if (params.length > 0) {
            int formIndex = 0;
            int creatureId;
            int dialogId = -1;
            try {
                creatureId = Integer.parseInt(params[0]);
                if (creatureId <= 0) {
                    return AbstractCommandHandler.getRejectResult("Creature ID must be a positive/notnull number!");
                }
                if (params.length > 1) {
                    formIndex = Integer.parseInt(params[1]);
                }
                if (params.length > 2) {
                    dialogId = Integer.parseInt(params[2]);
                }
            } catch (NumberFormatException ex) {
                return AbstractCommandHandler.getRejectResult("Number format error.");
            } catch (ArrayIndexOutOfBoundsException ex2) {
                return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
            }
            final Location loc = player.getLocation();
            SpawnPlacementT placementT = new SpawnPlacementT(creatureId, formIndex, loc);
            if (dialogId != -1)
                placementT.setDialogIndex(dialogId);
            final Creature creature = Creature.newCreature(placementT);
            if (creature != null && creature.getActionStorage() != null) {
                World.getInstance().spawn(creature, true, false);
                return AbstractCommandHandler.getAcceptResult("Creature " + creature.toString() + "spawned successfully");
            }
        }
        return AbstractCommandHandler.getRejectResult("Creature didn't exist or error while spawn happened.");
    }

    @CommandHandlerMethod
    public static Object[] restartAISector(final Player player, final String... params) {
        for (final Creature creature : player.getLocation().getGameSector().getObjects()) {
            if (creature.getGameObjectId() == player.getGameObjectId())
                continue;
            if (creature.getAi() != null && !creature.isDead()) {
                creature.getAi().notifyStop();
                creature.getAi().notifyStart();
            }
        }
        return AbstractCommandHandler.getAcceptResult("Restarted AI in sector.");
    }

    @CommandHandlerMethod(accessLevel = EAccessLevel.ADMIN)
    public static Object[] killnear(final Player player, final String... params) {
        int index = 0;
        for (final Creature creature : player.getLocation().getGameSector().getObjects()) {
            if (creature.getGameObjectId() == player.getGameObjectId())
                continue;

            if (MathUtils.getDistance(player.getLocation(), creature.getLocation()) <= 1500.0) {
                if (creature.getGameStats() != null && creature.getGameStats().getHp() != null) {
                    creature.getAggroList().clear(true);
                    creature.getGameStats().getHp().updateHp(-9999999999.0, player, true);
                    ++index;
                }
            }
        }
        return AbstractCommandHandler.getAcceptResult("Killed " + index + " characters.");
    }

    @CommandHandlerMethod(accessLevel = EAccessLevel.ADMIN)
    public static Object[] killid(final Player player, final String... params) {
        if (params.length > 0) {
            int index = 0;
            int creatureId;
            try {
                creatureId = Integer.parseInt(params[0]);
                if (creatureId <= 0) {
                    return AbstractCommandHandler.getRejectResult("Creature ID must be a positive/notnull number!");
                }
            } catch (NumberFormatException ex) {
                return AbstractCommandHandler.getRejectResult("Number format error.");
            } catch (ArrayIndexOutOfBoundsException ex2) {
                return AbstractCommandHandler.getRejectResult("Incorrect parameters count.");
            }
            // TODO: make option to kill desired creature ID for whole world instead of just knownlist area.
            for (final Creature creature : player.getLocation().getGameSector().getObjects()) {
                if (creature.getTemplate().getCreatureId() == creatureId) {
                    if (MathUtils.isInRange(player.getLocation(), creature.getLocation(), 5000)) {
                        if (creature.getGameStats() != null && creature.getGameStats().getHp() != null) {
                            creature.getAggroList().clear(true);
                            creature.getGameStats().getHp().updateHp(-9999999999.0, player, true);
                            ++index;
                        }
                    }
                    return AbstractCommandHandler.getAcceptResult("Killed " + index + " creatures with id " + creatureId);
                }
            }
        }
        return AbstractCommandHandler.getRejectResult("Creature didn't exist or there was a command error.");
    }

    @CommandHandlerMethod(accessLevel = EAccessLevel.TESTER)
    public static Object[] shownear(final Player player, final String... params) {
        final StrBuilder result = new StrBuilder();
        result.appendln("Near NPC's:");
        int index = 0;
        for (final Creature creature : player.getLocation().getGameSector().getObjects()) {
            if (MathUtils.isInRange(player.getLocation(), creature.getLocation(), 1000)) {
                if (creature.getAi() != null) {
                    Creature target = creature.getAggroList().getTarget();
                    result.appendln(index + ". " + creature.getName() + " [" + creature.getCreatureId() + "] (" + creature.getLocation().toString() + ", distance: " + MathUtils.getDistance(player.getLocation(), creature.getLocation()) + ") [AI: " + creature.getAi().getState() + ", Behavior: " + creature.getAi().getBehavior() + ", Stopped: " + creature.getAi().isStopped() + ", AC: " + (creature.getAggroList() != null ? creature.getAggroList().getAggroCreatures().size() : -1) + ", Target: " + (target != null ? target.getName() : "None") + "]");
                } else
                    result.appendln(index + ". " + creature.getName() + " [" + creature.getCreatureId() + "] (" + creature.getLocation().toString() + ", distance: " + MathUtils.getDistance(player.getLocation(), creature.getLocation()) + ")");
                ++index;
            }
        }
        return AbstractCommandHandler.getAcceptResult(result.toString());
    }
}
