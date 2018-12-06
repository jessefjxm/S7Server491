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
@IAIName("selfdestructbomb")
public class Ai_selfdestructbomb extends CreatureAI {
	public Ai_selfdestructbomb(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stop(blendTime), 1000));
	}

	protected void Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x29A56175L /*Stop*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 300 && getTargetHp(object) > 0)) {
			if (changeState(state -> Destruct_Att(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stop(blendTime), 500));
	}

	protected void Destruct_Att(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1F8EA445L /*Destruct_Att*/);
		if (isTargetLost()) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(3651567961L /*DESTRUCT_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Die_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x277B023DL /*Die_Ready*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1500));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Destruct_Att(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Call_stop(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Stop(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Call_remove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die_Ready(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
