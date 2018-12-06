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
@IAIName("cannonmonster")
public class Ai_cannonmonster extends CreatureAI {
	public Ai_cannonmonster(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x762AB710L /*_UnSummonTime*/, getVariable(0xCC757928L /*AI_UnSummonTime*/));
		setVariable(0x9326AD79L /*_SummonStartTime*/, 0);
		setVariable(0xE8822790L /*_SummonIngTime*/, 0);
		setVariable(0x51EDA18AL /*_SummonEndTime*/, 0);
		setVariable(0x9326AD79L /*_SummonStartTime*/, getTime());
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > getVariable(0xCC757928L /*AI_UnSummonTime*/)) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		// Prevent recursion;
	}

	protected void UnSummon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xCC02DCCFL /*UnSummon*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> UnSummon(blendTime), 1000));
	}

	protected void PartyReleased_state(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x21CFDFDBL /*PartyReleased_state*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(4059115659L /*KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(1872990688L /*KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1872990688L /*KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1872990688L /*KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void UnderWaterDead(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xB63D3C22L /*UnderWaterDead*/);
		clearAggro(true);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> UnderWaterDead(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && checkBuff(target, 13)) {
			if (changeState(state -> Damage_KnockBack(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleUnderWaterDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> UnderWaterDead(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStuned(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyInvited(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> TerminateState(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> PartyReleased_state(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handleTimeout(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> UnSummon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
