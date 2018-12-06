package com.bdoemu.gameserver.scripts.ai;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.commons.utils.Rnd;
import com.bdoemu.gameserver.model.ai.deprecated.*;
import com.bdoemu.gameserver.model.creature.Creature;
import com.bdoemu.gameserver.model.actions.enums.*;
import com.bdoemu.gameserver.model.chat.enums.EChatNoticeType;
import com.bdoemu.gameserver.model.misc.enums.ETradeCommerceType;
import com.bdoemu.gameserver.model.weather.enums.EWeatherFactorType;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author H1X4
 */

@SuppressWarnings("all")
@IAIName("sentry_main2")
public class Ai_sentry_main2 extends CreatureAI {
	public Ai_sentry_main2(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0xB06049AFL /*_Detection*/, 0);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		setVariable(0x1DEAAFE4L /*_TurnCount_L*/, 0);
		setVariable(0xF6B0C3D7L /*_TurnCount_R*/, 0);
		setVariable(0xF792E187L /*_MovingKind*/, getVariable(0x16D76012L /*AI_MovingKind*/));
		setVariable(0x9C1A0E76L /*_Fear*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x26243C4BL /*_Damage_KnockBack*/, getVariable(0xCA081A50L /*AI_Damage_KnockBack*/));
		setVariable(0x65B16C16L /*_Damage_KnockDown*/, getVariable(0xF9798513L /*AI_Damage_KnockDown*/));
		setVariable(0xB0B44BDAL /*_Damage_Bound*/, getVariable(0x9B63B813L /*AI_Damage_Bound*/));
		setVariable(0x71B20CF2L /*_Damage_Stun*/, getVariable(0x7EBC0F53L /*AI_Damage_Stun*/));
		setVariable(0xA09E148EL /*_Damage_Capture*/, getVariable(0x4A7D3EF6L /*AI_Damage_Capture*/));
		setVariable(0xB9CFA843L /*_Damage_Rigid*/, getVariable(0xA558FAB7L /*AI_Damage_Rigid*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
			if (changeState(state -> FailFindPath(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x7CD62D49L /*AI_DetectDistanceMin1*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Search_Enemy_Start(0.3)))
				return;
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xAE01F3A9L /*AI_DetectCreepDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0x11BF935FL /*AI_DetectCreepDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xEB0EFF8EL /*AI_DetectCreepDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x26C4481L /*AI_DetectCreepDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xCC5D1A30L /*AI_DetectCrouchDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0xD7444662L /*AI_DetectCrouchDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x69B75CB2L /*AI_DetectCrouchDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0xBADA6BCBL /*AI_DetectCrouchDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x7CD62D49L /*AI_DetectDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0x542FAB19L /*AI_DetectDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xA91DEBAL /*AI_DetectDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x5D7565CAL /*AI_DetectDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object) < getVariable(0x5103FB80L /*AI_EC_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (getVariable(0xF792E187L /*_MovingKind*/) == 0 && getVariable(0x1DEAAFE4L /*_TurnCount_L*/) < 2) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Turn_L(0.3)))
					return;
			}
		}
		if (getVariable(0xF792E187L /*_MovingKind*/) == 0 && getVariable(0xF6B0C3D7L /*_TurnCount_R*/) < 2) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Turn_R(0.3)))
					return;
			}
		}
		if (getVariable(0xF792E187L /*_MovingKind*/) == 1) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Move_Random(0.3)))
					return;
			}
		}
		if (getVariable(0xF792E187L /*_MovingKind*/) == 2) {
			if (changeState(state -> Move_Destination(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void Wait1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x17E06D10L /*Wait1*/);
		if(getCallCount() == 6) {
			if (changeState(state -> Move_Chaser1(0.3)))
				return;
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x6745CC38L /*AI_DetectAngleMin2*/) && getAngleToTarget(object) <= getVariable(0x80C40CD3L /*AI_DetectAngleMax2*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xEB0EFF8EL /*AI_DetectCreepDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x26C4481L /*AI_DetectCreepDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x6745CC38L /*AI_DetectAngleMin2*/) && getAngleToTarget(object) <= getVariable(0x80C40CD3L /*AI_DetectAngleMax2*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x69B75CB2L /*AI_DetectCrouchDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0xBADA6BCBL /*AI_DetectCrouchDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x6745CC38L /*AI_DetectAngleMin2*/) && getAngleToTarget(object) <= getVariable(0x80C40CD3L /*AI_DetectAngleMax2*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xA91DEBAL /*AI_DetectDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x5D7565CAL /*AI_DetectDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x6745CC38L /*AI_DetectAngleMin2*/) && getAngleToTarget(object) <= getVariable(0x80C40CD3L /*AI_DetectAngleMax2*/) && isCreatureVisible(object, false) && getDistanceToTarget(object) < getVariable(0x5103FB80L /*AI_EC_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object) > getVariable(0x542FAB19L /*AI_DetectDistanceMax1*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(927442757L /*WAIT1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait1(blendTime), 500));
	}

	protected void Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x84794EB4L /*Wait2*/);
		if(getCallCount() == 3) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x6745CC38L /*AI_DetectAngleMin2*/) && getAngleToTarget(object) <= getVariable(0x80C40CD3L /*AI_DetectAngleMax2*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x5103FB80L /*AI_EC_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object) > getVariable(0x542FAB19L /*AI_DetectDistanceMax1*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		if (getVariable(0x925166FDL /*_FirstAttack*/) == 1 && target != null && getTargetLevel(target) <= getVariable(0x233F196EL /*AI_CheckLevel*/) && target != null && (getAngleToTarget(target) >= getVariable(0x6745CC38L /*AI_DetectAngleMin2*/) && getAngleToTarget(target) <= getVariable(0x80C40CD3L /*AI_DetectAngleMax2*/)) && target != null && isCreatureVisible(target, false) && target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/)) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait2(blendTime), 500));
	}

	protected void Move_Destination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA5879D4L /*Move_Destination*/);
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xAE01F3A9L /*AI_DetectCreepDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0x11BF935FL /*AI_DetectCreepDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xEB0EFF8EL /*AI_DetectCreepDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x26C4481L /*AI_DetectCreepDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xCC5D1A30L /*AI_DetectCrouchDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0xD7444662L /*AI_DetectCrouchDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x69B75CB2L /*AI_DetectCrouchDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0xBADA6BCBL /*AI_DetectCrouchDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x7CD62D49L /*AI_DetectDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0x542FAB19L /*AI_DetectDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xA91DEBAL /*AI_DetectDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x5D7565CAL /*AI_DetectDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x6745CC38L /*AI_DetectAngleMin2*/) && getAngleToTarget(object) <= getVariable(0x80C40CD3L /*AI_DetectAngleMax2*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x5103FB80L /*AI_EC_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("instance_waypoint", "f_s_001", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Destination1(blendTime), 500)));
	}

	protected void Move_Destination1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8F092EE5L /*Move_Destination1*/);
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x6745CC38L /*AI_DetectAngleMin2*/) && getAngleToTarget(object) <= getVariable(0x80C40CD3L /*AI_DetectAngleMax2*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x5103FB80L /*AI_EC_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xAE01F3A9L /*AI_DetectCreepDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0x11BF935FL /*AI_DetectCreepDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xEB0EFF8EL /*AI_DetectCreepDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x26C4481L /*AI_DetectCreepDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xCC5D1A30L /*AI_DetectCrouchDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0xD7444662L /*AI_DetectCrouchDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x69B75CB2L /*AI_DetectCrouchDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0xBADA6BCBL /*AI_DetectCrouchDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x7CD62D49L /*AI_DetectDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0x542FAB19L /*AI_DetectDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xA91DEBAL /*AI_DetectDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x5D7565CAL /*AI_DetectDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("instance_waypoint", "f_s_002", ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Destination(blendTime), 500)));
	}

	protected void Turn_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA55D3525L /*Turn_L*/);
		setVariable(0x1DEAAFE4L /*_TurnCount_L*/, getVariable(0x1DEAAFE4L /*_TurnCount_L*/) + 1);
		setVariable(0xF6B0C3D7L /*_TurnCount_R*/, getVariable(0xF6B0C3D7L /*_TurnCount_R*/) - 1);
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xAE01F3A9L /*AI_DetectCreepDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0x11BF935FL /*AI_DetectCreepDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xEB0EFF8EL /*AI_DetectCreepDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x26C4481L /*AI_DetectCreepDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xCC5D1A30L /*AI_DetectCrouchDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0xD7444662L /*AI_DetectCrouchDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x69B75CB2L /*AI_DetectCrouchDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0xBADA6BCBL /*AI_DetectCrouchDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x7CD62D49L /*AI_DetectDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0x542FAB19L /*AI_DetectDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xA91DEBAL /*AI_DetectDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x5D7565CAL /*AI_DetectDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x6745CC38L /*AI_DetectAngleMin2*/) && getAngleToTarget(object) <= getVariable(0x80C40CD3L /*AI_DetectAngleMax2*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x5103FB80L /*AI_EC_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(4240056895L /*TURN_L*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Turn_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1700C253L /*Turn_R*/);
		setVariable(0x1DEAAFE4L /*_TurnCount_L*/, getVariable(0x1DEAAFE4L /*_TurnCount_L*/) - 1);
		setVariable(0xF6B0C3D7L /*_TurnCount_R*/, getVariable(0xF6B0C3D7L /*_TurnCount_R*/) + 1);
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xAE01F3A9L /*AI_DetectCreepDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0x11BF935FL /*AI_DetectCreepDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xEB0EFF8EL /*AI_DetectCreepDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x26C4481L /*AI_DetectCreepDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xCC5D1A30L /*AI_DetectCrouchDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0xD7444662L /*AI_DetectCrouchDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x69B75CB2L /*AI_DetectCrouchDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0xBADA6BCBL /*AI_DetectCrouchDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x7CD62D49L /*AI_DetectDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0x542FAB19L /*AI_DetectDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xA91DEBAL /*AI_DetectDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x5D7565CAL /*AI_DetectDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x6745CC38L /*AI_DetectAngleMin2*/) && getAngleToTarget(object) <= getVariable(0x80C40CD3L /*AI_DetectAngleMax2*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x5103FB80L /*AI_EC_Distance*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(1642243093L /*TURN_R*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Move_Return_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x47391F8FL /*Move_Return_Wait*/);
		if (getVariable(0xF792E187L /*_MovingKind*/) == 2) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Move_Destination(0.3)))
					return;
			}
		}
		if (getVariable(0xF792E187L /*_MovingKind*/) == 2) {
			if (changeState(state -> Move_Destination1(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Relative_Turn(blendTime), 1000)));
	}

	protected void Relative_Turn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xB2F6971CL /*Relative_Turn*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Chaser1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xFAF2B551L /*Move_Chaser1*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return_Wait(blendTime)))
				return;
		}
		if(getCallCount() == 40) {
			if (changeState(state -> Move_Return_Wait(0.3)))
				return;
		}
		if (getVariable(0xF792E187L /*_MovingKind*/) != 2 && getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/)) {
			if (changeState(state -> Move_Return_Wait(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Move_Return_Wait(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x542FAB19L /*AI_DetectDistanceMax1*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Return_Wait(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= getVariable(0x5103FB80L /*AI_EC_Distance*/)) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser1(blendTime), 500)));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Search_Enemy_End(blendTime)))
				return;
		}
		setVariable(0xB06049AFL /*_Detection*/, 1);
		if(getCallCount() == 40) {
			if (changeState(state -> Move_Return_Wait(0.3)))
				return;
		}
		if (getVariable(0xF792E187L /*_MovingKind*/) != 2 && getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/)) {
			if (changeState(state -> Move_Return_Wait(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) <= 0) {
			if (changeState(state -> Move_Return_Wait(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x542FAB19L /*AI_DetectDistanceMax1*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Return_Wait(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Attack_1(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 500)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x7CD62D49L /*AI_DetectDistanceMin1*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Search_Enemy_Start(0.3)))
				return;
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xAE01F3A9L /*AI_DetectCreepDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0x11BF935FL /*AI_DetectCreepDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xEB0EFF8EL /*AI_DetectCreepDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x26C4481L /*AI_DetectCreepDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xCC5D1A30L /*AI_DetectCrouchDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0xD7444662L /*AI_DetectCrouchDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x69B75CB2L /*AI_DetectCrouchDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0xBADA6BCBL /*AI_DetectCrouchDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0x7CD62D49L /*AI_DetectDistanceMin1*/) && getDistanceToTarget(object, false) <= getVariable(0x542FAB19L /*AI_DetectDistanceMax1*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		if ((getTargetBattleAimedActionType("Crouch")) && (getTargetBattleAimedActionType("Creep"))) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && isCreatureVisible(object, false) && getDistanceToTarget(object, false) >= getVariable(0xA91DEBAL /*AI_DetectDistanceMin2*/) && getDistanceToTarget(object, false) <= getVariable(0x5D7565CAL /*AI_DetectDistanceMax2*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> Wait2(0.3)))
					return;
			}
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getAngleToTarget(object) >= getVariable(0x81B98652L /*AI_DetectAngleMin1*/) && getAngleToTarget(object) <= getVariable(0x6A06081FL /*AI_DetectAngleMax1*/) && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0xA91DEBAL /*AI_DetectDistanceMin2*/) && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1600, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Search_Enemy_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDE5145EFL /*Search_Enemy_Start*/);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Search_Enemy_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6FE7BBC9L /*Search_Enemy_End*/);
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 0);
		doAction(1544982863L /*BATTLE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if(getCallCount() == 40) {
			if (changeState(state -> Search_Enemy_End(0.3)))
				return;
		}
		setVariable(0xFA9DA674L /*_IsBattleMode*/, 1);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Search_Enemy_End(0.3)))
				return;
		}
		if (getDistanceToSpawn() > getVariable(0x468D3572L /*AI_EC_LimitDistance*/)) {
			if (changeState(state -> Search_Enemy_End(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= getVariable(0x714E207L /*AI_BT_Attack2_Distance*/)) {
			if(Rnd.getChance(getVariable(0x23576610L /*AI_BT_Attack2*/))) {
				if (changeState(state -> Battle_Attack_2(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= getVariable(0xC944200L /*AI_BT_Attack3_Distance*/)) {
			if(Rnd.getChance(getVariable(0xBDC07D4BL /*AI_BT_Attack3*/))) {
				if (changeState(state -> Battle_Attack_3(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= getVariable(0x22CF02DCL /*AI_BT_Attack1_Distance*/)) {
			if (changeState(state -> Battle_Attack_1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x5103FB80L /*AI_EC_Distance*/)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 500));
	}

	protected void Battle_Attack_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x100909D7L /*Battle_Attack_1*/);
		if (isTargetLost()) {
			if (changeState(state -> Search_Enemy_End(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCB4271F4L /*Battle_Attack_2*/);
		if (isTargetLost()) {
			if (changeState(state -> Search_Enemy_End(blendTime)))
				return;
		}
		doAction(319900647L /*ATTACK_NORMAL_1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x92CAF548L /*Battle_Attack_3*/);
		if (isTargetLost()) {
			if (changeState(state -> Search_Enemy_End(blendTime)))
				return;
		}
		doAction(4047174547L /*ATTACK_NORMAL_2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 1000);
		});
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 3000);
		});
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 1000);
		});
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 1000);
		});
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 5000);
		});
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> {
			if (getVariable(0x9C1A0E76L /*_Fear*/) == 1) {
				if (changeState(state -> Damage_Fear(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 2000);
		});
	}

	protected void Damage_Fear(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBF1D8728L /*Damage_Fear*/);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 400, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Damage_Fear(blendTime), 400)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xFA9DA674L /*_IsBattleMode*/) == 0) {
			if (changeState(state -> Search_Enemy_Start(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x26243C4BL /*_Damage_KnockBack*/) == 1) {
			if (changeState(state -> Damage_KnockBack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x65B16C16L /*_Damage_KnockDown*/) == 1) {
			if (changeState(state -> Damage_KnockDown(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xB0B44BDAL /*_Damage_Bound*/) == 1) {
			if (changeState(state -> Damage_Bound(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x71B20CF2L /*_Damage_Stun*/) == 1) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xA09E148EL /*_Damage_Capture*/) == 1) {
			if (changeState(state -> Damage_Capture(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRigid(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xB9CFA843L /*_Damage_Rigid*/) == 1) {
			if (changeState(state -> Damage_Rigid(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
