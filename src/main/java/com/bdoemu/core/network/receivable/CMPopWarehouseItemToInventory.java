package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.GuildWarehouse;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.PopGuildWarehouseItemToInventoryEvent;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.PopWarehouseItemToInventoryEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.houses.House;
import com.bdoemu.gameserver.model.houses.HouseHold;
import com.bdoemu.gameserver.model.houses.HouseInstallation;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.team.guild.Guild;

import java.util.Collection;

public class CMPopWarehouseItemToInventory extends ReceivablePacket<GameClient> {
    private long count;
    private long objectId;
    private int npcSessionId;
    private int dstSession;
    private int type;
    private long houseObjId;
    private long installationObjId;

    public CMPopWarehouseItemToInventory(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.readC();
        this.dstSession = this.readD();
        this.objectId = this.readQ();
        this.readH();
        this.readH();
        this.count = this.readQ();
        this.readH();
        switch (this.type = this.readD()) {
            case 0: { // Town
                this.npcSessionId = this.readD();
                this.readQ();
                this.readH();
                this.readH();
                break;
            }
            case 1: { // House
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
                case 0: {
                    final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcSessionId);
                    if (npc == null || !npc.getTemplate().getCreatureFunctionT().isWarehouse()) {
                        return;
                    }
                    regionId = npc.getSpawnPlacement().getRegionId();
                    break;
                }
                case 1: {
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
            final Creature dstSpawn = KnowList.getObject(player, this.dstSession);
            if (dstSpawn != null) {
                if (!dstSpawn.isPlayable()) {
                    return;
                }
                final Playable dstActor = (Playable) dstSpawn;
                if (dstActor.isVehicle()) {
                    final Collection<Servant> servants = player.getServantController().getServants(EServantState.Field);
                    if (!servants.contains(dstActor)) {
                        return;
                    }
                }
                if (dstActor.isPlayer() && player != dstActor) {
                    return;
                }
                player.getPlayerBag().onEvent(isGuild
                        ? new PopGuildWarehouseItemToInventoryEvent(player, dstActor, this.objectId, this.count, regionId)
                        : new PopWarehouseItemToInventoryEvent(player, dstActor, this.objectId, this.count, regionId)
                );
            }
        }
    }
}
