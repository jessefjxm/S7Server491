package com.bdoemu.gameserver.model.stats.enums;

import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.stats.*;

public enum StatEnum {
    HP {
        @Override
        Stat newInstance(final Creature owner) {
            return new HpStat(owner);
        }
    },
    MP {
        @Override
        Stat newInstance(final Creature owner) {
            return new MpStat(owner);
        }
    },
    HPRegen {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    MPRegen {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    Weight {
        @Override
        Stat newInstance(final Creature owner) {
            return new WeightStat(owner);
        }
    },
    AddedDDD {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    AddedRDD {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    AddedMDD {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    DDD {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    DHIT {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    DDV {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    HDDV {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    DPV {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    HDPV {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    RDD {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    RHIT {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    RDV {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    HRDV {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    RPV {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    HRPV {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    MDD {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    MHIT {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    MDV {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    HMDV {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    MPV {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    HMPV {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    STR {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    VIT {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    WIS {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    INT {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    DEX {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    FallDamage {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    Stamina {
        @Override
        Stat newInstance(final Creature owner) {
            return new StaminaStat(owner);
        }
    },
    StunGauge {
        @Override
        Stat newInstance(final Creature owner) {
            return new StunGaugeStat(owner);
        }
    },
    BackAttackRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    DownAttackRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    AirAttackRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    CriticalAttackRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    SpeedAttackRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    CounterAttackRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    MoveSpeedRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new MoveSpeedStat(owner);
        }
    },
    AttackSpeedRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new AttackSpeedStat(owner);
        }
    },
    CastingSpeedRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new CastingSpeedStat(owner);
        }
    },
    CriticalRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new CriticalStat(owner);
        }
    },
    IntimacyRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    DropRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new DropItemStat(owner);
        }
    },
    DoubleDropRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    FishingLuck {
        @Override
        Stat newInstance(final Creature owner) {
            return new FishingStat(owner);
        }
    },
    CollectionLuck {
        @Override
        Stat newInstance(final Creature owner) {
            return new CollectionStat(owner);
        }
    },
    ResistStun {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    ResistHorror {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    ResistCapture {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    ResistKnockBack {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    ResistKnockDown {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    ResistStandDown {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    ResistGuardCrush {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    ResistRigid {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    ResistBound {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    JumpHeight {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    VisionRange {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    Swimming {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    EXP {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    TendencyEXP {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    SkillEXP {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    LifeEXP {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    HorseEXP {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    BreathGage {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    Adrenalin {
        @Override
        Stat newInstance(final Creature owner) {
            return new AdrenalinStat(owner);
        }
    },
    AccelerationRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    MaxMoveSpeedRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    CorneringSpeedRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    SubResourcePoint {
        @Override
        Stat newInstance(final Creature owner) {
            return new SubResourcePointStat(owner);
        }
    },
    BrakeSpeedRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    HumanAttackDamageStat {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    AinAttackDamageStat {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    EtcAttackDamageStat {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    BossAttackDamageStat {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    ReptileAttackDamageStat {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    UntribeAttackDamageStat {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    HuntingAttackDamageStat {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    ElephantAttackDamageStat {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    CannonAttackDamageStat {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    SiegeAttackDamageStat {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    AutoFishingTimeReduce {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    MagicalShield {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    WPRegen {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    DeathPenalty {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    EnduranceReduce {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    AquireKnowledgeRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    AquireHighKnowledgeRate {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    AlchemyDecreaseTime {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    CookingDecreaseTime {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    ManufactureDecreaseTime {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    DropRateCollect {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    VersusMonsterStat {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    HeatstrokeResistance {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    CatchFishChance {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    },
    StunPoints {
        @Override
        Stat newInstance(final Creature owner) {
            return new StunPointsStat(owner);
        }
    },
    TameEfficiency {
        @Override
        Stat newInstance(final Creature owner) {
            return new Stat(owner);
        }
    };

    abstract Stat newInstance(final Creature p0);

    public Stat newStatInstance(final Creature owner) {
        return this.newInstance(owner);
    }
}