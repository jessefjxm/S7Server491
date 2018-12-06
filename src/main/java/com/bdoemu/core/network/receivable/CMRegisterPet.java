// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.commons.network.SendablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.RegisterPetItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

public class CMRegisterPet extends ReceivablePacket<GameClient> {
    private String petName;
    private EItemStorageLocation petItemStorageType;
    private int petItemSlot;

    public CMRegisterPet(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.petName = this.readS(62);
        this.petItemStorageType = EItemStorageLocation.valueOf(this.readC());
        this.petItemSlot = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (this.petName.length() < 3) {
            this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoNameLengthIsTooShort, this.opCode));
            return;
        }
        if (this.petName.length() > 16) {
            this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoNameLengthIsTooLong, this.opCode));
            return;
        }
        String regex = "[0-9]+";
        if (this.petName.matches(regex)) {
            this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoNameDontHaveNumberOnly, this.opCode));
            return;
        }
        regex = "(.)\\1\\1";
        if (this.petName.matches(regex) || Character.isLowerCase(this.petName.charAt(0))) {
            this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
            return;
        }
        regex = "[a-zA-Z0-9]+";
        if (!this.petName.matches(regex)) {
            this.sendPacket((SendablePacket) new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
            return;
        }
        if (player != null) {
            player.getPlayerBag().onEvent(new RegisterPetItemEvent(player, this.petName, this.petItemStorageType, this.petItemSlot));
        }
    }
}
