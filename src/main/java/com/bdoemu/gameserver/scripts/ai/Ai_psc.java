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
@IAIName("psc")
public class Ai_psc extends CreatureAI {
	public Ai_psc(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0x1284CE30L /*_isInRest*/, 0);
		setVariable(0xF06310B7L /*_Stance*/, 2);
		setVariable(0x467456F3L /*_DeathTimer*/, 0);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Summon(blendTime), 1000 + Rnd.get(-0,0)));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void Stance_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x181EB08AL /*Stance_50*/);
		if (getVariable(0xF06310B7L /*_Stance*/) == 50) {
			if (changeState(state -> Skill_Sum_Leap(blendTime)))
				return;
		}
		changeState(state -> Skill_Sum_Leap(blendTime));
	}

	protected void Stance_51(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x72B11EE9L /*Stance_51*/);
		if (getVariable(0xF06310B7L /*_Stance*/) == 51) {
			if (changeState(state -> Skill_Sum_Leap(blendTime)))
				return;
		}
		changeState(state -> Skill_Sum_Leap2(blendTime));
	}

	protected void Stance_52(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBF9C74DBL /*Stance_52*/);
		if (getVariable(0xF06310B7L /*_Stance*/) == 52) {
			if (changeState(state -> Skill_Sum_Leap(blendTime)))
				return;
		}
		changeState(state -> Skill_Sum_Leap3(blendTime));
	}

	protected void Stance_60(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA64526CDL /*Stance_60*/);
		if (changeState(state -> Skill_Roaring(blendTime)))
			return;
		changeState(state -> Skill_Roaring(blendTime));
	}

	protected void OrderHT_Rage200(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1A958FA8L /*OrderHT_Rage200*/);
		if (changeState(state -> Skill_Rage200(blendTime)))
			return;
		changeState(state -> Skill_Rage200(blendTime));
	}

	protected void Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x34E5AE02L /*Summon*/);
		doAction(3635031213L /*SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_StandUp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3891BF54L /*Damage_StandUp*/);
		doAction(927041621L /*DAMAGE_STANDUP*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_StandUp(blendTime), 2000));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void UnSummon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xCC02DCCFL /*UnSummon*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Skill_Sum_Leap(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA6983D29L /*Skill_Sum_Leap*/);
		doAction(3910904692L /*SKILL_SUMMON_LEAP*/, blendTime, onDoActionEnd -> changeState(state -> UnSummon(blendTime)));
	}

	protected void Skill_Sum_Leap2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x10B5C42AL /*Skill_Sum_Leap2*/);
		doAction(1443612745L /*SKILL_SUMMON_LEAP_UP*/, blendTime, onDoActionEnd -> changeState(state -> UnSummon(blendTime)));
	}

	protected void Skill_Sum_Leap3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF21EA9F1L /*Skill_Sum_Leap3*/);
		doAction(4248552934L /*SKILL_SUMMON_LEAP_UP2*/, blendTime, onDoActionEnd -> changeState(state -> UnSummon(blendTime)));
	}

	protected void Skill_Roaring(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2489FFB1L /*Skill_Roaring*/);
		doAction(4033402241L /*SKILL_ATTACK_ROARING_START*/, blendTime, onDoActionEnd -> changeState(state -> Skill_Roaring_Ing(blendTime)));
	}

	protected void Skill_Roaring_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x50EA85E1L /*Skill_Roaring_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		if(getCallCount() == 4) {
			if (changeState(state -> UnSummon(blendTime)))
				return;
		}
		doAction(2559422284L /*SKILL_ATTACK_ROARING_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Skill_Roaring_Ing(blendTime), 1000));
	}

	protected void Skill_Rage200(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7E03955DL /*Skill_Rage200*/);
		doAction(3664200441L /*SKILL_ATTACK_ROARING_RAGE200*/, blendTime, onDoActionEnd -> changeState(state -> Skill_Roaring_Ing(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleStance50(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		if (getState() != 0x181EB08AL /*Stance_50*/ && getState() != 0x72B11EE9L /*Stance_51*/ && getState() != 0xBF9C74DBL /*Stance_52*/ && getState() != 0xA64526CDL /*Stance_60*/ && getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0xA6983D29L /*Skill_Sum_Leap*/ && getState() != 0x10B5C42AL /*Skill_Sum_Leap2*/ && getState() != 0xF21EA9F1L /*Skill_Sum_Leap3*/ && getState() != 0x2489FFB1L /*Skill_Roaring*/) {
			if (changeState(state -> Stance_50(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance51(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		if (getState() != 0x181EB08AL /*Stance_50*/ && getState() != 0x72B11EE9L /*Stance_51*/ && getState() != 0xBF9C74DBL /*Stance_52*/ && getState() != 0xA64526CDL /*Stance_60*/ && getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0xA6983D29L /*Skill_Sum_Leap*/ && getState() != 0x10B5C42AL /*Skill_Sum_Leap2*/ && getState() != 0xF21EA9F1L /*Skill_Sum_Leap3*/ && getState() != 0x2489FFB1L /*Skill_Roaring*/) {
			if (changeState(state -> Stance_51(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance52(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		if (getState() != 0x181EB08AL /*Stance_50*/ && getState() != 0x72B11EE9L /*Stance_51*/ && getState() != 0xBF9C74DBL /*Stance_52*/ && getState() != 0xA64526CDL /*Stance_60*/ && getState() != 0xCC02DCCFL /*UnSummon*/ && getState() != 0xA6983D29L /*Skill_Sum_Leap*/ && getState() != 0x10B5C42AL /*Skill_Sum_Leap2*/ && getState() != 0xF21EA9F1L /*Skill_Sum_Leap3*/ && getState() != 0x2489FFB1L /*Skill_Roaring*/) {
			if (changeState(state -> Stance_52(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance60(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Stance_60(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult OrderHT_Rage200(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> OrderHT_Rage200(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOnOwnerDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> UnSummon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance98(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		clearAggro(true);
		if (changeState(state -> UnSummon(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
