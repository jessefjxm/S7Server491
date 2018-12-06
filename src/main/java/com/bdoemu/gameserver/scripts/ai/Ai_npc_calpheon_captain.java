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
@IAIName("npc_calpheon_captain")
public class Ai_npc_calpheon_captain extends CreatureAI {
	public Ai_npc_calpheon_captain(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
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

	protected void Wait_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBC0E69B5L /*Wait_3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_4(blendTime), 10000));
	}

	protected void Wait_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8DBB13B1L /*Wait_4*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_5(blendTime), 10000));
	}

	protected void Wait_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3DBBFC67L /*Wait_5*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_6(blendTime), 10000));
	}

	protected void Wait_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3C7C7125L /*Wait_6*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_7(blendTime), 10000));
	}

	protected void Wait_7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB06AF720L /*Wait_7*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Roaming_8(blendTime), 10000));
	}

	protected void Wait_Idle1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFD76F44DL /*Wait_Idle1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_1(blendTime), 50000));
	}

	protected void Wait_Idle2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x637E7B28L /*Wait_Idle2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_2(blendTime), 50000));
	}

	protected void Wait_Idle3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE7C1385L /*Wait_Idle3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_3(blendTime), 50000));
	}

	protected void Wait_Idle4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2A539086L /*Wait_Idle4*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_4(blendTime), 50000));
	}

	protected void Wait_Idle5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA72A2739L /*Wait_Idle5*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_5(blendTime), 50000));
	}

	protected void Wait_Idle6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x94B0CEB6L /*Wait_Idle6*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_6(blendTime), 50000));
	}

	protected void Wait_Idle7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA93826ACL /*Wait_Idle7*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_7(blendTime), 50000));
	}

	protected void Wait_Idle8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xADB8124EL /*Wait_Idle8*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100000));
	}

	protected void Move_Roaming_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x677D24EL /*Move_Roaming_1*/);
		doAction(1339848291L /*GOBACK_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "South_West_Guard_Camp_Captian_Stop_1", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle1(blendTime), 10000)));
	}

	protected void Move_Roaming_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1D2A771DL /*Move_Roaming_2*/);
		doAction(1339848291L /*GOBACK_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "South_West_Guard_Camp_Captian_Stop_2", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle2(blendTime), 10000)));
	}

	protected void Move_Roaming_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF6C00942L /*Move_Roaming_3*/);
		doAction(1339848291L /*GOBACK_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "South_West_Guard_Camp_Captian_Stop_3", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle3(blendTime), 10000)));
	}

	protected void Move_Roaming_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBDE02CFFL /*Move_Roaming_4*/);
		doAction(1339848291L /*GOBACK_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "South_West_Guard_Camp_Captian_Stop_4", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle4(blendTime), 10000)));
	}

	protected void Move_Roaming_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7AE0C42EL /*Move_Roaming_5*/);
		doAction(1339848291L /*GOBACK_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "South_West_Guard_Camp_Captian_Stop_5_1", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle5(blendTime), 10000)));
	}

	protected void Move_Roaming_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9994A5A4L /*Move_Roaming_6*/);
		doAction(1339848291L /*GOBACK_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "South_West_Guard_Camp_Captian_Stop_5_2", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle5(blendTime), 10000)));
	}

	protected void Move_Roaming_7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8D45640CL /*Move_Roaming_7*/);
		doAction(1339848291L /*GOBACK_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "South_West_Guard_Camp_Captian_Stop_6", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle6(blendTime), 10000)));
	}

	protected void Move_Roaming_8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCA2A1DDEL /*Move_Roaming_8*/);
		doAction(1339848291L /*GOBACK_2*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "South_West_Guard_Camp_Captian_Origin", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Idle7(blendTime), 10000)));
	}

	protected void Fright(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x654BC56AL /*Fright*/);
		doAction(32379601L /*FRIGHT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
