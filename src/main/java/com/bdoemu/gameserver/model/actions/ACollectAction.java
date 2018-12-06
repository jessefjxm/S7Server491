package com.bdoemu.gameserver.model.actions;

import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.player.Player;

import java.nio.ByteBuffer;

public abstract class ACollectAction extends AAction {
    protected int sessionId;
    protected int resourceIndex;
    protected int subSectorX;
    protected int subSectorY;
    protected int collectType;
    protected int slotIndex;
    protected byte storageType;
    protected byte collectTentType;
    protected long houseHoldObjId;
    protected long houseInstallationObjId;

    public ACollectAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void read(final ByteBuffer buff, final Player owner) {
        super.read(buff, owner);
        this.sessionId = this.readD(buff);
        switch (this.collectType = this.readC(buff)) {
            case 8: {
                this.subSectorX = this.readH(buff);
                this.subSectorY = this.readH(buff);
                this.collectTentType = this.readC(buff);
                this.readC(buff);
                this.houseHoldObjId = this.readQ(buff);
                this.houseInstallationObjId = this.readQ(buff);
                break;
            }
            default: {
                this.subSectorX = this.readH(buff);
                this.subSectorY = this.readH(buff);
                this.resourceIndex = this.readH(buff);
                this.readD(buff);
                this.readD(buff);
                this.readD(buff);
                this.readD(buff);
                this.storageType = this.readC(buff);
                this.slotIndex = this.readCD(buff);
                break;
            }
        }
        this.skipAll(buff);
    }

    public byte getCollectTentType() {
        return this.collectTentType;
    }

    public long getHouseInstallationObjId() {
        return this.houseInstallationObjId;
    }

    public long getHouseHoldObjId() {
        return this.houseHoldObjId;
    }

    @Override
    public boolean canAct() {
        return this.subType == 3;
    }

    public int getCollectType() {
        return this.collectType;
    }

    public byte getStorageType() {
        return this.storageType;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public int getSessionId() {
        return this.sessionId;
    }

    public int getSubSectorX() {
        return this.subSectorX;
    }

    public int getSubSectorY() {
        return this.subSectorY;
    }

    public int getResourceIndex() {
        return this.resourceIndex;
    }
}
