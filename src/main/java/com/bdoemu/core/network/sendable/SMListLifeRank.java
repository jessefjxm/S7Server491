// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.lifeExperience.enums.ELifeExpType;
import com.bdoemu.gameserver.service.LifeRankService;

import java.util.List;

public class SMListLifeRank extends SendablePacket<GameClient> {
    private List<Player> players;
    private ELifeExpType lifeExpType;
    private Player owner;

    public SMListLifeRank(final List<Player> players, final ELifeExpType lifeExpType, final Player owner) {
        this.players = players;
        this.lifeExpType = lifeExpType;
        this.owner = owner;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeD(this.owner.getLifeExperienceStorage().getLifeExperience(this.lifeExpType).getRankLevel());
        buffer.writeD(LifeRankService.getInstance().getMembersRegistered());
        buffer.writeC(this.lifeExpType.ordinal());
        for (int i = 0; i < 30; ++i) {
            Player player = null;
            if (i < this.players.size()) {
                player = this.players.get(i);
            }
            this.writeLifeExpData(buffer, player);
        }
    }

    private void writeLifeExpData(final SendByteBuffer buffer, final Player player) {
        buffer.writeQ((player == null) ? 0L : player.getAccountId());
        buffer.writeS((CharSequence) ((player == null) ? "" : player.getName()), 62);
        buffer.writeS((CharSequence) ((player == null) ? "" : player.getFamily()), 62);
        buffer.writeQ((player == null) ? 0L : player.getObjectId());
        buffer.writeD((player == null) ? 0 : player.getLifeExperienceStorage().getLifeExperience(this.lifeExpType).getLevel());
        buffer.writeQ((player == null) ? 0L : player.getGuildId());
        buffer.writeD((player == null) ? -1024 : player.getGameObjectId());
    }
}
