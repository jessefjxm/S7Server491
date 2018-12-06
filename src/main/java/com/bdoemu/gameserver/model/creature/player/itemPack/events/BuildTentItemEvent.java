package com.bdoemu.gameserver.model.creature.player.itemPack.events;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.idFactory.GSIDStorageType;
import com.bdoemu.core.idFactory.GameServerIDFactory;
import com.bdoemu.core.network.receivable.CMStartAction;
import com.bdoemu.core.network.sendable.SMAddInstanceObject;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMTentInformation;
import com.bdoemu.gameserver.dataholders.CreatureData;
import com.bdoemu.gameserver.model.creature.npc.templates.SpawnPlacementT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.ItemPack;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.templates.CreatureTemplate;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.items.enums.EContentsEventType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.worldInstance.World;

import java.util.Collections;

public class BuildTentItemEvent extends AItemEvent {
    private EItemStorageLocation storageLocation;
    private int slotIndex;
    private Item item;
    private Location loc;

    public BuildTentItemEvent(final Player player, final EItemStorageLocation storageLocation, final int slotIndex, final Location loc) {
        super(player, player, player, EStringTable.eErrNoItemIsRemovedToBuildTent, EStringTable.eErrNoItemIsRemovedToBuildTent, player.getRegionId());
        this.storageLocation = storageLocation;
        this.slotIndex = slotIndex;
        this.loc = loc;
    }

    @Override
    public void onEvent() {
        super.onEvent();
        CreatureTemplate tpl = CreatureData.getInstance().getTemplate(this.item.getTemplate().getCharacterKey());
        if (tpl.getCharKind().isDeadbody()) {
            player.sendPacket(new SMNak(EStringTable.eErrNoIsGoingToImplement, CMStartAction.class));
        	//final Servant servant = new Servant((int) GameServerIDFactory.getInstance().nextId(GSIDStorageType.Creatures), tpl, new SpawnPlacementT(this.loc));
        	//player.sendPacket(new SMAddInstanceObject(Collections.singletonList(servant), player)); // TODO: WorldAdd, not just single player.
        	//player.getSummonStorage().addSummon(servant);
        } else {
            final HouseHold tent = new HouseHold(this.player, this.item.getTemplate().getCharacterKey(), this.item.getItemId(), new SpawnPlacementT(this.loc));
            this.player.getHouseholdController().addHouseHold(tent);
            World.getInstance().spawn(tent, true, false);
            this.player.sendPacket(new SMTentInformation(Collections.singleton(tent)));
        }
    }

    @Override
    public boolean canAct() {
        if (!this.storageLocation.isPlayerInventories())
            return false;

        final ItemPack pack = this.playerBag.getItemPack(this.storageLocation);
        this.item = pack.getItem(this.slotIndex);
        if (this.item == null)
            return false;

        final EContentsEventType contentsEventType = this.item.getTemplate().getContentsEventType();
        if (contentsEventType == null || !contentsEventType.isCampfire())
            return false;

        this.decreaseItem(this.slotIndex, 1L, this.storageLocation);
        return super.canAct();
    }
}
