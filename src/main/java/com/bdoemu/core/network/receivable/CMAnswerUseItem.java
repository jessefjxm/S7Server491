// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMUseItemNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.DeleteItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.worldInstance.World;

public class CMAnswerUseItem extends ReceivablePacket<GameClient> {
    private int slotIndex;
    private int gameObjectId;
    private boolean result;
    private EItemStorageLocation storageLocation;

    public CMAnswerUseItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.gameObjectId = this.readD();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.result = this.readCB();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        final Player askPlayer = World.getInstance().getPlayer(this.gameObjectId);
        if (player != null && askPlayer != null && this.storageLocation != null) {
            if (this.result) {
                if (askPlayer.getPlayerBag().onEvent(new DeleteItemEvent(askPlayer, this.slotIndex, 1L, this.storageLocation, ""))) {
                    player.revive(100, 100);
                }
            } else {
                askPlayer.sendPacket(new SMUseItemNak(this.storageLocation, this.slotIndex, EStringTable.eErrNoUseItemByOtherPlayerNo));
            }
        }
    }
}
