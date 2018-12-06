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
@IAIName("npc_calpheon_guard")
public class Ai_npc_calpheon_guard extends CreatureAI {
	public Ai_npc_calpheon_guard(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> GoBack(blendTime)))
				return;
		}
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> WaitHome(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_1(blendTime), 10000));
	}

	protected void Wait_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x96AB8779L /*Wait_1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_2(blendTime), 10000));
	}

	protected void Wait_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD6E9C46CL /*Wait_2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_3(blendTime), 10000));
	}

	protected void WaitHome(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE65762BFL /*WaitHome*/);
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 || getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> GoBack(blendTime)))
				return;
		}
		doAction(408565085L /*HOME*/, blendTime, onDoActionEnd -> scheduleState(state -> WaitHome(blendTime), 10000));
	}

	protected void Wait_Idle1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFD76F44DL /*Wait_Idle1*/);
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> GoToHome(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_1(blendTime), 50000));
	}

	protected void Wait_Idle2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x637E7B28L /*Wait_Idle2*/);
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> GoToHome(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_2(blendTime), 50000));
	}

	protected void Wait_Idle3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE7C1385L /*Wait_Idle3*/);
		if (getGameTimeHour() >= 02 && getGameTimeMinute() >= 00 && getGameTimeHour() <= 22 && getGameTimeMinute() <= 00) {
			if (changeState(state -> GoToHome(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 50000));
	}

	protected void Move_Roaming_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x677D24EL /*Move_Roaming_1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc_route", "waypoint_route", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle1(blendTime), 10000)));
	}

	protected void Move_Roaming_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1D2A771DL /*Move_Roaming_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc_route", "waypoint_route", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle2(blendTime), 10000)));
	}

	protected void Move_Roaming_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF6C00942L /*Move_Roaming_3*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc_route", "waypoint_route", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle3(blendTime), 10000)));
	}

	protected void GoToHome(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD03A9532L /*GoToHome*/);
		doAction(340406367L /*GOHOME*/, blendTime, onDoActionEnd -> moveToWaypoint("npc_route", "waypoint_route", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> WaitHome(blendTime), 10000)));
	}

	protected void GoBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDB128473L /*GoBack*/);
		doAction(853854709L /*GOBACK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc_route", "waypoint_route", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Turn_Orign(blendTime), 1000)));
	}

	protected void Turn_Orign(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xB69F39BAL /*Turn_Orign*/);
		doAction(2738613969L /*STOP2*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 30000)));
	}

	protected void Fright(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x654BC56AL /*Fright*/);
		doAction(32379601L /*FRIGHT*/, blendTime, onDoActionEnd -> scheduleState(state -> WaitHome(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
