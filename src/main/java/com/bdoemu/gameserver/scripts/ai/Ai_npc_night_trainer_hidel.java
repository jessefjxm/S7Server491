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
@IAIName("npc_night_trainer_hidel")
public class Ai_npc_night_trainer_hidel extends CreatureAI {
	public Ai_npc_night_trainer_hidel(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> GoBack(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_1(blendTime), 100000));
	}

	protected void Wait_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x96AB8779L /*Wait_1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_2(blendTime), 100000));
	}

	protected void Wait_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD6E9C46CL /*Wait_2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_3(blendTime), 100000));
	}

	protected void Wait_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBC0E69B5L /*Wait_3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_4(blendTime), 100000));
	}

	protected void Wait_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8DBB13B1L /*Wait_4*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_5(blendTime), 100000));
	}

	protected void WaitHome(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE65762BFL /*WaitHome*/);
		doAction(408565085L /*HOME*/, blendTime, onDoActionEnd -> scheduleState(state -> HomeDead(blendTime), 100));
	}

	protected void HomeDead(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x6C6077C1L /*HomeDead*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> HomeDead(blendTime), 100));
	}

	protected void Wait_Idle1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFD76F44DL /*Wait_Idle1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_1(blendTime), 100000));
	}

	protected void Wait_Idle2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x637E7B28L /*Wait_Idle2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_2(blendTime), 100000));
	}

	protected void Wait_Idle3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE7C1385L /*Wait_Idle3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_3(blendTime), 100000));
	}

	protected void Wait_Idle4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2A539086L /*Wait_Idle4*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_4(blendTime), 100000));
	}

	protected void Wait_Idle5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA72A2739L /*Wait_Idle5*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100000));
	}

	protected void Move_Roaming_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x677D24EL /*Move_Roaming_1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Night_Trainer_Hidel_Stop1", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle1(blendTime), 10000)));
	}

	protected void Move_Roaming_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1D2A771DL /*Move_Roaming_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Night_Trainer_Hidel_Stop2", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle2(blendTime), 10000)));
	}

	protected void Move_Roaming_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF6C00942L /*Move_Roaming_3*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Night_Trainer_Hidel_Stop3", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle3(blendTime), 10000)));
	}

	protected void Move_Roaming_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBDE02CFFL /*Move_Roaming_4*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Night_Trainer_Hidel_Stop4", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle4(blendTime), 10000)));
	}

	protected void Move_Roaming_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7AE0C42EL /*Move_Roaming_5*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Night_Trainer_Hidel_Arrive_Position", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle5(blendTime), 10000)));
	}

	protected void GoToHome(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD03A9532L /*GoToHome*/);
		doAction(2731866410L /*GOHOME_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Night_Trainer_Hidel_Start", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> WaitHome(blendTime), 10000)));
	}

	protected void GoBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDB128473L /*GoBack*/);
		doAction(1339848291L /*GOBACK_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "Night_Trainer_Hidel_Arrive_Position", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Turn_Orign(blendTime), 1000)));
	}

	protected void Turn_Orign(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB69F39BAL /*Turn_Orign*/);
		doAction(2738613969L /*STOP2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 30000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleAtSpawnEndTime(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> GoToHome(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
