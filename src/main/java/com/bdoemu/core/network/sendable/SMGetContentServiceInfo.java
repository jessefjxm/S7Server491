// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.LocalizingOptionConfig;
import com.bdoemu.core.configs.NetworkConfig;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.misc.enums.EContentsServiceType;

public class SMGetContentServiceInfo extends SendablePacket<GameClient> {
    public SMGetContentServiceInfo() {
        if (NetworkConfig.ENCRYPT_PACKETS) {
            this.setEncrypt(true);
        }
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeC(EContentsServiceType.Commercial.ordinal());
        buffer.writeC(LocalizingOptionConfig.DEFAULT_CHARACTER_SLOT);
        buffer.writeC(LocalizingOptionConfig.CHARACTER_SLOT_LIMIT);
        buffer.writeC(0);
        buffer.writeC(0);
        buffer.writeQ(3000000L);
        buffer.writeD(300000);
        buffer.writeD(0);
        buffer.writeD(LocalizingOptionConfig.ITEM_MARKET_REFUND_PERCENT_FOR_PREMIUM_PACKAGE);
        buffer.writeD(LocalizingOptionConfig.ITEM_MARKET_REFUND_PERCENT_FOR_PCROOM_AND_PREMIUM_PACKAGE);
        buffer.writeQ((long) LocalizingOptionConfig.BIDDING_TIME);
        LocalizingOptionConfig.POSSIBLE_CLASS.forEach(buffer::writeC);
        for (int i = 0; i < 32 - LocalizingOptionConfig.POSSIBLE_CLASS.size(); ++i) {
            buffer.writeC(32);
        }
        buffer.writeD(0);
        buffer.writeD(LocalizingOptionConfig.CHARACTER_REMOVE_TIME_CHECK_LEVEL);
        buffer.writeQ((long) LocalizingOptionConfig.LOW_LEVEL_CHARACTER_REMOVE_TIME);
        buffer.writeQ((long) LocalizingOptionConfig.CHARACTER_REMOVE_TIME);
        buffer.writeQ((long) LocalizingOptionConfig.NAME_REMOVE_TIME);
        buffer.writeC(LocalizingOptionConfig.GUILD_WAR_TYPE.ordinal());
        buffer.writeC(LocalizingOptionConfig.CAN_MAKE_GUILD);
        buffer.writeC(LocalizingOptionConfig.CAN_REGISTER_PEARL_ITEM_ON_MARKET);
        buffer.writeH(11);
        buffer.writeC(0);
        buffer.writeD(150);
        buffer.writeC(LocalizingOptionConfig.OPENED_DESERT_PK);
        buffer.writeH(LocalizingOptionConfig.ACCESSIBLE_REGION_GROUP_KEY.size());
        for (final int regionGroupKey : LocalizingOptionConfig.ACCESSIBLE_REGION_GROUP_KEY) {
            buffer.writeC(EContentsServiceType.Commercial.ordinal());
            buffer.writeH(regionGroupKey);
        }
    }
}
