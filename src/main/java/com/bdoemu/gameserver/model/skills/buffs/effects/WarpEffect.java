package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMLoadField;
import com.bdoemu.core.network.sendable.SMLoadFieldComplete;
import com.bdoemu.gameserver.dataholders.TeleportData;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.misc.enums.ETeleportType;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WarpEffect extends ABuffEffect {
    private static final Logger log = LoggerFactory.getLogger(WarpEffect.class);

    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final Integer[] params = buffTemplate.getParams();
        final ETeleportType teleportType = ETeleportType.values()[params[0]];
        Location teleportLocation = null;
        switch (teleportType) {
            case NORMAL:
            case JAIL: {
                teleportLocation = TeleportData.getInstance().getTeleportLocation(teleportType, params[1]);
                break;
            }
            case RANDOM: {
                final Location fixedTeleportLocation = owner.getVar("fixed_teleport_location", Location.class, null);
                if (fixedTeleportLocation == null) {
                    final List<Integer> points = new ArrayList<Integer>();
                    for (int index = 1; index < 10; ++index) {
                        final Integer param = params[index];
                        if (param != null) {
                            points.add(param);
                        }
                    }
                    final int rnd = Rnd.get(1, points.size() - 1);
                    teleportLocation = TeleportData.getInstance().getTeleportLocation(teleportType, points.get(rnd));
                    owner.setVar("fixed_teleport_location", teleportLocation);
                    break;
                }
                teleportLocation = fixedTeleportLocation;
                break;
            }
            default: {
                WarpEffect.log.warn("Not implemented ETeleportType: {}", (Object) teleportType);
                break;
            }
        }
        for (final Creature target : targets) {
            if (teleportLocation != null && target.isPlayer()) {
                final Player player = (Player) target;
                player.sendPacket(new SMLoadField());
                player.setReadyToPlay(false);
                teleportLocation.setLocation(player.getLocation().getCos(), player.getLocation().getSin());
                World.getInstance().teleport(player, teleportLocation);
                player.sendPacket(new SMLoadFieldComplete());
            }
        }
        return null;
    }
}
