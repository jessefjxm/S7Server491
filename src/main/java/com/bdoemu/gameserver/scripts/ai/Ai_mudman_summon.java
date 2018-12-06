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
@IAIName("mudman_summon")
public class Ai_mudman_summon extends CreatureAI {
	public Ai_mudman_summon(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x9326AD79L /*_SummonStartTime*/, 0);
		setVariable(0xE8822790L /*_SummonIngTime*/, 0);
		setVariable(0x51EDA18AL /*_SummonEndTime*/, 0);
		setVariable(0x9326AD79L /*_SummonStartTime*/, getTime());
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void TargetLost(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3496EAEL /*TargetLost*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1500)) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1500));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1500)) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 10000 && getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> Battle_DestructAttack(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 400) {
			if (changeState(state -> Battle_DestructAttack(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 400) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xE8822790L /*_SummonIngTime*/, getTime());
		setVariable(0x51EDA18AL /*_SummonEndTime*/, getVariable(0xE8822790L /*_SummonIngTime*/) - getVariable(0x9326AD79L /*_SummonStartTime*/));
		if (getVariable(0x51EDA18AL /*_SummonEndTime*/) > 10000 && getVariable(0x3F487035L /*_Hp*/) == 0) {
			if (changeState(state -> Battle_DestructAttack(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0 && target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 400) {
			if (changeState(state -> Battle_DestructAttack(0.3)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 200)));
	}

	protected void Battle_DestructAttack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5E2AFD6L /*Battle_DestructAttack*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(3496387840L /*BATTLE_DESTRUCTATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
