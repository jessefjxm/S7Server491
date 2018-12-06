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
@IAIName("crazyhelper")
public class Ai_crazyhelper extends CreatureAI {
	public Ai_crazyhelper(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000)) {
			if (changeState(state -> Detect_Target(0.4)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x96AB8779L /*Wait_1*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500)) {
			if (changeState(state -> Detect_Target_1(0.4)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random(0.4)))
				return;
		}
		doAction(1569894142L /*WAIT_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_1(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500)) {
			if (changeState(state -> Detect_Target_1(0.4)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1500, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_1(blendTime), 1000)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEC3F34D2L /*Detect_Target*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Detect_Target_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x727AB326L /*Detect_Target_1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(2035445255L /*DETECT_ENEMY_1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 500));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_1(blendTime), 1000)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2500));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -135 && getVariable(0xE5BD13F2L /*_Degree*/) >= -179) {
			if (changeState(state -> Battle_Turn_180(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 135 && getVariable(0xE5BD13F2L /*_Degree*/) <= 180) {
			if (changeState(state -> Battle_Turn_180(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -25) {
			if (changeState(state -> Battle_Turn_Left(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 25) {
			if (changeState(state -> Battle_Turn_Right(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1200) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 400 && getDistanceToTarget(target, false) <= 1200)) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Walk_Back(0.3)))
				return;
		}
		if(Rnd.getChance(25)) {
			if (changeState(state -> Battle_Walk_Around(0.5)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 250) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Normal_2(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 250 && getDistanceToTarget(target, false) <= 400)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Around_1(0.3)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Battle_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xEB438BF9L /*Battle_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Normal_1(0.4)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1200) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 400 && getDistanceToTarget(target, false) <= 1200)) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 200 && getDistanceToTarget(target, false) <= 350)) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Around_1(0.3)))
					return;
			}
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 100)));
	}

	protected void Battle_Walk_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC3F442L /*Battle_Walk_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 200) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal_3(0.3)))
					return;
			}
		}
		doAction(662185133L /*BATTLE_WALK_BACK*/, blendTime, onDoActionEnd -> escape(1500, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk_Back(blendTime), 100)));
	}

	protected void Battle_Walk_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x2EE72F2DL /*Battle_Walk_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1000) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 300 && getDistanceToTarget(target, false) <= 1200)) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		doAction(4272993707L /*BATTLE_MOVE*/, blendTime, onDoActionEnd -> moveAround(450 + Rnd.get(350, 550), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 100)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 550) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < 300) {
				if(Rnd.getChance(15)) {
					if (changeState(state -> Attack_Normal_2(0.3)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) > 400) {
				if(Rnd.getChance(15)) {
					if (changeState(state -> Attack_Around_1(0.4)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 550) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < 300) {
				if(Rnd.getChance(15)) {
					if (changeState(state -> Attack_Normal_2(0.3)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) > 400) {
				if(Rnd.getChance(15)) {
					if (changeState(state -> Attack_Around_1(0.4)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF94905AEL /*Battle_Turn_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1676409899L /*TURN_180*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < 300) {
				if(Rnd.getChance(15)) {
					if (changeState(state -> Attack_Normal_2(0.3)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) > 400) {
				if(Rnd.getChance(15)) {
					if (changeState(state -> Attack_Around_1(0.4)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Attack_Normal_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB0F6C1F5L /*Attack_Normal_1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(319900647L /*ATTACK_NORMAL_1*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Normal_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4BCE7F4DL /*Attack_Normal_2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(4047174547L /*ATTACK_NORMAL_2*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Normal_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7A81B572L /*Attack_Normal_3*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1120120107L /*ATTACK_NORMAL_3*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Around_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9754A017L /*Attack_Around_1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3790318524L /*ATTACK_AROUND_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Around_1_A(blendTime)));
	}

	protected void Attack_Around_1_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x558A8CBEL /*Attack_Around_1_A*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(258589499L /*ATTACK_AROUND_1_A*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && getState() == 0x866C7489L /*Wait*/ && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && getTargetHp(target) > 0 && (getState() == 0x96AB8779L /*Wait_1*/ || getState() == 0x8377635AL /*Move_Random*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Target_1(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x96AB8779L /*Wait_1*/) {
			if (changeState(state -> Detect_Target_1(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/) {
			if (changeState(state -> Detect_Target_1(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xD61E465EL /*Move_Return*/) {
			if (changeState(state -> Detect_Target_1(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
