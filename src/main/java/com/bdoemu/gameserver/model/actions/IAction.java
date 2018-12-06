package com.bdoemu.gameserver.model.actions;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.enums.ERidingSlotType;
import com.bdoemu.gameserver.model.world.Location;

import java.nio.ByteBuffer;
import java.util.Collection;

public interface IAction {
    boolean canAct();

    void init();

    void onError();

    void read(final ByteBuffer p0, final Player p1);

    void doAction(final int p0, final int p1, final long p2, final Collection<Creature> p3, final Collection<Creature> p4);

    void doAction(final Creature p0);

    void setNewLocation(final Location p0);

    void setOldLocation(final Location p0);

    long getActionHash();

    int getSpeedRate();

    int getSlowSpeedRate();

    String getActionName();

    int getBranchIndex();

    int getOwnerGameObj();

    int getTargetGameObj();

    double getBlendTime();

    void setBlendTime(final double p0);

    Creature getOwner();

    void setOwner(final Creature p0);

    double getNewX();

    double getNewY();

    double getNewZ();

    double getOldX();

    double getOldY();

    double getOldZ();

    double getCos();

    double getSin();

    double getTargetX();

    void setTargetX(final double p0);

    double getTargetY();

    void setTargetY(final double p0);

    double getTargetZ();

    void setTargetZ(final double p0);

    byte getType();

    void setType(final byte p0);

    boolean isCallAction();

    void setCallAction(final boolean p0);

    ActionChartActionT getActionChartActionT();

    void setActionChartActionT(final ActionChartActionT p0);

    long getStartTime();

    double getCarrierX();

    double getCarrierY();

    double getCarrierZ();

    int getCarrierGameObjId();

    ERidingSlotType getRidingSlotType();

    int getUnk2();

    int getUnk3();

    int getUnk4();

    int getSkillId();

    void setSkillId(final int p0);

    int getSkillLevel();

    void setSkillLevel(final int p0);

    void setTargetGameObjId(final int p0);

    EStringTable getMessage();

    void setMessage(final EStringTable p0);

    boolean isUsedFromQuickSlot();

    boolean isUsedInMove();
}