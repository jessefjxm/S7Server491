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
@IAIName("spirit_fire_dummy")
public class Ai_spirit_fire_dummy extends CreatureAI {
	public Ai_spirit_fire_dummy(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x4E0C1D52L /*_IsMeteo*/, getVariable(0x7EE74BEDL /*AI_Meteo*/));
		setVariable(0x617E9474L /*_IsFirepunch*/, getVariable(0x5CC89292L /*AI_Firepunch*/));
		setVariable(0x63846340L /*_IsExplosion*/, getVariable(0x1FF7BDFAL /*AI_Explosion*/));
		setVariable(0xF6228527L /*_IsMeteo100*/, getVariable(0x2AFF8682L /*AI_Meteo100*/));
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Logic(blendTime), 100));
	}

	protected void Attack_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x50B0953FL /*Attack_Logic*/);
		if (getVariable(0x4E0C1D52L /*_IsMeteo*/) == 1 && getVariable(0x617E9474L /*_IsFirepunch*/) == 0 && getVariable(0x63846340L /*_IsExplosion*/) == 0 && getVariable(0xF6228527L /*_IsMeteo100*/) == 0) {
			if (changeState(state -> Attack_Meteo(0.01)))
				return;
		}
		if (getVariable(0x4E0C1D52L /*_IsMeteo*/) == 0 && getVariable(0x617E9474L /*_IsFirePunch*/) == 1 && getVariable(0x63846340L /*_IsExplosion*/) == 0 && getVariable(0xF6228527L /*_IsMeteo100*/) == 0) {
			if (changeState(state -> Attack_FirePunch(0.01)))
				return;
		}
		if (getVariable(0x4E0C1D52L /*_IsMeteo*/) == 0 && getVariable(0x617E9474L /*_IsFirePunch*/) == 0 && getVariable(0x63846340L /*_IsExplosion*/) == 1 && getVariable(0xF6228527L /*_IsMeteo100*/) == 0) {
			if (changeState(state -> Attack_Explosion(0.1)))
				return;
		}
		if (getVariable(0x4E0C1D52L /*_IsMeteo*/) == 0 && getVariable(0x617E9474L /*_IsFirePunch*/) == 0 && getVariable(0x63846340L /*_IsExplosion*/) == 0 && getVariable(0xF6228527L /*_IsMeteo100*/) == 1) {
			if (changeState(state -> Attack_Meteo100(0.01)))
				return;
		}
		changeState(state -> Attack_Logic(blendTime));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void TargetLost(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3496EAEL /*TargetLost*/);
		changeState(state -> Die(blendTime));
	}

	protected void Attack_FirePunch(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x601E5646L /*Attack_FirePunch*/);
		doAction(366876067L /*ATTACK_FIREPUNCH*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().HandleBrotherCall(getActor(), null));
			changeState(state -> Die(blendTime));
		});
	}

	protected void Attack_Explosion(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3EE4B73AL /*Attack_Explosion*/);
		doAction(2587605505L /*ATTACK_EXPLOSION*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().HandleBrotherCall(getActor(), null));
			changeState(state -> Die(blendTime));
		});
	}

	protected void Attack_Meteo(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBBBADD74L /*Attack_Meteo*/);
		doAction(2676807743L /*ATTACK_METEO_STR*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().HandleBrotherCall(getActor(), null));
			changeState(state -> Die(blendTime));
		});
	}

	protected void Attack_Meteo100(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCF06A6A7L /*Attack_Meteo100*/);
		doAction(3345968897L /*ATTACK_METEO100_STR*/, blendTime, onDoActionEnd -> {
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
	public EAiHandlerResult HandleOnRifleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
