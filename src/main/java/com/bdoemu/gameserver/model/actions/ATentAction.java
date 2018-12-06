package com.bdoemu.gameserver.model.actions;

import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.items.enums.EItemStorageLocation;

import java.nio.ByteBuffer;

public class ATentAction extends AAction {
    protected EItemStorageLocation storageLocation;
    protected double fenceX;
    protected double fenceY;
    protected double fenceZ;
    protected double fenceSin;
    protected double fenceCos;
    protected int slotIndex;
    protected int tentGameObjId;

    public ATentAction(final ActionChartActionT actionChartActionT) {
        super(actionChartActionT);
    }

    @Override
    public void read(final ByteBuffer buff, final Player owner) {
        super.read(buff, owner);
        this.tentGameObjId = this.readD(buff);
        this.storageLocation = EItemStorageLocation.valueOf(this.readC(buff));
        this.slotIndex = this.readCD(buff);
        this.fenceX = this.readF(buff);
        this.fenceZ = this.readF(buff);
        this.fenceY = this.readF(buff);
        this.fenceCos = this.readF(buff);
        this.readD(buff);
        this.fenceSin = this.readF(buff);
        this.skipAll(buff);
    }

    @Override
    public boolean canAct() {
        return this.subType == 2;
    }

    public int getTentGameObjId() {
        return this.tentGameObjId;
    }

    public double getFenceX() {
        return this.fenceX;
    }

    public double getFenceY() {
        return this.fenceY;
    }

    public double getFenceZ() {
        return this.fenceZ;
    }

    public double getFenceCos() {
        return this.fenceCos;
    }

    public double getFenceSin() {
        return this.fenceSin;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public EItemStorageLocation getStorageLocation() {
        return this.storageLocation;
    }
}
