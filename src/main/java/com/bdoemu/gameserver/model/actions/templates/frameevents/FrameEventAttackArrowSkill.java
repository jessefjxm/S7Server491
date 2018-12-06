package com.bdoemu.gameserver.model.actions.templates.frameevents;

import com.bdoemu.commons.io.FileBinaryReader;
import com.bdoemu.gameserver.model.actions.IAction;
import com.bdoemu.gameserver.model.actions.enums.EAttackAbsorbType;
import com.bdoemu.gameserver.model.actions.enums.EFrameEventType;
import com.bdoemu.gameserver.model.actions.enums.ETargetType;
import com.bdoemu.gameserver.model.actions.templates.ActionChartActionT;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.skills.services.SkillService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FrameEventAttackArrowSkill extends FrameEvent {
    private int skillId;
    private int attackableCount;
    private int attackerDelay;
    private int attackeeDelay;
    private int attackableCountForNonPlayer;
    private List<ETargetType> targetTypes;
    private List<Integer> playerBuffList;
    private List<Integer> buffList;

    public FrameEventAttackArrowSkill(final EFrameEventType frameEventType) {
        super(frameEventType);
        this.playerBuffList = new ArrayList<>();
        this.buffList = new ArrayList<>();
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
        if (targets.size() > this.attackableCount) {
            return false;
        }
        final Creature owner = action.getOwner();
        final ActionChartActionT actionChartActionT = action.getActionChartActionT();
        this.doAbsorb(actionChartActionT.getAttackAbsorbAmount1(), actionChartActionT.getAttackAbsorbType1(), owner);
        this.doAbsorb(actionChartActionT.getAttackAbsorbAmount2(), actionChartActionT.getAttackAbsorbType2(), owner);
        if (actionChartActionT.getMpPerDamage() != 0) {
            owner.getGameStats().getMp().addMP(actionChartActionT.getMpPerDamage());
        }
        if (actionChartActionT.getHpPerDamage() != 0) {
            owner.getGameStats().getHp().addHP(actionChartActionT.getHpPerDamage(), owner);
        }
        if (actionChartActionT.getStunPerDamage() != 0) {
        }
        if (!targets.isEmpty()) {
            SkillService.useSkill(owner, this.skillId, this.buffList, targets);
        }
        if (!playerTargets.isEmpty()) {
            SkillService.useSkill(owner, this.skillId, this.playerBuffList, playerTargets);
        }
        return true;
    }

    @Override
    public boolean doFrame(final IAction action, final Creature target) {
        final Creature owner = action.getOwner();
        if (target == null) {
            return false;
        }
        if (target.isPlayer()) {
            SkillService.useSkill(owner, this.skillId, this.playerBuffList, Collections.singletonList(target));
        } else {
            SkillService.useSkill(owner, this.skillId, this.buffList, Collections.singletonList(target));
        }
        return super.doFrame(action, target);
    }
}
