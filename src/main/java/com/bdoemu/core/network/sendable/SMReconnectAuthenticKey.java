package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;

public class SMReconnectAuthenticKey extends SendablePacket<GameClient> {
	protected void writeBody(final SendByteBuffer buffer) {
		//
	}
}