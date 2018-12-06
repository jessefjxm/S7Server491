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
@IAIName("npc_ship_build")
public class Ai_npc_ship_build extends CreatureAI {
	public Ai_npc_ship_build(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 10000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_1(blendTime), 28800000));
	}

	protected void Progress_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xABA9BCC9L /*Progress_1*/);
		doAction(23780394L /*HouseCraft_Progress_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_2(blendTime), 28800000));
	}

	protected void Progress_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD6F6FE24L /*Progress_2*/);
		doAction(3867749376L /*HouseCraft_Progress_2*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_3(blendTime), 28800000));
	}

	protected void Progress_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8436ECD8L /*Progress_3*/);
		doAction(860069660L /*HouseCraft_Progress_3*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_4(blendTime), 28800000));
	}

	protected void Progress_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x30EFC6E8L /*Progress_4*/);
		doAction(1072544903L /*HouseCraft_Progress_4*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_5(blendTime), 28800000));
	}

	protected void Progress_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB183D0C3L /*Progress_5*/);
		doAction(3399238796L /*HouseCraft_Progress_5*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 28800000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
