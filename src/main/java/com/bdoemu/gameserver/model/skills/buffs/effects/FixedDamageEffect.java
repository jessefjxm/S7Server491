package com.bdoemu.gameserver.model.skills.buffs.effects;

import com.bdoemu.core.network.sendable.SMDoPhysicalAttack;
import com.bdoemu.gameserver.model.ai.deprecated.CreatureAI;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.agrolist.AttackResult;
import com.bdoemu.gameserver.model.creature.enums.EMainAttackType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.skills.buffs.ABuffEffect;
import com.bdoemu.gameserver.model.skills.buffs.ActiveBuff;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.model.skills.templates.SkillT;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FixedDamageEffect extends ABuffEffect {
    @Override
    public Collection<ActiveBuff> initEffect(final Creature owner, final Collection<? extends Creature> targets, final SkillT skillT, final BuffTemplate buffTemplate) {
        final Integer[] params = buffTemplate.getParams();
        final EMainAttackType mainAttackType = EMainAttackType.values()[params[0]];
        final int dmgPercentage = params[3];
        int criticalPercentage = 0;
        if (params[4] != null) {
            criticalPercentage = params[4];
        }
        final ConcurrentLinkedQueue<AttackResult> results = new ConcurrentLinkedQueue<AttackResult>();
        for (final Creature target : targets) {
            final CreatureAI ai = target.getAi();
            if (ai != null) {
                if (ai.getBehavior().isReturn())
                    continue;
                if (target.getActionStorage().getActionChartActionT().getGuardType().isAvoid())
                    continue;

                boolean canAttack = true;
                if (target.isPlayer()) {
                    final Player playerTarget = (Player) target;
                    if (playerTarget != null && playerTarget.getTendency() > 0)
                        canAttack = false;
                }

                if (target.isVehicle())
                    canAttack = false;

                if (target.getRegion().getTemplate().isSafe() && canAttack                                                            // Safe Zone AND PK
                        || !target.getRegion().getTemplate().isSafe() || target.getRegion().getTemplate().getRegionType().isArena()) {    // Not Safe Zone or Arena
                    final AttackResult attackResult = new AttackResult(skillT, owner, target);
                    attackResult.applyAttack(dmgPercentage, criticalPercentage, mainAttackType);
                    results.add(attackResult);
                }
            }
        }
        if (!results.isEmpty()) {
            owner.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(results));
        }
        return null;
    }
}
