package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.GuildWarehouse;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.PushInventoryItemToGuildWarehouseEvent;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.PushInventoryItemToWarehouseEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.houses.House;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.team.guild.Guild;

import java.util.Collection;

public class CMPushInventoryItemToWarehouse extends ReceivablePacket<GameClient> {
    private EItemStorageLocation storageType;
    private int sloIndex;
    private long count;
    private int npcSessionId;
    private int srcSession;
    private int type;
    private long houseObjId;
    private long installationObjId;

    public CMPushInventoryItemToWarehouse(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.srcSession = this.readD();
        this.storageType = EItemStorageLocation.valueOf(this.readC());
        this.sloIndex = this.readCD();
        this.count = this.readQ();
        this.readQ();
        switch (this.type = this.readD()) {
            case 0: {
                this.npcSessionId = this.readD();
                this.readQ();
                this.readH();
                this.readH();
                break;
            }
            case 1: {
                this.houseObjId = this.readQ();
                this.installationObjId = this.readQ();
                break;
            }
            case 2: {
                readQ();
                readQ();
                break;
            }
        }
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        int regionId = 0;
        if (player != null) {
            boolean isGuild = false;
            switch (this.type) {
                case 0: { // NPC, City
                    final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcSessionId);
                    if (npc == null || !npc.getTemplate().getCreatureFunctionT().isWarehouse()) {
                        return;
                    }
                    regionId = npc.getSpawnPlacement().getRegionId();
                    break;
                }
                case 1: { // House
                    if (!player.getHouseVisit().isInHouse(this.houseObjId)) {
                        return;
                    }
                    final HouseHold houseHold = player.getHouseholdController().getHouseHold(this.houseObjId);
                    if (houseHold == null) {
                        return;
                    }
                    final House house = player.getHouseStorage().getHouse(houseHold.getCreatureId());
                    regionId = house.getHouseInfoT().getAffiliatedTown();
                    final HouseInstallation houseInstallation = houseHold.getHouseInstallation(this.installationObjId);
                    if (houseInstallation == null || !houseInstallation.getObjectTemplate().getInstallationType().isWarehouseBox()) {
                        return;
                    }
                    break;
                }
                case 2: { // Guild
                    // Check guild
                    final Guild guild = player.getGuild();
                    if (guild == null)
                        return;

                    // Check guild warehouse
                    final GuildWarehouse warehouse = guild.getGuildWarehouse();
                    if (warehouse == null)
                        return;

                    // Set guild
                    isGuild = true;
                    break;
                }
                default: {
                    return;
                }
            }
            final Creature srcSpawn = KnowList.getObject(player, this.srcSession);
            if (srcSpawn != null) {
                if (!srcSpawn.isPlayable()) {
                    return;
                }
                final Playable srcActor = (Playable) srcSpawn;
                if (srcActor.isVehicle()) {
                    final Collection<Servant> servants = player.getServantController().getServants(EServantState.Field);
                    if (!servants.contains(srcActor)) {
                        return;
                    }
                }
                if (srcActor.isPlayer() && player != srcActor) {
                    return;
                }

                player.getPlayerBag().onEvent(isGuild
                        ? new PushInventoryItemToGuildWarehouseEvent(player, srcActor, this.storageType, this.sloIndex, this.count, regionId)
                        : new PushInventoryItemToWarehouseEvent(player, srcActor, this.storageType, this.sloIndex, this.count, regionId)
                );
            }
        }
    }
}
