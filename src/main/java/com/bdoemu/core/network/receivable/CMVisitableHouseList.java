// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.core.network.sendable.SMVisitableHouseList;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.FixedHouseTop;
import com.bdoemu.gameserver.model.houses.services.FixedHouseService;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.party.IParty;

import java.util.Collection;

public class CMVisitableHouseList extends ReceivablePacket<GameClient> {
    private int houseId;
    private byte type;

    public CMVisitableHouseList(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.houseId = this.readH();
        this.type = this.readC();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            Collection<FixedHouseTop> fixedHouseTops = null;
            switch (this.type) {
                case 0: {
                    fixedHouseTops = FixedHouseService.getInstance().getTopRank(this.houseId).getTop15();
                    break;
                }
                case 1: {
                    fixedHouseTops = FixedHouseService.getInstance().getTopRank(this.houseId).getTop15(player.getFriendHandler().getFriendAccounts());
                    break;
                }
                case 2: {
                    final Guild guild = player.getGuild();
                    if (guild == null) {
                        player.sendPacket(new SMNak(EStringTable.eErrNoGuildIsntMember, this.opCode));
                        return;
                    }
                    fixedHouseTops = FixedHouseService.getInstance().getTopRank(this.houseId).getTop15(guild.getGuildAccounts());
                    break;
                }
                case 3: {
                    final IParty<Player> party = player.getParty();
                    if (party == null) {
                        player.sendPacket(new SMNak(EStringTable.eErrNoPartyMemberNotExist, this.opCode));
                        return;
                    }
                    fixedHouseTops = FixedHouseService.getInstance().getTopRank(this.houseId).getTop15(party.getPartyAccounts());
                    break;
                }
                default: {
                    return;
                }
            }
            if (fixedHouseTops.size() > 0) {
                player.sendPacket(new SMVisitableHouseList(this.houseId, fixedHouseTops, this.type));
            }
        }
    }
}
