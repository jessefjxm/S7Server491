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
@IAIName("alter_shrine3")
public class Ai_alter_shrine3 extends CreatureAI {
	public Ai_alter_shrine3(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Rotate(blendTime), 1000));
	}

	protected void Wait_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC6855029L /*Wait_Rotate*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Rotate(blendTime), 1000));
	}

	protected void TurningPoint1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xBDFA3D7FL /*TurningPoint1*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -384168, -305881, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Start_Action(blendTime), 1000)));
	}

	protected void TurningPoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x66893464L /*TurningPoint2*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -399219, -312022, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Start_Action(blendTime), 1000)));
	}

	protected void TurningPoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xEAA203F4L /*TurningPoint3*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -400433, -306348, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Start_Action(blendTime), 1000)));
	}

	protected void TurningPoint4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x1539681EL /*TurningPoint4*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -391385, -300603, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Start_Action(blendTime), 1000)));
	}

	protected void TurningPoint5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x2B8E09ADL /*TurningPoint5*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -374385, -295091, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Start_Action(blendTime), 1000)));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(2232394283L /*START_ACTION_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0x3F487035L /*_HP*/) <= 80) {
			if (changeState(state -> Wait_80_Logic(0.1)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_80_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x99E079DAL /*Wait_80_Logic*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_4STAGE_80");
		changeState(state -> Wait_80(blendTime));
	}

	protected void Wait_80(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE5C5DE56L /*Wait_80*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0x3F487035L /*_HP*/) <= 60) {
			if (changeState(state -> Wait_60_Logic(0.1)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_80(blendTime), 1000));
	}

	protected void Wait_60_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xAFC69504L /*Wait_60_Logic*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_4STAGE_60");
		changeState(state -> Wait_60_A(blendTime));
	}

	protected void Wait_60_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x91F60F0BL /*Wait_60_A*/);
		doAction(3623768408L /*BREAK*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_60_B(blendTime), 1000));
	}

	protected void Wait_60_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DBE6E53L /*Wait_60_B*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0x3F487035L /*_HP*/) <= 40) {
			if (changeState(state -> Wait_40_Logic(0.1)))
				return;
		}
		doAction(1569894142L /*WAIT_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_60_B(blendTime), 1000));
	}

	protected void Wait_40_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8D60CA77L /*Wait_40_Logic*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_4STAGE_40");
		changeState(state -> Wait_40(blendTime));
	}

	protected void Wait_40(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7C635154L /*Wait_40*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0x3F487035L /*_HP*/) <= 20) {
			if (changeState(state -> Wait_20_Logic(0.1)))
				return;
		}
		doAction(1569894142L /*WAIT_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_40(blendTime), 1000));
	}

	protected void Wait_20_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x17D6D85CL /*Wait_20_Logic*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_4STAGE_20");
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_STAGE_WARNING");
		changeState(state -> Wait_20_A(blendTime));
	}

	protected void Wait_20_A(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD1272611L /*Wait_20_A*/);
		doAction(2364795313L /*BREAK2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_20_B(blendTime), 1000));
	}

	protected void Wait_20_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7D3146DFL /*Wait_20_B*/);
		doAction(800819627L /*WAIT_2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_20_B(blendTime), 1000));
	}

	protected void Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE70D4D89L /*Die_Logic*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_DAMAGE_4STAGE_0");
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().Destroyed_Shrine(getActor(), null));
		changeState(state -> Destroy(blendTime));
	}

	protected void Destroy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2C3A3C10L /*Destroy*/);
		doAction(3233702419L /*DESTROY*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 5000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 2000));
	}

	protected void Speed_Up(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x19FE7D55L /*Speed_Up*/);
		doAction(3620847424L /*SPEEDUP*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Speed_Up1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDC62FD29L /*Speed_Up1*/);
		doAction(2711525908L /*SPEEDUP1*/, blendTime, onDoActionEnd -> changeState(state -> Wait_80(blendTime)));
	}

	protected void Speed_Up2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE785F78AL /*Speed_Up2*/);
		doAction(426413179L /*SPEEDUP2*/, blendTime, onDoActionEnd -> changeState(state -> Wait_60_B(blendTime)));
	}

	protected void Speed_Up3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8FBCF0C8L /*Speed_Up3*/);
		doAction(3935137914L /*SPEEDUP3*/, blendTime, onDoActionEnd -> changeState(state -> Wait_40(blendTime)));
	}

	protected void Speed_Up4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEAD20D2AL /*Speed_Up4*/);
		doAction(2098074272L /*SPEEDUP4*/, blendTime, onDoActionEnd -> changeState(state -> Wait_20_B(blendTime)));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2737950888L /*DELETE_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 3000));
	}

	protected void Delete_Die1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3C26033L /*Delete_Die1*/);
		doAction(3918303016L /*DELETE_DIE_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 3000));
	}

	protected void Delete_Die2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7F18E68CL /*Delete_Die2*/);
		doAction(3180410719L /*DELETE_DIE_2*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 3000));
	}

	protected void Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x34E5AE02L /*Summon*/);
		doAction(3635031213L /*SUMMON*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Fake(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Summon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult SpeedUp(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0xD1272611L /*Wait_20_A*/ || getState() == 0x7D3146DFL /*Wait_20_B*/)) {
			if (changeState(state -> Speed_Up4(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x7C635154L /*Wait_40*/) {
			if (changeState(state -> Speed_Up3(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x91F60F0BL /*Wait_60_A*/ || getState() == 0x7DBE6E53L /*Wait_60_B*/)) {
			if (changeState(state -> Speed_Up2(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xE5C5DE56L /*Wait_80*/) {
			if (changeState(state -> Speed_Up1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (changeState(state -> Speed_Up(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Disappear(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0xD1272611L /*Wait_20_A*/ || getState() == 0x7D3146DFL /*Wait_20_B*/)) {
			if (changeState(state -> Delete_Die2(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0x7C635154L /*Wait_40*/ || getState() == 0x91F60F0BL /*Wait_60_A*/ || getState() == 0x7DBE6E53L /*Wait_60_B*/)) {
			if (changeState(state -> Delete_Die1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if ((getState() == 0xE5C5DE56L /*Wait_80*/ || getState() == 0x866C7489L /*Wait*/)) {
			if (changeState(state -> Delete_Die(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
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
