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
@IAIName("npc_questtalk_type1")
public class Ai_npc_questtalk_type1 extends CreatureAI {
	public Ai_npc_questtalk_type1(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Logic(blendTime), 1000));
	}

	protected void Wait_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9048C900L /*Wait_Logic*/);
		changeState(state -> Wait(blendTime));
	}

	protected void QuestIng_Wait_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x17B975AFL /*QuestIng_Wait_Str*/);
		doAction(1716795715L /*QUEST_WAIT_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> QuestIng_Wait_Ing(blendTime), 1000));
	}

	protected void QuestIng_Wait_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x57311BFCL /*QuestIng_Wait_Ing*/);
		if(getCallCount() == 180) {
			if (changeState(state -> QuestIng_Wait_End(0.2)))
				return;
		}
		doAction(391437532L /*QUEST_WAIT_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> QuestIng_Wait_Ing(blendTime), 1000));
	}

	protected void QuestIng_Wait_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBAE1920FL /*QuestIng_Wait_End*/);
		doAction(791194244L /*QUEST_WAIT_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
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
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (getState() != 0xB66FE623L /*QuestIng_Wait*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> QuestIng_Wait_Str(0.2)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
