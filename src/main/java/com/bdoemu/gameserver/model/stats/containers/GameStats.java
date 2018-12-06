package com.bdoemu.gameserver.model.stats.containers;

import com.bdoemu.gameserver.dataholders.CreatureData;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.stats.*;
import com.bdoemu.gameserver.model.stats.elements.BaseElement;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;

public abstract class GameStats<T extends Creature> {
    protected T owner;
    protected StatContainer container;

    public GameStats(final T owner) {
        this.owner = owner;
        this.container = new StatContainer();
    }

    public Stat getStat(final StatEnum type) {
        Stat stat = this.container.getStat(type);
        if (stat == null) {
            BaseElement base = this.getBaseElementForStat(type);
            if (base == null) {
                base = new BaseElement(0.0f);
            }
            stat = type.newStatInstance(this.owner);
            stat.setBase(base);
            this.addStat(type, stat);
        } else {
            final BaseElement base = this.getBaseElementForStat(type);
            if (base != null && stat.getBase() != base) {
                stat.setBase(base);
            }
        }
        return stat;
    }

    public BaseElement getBaseElementForStat(final StatEnum type) {
        return CreatureData.getInstance().getTemplate(this.owner.getCreatureId()).getGameStatsTemplate().getBaseElement(type);
    }

    public void addStat(final StatEnum type, final Stat stat) {
        this.container.addStat(type, stat);
    }

    public Stat getAutoFishingTimeReduce() {
        return this.getStat(StatEnum.AutoFishingTimeReduce);
    }

    public Stat getFallDamage() {
        return this.getStat(StatEnum.FallDamage);
    }

    public HpStat getHp() {
        return (HpStat) this.getStat(StatEnum.HP);
    }

    public MpStat getMp() {
        return (MpStat) this.getStat(StatEnum.MP);
    }

    public Stat getHPRegen() {
        return this.getStat(StatEnum.HPRegen);
    }

    public Stat getMPRegen() {
        return this.getStat(StatEnum.MPRegen);
    }

    public Stat getDDV() {
        return this.getStat(StatEnum.DDV);
    }

    public Stat getRDV() {
        return this.getStat(StatEnum.RDV);
    }

    public Stat getMDV() {
        return this.getStat(StatEnum.MDV);
    }

    public Stat getDHIT() {
        return this.getStat(StatEnum.DHIT);
    }

    public Stat getRHIT() {
        return this.getStat(StatEnum.RHIT);
    }

    public Stat getMHIT() {
        return this.getStat(StatEnum.MHIT);
    }

    public Stat getDDD() {
        return this.getStat(StatEnum.DDD);
    }

    public Stat getRDD() {
        return this.getStat(StatEnum.RDD);
    }

    public Stat getMDD() {
        return this.getStat(StatEnum.MDD);
    }

    public Stat getDPV() {
        return this.getStat(StatEnum.DPV);
    }

    public Stat getRPV() {
        return this.getStat(StatEnum.RPV);
    }

    public Stat getMPV() {
        return this.getStat(StatEnum.MPV);
    }

    public StaminaStat getStamina() {
        return (StaminaStat) this.getStat(StatEnum.Stamina);
    }

    public Stat getStr() {
        return this.getStat(StatEnum.STR);
    }

    public Stat getVit() {
        return this.getStat(StatEnum.VIT);
    }

    public Stat getWis() {
        return this.getStat(StatEnum.WIS);
    }

    public Stat getInt() {
        return this.getStat(StatEnum.INT);
    }

    public Stat getDex() {
        return this.getStat(StatEnum.DEX);
    }

    public WeightStat getWeight() {
        return (WeightStat) this.getStat(StatEnum.Weight);
    }

    public Stat getIntimacyRate() {
        return this.getStat(StatEnum.IntimacyRate);
    }

    public Stat getHorseEXP() {
        return this.getStat(StatEnum.HorseEXP);
    }

    public Stat getBreathGauge() {
        return this.getStat(StatEnum.BreathGage);
    }

    public Stat getJumpHeight() {
        return this.getStat(StatEnum.JumpHeight);
    }

    public Stat getVisionRange() {
        return this.getStat(StatEnum.VisionRange);
    }

    public Stat getSwimmingStat() {
        return this.getStat(StatEnum.Swimming);
    }

    public MoveSpeedStat getMoveSpeedRate() {
        return (MoveSpeedStat) this.getStat(StatEnum.MoveSpeedRate);
    }

    public AttackSpeedStat getAttackSpeedRate() {
        return (AttackSpeedStat) this.getStat(StatEnum.AttackSpeedRate);
    }

    public Stat getBackAttackRate() {
        return this.getStat(StatEnum.BackAttackRate);
    }

    public Stat getCounterAttackRate() {
        return this.getStat(StatEnum.CounterAttackRate);
    }

    public Stat getCriticalAttackRate() {
        return this.getStat(StatEnum.CriticalAttackRate);
    }

    public Stat getDownAttackRate() {
        return this.getStat(StatEnum.DownAttackRate);
    }

    public Stat getAirAttackRate() {
        return this.getStat(StatEnum.AirAttackRate);
    }

    public Stat getSpeedAttackRate() {
        return this.getStat(StatEnum.SpeedAttackRate);
    }

    public AdrenalinStat getAdrenalin() {
        return (AdrenalinStat) this.getStat(StatEnum.Adrenalin);
    }

    public CastingSpeedStat getCastingSpeedRate() {
        return (CastingSpeedStat) this.getStat(StatEnum.CastingSpeedRate);
    }

    public CriticalStat getCriticalRate() {
        return (CriticalStat) this.getStat(StatEnum.CriticalRate);
    }

    public DropItemStat getDropItemLuck() {
        return (DropItemStat) this.getStat(StatEnum.DropRate);
    }

    public Stat getDoubleDropItemLuck() {
        return this.getStat(StatEnum.DoubleDropRate);
    }

    public FishingStat getFishingLuck() {
        return (FishingStat) this.getStat(StatEnum.FishingLuck);
    }

    public CollectionStat getCollectionLuck() {
        return (CollectionStat) this.getStat(StatEnum.CollectionLuck);
    }

    public Stat getResistStun() {
        return this.getStat(StatEnum.ResistStun);
    }

    public Stat getResistHorror() {
        return this.getStat(StatEnum.ResistHorror);
    }

    public Stat getResistCapture() {
        return this.getStat(StatEnum.ResistCapture);
    }

    public Stat getResistKnockBack() {
        return this.getStat(StatEnum.ResistKnockBack);
    }

    public Stat getResistKnockDown() {
        return this.getStat(StatEnum.ResistKnockDown);
    }

    public Stat getResistStandDown() {
        return this.getStat(StatEnum.ResistStandDown);
    }

    public Stat getResistGuardCrush() {
        return this.getStat(StatEnum.ResistGuardCrush);
    }

    public Stat getResistRigid() {
        return this.getStat(StatEnum.ResistRigid);
    }

    public Stat getResistBound() {
        return this.getStat(StatEnum.ResistBound);
    }

    public StunGaugeStat getStunGauge() {
        return (StunGaugeStat) this.getStat(StatEnum.StunGauge);
    }

    public Stat getMagicalShield() {
        return this.getStat(StatEnum.MagicalShield);
    }

    public Stat getExpRate() {
        return this.getStat(StatEnum.EXP);
    }

    public Stat getTendencyEXP() {
        return this.getStat(StatEnum.TendencyEXP);
    }

    public Stat getSkillExpRate() {
        return this.getStat(StatEnum.SkillEXP);
    }

    public Stat getLifeExpRate() {
        return this.getStat(StatEnum.LifeEXP);
    }

    public Stat getDeathPenaltyStat() {
        return this.getStat(StatEnum.DeathPenalty);
    }

    public Stat getEnduranceReduceStat() {
        return this.getStat(StatEnum.EnduranceReduce);
    }

    public Stat getAquireKnowledgeRateStat() {
        return this.getStat(StatEnum.AquireKnowledgeRate);
    }

    public Stat getAquireHighKnowledgeRateStat() {
        return this.getStat(StatEnum.AquireHighKnowledgeRate);
    }

    public Stat getAlchemyDecreaseTimeStat() {
        return this.getStat(StatEnum.AlchemyDecreaseTime);
    }

    public Stat getCookingDecreaseTimeStat() {
        return this.getStat(StatEnum.CookingDecreaseTime);
    }

    public Stat getManufactureDecreaseTimeStat() {
        return this.getStat(StatEnum.ManufactureDecreaseTime);
    }

    public Stat getDropRateCollectStat() {
        return this.getStat(StatEnum.DropRateCollect);
    }

    public Stat getAccelerationRate() {
        return this.getStat(StatEnum.AccelerationRate);
    }

    public Stat getMaxMoveSpeedRate() {
        return this.getStat(StatEnum.MaxMoveSpeedRate);
    }

    public Stat getCorneringSpeedRate() {
        return this.getStat(StatEnum.CorneringSpeedRate);
    }

    public Stat getBrakeSpeedRate() {
        return this.getStat(StatEnum.BrakeSpeedRate);
    }

    public SubResourcePointStat getSubResourcePointStat() {
        return (SubResourcePointStat) this.getStat(StatEnum.SubResourcePoint);
    }

    public Stat getWPRegenStat() {
        return this.getStat(StatEnum.WPRegen);
    }

    public Stat getHumanAttackDamageStat() {
        return this.getStat(StatEnum.HumanAttackDamageStat);
    }

    public Stat getAinAttackDamageStat() {
        return this.getStat(StatEnum.AinAttackDamageStat);
    }

    public Stat getEtcAttackDamageStat() {
        return this.getStat(StatEnum.EtcAttackDamageStat);
    }

    public Stat getBossAttackDamageStat() {
        return this.getStat(StatEnum.BossAttackDamageStat);
    }

    public Stat getReptileAttackDamageStat() {
        return this.getStat(StatEnum.ReptileAttackDamageStat);
    }

    public Stat getUntribeAttackDamageStat() {
        return this.getStat(StatEnum.UntribeAttackDamageStat);
    }

    public Stat getHuntingAttackDamageStat() {
        return this.getStat(StatEnum.HuntingAttackDamageStat);
    }

    public Stat getElephantAttackDamageStat() {
        return this.getStat(StatEnum.ElephantAttackDamageStat);
    }

    public Stat getCannonAttackDamageStat() {
        return this.getStat(StatEnum.CannonAttackDamageStat);
    }

    public Stat getSiegeAttackDamageStat() {
        return this.getStat(StatEnum.SiegeAttackDamageStat);
    }

    public Stat getTameEfficiency() {
        return this.getStat(StatEnum.TameEfficiency);
    }
}