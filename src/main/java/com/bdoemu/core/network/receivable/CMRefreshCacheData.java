package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.*;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.worldInstance.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMRefreshCacheData extends ReceivablePacket<GameClient> {
    private static final Logger log = LoggerFactory.getLogger(CMRefreshCacheData.class);
    private int id;
    private short type;
    private int unkA;
    private int unkB;
    private long lid;

    public CMRefreshCacheData(final short opcode) {
        super(opcode);
    }

    protected void read() {
        switch (this.type = this.readH()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7: {
                this.id = this.readD();
                this.unkA = this.readD();
                this.unkB = this.readD();
                break;
            }
            case 8:
            case 9: {
                this.lid = this.readQ();
                this.unkA = this.readD();
                break;
            }
            case 11: {
                this.id = this.readD();
                this.lid = this.readQ();
                break;
            }
        }
    }

    public void runImpl() {
        final Player player = World.getInstance().getPlayer(this.id);
        switch (this.type) {
            case 0: {
                if (player != null) {
                    this.getClient().sendPacket(new SMRefreshPcCustomizationCache(player));
                    break;
                }
                break;
            }
            case 1: {
                if (player != null) {
                    this.getClient().sendPacket(new SMRefreshPcLearnedActiveSkillsCache(player));
                    break;
                }
                break;
            }
            case 2: {
                if (player != null) {
                    this.getClient().sendPacket(new SMRefreshPcEquipSlotCache(player));
                    break;
                }
                break;
            }
            case 3: {
                final Servant servant = World.getInstance().getServant(this.id);
                if (servant != null && servant.getOwner() != null) {
                    this.getClient().sendPacket(new SMRefreshVehicleEquipSlotCache(servant));
                    break;
                }
                break;
            }
            case 4: {
                if (player != null) {
                    this.getClient().sendPacket(new SMRefreshUserBasicCache(player));
                    break;
                }
                break;
            }
            case 5: {
                if (player != null) {
                    this.getClient().sendPacket(new SMRefreshPcBasicCache(player));
                    break;
                }
                break;
            }
            case 6: {
                if (player != null) {
                    this.getClient().sendPacket(new SMRefreshPcNonSavedCache(player));
                    break;
                }
                break;
            }
            case 7: {
                final Servant servant = World.getInstance().getServant(this.id);
                if (servant != null && servant.getOwner() != null) {
                    this.getClient().sendPacket(new SMRefreshVehicleBasicCache(servant));
                    break;
                }
                break;
            }
            case 8: {
                final Guild guild = GuildService.getInstance().getGuild(this.lid);
                if (guild != null) {
                    this.getClient().sendPacket(new SMRefreshGuildBasicCache(guild));
                    break;
                }
                break;
            }
            case 9: {
                final Guild guild = GuildService.getInstance().getGuild(this.lid);
                if (guild != null) {
                    this.getClient().sendPacket(new SMRefreshGuildMarkCache(guild));
                    break;
                }
                break;
            }
            case 11: {
                final Servant servant = World.getInstance().getServant(this.id);
                if (servant != null && servant.getOwner() != null) {
                    for (final Servant linkedServant : servant.getLinkedServants()) {
                        if (linkedServant.getObjectId() == this.lid) {
                            this.getClient().sendPacket(new SMRefreshVehicleEquipSlotCache(linkedServant));
                            break;
                        }
                    }
                    break;
                }
                break;
            }
        }
    }
}
