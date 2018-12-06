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
@IAIName("catapult")
public class Ai_catapult extends CreatureAI {
	public Ai_catapult(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0x19EB970L /*_WayPointLoopCount*/, getVariable(0xABA96D52L /*AI_WayPointLoopCount*/));
		setVariable(0x610E183EL /*_WayPointType*/, getVariable(0xC687E0D9L /*AI_WayPointType*/));
		setVariable(0x692F3C5CL /*_WayPointKey1*/, getVariable(0x3DFFB456L /*AI_WayPointKey1*/));
		setVariableArray(0x2C4960E5L /*_WaypointValue1*/, new Integer[] { getVariable(0x610E183EL /*_WayPointType*/), getVariable(0x692F3C5CL /*_WayPointKey1*/) });
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if(Rnd.getChance(getVariable(0xBAEE337BL /*AI_BT_RangeAttack3*/))) {
			if (changeState(state -> Battle_RangeAttack3(blendTime)))
				return;
		}
		if(Rnd.getChance(getVariable(0xA03B8B01L /*AI_BT_RangeAttack2*/))) {
			if (changeState(state -> Battle_RangeAttack2(blendTime)))
				return;
		}
		if(Rnd.getChance(getVariable(0xDAB0B4A8L /*AI_BT_RangeAttack1*/))) {
			if (changeState(state -> Battle_RangeAttack1(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 0 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1) {
			if (changeState(state -> Movepoint_Logic(0.2)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 9000 + Rnd.get(-500,500)));
	}

	protected void Movepoint_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x244DE952L /*Movepoint_Logic*/);
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 0 && getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1) {
			if (changeState(state -> Move_WayPoint(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Move_WayPoint(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8F2D3B47L /*Move_WayPoint*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Battle_RangeAttack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC349CD1FL /*Battle_RangeAttack1*/);
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Battle_RangeAttack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD6E1AEE4L /*Battle_RangeAttack2*/);
		doAction(376694480L /*BATTLE_RANGEATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	protected void Battle_RangeAttack3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x395B66CCL /*Battle_RangeAttack3*/);
		doAction(4032514383L /*BATTLE_RANGEATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Logic(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
