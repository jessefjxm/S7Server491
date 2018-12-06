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
@IAIName("ominousscarecrow")
public class Ai_ominousscarecrow extends CreatureAI {
	public Ai_ominousscarecrow(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(2235988577L /*START_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Blaze_UP(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1EF22130L /*Blaze_UP*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Blaze_UP2(0.3)))
				return;
		}
		doAction(466796970L /*BLAZE_UP*/, blendTime, onDoActionEnd -> scheduleState(state -> Blaze_UP(blendTime), 1000));
	}

	protected void Blaze_UP2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x34B838FAL /*Blaze_UP2*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Die(0.3)))
				return;
		}
		doAction(520793189L /*BLAZE_UP2*/, blendTime, onDoActionEnd -> scheduleState(state -> Blaze_UP2(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTalkToDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x1EF22130L /*Blaze_UP*/) {
			if (changeState(state -> Blaze_UP(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
