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
@IAIName("npc_santa_snow")
public class Ai_npc_santa_snow extends CreatureAI {
	public Ai_npc_santa_snow(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x6FF88319L /*_RegionNumber*/, getVariable(0xD5A4B607L /*RegionNumber*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Select_Location(blendTime)));
	}

	protected void Select_Location(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2F1C1489L /*Select_Location*/);
		if (getVariable(0x6FF88319L /*_RegionNumber*/) == 1) {
			if (changeState(state -> Teleport_Balenos(0.3)))
				return;
		}
		if (getVariable(0x6FF88319L /*_RegionNumber*/) == 2) {
			if (changeState(state -> Teleport_Serendia(0.3)))
				return;
		}
		if (getVariable(0x6FF88319L /*_RegionNumber*/) == 3) {
			if (changeState(state -> Teleport_Calpheon(0.3)))
				return;
		}
		if (getVariable(0x6FF88319L /*_RegionNumber*/) == 4) {
			if (changeState(state -> Teleport_Media(0.3)))
				return;
		}
		if (getVariable(0x6FF88319L /*_RegionNumber*/) == 5) {
			if (changeState(state -> Teleport_Valensia(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Location(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Teleport_Balenos(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7C3AA71AL /*Teleport_Balenos*/);
		doTeleportToWaypoint("snow_balenos", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Teleport_Serendia(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3AB2B98DL /*Teleport_Serendia*/);
		doTeleportToWaypoint("snow_serendia", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Teleport_Calpheon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8F6AEC2AL /*Teleport_Calpheon*/);
		doTeleportToWaypoint("snow_calpheon", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Teleport_Media(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC5F9EF2EL /*Teleport_Media*/);
		doTeleportToWaypoint("snow_media", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Teleport_Valensia(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF72176E5L /*Teleport_Valensia*/);
		doTeleportToWaypoint("snow_valensia", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
