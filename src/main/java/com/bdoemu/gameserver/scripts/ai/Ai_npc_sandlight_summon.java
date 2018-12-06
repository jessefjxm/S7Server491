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
@IAIName("npc_sandlight_summon")
public class Ai_npc_sandlight_summon extends CreatureAI {
	public Ai_npc_sandlight_summon(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x20AF4AEAL /*_GrowPoint*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Wait(blendTime), 1000));
	}

	protected void Summon_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE4A6F0FL /*Summon_Wait*/);
		if (findTarget(EAIFindTargetType.Npc, EAIFindType.normal, true, object -> getDistanceToTarget(object) < 1000 && getTargetCharacterKey(object) == 37133)) {
			if (changeState(state -> Wait_Hide(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon_Kamasylve(blendTime), 1000));
	}

	protected void Summon_Kamasylve(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5618E567L /*Summon_Kamasylve*/);
		doAction(921066669L /*SUMMON_KAMASYLVE*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Mission_Complete(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x99181DBBL /*Mission_Complete*/);
		doAction(286551674L /*MISSION_COMPLETE*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 2500) {
			if (changeState(state -> Mission_Complete(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_Hide(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC34C44EDL /*Wait_Hide*/);
		doAction(2877983296L /*WAIT_HIDE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Hide(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Growing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCFE0FBB0L /*Growing*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandlerSendToGrow(getActor(), null));
		setVariable(0x20AF4AEAL /*_GrowPoint*/, getVariable(0x20AF4AEAL /*_GrowPoint*/) + 1);
		scheduleState(state -> Wait(blendTime), 1000);
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTalkToGrow(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0xCFE0FBB0L /*Growing*/ && getState() != 0x5618E567L /*Summon_Kamasylve*/) {
			if (changeState(state -> Growing(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerSendToEnd(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0xCFE0FBB0L /*Growing*/ && getState() != 0x99181DBBL /*Mission_Complete*/) {
			if (changeState(state -> Mission_Complete(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
