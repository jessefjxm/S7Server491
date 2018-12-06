// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.core.network.sendable.SMNak;
import com.bdoemu.gameserver.model.creature.enums.ECharKind;
import com.bdoemu.gameserver.model.creature.npc.Npc;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.ChangeServantNameItemEvent;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;

public class CMChangeServantName extends ReceivablePacket<GameClient> {
    private long servantObjId;
    private int npcGameObjId;
    private int slotIndex;
    private String servantName;
    private EItemStorageLocation storageLocation;

    public CMChangeServantName(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.servantObjId = this.readQ();
        this.npcGameObjId = this.readD();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.servantName = this.readS(62);
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            if (this.servantName.length() < 2 || this.servantName.length() > 10) {
                player.sendPacket(new SMNak(EStringTable.eErrNoNameLengthIsTooShort, this.opCode));
                return;
            }
            String regex = "[0-9]+";
            if (this.servantName.matches(regex)) {
                player.sendPacket(new SMNak(EStringTable.eErrNoNameDontHaveNumberOnly, this.opCode));
                return;
            }
            regex = "(.)\\1\\1";
            if (this.servantName.matches(regex) || Character.isLowerCase(this.servantName.charAt(0))) {
                player.sendPacket(new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
                return;
            }
            regex = "[a-zA-Z0-9]+";
            if (!this.servantName.matches(regex)) {
                player.sendPacket(new SMNak(EStringTable.eErrNoNameCharacterIsInvalid, this.opCode));
                return;
            }
            final Npc npc = KnowList.getObject(player, ECharKind.Npc, this.npcGameObjId);
            if (npc == null) {
                return;
            }
            player.getPlayerBag().onEvent(new ChangeServantNameItemEvent(player, this.servantName, this.servantObjId, this.slotIndex, this.storageLocation, npc.getRegionId()));
        }
    }
}
