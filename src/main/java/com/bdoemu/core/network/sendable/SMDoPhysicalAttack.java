// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.agrolist.AttackResult;
import com.bdoemu.gameserver.model.creature.agrolist.enums.EAttackType;
import com.bdoemu.gameserver.model.creature.agrolist.enums.EDmgType;

import java.util.Collection;

public class SMDoPhysicalAttack extends SendablePacket<GameClient> {
    private Collection<AttackResult> attacks;
    private boolean isImmuned;
    private Creature owner;
    private Creature target;

    public SMDoPhysicalAttack(final Collection<AttackResult> attacks) {
        this.isImmuned = false;
        this.attacks = attacks;
    }

    public SMDoPhysicalAttack(final Creature owner, final Creature target) {
        this.isImmuned = false;
        this.owner = owner;
        this.target = target;
        this.isImmuned = true;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        if (this.isImmuned) {
            buffer.writeH(1);
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeD(this.owner.getGameObjectId());
            buffer.writeD(this.target.getGameObjectId());
            buffer.writeF(0);
            buffer.writeF(0);
            buffer.writeF(0);
            buffer.writeF(0);
            buffer.writeF(0.5f);
            buffer.writeF(0);
            buffer.writeF(1);
            buffer.writeF(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeF(0);
            buffer.writeH(0);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeC(0);
            buffer.writeC(0);
            buffer.writeC((int) EAttackType.FrontAttack.getId());
            buffer.writeC(EDmgType.Immuned.getId());
            buffer.writeC(0);
        } else {
            buffer.writeH(this.attacks.size());
            for (final AttackResult attackResult : this.attacks) {
                //System.out.println("Sending out DMG: " + attackResult.getDmg());
                final Creature target = attackResult.getTarget();
                buffer.writeQ(target.getGameStats().getHp().getHealCacheCount());
                buffer.writeQ(target.getGameStats().getMp().getMpCacheCount());
                buffer.writeD(attackResult.getAttacker().getGameObjectId());
                buffer.writeD(attackResult.getTarget().getGameObjectId());
                buffer.writeF(attackResult.getTargetLoc().getX());
                buffer.writeF(attackResult.getTargetLoc().getZ());
                buffer.writeF(attackResult.getTargetLoc().getY());
                buffer.writeF(0);
                buffer.writeF(0.5f);
                buffer.writeF(0);
                buffer.writeF(1);
                buffer.writeF(target.getGameStats().getHp().getCurrentHp());
                buffer.writeD(target.getGameStats().getMp().getCurrentMp());
                buffer.writeD(0);
                buffer.writeD(attackResult.getAttacker().getActionStorage().getActionHash());
                buffer.writeF(attackResult.getDmg());
                buffer.writeH(0);
                buffer.writeH(-1);
                buffer.writeH(0);
                buffer.writeH(275);
                buffer.writeC(0);
                buffer.writeC(0);
                buffer.writeC((int) attackResult.getAttackType().getId());
                buffer.writeC(attackResult.getDmgType().getId());
                buffer.writeC(3);
            }
        }
    }
}
