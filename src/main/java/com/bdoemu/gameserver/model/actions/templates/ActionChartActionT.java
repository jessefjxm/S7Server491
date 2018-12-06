package com.bdoemu.gameserver.model.actions.templates;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.gameserver.model.actions.enums.*;
import com.bdoemu.gameserver.model.actions.templates.frameevents.FrameEvent;
import com.bdoemu.gameserver.model.creature.Creature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionChartActionT {
    private static final Logger log = LoggerFactory.getLogger(ActionChartActionT.class);
    public static float FRAME_RATE = 33.333332f;
    private final String actionName;
    private final long actionHash;
    private final int skillId;
    private final int vehicleSkillKey;
    private final int indexedActionCount;
    private final int addDvForMonster;
    private final int addPvForMonster;
    private final int varyWP;
    private final int varySRP;
    private final int addDv;
    private final int addPv;
    private final int attackAbsorbAmount1;
    private final int attackAbsorbAmount2;
    private final int hpPerDamage;
    private final int mpPerDamage;
    private final int stunPerDamage;
    private final int applySp;
    private final int combineWavePoint;
    private final int needCombineWavePoint;
    private final Map<String, ActionBranchT> branches;
    private final List<FrameEvent> frameEvents;
    private final List<FrameEvent> indexedFrameEvents;
    private final EActionType actionType;
    private final ESpUseType spUseType;
    private final int navigationTypes;
    private final float moveSpeed;
    private final float minMoveSpeed;
    private final float maxMoveSpeed;
    private final float staminaSpeed;
    private final float startFrame;
    private final float endFrame;
    private final float animationSpeed;
    private final float animationTime;
    private final EAttackAbsorbType attackAbsorbType1;
    private final EAttackAbsorbType attackAbsorbType2;
    private final EBattleAimedActionType battleAimedActionType;
    private final EApplySpeedBuffType applySpeedBuffType;
    private final EMoveDirectionType moveDirectionType;
    private final EAttachTerrainType attachTerrainType;
    private final EGuardType guardType;
    private int actionTime;
    private List<ETargetType> targetTypes;
    private boolean isDoBranch;
    private boolean isForceMove;
    private boolean isSwimmingAction;
    private boolean isNoDelayAI;
    private boolean isParkingAction;
    private boolean isEvadeEmergency;

    public ActionChartActionT(final FileBinaryReader reader) {
        this.branches = new HashMap<>();
        this.frameEvents = new ArrayList<>();
        this.indexedFrameEvents = new ArrayList<>();
        this.targetTypes = ETargetType.valueof(reader.readD());
        this.actionHash = reader.readDQ();
        this.actionName = reader.readS();
        this.actionType = EActionType.valueof(reader.readCD());
        this.battleAimedActionType = EBattleAimedActionType.valueof(reader.readCD());
        this.applySpeedBuffType = EApplySpeedBuffType.valueof(reader.readCD());
        this.minMoveSpeed = reader.readF();
        this.maxMoveSpeed = reader.readF();
        this.moveSpeed = reader.readF();
        this.startFrame = reader.readF();
        this.endFrame = reader.readF();
        this.animationSpeed = reader.readF();
        this.animationTime = reader.readF();
        this.combineWavePoint = reader.readHD();
        this.needCombineWavePoint = reader.readHD();
        this.hpPerDamage = reader.readHD();
        this.mpPerDamage = reader.readHD();
        this.stunPerDamage = reader.readHD();
        this.addDvForMonster = reader.readHD();
        this.addPvForMonster = reader.readHD();
        this.varyWP = reader.readHD();
        this.varySRP = reader.readD();
        this.applySp = reader.readHD();
        this.staminaSpeed = reader.readF();
        this.addDv = reader.readCD();
        this.addPv = reader.readCD();
        this.attackAbsorbAmount1 = reader.readH();
        this.attackAbsorbAmount2 = reader.readH();
        this.attackAbsorbType1 = EAttackAbsorbType.valueOf(reader.readCD());
        this.attackAbsorbType2 = EAttackAbsorbType.valueOf(reader.readCD());
        this.spUseType = ESpUseType.values()[reader.readCD()];
        this.guardType = EGuardType.values()[reader.readCD()];
        this.skillId = reader.readHD();
        this.vehicleSkillKey = reader.readCD();
        this.navigationTypes = reader.readD();
        this.moveDirectionType = EMoveDirectionType.values()[reader.readC()];
        this.attachTerrainType = EAttachTerrainType.values()[reader.readC()];
        this.indexedActionCount = reader.readHD();
        final int bitData1 = reader.readCD();
        final int bitData2 = reader.readCD();
        this.isNoDelayAI = ((bitData2 & 0x40) == 0x40);
        final int bitData3 = reader.readCD();
        this.isParkingAction = ((bitData3 & 0x20) == 0x20);
        final int bitData4 = reader.readCD();
        final int bitData5 = reader.readCD();
        this.isDoBranch = ((bitData5 & 0x1) == 0x1);
        this.isSwimmingAction = ((bitData5 & 0x10) == 0x10);
        final int bitData6 = reader.readCD();
        this.isEvadeEmergency = ((bitData6 & 0x10) == 0x10);
        final int bitData7 = reader.readCD();
        this.isForceMove = ((bitData7 & 0x2) == 0x2);
        for (int branchCount = reader.readD(), branchIndex = 0; branchIndex < branchCount; ++branchIndex) {
            final ActionBranchT actionBranchT = new ActionBranchT(reader);
            this.branches.put(actionBranchT.getCondition(), actionBranchT);
        }
        final float totalFrames = this.animationTime * 1000.0f / ActionChartActionT.FRAME_RATE;
        final float fps = this.animationTime * 1000.0f * this.animationSpeed / totalFrames;
        this.actionTime = (int) ((((this.endFrame == -1.0f) ? totalFrames : this.endFrame) - this.startFrame) * fps);
        float delay = 0.0f;
        final int frameCount = reader.readD();
        float currentFrame = this.startFrame;
        float currentDelay = 0.0f;
        int prevFrameTime = 0;
        for (int frameIndex = 0; frameIndex < frameCount; ++frameIndex) {
            final EFrameEventType frameEventType = EFrameEventType.valueof(reader.readCD());
            final FrameEvent frameEvent = frameEventType.getNewFrameEventInstance();
            if (frameEvent != null) {
                frameEvent.read(reader);
                if (currentFrame != frameEvent.getFrame()) {
                    delay += currentDelay;
                    currentDelay = 0.0f;
                    currentFrame = frameEvent.getFrame();
                }
                currentDelay += frameEvent.getDelay();
                final int frameTime = (int) ((frameEvent.getFrame() - this.startFrame) * fps + delay);
                frameEvent.setFrameTime(frameTime - prevFrameTime);
                prevFrameTime = frameTime;
                if (!frameEventType.isSpeed()) {
                    this.indexedFrameEvents.add(frameEvent);
                }
                this.frameEvents.add(frameEvent);
            } else {
                ActionChartActionT.log.error("Found non server-side FrameEvent in binary data: type={}", (Object) frameEventType.toString());
            }
        }
        this.actionTime += (int) (delay + currentDelay);
        this.actionTime -= prevFrameTime;
    }

    public int getSpeedRate(final Creature owner) {
        switch (this.applySpeedBuffType) {
            case Move: {
                return owner.getGameStats().getMoveSpeedRate().getMoveSpeedRate();
            }
            case Attack: {
                return owner.getGameStats().getAttackSpeedRate().getAttackSpeedRate();
            }
            case Cast: {
                return owner.getGameStats().getCastingSpeedRate().getCastingSpeedRate();
            }
            default: {
                return 0;
            }
        }
    }

    public int getSlowSpeedRate(final Creature owner) {
        int slowRate = 0;
        final long weightPercentage = owner.getGameStats().getWeight().getWeightPercentage();
        switch (this.applySpeedBuffType) {
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

    public boolean isParkingAction() {
        return this.isParkingAction;
    }

    public boolean isForceMove() {
        return this.isForceMove;
    }

    public boolean isSwimmingAction() {
        return this.isSwimmingAction;
    }

    public boolean isDoBranch() {
        return this.isDoBranch;
    }

    public boolean isNoDelayAI() {
        return this.isNoDelayAI;
    }

    public int getVarySRP() {
        return this.varySRP;
    }

    public int getVaryWP() {
        return this.varyWP;
    }

    public EMoveDirectionType getMoveDirectionType() {
        return this.moveDirectionType;
    }

    public EAttachTerrainType getAttachTerrainType() {
        return this.attachTerrainType;
    }

    public int getIndexedActionCount() {
        return this.indexedActionCount;
    }

    public EApplySpeedBuffType getApplySpeedBuffType() {
        return this.applySpeedBuffType;
    }

    public EBattleAimedActionType getBattleAimedActionType() {
        return this.battleAimedActionType;
    }

    public ActionBranchT getBranch(final String condition) {
        return this.branches.get(condition);
    }

    public Map<String, ActionBranchT> getBranches() {
        return this.branches;
    }

    public float getMinMoveSpeed() {
        return this.minMoveSpeed;
    }

    public float getMaxMoveSpeed() {
        return this.maxMoveSpeed;
    }

    public float getMoveSpeed() {
        return this.moveSpeed;
    }

    public int getAttackAbsorbAmount2() {
        return this.attackAbsorbAmount2;
    }

    public int getAttackAbsorbAmount1() {
        return this.attackAbsorbAmount1;
    }

    public EAttackAbsorbType getAttackAbsorbType2() {
        return this.attackAbsorbType2;
    }

    public EAttackAbsorbType getAttackAbsorbType1() {
        return this.attackAbsorbType1;
    }

    public List<FrameEvent> getFrameEvents() {
        return this.frameEvents;
    }

    public List<FrameEvent> getIndexedFrameEvents() {
        return this.indexedFrameEvents;
    }

    public EActionType getActionType() {
        return this.actionType;
    }

    public int getNavigationTypes() {
        return this.navigationTypes;
    }

    public ESpUseType getSpUseType() {
        return this.spUseType;
    }

    public EGuardType getGuardType() {
        return this.guardType;
    }

    public int getApplySp() {
        return this.applySp;
    }

    public int getSkillId() {
        return this.skillId;
    }

    public int getSkillLevel() {
        return (this.skillId > 0) ? 1 : 0;
    }

    public int getVehicleSkillKey() {
        return this.vehicleSkillKey;
    }

    public int getHpPerDamage() {
        return this.hpPerDamage;
    }

    public int getMpPerDamage() {
        return this.mpPerDamage;
    }

    public int getStunPerDamage() {
        return this.stunPerDamage;
    }

    public long getActionHash() {
        return this.actionHash;
    }

    public int getCombineWavePoint() {
        return this.combineWavePoint;
    }

    public int getNeedCombineWavePoint() {
        return this.needCombineWavePoint;
    }

    public String getActionName() {
        return this.actionName;
    }

    public float getStaminaSpeed() {
        return this.staminaSpeed;
    }

    public float getStartFrame() {
        return this.startFrame;
    }

    public float getEndFrame() {
        return this.endFrame;
    }

    public float getAnimationSpeed() {
        return this.animationSpeed;
    }

    public float getAnimationTime() {
        return this.animationTime;
    }

    public int getActionTime() {
        return this.actionTime;
    }

    public boolean isEvadeEmergency() {
        return this.isEvadeEmergency;
    }

    public int getAddDv() {
        return this.addDv;
    }

    public int getAddPv() {
        return this.addPv;
    }

    public int getAddDvForMonster() {
        return this.addDvForMonster;
    }

    public int getAddPvForMonster() {
        return this.addPvForMonster;
    }
}
