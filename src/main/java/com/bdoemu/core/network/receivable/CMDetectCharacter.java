// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMDetectCharacter;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.DetectCharacterItemEvent;
import com.bdoemu.gameserver.worldInstance.World;

public class CMDetectCharacter extends ReceivablePacket<GameClient> {
    private String name;

    public CMDetectCharacter(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.name = this.readS(62);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null && player.getPlayerBag().onEvent(new DetectCharacterItemEvent(player))) {
            player.sendPacket(new SMDetectCharacter(World.getInstance().getPlayer(this.name)));
        }
    }
}
