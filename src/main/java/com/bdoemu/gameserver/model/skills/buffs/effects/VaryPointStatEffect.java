// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.core.network.sendable.SMSetCharacterSpeeds;
import com.bdoemu.core.network.sendable.SMSetCharacterStatPoint;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.stats.elements.BuffElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VaryPointStatEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final List<ActiveBuff> buffs = new ArrayList<ActiveBuff>();
        final Integer[] params = buffTemplate.getParams();
        final int type = params[0];
        final int value = params[1];
        final BuffElement element = new BuffElement(value, false);
        for (final Creature target : targets) {
            buffs.add(new ActiveBuff(skillT, buffTemplate, owner, target, element));
        }
        return buffs;
    }

    @Override
    public void applyEffect(final ActiveBuff activeBuff) {
        final Creature target = activeBuff.getTarget();
        final BuffElement element = activeBuff.getElement();
        final Integer[] params = activeBuff.getTemplate().getParams();
        final int type = params[0];
        final int value = params[1];
        switch (type) {
            case 0: {
                target.getGameStats().getMoveSpeedRate().addElement(element);
                break;
            }
            case 1: {
                target.getGameStats().getAttackSpeedRate().addElement(element);
                break;
            }
            case 2: {
                target.getGameStats().getCastingSpeedRate().addElement(element);
                break;
            }
            case 3: {
                target.getGameStats().getCriticalRate().addElement(element);
                break;
            }
            case 4: {
                target.getGameStats().getDropItemLuck().addElement(element);
                break;
            }
            case 5: {
                target.getGameStats().getFishingLuck().addElement(element);
                break;
            }
            case 6: {
                target.getGameStats().getCollectionLuck().addElement(element);
                break;
            }
        }
        if (target.isPlayer()) {
            final Player player = (Player) target;
            player.sendPacket(new SMSetCharacterSpeeds(player.getGameStats()));
            player.sendBroadcastItSelfPacket(new SMSetCharacterStatPoint(player));
        }
    }

    @Override
    public void endEffect(final ActiveBuff activeBuff) {
        final Integer[] params = activeBuff.getTemplate().getParams();
        final int type = params[0];
        final BuffElement element = activeBuff.getElement();
        final Creature target = activeBuff.getTarget();
        switch (type) {
            case 0: {
                target.getGameStats().getMoveSpeedRate().removeElement(element);
                break;
            }
            case 1: {
                target.getGameStats().getAttackSpeedRate().removeElement(element);
                break;
            }
            case 2: {
                target.getGameStats().getCastingSpeedRate().removeElement(element);
                break;
            }
            case 3: {
                target.getGameStats().getCriticalRate().removeElement(element);
                break;
            }
            case 4: {
                target.getGameStats().getDropItemLuck().removeElement(element);
                break;
            }
            case 5: {
                target.getGameStats().getFishingLuck().removeElement(element);
                break;
            }
            case 6: {
                target.getGameStats().getCollectionLuck().removeElement(element);
                break;
            }
        }
        if (target.isPlayer()) {
            final Player player = (Player) target;
            player.sendPacket(new SMSetCharacterSpeeds(player.getGameStats()));
            player.sendBroadcastItSelfPacket(new SMSetCharacterStatPoint(player));
        }
    }
}
