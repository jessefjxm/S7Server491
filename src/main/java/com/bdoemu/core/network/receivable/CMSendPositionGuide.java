// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMSendPositionGuide;
import com.bdoemu.gameserver.dataholders.RegionData;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.party.IParty;

public class CMSendPositionGuide extends ReceivablePacket<GameClient> {
    private float x;
    private float y;
    private float z;
    private byte markType;
    private boolean isPath;

    public CMSendPositionGuide(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.markType = this.readC();
        this.isPath = this.readCB();
        this.readC();
        this.x = this.readF();
        this.z = this.readF();
        this.y = this.readF();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Guild guild = player.getGuild();
            boolean isGuildLeader = false;
            if (guild != null) {
                isGuildLeader = (guild.getLeaderAccountId() == player.getAccountId());
            }
            switch (this.markType) {
                case 0: {
                    final IParty<Player> party = player.getParty();
                    if (party != null) {
                        party.sendBroadcastPacket(new SMSendPositionGuide(player.getGameObjectId(), isGuildLeader, this.markType, this.isPath, this.x, this.y, this.z));
                        break;
                    }
                    break;
                }
                case 1: {
                    if (guild != null) {
                        guild.sendBroadcastPacket(new SMSendPositionGuide(player.getGameObjectId(), isGuildLeader, this.markType, this.isPath, this.x, this.y, this.z));
                        break;
                    }
                    break;
                }
            }
            if (player.getAccessLevel().isAdmin()) {
                final double diffX = this.x - RegionData.MAP_MIN_X;
                final double diffY = RegionData.MAP_MAX_Y - this.y;
                final int pixelX = (int) (diffX / RegionData.MAP_IMAGE_SCALE);
                final int pixelY = (int) (diffY / RegionData.MAP_IMAGE_SCALE);
                player.sendMessage(" X: " + this.x + " Y: " + this.y + " Z: " + this.z + " pixelX " + pixelX + " pixelY " + pixelY, true);
                System.out.println("X=\"" + this.x + "\" Y=\"" + this.y + "\" Z=\"" + this.z + "\"");
            }
        }
    }
}
