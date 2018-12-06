// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.commons.utils.HexUtils;
import com.bdoemu.core.network.GameClient;

public class SMAiKillNotifier extends SendablePacket<GameClient> {
    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(149126297);
        buffer.writeS((CharSequence) "RedWinner", 62);
        buffer.writeS((CharSequence) "BlueWinner", 62);
        buffer.writeS((CharSequence) "GreenWinner", 62);
        buffer.writeB(HexUtils.hex2Byte("000008010000D0F272FB0A01000000000000000000000000000000000000A02A2F0C08010000F0DB43EEF10000000000000000000000FEFFFFFFFFFFFFFF000072FB0A0100000000000000000000FAA14B2DF77F000000000000000000008E00000000000000F890F183F100000001000000000000000000000000000101010000"));
    }
}
