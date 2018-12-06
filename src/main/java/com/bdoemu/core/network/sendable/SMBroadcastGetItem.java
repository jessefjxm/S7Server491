package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.enums.EItemGetType;

public class SMBroadcastGetItem extends SendablePacket<GameClient> {
    private final EItemGetType type;
    private final String name;
    private final int itemId;
    private final int enchantLevel;
    private final int param1;

    public SMBroadcastGetItem(final EItemGetType type, final String name, final int itemId, final int enchantLevel, final int param1) {
        this.type = type;
        this.name = name;
        this.itemId = itemId;
        this.enchantLevel = enchantLevel;
        this.param1 = param1;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.type.ordinal());
        buffer.writeS((CharSequence) this.name, 62);
        switch (this.type) {
            case DROP: {
                buffer.writeH(this.itemId);
                buffer.writeH(this.enchantLevel);
                buffer.writeD(0);
                buffer.writeH(this.param1);
                break;
            }
            case OPEN_BOX: {
                buffer.writeH(this.itemId);
                buffer.writeH(this.enchantLevel);
                buffer.writeD(0);
                buffer.writeH(this.param1);
                break;
            }
            case ENCHANT_FAIL: {
                buffer.writeH(this.itemId);
                buffer.writeH(this.enchantLevel);
                buffer.writeD(0);
                buffer.writeH(this.param1);
                break;
            }
            case ENCHANT: {
                buffer.writeH(this.itemId);
                buffer.writeH(this.enchantLevel);
                buffer.writeD(0);
                buffer.writeH(this.param1);
                break;
            }
            case HORSE: {
                buffer.writeH(this.itemId);
                buffer.writeH(0);
                buffer.writeD(0);
                buffer.writeH(0);
                break;
            }
        }
        buffer.writeH(0);
        buffer.writeD(0);
        buffer.writeQ(0L);
        buffer.writeC(0);
    }
}
