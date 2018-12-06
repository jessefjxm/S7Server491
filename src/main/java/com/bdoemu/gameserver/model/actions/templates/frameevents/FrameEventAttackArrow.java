package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.core.network.sendable.SMDoPhysicalAttack;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.ai.deprecated.CreatureAI;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.agrolist.AttackResult;
import com.bdoemu.gameserver.model.creature.enums.EMainAttackType;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class FrameEventAttackArrow extends FrameEvent {
    private int attackerDelay;
    private int attackeeDelay;
    private int attackableCount;
    private int attackableCountForNonPlayer;

    public FrameEventAttackArrow(final EFrameEventType frameEventType) {
        super(frameEventType);
    }

    @Override
    public boolean doFrame(final IAction action, final Creature target) {
        final Creature owner = action.getOwner();
        return target != null && this.useAttack(owner, target);
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        final Creature owner = action.getOwner();
        final Iterator<Creature> iterator = targets.iterator();
        if (iterator.hasNext()) {
            final Creature target = iterator.next();
            return this.useAttack(owner, target);
        }
        final Iterator<Creature> iterator2 = playerTargets.iterator();
        if (iterator2.hasNext()) {
            final Creature target = iterator2.next();
            return this.useAttack(owner, target);
        }
        return true;
    }

    @Override
    public int getDelay() {
        return (this.attackeeDelay > 0 && this.attackerDelay > 0 && this.attackeeDelay == this.attackerDelay) ? this.attackeeDelay : 0;
    }

    private boolean useAttack(final Creature owner, final Creature target) {
        final CreatureAI ai = target.getAi();
        if (ai == null || ai.getBehavior().isReturn()) {
            return true;
        }
        if (!target.getActionStorage().getActionChartActionT().getGuardType().isAvoid()) {
            if (owner.isEnemy(target)) {
                final AttackResult attackResult = new AttackResult(null, owner, target);
                attackResult.applyAttack(0, 0, EMainAttackType.RDD);
                owner.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(Collections.singleton(attackResult)));
            }
        }
        return true;
    }

    @Override
    public void read(final FileBinaryReader reader) {
        super.read(reader);
        this.attackerDelay = reader.readHD();
        this.attackeeDelay = reader.readHD();
        this.attackableCount = reader.readCD();
        this.attackableCountForNonPlayer = reader.readCD();
    }
}
