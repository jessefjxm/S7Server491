// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable.utils;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.Jewel;
import com.bdoemu.gameserver.model.items.templates.DyeingItemT;

public abstract class WriteItemInfo extends SendablePacket<GameClient> {
    protected void writeEquipData(final SendByteBuffer buffer, final Item item) {
        this.writeItemHeader(buffer, item);
        this.writeExpirationPeriod(buffer, item);
        this.writeCurrentEndurance(buffer, item);
        this.writeColorData(buffer, item);
    }

    protected void writeItemInfo(final SendByteBuffer buffer, final Item item) {
        this.writeItemHeader(buffer, item);
        this.writeItemData(buffer, item);
        buffer.writeQ((item != null) ? item.getObjectId() : 0L);
        this.writeColorData(buffer, item);
        buffer.writeD((item != null) ? item.getAlchemyStoneExp() : 0);
        this.writeJewelData(buffer, item);
    }

    protected void writeItemHeader(final SendByteBuffer buffer, final Item item) {
        if (item != null) {
            buffer.writeH(item.getItemId());
            buffer.writeH(item.getEnchantLevel());
        } else {
            buffer.writeH(0);
            buffer.writeH(0);
        }
    }

    protected void writeColorData(final SendByteBuffer buffer, final Item item) {
        if (item != null) {
            for (int key = 0; key < 12; ++key) {
                final DyeingItemT palette = item.getColorPaletteByIndex(key);
                if (palette != null) {
                    buffer.writeC(palette.getPaletteType());
                    buffer.writeC(palette.getPaletteIndex());
                } else {
                    buffer.writeH(-1);
                }
            }
            buffer.writeC(item.getColorPaletteType());
        } else {
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeH(-1);
            buffer.writeC(0);
        }
    }

    protected void writeJewelData(final SendByteBuffer buffer, final Item item) {
        if (item != null) {
            for (int key = 0; key < 6; ++key) {
                final Jewel jewel = item.getJewelsByIndex(key);
                if (jewel != null) {
                    buffer.writeD(jewel.getJewelId());
                } else {
                    buffer.writeD(0);
                }
            }
        } else {
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
            buffer.writeD(0);
        }
    }

    protected void writeItemData(final SendByteBuffer buffer, final Item item) {
        if (item != null) {
            buffer.writeQ(item.getCount());
            buffer.writeQ(item.getExpirationPeriod());
            buffer.writeC(item.isVested());
            buffer.writeQ(item.getItemPrice());
            buffer.writeH(item.getRegionId());
            if (item.getTemplate().getEndurance() > 0) {
                buffer.writeH(item.getEndurance());
                buffer.writeH(item.getMaxEndurance());
            } else {
                buffer.writeH(32767);
                buffer.writeH(32767);
            }
        } else {
            buffer.writeQ(0L);
            buffer.writeQ(0L);
            buffer.writeC(0);
            buffer.writeQ(0L);
            buffer.writeH(0);
            buffer.writeH(0);
            buffer.writeH(0);
        }
    }

    protected void writeExpirationPeriod(final SendByteBuffer buffer, final Item item) {
        buffer.writeQ((item != null) ? item.getExpirationPeriod() : -2L);
    }

    protected void writeCurrentEndurance(final SendByteBuffer buffer, final Item item) {
        buffer.writeH((item != null) ? ((item.getTemplate().getEndurance() > 0) ? item.getEndurance() : 32767) : 0);
    }
}
