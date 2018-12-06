package com.bdoemu.gameserver.model.actions.actioncharts;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.network.sendable.SMDoPhysicalAttack;
import com.bdoemu.gameserver.model.actions.ADefaultAction;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.agrolist.AttackResult;
import com.bdoemu.gameserver.model.creature.enums.EMainAttackType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.knowlist.KnowList;

import java.util.Collections;

public class CatchAction extends ADefaultAction {
    public CatchAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void init() {
        final Creature target = KnowList.getObject(this.owner, this.getTargetGameObj());
        if (target != null && !target.isPlayer()) { // TODO: Fix grabs lol
            if (owner.isEnemy(target)) {
                final boolean isPvpTarget = this.owner.isPlayer() && target.isPlayer();
                int resist = target.getGameStats().getResistCapture().getIntMaxValue();
                if (isPvpTarget && resist > 600000) {
                    resist = 600000;
                }
                //resist -= this.owner.getGameStats().getResistCapture().getIntValue();

                if (target.canForceMove() && Rnd.get(1000000) > resist) {
                    final AttackResult attackResult = new AttackResult(null, this.owner, target);
                    attackResult.applyAttack(0, 0, EMainAttackType.DDD);
                    this.owner.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(Collections.singleton(attackResult)));
                    target.getAi().HandleCapture(this.owner, null);
                } else {
                    this.targetGameObjId = -1024;
                    target.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(this.owner, target));
                }
            }
            super.init();
        } else {
            this.targetGameObjId = -1024;

            if (target != null)
                target.sendBroadcastItSelfPacket(new SMDoPhysicalAttack(this.owner, target));
        }
    }
}
