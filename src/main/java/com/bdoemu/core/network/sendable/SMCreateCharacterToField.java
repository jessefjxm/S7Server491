package com.bdoemu.core.network.sendable;

import com.bdoemu.commons.network.SendByteBuffer;
import com.bdoemu.core.network.sendable.utils.WriteCharacterInfo;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.appearance.holders.AppearanceFaceHolder;
import com.bdoemu.gameserver.model.creature.player.appearance.holders.AppearanceHairHolder;

public class SMCreateCharacterToField extends WriteCharacterInfo {
    private final Player player;

    public SMCreateCharacterToField(final Player player) {
        this.player = player;
    }

    protected void writeBody(final SendByteBuffer buffer) {
        buffer.writeH(this.player.getPlayerTemplate().getCharacterKey());
        buffer.writeQ(this.player.getObjectId());
        buffer.writeC(this.player.getSlot());
        buffer.writeS((CharSequence) this.player.getName(), 62);
        buffer.writeD(this.player.getLevel());
        buffer.writeD(0);
        for (int i = 0; i < 31; ++i) {
            this.writeEquipData(buffer, null);
        }
        final AppearanceFaceHolder face = this.player.getPlayerAppearance().getFace();
        final AppearanceHairHolder hairs = this.player.getPlayerAppearance().getHairs();
        buffer.writeC(face.getId());
        buffer.writeC(hairs.getId());
        buffer.writeC(face.getBeard().getId());
        buffer.writeC(face.getMustache().getId());
        buffer.writeC(face.getWhiskers().getId());
        buffer.writeC(face.getEyebrows().getId());
        buffer.writeD(1);
        buffer.writeC(this.player.getZodiac().getId());
        this.writeAppearanceData(buffer, this.player);
        buffer.writeQ(this.player.getDeletionDate());
        buffer.writeC(0);
        buffer.writeQ(this.player.getLastLogin() / 1000L);
        buffer.writeQ(this.player.getCreationDate() / 1000L);
        buffer.writeF(this.player.getLocation().getX());
        buffer.writeF(this.player.getLocation().getZ());
        buffer.writeF(this.player.getLocation().getY());
        buffer.writeQ(this.player.getBlockDate());
        buffer.writeH(0);
        buffer.writeQ(0L);
        buffer.writeB(new byte[44 + 15]);
    }
}
