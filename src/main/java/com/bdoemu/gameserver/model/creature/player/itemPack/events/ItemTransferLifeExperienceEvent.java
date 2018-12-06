// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMItemTransferLifeExperience;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.dataholders.LifeEXPData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.LifeExperienceInformation;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class ItemTransferLifeExperienceEvent extends AItemEvent {
    private ELifeExpType lifeExpType;
    private EItemStorageLocation storageLocation;
    private int slotIndex;
    private int currentLevel;
    private long objectId;
    private double currentExp;
    private BasicDBObject basicDBObject;

    public ItemTransferLifeExperienceEvent(final Player player, final EItemStorageLocation storageLocation, final int slotIndex, final long objectId, final ELifeExpType lifeExpType) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, 0);
        this.storageLocation = storageLocation;
        this.lifeExpType = lifeExpType;
        this.slotIndex = slotIndex;
        this.objectId = objectId;
    }

    private void addExp(final double exp) {
        this.currentExp += exp;
        Double maxExp;
        for (maxExp = LifeEXPData.getInstance().getMaxExp(this.lifeExpType, this.currentLevel); this.currentExp > maxExp && this.currentLevel + 1 <= 100; maxExp = LifeEXPData.getInstance().getMaxExp(this.lifeExpType, this.currentLevel)) {
            this.currentExp -= maxExp;
            ++this.currentLevel;
        }
        if (this.currentLevel + 1 > 100 && this.currentExp > maxExp) {
            this.currentExp = maxExp;
        }
    }

    @Override
    public void onEvent() {
        super.onEvent();
        final BasicDBObject lifeExpBasicDBObject = (BasicDBObject) ((BasicDBList) ((BasicDBObject) this.basicDBObject.get("lifeExperience")).get("lifeExperienceList")).get(this.lifeExpType.ordinal());
        this.currentExp = lifeExpBasicDBObject.getDouble("exp");
        this.currentLevel = lifeExpBasicDBObject.getInt("level");
        final LifeExperienceInformation lifeExperienceInformation = this.player.getLifeExperienceStorage().getLifeExperience(this.lifeExpType);
        int lifeExpLevel = lifeExperienceInformation.getLevel();
        this.addExp(lifeExperienceInformation.getExp());
        while (lifeExpLevel > 1) {
            --lifeExpLevel;
            final Double expToAdd = LifeEXPData.getInstance().getMaxExp(this.lifeExpType, lifeExpLevel);
            if (expToAdd != null) {
                this.addExp(expToAdd);
            }
        }
        lifeExperienceInformation.setValues(0L, 1);
        lifeExpBasicDBObject.put("exp", (Object) this.currentExp);
        lifeExpBasicDBObject.put("level", (Object) this.currentLevel);
        PlayersDBCollection.getInstance().updateLifeExperience(this.basicDBObject);
        this.player.sendPacket(new SMItemTransferLifeExperience(this.lifeExpType));
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isPlayerInventories()) {
            return false;
        }
        final Item item = this.playerBag.getItemPack(this.storageLocation).getItem(this.slotIndex);
        if (item == null) {
            return false;
        }
        final EContentsEventType contentsEventType = item.getTemplate().getContentsEventType();
        if (contentsEventType == null || !contentsEventType.isTransferLifeExperience() || ELifeExpType.values()[item.getTemplate().getContentsEventParam1()] != this.lifeExpType) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, this.storageLocation);
        this.basicDBObject = PlayersDBCollection.getInstance().getLifeExperience(this.objectId);
        return this.basicDBObject != null && this.basicDBObject.getLong("accountId") == this.player.getAccountId() && super.canAct();
    }
}
