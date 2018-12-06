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
@IAIName("alter_5_ancientworm")
public class Ai_alter_5_ancientworm extends CreatureAI {
	public Ai_alter_5_ancientworm(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x37016224L /*_Waypointposition*/, 0);
		setVariable(0xACD660AFL /*WaypointValue*/, 0);
		setVariable(0x75F98DEL /*_InhaleCount*/, 0);
		doAction(3820701766L /*START_ACTION_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Teleport(blendTime), 1000));
	}

	protected void Start_Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x53798F54L /*Start_Teleport*/);
		setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 40061 });
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Start_Wait(blendTime));
	}

	protected void Start_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9D7E92CDL /*Start_Wait*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Rotate_Logic(blendTime)))
				return;
		}
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Wait(blendTime), 1000));
	}

	protected void Rotate_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x65942A11L /*Rotate_Logic*/);
		if (checkParentInstanceTeamNo()) {
			if (findTarget(EAIFindTargetType.Monster, EAIFindType.nearest, false, object -> getDistanceToTarget(object) < 10000 && getTargetCharacterKey(object) == 27138)) {
				if (changeState(state -> Wait_Rotate(blendTime)))
					return;
			}
		}
		changeState(state -> Rotate_Logic(blendTime));
	}

	protected void Wait_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC6855029L /*Wait_Rotate*/);
		doAction(2910378664L /*WAIT_ROTATE*/, blendTime, onDoActionEnd -> changeState(state -> Start_Wait2(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Start_Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA0BA4DD9L /*Start_Wait2*/);
		doAction(3820701766L /*START_ACTION_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Ground_Up(blendTime), 1000));
	}

	protected void Start_Ground_Up(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xADBE3D76L /*Start_Ground_Up*/);
		doAction(555451679L /*UNDERGROUND_END*/, blendTime, onDoActionEnd -> {
			instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_5STAGE_KUTUM");
			scheduleState(state -> Start_Wait3(blendTime), 500);
		});
	}

	protected void Start_Wait3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7967BDCDL /*Start_Wait3*/);
		if(getCallCount() == 10) {
			if (changeState(state -> Dig(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Wait3(blendTime), 1000));
	}

	protected void Under_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2B164A18L /*Under_Wait*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Area_Select_Logic(blendTime)))
				return;
		}
		doAction(3820701766L /*START_ACTION_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Under_Wait(blendTime), 1000));
	}

	protected void Dig(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA575A5C4L /*Dig*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_5STAGE_KUTUM_START");
		doAction(3503942913L /*START_UNDERGROUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Under_Wait(blendTime), 1000));
	}

	protected void Area_Select_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8F3467B1L /*Area_Select_Logic*/);
		setVariable(0x37016224L /*_Waypointposition*/, getRandom(7));
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 40062 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 1 && getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 40063 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 2 && getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 40064 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 3 && getVariable(0x37016224L /*_Waypointposition*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 40065 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4 && getVariable(0x37016224L /*_Waypointposition*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 40066 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 5 && getVariable(0x37016224L /*_Waypointposition*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 40067 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 6 && getVariable(0x37016224L /*_Waypointposition*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 40068 });
		}
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Attack_Logic(blendTime));
	}

	protected void Area_Select_Logic_Inhale(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x92841FBBL /*Area_Select_Logic_Inhale*/);
		setVariable(0x37016224L /*_Waypointposition*/, getRandom(3));
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 40066 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 1 && getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 40067 });
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 2 && getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 4, 40068 });
		}
		doTeleportToWaypoint("waypoint_variable", "WaypointValue", 0, 0, 1, 1);
		changeState(state -> Stand_Wait_Rotate(blendTime));
	}

	protected void Attack_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x50B0953FL /*Attack_Logic*/);
		setVariable(0x75F98DEL /*_InhaleCount*/, getVariable(0x75F98DEL /*_InhaleCount*/) + 1);
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 4) {
			if (changeState(state -> Dig_Wait_Rotate(blendTime)))
				return;
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4) {
			if (changeState(state -> Stand_Wait_Rotate(blendTime)))
				return;
		}
		changeState(state -> Attack_Logic(blendTime));
	}

	protected void Dig_Wait_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x430CD1C7L /*Dig_Wait_Rotate*/);
		doAction(2910378664L /*WAIT_ROTATE*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Dig(blendTime)));
	}

	protected void Attack_Dig(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9E8E4CBCL /*Attack_Dig*/);
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 3) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> TurnRight(blendTime)))
					return;
			}
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) <= 2) {
			if (changeState(state -> TurnLeft(blendTime)))
				return;
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 4) {
			if (changeState(state -> TurnRight(blendTime)))
				return;
		}
		if (getVariable(0x37016224L /*_Waypointposition*/) > 3) {
			if (changeState(state -> TurnLeft(blendTime)))
				return;
		}
		changeState(state -> Attack_Dig(blendTime));
	}

	protected void TurnRight(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7B661F70L /*TurnRight*/);
		doAction(4007494528L /*WAIT_ROTATE_RIGHT45*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Wait(blendTime)));
	}

	protected void TurnLeft(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA4060912L /*TurnLeft*/);
		doAction(3492396029L /*WAIT_ROTATE_LEFT45*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Wait(blendTime)));
	}

	protected void Attack_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9DBAB6B1L /*Attack_Wait*/);
		doAction(3820701766L /*START_ACTION_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Under_Attack(blendTime), 1000));
	}

	protected void Under_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBA65BF3BL /*Under_Attack*/);
		doAction(420095113L /*BATTLE_DEEPUNDER_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> End_Wait(blendTime)));
	}

	protected void End_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8402CF86L /*End_Wait*/);
		if (getVariable(0x75F98DEL /*_InhaleCount*/) > 2) {
			if(getCallCount() == 5) {
				if (changeState(state -> Area_Select_Logic_Inhale(blendTime)))
					return;
			}
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Area_Select_Logic(blendTime)))
				return;
		}
		doAction(3820701766L /*START_ACTION_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> End_Wait(blendTime), 1000));
	}

	protected void Stand_Wait_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA99DC64EL /*Stand_Wait_Rotate*/);
		doAction(2910378664L /*WAIT_ROTATE*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Wait2(blendTime)));
	}

	protected void Attack_Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA1DCE0E6L /*Attack_Wait2*/);
		doAction(3820701766L /*START_ACTION_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Ground_Up(blendTime), 1000));
	}

	protected void Ground_Up(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3F548BECL /*Ground_Up*/);
		doAction(555451679L /*UNDERGROUND_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Wait3(blendTime), 500));
	}

	protected void Attack_Wait3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x393A462CL /*Attack_Wait3*/);
		if(getCallCount() == 1) {
			if (changeState(state -> Stand_Attack_Select(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Wait3(blendTime), 1000));
	}

	protected void Stand_Attack_Select(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC1BE9D17L /*Stand_Attack_Select*/);
		if (getVariable(0x75F98DEL /*_InhaleCount*/) > 2) {
			if (changeState(state -> Inhale_Attack_Notifier(0.3)))
				return;
		}
		if (changeState(state -> Attack_Range(0.3)))
			return;
		changeState(state -> Stand_Attack_Select(blendTime));
	}

	protected void Inhale_Attack_Notifier(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x18F79FB8L /*Inhale_Attack_Notifier*/);
		instanceNotify(EChatNoticeType.Defence, "GAME", "LUA_DEFENCE_ALTER_5STAGE_KUTUM_INHALE");
		changeState(state -> Inhale_Attack(blendTime));
	}

	protected void Inhale_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x940033B0L /*Inhale_Attack*/);
		setVariable(0x75F98DEL /*_InhaleCount*/, 0);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 8000 && getTargetCharacterKey(object) == 27339).forEach(consumer -> consumer.getAi().Inhale_Start(getActor(), null));
		}
		doAction(63557765L /*INHALE_ATTACK*/, blendTime, onDoActionEnd -> {
			if (checkInstanceTeamNo()) {
				getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 8000 && getTargetCharacterKey(object) == 27339).forEach(consumer -> consumer.getAi().Inhale_Stop(getActor(), null));
			}
			changeState(state -> End_Wait2(blendTime));
		});
	}

	protected void Attack_Range(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x93D918A4L /*Attack_Range*/);
		doAction(3636538705L /*EMISSION_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> End_Wait2(blendTime)));
	}

	protected void End_Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE899E880L /*End_Wait2*/);
		if(getCallCount() == 1) {
			if (changeState(state -> Bye_Dig(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> End_Wait2(blendTime), 1000));
	}

	protected void Bye_Dig(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB9ECF1AFL /*Bye_Dig*/);
		doAction(3589734263L /*UNDERGROUND_STR*/, blendTime, onDoActionEnd -> changeState(state -> End_Wait(blendTime)));
	}

	protected void Attack_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2AD7F81CL /*Attack_Stop*/);
		if (checkInstanceTeamNo()) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 8000 && getTargetCharacterKey(object) == 27339).forEach(consumer -> consumer.getAi().Inhale_Stop(getActor(), null));
		}
		doAction(2031412209L /*BATTLE_ATTACK_STOP*/, blendTime, onDoActionEnd -> scheduleState(state -> End_Wait(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Attack_Start(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Start_Ground_Up(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult TimeStop(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x940033B0L /*Inhale_Attack*/) {
			if (changeState(state -> Attack_Stop(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
