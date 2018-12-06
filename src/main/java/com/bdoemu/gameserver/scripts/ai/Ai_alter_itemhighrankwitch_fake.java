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
@IAIName("alter_itemhighrankwitch_fake")
public class Ai_alter_itemhighrankwitch_fake extends CreatureAI {
	public Ai_alter_itemhighrankwitch_fake(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xDBA53A2L /*_isFaker*/, getVariable(0x8D0BA7FL /*AI_Faker*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Rotate(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 5000));
	}

	protected void Wait_Rotate(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xC6855029L /*Wait_Rotate*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToSettedPosition, -391814, -300138, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Detect_Target(blendTime), 1000)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		doAction(1320279259L /*MIRRORIMAGE_APPEAR*/, blendTime, onDoActionEnd -> scheduleState(state -> Explosion_Logic(blendTime), 1000));
	}

	protected void Explosion_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCF03DC00L /*Explosion_Logic*/);
		if (getVariable(0xDBA53A2L /*_isFaker*/) == 0) {
			if (changeState(state -> Explosion(blendTime)))
				return;
		}
		if (getVariable(0xDBA53A2L /*_isFaker*/) == 1) {
			if (changeState(state -> Explosion_Faker(blendTime)))
				return;
		}
		changeState(state -> Explosion_Logic(blendTime));
	}

	protected void Explosion(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCDBD5BA4L /*Explosion*/);
		doAction(2350128841L /*BATTLE_ATTACK_EXPLOSION*/, blendTime, onDoActionEnd -> changeState(state -> Explosion(blendTime)));
	}

	protected void Explosion_Faker(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5A4D0296L /*Explosion_Faker*/);
		doAction(3701544864L /*BATTLE_ATTACK_EXPLOSION_FAKE*/, blendTime, onDoActionEnd -> changeState(state -> Explosion_Faker(blendTime)));
	}

	protected void Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE70D4D89L /*Die_Logic*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().Seek(getActor(), null));
		getObjects(EAIFindTargetType.Sibling, object -> true).forEach(consumer -> consumer.getAi().Seek(getActor(), null));
		changeState(state -> Bomb_Die(blendTime));
	}

	protected void Bomb(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA67090F0L /*Bomb*/);
		doAction(2886130280L /*BATTLE_ATTACK_BOMB*/, blendTime, onDoActionEnd -> changeState(state -> Bomb_Die(blendTime)));
	}

	protected void Bomb_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x7570BAE5L /*Bomb_Die*/);
		doAction(643809287L /*BATTLE_ATTACK_BOMB2*/, blendTime, onDoActionEnd -> scheduleState(state -> Bomb_Die(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2978442901L /*DIE_DELETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xDBA53A2L /*_isFaker*/) == 0) {
			if (changeState(state -> Die_Logic(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xDBA53A2L /*_isFaker*/) == 1) {
			if (changeState(state -> Bomb(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Seek(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xDBA53A2L /*_isFaker*/) == 1) {
			if (changeState(state -> Bomb_Die(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
