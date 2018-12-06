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
@IAIName("alter_summon_shrine")
public class Ai_alter_summon_shrine extends CreatureAI {
	public Ai_alter_summon_shrine(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		setVariableArray(0x68C5BE96L /*WaypointValue1*/, new Integer[] { 0, 0 });
		setVariableArray(0xF99872EDL /*WaypointValue2*/, new Integer[] { 0, 0 });
		setVariableArray(0xD865EB82L /*WaypointValue3*/, new Integer[] { 0, 0 });
		setVariableArray(0xD87EDDAL /*WaypointValue4*/, new Integer[] { 0, 0 });
		setVariableArray(0x625AFE4CL /*WaypointValue5*/, new Integer[] { 0, 0 });
		setVariable(0x37016224L /*_Waypointposition*/, 0);
		setVariable(0xD3505174L /*_Summon_Count*/, 0);
		setVariable(0x1C1B5E4EL /*_SummonArea1*/, 0);
		setVariable(0x4085EAC8L /*_SummonArea2*/, 0);
		setVariable(0xCA2A0728L /*_SummonArea3*/, 0);
		setVariable(0x1950FCA4L /*_SummonArea4*/, 0);
		setVariable(0xEAB74A0CL /*_SummonArea5*/, 0);
		setVariable(0x9F2A066FL /*_DeathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Spawn_Area_Select_EASY(blendTime), 1000));
	}

	protected void Spawn_Area_Select_EASY(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9F829B4CL /*Spawn_Area_Select_EASY*/);
		setVariable(0x1C1B5E4EL /*_SummonArea1*/, 1);
		setVariable(0x4085EAC8L /*_SummonArea2*/, 2);
		setVariable(0xCA2A0728L /*_SummonArea3*/, 3);
		changeState(state -> Spawn_Area_Select_Complete(blendTime));
	}

	protected void Spawn_Area_Select1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA2D3DA16L /*Spawn_Area_Select1*/);
		setVariable(0x37016224L /*_Waypointposition*/, getRandom(5));
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 1) {
			setVariable(0x1C1B5E4EL /*_SummonArea1*/, 1);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 1 && getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			setVariable(0x1C1B5E4EL /*_SummonArea1*/, 2);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 2 && getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			setVariable(0x1C1B5E4EL /*_SummonArea1*/, 3);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 3 && getVariable(0x37016224L /*_Waypointposition*/) <= 4) {
			setVariable(0x1C1B5E4EL /*_SummonArea1*/, 4);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4 && getVariable(0x37016224L /*_Waypointposition*/) <= 5) {
			setVariable(0x1C1B5E4EL /*_SummonArea1*/, 5);
		}
		changeState(state -> Spawn_Area_Select2(blendTime));
	}

	protected void Spawn_Area_Select2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6C5BC718L /*Spawn_Area_Select2*/);
		setVariable(0x37016224L /*_Waypointposition*/, getRandom(5));
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 1) {
			setVariable(0x4085EAC8L /*_SummonArea2*/, 1);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 1 && getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			setVariable(0x4085EAC8L /*_SummonArea2*/, 2);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 2 && getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			setVariable(0x4085EAC8L /*_SummonArea2*/, 3);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 3 && getVariable(0x37016224L /*_Waypointposition*/) <= 4) {
			setVariable(0x4085EAC8L /*_SummonArea2*/, 4);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4 && getVariable(0x37016224L /*_Waypointposition*/) <= 5) {
			setVariable(0x4085EAC8L /*_SummonArea2*/, 5);
		}
		if (getVariable(0x4085EAC8L /*_SummonArea2*/) != getVariable(0x1C1B5E4EL /*_SummonArea1*/)) {
			if (changeState(state -> Spawn_Area_Select3(blendTime)))
				return;
		}
		changeState(state -> Spawn_Area_Select2(blendTime));
	}

	protected void Spawn_Area_Select3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1111CF11L /*Spawn_Area_Select3*/);
		setVariable(0x37016224L /*_Waypointposition*/, getRandom(5));
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 1) {
			setVariable(0xCA2A0728L /*_SummonArea3*/, 1);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 1 && getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			setVariable(0xCA2A0728L /*_SummonArea3*/, 2);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 2 && getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			setVariable(0xCA2A0728L /*_SummonArea3*/, 3);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 3 && getVariable(0x37016224L /*_Waypointposition*/) <= 4) {
			setVariable(0xCA2A0728L /*_SummonArea3*/, 4);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4 && getVariable(0x37016224L /*_Waypointposition*/) <= 5) {
			setVariable(0xCA2A0728L /*_SummonArea3*/, 5);
		}
		if (getVariable(0xCA2A0728L /*_SummonArea3*/) != getVariable(0x4085EAC8L /*_SummonArea2*/) && getVariable(0xCA2A0728L /*_SummonArea3*/) != getVariable(0x1C1B5E4EL /*_SummonArea1*/)) {
			if (changeState(state -> Spawn_Area_Select4(blendTime)))
				return;
		}
		changeState(state -> Spawn_Area_Select3(blendTime));
	}

	protected void Spawn_Area_Select4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x330ADAAFL /*Spawn_Area_Select4*/);
		setVariable(0x37016224L /*_Waypointposition*/, getRandom(5));
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 1) {
			setVariable(0x1950FCA4L /*_SummonArea4*/, 1);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 1 && getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			setVariable(0x1950FCA4L /*_SummonArea4*/, 2);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 2 && getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			setVariable(0x1950FCA4L /*_SummonArea4*/, 3);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 3 && getVariable(0x37016224L /*_Waypointposition*/) <= 4) {
			setVariable(0x1950FCA4L /*_SummonArea4*/, 4);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4 && getVariable(0x37016224L /*_Waypointposition*/) <= 5) {
			setVariable(0x1950FCA4L /*_SummonArea4*/, 5);
		}
		if (getVariable(0x1950FCA4L /*_SummonArea4*/) != getVariable(0xCA2A0728L /*_SummonArea3*/) && getVariable(0x1950FCA4L /*_SummonArea4*/) != getVariable(0x4085EAC8L /*_SummonArea2*/) && getVariable(0x1950FCA4L /*_SummonArea4*/) != getVariable(0x1C1B5E4EL /*_SummonArea1*/)) {
			if (changeState(state -> Spawn_Area_Select5(blendTime)))
				return;
		}
		changeState(state -> Spawn_Area_Select4(blendTime));
	}

	protected void Spawn_Area_Select5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE7629AB2L /*Spawn_Area_Select5*/);
		setVariable(0x37016224L /*_Waypointposition*/, getRandom(5));
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 1) {
			setVariable(0xEAB74A0CL /*_SummonArea5*/, 1);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 1 && getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			setVariable(0xEAB74A0CL /*_SummonArea5*/, 2);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 2 && getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			setVariable(0xEAB74A0CL /*_SummonArea5*/, 3);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 3 && getVariable(0x37016224L /*_Waypointposition*/) <= 4) {
			setVariable(0xEAB74A0CL /*_SummonArea5*/, 4);
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4 && getVariable(0x37016224L /*_Waypointposition*/) <= 5) {
			setVariable(0xEAB74A0CL /*_SummonArea5*/, 5);
		}
		if (getVariable(0xEAB74A0CL /*_SummonArea5*/) != getVariable(0x1950FCA4L /*_SummonArea4*/) && getVariable(0xEAB74A0CL /*_SummonArea5*/) != getVariable(0xCA2A0728L /*_SummonArea3*/) && getVariable(0xEAB74A0CL /*_SummonArea5*/) != getVariable(0x4085EAC8L /*_SummonArea2*/) && getVariable(0xEAB74A0CL /*_SummonArea5*/) != getVariable(0x1C1B5E4EL /*_SummonArea1*/)) {
			if (changeState(state -> Spawn_Area_Select_Complete(blendTime)))
				return;
		}
		changeState(state -> Spawn_Area_Select5(blendTime));
	}

	protected void Spawn_Area_Select_Complete(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC5C887ABL /*Spawn_Area_Select_Complete*/);
		if (getVariable(0x1C1B5E4EL /*_SummonArea1*/) == 1) {
			setVariableArray(0x68C5BE96L /*WaypointValue1*/, new Integer[] { 4, 39557 });
		}
		if (getVariable(0x1C1B5E4EL /*_SummonArea1*/) == 2) {
			setVariableArray(0x68C5BE96L /*WaypointValue1*/, new Integer[] { 4, 39558 });
		}
		if (getVariable(0x1C1B5E4EL /*_SummonArea1*/) == 3) {
			setVariableArray(0x68C5BE96L /*WaypointValue1*/, new Integer[] { 4, 39559 });
		}
		if (getVariable(0x1C1B5E4EL /*_SummonArea1*/) == 4) {
			setVariableArray(0x68C5BE96L /*WaypointValue1*/, new Integer[] { 4, 39560 });
		}
		if (getVariable(0x1C1B5E4EL /*_SummonArea1*/) == 5) {
			setVariableArray(0x68C5BE96L /*WaypointValue1*/, new Integer[] { 4, 39561 });
		}
		if (getVariable(0x4085EAC8L /*_SummonArea2*/) == 1) {
			setVariableArray(0xF99872EDL /*WaypointValue2*/, new Integer[] { 4, 39557 });
		}
		if (getVariable(0x4085EAC8L /*_SummonArea2*/) == 2) {
			setVariableArray(0xF99872EDL /*WaypointValue2*/, new Integer[] { 4, 39558 });
		}
		if (getVariable(0x4085EAC8L /*_SummonArea2*/) == 3) {
			setVariableArray(0xF99872EDL /*WaypointValue2*/, new Integer[] { 4, 39559 });
		}
		if (getVariable(0x4085EAC8L /*_SummonArea2*/) == 4) {
			setVariableArray(0xF99872EDL /*WaypointValue2*/, new Integer[] { 4, 39560 });
		}
		if (getVariable(0x4085EAC8L /*_SummonArea2*/) == 5) {
			setVariableArray(0xF99872EDL /*WaypointValue2*/, new Integer[] { 4, 39561 });
		}
		if (getVariable(0xCA2A0728L /*_SummonArea3*/) == 1) {
			setVariableArray(0xD865EB82L /*WaypointValue3*/, new Integer[] { 4, 39557 });
		}
		if (getVariable(0xCA2A0728L /*_SummonArea3*/) == 2) {
			setVariableArray(0xD865EB82L /*WaypointValue3*/, new Integer[] { 4, 39558 });
		}
		if (getVariable(0xCA2A0728L /*_SummonArea3*/) == 3) {
			setVariableArray(0xD865EB82L /*WaypointValue3*/, new Integer[] { 4, 39559 });
		}
		if (getVariable(0xCA2A0728L /*_SummonArea3*/) == 4) {
			setVariableArray(0xD865EB82L /*WaypointValue3*/, new Integer[] { 4, 39560 });
		}
		if (getVariable(0xCA2A0728L /*_SummonArea3*/) == 5) {
			setVariableArray(0xD865EB82L /*WaypointValue3*/, new Integer[] { 4, 39561 });
		}
		if (getVariable(0x1950FCA4L /*_SummonArea4*/) == 1) {
			setVariableArray(0xD87EDDAL /*WaypointValue4*/, new Integer[] { 4, 39557 });
		}
		if (getVariable(0x1950FCA4L /*_SummonArea4*/) == 2) {
			setVariableArray(0xD87EDDAL /*WaypointValue4*/, new Integer[] { 4, 39558 });
		}
		if (getVariable(0x1950FCA4L /*_SummonArea4*/) == 3) {
			setVariableArray(0xD87EDDAL /*WaypointValue4*/, new Integer[] { 4, 39559 });
		}
		if (getVariable(0x1950FCA4L /*_SummonArea4*/) == 4) {
			setVariableArray(0xD87EDDAL /*WaypointValue4*/, new Integer[] { 4, 39560 });
		}
		if (getVariable(0x1950FCA4L /*_SummonArea4*/) == 5) {
			setVariableArray(0xD87EDDAL /*WaypointValue4*/, new Integer[] { 4, 39561 });
		}
		if (getVariable(0xEAB74A0CL /*_SummonArea5*/) == 1) {
			setVariableArray(0x625AFE4CL /*WaypointValue5*/, new Integer[] { 4, 39557 });
		}
		if (getVariable(0xEAB74A0CL /*_SummonArea5*/) == 2) {
			setVariableArray(0x625AFE4CL /*WaypointValue5*/, new Integer[] { 4, 39558 });
		}
		if (getVariable(0xEAB74A0CL /*_SummonArea5*/) == 3) {
			setVariableArray(0x625AFE4CL /*WaypointValue5*/, new Integer[] { 4, 39559 });
		}
		if (getVariable(0xEAB74A0CL /*_SummonArea5*/) == 4) {
			setVariableArray(0x625AFE4CL /*WaypointValue5*/, new Integer[] { 4, 39560 });
		}
		if (getVariable(0xEAB74A0CL /*_SummonArea5*/) == 5) {
			setVariableArray(0x625AFE4CL /*WaypointValue5*/, new Integer[] { 4, 39561 });
		}
		if (changeState(state -> Start_Message_Logic(blendTime)))
			return;
		changeState(state -> Spawn_Area_Select_Complete(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Start_Message_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5CAA3F85L /*Start_Message_Logic*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_START");
		changeState(state -> Start_Wait(blendTime));
	}

	protected void Start_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9D7E92CDL /*Start_Wait*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Monster_Teleport_Logic(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Child(blendTime), 1000));
	}

	protected void Summon_Child(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFBD542C5L /*Summon_Child*/);
		doAction(1272569089L /*BATTLE_SUMMON_CHILD*/, blendTime, onDoActionEnd -> changeState(state -> Monster_Teleport_Logic(blendTime)));
	}

	protected void Monster_Teleport_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE066E8DBL /*Monster_Teleport_Logic*/);
		if (checkInstanceTeamNo() && getVariable(0x1C1B5E4EL /*_SummonArea1*/) == 1) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23082).forEach(consumer -> consumer.getAi().Teleport_Position_A1(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0x1C1B5E4EL /*_SummonArea1*/) == 2) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23082).forEach(consumer -> consumer.getAi().Teleport_Position_A2(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0x1C1B5E4EL /*_SummonArea1*/) == 3) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23082).forEach(consumer -> consumer.getAi().Teleport_Position_A3(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0x1C1B5E4EL /*_SummonArea1*/) == 4) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23082).forEach(consumer -> consumer.getAi().Teleport_Position_A4(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0x1C1B5E4EL /*_SummonArea1*/) == 5) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23082).forEach(consumer -> consumer.getAi().Teleport_Position_A5(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0x4085EAC8L /*_SummonArea2*/) == 1) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23090).forEach(consumer -> consumer.getAi().Teleport_Position_B1(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0x4085EAC8L /*_SummonArea2*/) == 2) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23090).forEach(consumer -> consumer.getAi().Teleport_Position_B2(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0x4085EAC8L /*_SummonArea2*/) == 3) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23090).forEach(consumer -> consumer.getAi().Teleport_Position_B3(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0x4085EAC8L /*_SummonArea2*/) == 4) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23090).forEach(consumer -> consumer.getAi().Teleport_Position_B4(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0x4085EAC8L /*_SummonArea2*/) == 5) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23090).forEach(consumer -> consumer.getAi().Teleport_Position_B5(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0xCA2A0728L /*_SummonArea3*/) == 1) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23091).forEach(consumer -> consumer.getAi().Teleport_Position_C1(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0xCA2A0728L /*_SummonArea3*/) == 2) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23091).forEach(consumer -> consumer.getAi().Teleport_Position_C2(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0xCA2A0728L /*_SummonArea3*/) == 3) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23091).forEach(consumer -> consumer.getAi().Teleport_Position_C3(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0xCA2A0728L /*_SummonArea3*/) == 4) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23091).forEach(consumer -> consumer.getAi().Teleport_Position_C4(getActor(), null));
		}
		if (checkInstanceTeamNo() && getVariable(0xCA2A0728L /*_SummonArea3*/) == 5) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23091).forEach(consumer -> consumer.getAi().Teleport_Position_C5(getActor(), null));
		}
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23088).forEach(consumer -> consumer.getAi().Rotate1(getActor(), null));
		}
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23092).forEach(consumer -> consumer.getAi().Rotate2(getActor(), null));
		}
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23093).forEach(consumer -> consumer.getAi().Rotate3(getActor(), null));
		}
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23094).forEach(consumer -> consumer.getAi().Rotate4(getActor(), null));
		}
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && getTargetCharacterKey(object) == 23095).forEach(consumer -> consumer.getAi().Rotate5(getActor(), null));
		}
		changeState(state -> Teleport_Position(blendTime));
	}

	protected void Teleport_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCCD414B8L /*Teleport_Position*/);
		if (getVariable(0xD3505174L /*_Summon_Count*/) == 0) {
			doTeleportToWaypoint("waypoint_variable", "WaypointValue1", 0, 0, 1, 1);
		}
		if (getVariable(0xD3505174L /*_Summon_Count*/) == 1) {
			doTeleportToWaypoint("waypoint_variable", "WaypointValue2", 0, 0, 1, 1);
		}
		if (getVariable(0xD3505174L /*_Summon_Count*/) == 2) {
			doTeleportToWaypoint("waypoint_variable", "WaypointValue3", 0, 0, 1, 1);
		}
		if (getVariable(0xD3505174L /*_Summon_Count*/) == 3) {
			doTeleportToWaypoint("waypoint_variable", "WaypointValue4", 0, 0, 1, 1);
		}
		if (getVariable(0xD3505174L /*_Summon_Count*/) == 4) {
			doTeleportToWaypoint("waypoint_variable", "WaypointValue5", 0, 0, 1, 1);
		}
		changeState(state -> Summon_Logic(blendTime));
	}

	protected void Summon_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xECE60874L /*Summon_Logic*/);
		if (getVariable(0xD3505174L /*_Summon_Count*/) == 0) {
			if (changeState(state -> Summon_Wave1_Notifier(blendTime)))
				return;
		}
		if (getVariable(0xD3505174L /*_Summon_Count*/) == 1) {
			if (changeState(state -> Summon_Wave2_Notifier(blendTime)))
				return;
		}
		if (getVariable(0xD3505174L /*_Summon_Count*/) == 2) {
			if (changeState(state -> Summon_Wave3_Notifier(blendTime)))
				return;
		}
		changeState(state -> Summon_Logic(blendTime));
	}

	protected void Summon_Wave1_Notifier(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4245C7B6L /*Summon_Wave1_Notifier*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_1STAGE_READY");
		useSkill(40469, 1, true, EAIFindTargetType.Self, object -> true);
		doAction(91806197L /*BATTLE_SUMMON_SHRINE1*/, blendTime, onDoActionEnd -> changeState(state -> Summon_Wave1_READY(blendTime)));
	}

	protected void Summon_Wave1_READY(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x885E6FE8L /*Summon_Wave1_READY*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Mission_Failed(0.3)))
				return;
		}
		if(getCallCount() == 17) {
			if (changeState(state -> Summon_Wave1_READY1(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wave1_READY(blendTime), 1000));
	}

	protected void Summon_Wave1_READY1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9836737FL /*Summon_Wave1_READY1*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_1STAGE_READY1");
		changeState(state -> Summon_Wave1_READY2(blendTime));
	}

	protected void Summon_Wave1_READY2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB3469429L /*Summon_Wave1_READY2*/);
		if(getCallCount() == 10) {
			if (changeState(state -> Summon_Wave1_A(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wave1_READY2(blendTime), 1000));
	}

	protected void Summon_Wave1_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDDC8900CL /*Summon_Wave1_A*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_1STAGE_START");
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_STAGE_HELP");
		setVariable(0xD3505174L /*_Summon_Count*/, getVariable(0xD3505174L /*_Summon_Count*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wave1_A_Logic(blendTime), 1000));
	}

	protected void Summon_Wave1_A_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1C21392FL /*Summon_Wave1_A_Logic*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && (getTargetCharacterKey(object) == 23082 || getTargetCharacterKey(object) == 23090 || getTargetCharacterKey(object) == 23091)).forEach(consumer -> consumer.getAi().Wave1_A(getActor(), null));
		}
		changeState(state -> Summon_Wait1_A(blendTime));
	}

	protected void Summon_Wait1_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x107F0249L /*Summon_Wait1_A*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 30) {
			if (changeState(state -> Summon_Wave1_B(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait1_A(blendTime), 1000));
	}

	protected void Summon_Wave1_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x966DD71DL /*Summon_Wave1_B*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && (getTargetCharacterKey(object) == 23082 || getTargetCharacterKey(object) == 23090 || getTargetCharacterKey(object) == 23091)).forEach(consumer -> consumer.getAi().Wave1_B(getActor(), null));
		}
		changeState(state -> Summon_Wait1_B(blendTime));
	}

	protected void Summon_Wait1_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBEB96FD0L /*Summon_Wait1_B*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 30) {
			if (changeState(state -> Summon_Wave1_C(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait1_B(blendTime), 1000));
	}

	protected void Summon_Wave1_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCB5E3427L /*Summon_Wave1_C*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_1STAGE_BOSS");
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && (getTargetCharacterKey(object) == 23082 || getTargetCharacterKey(object) == 23090 || getTargetCharacterKey(object) == 23091)).forEach(consumer -> consumer.getAi().Wave1_C(getActor(), null));
		}
		changeState(state -> Summon_Wait1_C(blendTime));
	}

	protected void Summon_Wait1_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x77068EC9L /*Summon_Wait1_C*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 60) {
			if (changeState(state -> Teleport_Position(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait1_C(blendTime), 1000));
	}

	protected void Summon_Wave2_Notifier(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD3CCE873L /*Summon_Wave2_Notifier*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_2STAGE_READY");
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_STAGE_READY_LIGHT");
		useSkill(40444, 1, true, EAIFindTargetType.Self, object -> true);
		doAction(2980524529L /*BATTLE_SUMMON_SHRINE2*/, blendTime, onDoActionEnd -> changeState(state -> Summon_Wave2_READY(blendTime)));
	}

	protected void Summon_Wave2_READY(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC8C2E1F6L /*Summon_Wave2_READY*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 17) {
			if (changeState(state -> Summon_Wave2_READY1(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wave2_READY(blendTime), 1000));
	}

	protected void Summon_Wave2_READY1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xABE85700L /*Summon_Wave2_READY1*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_2STAGE_READY1");
		useSkill(40444, 1, true, EAIFindTargetType.Self, object -> true);
		changeState(state -> Summon_Wave2_READY2(blendTime));
	}

	protected void Summon_Wave2_READY2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7C3A3D84L /*Summon_Wave2_READY2*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 10) {
			if (changeState(state -> Summon_Wave2_A(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wave2_READY2(blendTime), 1000));
	}

	protected void Summon_Wave2_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEB0AE5F8L /*Summon_Wave2_A*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_2STAGE_START");
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_STAGE_HELP");
		setVariable(0xD3505174L /*_Summon_Count*/, getVariable(0xD3505174L /*_Summon_Count*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wave2_A_Logic(blendTime), 1000));
	}

	protected void Summon_Wave2_A_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7A29E09AL /*Summon_Wave2_A_Logic*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && (getTargetCharacterKey(object) == 23082 || getTargetCharacterKey(object) == 23090 || getTargetCharacterKey(object) == 23091)).forEach(consumer -> consumer.getAi().Wave2_A(getActor(), null));
		}
		changeState(state -> Summon_Wait2_A(blendTime));
	}

	protected void Summon_Wait2_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF71586B2L /*Summon_Wait2_A*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 30) {
			if (changeState(state -> Summon_Wave2_B(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait2_A(blendTime), 1000));
	}

	protected void Summon_Wave2_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1CA796C6L /*Summon_Wave2_B*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && (getTargetCharacterKey(object) == 23082 || getTargetCharacterKey(object) == 23090 || getTargetCharacterKey(object) == 23091)).forEach(consumer -> consumer.getAi().Wave2_B(getActor(), null));
		}
		changeState(state -> Summon_Wait2_B(blendTime));
	}

	protected void Summon_Wait2_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8EC8D6FCL /*Summon_Wait2_B*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 30) {
			if (changeState(state -> Summon_Wave2_C(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait2_B(blendTime), 1000));
	}

	protected void Summon_Wave2_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x151D1045L /*Summon_Wave2_C*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_2STAGE_BOSS");
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && (getTargetCharacterKey(object) == 23082 || getTargetCharacterKey(object) == 23090 || getTargetCharacterKey(object) == 23091)).forEach(consumer -> consumer.getAi().Wave2_C(getActor(), null));
		}
		changeState(state -> Summon_Wait2_C(blendTime));
	}

	protected void Summon_Wait2_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFE2DFF2L /*Summon_Wait2_C*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 40) {
			if (changeState(state -> Teleport_Position(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait2_C(blendTime), 1000));
	}

	protected void Summon_Wave3_Notifier(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9333F01AL /*Summon_Wave3_Notifier*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_3STAGE_READY");
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_STAGE_READY_LIGHT");
		useSkill(40444, 1, true, EAIFindTargetType.Self, object -> true);
		doAction(1372700123L /*BATTLE_SUMMON_SHRINE3*/, blendTime, onDoActionEnd -> changeState(state -> Summon_Wave3_READY(blendTime)));
	}

	protected void Summon_Wave3_READY(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5AF48190L /*Summon_Wave3_READY*/);
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 20) {
			if (changeState(state -> Summon_Wave3_READY1(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wave3_READY(blendTime), 1000));
	}

	protected void Summon_Wave3_READY1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x948C09D1L /*Summon_Wave3_READY1*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_3STAGE_READY1");
		changeState(state -> Summon_Wave3_READY2(blendTime));
	}

	protected void Summon_Wave3_READY2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x31BA5D2L /*Summon_Wave3_READY2*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 10) {
			if (changeState(state -> Summon_Wave3_A(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wave3_READY2(blendTime), 1000));
	}

	protected void Summon_Wave3_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x58FC3854L /*Summon_Wave3_A*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_3STAGE_START");
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_STAGE_HELP");
		setVariable(0xD3505174L /*_Summon_Count*/, getVariable(0xD3505174L /*_Summon_Count*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wave3_A_Logic(blendTime), 1000));
	}

	protected void Summon_Wave3_A_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xFDC85FEAL /*Summon_Wave3_A_Logic*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && (getTargetCharacterKey(object) == 23082 || getTargetCharacterKey(object) == 23090 || getTargetCharacterKey(object) == 23091)).forEach(consumer -> consumer.getAi().Wave3_A(getActor(), null));
		}
		changeState(state -> Summon_Wait3_A(blendTime));
	}

	protected void Summon_Wait3_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3AECF85BL /*Summon_Wait3_A*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 30) {
			if (changeState(state -> Summon_Wave3_B(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait3_A(blendTime), 1000));
	}

	protected void Summon_Wave3_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB246770AL /*Summon_Wave3_B*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && (getTargetCharacterKey(object) == 23082 || getTargetCharacterKey(object) == 23090 || getTargetCharacterKey(object) == 23091)).forEach(consumer -> consumer.getAi().Wave3_B(getActor(), null));
		}
		changeState(state -> Summon_Wait3_B(blendTime));
	}

	protected void Summon_Wait3_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x10CB822BL /*Summon_Wait3_B*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		if(getCallCount() == 30) {
			if (changeState(state -> Summon_Wave3_C(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait3_B(blendTime), 1000));
	}

	protected void Summon_Wave3_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD576165L /*Summon_Wave3_C*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_3STAGE_BOSS");
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000 && (getTargetCharacterKey(object) == 23082 || getTargetCharacterKey(object) == 23090 || getTargetCharacterKey(object) == 23091)).forEach(consumer -> consumer.getAi().Wave3_C(getActor(), null));
		}
		changeState(state -> Summon_Wait3_C(blendTime));
	}

	protected void Summon_Wait3_C(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB267EE73L /*Summon_Wait3_C*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait3_C(blendTime), 1000));
	}

	protected void Mission_Complete(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x99181DBBL /*Mission_Complete*/);
		doAction(2123161441L /*WAVE_PING*/, blendTime, onDoActionEnd -> scheduleState(state -> Mission_Complete1(blendTime), 1000));
	}

	protected void Mission_Complete1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x12CD3CA3L /*Mission_Complete1*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_ALL_CLEAR");
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_END");
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Disappear(getActor(), null));
		changeState(state -> Mission_Failed1(blendTime));
	}

	protected void Mission_Failed(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD54FCF21L /*Mission_Failed*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_FAIL");
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_END");
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Disappear(getActor(), null));
		changeState(state -> Mission_Failed1(blendTime));
	}

	protected void Mission_Failed1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA2873ADL /*Mission_Failed1*/);
		useSkill(40475, 1, true, EAIFindTargetType.Self, object -> true);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Mission_Failed2(blendTime), 1000));
	}

	protected void Mission_Failed2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA161743L /*Mission_Failed2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
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
		if (changeState(state -> Mission_Complete(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Destroyed_Shrine(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Mission_Failed(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
