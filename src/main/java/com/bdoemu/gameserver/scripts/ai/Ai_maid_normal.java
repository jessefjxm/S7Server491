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
@IAIName("maid_normal")
public class Ai_maid_normal extends CreatureAI {
	public Ai_maid_normal(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0xB680743BL /*_IsWarehouse*/, getVariable(0x14BDEF98L /*AI_IsWarehouse*/));
		setVariable(0x4F1DEA84L /*_IsMarket*/, getVariable(0x82D2073FL /*AI_IsMarket*/));
		setVariable(0x3184D4A1L /*_Housing_Greeting_Count*/, 0);
		if (getVariable(0xD01879C3L /*ai_maid_type*/) == 2) {
			if (changeState(state -> Welcome_str(0.3)))
				return;
		}
		if (getVariable(0xD01879C3L /*ai_maid_type*/) == 1) {
			if (changeState(state -> Summon_Greeting_str(0.3)))
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
		if (getVariable(0xD01879C3L /*ai_maid_type*/) == 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Cleaning(0.3)))
					return;
			}
		}
		if (getVariable(0xD01879C3L /*ai_maid_type*/) == 0) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Move_Random(0.3)))
					return;
			}
		}
		if (getVariable(0xD01879C3L /*ai_maid_type*/) == 1) {
			if (changeState(state -> Summon_Greeting_str(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Housing_Welcome(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x633402FDL /*Housing_Welcome*/);
		setVariable(0x3184D4A1L /*_Housing_Greeting_Count*/, 1);
		doAction(4269266756L /*SUMMON_GREETING*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
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
		doAction(1905714489L /*LOGOUT_GREETING*/, blendTime, onDoActionEnd -> scheduleState(state -> LogOut(blendTime), 1000));
	}

	protected void Welcome_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE28C6348L /*Welcome_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> LogOut(blendTime), 500));
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

	protected void Summon_Greeting_str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x77077C3CL /*Summon_Greeting_str*/);
		doAction(2799348963L /*SUMMON_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Greeting(blendTime), 1000));
	}

	protected void Summon_Greeting(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9A4B72C6L /*Summon_Greeting*/);
		doAction(4269266756L /*SUMMON_GREETING*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Greeting_Wait(blendTime), 1000));
	}

	protected void Summon_Greeting_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA562F031L /*Summon_Greeting_Wait*/);
		doAction(3602854557L /*SUMMON_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Greeting_Wait(blendTime), 1000));
	}

	protected void OwnerTeleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCB263523L /*OwnerTeleport*/);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 100, 1, 1);
		doAction(2799348963L /*SUMMON_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> OwnerTeleport_Turn(blendTime), 100));
	}

	protected void OwnerTeleport_Turn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xE29DF4DFL /*OwnerTeleport_Turn*/);
		doAction(2799348963L /*SUMMON_STR*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToParent, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Greeting_Wait(blendTime), 100)));
	}

	protected void LogOut_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E9EAC74L /*LogOut_Wait*/);
		doAction(1067482578L /*LOGOUT_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> LogOut(blendTime), 1000));
	}

	protected void LogOut_Greeting(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x60593D26L /*LogOut_Greeting*/);
		doAction(1905714489L /*LOGOUT_GREETING*/, blendTime, onDoActionEnd -> scheduleState(state -> LogOut(blendTime), 1000));
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
	public EAiHandlerResult _StrAllmaidLogOut(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> LogOut(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _maidLogOut(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 1200) {
			if (changeState(state -> LogOut(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 1200) {
			if (changeState(state -> LogOut_Greeting(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _warehouseMaidLogOut(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xB680743BL /*_IsWarehouse*/) == 1) {
			if (changeState(state -> LogOut(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _marketMaid(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 1200 && getVariable(0x4F1DEA84L /*_IsMarket*/) == 1) {
			if (changeState(state -> OwnerTeleport(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 1200 && getVariable(0x4F1DEA84L /*_IsMarket*/) == 1) {
			if (changeState(state -> Summon_Greeting_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _marketMaidLogOut(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x4F1DEA84L /*_IsMarket*/) == 1) {
			if (changeState(state -> LogOut(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _warehouseMaid(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwnerNotFormation());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 1200 && getVariable(0xB680743BL /*_IsWarehouse*/) == 1) {
			if (changeState(state -> OwnerTeleport(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) <= 1200 && getVariable(0xB680743BL /*_IsWarehouse*/) == 1) {
			if (changeState(state -> Summon_Greeting_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
