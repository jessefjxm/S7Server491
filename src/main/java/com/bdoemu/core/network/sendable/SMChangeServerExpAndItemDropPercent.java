package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.configs.RateConfig;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.network.GameClient;

public class SMChangeServerExpAndItemDropPercent extends SendablePacket<GameClient> {
    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(ServerConfig.SERVER_ID);
        buffer.writeH(ServerConfig.SERVER_CHANNEL_ID);
        /* 0 */
        buffer.writeD(0);
		/* 1 */
        buffer.writeD(RateConfig.RATE_EXP * 10000);
		/* 2 */
        buffer.writeD(RateConfig.MONSTER_DROP_RATE * 10000);
		/* 3 */
        buffer.writeD(0);
		/* 4 */
        buffer.writeD(100 * 10000);
		/* 5 */
        buffer.writeD(0);
		/* 6 */
        buffer.writeD(0);
		/* 7 */
        buffer.writeD(0);
		/* 8 */
        buffer.writeD(0);
		/* 9 */
        buffer.writeD(0);
		/*10 */
        buffer.writeD(0);
		/*11 */
        buffer.writeD(0);
		/*12 */
        buffer.writeD(0);
		/*13 */
        buffer.writeD(0);
		/*14 */
        buffer.writeD(0);
		/*15 */
        buffer.writeD(0);
		/*16 */
        buffer.writeD(0);
		/*17 */
        buffer.writeD(0);
		/*18 */
        buffer.writeD(0);
		/*19 */
        buffer.writeD(0);
		/*20 */
        buffer.writeD(0);
        buffer.writeS("Welcome to OgreFest!", 62);
    }
}
