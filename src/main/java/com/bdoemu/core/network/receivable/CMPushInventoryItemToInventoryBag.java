package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;

public class CMPushInventoryItemToInventoryBag extends ReceivablePacket<GameClient> {
	public CMPushInventoryItemToInventoryBag(final short opcode) {
		super(opcode);
	}

	protected void read() {
		//
	}

	public void runImpl() {
		//
	}

}