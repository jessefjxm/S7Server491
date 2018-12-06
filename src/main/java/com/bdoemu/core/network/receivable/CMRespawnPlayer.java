/*
 * Decompiled with CFR 0_121.
 * 
 * Could not load the following classes:
 *  com.bdoemu.commons.network.Client
 *  com.bdoemu.commons.network.ReceivablePacket
 *  com.bdoemu.commons.network.SendablePacket
 */
package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMLoadField;
import com.bdoemu.core.network.sendable.SMLoadFieldComplete;
import com.bdoemu.gameserver.dataholders.RegionData;
import com.bdoemu.gameserver.dataholders.TeleportData;
import com.bdoemu.gameserver.dataholders.WaypointDataOld;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.enums.ERespawnType;
import com.bdoemu.gameserver.model.creature.player.exploration.templates.WaypointTemplate;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.RespawnPlayerItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.misc.enums.ETeleportType;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.model.world.region.templates.RegionTemplate;
import com.bdoemu.gameserver.worldInstance.World;

public class CMRespawnPlayer
        extends ReceivablePacket<GameClient> {
    private ERespawnType respawnType;
    private int slotIndex;
    private EItemStorageLocation storageLocation;

    public CMRespawnPlayer(short opcode) {
        super(opcode);
    }

    protected void read() {
        this.respawnType = ERespawnType.valueOf(this.readC());
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.readH();
        this.readQ();
    }

    public void runImpl() {
        Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && this.respawnType != null) {
            Location location;
            if (!player.isDead()) {
                return;
            }
            Location loc = player.getLocation();
            if (player.getBanController().isInJail()) {
                Location jaiLoc = TeleportData.getInstance().getTeleportLocation(ETeleportType.JAIL, 1);
                location = new Location(jaiLoc.getX(), jaiLoc.getY(), jaiLoc.getZ(), loc.getCos(), loc.getSin());
            } else {
                block0:
                switch (this.respawnType) {
                    case Ressurect: {
                        if (player.isDeadInDuel() || player.getRegion().getRegionType().isArena()) {
                            player.revive(100, 100);
                            player.setDeadInDuel(false);
                            player.getAi().notifyStart();
                        } else {
                            player.getPlayerBag().onEvent(new RespawnPlayerItemEvent(player, this.storageLocation, this.slotIndex));
                        }
                        return;
                    }
                    case RespawnInNode: {
                        WaypointTemplate waypointTemplate = WaypointDataOld.getInstance().getNearestTemplate(loc.getX(), loc.getY(), loc.getZ());
                        location = new Location(waypointTemplate.getX(), waypointTemplate.getY(), waypointTemplate.getZ() + 30.0f, loc.getCos(), loc.getSin());
                        break;
                    }
                    case RespawnInTown:
                    case AutoRespawn: {
                        RegionTemplate regionTemplate = player.getRegion().getTemplate();
                        if (regionTemplate.getReturnX() == 0.0f && regionTemplate.getReturnY() == 0.0f) {
                            regionTemplate = RegionData.getInstance().getNearestReturnTemplate(loc.getX(), loc.getY(), loc.getZ() + 30.0, regionTemplate.getTerritoryKey());
                        }
                        if (regionTemplate == null) {
                            return;
                        }
                        location = new Location(regionTemplate.getReturnX(), regionTemplate.getReturnY(), regionTemplate.getReturnZ() + 30.0f, loc.getCos(), loc.getSin());
                        break;
                    }
                    case RedBattlefieldRespawn: {
                        switch (player.getPVPController().getLocalWarTeamType()) {
                            case YellowTeam: {
                                location = new Location(326171.0, 613313.0, -1815.0);
                                break block0;
                            }
                            case RedTeam: {
                                location = new Location(344161.0, 614107.0, -1715.0);
                                break block0;
                            }
                        }
                        return;
                    }
                    default: {
                        return;
                    }
                }
            }
            player.sendPacket(new SMLoadField());
            player.setReadyToPlay(false);
            player.getGameStats().getHp().fill();
            player.getGameStats().getMp().fill();
            World.getInstance().teleport(player, location);
            player.sendPacket(new SMLoadFieldComplete());
        }
    }

}

