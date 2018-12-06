// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;

public class SMSetServantLevels extends SendablePacket<GameClient> {
    private final Servant servant;
    private final long neededForNextLevelExp;
    private final boolean showLvlUpAnimation;

    public SMSetServantLevels(final Servant servant) {
        this(servant, servant.getServantTemplate().getRequireExp(), false);
    }

    public SMSetServantLevels(final Servant servant, final long neededForNextLevelExp, final boolean showLvlUpAnimation) {
        this.servant = servant;
        this.neededForNextLevelExp = neededForNextLevelExp;
        this.showLvlUpAnimation = showLvlUpAnimation;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeQ(this.servant.getObjectId());
        buffer.writeD(this.servant.getLevel());
        buffer.writeQ(this.servant.getExp());
        buffer.writeQ(this.neededForNextLevelExp);
        buffer.writeC((int) (this.showLvlUpAnimation ? 1 : 0));
    }
}
