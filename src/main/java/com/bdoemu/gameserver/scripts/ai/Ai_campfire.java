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
@IAIName("campfire")
public class Ai_campfire extends CreatureAI {
	public Ai_campfire(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x61BB6156L /*_Burn_StartTime*/, 0);
		setVariable(0xEABB8A96L /*_Burn_IngTime*/, 0);
		setVariable(0x687A820EL /*_Burn_EndTime*/, 0);
		setVariable(0x61BB6156L /*_Burn_StartTime*/, getTime());
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x687A820EL /*_Burn_EndTime*/, getTime() - getVariable(0x61BB6156L /*_Burn_StartTime*/));
		if (getVariable(0x687A820EL /*_Burn_EndTime*/) > 600000) {
			if (changeState(state -> Wait_Lv3(blendTime)))
				return;
		}
		doAction(3748214621L /*BURN*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_Lv4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB55F8451L /*Wait_Lv4*/);
		setVariable(0x687A820EL /*_Burn_EndTime*/, getTime() - getVariable(0x61BB6156L /*_Burn_StartTime*/));
		if (getVariable(0x687A820EL /*_Burn_EndTime*/) > 300000) {
			if (changeState(state -> Wait_Lv3(blendTime)))
				return;
		}
		doAction(1082967661L /*WAIT_LV4*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Lv4(blendTime), 1000));
	}

	protected void Wait_Lv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA0437116L /*Wait_Lv1*/);
		setVariable(0x687A820EL /*_Burn_EndTime*/, getTime() - getVariable(0x61BB6156L /*_Burn_StartTime*/));
		if (getVariable(0x687A820EL /*_Burn_EndTime*/) > 600000) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(3621709225L /*WAIT_LV1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Lv1(blendTime), 1000));
	}

	protected void Wait_Lv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9255ECF2L /*Wait_Lv2*/);
		setVariable(0x687A820EL /*_Burn_EndTime*/, getTime() - getVariable(0x61BB6156L /*_Burn_StartTime*/));
		if (getVariable(0x687A820EL /*_Burn_EndTime*/) > 540000) {
			if (changeState(state -> Wait_Lv1(blendTime)))
				return;
		}
		doAction(3616867021L /*WAIT_LV2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Lv2(blendTime), 1000));
	}

	protected void Wait_Lv3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE9F21990L /*Wait_Lv3*/);
		setVariable(0x687A820EL /*_Burn_EndTime*/, getTime() - getVariable(0x61BB6156L /*_Burn_StartTime*/));
		if (getVariable(0x687A820EL /*_Burn_EndTime*/) > 480000) {
			if (changeState(state -> Wait_Lv2(blendTime)))
				return;
		}
		doAction(997754365L /*WAIT_LV3*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Lv3(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
