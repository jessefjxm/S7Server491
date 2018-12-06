// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.houses.events.ChangeHouseUseTypeForTownManagementEvent;

public class CMChangeHouseUseTypeForTownManagement extends ReceivablePacket<GameClient> {
    private int houseId;
    private int receipeKey;
    private int level;

    public CMChangeHouseUseTypeForTownManagement(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.houseId = this.readHD();
        this.receipeKey = this.readD();
        this.level = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getHouseStorage().onEvent(new ChangeHouseUseTypeForTownManagementEvent(player, this.houseId, this.receipeKey));
        }
    }
}
