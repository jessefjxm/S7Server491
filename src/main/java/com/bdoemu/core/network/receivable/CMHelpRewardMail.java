// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.RewardMailItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMHelpRewardMail extends ReceivablePacket<GameClient> {
    private EItemStorageLocation storageType;
    private int slotIndex;
    private String name;
    private String mailSubject;
    private String mailMessage;

    public CMHelpRewardMail(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.storageType = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.name = this.readS(62);
        this.mailSubject = this.readS(202);
        this.mailMessage = this.readS(602);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new RewardMailItemEvent(player, this.storageType, this.slotIndex, this.name, this.mailSubject, this.mailMessage));
        }
    }
}
