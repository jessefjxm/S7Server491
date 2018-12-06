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
@IAIName("flying_creature")
public class Ai_flying_creature extends CreatureAI {
	public Ai_flying_creature(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xDD6BDE1AL /*_isFly*/, 0);
		setVariable(0x9617E562L /*isWater*/, 0);
		setVariable(0xA486D3DL /*_FlyCount*/, 0);
		setVariable(0xFE9E61F8L /*_EscapeCount*/, 0);
		setVariable(0xADD3AE72L /*_characterType*/, getVariable(0x87951E9AL /*FlyingCreatureType*/));
		setVariable(0xB01283E1L /*_spawnType*/, getVariable(0x282E9A83L /*FlyingSpawnType*/));
		setVariable(0x4B55E904L /*_FlyingRandomType*/, getVariable(0x3BB5BFFDL /*FlyingRandomType*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xB01283E1L /*_spawnType*/) == 1 && getVariable(0x4B55E904L /*_FlyingRandomType*/) <= 0) {
				if (changeState(state -> KeepFlying(blendTime)))
					return;
			}
			if (getVariable(0xB01283E1L /*_spawnType*/) == 1 && getVariable(0x4B55E904L /*_FlyingRandomType*/) >= 1) {
				if (changeState(state -> FlyingWait(blendTime)))
					return;
			}
			scheduleState(state -> Wait(blendTime), 1000);
		});
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getVariable(0xDD6BDE1AL /*_isFly*/) == 1) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 700)) {
				if (changeState(state -> FlyUp(blendTime)))
					return;
			}
		}
		if (getVariable(0x4B55E904L /*_FlyingRandomType*/) >= 1) {
			if(Rnd.getChance(45)) {
				if (changeState(state -> FlyingRandom(blendTime)))
					return;
			}
		}
		if (getVariable(0xDD6BDE1AL /*_isFly*/) == 1 && getVariable(0x4B55E904L /*_FlyingRandomType*/) <= 0) {
			if (changeState(state -> FlyUp(blendTime)))
				return;
		}
		if (getVariable(0xADD3AE72L /*_characterType*/) == 3 && getVariable(0x4B55E904L /*_FlyingRandomType*/) <= 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Shout(blendTime)))
					return;
			}
		}
		if (getVariable(0x4B55E904L /*_FlyingRandomType*/) <= 0) {
			if(getCallCount() == 5) {
				if (changeState(state -> WalkRandom(blendTime)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 300 + Rnd.get(-300,300)));
	}

	protected void Shout(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x869EA8C8L /*Shout*/);
		if (getVariable(0xDD6BDE1AL /*_isFly*/) == 1) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 700)) {
				if (changeState(state -> FlyUp(blendTime)))
					return;
			}
		}
		doAction(240458397L /*WAIT_TYPE1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 300));
	}

	protected void WalkRandom(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD5712181L /*WalkRandom*/);
		if (getVariable(0xDD6BDE1AL /*_isFly*/) == 1) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 700)) {
				if (changeState(state -> FlyUp(blendTime)))
					return;
			}
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 200, 400, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 300)));
	}

	protected void FlyingWait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD54A281BL /*FlyingWait*/);
		if (getVariable(0xDD6BDE1AL /*_isFly*/) == 1) {
			if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 700)) {
				if (changeState(state -> FlyUp(blendTime)))
					return;
			}
		}
		if (getVariable(0x4B55E904L /*_FlyingRandomType*/) >= 1) {
			if(Rnd.getChance(45)) {
				if (changeState(state -> FlyingRandom(blendTime)))
					return;
			}
		}
		if (getVariable(0xDD6BDE1AL /*_isFly*/) == 1 && getVariable(0x4B55E904L /*_FlyingRandomType*/) <= 0) {
			if (changeState(state -> FlyUp(blendTime)))
				return;
		}
		if (getVariable(0xADD3AE72L /*_characterType*/) == 3 && getVariable(0x4B55E904L /*_FlyingRandomType*/) <= 0) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Shout(blendTime)))
					return;
			}
		}
		if (getVariable(0x4B55E904L /*_FlyingRandomType*/) <= 0) {
			if(getCallCount() == 5) {
				if (changeState(state -> WalkRandom(blendTime)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> FlyingWait(blendTime), 300 + Rnd.get(-300,300)));
	}

	protected void FlyingRandom(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA7442DBDL /*FlyingRandom*/);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 1800, 2500, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> FlyingWait(blendTime), 300)));
	}

	protected void FlyUp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD9B5EE01L /*FlyUp*/);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 1000).forEach(consumer -> consumer.getAi().fly_near(getActor(), null));
		setVariable(0xA486D3DL /*_FlyCount*/, 0);
		doAction(2040165886L /*FLYING_UP*/, blendTime, onDoActionEnd -> changeState(state -> FlyingFlap(blendTime)));
	}

	protected void FlyingFlap(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x54CA9FC8L /*FlyingFlap*/);
		doAction(1059120586L /*FLYING_FLAP*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1000, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying(blendTime), 1000)));
	}

	protected void Flying(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCF0613F2L /*Flying*/);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 700, 1200, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> ThinkingInFly(blendTime), 1000)));
	}

	protected void ThinkingInFly(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF8AA0120L /*ThinkingInFly*/);
		setVariable(0xA486D3DL /*_FlyCount*/, getVariable(0xA486D3DL /*_FlyCount*/) + 1);
		if (getVariable(0xA486D3DL /*_FlyCount*/) < 5) {
			if (changeState(state -> Flying(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000)) {
			if (changeState(state -> Flying(blendTime)))
				return;
		}
		changeState(state -> FlyingFlapDown(blendTime));
	}

	protected void Flying_Escape(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF06DE92EL /*Flying_Escape*/);
		setVariable(0xA486D3DL /*_FlyCount*/, 0);
		setVariable(0xFE9E61F8L /*_EscapeCount*/, getVariable(0xFE9E61F8L /*_EscapeCount*/) + 1);
		if (getVariable(0xFE9E61F8L /*_EscapeCount*/) > 2) {
			if (changeState(state -> Flying(blendTime)))
				return;
		}
		doAction(1059120586L /*FLYING_FLAP*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 700, 1200, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Flying_Escape(blendTime), 1000)));
	}

	protected void FlyingFlapDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB4C70C3CL /*FlyingFlapDown*/);
		doAction(395241493L /*FLYING_FLAP_DOWN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1200, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Landing(blendTime), 1000)));
	}

	protected void Landing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBE797C29L /*Landing*/);
		doAction(116698182L /*LANDING*/, blendTime, onDoActionEnd -> {
			setVariable(0xDD6BDE1AL /*_isFly*/, 0);
			changeState(state -> Wait(blendTime));
		});
	}

	protected void Fall(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x433E1948L /*Fall*/);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 1500).forEach(consumer -> consumer.getAi().fly_escape(getActor(), null));
		doAction(2369942272L /*FALLING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Die(blendTime), 1000)));
	}

	protected void Falling_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x258C1DB6L /*Falling_Wait*/);
		doAction(2764968659L /*FALLING_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Fall(blendTime), 300));
	}

	protected void Fall_ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD5CC3C50L /*Fall_ing*/);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 1500).forEach(consumer -> consumer.getAi().fly_escape(getActor(), null));
		doAction(2369942272L /*FALLING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Die(blendTime), 1000)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 1000).forEach(consumer -> consumer.getAi().fly_near(getActor(), null));
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport(blendTime), 5000));
	}

	protected void Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x88C3A72EL /*Teleport*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1000, 2000);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void KeepFlying(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF091D228L /*KeepFlying*/);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_random", "flying_gull_15", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> KeepFlying_Again(blendTime), 1000)));
	}

	protected void KeepFlying_Again(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x23513720L /*KeepFlying_Again*/);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_random", "flying_gull_25", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> KeepFlying(blendTime), 1000)));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xDD6BDE1AL /*_isFly*/) == 1) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> FlyUp(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/ && getState() != 0x433E1948L /*Fall*/) {
			if (changeState(state -> Die(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xD5712181L /*WalkRandom*/ && getState() != 0x433E1948L /*Fall*/) {
			if (changeState(state -> Die(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xD9B5EE01L /*FlyUp*/ && getState() != 0x433E1948L /*Fall*/) {
			if (changeState(state -> Die(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xBE797C29L /*Landing*/ && getState() != 0x433E1948L /*Fall*/) {
			if (changeState(state -> Die(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x54CA9FC8L /*FlyingFlap*/ && getState() != 0x433E1948L /*Fall*/) {
			if (changeState(state -> Die(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xCF0613F2L /*Flying*/ && getState() != 0x433E1948L /*Fall*/) {
			if (changeState(state -> Die(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xF8AA0120L /*ThinkingInFly*/ && getState() != 0x433E1948L /*Fall*/) {
			if (changeState(state -> Die(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xB4C70C3CL /*FlyingFlapDown*/ && getState() != 0x433E1948L /*Fall*/) {
			if (changeState(state -> Die(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xD54A281BL /*FlyingWait*/ && getState() != 0x433E1948L /*Fall*/) {
			if (changeState(state -> Die(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xA7442DBDL /*FlyingRandom*/ && getState() != 0x433E1948L /*Fall*/) {
			if (changeState(state -> Die(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xF091D228L /*KeepFlying*/ && getState() != 0x433E1948L /*Fall*/) {
			if (changeState(state -> Die(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x23513720L /*KeepFlying_Again*/ && getState() != 0x433E1948L /*Fall*/) {
			if (changeState(state -> Die(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult fly_near(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/) {
			setVariable(0xDD6BDE1AL /*_isFly*/, 1);
		}
		if (getState() == 0xD5712181L /*WalkRandom*/) {
			setVariable(0xDD6BDE1AL /*_isFly*/, 1);
		}
		if (getState() == 0x869EA8C8L /*Shout*/) {
			setVariable(0xDD6BDE1AL /*_isFly*/, 1);
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult fly_escape(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xFE9E61F8L /*_EscapeCount*/, 0);
		if (getState() == 0xCF0613F2L /*Flying*/) {
			if (changeState(state -> Flying_Escape(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xF8AA0120L /*ThinkingInFly*/) {
			if (changeState(state -> Flying_Escape(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0xB4C70C3CL /*FlyingFlapDown*/) {
			if (changeState(state -> Flying_Escape(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
