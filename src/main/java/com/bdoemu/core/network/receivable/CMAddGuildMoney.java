package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;

public class CMAddGuildMoney extends ReceivablePacket<GameClient> {
	public CMAddGuildMoney(final short opcode) {
		super(opcode);
	}

	protected void read() {
		//
	}

	public void runImpl() {
		//
	}

}