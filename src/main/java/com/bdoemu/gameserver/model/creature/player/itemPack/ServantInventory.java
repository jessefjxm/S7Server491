package com.bdoemu.gameserver.model.creature.player.itemPack;

import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.stats.Stat;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.stats.elements.ExpandElement;
import com.mongodb.BasicDBObject;

public class ServantInventory extends AbstractAddItemPack {
    public ServantInventory(final Servant servant) {
        super(EItemStorageLocation.ServantInventory, new Stat(servant, new BaseElement(servant.getTemplate().getInventoryMax())), servant);
    }

    public ServantInventory(final BasicDBObject dbObject, final Servant servant) {
        super(EItemStorageLocation.ServantInventory, dbObject, servant);
    }

    @Override
    public int getMaxExpandSize() {
        return this.getOwner().getTemplate().getInventoryMax();
    }

    public void expandBase(final int expandCount) {
        this.expandStat.increaseElement(this.expandStat.getBase(), (this.getBaseExpandSize() + expandCount > this.getMaxExpandSize()) ? ((float) (this.getMaxExpandSize() - this.getBaseExpandSize())) : ((float) expandCount));
    }

    public void addExpandElement(final ExpandElement element) {
        this.expandStat.addElement(element);
    }
}
