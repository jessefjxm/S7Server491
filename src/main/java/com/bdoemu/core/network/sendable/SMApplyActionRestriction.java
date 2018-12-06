package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.buffs.templates.BuffTemplate;
import com.bdoemu.gameserver.service.GameTimeService;

public class SMApplyActionRestriction extends SendablePacket<GameClient> {
    private BuffTemplate _template;
    private Creature _owner;
    private Creature _target;

    public SMApplyActionRestriction(BuffTemplate template, Creature owner, Creature target) {
        _template = template;
        _owner = owner;
        _target = target;
    }


    protected void writeBody(final SendByteBuffer buffer) {
        Integer[] params = _template.getParams();
        buffer.writeD(_target.getGameObjectId());
        buffer.writeH(params[0]);    // Action ID [1 ~ 7]
        buffer.writeC(params[3]);    // 1
        buffer.writeH(params[4]);    // 3
        buffer.writeC(params[2]);    // 0
        if (_owner.getAi() != null)
            buffer.writeF((float) _owner.getAi().getDistanceToWrapper(_target));
        else
            buffer.writeF(497.25f);
        buffer.writeC(_target.getActionStorage().getAction().getRidingSlotType().ordinal());
        buffer.writeD(3);            // [1 ~ 3]
        buffer.writeQ(params[1]);    // 1500
        buffer.writeQ(GameTimeService.getServerTimeInMillis());
    }
}