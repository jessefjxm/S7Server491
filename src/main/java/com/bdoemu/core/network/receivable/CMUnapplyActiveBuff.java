package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;

public class CMUnapplyActiveBuff extends ReceivablePacket<GameClient> {
    private int _buffIndex;

	public CMUnapplyActiveBuff(final short opcode) {
		super(opcode);
	}

	protected void read() {
        _buffIndex = readHD();
	}

	public void runImpl() {
		if (getClient() != null && getClient().getPlayer() != null) {
		    // TODO
            // https://puu.sh/wReQx.png
            // end-time = -1 if toggle
        }
	}
}