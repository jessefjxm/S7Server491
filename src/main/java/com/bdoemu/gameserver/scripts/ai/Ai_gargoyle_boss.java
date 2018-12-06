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
@IAIName("gargoyle_boss")
public class Ai_gargoyle_boss extends CreatureAI {
	public Ai_gargoyle_boss(Creature actor, Map<Long, Integer> aiVariables) {
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
			if (changeState(state -> Battle_Wait(0.3)))
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
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500 + Rnd.get(-500,500)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
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
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3500));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3500));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2500));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
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
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 135 && getVariable(0xE5BD13F2L /*_Degree*/) <= 179) {
			if (changeState(state -> Battle_Turn_Right_180(0.3)))
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
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Battle_Walk_Around(0.3)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Combination(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Wing(0.3)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Roar(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 250) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 700) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 320) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Combination(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 450) {
			if(Rnd.getChance(7)) {
				if (changeState(state -> Attack_Wing(0.4)))
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
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if(getCallCount() == 10) {
			if (changeState(state -> Attack_Roar(0.4)))
				return;
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_Combination(0.4)))
				return;
		}
		doAction(662185133L /*BATTLE_WALK_BACK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
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
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Normal(0.4)))
				return;
		}
		if(Rnd.getChance(12)) {
			if (changeState(state -> Attack_Combination(0.4)))
				return;
		}
		doAction(4272993707L /*BATTLE_MOVE*/, blendTime, onDoActionEnd -> moveAround(250 + Rnd.get(150, 350), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 100)));
	}

	protected void Battle_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xEB438BF9L /*Battle_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 250) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(12)) {
				if (changeState(state -> Attack_Combination(0.4)))
					return;
			}
		}
		doAction(1664053560L /*BATTLE_TURN_LEFT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(12)) {
				if (changeState(state -> Attack_Combination(0.4)))
					return;
			}
		}
		doAction(2806128650L /*BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x177CAD44L /*Battle_Turn_Right_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if(Rnd.getChance(12)) {
				if (changeState(state -> Attack_Combination(0.4)))
					return;
			}
		}
		doAction(2084317812L /*BATTLE_TURN_RIGHT_180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Combination(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB2905D73L /*Attack_Combination*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1058081582L /*ATTACK_COMBINATION*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5ABF220L /*Attack_Roar*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(93247687L /*ATTACK_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Attack_Wing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC0AB458EL /*Attack_Wing*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3002457970L /*ATTACK_WING_READY*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
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
		if (getState() == 0xD5712181L /*WalkRandom*/) {
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
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
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

	@Override
	public EAiHandlerResult HandleRigid(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
