package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.service.GameTimeService;

public class SMStartAction extends SendablePacket<GameClient> {
    private final IAction action;

    public SMStartAction(final IAction action) {
        this.action = action;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.action.getOwnerGameObj());
        buffer.writeD(this.action.getActionHash());
        buffer.writeC(this.action.isUsedInMove());
        buffer.writeC(this.action.isUsedFromQuickSlot());
        buffer.writeF(this.action.getBlendTime());
        buffer.writeD(this.action.getCarrierGameObjId());
        buffer.writeF(this.action.getCarrierX());
        buffer.writeF(this.action.getCarrierZ());
        buffer.writeF(this.action.getCarrierY());
        buffer.writeF(this.action.getNewX());
        buffer.writeF(this.action.getNewZ());
        buffer.writeF(this.action.getNewY());
        buffer.writeF(0);
        buffer.writeF(this.action.getCos());
        buffer.writeD(0);
        buffer.writeF(this.action.getSin());
        buffer.writeC(this.action.getActionChartActionT().getApplySpeedBuffType().ordinal());
        buffer.writeD(this.action.getSpeedRate());
        buffer.writeD(this.action.getSlowSpeedRate());
        buffer.writeF(this.action.getOwner().getGameStats().getJumpHeight().getMaxValue());
        buffer.writeQ(GameTimeService.getServerTimeInMillis());
        buffer.writeH(this.action.getSkillLevel());
        buffer.writeH(this.action.getSkillId());
        buffer.writeF(this.action.getOldX());
        buffer.writeF(this.action.getOldZ());
        buffer.writeF(this.action.getOldY());
        buffer.writeD(this.action.getTargetGameObj());
        buffer.writeD(this.action.getMessage().getHash());
        buffer.writeC((int) this.action.getType());
        buffer.writeC(this.action.isCallAction());
        buffer.writeC(this.action.getRidingSlotType().ordinal());
        buffer.writeC(this.action.getUnk2());
        buffer.writeC(this.action.getUnk3());
        buffer.writeC(this.action.getUnk4());
        buffer.writeF(this.action.getTargetX());
        buffer.writeF(this.action.getTargetZ());
        buffer.writeF(this.action.getTargetY());
        buffer.writeB(new byte[18]);
    }
}
