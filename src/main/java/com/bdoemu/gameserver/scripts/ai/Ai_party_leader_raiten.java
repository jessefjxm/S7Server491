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
@IAIName("party_leader_raiten")
public class Ai_party_leader_raiten extends CreatureAI {
	public Ai_party_leader_raiten(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0xDC8695E5L /*DiceCount*/, 0);
		setVariable(0xE289FF90L /*First_Spwan*/, 0);
		setVariable(0xBB6FB7E0L /*SelectTypeValue*/, getVariable(0xBB6FB7E0L /*SelectTypeValue*/));
		setVariable(0x37016224L /*_Waypointposition*/, 0);
		setVariable(0xACD660AFL /*WaypointValue*/, 0);
		setVariable(0xDC8695E5L /*DiceCount*/, 0);
		setVariable(0xE289FF90L /*First_Spwan*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00 && getPartyMembersCount()< 1 && getVariable(0xE289FF90L /*First_Spwan*/) < 1 && getVariable(0xBB6FB7E0L /*SelectTypeValue*/) == 0) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Summon_Raiten(blendTime)))
					return;
			}
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00 && getPartyMembersCount()< 1 && getVariable(0xBB6FB7E0L /*SelectTypeValue*/) == 1) {
			if (changeState(state -> Select_Position_Type1(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00 && getPartyMembersCount()< 1 && getVariable(0xBB6FB7E0L /*SelectTypeValue*/) == 2) {
			if (changeState(state -> Select_Position_Type2(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00 && getPartyMembersCount()< 1 && getVariable(0xBB6FB7E0L /*SelectTypeValue*/) == 3) {
			if (changeState(state -> Select_Position_Type3(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00 && getPartyMembersCount()< 1 && getVariable(0xBB6FB7E0L /*SelectTypeValue*/) == 4) {
			if (changeState(state -> Select_Position_Type4(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00 && getPartyMembersCount()< 1 && getVariable(0xBB6FB7E0L /*SelectTypeValue*/) == 5) {
			if (changeState(state -> Select_Position_Type5(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00 && getPartyMembersCount()< 1 && getVariable(0xE289FF90L /*First_Spwan*/) > 0 && getVariable(0xBB6FB7E0L /*SelectTypeValue*/) == 0) {
			if (changeState(state -> Trigger_1(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00 && getVariable(0xE289FF90L /*First_Spwan*/) > 0) {
			if (changeState(state -> Reset(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Select_Position_Type1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x37B6525EL /*Select_Position_Type1*/);
		setVariable(0x37016224L /*_Waypointposition*/, getRandom(50));
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39313 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 1 && getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39314 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 2 && getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39315 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 3 && getVariable(0x37016224L /*_Waypointposition*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39316 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4 && getVariable(0x37016224L /*_Waypointposition*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39317 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 5 && getVariable(0x37016224L /*_Waypointposition*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39318 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 6 && getVariable(0x37016224L /*_Waypointposition*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39319 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 7 && getVariable(0x37016224L /*_Waypointposition*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39320 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 8 && getVariable(0x37016224L /*_Waypointposition*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39321 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 9 && getVariable(0x37016224L /*_Waypointposition*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39322 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 10 && getVariable(0x37016224L /*_Waypointposition*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39323 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 11 && getVariable(0x37016224L /*_Waypointposition*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39324 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 12 && getVariable(0x37016224L /*_Waypointposition*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39325 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 13 && getVariable(0x37016224L /*_Waypointposition*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39326 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 14 && getVariable(0x37016224L /*_Waypointposition*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39327 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 15 && getVariable(0x37016224L /*_Waypointposition*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39328 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 16 && getVariable(0x37016224L /*_Waypointposition*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39329 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 17 && getVariable(0x37016224L /*_Waypointposition*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39330 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 18 && getVariable(0x37016224L /*_Waypointposition*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39331 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 19 && getVariable(0x37016224L /*_Waypointposition*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39332 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 20 && getVariable(0x37016224L /*_Waypointposition*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39333 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 21 && getVariable(0x37016224L /*_Waypointposition*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39334 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 22 && getVariable(0x37016224L /*_Waypointposition*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39335 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 23 && getVariable(0x37016224L /*_Waypointposition*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39336 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 24 && getVariable(0x37016224L /*_Waypointposition*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39337 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 25 && getVariable(0x37016224L /*_Waypointposition*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39338 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 26 && getVariable(0x37016224L /*_Waypointposition*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39339 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 27 && getVariable(0x37016224L /*_Waypointposition*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39340 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 28 && getVariable(0x37016224L /*_Waypointposition*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39341 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 29 && getVariable(0x37016224L /*_Waypointposition*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39342 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 30 && getVariable(0x37016224L /*_Waypointposition*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39343 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 31 && getVariable(0x37016224L /*_Waypointposition*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39344 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 32 && getVariable(0x37016224L /*_Waypointposition*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39345 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 33 && getVariable(0x37016224L /*_Waypointposition*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39346 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 34 && getVariable(0x37016224L /*_Waypointposition*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39347 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 35 && getVariable(0x37016224L /*_Waypointposition*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39348 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 36 && getVariable(0x37016224L /*_Waypointposition*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39349 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 37 && getVariable(0x37016224L /*_Waypointposition*/) <= 38) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39350 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 38 && getVariable(0x37016224L /*_Waypointposition*/) <= 39) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39351 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 39 && getVariable(0x37016224L /*_Waypointposition*/) <= 40) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39352 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 40 && getVariable(0x37016224L /*_Waypointposition*/) <= 41) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39353 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 41 && getVariable(0x37016224L /*_Waypointposition*/) <= 42) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39354 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 42 && getVariable(0x37016224L /*_Waypointposition*/) <= 43) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39355 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 43 && getVariable(0x37016224L /*_Waypointposition*/) <= 44) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39356 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 44 && getVariable(0x37016224L /*_Waypointposition*/) <= 45) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39357 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 45 && getVariable(0x37016224L /*_Waypointposition*/) <= 46) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39358 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 46 && getVariable(0x37016224L /*_Waypointposition*/) <= 47) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39359 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 47 && getVariable(0x37016224L /*_Waypointposition*/) <= 48) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39360 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 48 && getVariable(0x37016224L /*_Waypointposition*/) <= 49) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39361 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 49 && getVariable(0x37016224L /*_Waypointposition*/) <= 50) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39362 });
		}
		if (changeState(state -> Teleport_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_Type1(blendTime), 5000 + Rnd.get(-500,500)));
	}

	protected void Select_Position_Type2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB079A4ABL /*Select_Position_Type2*/);
		setVariable(0x37016224L /*_Waypointposition*/, getRandom(50));
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39366 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 1 && getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39367 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 2 && getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39368 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 3 && getVariable(0x37016224L /*_Waypointposition*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39369 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4 && getVariable(0x37016224L /*_Waypointposition*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39370 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 5 && getVariable(0x37016224L /*_Waypointposition*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39371 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 6 && getVariable(0x37016224L /*_Waypointposition*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39372 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 7 && getVariable(0x37016224L /*_Waypointposition*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39373 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 8 && getVariable(0x37016224L /*_Waypointposition*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39374 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 9 && getVariable(0x37016224L /*_Waypointposition*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39375 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 10 && getVariable(0x37016224L /*_Waypointposition*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39376 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 11 && getVariable(0x37016224L /*_Waypointposition*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39377 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 12 && getVariable(0x37016224L /*_Waypointposition*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39378 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 13 && getVariable(0x37016224L /*_Waypointposition*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39379 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 14 && getVariable(0x37016224L /*_Waypointposition*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39380 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 15 && getVariable(0x37016224L /*_Waypointposition*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39381 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 16 && getVariable(0x37016224L /*_Waypointposition*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39382 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 17 && getVariable(0x37016224L /*_Waypointposition*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39383 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 18 && getVariable(0x37016224L /*_Waypointposition*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39384 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 19 && getVariable(0x37016224L /*_Waypointposition*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39385 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 20 && getVariable(0x37016224L /*_Waypointposition*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39386 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 21 && getVariable(0x37016224L /*_Waypointposition*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39387 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 22 && getVariable(0x37016224L /*_Waypointposition*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39388 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 23 && getVariable(0x37016224L /*_Waypointposition*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39389 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 24 && getVariable(0x37016224L /*_Waypointposition*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39390 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 25 && getVariable(0x37016224L /*_Waypointposition*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39391 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 26 && getVariable(0x37016224L /*_Waypointposition*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39392 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 27 && getVariable(0x37016224L /*_Waypointposition*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39393 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 28 && getVariable(0x37016224L /*_Waypointposition*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39394 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 29 && getVariable(0x37016224L /*_Waypointposition*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39395 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 30 && getVariable(0x37016224L /*_Waypointposition*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39396 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 31 && getVariable(0x37016224L /*_Waypointposition*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39397 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 32 && getVariable(0x37016224L /*_Waypointposition*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39398 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 33 && getVariable(0x37016224L /*_Waypointposition*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39399 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 34 && getVariable(0x37016224L /*_Waypointposition*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39400 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 35 && getVariable(0x37016224L /*_Waypointposition*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39401 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 36 && getVariable(0x37016224L /*_Waypointposition*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39402 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 37 && getVariable(0x37016224L /*_Waypointposition*/) <= 38) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39403 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 38 && getVariable(0x37016224L /*_Waypointposition*/) <= 39) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39404 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 39 && getVariable(0x37016224L /*_Waypointposition*/) <= 40) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39405 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 40 && getVariable(0x37016224L /*_Waypointposition*/) <= 41) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39406 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 41 && getVariable(0x37016224L /*_Waypointposition*/) <= 42) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39407 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 42 && getVariable(0x37016224L /*_Waypointposition*/) <= 43) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39408 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 43 && getVariable(0x37016224L /*_Waypointposition*/) <= 44) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39409 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 44 && getVariable(0x37016224L /*_Waypointposition*/) <= 45) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39410 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 45 && getVariable(0x37016224L /*_Waypointposition*/) <= 46) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39411 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 46 && getVariable(0x37016224L /*_Waypointposition*/) <= 47) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39412 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 47 && getVariable(0x37016224L /*_Waypointposition*/) <= 48) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39413 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 48 && getVariable(0x37016224L /*_Waypointposition*/) <= 49) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39414 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 49 && getVariable(0x37016224L /*_Waypointposition*/) <= 50) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39415 });
		}
		if (changeState(state -> Teleport_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_Type2(blendTime), 5000 + Rnd.get(-500,500)));
	}

	protected void Select_Position_Type3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEA682222L /*Select_Position_Type3*/);
		setVariable(0x37016224L /*_Waypointposition*/, getRandom(45));
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39416 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 1 && getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39417 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 2 && getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39418 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 3 && getVariable(0x37016224L /*_Waypointposition*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39419 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4 && getVariable(0x37016224L /*_Waypointposition*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39420 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 5 && getVariable(0x37016224L /*_Waypointposition*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39421 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 6 && getVariable(0x37016224L /*_Waypointposition*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39422 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 7 && getVariable(0x37016224L /*_Waypointposition*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39423 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 8 && getVariable(0x37016224L /*_Waypointposition*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39424 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 9 && getVariable(0x37016224L /*_Waypointposition*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39425 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 10 && getVariable(0x37016224L /*_Waypointposition*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39426 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 11 && getVariable(0x37016224L /*_Waypointposition*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39427 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 12 && getVariable(0x37016224L /*_Waypointposition*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39428 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 13 && getVariable(0x37016224L /*_Waypointposition*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39429 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 14 && getVariable(0x37016224L /*_Waypointposition*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39430 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 15 && getVariable(0x37016224L /*_Waypointposition*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39431 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 16 && getVariable(0x37016224L /*_Waypointposition*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39432 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 17 && getVariable(0x37016224L /*_Waypointposition*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39433 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 18 && getVariable(0x37016224L /*_Waypointposition*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39434 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 19 && getVariable(0x37016224L /*_Waypointposition*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39435 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 20 && getVariable(0x37016224L /*_Waypointposition*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39436 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 21 && getVariable(0x37016224L /*_Waypointposition*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39437 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 22 && getVariable(0x37016224L /*_Waypointposition*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39438 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 23 && getVariable(0x37016224L /*_Waypointposition*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39439 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 24 && getVariable(0x37016224L /*_Waypointposition*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39440 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 25 && getVariable(0x37016224L /*_Waypointposition*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39441 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 26 && getVariable(0x37016224L /*_Waypointposition*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39442 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 27 && getVariable(0x37016224L /*_Waypointposition*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39443 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 28 && getVariable(0x37016224L /*_Waypointposition*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39444 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 29 && getVariable(0x37016224L /*_Waypointposition*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39445 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 30 && getVariable(0x37016224L /*_Waypointposition*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39446 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 31 && getVariable(0x37016224L /*_Waypointposition*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39447 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 32 && getVariable(0x37016224L /*_Waypointposition*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39448 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 33 && getVariable(0x37016224L /*_Waypointposition*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39449 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 34 && getVariable(0x37016224L /*_Waypointposition*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39450 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 35 && getVariable(0x37016224L /*_Waypointposition*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39451 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 36 && getVariable(0x37016224L /*_Waypointposition*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39452 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 37 && getVariable(0x37016224L /*_Waypointposition*/) <= 38) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39453 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 38 && getVariable(0x37016224L /*_Waypointposition*/) <= 39) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39454 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 39 && getVariable(0x37016224L /*_Waypointposition*/) <= 40) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39455 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 40 && getVariable(0x37016224L /*_Waypointposition*/) <= 41) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39456 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 41 && getVariable(0x37016224L /*_Waypointposition*/) <= 42) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39457 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 42 && getVariable(0x37016224L /*_Waypointposition*/) <= 43) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39458 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 43 && getVariable(0x37016224L /*_Waypointposition*/) <= 44) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39459 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 44 && getVariable(0x37016224L /*_Waypointposition*/) <= 45) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39460 });
		}
		if (changeState(state -> Teleport_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_Type3(blendTime), 5000 + Rnd.get(-500,500)));
	}

	protected void Select_Position_Type4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBDED7DD5L /*Select_Position_Type4*/);
		setVariable(0x37016224L /*_Waypointposition*/, getRandom(50));
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39461 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 1 && getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39462 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 2 && getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39463 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 3 && getVariable(0x37016224L /*_Waypointposition*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39464 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4 && getVariable(0x37016224L /*_Waypointposition*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39465 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 5 && getVariable(0x37016224L /*_Waypointposition*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39466 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 6 && getVariable(0x37016224L /*_Waypointposition*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39467 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 7 && getVariable(0x37016224L /*_Waypointposition*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39468 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 8 && getVariable(0x37016224L /*_Waypointposition*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39469 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 9 && getVariable(0x37016224L /*_Waypointposition*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39470 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 10 && getVariable(0x37016224L /*_Waypointposition*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39471 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 11 && getVariable(0x37016224L /*_Waypointposition*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39472 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 12 && getVariable(0x37016224L /*_Waypointposition*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39473 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 13 && getVariable(0x37016224L /*_Waypointposition*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39474 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 14 && getVariable(0x37016224L /*_Waypointposition*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39475 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 15 && getVariable(0x37016224L /*_Waypointposition*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39476 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 16 && getVariable(0x37016224L /*_Waypointposition*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39477 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 17 && getVariable(0x37016224L /*_Waypointposition*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39478 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 18 && getVariable(0x37016224L /*_Waypointposition*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39479 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 19 && getVariable(0x37016224L /*_Waypointposition*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39480 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 20 && getVariable(0x37016224L /*_Waypointposition*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39481 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 21 && getVariable(0x37016224L /*_Waypointposition*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39482 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 22 && getVariable(0x37016224L /*_Waypointposition*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39483 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 23 && getVariable(0x37016224L /*_Waypointposition*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39484 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 24 && getVariable(0x37016224L /*_Waypointposition*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39485 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 25 && getVariable(0x37016224L /*_Waypointposition*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39486 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 26 && getVariable(0x37016224L /*_Waypointposition*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39487 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 27 && getVariable(0x37016224L /*_Waypointposition*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39488 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 28 && getVariable(0x37016224L /*_Waypointposition*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39489 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 29 && getVariable(0x37016224L /*_Waypointposition*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39490 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 30 && getVariable(0x37016224L /*_Waypointposition*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39491 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 31 && getVariable(0x37016224L /*_Waypointposition*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39492 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 32 && getVariable(0x37016224L /*_Waypointposition*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39493 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 33 && getVariable(0x37016224L /*_Waypointposition*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39494 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 34 && getVariable(0x37016224L /*_Waypointposition*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39495 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 35 && getVariable(0x37016224L /*_Waypointposition*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39496 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 36 && getVariable(0x37016224L /*_Waypointposition*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39497 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 37 && getVariable(0x37016224L /*_Waypointposition*/) <= 38) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39498 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 38 && getVariable(0x37016224L /*_Waypointposition*/) <= 39) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39499 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 39 && getVariable(0x37016224L /*_Waypointposition*/) <= 40) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39500 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 40 && getVariable(0x37016224L /*_Waypointposition*/) <= 41) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39501 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 41 && getVariable(0x37016224L /*_Waypointposition*/) <= 42) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39502 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 42 && getVariable(0x37016224L /*_Waypointposition*/) <= 43) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39503 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 43 && getVariable(0x37016224L /*_Waypointposition*/) <= 44) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39504 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 44 && getVariable(0x37016224L /*_Waypointposition*/) <= 45) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39505 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 45 && getVariable(0x37016224L /*_Waypointposition*/) <= 46) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39506 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 46 && getVariable(0x37016224L /*_Waypointposition*/) <= 47) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39507 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 47 && getVariable(0x37016224L /*_Waypointposition*/) <= 48) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39508 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 48 && getVariable(0x37016224L /*_Waypointposition*/) <= 49) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39509 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 49 && getVariable(0x37016224L /*_Waypointposition*/) <= 50) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39510 });
		}
		if (changeState(state -> Teleport_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_Type4(blendTime), 5000 + Rnd.get(-500,500)));
	}

	protected void Select_Position_Type5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x99523EE4L /*Select_Position_Type5*/);
		setVariable(0x37016224L /*_Waypointposition*/, getRandom(23));
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39511 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 1 && getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39512 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 2 && getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39516 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 3 && getVariable(0x37016224L /*_Waypointposition*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39525 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4 && getVariable(0x37016224L /*_Waypointposition*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39526 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 5 && getVariable(0x37016224L /*_Waypointposition*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39529 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 6 && getVariable(0x37016224L /*_Waypointposition*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39530 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 7 && getVariable(0x37016224L /*_Waypointposition*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39531 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 8 && getVariable(0x37016224L /*_Waypointposition*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39532 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 9 && getVariable(0x37016224L /*_Waypointposition*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39533 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 10 && getVariable(0x37016224L /*_Waypointposition*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39534 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 11 && getVariable(0x37016224L /*_Waypointposition*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39535 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 12 && getVariable(0x37016224L /*_Waypointposition*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39536 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 13 && getVariable(0x37016224L /*_Waypointposition*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39537 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 14 && getVariable(0x37016224L /*_Waypointposition*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39538 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 15 && getVariable(0x37016224L /*_Waypointposition*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39539 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 16 && getVariable(0x37016224L /*_Waypointposition*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39540 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 17 && getVariable(0x37016224L /*_Waypointposition*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39541 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 18 && getVariable(0x37016224L /*_Waypointposition*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39542 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 19 && getVariable(0x37016224L /*_Waypointposition*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39543 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 20 && getVariable(0x37016224L /*_Waypointposition*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39544 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 21 && getVariable(0x37016224L /*_Waypointposition*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39545 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 22 && getVariable(0x37016224L /*_Waypointposition*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 39546 });
		}
		if (changeState(state -> Teleport_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_Type5(blendTime), 5000 + Rnd.get(-500,500)));
	}

	protected void Teleport_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCCD414B8L /*Teleport_Position*/);
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Trigger_1F(blendTime));
	}

	protected void Trigger_1F(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCB35C09FL /*Trigger_1F*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, true, object -> getDistanceToTarget(object) < 5000 && getTargetCharacterKey(object) == 22087)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 180000) {
			if (changeState(state -> Trigger_2F(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> Reset(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Trigger_1F(blendTime), 2000));
	}

	protected void Trigger_2F(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x83B2358CL /*Trigger_2F*/);
		setVariable(0xDC8695E5L /*DiceCount*/, getVariable(0xDC8695E5L /*DiceCount*/) + 1);
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00 && getPartyMembersCount()< 1) {
			if(Rnd.getChance(4)) {
				if (changeState(state -> Summon_Raiten(blendTime)))
					return;
			}
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00 && getPartyMembersCount()< 1 && getVariable(0xDC8695E5L /*DiceCount*/) > 70) {
			if (changeState(state -> Summon_Raiten(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> Reset(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Trigger_2F(blendTime), 2000));
	}

	protected void Trigger_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2F2D0185L /*Trigger_1*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 900000) {
			if (changeState(state -> Trigger_2(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> Reset(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Trigger_1(blendTime), 2000));
	}

	protected void Trigger_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB29E49FBL /*Trigger_2*/);
		setVariable(0xDC8695E5L /*DiceCount*/, getVariable(0xDC8695E5L /*DiceCount*/) + 1);
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00 && getPartyMembersCount()< 1) {
			if(Rnd.getChance(2)) {
				if (changeState(state -> Summon_Raiten(blendTime)))
					return;
			}
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00 && getPartyMembersCount()< 1 && getVariable(0xDC8695E5L /*DiceCount*/) > 140) {
			if (changeState(state -> Summon_Raiten(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> Reset(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Trigger_2(blendTime), 2000));
	}

	protected void Summon_Raiten(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x19392194L /*Summon_Raiten*/);
		setVariable(0xDC8695E5L /*DiceCount*/, 0);
		setVariable(0xE289FF90L /*First_Spwan*/, getVariable(0xE289FF90L /*First_Spwan*/) + 1);
		doAction(2372044356L /*SUMMON_RAITEN*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Reset(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x408B818FL /*Reset*/);
		setVariable(0xDC8695E5L /*DiceCount*/, 0);
		setVariable(0xE289FF90L /*First_Spwan*/, 0);
		changeState(state -> Wait(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
