// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMGetUserNickNameByCharacterName;
import com.bdoemu.gameserver.databaseCollections.PlayersDBCollection;
import com.bdoemu.gameserver.model.creature.player.Player;

public class CMGetUserNickNameByCharacterName extends ReceivablePacket<GameClient> {
    private String name;

    public CMGetUserNickNameByCharacterName(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.name = this.readS(62);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final String familyName = PlayersDBCollection.getInstance().getFamilyByName(this.name);
            this.sendPacket((SendablePacket) new SMGetUserNickNameByCharacterName(this.name, (familyName == null) ? "" : familyName));
        }
    }
}
