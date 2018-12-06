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
@IAIName("party_leader_eventraidboss")
public class Ai_party_leader_eventraidboss extends CreatureAI {
	public Ai_party_leader_eventraidboss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariableArray(0x68C5BE96L /*WaypointValue1*/, new Integer[] { 0, 0 });
		setVariableArray(0xF99872EDL /*WaypointValue2*/, new Integer[] { 0, 0 });
		changeState(state -> Teleport1(blendTime));
	}

	protected void Teleport1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE9440C24L /*Teleport1*/);
		setVariableArray(0x68C5BE96L /*WaypointValue1*/, new Integer[] { 4, 39568 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue1", 0, 0, 1, 1);
		changeState(state -> Summon1(blendTime));
	}

	protected void Summon1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE0608364L /*Summon1*/);
		doAction(3426935870L /*SUMMON_BOSS1*/, blendTime, onDoActionEnd -> changeState(state -> Teleport2(blendTime)));
	}

	protected void Teleport2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD2C8F9C7L /*Teleport2*/);
		setVariableArray(0xF99872EDL /*WaypointValue2*/, new Integer[] { 4, 39570 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue2", 0, 0, 1, 1);
		changeState(state -> Summon2(blendTime));
	}

	protected void Summon2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4C8DB342L /*Summon2*/);
		doAction(1446067582L /*SUMMON_BOSS2*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
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
