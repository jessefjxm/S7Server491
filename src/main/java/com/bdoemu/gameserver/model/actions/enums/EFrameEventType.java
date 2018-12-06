package com.bdoemu.gameserver.model.actions.enums;

import com.bdoemu.gameserver.model.actions.templates.frameevents.*;

public enum EFrameEventType {
    Attack {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventAttack(this);
        }
    },
    AttackSkill {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventAttackSkill(this);
        }
    },
    AttackArrow {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventAttackArrow(this);
        }
    },
    AttackArrowSkill {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventAttackArrowSkill(this);
        }
    },
    AttackCatch {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventAttackCatch(this);
        }
    },
    AttackThrow {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventAttackThrow(this);
        }
    },
    Effect {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    EffectGround {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    EffectSwitch {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    EffectSwitchChain {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    EffectSwitchTarget {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    EffectSwitchTargetAllOff {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    EffectSwitchChange {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    EffectSwitchAllOff {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    CharacterEffect {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    GrassShake {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    CameraShake {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    CameraDirShake {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    CameraMotion {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    OrderToSummon {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    OrderToHorse {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    OrderToRideVehicle {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    ShowBubbleBox {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Delay {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    AnimationSpeed {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Speed {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventSpeed(this);
        }
    },
    Rotate {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventRotate(this);
        }
    },
    Variable {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SwordTrail {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    BounceBack {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    PushBack {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    PickItemDropList {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    MeshOn {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    MeshOff {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Jump {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    WeaponIn {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    WeaponOut {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    WeaponMeshOn {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    WeaponMeshOff {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    PointBlur {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    ChromaticBlur {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Blur {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SlowMotion {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    ColorByPass {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    ColorBalance {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Glitter {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    BoneScale {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundKill {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundBase {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Sound {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Voice {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundExternal {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundWeaponSwing {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundFootstep {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundJumpUp {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundJumpLand {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundMovement {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundWeaponIn {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundWeaponOut {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundImpact {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundCommon {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundBodyfall {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundMovementStop {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundWeaponReady {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SoundPassingGrass {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    FaceAni {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    FaceAniWeight {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    FaceMorphWeight {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    KeepAimOn {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    KeepAimOff {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Collect {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventCollect(this);
        }
    },
    Product {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventProduct(this);
        }
    },
    ReTargetPosition {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventReTargetPosition(this);
        }
    },
    ChangeSpeedToTarget {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    FindTarget {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SetFindedTarget {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SetCameraFov {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    ResetCameraFov {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SetCameraPos {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    ResetCameraPos {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    RiderIK {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    AdditiveAnim {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    LookAt {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    StartCameraWave {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    StopCameraWave {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    MovePos {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventMovePos(this);
        }
    },
    VaryStat {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventVaryStat(this);
        }
    },
    VaryVehicleStat {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventVaryVehicleStat(this);
        }
    },
    TowerAim {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Fishing {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventFishing(this);
        }
    },
    SetHitPartHp {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventSetHitPartAvoid(this);
        }
    },
    VaryHitPartHp {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventVaryHitPartHp(this);
        }
    },
    SetHitPartAvoid {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventSetHitPartAvoid(this);
        }
    },
    BuildTent {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventBuildTent(this);
        }
    },
    TakeDownTent {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventTakeDownTent(this);
        }
    },
    RollRateDice {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Manufacture {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventManufacture(this);
        }
    },
    HideCharacter {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    RevealCharacter {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    HeadLineMessage {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    RepairItem {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventRepairItem(this);
        }
    },
    Campfire {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventCampfire(this);
        }
    },
    StartSitDownIk {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    EndSitDownIk {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Greet {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventGreet(this);
        }
    },
    Steal {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventSteal(this);
        }
    },
    StartCasting {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    CancelCasting {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    ReadBook {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventReadBook(this);
        }
    },
    CallLuaEvent {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Decal {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    RetryActionMoveReady {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    RetryActionMove {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventRetryActionMove(this);
        }
    },
    WeaponType {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventWeaponType(this);
        }
    },
    FishingFloatIK {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    AdditionalDamageType {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    UsePetKill {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    FlashBang {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Lighten {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Weather {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Thunder {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    CharacterParticle {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    SetBerserk {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventSetBerserk(this);
        }
    },
    OperateCommonGauge {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    TakeDownCannon {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventTakeDownCannon(this);
        }
    },
    SummonCharacter {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventSummonCharacter(this);
        }
    },
    CannonAngle {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    PoseFemale {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    CameraRotate {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    CommandRepair {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventCommandRepair(this);
        }
    },
    ActiveSkill {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventActiveSkill(this);
        }
    },
    BuildingUpgrade {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventBuildingUpgrade(this);
        }
    },
    AttackBounds {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    PredictCannonAtkPos {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Banquet {
        @Override
        FrameEvent newFrameEvent() {
            return new FrameEventBanquet(this);
        }
    },
    GenerateWave {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    WeaponBoneFlag {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    ActionTimer {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    ModelAnimation {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Observer {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    Texture {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    },
    ResetTargetList {
        @Override
        FrameEvent newFrameEvent() {
            return null;
        }
    };

    public static EFrameEventType valueof(final int id) {
        if (id < 0 || id > values().length - 1) {
            throw new IllegalArgumentException("Unknown FrameEventType:" + id);
        }
        return values()[id];
    }

    public int getId() {
        return this.ordinal();
    }

    abstract FrameEvent newFrameEvent();

    public FrameEvent getNewFrameEventInstance() {
        return this.newFrameEvent();
    }

    public boolean isSpeed() {
        return this == EFrameEventType.Speed;
    }
}
