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
@IAIName("cat_creature")
public class Ai_cat_creature extends CreatureAI {
	public Ai_cat_creature(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
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
		clearAggro(true);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 200 && getDistanceToTarget(object, false) <= 700)) {
			if (changeState(state -> FallowHuman(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> WalkRandom(blendTime), 500));
	}

	protected void WalkRandom(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD5712181L /*WalkRandom*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 200 && getDistanceToTarget(object, false) <= 700)) {
			if (changeState(state -> FallowHuman(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 200, 400, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 500)));
	}

	protected void FallowHuman(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xBE7925A7L /*FallowHuman*/);
		if (isTargetLost()) {
			if (changeState(state -> WalkRandom(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 500)));
	}

	protected void Run_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFC8E70CEL /*Run_Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 350)) {
			if (changeState(state -> RunFast(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1500) {
			clearAggro(true);
		}
		doAction(3105119162L /*WAIT_SHORT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 250));
	}

	protected void Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x6D77C592L /*Run*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 1500)) {
			if (changeState(state -> Run(blendTime)))
				return;
		}
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> escape(0, () -> {
			if(Rnd.getChance(50)) {
				if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 400)) {
					if (changeState(state -> RunFast(blendTime)))
						return true;
				}
			}
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 300 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 300)) {
				if (changeState(state -> Run_Twist1(blendTime)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Run(blendTime), 1000)));
	}

	protected void Run_ResetAggro(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x5625A493L /*Run_ResetAggro*/);
		clearAggro(true);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 700)) {
			if (changeState(state -> Run(blendTime)))
				return;
		}
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> escape(0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Run(blendTime), 1000)));
	}

	protected void RunFast(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x9B7EFC86L /*RunFast*/);
		doAction(4171429047L /*RUN_FAST*/, blendTime, onDoActionEnd -> escape(0, () -> {
			if (target != null && getDistanceToTarget(target) < 200) {
				if (changeState(state -> RunFast(0.15)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Run(blendTime), 1000)));
	}

	protected void RunFast_FindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB54A0978L /*RunFast_FindPath*/);
		if (isTargetLost()) {
			if (changeState(state -> RunStop(blendTime)))
				return;
		}
		doAction(881069330L /*RUN_STOP*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < 250) {
				if (changeState(state -> Run_Twist1(blendTime)))
					return;
			}
			changeState(state -> Run(blendTime));
		});
	}

	protected void Run_Twist1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xE73CD6C8L /*Run_Twist1*/);
		doAction(3906190850L /*RUN_Twist_A*/, blendTime, onDoActionEnd -> escape(0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Run(blendTime), 1500)));
	}

	protected void Run_Twist2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x4F6C2245L /*Run_Twist2*/);
		doAction(1584797671L /*RUN_Twist_B*/, blendTime, onDoActionEnd -> escape(0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Run(blendTime), 1500)));
	}

	protected void RunStop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDE493DF8L /*RunStop*/);
		if (isTargetLost()) {
			if (changeState(state -> Run_Wait(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Run_Twist1(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 600) {
			clearAggro(true);
		}
		doAction(881069330L /*RUN_STOP*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 600) {
				clearAggro(true);
			}
			changeState(state -> Run_Wait(blendTime));
		});
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 5000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Call_from_Dialog(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
