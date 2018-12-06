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
@IAIName("bigghostship_normal")
public class Ai_bigghostship_normal extends CreatureAI {
	public Ai_bigghostship_normal(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		useSkill(40436, 1, true, EAIFindTargetType.Player, object -> true);
		useSkill(40435, 1, true, EAIFindTargetType.Enemy, object -> isTargetVehicle(object));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> checkBuff(object, 138) && getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 8000 && getTargetHp(object) > 0 && isTargetVehicle(object))) {
			if (changeState(state -> Summon_Start(0.3)))
				return;
		}
		doAction(1878700470L /*START_ACTION_HOLD*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 10000));
	}

	protected void Summon_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3FB8F870L /*Summon_Start*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3820701766L /*START_ACTION_WAIT*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, getVariable(0xD8D022E3L /*False*/), 3000, 600, 0, 1, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Turn(blendTime), 10000)));
	}

	protected void Turn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x6B3D2433L /*Turn*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3820701766L /*START_ACTION_WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToTarget, 90, () -> {
			return false;
		}, onExit -> scheduleState(state -> Attack_Sumon(blendTime), 1000)));
	}

	protected void Attack_Sumon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3D8D7802L /*Attack_Sumon*/);
		doAction(4090772231L /*BATTLE_SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Start_Action(blendTime)));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> changeState(state -> Wait2(blendTime)));
	}

	protected void Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x84794EB4L /*Wait2*/);
		if (getPartyMembersCount()<= 0) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait2(blendTime), 500));
	}

	protected void Handler_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x63EE0130L /*Handler_Logic*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Send_Target(getActor(), null));
		changeState(state -> Wait3(blendTime));
	}

	protected void Wait3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x75E9DA83L /*Wait3*/);
		if(getCallCount() == 10) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (getPartyMembersCount()>= 1) {
			if (changeState(state -> Attack_End_Logic(0.4)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait3(blendTime), 3000));
	}

	protected void Attack_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x355E057L /*Attack_End_Logic*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_End_Logic(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> checkBuff(object, 138) && getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 8000 && getTargetHp(object) > 0 && isTargetVehicle(object))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> checkBuff(object, 138) && getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 8000 && getTargetHp(object) > 0 && isTargetVehicle(object))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Die(0.4)))
				return;
		}
		scheduleState(state -> Logic(blendTime), 1000);
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		useSkill(40435, 2, true, EAIFindTargetType.Enemy, object -> isTargetVehicle(object) && getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 5000);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 700));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> checkBuff(object, 138) && getTargetHp(object) > 0 && getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 8000 && isCreatureVisible(object, false) && isTargetVehicle(object))) {
			if (changeState(state -> Detect_Target(0.2)))
				return;
		}
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().YouDie(getActor(), null));
		if(getCallCount() == 5) {
			if (changeState(state -> Die(0.4)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 30000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 9000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 100);
		});
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 500));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		changeState(state -> FailFindPathToTarget_Logic(blendTime));
	}

	protected void FailFindPathToTarget_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3C75D394L /*FailFindPathToTarget_Logic*/);
		if (changeState(state -> Battle_Wait(blendTime)))
			return;
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getPartyMembersCount()<= 0) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) < 113 && target != null && getDistanceToTarget(target) >= 0 && target != null && getDistanceToTarget(target) < 1500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_R(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) > -113 && target != null && getDistanceToTarget(target) >= 0 && target != null && getDistanceToTarget(target) < 1500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_L(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) < 113 && target != null && getDistanceToTarget(target) >= 1500 && target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Long_Attack_R(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) > -113 && target != null && getDistanceToTarget(target) >= 1500 && target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Long_Attack_L(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) >= 48 && target != null && getAngleToTarget(target) < 113 && target != null && getDistanceToTarget(target) >= 3000 && target != null && getDistanceToTarget(target) < 4500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> SLong_Attack_R(0.3)))
				return;
		}
		if (target != null && getAngleToTarget(target) <= -48 && target != null && getAngleToTarget(target) > -113 && target != null && getDistanceToTarget(target) >= 3000 && target != null && getDistanceToTarget(target) < 4500 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> SLong_Attack_L(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> checkBuff(object, 138) && getDistanceToTarget(object) <= 8000 && getTargetHp(object) > 0 && isTargetVehicle(object))) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void Attack_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x61CFF41EL /*Attack_R*/);
		doAction(4136558927L /*ATTACK_R_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Attack_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x40F853F4L /*Attack_L*/);
		doAction(216985241L /*ATTACK_L_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Long_Attack_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCAECEDC8L /*Long_Attack_R*/);
		doAction(1339189768L /*LONG_ATTACK_R_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void Long_Attack_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5DEF57FDL /*Long_Attack_L*/);
		doAction(1228385763L /*LONG_ATTACK_L_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void SLong_Attack_R(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x151FEB4CL /*SLong_Attack_R*/);
		doAction(2186094444L /*SLONG_ATTACK_R_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	protected void SLong_Attack_L(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFD4072CDL /*SLong_Attack_L*/);
		doAction(1481860757L /*SLONG_ATTACK_L_A*/, blendTime, onDoActionEnd -> changeState(state -> Attack_End_Logic(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Mami(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Handler_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Mamibye(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		useSkill(40435, 2, true, EAIFindTargetType.Enemy, object -> isTargetVehicle(object) && getDistanceToTarget(object) >= 0 && getDistanceToTarget(object) < 5000);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getPartyMembersCount()<= 0) {
			if (changeState(state -> Die(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
