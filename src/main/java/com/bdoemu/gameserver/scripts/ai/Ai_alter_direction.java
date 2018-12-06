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
@IAIName("alter_direction")
public class Ai_alter_direction extends CreatureAI {
	public Ai_alter_direction(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xACD660AFL /*WaypointValue*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Stage1_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA9F0AFL /*Stage1_Wait*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 3000).forEach(consumer -> consumer.getAi().LookAtMe1(getActor(), null));
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage1_Wait(blendTime), 1000));
	}

	protected void Stage2_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x904CE6E1L /*Stage2_Wait*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 3000).forEach(consumer -> consumer.getAi().LookAtMe2(getActor(), null));
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage2_Wait(blendTime), 1000));
	}

	protected void Stage3_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAC0F1FD4L /*Stage3_Wait*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 3000).forEach(consumer -> consumer.getAi().LookAtMe3(getActor(), null));
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage3_Wait(blendTime), 1000));
	}

	protected void Stage4_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC5953949L /*Stage4_Wait*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 3000).forEach(consumer -> consumer.getAi().LookAtMe4(getActor(), null));
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage4_Wait(blendTime), 1000));
	}

	protected void Stage5_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA13E7C7EL /*Stage5_Wait*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 3000).forEach(consumer -> consumer.getAi().LookAtMe5(getActor(), null));
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stage5_Wait(blendTime), 1000));
	}

	protected void Teleport_Position1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8227B7F5L /*Teleport_Position1*/);
		setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39562 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Stage1_Wait(blendTime));
	}

	protected void Teleport_Position2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE4E0FEF7L /*Teleport_Position2*/);
		setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39563 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Stage2_Wait(blendTime));
	}

	protected void Teleport_Position3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBA65EFF3L /*Teleport_Position3*/);
		setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39564 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Stage3_Wait(blendTime));
	}

	protected void Teleport_Position4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x98F5218EL /*Teleport_Position4*/);
		setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39565 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Stage4_Wait(blendTime));
	}

	protected void Teleport_Position5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x48EFC89EL /*Teleport_Position5*/);
		setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39566 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Stage5_Wait(blendTime));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 2000));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 2000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Rotate1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position1(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Rotate2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position2(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Rotate3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position3(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Rotate4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position4(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Rotate5(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position5(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Disappear_Summoner(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Delete_Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
