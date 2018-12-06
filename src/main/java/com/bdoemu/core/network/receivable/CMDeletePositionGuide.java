// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMDeletePositionGuide;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.party.IParty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMDeletePositionGuide extends ReceivablePacket<GameClient> {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger((Class) CMDeletePositionGuide.class);
    }

    private int sessionId;
    private byte markType;

    public CMDeletePositionGuide(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.sessionId = this.readD();
        this.markType = this.readC();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            switch (this.markType) {
                case 0: {
                    final IParty<Player> party = player.getParty();
                    if (party != null) {
                        party.sendBroadcastPacket(new SMDeletePositionGuide(player.getGameObjectId(), this.markType));
                        break;
                    }
                    break;
                }
                case 1: {
                    final Guild guild = player.getGuild();
                    if (guild != null) {
                        guild.sendBroadcastPacket(new SMDeletePositionGuide(player.getGameObjectId(), this.markType));
                        break;
                    }
                    break;
                }
                default: {
                    CMDeletePositionGuide.log.warn("Unknown CMDeletePositionGuide type detected (markType={})", (Object) this.markType);
                    break;
                }
            }
        }
    }
}
