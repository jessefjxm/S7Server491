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
@IAIName("spirit_water2")
public class Ai_spirit_water2 extends CreatureAI {
	public Ai_spirit_water2(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x32909C21L /*_IsThunderspear*/, getVariable(0x69F6A8BFL /*AI_IsThunderspear*/));
		setVariable(0xCAD4AAAEL /*_IsThunderbomb*/, getVariable(0x5ECDEEBAL /*AI_IsThunderbomb*/));
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Logic(blendTime), 100));
	}

	protected void Attack_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x50B0953FL /*Attack_Logic*/);
		if (getVariable(0xCAD4AAAEL /*_IsThunderbomb*/) == 1 && getVariable(0x32909C21L /*_IsThunderspear*/) == 0) {
			if (changeState(state -> Attack_Thunderbomb(0.01)))
				return;
		}
		if (getVariable(0x32909C21L /*_IsThunderspear*/) == 1 && getVariable(0xCAD4AAAEL /*_IsThunderbomb*/) == 0) {
			if (changeState(state -> Attack_Thunderspear_Str(0.01)))
				return;
		}
		changeState(state -> Attack_Thunderspear_Str(blendTime));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x90DBFD38L /*Die*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().HandleBrotherCall(getActor(), null));
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die2(blendTime), 100));
	}

	protected void Die2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x6E60EEB7L /*Die2*/);
		doAction(4260439228L /*DIE2*/, blendTime, onDoActionEnd -> scheduleState(state -> Die2(blendTime), 100));
	}

	protected void TargetLost(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3496EAEL /*TargetLost*/);
		changeState(state -> Die2(blendTime));
	}

	protected void Attack_Thunderspear_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCB560BE7L /*Attack_Thunderspear_Str*/);
		doAction(2395503340L /*ATTACK_SKILL*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Thunderspear_End2(blendTime)));
	}

	protected void Attack_Thunderspear_End2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8D035B87L /*Attack_Thunderspear_End2*/);
		doAction(3918767627L /*ATTACK_SKILL_B*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Thunderspear_End3(blendTime)));
	}

	protected void Attack_Thunderspear_End3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC0054B2FL /*Attack_Thunderspear_End3*/);
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().HandleBrotherCall(getActor(), null));
		doAction(1183026925L /*ATTACK_SKILL_C*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 100));
	}

	protected void Attack_Thunderbomb(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC5BC8D18L /*Attack_Thunderbomb*/);
		doAction(4171112582L /*ATTACK_SKILL_Idx1*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().HandleBrotherCall(getActor(), null));
			changeState(state -> Die(blendTime));
		});
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult End_3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOnRifleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die2(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
