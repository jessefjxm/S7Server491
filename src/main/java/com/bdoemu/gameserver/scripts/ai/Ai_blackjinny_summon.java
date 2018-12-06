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
@IAIName("blackjinny_summon")
public class Ai_blackjinny_summon extends CreatureAI {
	public Ai_blackjinny_summon(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0x5640DAE5L /*_IsAttacker*/, getVariable(0x95278C12L /*AI_IsAttacker*/));
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		if (getVariable(0x5640DAE5L /*_IsAttacker*/) == 0) {
			if (changeState(state -> Wait1(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x17E06D10L /*Wait1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait1(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 10000) {
			if (changeState(state -> Battle_DestructAttack(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Search_Wait(blendTime), 1000));
	}

	protected void Search_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x58D0B0D8L /*Search_Wait*/);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 10000) {
			if (changeState(state -> Battle_DestructAttack(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1200 && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_Chaser(0.2)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		clearAggro(true);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 10000) {
			if (changeState(state -> Battle_DestructAttack(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if(getCallCount() == 40) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) <= 0) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Battle_DestructAttack(blendTime)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 500)));
	}

	protected void Battle_DestructAttack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5E2AFD6L /*Battle_DestructAttack*/);
		if (isTargetLost()) {
			if (changeState(state -> Battle_Death(blendTime)))
				return;
		}
		doAction(3496387840L /*BATTLE_DESTRUCTATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Death(blendTime)));
	}

	protected void Battle_Death(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD4F2761DL /*Battle_Death*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Death(blendTime), 2000));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Move_Chaser(0.3)))
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
	public EAiHandlerResult Attack_Enemy(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (changeState(state -> Move_Chaser(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
