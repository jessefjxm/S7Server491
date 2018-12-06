// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.sendable.SMListFixedHouseInstallations;
import com.bdoemu.core.network.sendable.SMTentInformation;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.houses.HouseStorage;
import com.bdoemu.gameserver.model.houses.enums.EFixedHouseType;
import com.bdoemu.gameserver.model.houses.services.FixedHouseService;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;
import com.bdoemu.gameserver.model.world.Location;

import java.util.Collections;

public class UseInstallationItemEvent extends AItemEvent {
    private EItemStorageLocation storageType;
    private int slotIndex;
    private Location loc;
    private int creatureId;
    private HouseHold houseHold;
    private HouseStorage houseStorage;
    private Integer characterKey;
    private Item item;
    private long objectId;
    private long parentObjId;
    private EFixedHouseType fixedHouseType;

    public UseInstallationItemEvent(final Player player, final long objectId, final EItemStorageLocation storageType, final int slotIndex, final Location loc, final int creatureId, final long parentObjId, final EFixedHouseType fixedHouseType) {
        super(player, player, player, EStringTable.eErrNoItemIsRemovedToItemInstallation, EStringTable.eErrNoItemIsRemovedToItemInstallation, 0);
        this.objectId = objectId;
        this.storageType = storageType;
        this.slotIndex = slotIndex;
        this.loc = loc;
        this.creatureId = creatureId;
        this.parentObjId = parentObjId;
        this.houseStorage = player.getHouseStorage();
        this.fixedHouseType = fixedHouseType;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        final HouseInstallation houseInstallation = HouseInstallation.newHouseInstallation(this.characterKey, this.item.getItemId(), this.item.getEndurance(), this.loc, this.parentObjId);
        this.houseHold.putInstallation(houseInstallation);
        this.houseHold.recalculatePoints();
        switch (this.fixedHouseType) {
            case Tent: {
                this.houseHold.refreshLifeTime();
                this.player.sendPacket(new SMTentInformation(Collections.singleton(this.houseHold)));
                break;
            }
            case House: {
                FixedHouseService.getInstance().updateToTop(this.houseHold);
                break;
            }
        }
        this.player.sendBroadcastItSelfPacket(new SMListFixedHouseInstallations(this.houseHold, Collections.singleton(houseInstallation), EPacketTaskType.Add));
    }

    @Override
    public boolean canAct() {
        final ItemPack removePack = this.playerBag.getItemPack(this.storageType);
        if (removePack == null) {
            return false;
        }
        this.item = removePack.getItem(this.slotIndex);
        if (this.item == null || !this.item.getItemType().isInstallation()) {
            return false;
        }
        this.characterKey = this.item.getTemplate().getCharacterKey();
        this.houseHold = this.player.getHouseholdController().getHouseHold(this.objectId);
        if (this.houseHold == null) {
            return false;
        }
        final Integer installationMaxCount = this.houseHold.getTemplate().getObjectTemplate().getInstallationMaxCount();
        if (installationMaxCount != null && this.houseHold.getInstalledMaxCount() >= installationMaxCount) {
            return false;
        }
        if (this.parentObjId > 0L && this.houseHold.getHouseInstallation(this.parentObjId) == null) {
            return false;
        }
        this.decreaseItem(this.slotIndex, 1L, removePack.getLocationType());
        return super.canAct();
    }
}
