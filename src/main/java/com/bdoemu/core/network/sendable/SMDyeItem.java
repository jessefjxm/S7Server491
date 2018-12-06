// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.templates.DyeingItemT;

public class SMDyeItem extends SendablePacket<GameClient> {
    private final Playable target;
    private final int itemDyePart;
    private Item item;
    private DyeingItemT dyeingItemT;

    public SMDyeItem(final Playable target, final Item item, final int itemDyePart, final DyeingItemT dyeingItemT) {
        this.target = target;
        this.item = item;
        this.itemDyePart = itemDyePart;
        this.dyeingItemT = dyeingItemT;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.target.getGameObjectId());
        buffer.writeC(this.item.getStorageLocation().ordinal());
        buffer.writeC(this.item.getSlotIndex());
        buffer.writeC(0);
        buffer.writeD(0);
        buffer.writeH(0);
        buffer.writeH(this.itemDyePart);
        buffer.writeC(this.dyeingItemT.getPaletteType());
        buffer.writeC(this.dyeingItemT.getPaletteIndex());
        buffer.writeD(this.dyeingItemT.getItemId());
        buffer.writeC(this.item.getColorPaletteType());
        buffer.writeC(0);
        buffer.writeD(this.target.getEquipSlotCacheCount());
    }
}
