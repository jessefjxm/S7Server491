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
@IAIName("alter_summon_monster")
public class Ai_alter_summon_monster extends CreatureAI {
	public Ai_alter_summon_monster(Creature actor, Map<Long, Integer> aiVariables) {
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

	protected void Wait_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC6855029L /*Wait_Rotate*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Rotate(blendTime), 1000));
	}

	protected void TurningPoint1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xBDFA3D7FL /*TurningPoint1*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -384168, -305881, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void TurningPoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x66893464L /*TurningPoint2*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -399219, -312022, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void TurningPoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xEAA203F4L /*TurningPoint3*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -400433, -306348, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void TurningPoint4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x1539681EL /*TurningPoint4*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -391385, -300603, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void TurningPoint5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x2B8E09ADL /*TurningPoint5*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -374385, -295091, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Teleport_Position_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x37389610L /*Teleport_Position_A*/);
		setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39557 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Wait_Rotate(blendTime));
	}

	protected void Teleport_Position_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCC74B589L /*Teleport_Position_B*/);
		setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39558 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Wait_Rotate(blendTime));
	}

	protected void Teleport_Position_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x52DA5069L /*Teleport_Position_C*/);
		setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39559 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Wait_Rotate(blendTime));
	}

	protected void Teleport_Position_D(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE7708316L /*Teleport_Position_D*/);
		setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39560 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Wait_Rotate(blendTime));
	}

	protected void Teleport_Position_E(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD45BD56EL /*Teleport_Position_E*/);
		setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39561 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Wait_Rotate(blendTime));
	}

	protected void Summon_Wave1_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDDC8900CL /*Summon_Wave1_A*/);
		doAction(1962782481L /*BATTLE_SUMMON_WAVE1_A*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave1_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x966DD71DL /*Summon_Wave1_B*/);
		doAction(697460359L /*BATTLE_SUMMON_WAVE1_B*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave1_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCB5E3427L /*Summon_Wave1_C*/);
		doAction(3341819729L /*BATTLE_SUMMON_WAVE1_C*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave2_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEB0AE5F8L /*Summon_Wave2_A*/);
		doAction(4134013368L /*BATTLE_SUMMON_WAVE2_A*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave2_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1CA796C6L /*Summon_Wave2_B*/);
		doAction(1927391693L /*BATTLE_SUMMON_WAVE2_B*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave2_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x151D1045L /*Summon_Wave2_C*/);
		doAction(2700350753L /*BATTLE_SUMMON_WAVE2_C*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave3_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x58FC3854L /*Summon_Wave3_A*/);
		doAction(3094892535L /*BATTLE_SUMMON_WAVE3_A*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave3_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB246770AL /*Summon_Wave3_B*/);
		doAction(1911997657L /*BATTLE_SUMMON_WAVE3_B*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave3_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD576165L /*Summon_Wave3_C*/);
		doAction(2149485272L /*BATTLE_SUMMON_WAVE3_C*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave4_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x95C6FE8EL /*Summon_Wave4_A*/);
		doAction(2119786952L /*BATTLE_SUMMON_WAVE4_A*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave4_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xAA94192FL /*Summon_Wave4_B*/);
		doAction(2627766364L /*BATTLE_SUMMON_WAVE4_B*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave4_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x32FC1A4DL /*Summon_Wave4_C*/);
		doAction(2313989959L /*BATTLE_SUMMON_WAVE4_C*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave4_C1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE89847EL /*Summon_Wave4_C1*/);
		doAction(1740512700L /*BATTLE_SUMMON_WAVE4_C1*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave4_C2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1C8CA0C2L /*Summon_Wave4_C2*/);
		doAction(2691209839L /*BATTLE_SUMMON_WAVE4_C2*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave4_C3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x331979C3L /*Summon_Wave4_C3*/);
		doAction(771395215L /*BATTLE_SUMMON_WAVE4_C3*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave4_C4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xADD150A2L /*Summon_Wave4_C4*/);
		doAction(2574893129L /*BATTLE_SUMMON_WAVE4_C4*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave5_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x86852663L /*Summon_Wave5_A*/);
		doAction(446094854L /*BATTLE_SUMMON_WAVE5_A*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave5_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x36B6EE47L /*Summon_Wave5_B*/);
		doAction(2263515465L /*BATTLE_SUMMON_WAVE5_B*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Summon_Wave5_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC55C538AL /*Summon_Wave5_C*/);
		doAction(1356500386L /*BATTLE_SUMMON_WAVE5_C*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Victory_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5C2DAEACL /*Victory_Logic*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().Victory(getActor(), null));
		changeState(state -> Wait(blendTime));
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
	public EAiHandlerResult Victory(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Victory_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Disappear(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Delete_Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave1_A(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave1_A(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave1_B(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave1_B(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave1_C(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave1_C(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave2_A(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave2_A(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave2_B(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave2_B(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave2_C(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave2_C(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave3_A(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave3_A(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave3_B(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave3_B(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave3_C(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave3_C(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave4_A(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave4_A(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave4_B(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave4_B(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave4_C(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave4_C(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave4_C1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave4_C1(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave4_C2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave4_C2(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave4_C3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave4_C3(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave4_C4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave4_C4(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave5_A(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave5_A(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave5_B(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave5_B(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Wave5_C(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon_Wave5_C(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_A1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_A(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_A2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_A(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_A3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_A(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_A4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_A(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_A5(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_A(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_B1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_B(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_B2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_B(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_B3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_B(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_B4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_B(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_B5(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_B(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_C1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_C(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_C2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_C(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_C3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_C(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_C4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_C(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_C5(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_C(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_D1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_D(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_D2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_D(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_D3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_D(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_D4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_D(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_D5(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_D(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_E1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_E(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_E2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_E(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_E3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_E(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_E4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_E(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Teleport_Position_E5(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Teleport_Position_E(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult LookAtMe1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xC6855029L /*Wait_Rotate*/) {
			if (changeState(state -> TurningPoint1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult LookAtMe2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xC6855029L /*Wait_Rotate*/) {
			if (changeState(state -> TurningPoint2(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult LookAtMe3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xC6855029L /*Wait_Rotate*/) {
			if (changeState(state -> TurningPoint3(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult LookAtMe4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xC6855029L /*Wait_Rotate*/) {
			if (changeState(state -> TurningPoint4(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult LookAtMe5(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xC6855029L /*Wait_Rotate*/) {
			if (changeState(state -> TurningPoint5(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
