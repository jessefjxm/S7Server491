// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.dataholders.PetChangeLookTable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ChangePetLookEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMChangeLookPet extends ReceivablePacket<GameClient> {
    private long petObjectId;
    private int petNpcId;
    private int slotIndex;
    private int actionIndex;
    private EItemStorageLocation storageLocation;

    public CMChangeLookPet(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.petObjectId = this.readQ();
        this.petNpcId = this.readHD();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readC();
        this.actionIndex = this.readD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Servant pet = player.getServantController().getServant(this.petObjectId);
            if (pet == null) {
                return;
            }
            if (pet.getCreatureId() != this.petNpcId) {
                return;
            }
            if (!PetChangeLookTable.getInstance().isPetActionIndexValid(pet.getPetTemplate().getPetChangeLookKey(), this.actionIndex)) {
                return;
            }
            player.getPlayerBag().onEvent(new ChangePetLookEvent(player, this.petObjectId, this.actionIndex, this.storageLocation, this.slotIndex));
        }
    }
}
