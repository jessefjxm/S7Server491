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
@IAIName("fishpoint_summon")
public class Ai_fishpoint_summon extends CreatureAI {
	public Ai_fishpoint_summon(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xADD3AE72L /*_characterType*/, getVariable(0x946DA634L /*FishCreatureType*/));
		if (getVariable(0xADD3AE72L /*_characterType*/) == 1) {
			if (changeState(state -> Wait_1(blendTime)))
				return;
		}
		if (getVariable(0xADD3AE72L /*_characterType*/) == 2) {
			if (changeState(state -> Wait_2(blendTime)))
				return;
		}
		if (getVariable(0xADD3AE72L /*_characterType*/) == 3) {
			if (changeState(state -> Wait_3(blendTime)))
				return;
		}
		if (getVariable(0xADD3AE72L /*_characterType*/) == 4) {
			if (changeState(state -> Wait_4(blendTime)))
				return;
		}
		if (getVariable(0xADD3AE72L /*_characterType*/) == 5) {
			if (changeState(state -> Wait_5(blendTime)))
				return;
		}
		if (getVariable(0xADD3AE72L /*_characterType*/) == 6) {
			if (changeState(state -> Wait_6(blendTime)))
				return;
		}
		if (getVariable(0xADD3AE72L /*_characterType*/) == 7) {
			if (changeState(state -> Wait_7(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x96AB8779L /*Wait_1*/);
		summonCharacter(916, 1, 0, 0, 250, true);
		summonCharacter(915, 1, 0, 0, -250, true);
		summonCharacter(915, 1, 250, 0, 0, true);
		summonCharacter(916, 1, -250, 0, 0, true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD6E9C46CL /*Wait_2*/);
		summonCharacter(916, 2, 0, 0, 250, true);
		summonCharacter(915, 2, 0, 0, -250, true);
		summonCharacter(915, 2, 250, 0, 0, true);
		summonCharacter(916, 2, -250, 0, 0, true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBC0E69B5L /*Wait_3*/);
		summonCharacter(916, 3, 0, 0, 250, true);
		summonCharacter(915, 3, 0, 0, -250, true);
		summonCharacter(915, 3, 250, 0, 0, true);
		summonCharacter(916, 3, -250, 0, 0, true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8DBB13B1L /*Wait_4*/);
		summonCharacter(916, 4, 0, 0, 250, true);
		summonCharacter(915, 4, 0, 0, -250, true);
		summonCharacter(915, 4, 250, 0, 0, true);
		summonCharacter(916, 4, -250, 0, 0, true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3DBBFC67L /*Wait_5*/);
		summonCharacter(916, 5, 0, 0, 250, true);
		summonCharacter(915, 5, 0, 0, -250, true);
		summonCharacter(915, 5, 250, 0, 0, true);
		summonCharacter(916, 5, -250, 0, 0, true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3C7C7125L /*Wait_6*/);
		summonCharacter(916, 6, 0, 0, 250, true);
		summonCharacter(915, 6, 0, 0, -250, true);
		summonCharacter(915, 6, 250, 0, 0, true);
		summonCharacter(916, 6, -250, 0, 0, true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB06AF720L /*Wait_7*/);
		summonCharacter(916, 7, 0, 0, 250, true);
		summonCharacter(915, 7, 0, 0, -250, true);
		summonCharacter(915, 7, 250, 0, 0, true);
		summonCharacter(916, 7, -250, 0, 0, true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (changeState(state -> Die(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Logout(blendTime), 1000));
	}

	protected void Logout(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x40BC1B1EL /*Logout*/);
		logout();
		changeState(state -> Logout(blendTime));
	}

	@Override
	public EAiHandlerResult HandleFishCountZero(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 251 && (getTargetCharacterKey(object) == 915 || getTargetCharacterKey(object) == 916)).forEach(consumer -> consumer.getAi().HandleFishCountZero(getActor(), null));
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
