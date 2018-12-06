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
@IAIName("wildhorse_parent")
public class Ai_wildhorse_parent extends CreatureAI {
	public Ai_wildhorse_parent(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 800 && getTargetHp(object) > 0)) {
			if (changeState(state -> Escape(blendTime)))
				return;
		}
		if (getVariable(0x22A52166L /*_RandomMoveCount*/) <= 5) {
			if(getCallCount() == 10) {
				if (changeState(state -> Move_Random(blendTime)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, getVariable(0x22A52166L /*_RandomMoveCount*/) + 1);
		doAction(66707087L /*MOVE_LV1_START*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 200, 800, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void MovePoint(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE03B724EL /*MovePoint*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 2000).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		doAction(1145066443L /*MOVE_LV3_START*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "mustang_01 mustang_4", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Detect_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x97099137L /*Detect_Enemy*/);
		doAction(133347576L /*ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		if(getCallCount() == 10) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Lost_Target(blendTime), 1000));
	}

	protected void Escape(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xDCE8DF7DL /*Escape*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(1145066443L /*MOVE_LV3_START*/, blendTime, onDoActionEnd -> escape(1500, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1500)));
	}

	protected void Battle_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xEB438BF9L /*Battle_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(1145066443L /*MOVE_LV3_START*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 150 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 750 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Run(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Battle_Attack(blendTime)))
					return;
			}
		}
		doAction(1099068188L /*MOVE_LV2_START*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 100)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -25) {
			if (changeState(state -> Battle_Turn_Left(blendTime)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 25) {
			if (changeState(state -> Battle_Turn_Right(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= -25 && getVariable(0xE5BD13F2L /*_Degree*/) <= 25 && target != null && getDistanceToTarget(target) > 350 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Walk(blendTime)))
				return;
		}
		if(Rnd.getChance(50)) {
			if (changeState(state -> Battle_Attack(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= -5 && getVariable(0xE5BD13F2L /*_Degree*/) <= 5 && target != null && getDistanceToTarget(target) > 350 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Run(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Battle_Attack(blendTime)))
					return;
			}
		}
		doAction(4276078879L /*TURN_L_START*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= -5 && getVariable(0xE5BD13F2L /*_Degree*/) <= 5 && target != null && getDistanceToTarget(target) > 350 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Run(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHp(target) > 0) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Battle_Attack(blendTime)))
					return;
			}
		}
		doAction(25537389L /*TURN_R_START*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 10000));
	}

	protected void Battle_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEECD0FB6L /*Battle_Attack*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD72BCC90L /*Battle_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1464313205L /*ATTACK_RUSH_START*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Escape(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyCheck(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetCharacterKey(target) == 20094) {
			createParty(3, 3);
		}
		if (isPartyLeader()) {
			if (changeState(state -> Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
