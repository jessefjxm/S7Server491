// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteItemInfo;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.Item;

public class SMServantEquipItemList extends WriteItemInfo {
    private final Servant servant;

    public SMServantEquipItemList(final Servant servant) {
        this.servant = servant;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.servant.getGameObjectId());
        for (int index = 0; index < this.servant.getEquipments().getExpandSize(); ++index) {
            final Item item = this.servant.getEquipments().getItem(index);
            this.writeItemInfo(buffer, item);
        }
    }
}
