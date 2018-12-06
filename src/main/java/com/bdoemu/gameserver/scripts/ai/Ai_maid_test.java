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
@IAIName("maid_test")
public class Ai_maid_test extends CreatureAI {
	public Ai_maid_test(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0x3184D4A1L /*_Housing_Greeting_Count*/, 0);
		if (getVariable(0xD01879C3L /*ai_maid_type*/) == 2) {
			if (changeState(state -> Welcome_str(0.3)))
				return;
		}
		if (getVariable(0xD01879C3L /*ai_maid_type*/) == 1) {
			if (changeState(state -> Greeting_str(0.3)))
				return;
		}
		doAction(3722607087L /*WAIT_Nomesh*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 1000 && getVariable(0x3184D4A1L /*_Housing_Greeting_Count*/) == 0) {
			if (changeState(state -> Greeting_Turn(blendTime)))
				return;
		}
		if (getVariable(0xD01879C3L /*ai_maid_type*/) == 2) {
			if (changeState(state -> Welcome_str(0.3)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Cleaning(0.3)))
				return;
		}
		if(Rnd.getChance(40)) {
			if (changeState(state -> Move_Random(0.3)))
				return;
		}
		if (getVariable(0xD01879C3L /*ai_maid_type*/) == 1) {
			if (changeState(state -> Greeting_str(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Housing_Welcome(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x633402FDL /*Housing_Welcome*/);
		setVariable(0x3184D4A1L /*_Housing_Greeting_Count*/, 1);
		doAction(908189647L /*GREETING*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Greeting_Turn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xE344583AL /*Greeting_Turn*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToParent, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Housing_Welcome(blendTime), 500 + Rnd.get(-1000,1000))));
	}

	protected void Welcome_str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFA9780A9L /*Welcome_str*/);
		doAction(3602854557L /*SUMMON_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Welcome(blendTime), 500));
	}

	protected void Welcome(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1ED7AEB5L /*Welcome*/);
		doAction(908189647L /*GREETING*/, blendTime, onDoActionEnd -> scheduleState(state -> Welcome_Wait(blendTime), 1000));
	}

	protected void Welcome_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE28C6348L /*Welcome_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> LogOut(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 10, 500, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 500)));
	}

	protected void Cleaning(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCA9695CDL /*Cleaning*/);
		if(Rnd.getChance(10)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		if(getCallCount() == 15) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(2893297891L /*CLEANING*/, blendTime, onDoActionEnd -> scheduleState(state -> Cleaning(blendTime), 1000));
	}

	protected void Greeting_str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8637B470L /*Greeting_str*/);
		doAction(3602854557L /*SUMMON_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Greeting(blendTime), 1000));
	}

	protected void Greeting(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x57CB1F04L /*Greeting*/);
		doAction(908189647L /*GREETING*/, blendTime, onDoActionEnd -> scheduleState(state -> Greeting_Wait(blendTime), 1000));
	}

	protected void Greeting_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1E733531L /*Greeting_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Greeting_Wait(blendTime), 1000));
	}

	protected void LogOut_Greeting(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x60593D26L /*LogOut_Greeting*/);
		doAction(908189647L /*GREETING*/, blendTime, onDoActionEnd -> scheduleState(state -> LogOut_Wait(blendTime), 1000));
	}

	protected void LogOut_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E9EAC74L /*LogOut_Wait*/);
		doAction(1067482578L /*LOGOUT_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> LogOut(blendTime), 1000));
	}

	protected void LogOut(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x40BC1B1EL /*LogOut*/);
		logout();
		doAction(3722607087L /*WAIT_Nomesh*/, blendTime, onDoActionEnd -> scheduleState(state -> LogOut(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _maidLogOut(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> LogOut_Greeting(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
