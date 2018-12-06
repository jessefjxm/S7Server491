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
@IAIName("party_imp_leader")
public class Ai_party_imp_leader extends CreatureAI {
	public Ai_party_imp_leader(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 1000));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 3000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 5000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 5000));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 5000));
	}

	protected void Party_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x677C6416L /*Party_Wait*/);
		if (getPartyMembersCount()>= 30) {
			if (changeState(state -> Party_ReadyForRush(blendTime)))
				return;
		}
		if (getVariable(0x22A52166L /*_RandomMoveCount*/) <= 2) {
			if(getCallCount() == 5) {
				if (changeState(state -> Party_WalkRandom(blendTime)))
					return;
			}
		}
		if (getVariable(0x22A52166L /*_RandomMoveCount*/) > 2) {
			if(getCallCount() == 5) {
				if (changeState(state -> Party_Patrol(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 3000 && target != null && (getTargetCharacterKey(target) == 20010 || getTargetCharacterKey(target) == 20011 || getTargetCharacterKey(target) == 20012 || getTargetCharacterKey(target) == 20013)) {
			createParty(1, 1);
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Wait(blendTime), 1000));
	}

	protected void Party_WalkRandom(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x11984335L /*Party_WalkRandom*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, getVariable(0x22A52166L /*_RandomMoveCount*/) + 1);
		if (getPartyMembersCount()>= 30) {
			if (changeState(state -> Party_ReadyForRush(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 3000 && target != null && (getTargetCharacterKey(target) == 20010 || getTargetCharacterKey(target) == 20011 || getTargetCharacterKey(target) == 20012 || getTargetCharacterKey(target) == 20013)) {
			createParty(1, 1);
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 200, 400, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_Patrol(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3FA3638AL /*Party_Patrol*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		if (getPartyMembersCount()>= 30) {
			if (changeState(state -> Party_ReadyForRush(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 3000 && target != null && (getTargetCharacterKey(target) == 20010 || getTargetCharacterKey(target) == 20011 || getTargetCharacterKey(target) == 20012 || getTargetCharacterKey(target) == 20013)) {
			createParty(1, 1);
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "imp_patrol_1 imp_patrol_10 imp_patrol_15 imp_patrol_16 imp_patrol_19 imp_patrol_21", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Wait(blendTime), 1000)));
	}

	protected void Party_ReadyForRush(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x76F50547L /*Party_ReadyForRush*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, 0);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleReadyForRush(getActor(), null));
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Rush(blendTime), 1000));
	}

	protected void Party_Rush(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC7D7B6B9L /*Party_Rush*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "imp_rushtown_22", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000)));
	}

	protected void Party_Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAC252DE8L /*Party_Battle_Wait*/);
		if (getVariable(0x22A52166L /*_RandomMoveCount*/) <= 2) {
			if(getCallCount() == 3) {
				if (changeState(state -> Party_WalkRandom_InTown(blendTime)))
					return;
			}
		}
		if (getVariable(0x22A52166L /*_RandomMoveCount*/) > 2) {
			if(getCallCount() == 3) {
				if (changeState(state -> Party_Rush_InTown(blendTime)))
					return;
			}
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000));
	}

	protected void Party_Rush_InTown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7E15DFC1L /*Party_Rush_InTown*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleFollowMe(getActor(), null));
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "imp_rushtown_27 imp_rushtown_31 imp_rushtown_36 imp_rushtown_41 imp_rushtown_47", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000)));
	}

	protected void Party_WalkRandom_InTown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x33F90F1DL /*Party_WalkRandom_InTown*/);
		setVariable(0x22A52166L /*_RandomMoveCount*/, getVariable(0x22A52166L /*_RandomMoveCount*/) + 1);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 200, 400, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Party_Battle_Wait(blendTime), 1000)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleHelpMe(getActor(), null));
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_KnockBack(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStuned(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Stun(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_KnockDown(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Stun(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Capture(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvade(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleGuardCrash(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyCheck(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getDistanceToTarget(target) < 3000 && target != null && (getTargetCharacterKey(target) == 20011 || getTargetCharacterKey(target) == 20012 || getTargetCharacterKey(target) == 20013)) {
			createParty(1, 1);
		}
		return EAiHandlerResult.BYPASS;
	}
}
