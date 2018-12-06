// 
// Decompiled by Procyon v0.5.30
// 

package com.bdoemu.core.network.receivable;

import com.bdoemu.commons.network.ReceivablePacket;
import com.bdoemu.core.network.GameClient;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.itemPack.events.UseInstallationItemEvent;
import com.bdoemu.gameserver.model.houses.enums.EFixedHouseType;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;
import com.bdoemu.gameserver.model.world.Location;

public class CMUseInstallationItem extends ReceivablePacket<GameClient> {
    private long accountId;
    private long objectId;
    private long parentObjId;
    private int creatureId;
    private int slotIndex;
    private EItemStorageLocation storageType;
    private float x;
    private float y;
    private float z;
    private float cos;
    private float sin;
    private EFixedHouseType fixedHouseType;

    public CMUseInstallationItem(final short opcode) {
        super(opcode);
    }

    protected void read() {
        this.accountId = this.readQ();
        this.objectId = this.readQ();
        this.creatureId = this.readHD();
        this.storageType = EItemStorageLocation.valueOf(this.readC());
        this.slotIndex = this.readCD();
        this.x = this.readF();
        this.z = this.readF();
        this.y = this.readF();
        this.readD();
        this.cos = this.readF();
        this.readD();
        this.sin = this.readF();
        this.fixedHouseType = EFixedHouseType.values()[this.readCD()];
        this.readC();
        this.readQ();
        this.parentObjId = this.readQ();
    }

    public void runImpl() {
        final Player player = ((GameClient) this.getClient()).getPlayer();
        if (player != null) {
            final Location loc = new Location(this.x, this.y, this.z, this.cos, this.sin);
            player.getPlayerBag().onEvent(new UseInstallationItemEvent(player, this.objectId, this.storageType, this.slotIndex, loc, this.creatureId, this.parentObjId, this.fixedHouseType));
        }
    }
}
