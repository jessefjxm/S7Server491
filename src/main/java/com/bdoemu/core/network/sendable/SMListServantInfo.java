// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.stats.containers.ServantGameStats;

import java.util.Collection;
import java.util.Collections;

public class SMListServantInfo extends SendablePacket<GameClient> {
    public static final int MAXIMUM = 4;
    private final boolean isHeader;
    private final Collection<Servant> servants;
    private EPacketTaskType packetTaskType;

    public SMListServantInfo(final Collection<Servant> servants, final EPacketTaskType packetTaskType, final boolean isHeader) {
        this.packetTaskType = packetTaskType;
        this.isHeader = isHeader;
        this.servants = servants;
    }

    public SMListServantInfo(final Servant servant, final EPacketTaskType packetTaskType) {
        this(Collections.singletonList(servant), packetTaskType, false);
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(this.packetTaskType.ordinal());
        buffer.writeC(this.isHeader);
        buffer.writeH(this.servants.size());
        for (final Servant servant : this.servants) {
            buffer.writeH(servant.getRegionId());
            buffer.writeQ(servant.getObjectId());
            buffer.writeC(0);
            buffer.writeD((servant.getServantState().isField() || servant.getServantState().isMating()) ? servant.getGameObjectId() : -1024);
            buffer.writeH(servant.getCreatureId());
            buffer.writeC(servant.getServantState().ordinal());
            buffer.writeQ(-1L);
            buffer.writeS((CharSequence) servant.getName(), 62);
            buffer.writeC(servant.getMatingCount());
            buffer.writeD(servant.getDeathCount());
            buffer.writeC(servant.isClearedMatingCount());
            buffer.writeC(servant.isClearedDeathCount());
            buffer.writeD(servant.getHope());
            buffer.writeH(servant.getFormIndex());
            buffer.writeQ(servant.getCarriageObjectId());
            buffer.writeQ(servant.getMatingTime());
            buffer.writeQ(servant.getLastInteractedDate());
            buffer.writeC(servant.isSeized());
            buffer.writeC(servant.isImprint());
            buffer.writeD(servant.getLevel());
            buffer.writeQ(servant.getExp());
            buffer.writeF(servant.getGameStats().getHp().getCurrentHp());
            buffer.writeD(servant.getGameStats().getMp().getCurrentMp());
            for (int i = 0; i < 31; ++i) {
                buffer.writeH(0);
                buffer.writeH(0);
                buffer.writeQ(0L);
                buffer.writeQ(0L);
                buffer.writeC(0);
                buffer.writeQ(0L);
                buffer.writeH(0);
                buffer.writeH(0);
                buffer.writeH(0);
                buffer.writeQ(0L);
                for (int d = 0; d < 12; ++d) {
                    buffer.writeC(0);
                    buffer.writeC(0);
                }
                buffer.writeD(0);
                buffer.writeC(0);
                for (int j = 0; j < 6; ++j) {
                    buffer.writeD(0);
                }
            }
            for (int skillId = 0; skillId < 55; ++skillId) {
                buffer.writeC(servant.hasServantSkill(skillId));
            }
            for (int skillId = 0; skillId < 55; ++skillId) {
                buffer.writeD(servant.getSkillExp(skillId));
            }
            for (int skillId = 0; skillId < 55; ++skillId) {
                buffer.writeC(servant.isCannotChange(skillId));
            }
            final ServantGameStats gameStats = servant.getGameStats();
            buffer.writeF(gameStats.getHp().getFloatBonus());
            buffer.writeD(gameStats.getMp().getIntBonus());
            buffer.writeD(gameStats.getAccelerationRate().getIntBonus());
            buffer.writeD(gameStats.getMaxMoveSpeedRate().getIntBonus());
            buffer.writeD(gameStats.getCorneringSpeedRate().getIntBonus());
            buffer.writeD(gameStats.getBrakeSpeedRate().getIntBonus());
            buffer.writeQ(servant.getSkillTrainingTime());
            buffer.writeH((int) ServerConfig.SERVER_CHANNEL_ID);
            buffer.writeD(-1);
            buffer.writeD(-1);
            buffer.writeD(-1);
            buffer.writeD(-1);
            buffer.writeD(-1);
            buffer.writeD(-1);
            buffer.writeD(-1);
            buffer.writeD(-1);
        }
    }
}
