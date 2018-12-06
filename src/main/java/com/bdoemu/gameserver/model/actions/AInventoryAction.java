package com.bdoemu.gameserver.model.actions;

import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.nio.ByteBuffer;

public abstract class AInventoryAction extends AAction {
    protected int[][] manufactures;
    protected byte storageType;
    protected int slotIndex;
    protected int characterKey;
    protected long houseObjId;
    protected long installationObjId;

    public AInventoryAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void read(final ByteBuffer buff, final Player owner) {
        super.read(buff, owner);
        this.storageType = this.readC(buff);
        this.slotIndex = this.readCD(buff);
        this.houseObjId = this.readQ(buff);
        this.installationObjId = this.readQ(buff);
        this.characterKey = this.readHD(buff);
        this.manufactures = new int[5][2];
        for (int i = 0; i < 5; ++i) {
            final int itemId = this.readHD(buff);
            final int enchantLevel = this.readHD(buff);
            this.readC(buff);
            final long itemObjectId = this.readQ(buff);
            this.readC(buff);
            this.readD(buff);
            this.readH(buff);
            this.manufactures[i][0] = this.readC(buff);
            this.manufactures[i][1] = this.readCD(buff);
            this.type = this.readC(buff);
            this.readD(buff);
            this.readH(buff);
        }
    }

    public int getCharacterKey() {
        return this.characterKey;
    }

    public long getHouseObjId() {
        return this.houseObjId;
    }

    public long getInstallationObjId() {
        return this.installationObjId;
    }

    public int[][] getManufactures() {
        return this.manufactures;
    }

    public byte getStorageType() {
        return this.storageType;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    @Override
    public boolean canAct() {
        return this.subType == 1;
    }
}
