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
@IAIName("9999_bow")
public class Ai_9999_bow extends CreatureAI {
	public Ai_9999_bow(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE4FBB1E7L /*_FindTarget*/, getVariable(0xA130948EL /*AI_FindTarget*/));
		setVariable(0x3BA2904FL /*_WaitRandom*/, getVariable(0x62E3E7CFL /*AI_RandomWait*/));
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0x9225781EL /*_GreetingTime*/, 0);
		setVariable(0x78843D57L /*_GreetingElapsed*/, 0);
		setVariable(0x6C8A49C5L /*_GreetingCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x78843D57L /*_GreetingElapsed*/, getTime() - getVariable(0x9225781EL /*_GreetingTime*/));
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		if (getVariable(0xE4FBB1E7L /*_FindTarget*/) == 1) {
			if (findTarget(EAIFindTargetType.Monster, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && getTargetHp(object) > 0)) {
				if (changeState(state -> FindTarget(0.3)))
					return;
			}
		}
		if (getVariable(0x6C8A49C5L /*_GreetingCount*/) < 1) {
			if (findTarget(EAIFindTargetType.LordOrKingPlayer, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000)) {
				if (changeState(state -> Greeting(0.3)))
					return;
			}
		}
		if (getVariable(0x6C8A49C5L /*_GreetingCount*/) >= 1 && getVariable(0x78843D57L /*_GreetingElapsed*/) >= 60000) {
			if (findTarget(EAIFindTargetType.LordOrKingPlayer, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1000)) {
				if (changeState(state -> Greeting_Second(0.3)))
					return;
			}
		}
		if (getVariable(0x78843D57L /*_GreetingElapsed*/) >= 60000 && getVariable(0xF630F33AL /*_Distance*/) > 1000) {
			if (changeState(state -> Turn_Spawn(0.3)))
				return;
		}
		if (getVariable(0x3BA2904FL /*_WaitRandom*/) == 1) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Wait1(0.3)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2500 + Rnd.get(-500,500)));
	}

	protected void Wait1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x17E06D10L /*Wait1*/);
		if(Rnd.getChance(50)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(2576679943L /*FIND_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait1(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void FindTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x960B75FBL /*FindTarget*/);
		setVariable(0xF630F33AL /*_Distance*/, getDistanceToTarget());
		if (target != null && getTargetHp(target) <= 0) {
			if (changeState(state -> Turn_Spawn(0.3)))
				return;
		}
		if (target == null) {
			if (changeState(state -> Turn_Spawn(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x385C5340L /*AI_FA_DetectDistance*/) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Turn_Spawn(0.3)))
				return;
		}
		doAction(2576679943L /*FIND_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> FindTarget(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void Turn_Spawn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xE67FFF90L /*Turn_Spawn*/);
		clearAggro(true);
		setVariable(0x6C8A49C5L /*_GreetingCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void Greeting(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x57CB1F04L /*Greeting*/);
		doAction(1029767713L /*BOW*/, blendTime, onDoActionEnd -> {
			setVariable(0x9225781EL /*_GreetingTime*/, getTime());
			setVariable(0x6C8A49C5L /*_GreetingCount*/, getVariable(0x6C8A49C5L /*_GreetingCount*/) + 1);
			scheduleState(state -> Wait(blendTime), 1000);
		});
	}

	protected void Greeting_Second(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFDB968D4L /*Greeting_Second*/);
		doAction(1029767713L /*BOW*/, blendTime, onDoActionEnd -> {
			setVariable(0x6C8A49C5L /*_GreetingCount*/, getVariable(0x6C8A49C5L /*_GreetingCount*/) + 1);
			setVariable(0x9225781EL /*_GreetingTime*/, getTime());
			scheduleState(state -> Wait(blendTime), 1000);
		});
	}

	protected void Fright(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x654BC56AL /*Fright*/);
		doAction(32379601L /*FRIGHT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/ && getVariable(0xE4FBB1E7L /*_FindTarget*/) == 1 && target != null && getTargetHp(target) > 0) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> FindTarget(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFailSteal(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender);
		getObjects(EAIFindTargetType.Npc, object -> getDistanceToTarget(object) < 2000).forEach(consumer -> consumer.getAi()._helpme(getActor(), null));
		if (changeState(state -> Fright(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleLordKingGreeting(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Greeting(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
