// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.Playable;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.DyeItemEvent;
import com.bdoemu.gameserver.model.creature.servant.Servant;
import com.bdoemu.gameserver.model.creature.servant.enums.EServantState;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.knowlist.KnowList;
import com.bdoemu.gameserver.model.skills.packageeffects.enums.EChargeUserType;

import java.util.Collection;

public class CMDyeItem extends ReceivablePacket<GameClient> {
    private int sessionId;
    private int slotIndex;
    private int itemDyePart;
    private int invSlot;
    private int itemDyeId;
    private int storageType;
    private int palleteType;
    private int type;
    private EItemStorageLocation storageLocation;

    public CMDyeItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.sessionId = this.readD();
        this.storageLocation = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.readC();
        this.readD();
        this.readH();
        this.itemDyePart = this.readH();
        this.readC();
        this.readC();
        this.itemDyeId = this.readD();
        this.type = this.readC();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Creature target = KnowList.getObject(player, this.sessionId);
            if (target != null) {
                if (!target.isPlayable()) {
                    return;
                }
                final Playable dstActor = (Playable) target;
                if (dstActor.isVehicle()) {
                    final Collection<Servant> servants = player.getServantController().getServants(EServantState.Field);
                    if (!servants.contains(dstActor)) {
                        return;
                    }
                }
                if (dstActor.isPlayer() && player != dstActor) {
                    return;
                }
                if (this.type == 1 && !player.getAccountData().getChargeUserStorage().isActiveChargeUserEffect(EChargeUserType.DyeingPackage)) {
                    return;
                }
                player.getPlayerBag().onEvent(new DyeItemEvent(player, this.slotIndex, this.itemDyePart, this.itemDyeId, this.storageLocation, dstActor, this.type));
            }
        }
    }
}
