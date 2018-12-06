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
@IAIName("horse_dummy")
public class Ai_horse_dummy extends CreatureAI {
	public Ai_horse_dummy(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x27462B34L /*_isTamingGameStart*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0x940229E0L /*_isState*/, 0);
		setVariable(0x41E6DE54L /*_isTamingRunCount*/, 0);
		setVariable(0x3F487035L /*_HP*/, 0);
		doAction(2877983296L /*WAIT_HIDE*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport(blendTime), 100));
	}

	protected void Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x88C3A72EL /*Teleport*/);
		doTeleportToWaypoint("woodenhorse", "teleport", 0, 0, 1, 1);
		doAction(2877983296L /*WAIT_HIDE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x27462B34L /*_isTamingGameStart*/, 0);
		if (getVariable(0x940229E0L /*_isState*/) == 1) {
			if (changeState(state -> ChaseOwner_Taming(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) < 10) {
			if (changeState(state -> ChaseOwner_Reset(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_taming_rope(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC219A44FL /*Wait_taming_rope*/);
		doAction(1584187648L /*WAIT_WILD*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_taming_rope2(blendTime), 1000));
	}

	protected void Wait_taming_rope2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9F4EB4A7L /*Wait_taming_rope2*/);
		if(getCallCount() == 150) {
			if (changeState(state -> Wait_taming_fail(blendTime)))
				return;
		}
		if (getVariable(0x27462B34L /*_isTamingGameStart*/) == 1) {
			if (changeState(state -> Wait_taming_fail(blendTime)))
				return;
		}
		if (getVariable(0x27462B34L /*_isTamingGameStart*/) == 2) {
			if (changeState(state -> Wait_taming_fail(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 250) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Wait_taming_rope_check(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 251) {
			if (changeState(state -> Wait_taming_rope3(blendTime)))
				return;
		}
		doAction(1584187648L /*WAIT_WILD*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_taming_rope2(blendTime), 1000));
	}

	protected void Wait_taming_rope3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x83DB1CFEL /*Wait_taming_rope3*/);
		if(getCallCount() == 150) {
			if (changeState(state -> Wait_taming_fail(blendTime)))
				return;
		}
		if (getVariable(0x27462B34L /*_isTamingGameStart*/) == 1) {
			if (changeState(state -> Wait_taming_fail(blendTime)))
				return;
		}
		if (getVariable(0x27462B34L /*_isTamingGameStart*/) == 2) {
			if (changeState(state -> Wait_taming_fail(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 250) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Wait_taming_rope_check(blendTime)))
					return;
			}
		}
		doAction(348592191L /*WAIT_RIDE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_taming_rope3(blendTime), 1000));
	}

	protected void Wait_taming_rope_check(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x746EBC40L /*Wait_taming_rope_check*/);
		if (getVariable(0x27462B34L /*_isTamingGameStart*/) == 0) {
			if(getCallCount() == 4) {
				if (changeState(state -> Wait_taming_fail(blendTime)))
					return;
			}
		}
		if (getVariable(0x27462B34L /*_isTamingGameStart*/) == 1) {
			if(getCallCount() == 4) {
				if (changeState(state -> Wait_taming_rope_check2(blendTime)))
					return;
			}
		}
		doAction(3403694740L /*SHAKE_READY*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_taming_rope_check(blendTime), 1000));
	}

	protected void Wait_taming_rope_check2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67CF8538L /*Wait_taming_rope_check2*/);
		if (getVariable(0x27462B34L /*_isTamingGameStart*/) == 0) {
			if(getCallCount() == 13) {
				if (changeState(state -> Wait_taming_fail(blendTime)))
					return;
			}
		}
		if (getVariable(0x27462B34L /*_isTamingGameStart*/) == 1) {
			if(getCallCount() == 13) {
				if (changeState(state -> Wait_taming_fail(blendTime)))
					return;
			}
		}
		if (getVariable(0x27462B34L /*_isTamingGameStart*/) == 2) {
			if (changeState(state -> Wait_taming_rope_check3(blendTime)))
				return;
		}
		doAction(3625000912L /*SHAKE_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_taming_rope_check2(blendTime), 1000));
	}

	protected void Wait_taming_rope_check3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA20B222DL /*Wait_taming_rope_check3*/);
		setVariable(0x27462B34L /*_isTamingGameStart*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_taming_rope2(blendTime), 1000));
	}

	protected void Wait_taming_fail(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF812D6DCL /*Wait_taming_fail*/);
		resetTamingInfo();
		setVariable(0x27462B34L /*_isTamingGameStart*/, 0);
		doAction(1584187648L /*WAIT_WILD*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void ChaseOwner_Taming(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x806FDD59L /*ChaseOwner_Taming*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250) {
			if (changeState(state -> ChaseOwner_Reset(blendTime)))
				return;
		}
		doAction(1159906662L /*WAIT_TAMED*/, blendTime, onDoActionEnd -> scheduleState(state -> ChaseOwner_Taming(blendTime), 1000));
	}

	protected void ChaseOwner_Reset(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFD290D05L /*ChaseOwner_Reset*/);
		if (isTargetLost()) {
			if (changeState(state -> ChaseOwner_Teleport(blendTime)))
				return;
		}
		setVariable(0x940229E0L /*_isState*/, 0);
		doAction(935323904L /*WAIT_SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> ChaseOwner_Teleport(blendTime)));
	}

	protected void ChaseOwner_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1DE0B3F2L /*ChaseOwner_Teleport*/);
		doTeleportToWaypoint("tombofhorse", "teleport", 0, 0, 1, 1);
		doAction(4070086861L /*WAIT_TELEPORT*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 1000));
	}

	protected void Suicide_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x2BD8C797L /*Suicide_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTryTaming(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Wait_taming_rope(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTamingStep1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x27462B34L /*_isTamingGameStart*/, 1);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTamingStep2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x27462B34L /*_isTamingGameStart*/, 2);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTaming(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x940229E0L /*_isState*/, 1);
		if (changeState(state -> ChaseOwner_Taming(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFailTaming(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Wait_taming_fail(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTamingSugar(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x41E6DE54L /*_isTamingRunCount*/, 0);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
