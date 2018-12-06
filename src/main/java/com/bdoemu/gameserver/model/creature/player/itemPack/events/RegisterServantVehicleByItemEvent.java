// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.network.receivable.CMRegisterServantVehicleByItem;
import com.bdoemu.core.network.sendable.SMListServantInfo;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.dataholders.ServantData;
import com.bdoemu.gameserver.dataholders.ServantSetData;
import com.bdoemu.gameserver.model.creature.observers.enums.EObserveType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.model.ServantTemplate;
import com.bdoemu.gameserver.model.creature.servant.templates.ServantSetTemplate;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.EPacketTaskType;

public class RegisterServantVehicleByItemEvent extends AItemEvent {
    private String servantName;
    private int slotIndex;
    private EItemStorageLocation storageType;
    private Servant servant;
    private int itemId;

    public RegisterServantVehicleByItemEvent(final Player player, final String servantName, final int slotIndex, final EItemStorageLocation storageType, final int regionId) {
        super(player, player, player, EStringTable.NONE, EStringTable.NONE, regionId);
        this.servantName = servantName;
        this.slotIndex = slotIndex;
        this.storageType = storageType;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        this.servant.setRegionId(this.regionId);
        this.servant.setAccountId(this.player.getAccountId());
        this.player.getServantController().add(this.servant);
        this.player.sendPacket(new SMListServantInfo(this.servant, EPacketTaskType.Update));
        this.player.getObserveController().notifyObserver(EObserveType.tamingServant, this.itemId);
    }

    @Override
    public boolean canAct() {
        if (!this.storageType.isPlayerInventories()) {
            return false;
        }
        final Item item = this.playerBag.getItemPack(this.storageType).getItem(this.slotIndex);
        if (item == null || !item.getItemClassify().isVehicle()) {
            return false;
        }
        final int vehicleId = item.getTemplate().getCharacterKey();
        final ServantTemplate template = ServantData.getInstance().getTemplate(vehicleId, 1);
        if (template == null) {
            return false;
        }
        final ServantSetTemplate servantSetTemplate = ServantSetData.getInstance().getTemplate(template.getId());
        if (this.player.getServantController().isStableFull(this.regionId, servantSetTemplate.getServantType())) {
            this.player.sendPacket(new SMNak(EStringTable.eErrNoStableIsFull, CMRegisterServantVehicleByItem.class));
            return false;
        }
        this.itemId = item.getItemId();
        this.servant = new Servant(template, this.player, this.servantName);
        this.decreaseItem(this.slotIndex, 1L, this.storageType);
        return this.servant != null;
    }
}
