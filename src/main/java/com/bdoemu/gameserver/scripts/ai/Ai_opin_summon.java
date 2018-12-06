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
@IAIName("opin_summon")
public class Ai_opin_summon extends CreatureAI {
	public Ai_opin_summon(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x4E6C22F5L /*_Before_die*/, 0);
		setVariable(0x9E02F645L /*_TimeAttack_Start*/, 0);
		setVariable(0xEC993117L /*_TimeAttack_Ing*/, 0);
		setVariable(0x705919FFL /*_TimeAttack_End*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x34E5AE02L /*Summon*/);
		doAction(3635031213L /*SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void TimeAttack_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2F7CB3BDL /*TimeAttack_Logic*/);
		setVariable(0x9E02F645L /*_TimeAttack_Start*/, getTime());
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TimeAttack(blendTime), 1000));
	}

	protected void TimeAttack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5568B3AFL /*TimeAttack*/);
		setVariable(0xEC993117L /*_TimeAttack_Ing*/, getTime());
		setVariable(0x705919FFL /*_TimeAttack_End*/, getVariable(0xEC993117L /*_TimeAttack_Ing*/) - getVariable(0x9E02F645L /*_TimeAttack_Start*/));
		if (getVariable(0x705919FFL /*_TimeAttack_End*/) >= 1800000) {
			if (changeState(state -> Delete_Die(0.2)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TimeAttack(blendTime), 1000));
	}

	protected void Before_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB1B32239L /*Before_Die*/);
		setVariable(0x4E6C22F5L /*_Before_die*/, getTime());
		if(getCallCount() == 600) {
			if (changeState(state -> Die(0.2)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			// Missing AI Handler: Before_Die : send_command()
			scheduleState(state -> Before_Die(blendTime), 1000);
		});
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD43BC680L /*Delete_Die*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Delete_Die(getActor(), null));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
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
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (changeState(state -> Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Parent_Die(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Before_Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult TimeAttack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> TimeAttack_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
