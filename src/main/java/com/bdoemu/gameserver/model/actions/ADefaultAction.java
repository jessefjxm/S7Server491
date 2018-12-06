package com.bdoemu.gameserver.model.actions;

import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.enums.ERidingSlotType;
import com.bdoemu.gameserver.model.knowlist.KnowList;

import java.nio.ByteBuffer;

public abstract class ADefaultAction extends AAction {
    public ADefaultAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void read(final ByteBuffer buff, final Player owner) {
        super.read(buff, owner);
        this.readC(buff);
        this.readCD(buff);
        this.oldX = this.readF(buff);
        this.oldZ = this.readF(buff);
        this.oldY = this.readF(buff);
        this.targetGameObjId = this.readD(buff);
        this.readC(buff);
        this.ridingSlotType = ERidingSlotType.values()[this.readC(buff)];
        this.unk2 = this.readC(buff);
        this.unk3 = this.readC(buff);
        this.unk4 = this.readC(buff);
        this.skipAll(buff);
        if (this.targetGameObjId > 0) {
            final Creature target = KnowList.getObject(owner, this.targetGameObjId);
            if (target != null && owner.isEnemy(target)) {
                owner.getAggroList().setTarget(target);
            }
        }
    }

    @Override
    public boolean canAct() {
        return this.subType == 0;
    }
}
