package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMApplyActionRestriction;
import com.bdoemu.core.network.sendable.SMDoPhysicalAttack;
import com.bdoemu.gameserver.model.actions.enums.EGuardType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.agrolist.AttackResult;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// test
// end test

public class AddActionRestrictEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final Integer[] params = buffTemplate.getParams();
        final int type = params[0];
        final int time = params[1];

        final List<Integer> ignoredActionRestrictionEffectTypes = new ArrayList<Integer>();
        try {
            for (int index = 2; index < 10; ++index) {
                final Integer param = params[index];
                if (param != null && param > 0)
                    ignoredActionRestrictionEffectTypes.add(param);
            }
        } catch (IndexOutOfBoundsException e) {
            //
        }

        for (final Creature target : targets) {
            if (target.getAi() == null)
                continue;

            final boolean isPvpTarget = owner.isPlayer() && target.isPlayer();
            boolean isImmune = false;

            if (target.getActionStorage().getActionChartActionT().getGuardType() == EGuardType.SuperArmor) {
                //if (owner.isPlayer())
                //	((Player) owner).dispatchSystemMessage("RestrictAction[SuperArmor]", EChatType.Notice, EChatNoticeType.Normal);
                isImmune = true;
            } else
                // I have my block up, so theorethically, you cannot stun me in any way.
                if (target.getActionStorage().getActionChartActionT().getGuardType().isDefence()) {
                    // I only care if you have your block up at your screen.
                    if (AttackResult.isFrontSideAttack(owner.getLocation(), target.getLocation())) {
                        // Okay block is up, let's remove some stats from your "block" so we could actually stun you.
                        if (type != 0) {
                            //if (owner.isPlayer())
                            //	((Player) owner).dispatchSystemMessage("RestrictAction[GuardFront]", EChatType.Notice, EChatNoticeType.Normal);
                            isImmune = true; // Okay, you still have your block up, so we cannot stun you.
                        }
                    }
                }

            if (target.getActionStorage().getActionChartActionT().isEvadeEmergency())
                isImmune = true;

            if (!isImmune && target.isPlayer()) {
                ignoredActionRestrictionEffectTypes.add(type);
                if (((Player) target).getActionRestrictionController().checkRestrictCondition(ignoredActionRestrictionEffectTypes)) {
                    ((Player) target).getActionRestrictionController().restrictActions(owner, ignoredActionRestrictionEffectTypes, type, time);

                    //if (owner.isPlayer())
                    //	((Player) owner).dispatchSystemMessage("RestrictAction[Type=" + type + "]=Yes", EChatType.Notice, EChatNoticeType.Normal);
                } else
                    isImmune = true;
            }

            /**
             Types:
             0  - Nothing, but gives resistance to other restriction types.
             1  - KnockBACK
             2  - KnockDOWN
             4  - Stun
             5  - Guard Crush
             6  - Rigid
             7  - Bound
             12 - AirFloat
             13 - AirSmash
             14 - DownSmash
             15 - Resistance Ignore Bound aka Released
             17 - Resistance Ignore Knockdown
             19 - Grass? lol 1&20 ignore
             20 - Rage 1 Stern 2 sec (1&3 ignore)
             22 - Freeze
             23 - Resistance ignore (1&3 ignore)
             */
            int resistOffset = !owner.isPlayer() && target.isPlayer() ? 300000 : 0;
            switch (type) {
                case 23: // Ignores knockback and 3
                case 21: // ?
                case 20: // Ignore Knockback and Stun for 2 seconds
                case 17: // Resistance Ignore Knockdown
                case 18: // ?
                case 16: // ?
                case 11: // ?
                case 10: // ?
                case 8: // ?
                case 9: // ?
                case 3: // ?
                case 0:
                    break;
                case 1: // KnockBack
                    if (!isImmune) {
                        int resist = target.getGameStats().getResistKnockBack().getIntMaxValue() + resistOffset;
                        if (isPvpTarget && resist > 600000)
                            resist = 600000;

                        if (target.canForceMove() && Rnd.getChance(1000000 - resist, 10000))
                            target.getAi().HandleKnockBack(owner, null);
                        else
                            target.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(owner, target));
                    }
                    break;
                case 2: // KnockDown
                    if (!isImmune) {
                        int resist = target.getGameStats().getResistKnockDown().getIntMaxValue() + resistOffset;
                        if (isPvpTarget && resist > 600000)
                            resist = 600000;

                        if (target.canForceMove() && Rnd.getChance(1000000 - resist, 10000)) {
                            target.getAi().HandleKnockDown(owner, null);
                        } else
                            target.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(owner, target));
                    }
                    break;
                case 4: // Stun
                    if (!isImmune) {
                        int resist = target.getGameStats().getResistStun().getIntMaxValue() + resistOffset;
                        if (isPvpTarget && resist > 600000 && target.canForceMove())
                            resist = 600000;

                        if (target.canForceMove() && Rnd.getChance(1000000 - resist, 10000))
                            target.getAi().HandleStun(owner, new Integer[]{time});
                        else
                            target.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(owner, target));
                    }
                    break;
                case 5: // Guard Crush
                {
                    int resist = target.getGameStats().getResistGuardCrush().getIntMaxValue() + resistOffset;
                    if (isPvpTarget && resist > 600000 && target.canForceMove())
                        resist = 600000;

                    if (target.getActionStorage().getActionChartActionT().getGuardType().isDefence()
                            && Rnd.getChance(1000000 - resist, 10000))
                        target.getAi().HandleGuardCrash(owner, null);
                }
                break;
                case 6: // Rigid
                    if (!isImmune) {
                        int resist = target.getGameStats().getResistRigid().getIntMaxValue() + resistOffset;
                        if (isPvpTarget && resist > 600000 && target.canForceMove())
                            resist = 600000;

                        if (target.canForceMove() && Rnd.getChance(1000000 - resist, 10000))
                            target.getAi().HandleRigid(owner, null);
                    }
                    break;
                case 7: // Bound
                    if (!isImmune) {
                        int resist = target.getGameStats().getResistKnockDown().getIntMaxValue() + resistOffset;
                        if (isPvpTarget && resist > 600000)
                            resist = 600000;

                        if (target.canForceMove() && Rnd.getChance(1000000 - resist, 10000))
                            target.getAi().HandleBound(owner, null);
                        else
                            target.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(owner, target));
                    }
                    break;
                case 12: // Air Float
                    if (!isImmune) {
                        int resist = target.getGameStats().getResistKnockBack().getIntMaxValue() + resistOffset;
                        if (isPvpTarget && resist > 600000)
                            resist = 600000;

                        //resist -= owner.getGameStats().getResistKnockBack().getIntValue();
                        if (target.canForceMove() && Rnd.getChance(1000000 - resist, 10000)) {
                            target.getAi().HandleAirFloat(owner, null);
                        } else
                            target.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(owner, target));
                    }
                    break;
                case 13: // Air Smash
                    if (!isImmune) {
                        int resist = target.getGameStats().getResistStandDown().getIntMaxValue() + resistOffset;
                        if (isPvpTarget && resist > 600000)
                            resist = 600000;

                        if (target.getAi().getBehavior().isKnockback() && Rnd.getChance(1000000 - resist, 10000))
                            target.getAi().HandleAirSmash(owner, null);
                        else
                            target.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(owner, target));
                    }
                    break;
                case 14: // Down Smash
                    if (!isImmune) {
                        int resist = target.getGameStats().getResistStandDown().getIntMaxValue() + resistOffset;
                        if (isPvpTarget && resist > 600000)
                            resist = 600000;
                        if (target.getAi().getBehavior().isKnockback() && Rnd.getChance(1000000 - resist, 10000))
                            target.getAi().HandleDownSmash(owner, null);
                        else
                            target.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(owner, target));
                    }
                    break;
                case 15: // Released
                    final Location loc = owner.getLocation();
                    if (World.getInstance().getWorldMap().updateLocation(target, loc.getX(), loc.getY()))
                        target.getLocation().setXYZ(loc.getX(), loc.getY(), loc.getZ());

                    if (owner.isPlayer())
                        owner.sendBroadcastItSelfPacket(new SMApplyActionRestriction(buffTemplate, owner, target));
                    target.getAi().HandleReleased(owner, null);
                    break;
                case 19: // Grass, Ignores the ignore knockback.
                    if (owner.isPlayer() && !isImmune) {
                        int resist = target.getGameStats().getResistStandDown().getIntMaxValue() + resistOffset;
                        if (isPvpTarget && resist > 600000)
                            resist = 600000;
                        if (Rnd.getChance(1000000 - resist, 10000))
                            target.getAi().HandleGroggy(owner, null);
                    }
                    break;
                case 22: // Freeze
                    if (owner.isPlayer() && !isImmune) {
                        int resist = target.getGameStats().getResistStun().getIntMaxValue() + resistOffset;
                        if (isPvpTarget && resist > 600000)
                            resist = 600000;

                        if (target.canForceMove() && Rnd.getChance(1000000 - resist, 10000))
                            target.getAi().HandleIceFreeze(owner, new Integer[]{time});
                        else
                            target.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(owner, target));
                    }
                    break;
                default:
                    break;
            }
        }
        return null;
    }
}