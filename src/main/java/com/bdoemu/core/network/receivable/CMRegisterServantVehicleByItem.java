// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.RegisterServantVehicleByItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.worldInstance.World;

public class CMRegisterServantVehicleByItem extends ReceivablePacket<GameClient> {
    private String name;
    private int slotIndex;
    private int npcSessionId;
    private EItemStorageLocation storageType;

    public CMRegisterServantVehicleByItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.name = this.readS(62);
        this.npcSessionId = this.readD();
        this.storageType = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (this.name.length() < 3) {
            player.sendPacket(new SMNak(EStringTable.eErrNoNameLengthIsTooShort, this.opCode));
            return;
        }
        if (this.name.length() > 16) {
            player.sendPacket(new SMNak(EStringTable.eErrNoNameLengthIsTooLong, this.opCode));
            return;
        }
        String regex = "[0-9]+";
        if (this.name.matches(regex)) {
            player.sendPacket(new SMNak(EStringTable.eErrNoNameDontHaveNumberOnly, this.opCode));
            return;
        }
        regex = "(.)\\1\\1";
        if (this.name.matches(regex) || Character.isLowerCase(this.name.charAt(0))) {
            player.sendPacket(new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
            return;
        }
        regex = "[a-zA-Z0-9]+";
        if (!this.name.matches(regex)) {
            player.sendPacket(new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
            return;
        }
        if (player != null) {
            final Creature npcObject = World.getInstance().getObjectById(this.npcSessionId);
            if (npcObject != null && npcObject.isNpc()) {
                final Npc npc = (Npc) npcObject;
                player.getPlayerBag().onEvent(new RegisterServantVehicleByItemEvent(player, this.name, this.slotIndex, this.storageType, npc.getRegionId()));
            }
        }
    }
}
