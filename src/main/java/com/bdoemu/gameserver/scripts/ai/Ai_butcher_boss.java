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
@IAIName("butcher_boss")
public class Ai_butcher_boss extends CreatureAI {
	public Ai_butcher_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 300, 700, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
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
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(blendTime)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500));
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

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_Return(0.3)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -25) {
			if (changeState(state -> Battle_Turn_Left(0.3)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 25) {
			if (changeState(state -> Battle_Turn_Right(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 100) {
			if (changeState(state -> Battle_Walk_Back(0.5)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Battle_Walk(0.5)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Battle_Walk_Around(0.5)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Mace_1(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Mace_2(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Mace_4(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Shield(0.3)))
				return;
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
		if (getDistanceToSpawn() > 4000) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_Return(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 15) {
				if (changeState(state -> Move_Return(blendTime)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(blendTime)))
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
		if(getCallCount() == 20) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 700) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_Return(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 300) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Mace_1(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Mace_2(0.3)))
					return;
			}
		}
		doAction(1093610341L /*BATTLE_WALK_FRONT*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 15) {
				if (changeState(state -> Move_Return(blendTime)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(blendTime)))
					return true;
			}
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
		if (getDistanceToSpawn() > 4000) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_Return(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 170) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Shield(0.3)))
					return;
			}
		}
		doAction(1516377807L /*MOVE_BACK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
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
		if (target != null && getDistanceToTarget(target) <= 100) {
			if (changeState(state -> Battle_Walk_Back(0.5)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Battle_Walk(0.5)))
				return;
		}
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> moveAround(150 + Rnd.get(100, 150), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 15) {
				if (changeState(state -> Move_Return(blendTime)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(blendTime)))
					return true;
			}
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
		if (target != null && getDistanceToTarget(target) <= 100) {
			if (changeState(state -> Battle_Walk_Back(0.5)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Battle_Walk(0.5)))
				return;
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 100) {
			if (changeState(state -> Battle_Walk_Back(0.5)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Battle_Walk(0.5)))
				return;
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Mace_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE9459524L /*Attack_Mace_1*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Mace_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1AFF3E50L /*Attack_Mace_2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Mace_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x14C1A78BL /*Attack_Mace_4*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1982273892L /*BATTLE_ATTACK4*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Shield(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2588FDD2L /*Attack_Shield*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2237378522L /*BATTLE_SHIELD_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3E8D0930L /*Attack_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2428216894L /*TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA9B11BF9L /*Attack_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(217859608L /*TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/) && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.4)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/) {
			if (changeState(state -> Detect_Target(0.4)))
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
