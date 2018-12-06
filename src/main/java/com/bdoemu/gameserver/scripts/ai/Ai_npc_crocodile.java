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
@IAIName("npc_crocodile")
public class Ai_npc_crocodile extends CreatureAI {
	public Ai_npc_crocodile(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
		setVariable(0xACD660AFL /*WaypointValue*/, 0);
		setVariable(0x5FA18DB2L /*_AttackPoint*/, 0);
		setVariable(0xE60F92ADL /*_MinMove*/, 0);
		setVariable(0xF7AC801L /*_MaxMove*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 1000));
	}

	protected void Select_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC5E05D2L /*Select_Position*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(5));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 7228 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 7237 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 7216 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 7253 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			if (changeState(state -> Move_Position(blendTime)))
				return;
		}
		if (changeState(state -> Action_Ready(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 2000 + Rnd.get(-500,500)));
	}

	protected void Move_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFF3E58ABL /*Move_Position*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0xACD660AFL /*WaypointValue*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 5000)));
	}

	protected void Move_Position_Underground(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5DF73086L /*Move_Position_Underground*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0xACD660AFL /*WaypointValue*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 5000)));
	}

	protected void Action_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x48CD34A8L /*Action_Ready*/);
		if(Rnd.getChance(40)) {
			if (changeState(state -> Action1(blendTime)))
				return;
		}
		if (changeState(state -> Action2(blendTime)))
			return;
		doAction(1855214820L /*ACTION_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 2000));
	}

	protected void Action1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8B29A951L /*Action1*/);
		doAction(3139735212L /*ACTION1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2000));
	}

	protected void Action2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x385DF395L /*Action2*/);
		doAction(400384921L /*ACTION2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2000));
	}

	protected void Action_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x150B6FB9L /*Action_End*/);
		if(Rnd.getChance(40)) {
			if (changeState(state -> Action1(blendTime)))
				return;
		}
		if (changeState(state -> Action2(blendTime)))
			return;
		doAction(1578968752L /*ACTION_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 2000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
