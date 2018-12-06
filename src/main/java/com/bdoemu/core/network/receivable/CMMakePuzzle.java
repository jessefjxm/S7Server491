// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.MakePuzzleItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMMakePuzzle extends ReceivablePacket<GameClient> {
    private int puzzleKey;
    private int slotIndex;
    private EItemStorageLocation storageType;

    public CMMakePuzzle(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.puzzleKey = this.readHD();
        this.storageType = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            player.getPlayerBag().onEvent(new MakePuzzleItemEvent(player, this.puzzleKey, this.slotIndex, this.storageType));
        }
    }
}
