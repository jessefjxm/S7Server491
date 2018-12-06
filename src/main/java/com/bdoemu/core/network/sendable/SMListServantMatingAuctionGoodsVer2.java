// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.auction.ServantItemMarket;
import com.bdoemu.gameserver.model.stats.containers.ServantGameStats;

import java.util.Collection;

public class SMListServantMatingAuctionGoodsVer2 extends SendablePacket<GameClient> {
    private Collection<ServantItemMarket> servants;
    private int auctionKey;

    public SMListServantMatingAuctionGoodsVer2(final Collection<ServantItemMarket> servants, final int auctionKey) {
        this.servants = servants;
        this.auctionKey = auctionKey;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.auctionKey);
        buffer.writeH(this.servants.size());
        for (final ServantItemMarket servant : this.servants) {
            buffer.writeQ(servant.getObjectId());
            buffer.writeC(servant.isSelfOnly());
            buffer.writeQ(servant.getPrice());
            buffer.writeQ(22244722669L);
            buffer.writeQ(servant.getServant().getObjectId());
            buffer.writeD(servant.getServant().getLevel());
            buffer.writeH(servant.getServant().getCreatureId());
            buffer.writeC(servant.getServant().getMatingCount());
            buffer.writeD(servant.getServant().getDeathCount());
            buffer.writeC(servant.getServant().isClearedMatingCount());
            buffer.writeC(servant.getServant().isClearedDeathCount());
            buffer.writeD(993984512);
            buffer.writeD(0);
            buffer.writeC(0);
            buffer.writeC(0);
            buffer.writeC(servant.isSold());
            for (int skillId = 0; skillId < 55; ++skillId) {
                buffer.writeC(servant.getServant().hasServantSkill(skillId));
            }
            for (int skillId = 0; skillId < 55; ++skillId) {
                buffer.writeC(servant.getServant().isCannotChange(skillId));
            }
            for (int skillId = 0; skillId < 55; ++skillId) {
                buffer.writeD(servant.getServant().getSkillExp(skillId));
            }
            final ServantGameStats gameStats = servant.getServant().getGameStats();
            buffer.writeF(gameStats.getHp().getFloatBonus());
            buffer.writeD(gameStats.getMp().getIntBonus());
            buffer.writeD(gameStats.getAccelerationRate().getIntBonus());
            buffer.writeD(gameStats.getMaxMoveSpeedRate().getIntBonus());
            buffer.writeD(gameStats.getCorneringSpeedRate().getIntBonus());
            buffer.writeD(gameStats.getBrakeSpeedRate().getIntBonus());
        }
    }
}
