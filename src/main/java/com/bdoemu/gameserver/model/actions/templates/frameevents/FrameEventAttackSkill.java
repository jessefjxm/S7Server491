package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EAttackAbsorbType;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.actions.enums.ETargetType;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.creature.agrolist.AttackResult;
import com.bdoemu.gameserver.model.creature.agrolist.CreatureAggroList;
import com.bdoemu.gameserver.model.creature.agrolist.IAggroList;
import com.bdoemu.gameserver.model.skills.services.SkillService;
import com.bdoemu.gameserver.utils.MathUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FrameEventAttackSkill extends FrameEvent {
    private int skillId;
    private int attackableCount;
    private int attackerDelay;
    private int attackeeDelay;
    private int attackableCountForNonPlayer;
    private List<ETargetType> targetTypes;
    private List<Integer> playerBuffList;
    private List<Integer> buffList;

    public FrameEventAttackSkill(final EFrameEventType frameEventType) {
        super(frameEventType);
        this.playerBuffList = new ArrayList<Integer>();
        this.buffList = new ArrayList<Integer>();
    }

    @Override
    public void read(final FileBinaryReader reader) {
        super.read(reader);
        this.attackerDelay = reader.readHD();
        this.attackeeDelay = reader.readHD();
        this.attackableCount = reader.readCD();
        this.attackableCountForNonPlayer = reader.readCD();
        this.skillId = reader.readD();
        this.targetTypes = ETargetType.valueof(reader.readD());
        for (int playerBuffListCount = reader.readD(), playerBuffListIndex = 0; playerBuffListIndex < playerBuffListCount; ++playerBuffListIndex) {
            this.playerBuffList.add(reader.readHD());
        }
        for (int buffListCount = reader.readD(), buffListIndex = 0; buffListIndex < buffListCount; ++buffListIndex) {
            this.buffList.add(reader.readHD());
        }
    }

    private void doAbsorb(final int attackAbsorbAmount, final EAttackAbsorbType attackAbsorbType, final Creature owner) {
        if (attackAbsorbAmount != 0) {
            switch (attackAbsorbType) {
                case HP: {
                    owner.getGameStats().getHp().addHP(attackAbsorbAmount, owner);
                    break;
                }
                case MP: {
                    owner.getGameStats().getMp().addMP(attackAbsorbAmount);
                    break;
                }
                case SubResourcePoint: {
                    owner.getGameStats().getSubResourcePointStat().addSubResourcePoints(attackAbsorbAmount);
                    break;
                }
            }
        }
    }

    @Override
    public int getDelay() {
        return (this.attackeeDelay > 0 && this.attackerDelay > 0 && this.attackeeDelay == this.attackerDelay) ? this.attackeeDelay : 0;
    }

    @Override
    public boolean doFrame(final IAction action, final int npcGameObjId, final long staticObjectId, final Collection<Creature> targets, final Collection<Creature> playerTargets) {
        if (targets.size() > attackableCount)
            return false;

        final Creature owner = action.getOwner();
        final ActionChartActionT actionChartActionT = action.getActionChartActionT();
        this.doAbsorb(actionChartActionT.getAttackAbsorbAmount1(), actionChartActionT.getAttackAbsorbType1(), owner);
        this.doAbsorb(actionChartActionT.getAttackAbsorbAmount2(), actionChartActionT.getAttackAbsorbType2(), owner);
        if (actionChartActionT.getMpPerDamage() != 0)
            owner.getGameStats().getMp().addMP(actionChartActionT.getMpPerDamage());

        if (actionChartActionT.getHpPerDamage() != 0)
            owner.getGameStats().getHp().addHP(actionChartActionT.getHpPerDamage(), owner);

        if (actionChartActionT.getStunPerDamage() != 0) {
            // TODO: Implement SMSetStunPoints.
        }

        if (!targets.isEmpty())
            SkillService.useSkill(owner, this.skillId, this.buffList, targets);

        if (!playerTargets.isEmpty()) {
            //System.out.println("\tAttacking " + playerTargets.size() + " players.");
            SkillService.useSkill(owner, this.skillId, this.playerBuffList, playerTargets);
        }
        return true;
    }

    @Override
    public boolean doFrame(final IAction action, Creature target) {
        final Creature owner = action.getOwner();
        for (final ETargetType targetType : this.targetTypes) {
            if (targetType.isSelf())
                target = owner;
        }
        if (target == null)
            return false;

        // To avoid bottleneck, will only check vs player.
        //if (!owner.isPlayer() && target.isPlayer()) {
        //	System.out.println("Creature: " + owner.getName());
        //	System.out.println("\tAttackable: " + attackableCount);
        //	System.out.println("\tAttackableNonP: " + attackableCountForNonPlayer);
        //	System.out.println("\tCondition: " + (attackableCount > 1 && owner.isMonster() && (owner.getOwner() == null || !owner.getOwner().isPlayer()) && target.isPlayer()));
        //	System.out.println("\tSkillId: " + skillId);
        //}

        // Monster AoE damage
        if (attackableCountForNonPlayer > 1 && owner.isMonster() && (owner.getOwner() == null || !owner.getOwner().isPlayer()) && target.isPlayer()) {
            IAggroList aggroInfo = owner.getAggroList();
            if (aggroInfo instanceof CreatureAggroList) {
                final CreatureAggroList creatureAggroInfo = (CreatureAggroList) aggroInfo;
                List<Creature> creatures = creatureAggroInfo.getAggroCreatures();

                int processedObjects = 0;
                for (Creature creature : creatures) {
                    if (!creature.isDead()
                            && owner.isEnemy(creature)
                            && MathUtils.isInRange(owner, creature, owner.getTemplate().getAttackRange())
                            && AttackResult.isFrontSideAttack(owner.getLocation(), creature.getLocation())) {
                        if (processedObjects > attackableCountForNonPlayer)
                            break;

                        ++processedObjects;
                        SkillService.useSkill(owner, skillId, creature.isPlayer() ? playerBuffList : buffList, Collections.singletonList(creature));
                    }
                }

                if (processedObjects == 0)
                    SkillService.useSkill(owner, skillId, target.isPlayer() ? playerBuffList : buffList, Collections.singletonList(target));
            } else {
                SkillService.useSkill(owner, skillId, target.isPlayer() ? playerBuffList : buffList, Collections.singletonList(target));
            }
        } else {
            SkillService.useSkill(owner, skillId, target.isPlayer() ? playerBuffList : buffList, Collections.singletonList(target));
        }
        return super.doFrame(action, target);
    }
}