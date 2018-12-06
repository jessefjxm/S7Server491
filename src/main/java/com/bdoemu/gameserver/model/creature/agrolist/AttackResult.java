package com.bdoemu.gameserver.model.creature.agrolist;

import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.core.configs.BattleOptionConfig;
import com.bdoemu.gameserver.dataholders.ClassBalanceData;
import com.bdoemu.gameserver.model.actions.enums.EAttachTerrainType;
import com.bdoemu.gameserver.model.actions.enums.EWeaponType;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.agrolist.enums.EAttackType;
import com.bdoemu.gameserver.model.creature.agrolist.enums.EDmgType;
import com.bdoemu.gameserver.model.creature.enums.EMainAttackType;
import com.bdoemu.gameserver.model.creature.player.Player;
import com.bdoemu.gameserver.model.creature.player.duel.PvpMatch;
import com.bdoemu.gameserver.model.creature.player.enums.EEquipSlot;
import com.bdoemu.gameserver.model.items.Item;
import com.bdoemu.gameserver.model.skills.templates.SkillT;
import com.bdoemu.gameserver.model.stats.elements.Element;
import com.bdoemu.gameserver.model.stats.enums.StatEnum;
import com.bdoemu.gameserver.model.world.Location;
import com.bdoemu.gameserver.service.GameTimeService;
import com.bdoemu.gameserver.service.LocalWarService;
import com.bdoemu.gameserver.utils.MathUtils;
import com.bdoemu.gameserver.utils.PARand;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Nullbyte
 *         Damage (405.000000) =
 *         Character_DD(2D5+25+45.000000, 48.000000)
 *         + Weapon_DD(1D5+58+113.000000, 115.000000)
 *         - PV(9.000000)
 *         + Additional_Damage(139.000000)
 *
 *
 *         HitRate(254.500000) =
 *         (
 *         Hit(751.000000)
 *         - 	(
 *         DV(1.000000)
 *         )
 *         + 67.0f
 *         ) / 100.0f
 */
public class AttackResult {
    /**
     * Target Location
     */
    private Location targetLoc;

    /**
     * Attacker Location
     */
    private Location attackerLoc;

    /**
     * Target creature
     */
    private Creature target;

    /**
     * Attacker Creature
     */
    private Creature attacker;

    /**
     * Skill that was used on this attack
     */
    private SkillT skillT;

    /**
     * Attack Type of this attack
     */
    private EAttackType attackType;

    /**
     * Damage type of this attack
     */
    private EDmgType dmgType;

    /**
     * Total damage dealt to creature
     */
    private double dmg;

    /**
     * Attack result constructor.
     *
     * @param skillT   SkillTemplate that is being exexcuted.
     * @param attacker Attackee.
     * @param target   Attackee's Target.
     */
    public AttackResult(final SkillT skillT, final Creature attacker, final Creature target) {
        this.attackType = EAttackType.FrontAttack;
        this.dmgType = EDmgType.Default;
        this.skillT = skillT;
        this.attackerLoc = attacker.getLocation();
        this.targetLoc = target.getLocation();
        this.attacker = attacker;
        this.target = target;
    }

    /**
     * Checks if player is attacking within 180 FoV.
     *
     * @param attackerLoc Attacker Location
     * @param targetLoc   Target Location
     * @return true if player attacked in front or side, false otherwise.
     */
    public static boolean isFrontSideAttack(final Location attackerLoc, final Location targetLoc) {
        final double distance = MathUtils.getDistance(attackerLoc, targetLoc);
        final double vectorHeading = Math.toDegrees(Math.atan2((targetLoc.getY() - attackerLoc.getY()) / distance, (targetLoc.getX() - attackerLoc.getX()) / distance));
        final double targetHeading = Math.toDegrees(Math.atan2(targetLoc.getSin(), targetLoc.getCos()));
        final int result = (int) Math.round((targetHeading < 0.0) ? (360.0 + targetHeading) : targetHeading) - (int) Math.round((vectorHeading < 0.0) ? (360.0 + vectorHeading) : vectorHeading);
        return result < -90 || result > 90;
    }

    /**
     * Damage Type of Attack Result
     *
     * @return EDmgType
     */
    public EDmgType getDmgType() {
        return dmgType;
    }

    /**
     * Checks if target is an enemy.
     *
     * @return true if enemy, false otherwise.
     */
    public boolean isEnemy() {
        return attacker.isEnemy(target);
    }

    /**
     * Returns an attacker.
     *
     * @return Creature
     * @see Creature
     */
    public Creature getAttacker() {
        return attacker;
    }

    /**
     * Returns target location.
     *
     * @return Location
     * @see Location
     */
    public Location getTargetLoc() {
        return targetLoc;
    }

    /**
     * Returns attackers location.
     *
     * @return Location
     * @see Location
     */
    public Location getAttackerLoc() {
        return attackerLoc;
    }

    /**
     * Returns target.
     *
     * @return Creature
     * @see Creature
     */
    public Creature getTarget() {
        return target;
    }

    /**
     * Returns attack type
     *
     * @return EAttackType
     * @see EAttackType
     */
    public EAttackType getAttackType() {
        return attackType;
    }

    /**
     * Returns calculated diced damage including equipment.
     *
     * @param attackType Attack type of dice damage
     * @return double
     */
    private double getDicedDamage(EMainAttackType attackType) {
        // If attacker is not player, just use the Direct values.
        if (!attacker.isPlayer())
            attackType = EMainAttackType.DDD;

        StatEnum statEnumType =
                attackType == EMainAttackType.DDD ? StatEnum.DDD : (
                        attackType == EMainAttackType.RDD ? StatEnum.RDD :
                                StatEnum.MDD);

        double baseDmg = 0.0;
        if (getAttacker().isPlayer() && !getAttacker().getActionStorage().getWeaponType().isNone()) {
            Player attacker = (Player) getAttacker();
            final Item mainWeaponItem = attacker.getEquipments().getItem(EEquipSlot.rightHand.getId());
            if (mainWeaponItem != null) {
                baseDmg += rollDice(mainWeaponItem.getEnchantTemplate().getStats().get(statEnumType));
                if (attacker.getActionStorage().getWeaponType().isAwakeningWeapon()) {
                    final Item awakeningWeaponItem = attacker.getEquipments().getItem(EEquipSlot.awakenWeapon.getId());
                    if (awakeningWeaponItem != null) {
                        baseDmg *= 0.3;
                        baseDmg += 0.7 * rollDice(awakeningWeaponItem.getEnchantTemplate().getStats().get(statEnumType));
                    }
                }
            }
        }

        return baseDmg
                + rollDice(attacker.getTemplate().getGameStatsTemplate().getBaseElement(statEnumType))
                + rollDiceForElements(attacker.getGameStats().getStat(statEnumType).getElements());
    }

    /**
     * Returns HIT based on attack type.
     *
     * @param attackType HIT type
     * @return int
     */
    private double getHIT(EMainAttackType attackType) {
        // If attacker is not player, just use the Direct values.
        if (!attacker.isPlayer())
            attackType = EMainAttackType.DDD;

        return attacker.getGameStats().getStat(
                attackType == EMainAttackType.DDD ? StatEnum.DHIT : (
                        attackType == EMainAttackType.RDD ? StatEnum.RHIT :
                                StatEnum.MHIT)
        ).getIntMaxValue() + (skillT != null ? skillT.getVariedHit() : 0);
    }

    /**
     * Returns defense (DV)
     *
     * @param attackType Attack Type
     * @return int
     */
    private double getDV(EMainAttackType attackType) {
        // If attacker is not player, just use the Direct values.
        if (!attacker.isPlayer())
            attackType = EMainAttackType.DDD;

        double dv = 0;

        if (isAttackerPlayerOrSummon() && !target.isPlayer() && target.getLevel() - attacker.getLevel() > 0)
            dv += Math.pow(target.getLevel() - attacker.getLevel(), 5);

        switch (attacker.getActionStorage().getWeaponType()) {
            case AwakeningWeapon:
                dv += target.getGameStats().getStat(attackType == EMainAttackType.DDD ? StatEnum.HDDV : (attackType == EMainAttackType.RDD ? StatEnum.HRDV : StatEnum.HMDV)).getIntMaxValue();
            case None: // Do nothing and just add basic.
            case Weapon:
                dv += target.getGameStats().getStat(attackType == EMainAttackType.DDD ? StatEnum.DDV : (attackType == EMainAttackType.RDD ? StatEnum.RDV : StatEnum.MDV)).getIntMaxValue();
                break;
        }
        return dv > 0.0 ? dv : 0.0;
    }

    /**
     * Returns evasion (PV)
     *
     * @param attackType Attack Type
     * @return int
     */
    private int getPV(EMainAttackType attackType) {
        // If attacker is not player, just use the Direct values.
        if (!attacker.isPlayer())
            attackType = EMainAttackType.DDD;

        int pv = 0;
        switch (attacker.getActionStorage().getWeaponType()) {
            case AwakeningWeapon:
                pv += target.getGameStats().getStat(attackType == EMainAttackType.DDD ? StatEnum.HDPV : (attackType == EMainAttackType.RDD ? StatEnum.HRPV : StatEnum.HMPV)).getIntMaxValue();
            case None: // Do nothing and just add basic.
            case Weapon:
                pv += target.getGameStats().getStat(attackType == EMainAttackType.DDD ? StatEnum.DPV : (attackType == EMainAttackType.RDD ? StatEnum.RPV : StatEnum.MPV)).getIntMaxValue();
                break;
        }
        return target.getGameStats().getStat(attackType == EMainAttackType.DDD ? StatEnum.DPV : (attackType == EMainAttackType.RDD ? StatEnum.RPV : StatEnum.MPV)).getIntMaxValue();
    }

    /**
     * Returns added attack damage based on attack type.
     *
     * @param attackType Attack Type of added attack damage.
     * @return int
     */
    private int getAddedDamage(EMainAttackType attackType) {
        // If attacker is not player, just use the Direct values.
        if (!attacker.isPlayer())
            attackType = EMainAttackType.DDD;

        return attacker.getGameStats().getStat(
                attackType == EMainAttackType.DDD ? StatEnum.AddedDDD : (
                        attackType == EMainAttackType.RDD ? StatEnum.AddedRDD :
                                StatEnum.AddedMDD)
        ).getIntMaxValue();
    }

    /**
     * Depending on tribe, the damage will differ.
     *
     * @return Added Tribe Damage
     */
    private int getTribeAddedDamage() {
        switch (target.getTemplate().getTribeType()) {
            case Human:
                return target.getGameStats().getHumanAttackDamageStat().getIntMaxValue();
            case Ain:
                return target.getGameStats().getAinAttackDamageStat().getIntMaxValue();
            case Etc:
                return target.getGameStats().getEtcAttackDamageStat().getIntMaxValue();
            case Boss:
                return target.getGameStats().getBossAttackDamageStat().getIntMaxValue();
            case Reptile:
                return target.getGameStats().getReptileAttackDamageStat().getIntMaxValue();
            case Untribe:
                return target.getGameStats().getUntribeAttackDamageStat().getIntMaxValue();
            case Hunting:
                return target.getGameStats().getHuntingAttackDamageStat().getIntMaxValue();
            case Elephant:
                return target.getGameStats().getElephantAttackDamageStat().getIntMaxValue();
            case Cannon:
                return target.getGameStats().getCannonAttackDamageStat().getIntMaxValue();
            case Siege:
                return target.getGameStats().getSiegeAttackDamageStat().getIntMaxValue();
            default:
                return 0;
        }
    }

    private double getClassBalanceMultiplier() {
        if (isAttackerPlayerOrSummon()) {
            if (target.isPlayer()) {
                Player owner = !attacker.isPlayer() && attacker.getOwner() != null
                        ? (attacker.getOwner().isPlayer()
                        ? (Player) attacker.getOwner()
                        : null
                ) : (attacker.isPlayer()
                        ? (Player) attacker
                        : null
                );

                if (owner != null) {
                    float classBalanceRate = ClassBalanceData.getInstance().getClassBalance(owner.getClassType(), ((Player) target).getClassType());
                    if (classBalanceRate <= 0)
                        classBalanceRate = 0.25f;
                    return classBalanceRate;
                }
            }
        }
        return 1f;
    }

    private boolean isAttackerPlayerOrSummon() {
        return attacker.isPlayer() || attacker.getOwner() != null && attacker.getOwner().isPlayer();
    }

    private boolean isTargetPlayerOrSummon() {
        return target.isPlayer() || target.getOwner() != null && target.getOwner().isPlayer();
    }

    private double normalizePercentage(int percentage) {
        if (percentage < 1)
            return 1;
        return percentage / 1_000_000.0;
    }

    /**
     * Rolls all elements
     *
     * @param elements Elements to roll
     * @return int
     */
    private int rollDiceForElements(final List<Element> elements) {
        int value = 0;
        for (Element el : elements) {
            for (int i = 0; i < el.getDValue()[0]; ++i)
                value += ThreadLocalRandom.current().nextInt(0, el.getDValue()[1] + 1);

            if (el.getDValue().length > 2)
                value += el.getDValue()[2];
        }
        return value;
    }

    /**
     * Rolls a dice
     *
     * @param el Element of dice.
     * @return int
     */
    private int rollDice(final Element el) {
        int value = 0;
        for (int i = 0; i < el.getDValue()[0]; ++i)
            value += ThreadLocalRandom.current().nextInt(0, el.getDValue()[1] + 1);

        if (el.getDValue().length > 2)
            value += el.getDValue()[2];
        return value;
    }

    /**
     * Calculates and applies damage to creature.
     *
     * @param dmgPercentage      Damage Percentage
     * @param criticalPercentage Critical Percentage
     * @param mainAttackType     Attack Type of Skill or Hit.
     */
    public boolean applyAttack(int dmgPercentage, int criticalPercentage, final EMainAttackType mainAttackType) {
        //if (isAttackerPlayerOrSummon() || target.isPlayer())
        //	System.out.println("\n" + attacker.getName() + " vs " + target.getName() + " [" + (isAttackerPlayerOrSummon() ? "PlayerOrSummon" : "Monster")+ "]");

        //////////////////////////////// Accuracy Calculation
        // $chanceToHit = $HIT / ( $HIT + (($DDV/4)^0.9));
        //double accuracyPercentage = (getHIT(mainAttackType) / 3 - getDV(mainAttackType) / 4);
        double accuracyPercentage = getHIT(mainAttackType)
                / (getHIT(mainAttackType)
                + (Math.pow(getDV(mainAttackType) / 4, BattleOptionConfig.ATTACK_HIT_RATE)));

        if (accuracyPercentage < 0.05)
            accuracyPercentage = 0.05;
        if (accuracyPercentage > 0.95)
            accuracyPercentage = 0.95;

        if (accuracyPercentage <= ThreadLocalRandom.current().nextDouble() || Double.isNaN(accuracyPercentage) || Double.isInfinite(accuracyPercentage)) {
            if (attacker.isPlayer() && ((Player) attacker).isDamageDebugEnabled())
                ((Player) attacker).sendMessage("Attack missed to " + target.getName() + ". Acc: " + (Math.round(accuracyPercentage * 100 * 100) / 100.0) + "%", true);

            if (target.isPlayer() && ((Player) target).isDamageDebugEnabled())
                ((Player) target).sendMessage("Attack missed from " + attacker.getName() + ". Acc: " + (Math.round(accuracyPercentage * 100 * 100) / 100.0) + "%", true);

            this.dmgType = EDmgType.Evasion;
            return true;
        }

        //////////////////////////////// Damage Type Detection
        // Detect Back Attack
        // When your opponent has their flank exposed to you, damage is increased by 1.5 times.
        final boolean isFrontSideAttack = isFrontSideAttack(attackerLoc, targetLoc);
        if (!target.getTemplate().isFixed() && !isFrontSideAttack
                && MathUtils.getDistance(this.attackerLoc, this.targetLoc) <= this.target.getTemplate().getBodySize() + this.attacker.getTemplate().getBodySize() + 170)
            attackType = EAttackType.BackAttack;
        else
            // Detect Counter Attack
            // Skills listed with the "Counter Attack" ability do an additional 2.0 times damage if attacks land while opponent is casting.
            // TODO

            // Detect Air Attack (Need a lot of test!)
            // Attacks hitting opponents while knocked into the air do an additional 2.5 times damage.
            if (target.getAi().getBehavior().isKnockback() && target.getActionStorage().getActionChartActionT().getAttachTerrainType() == EAttachTerrainType.Air)
                attackType = EAttackType.AirAttack;
            else
                // Detect Down Attack
                // Attacks on opponents that are knocked down inflict an additional 1.5 times damage.
                if (target.getAi().getState() == 0xAB2D9772L /*KnockDown_State*/
                        || target.getAi().getState() == 0x2F6688E1L /*KnockDown_CanRoll*/
                        || target.getAi().getState() == 0xAD9D0DFL /*Damage_DownSmash*/)
                    attackType = EAttackType.DownAttack;
                    // Detect Speed Attack
                    // Skills listed with the "Speed Attack" ability do an additional 1.5 times damage to opponents as they charge at you.
                    // TODO EApplySpeedBuffType.Attack?
                else // FrontAttack
                    attackType = EAttackType.FrontAttack;

        double attackTypeMultiplier = 1.0;
        switch (attackType) {
            case BackAttack:
                attackTypeMultiplier = normalizePercentage(attacker.getGameStats().getBackAttackRate().getIntMaxValue() + BattleOptionConfig.BACK_ATTACK_DAMAGE_RATE);
                break;
            case CounterAttack:
                attackTypeMultiplier = normalizePercentage(attacker.getGameStats().getCounterAttackRate().getIntMaxValue() + BattleOptionConfig.COUNTER_ATTACK_DAMAGE_RATE);
                break;
            case DownAttack:
                attackTypeMultiplier = normalizePercentage(attacker.getGameStats().getDownAttackRate().getIntMaxValue() + BattleOptionConfig.DOWN_ATTACK_DAMAGE_RATE);
                break;
            case SpeedAttack:
                attackTypeMultiplier = normalizePercentage(attacker.getGameStats().getSpeedAttackRate().getIntMaxValue() + BattleOptionConfig.SPEED_ATTACK_DAMAGE_RATE);
                break;
            case AirAttack:
                attackTypeMultiplier = normalizePercentage(attacker.getGameStats().getAirAttackRate().getIntMaxValue() + BattleOptionConfig.AIR_ATTACK_DAMAGE_RATE);
                break;
        }
        //////////////////////////////// Critical Damage
        double criticalMultiplier = 1.0;
        criticalPercentage += attacker.getGameStats().getCriticalRate().getCriticalRate();
        if (criticalPercentage > 0 && Rnd.get(1000000) <= criticalPercentage) {
            dmgType = EDmgType.Critical;
            criticalMultiplier = normalizePercentage(this.attacker.getGameStats().getCriticalAttackRate().getIntMaxValue() + BattleOptionConfig.CRITICAL_DAMAGE_RATE);
        }

        //////////////////////////////// Damage Calculation
        // Damage (405.000000) = Character_DD(2D5+25+45.000000, 48.000000) + Weapon_DD(1D5+58+113.000000, 115.000000) - PV(9.000000) + Additional_Damage(139.000000)
        // Damage 			   = %factor(skill) x DD x log10(DD / (PV + 1) + 1) ^ 0.9 + Additional_Damage
        double attackDamage = getDicedDamage(mainAttackType);
        double pv = getPV(mainAttackType);

        if (attackDamage < 1) {
            dmg = 0;
            this.dmgType = EDmgType.Evasion;
            return true;
        }

        double raw;
        // Monster vs Player
        if (!isAttackerPlayerOrSummon() && target.isPlayer()) {
            attackDamage += getAddedDamage(mainAttackType);
            attackDamage += getTribeAddedDamage();
            attackDamage *= normalizePercentage(dmgPercentage);
            attackDamage *= attackTypeMultiplier;
            raw = Math.pow(attackDamage, 2) / ((attackDamage + pv * 4)) * Math.pow(Math.log10((attackDamage * 4) / (pv + 1) + 1), 2);
            dmg = raw;
        } else { // Player vs Player, Player vs Monster
            dmg = attackDamage + 8;
            dmg += getAddedDamage(mainAttackType);
            dmg += getTribeAddedDamage();
            dmg *= normalizePercentage(dmgPercentage);
            dmg *= attackTypeMultiplier;
            raw = dmg;
            dmg -= pv;
        }
        dmg *= criticalMultiplier;
        dmg *= getClassBalanceMultiplier();
        dmg = Math.ceil(dmg);

        if (!isAttackerPlayerOrSummon() && GameTimeService.getInstance().isNight())
            dmg *= 1.5;

        if (attacker.isPlayer() && ((Player) attacker).isDamageDebugEnabled())
            ((Player) attacker).sendMessage("Dealt " + (Math.round(dmg * 100) / 100.0) + " damage to " + target.getName() + ". Mult: " + normalizePercentage(dmgPercentage) + " CB: " + getClassBalanceMultiplier() + ". DD: " + attackDamage + " Acc: " + (Math.round(accuracyPercentage * 100 * 100) / 100.0) + "% (" + accuracyPercentage + ") Raw: " + (Math.round(raw * 100) / 100.0), true);

        if (target.isPlayer() && ((Player) target).isDamageDebugEnabled())
            ((Player) target).sendMessage("Received " + (Math.round(dmg * 100) / 100.0) + " damage from " + attacker.getName() + ". Mult: " + normalizePercentage(dmgPercentage) + " CB: " + getClassBalanceMultiplier() + ". DD: " + attackDamage + " Acc: " + (Math.round(accuracyPercentage * 100 * 100) / 100.0) + "% Raw: " + (Math.round(raw * 100) / 100.0), true);

        if (dmg < 1) {
            dmg = 0;
            dmgType = EDmgType.Evasion;
            return true;
        }

        //////////////////////////////// Mana Shield Calculation
        if (target.isPlayer() && !Double.isNaN(dmg) && !Double.isInfinite(dmg)) {
            final int manaShield = target.getGameStats().getMagicalShield().getIntMaxValue();
            if (manaShield > 0) {
                final double manaReduce = dmg * (manaShield / 10000) / 100.0;
                if (manaReduce > 0.0 && target.getGameStats().getMp().updateMp(-manaReduce) != 0)
                    this.dmg -= (int) manaReduce;

                if (this.dmg < 1)
                    this.dmg = 1;
            }
        }

        //////////////////////////////// Blocks Calculation
        if (isFrontSideAttack && target.getActionStorage().getActionChartActionT().getGuardType().isDefence()) {
            // If update is success, then deflect the damage.
            if (target.getGameStats().getStunGauge().updateStunGauge(-dmg)) {
                dmgType = EDmgType.Block;
                dmg = 1;
            }
        }

        //////////////////////////////// Health updates
        if (!Double.isNaN(dmg) && !Double.isInfinite(dmg)) {
            if (dmg > 0)
                target.getGameStats().getHp().updateHp(-dmg, attacker, true);
        } else {
            dmg = 0;
            dmgType = EDmgType.Evasion;
            return true;
        }

        //////////////////////////////// PvP and stuff.
        if (isAttackerPlayerOrSummon() && attacker != target) {
            Player player = attacker.getOwner() != null ? (
                    attacker.getOwner().isPlayer() ? (Player) attacker.getOwner() : null
            ) : (attacker.isPlayer() ? (Player) attacker : null);
            if (player != null) {
                if (player.getLevel() >= BattleOptionConfig.ADRENALIN_SUPER_SKILL_MIN_LEVEL && target.isDead() && dmg > 0.0)
                    player.getGameStats().getAdrenalin().addAdrenalin(1, false);

                if (target.isPlayer() && !player.getRegion().getRegionType().isArena() && !LocalWarService.getInstance().hasParticipant(player)) {
                    final PvpMatch pvpMatch = player.getPvpMatch();
                    if (pvpMatch == null || !pvpMatch.hasParticipant(target.getGameObjectId())) {
                        final Player targetPlayer = (Player) target;
                        if (targetPlayer.getTendency() > 0 && targetPlayer.getPreemptiveStrikeTime() == 0L)
                            player.setPreemptiveStrike();
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns damage
     *
     * @return double\
     */
    public double getDmg() {
        return dmg;
    }
}