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
@IAIName("oceanfish5")
public class Ai_oceanfish5 extends CreatureAI {
	public Ai_oceanfish5(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xF630F33AL /*_Distance*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x21C08552L /*_TurnCount*/, 0);
		setVariable(0x1995ACA8L /*_isMove*/, getVariable(0xAA621BBFL /*AI_Move*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Turn_Logic(blendTime), 1000));
	}

	protected void Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x31999313L /*Turn_Logic*/);
		if (getVariable(0x1995ACA8L /*_isMove*/) == 0) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Turn1(0.1)))
					return;
			}
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 0) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Turn3(0.1)))
					return;
			}
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 0) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Turn2(0.1)))
					return;
			}
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 0) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Turn4(0.1)))
					return;
			}
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 0) {
			if (changeState(state -> Turn(0.1)))
				return;
		}
		changeState(state -> Turn_Logic(blendTime));
	}

	protected void Turn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x6B3D2433L /*Turn*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void Turn1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x82FC64EL /*Turn1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 30, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void Turn2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x65EA08C9L /*Turn2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -30, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void Turn3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xA2662369L /*Turn3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 15, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void Turn4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x97C03B7EL /*Turn4*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -15, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 3000)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(553029183L /*START_ACTION1*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Walk(blendTime), 5000));
	}

	protected void Move_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCF372FA4L /*Move_Walk*/);
		if(getCallCount() == 80) {
			if (changeState(state -> Attack1(0.2)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 2500 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack1(0.2)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, 2000, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Walk(blendTime), 500)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
			if (changeState(state -> FailFindPath(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Bomb_Die(blendTime), 1500));
	}

	protected void Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x566D5E96L /*Attack1*/);
		doAction(2362681632L /*ATTACK_BOMB*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Bomb_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7570BAE5L /*Bomb_Die*/);
		doAction(4292079804L /*BOMB_DIE*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		clearAggro(true);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x7570BAE5L /*Bomb_Die*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Bomb_Die(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult ByeEverybody(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x7570BAE5L /*Bomb_Die*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Attack1(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
