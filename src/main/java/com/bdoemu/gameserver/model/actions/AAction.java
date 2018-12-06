package com.bdoemu.gameserver.model.actions;

import com.bdoemu.commons.model.enums.EStringTable;
import com.bdoemu.core.configs.DebugConfig;
import com.bdoemu.core.network.sendable.SMStartAction;
import com.bdoemu.core.network.sendable.SMStartActionNak;
import com.bdoemu.core.network.sendable.SMStartEmergencyEvade;
import com.bdoemu.gameserver.model.actions.enums.EActionEffectType;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.actions.templates.frameevents.FrameEvent;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.servant.enums.ERidingSlotType;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.model.world.region.GameSector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

public abstract class AAction implements IAction {
    private static final Logger log = LoggerFactory.getLogger(AAction.class);
    protected long actionHash;
    protected long startTime;
    protected long clientTimeMillis;
    protected int targetGameObjId;
    protected int branchIndex;
    protected int carrierGameObjId;
    protected int skillId;
    protected int skillLevel;
    protected byte subType;
    protected byte type;
    protected double newX;
    protected double newY;
    protected double newZ;
    protected double carrierX;
    protected double carrierY;
    protected double carrierZ;
    protected double x3;
    protected double y3;
    protected double z3;
    protected double x4;
    protected double y4;
    protected double z4;
    protected double x5;
    protected double y5;
    protected double z5;
    protected double x6;
    protected double y6;
    protected double z6;
    protected double x7;
    protected double y7;
    protected double z7;
    protected double x8;
    protected double y8;
    protected double z8;
    protected double x9;
    protected double y9;
    protected double z9;
    protected double oldX;
    protected double oldY;
    protected double oldZ;
    protected double targetX;
    protected double targetY;
    protected double targetZ;
    protected double cos;
    protected double sin;
    protected double blendTime;
    protected Creature owner;
    protected IAction parentAction;
    protected ActionChartActionT actionChartActionT;
    protected EStringTable message;
    protected ERidingSlotType ridingSlotType;
    protected int unk2;
    protected int unk3;
    protected int unk4;
    protected boolean callAction;
    protected boolean isUsedFromQuickSlot;
    protected boolean isUsedInMove;

    public AAction(final ActionChartActionT actionChartActionT) {
        this.targetGameObjId = -1024;
        this.carrierGameObjId = -1024;
        this.blendTime = 0.1;
        this.message = EStringTable.NONE;
        this.ridingSlotType = ERidingSlotType.None;
        this.unk2 = 255;
        this.unk3 = 255;
        this.unk4 = 0;
        this.callAction = false;
        this.isUsedFromQuickSlot = false;
        this.actionChartActionT = actionChartActionT;
        this.actionHash = actionChartActionT.getActionHash();
    }

    @Override
    public void doAction(final Creature target) {
    }

    @Override
    public void doAction(final int frameEventIndex, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        if (this.actionChartActionT != null) {
            final List<FrameEvent> frameEvents = this.actionChartActionT.getIndexedFrameEvents();
            if (frameEventIndex < 0 || frameEventIndex >= frameEvents.size()) {
                if (DebugConfig.ENABLE_ACTIONS_DEBUG) {
                    AAction.log.info("Bad FrameEventIndex: [{}] Action: [{}] Hash: [{}] ", frameEventIndex, this.actionChartActionT.getActionName(), this.actionHash);
                }
                return;
            }
            final FrameEvent event = frameEvents.get(frameEventIndex);
            final boolean result = event.doFrame(this, npcGameObjId, staticObjectId, targets, playerTargets);
            if (result && DebugConfig.ENABLE_ACTIONS_DEBUG) {
                AAction.log.info("FrameEvent [{}] FrameEventIndex[{}] Action: [{}] Hash: [{}] [{}]", event.getFrameEventType().name(), frameEventIndex, this.actionChartActionT.getActionName(), this.actionHash + " Targets " + (targets.size() + playerTargets.size()), actionChartActionT.getActionType());
            }
        }
    }

    @Override
    public void read(final ByteBuffer buff, final Player owner) {
        this.owner = owner;
        this.branchIndex = this.readH(buff);
        this.isUsedInMove = this.readCB(buff);
        this.readC(buff);
        this.cos = this.readF(buff);
        this.readD(buff);
        this.sin = this.readF(buff);
        this.newX = this.readF(buff);
        this.newZ = this.readF(buff);
        this.newY = this.readF(buff);
        this.carrierX = this.readF(buff);
        this.carrierZ = this.readF(buff);
        this.carrierY = this.readF(buff);
        this.readF(buff);
        this.readF(buff);
        this.readF(buff);
        this.carrierGameObjId = this.readD(buff);
        this.clientTimeMillis = this.readQ(buff);
        this.isUsedFromQuickSlot = this.readCB(buff);
        this.x3 = this.readF(buff);
        this.z3 = this.readF(buff);
        this.y3 = this.readF(buff);
        this.x4 = this.readF(buff);
        this.z4 = this.readF(buff);
        this.y4 = this.readF(buff);
        this.x5 = this.readF(buff);
        this.z5 = this.readF(buff);
        this.y5 = this.readF(buff);
        this.x6 = this.readF(buff);
        this.z6 = this.readF(buff);
        this.y6 = this.readF(buff);
        this.x7 = this.readF(buff);
        this.z7 = this.readF(buff);
        this.y7 = this.readF(buff);
        this.x8 = this.readF(buff);
        this.z8 = this.readF(buff);
        this.y8 = this.readF(buff);
        this.x9 = this.readF(buff);
        this.z9 = this.readF(buff);
        this.y9 = this.readF(buff);
        this.readQ(buff);
        this.readQ(buff);
        this.readQ(buff);
        this.readQ(buff);
        this.readQ(buff);
        this.readQ(buff);
        this.subType = this.readC(buff);
    }

    @Override
    public void onError() {
        if (this.owner.isPlayer()) {
            this.owner.sendPacket(new SMStartActionNak(this.owner, this.message));
        }
    }

    @Override
    public void init() {
        if (this.getActionChartActionT().getSkillId() > 0) {
            this.skillId = this.getActionChartActionT().getSkillId();
            this.skillLevel = 1;
        }
        this.startTime = System.currentTimeMillis();
        final ActionChartActionT parentActionChartActionT = this.owner.getActionStorage().getActionChartActionT();
        this.parentAction = this.owner.getActionStorage().getAction();
        this.owner.getActionStorage().setAction(this);
        if (this.owner.isPlayer()) {
            final Player player = (Player) this.owner;
            if (!parentActionChartActionT.isEvadeEmergency() && this.owner.getActionStorage().getActionChartActionT().isEvadeEmergency()) {
                player.sendPacket(new SMStartEmergencyEvade(0));
            }
            final int needCombineWavePoint = this.actionChartActionT.getNeedCombineWavePoint();
            if (needCombineWavePoint > 0) {
                if (player.getGameStats().getAdrenalin().getIntValue() < 1) {
                    owner.getActionStorage().onActionError(2524986171L, EStringTable.eErrNoActorSubResourceIsLack);
                    return;
                } else
                    player.getGameStats().getAdrenalin().addAdrenalin(-needCombineWavePoint, false);
            }
            owner.getLocation().setLocation(newX, newY, newZ, cos, sin);
            this.owner.sendBroadcastItSelfPacket(new SMStartAction(this));
            if (!player.getActionStorage().hasActionEffect(EActionEffectType.NotUseStaminaInMove) || !this.actionChartActionT.getActionType().isMove() || !this.actionChartActionT.getSpUseType().isContinue()) {
                player.getGameStats().getStamina().updateStaminaV2(this.actionChartActionT.getSpUseType(), this.actionChartActionT.getApplySp());
            }
            if (this.actionChartActionT.getVarySRP() != 0) {
                player.getGameStats().getSubResourcePointStat().addSubResourcePoints(this.actionChartActionT.getVarySRP());
            }
            if (this.getActionChartActionT().getGuardType().isDefence()) {
                player.getGameStats().getStunGauge().stopUpdateStaminaTask();
            } else {
                player.getGameStats().getStunGauge().startUpdateStaminaTask();
            }
        } else if (!this.actionChartActionT.getActionType().isMove()) {
            final GameSector gameSector = this.owner.getLocation().getGameSector();
            if (gameSector != null && gameSector.hasActiveNeighbours()) {
                this.owner.sendBroadcastItSelfPacket(new SMStartAction(this));
            }
        }
    }

    @Override
    public boolean isUsedFromQuickSlot() {
        return this.isUsedFromQuickSlot;
    }

    @Override
    public boolean isUsedInMove() {
        return this.isUsedInMove;
    }

    @Override
    public int getSpeedRate() {
        switch (this.getActionChartActionT().getApplySpeedBuffType()) {
            case Move: {
                return this.getOwner().getGameStats().getMoveSpeedRate().getMoveSpeedRate();
            }
            case Attack: {
                return this.getOwner().getGameStats().getAttackSpeedRate().getAttackSpeedRate();
            }
            case Cast: {
                return this.getOwner().getGameStats().getCastingSpeedRate().getCastingSpeedRate();
            }
            default: {
                return 0;
            }
        }
    }

    @Override
    public int getSlowSpeedRate() {
        int slowRate = 0;
        final long weightPercentage = this.getOwner().getGameStats().getWeight().getWeightPercentage();
        switch (this.getActionChartActionT().getApplySpeedBuffType()) {
            case Move: {
                if (weightPercentage >= 150L) {
                    slowRate = 950000;
                    break;
                }
                if (weightPercentage >= 125L) {
                    slowRate = 650000;
                    break;
                }
                if (weightPercentage >= 100L) {
                    slowRate = 300000;
                    break;
                }
                break;
            }
            case Attack:
            case Cast: {
                if (weightPercentage >= 150L) {
                    slowRate = 500000;
                    break;
                }
                if (weightPercentage >= 125L) {
                    slowRate = 300000;
                    break;
                }
                if (weightPercentage >= 100L) {
                    slowRate = 150000;
                    break;
                }
                break;
            }
        }
        return slowRate;
    }

    @Override
    public double getTargetX() {
        return this.targetX;
    }

    @Override
    public void setTargetX(final double targetX) {
        this.targetX = targetX;
    }

    @Override
    public double getTargetY() {
        return this.targetY;
    }

    @Override
    public void setTargetY(final double targetY) {
        this.targetY = targetY;
    }

    @Override
    public double getTargetZ() {
        return this.targetZ;
    }

    @Override
    public void setTargetZ(final double targetZ) {
        this.targetZ = targetZ;
    }

    @Override
    public int getSkillLevel() {
        return this.skillLevel;
    }

    @Override
    public void setSkillLevel(final int skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public int getSkillId() {
        return this.skillId;
    }

    @Override
    public void setSkillId(final int skillId) {
        this.skillId = skillId;
    }

    @Override
    public ERidingSlotType getRidingSlotType() {
        return this.ridingSlotType;
    }

    @Override
    public int getUnk2() {
        return this.unk2;
    }

    @Override
    public int getUnk3() {
        return this.unk3;
    }

    @Override
    public int getUnk4() {
        return this.unk4;
    }

    @Override
    public long getActionHash() {
        return this.actionHash;
    }

    @Override
    public String getActionName() {
        return this.actionChartActionT.getActionName();
    }

    @Override
    public long getStartTime() {
        return this.startTime;
    }

    @Override
    public ActionChartActionT getActionChartActionT() {
        return this.actionChartActionT;
    }

    @Override
    public void setActionChartActionT(final ActionChartActionT actionChartActionT) {
        this.actionChartActionT = actionChartActionT;
        this.actionHash = actionChartActionT.getActionHash();
    }

    @Override
    public int getBranchIndex() {
        return this.branchIndex;
    }

    @Override
    public double getCos() {
        return this.cos;
    }

    @Override
    public double getSin() {
        return this.sin;
    }

    @Override
    public double getOldX() {
        return this.oldX;
    }

    @Override
    public double getOldY() {
        return this.oldY;
    }

    @Override
    public double getOldZ() {
        return this.oldZ;
    }

    @Override
    public int getCarrierGameObjId() {
        return this.carrierGameObjId;
    }

    @Override
    public double getCarrierZ() {
        return (this.carrierGameObjId > 0) ? this.carrierZ : this.newZ;
    }

    @Override
    public double getCarrierY() {
        return (this.carrierGameObjId > 0) ? this.carrierY : this.newY;
    }

    @Override
    public double getCarrierX() {
        return (this.carrierGameObjId > 0) ? this.carrierX : this.newX;
    }

    @Override
    public int getOwnerGameObj() {
        return this.owner.getGameObjectId();
    }

    @Override
    public double getBlendTime() {
        return this.blendTime;
    }

    @Override
    public void setBlendTime(final double blendTime) {
        this.blendTime = blendTime;
    }

    @Override
    public Creature getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(final Creature owner) {
        this.owner = owner;
    }

    @Override
    public double getNewX() {
        return this.newX;
    }

    @Override
    public double getNewY() {
        return this.newY;
    }

    @Override
    public double getNewZ() {
        return this.newZ;
    }

    @Override
    public byte getType() {
        return this.type;
    }

    @Override
    public void setType(final byte type) {
        this.type = type;
    }

    @Override
    public int getTargetGameObj() {
        return this.targetGameObjId;
    }

    @Override
    public boolean isCallAction() {
        return this.callAction;
    }

    @Override
    public void setCallAction(final boolean callAction) {
        this.callAction = callAction;
    }

    @Override
    public void setNewLocation(final Location loc) {
        this.newX = loc.getX();
        this.newY = loc.getY();
        this.newZ = loc.getZ();
        this.cos = loc.getCos();
        this.sin = loc.getSin();
    }

    @Override
    public void setOldLocation(final Location loc) {
        this.oldX = loc.getX();
        this.oldY = loc.getY();
        this.oldZ = loc.getZ();
    }

    @Override
    public void setTargetGameObjId(final int targetGameObjId) {
        this.targetGameObjId = targetGameObjId;
    }

    protected final byte readC(final ByteBuffer buff) {
        return (byte) (buff.get() & 0xFF);
    }

    protected final boolean readCB(final ByteBuffer buff) {
        return (buff.get() & 0xFF) == 0x1;
    }

    protected final float readF(final ByteBuffer buff) {
        return buff.getFloat();
    }

    protected final int readD(final ByteBuffer buff) {
        return buff.getInt();
    }

    protected final long readQ(final ByteBuffer buff) {
        return buff.getLong();
    }

    protected final short readH(final ByteBuffer buff) {
        return (short) (buff.getShort() & 0xFFFF);
    }

    protected final int readHD(final ByteBuffer buff) {
        return buff.getShort() & 0xFFFF;
    }

    protected final byte[] readB(final ByteBuffer buff, final int len) {
        final byte[] tmp = new byte[len];
        buff.get(tmp);
        return tmp;
    }

    protected final int readCD(final ByteBuffer buff) {
        return buff.get() & 0xFF;
    }

    protected final void skipAll(final ByteBuffer buff) {
        buff.position(buff.limit());
    }

    @Override
    public EStringTable getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(final EStringTable message) {
        this.message = message;
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
