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
@IAIName("party_blacklwolf_leader")
public class Ai_party_blacklwolf_leader extends CreatureAI {
	public Ai_party_blacklwolf_leader(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xDF658C6DL /*_HandleHelpMeCount*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		if (isPartyLeader()) {
			if (changeState(state -> Party_Wait(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500 && getTargetHp(object) > 0)) {
			if (changeState(state -> Detect_Enemy(blendTime)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> WalkRandom(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void WalkRandom(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD5712181L /*WalkRandom*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000)) {
			if (changeState(state -> Detect_Enemy(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 300, 2200, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Detect_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x97099137L /*Detect_Enemy*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleHelpMe(getActor(), null));
		setVariable(0xDF658C6DL /*_HandleHelpMeCount*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		setVariable(0xDF658C6DL /*_HandleHelpMeCount*/, 0);
		if(getCallCount() == 5) {
			if (changeState(state -> Party_Wait(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1200 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Lost_Target(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xDF658C6DL /*_HandleHelpMeCount*/, 0);
		doAction(2391830576L /*MOVE_RUNAWAY*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
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
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 200 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 120 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Attack_Normal1(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 120 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Attack_Normal2(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 120 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Attack_Normal3(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Attack_Normal4(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 10000));
	}

	protected void Party_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x677C6416L /*Party_Wait*/);
		if(Rnd.getChance(10)) {
			if (changeState(state -> Party_WalkRandom(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Detect_Enemy(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 2000 && getPartyMembersCount()<= 3 && target != null && getTargetCharacterKey(target) == 20558) {
			createParty(3, 3);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 1000));
	}

	protected void Party_WalkRandom(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x11984335L /*Party_WalkRandom*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1500 && getTargetHp(object) > 0)) {
			if (changeState(state -> Detect_Enemy(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 2000 && getPartyMembersCount()<= 3 && target != null && getTargetCharacterKey(target) == 20558) {
			createParty(3, 3);
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 1000, 2000, true, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Attack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB1EFA0F9L /*Attack_Normal1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5F2C1966L /*Attack_Normal3*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5CA8868L /*Attack_Normal4*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3904990386L /*BATTLE_CHARGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && getState() != 0x67695F37L /*Lost_Target*/) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && getTargetHp(target) > 0 && getState() == 0x67695F37L /*Lost_Target*/) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Battle_Wait(0.3)))
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
		if (target != null && getDistanceToTarget(target) < 2000 && target != null && getTargetCharacterKey(target) == 20558) {
			createParty(3, 3);
		}
		if (isPartyLeader()) {
			if (changeState(state -> Party_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
