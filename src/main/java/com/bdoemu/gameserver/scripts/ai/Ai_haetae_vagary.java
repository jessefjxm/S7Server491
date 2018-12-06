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
@IAIName("haetae_vagary")
public class Ai_haetae_vagary extends CreatureAI {
	public Ai_haetae_vagary(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xBC175512L /*_isRoaring*/, getVariable(0xA11078B5L /*AI_isRoaring*/));
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 300));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getVariable(0xBC175512L /*_isRoaring*/) == 0) {
			if (changeState(state -> Attack_Awakening_Party_Dash(blendTime)))
				return;
		}
		if (getVariable(0xBC175512L /*_isRoaring*/) == 1) {
			if (changeState(state -> Attack_Awakening_Party_Roaring(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500));
	}

	protected void Attack_Awakening_Party_Dash(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9A52BC78L /*Attack_Awakening_Party_Dash*/);
		doAction(1351850438L /*ATTACK_AWAKENING_PARTY_SKILL_DASH*/, blendTime, onDoActionEnd -> changeState(state -> Kill(blendTime)));
	}

	protected void Attack_Awakening_Party_Roaring(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2E2C2D72L /*Attack_Awakening_Party_Roaring*/);
		doAction(2400450978L /*ATTACK_AWAKENING_PARTY_SKILL_ROARING*/, blendTime, onDoActionEnd -> changeState(state -> Kill2(blendTime)));
	}

	protected void Kill(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x9BA5FB3BL /*Kill*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Kill(blendTime), 1000));
	}

	protected void Kill2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x6949CD18L /*Kill2*/);
		doAction(4260439228L /*DIE2*/, blendTime, onDoActionEnd -> scheduleState(state -> Kill2(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOnRifleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Kill(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
