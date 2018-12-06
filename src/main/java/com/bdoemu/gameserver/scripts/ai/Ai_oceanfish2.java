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
@IAIName("oceanfish2")
public class Ai_oceanfish2 extends CreatureAI {
	public Ai_oceanfish2(Creature actor, Map<Long, Integer> aiVariables) {
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
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		if (getVariable(0x1995ACA8L /*_isMove*/) == 1) {
			if (changeState(state -> Trap_Wait(0.1)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 2) {
			if (changeState(state -> Trap_Wait2(0.1)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 3) {
			if (changeState(state -> Wait_Idx3(0.1)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Trap_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5BA82333L /*Trap_Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 400 && getTargetHp(object) > 0)) {
			if (changeState(state -> Trap_Summon(0.2)))
				return;
		}
		if(getCallCount() == 20) {
			if (changeState(state -> Die(0.1)))
				return;
		}
		doAction(250801207L /*START_ACTION1_Idx1*/, blendTime, onDoActionEnd -> scheduleState(state -> Trap_Wait(blendTime), 1000));
	}

	protected void Trap_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3A4763D4L /*Trap_Summon*/);
		if(getCallCount() == 2) {
			if (changeState(state -> Trap_Attack(0.1)))
				return;
		}
		doAction(250801207L /*START_ACTION1_Idx1*/, blendTime, onDoActionEnd -> scheduleState(state -> Trap_Summon(blendTime), 1000));
	}

	protected void Trap_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x48B53383L /*Trap_Attack*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Trap_End(0.1)))
				return;
		}
		doAction(250801207L /*START_ACTION1_Idx1*/, blendTime, onDoActionEnd -> scheduleState(state -> Trap_Attack(blendTime), 1000));
	}

	protected void Trap_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x165F0DBCL /*Trap_End*/);
		if(getCallCount() == 2) {
			if (changeState(state -> Die(0.1)))
				return;
		}
		doAction(250801207L /*START_ACTION1_Idx1*/, blendTime, onDoActionEnd -> scheduleState(state -> Trap_End(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (isTargetLost()) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 3000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Wait2(0.2)))
				return;
		}
		doAction(553029183L /*START_ACTION1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 3000));
	}

	protected void Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x84794EB4L /*Wait2*/);
		if (isTargetLost()) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(541879788L /*START_ACTION1_A*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) <= 3000 && target != null && getTargetHp(target) > 0) {
				if (changeState(state -> Attack2(0.2)))
					return;
			}
			changeState(state -> Attack1(blendTime));
		});
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
		doAction(3622503696L /*ATTACK_NORMAL1_A*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
	}

	protected void Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1CF46EC2L /*Attack2*/);
		doAction(2463627859L /*ATTACK_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Die(blendTime)));
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

	protected void Trap_Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAF7D9E42L /*Trap_Wait2*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 400 && getTargetHp(object) > 0)) {
			if (changeState(state -> Trap_Summon2(0.2)))
				return;
		}
		if(getCallCount() == 20) {
			if (changeState(state -> Die(0.1)))
				return;
		}
		doAction(747099284L /*START_ACTION1_Idx2*/, blendTime, onDoActionEnd -> scheduleState(state -> Trap_Wait2(blendTime), 1000));
	}

	protected void Trap_Summon2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2D2192FCL /*Trap_Summon2*/);
		doAction(747099284L /*START_ACTION1_Idx2*/, blendTime, onDoActionEnd -> scheduleState(state -> Trap_Attack2(blendTime), 1000));
	}

	protected void Trap_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5C68CEF3L /*Trap_Attack2*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Trap_End2(0.1)))
				return;
		}
		doAction(747099284L /*START_ACTION1_Idx2*/, blendTime, onDoActionEnd -> scheduleState(state -> Trap_Attack2(blendTime), 1000));
	}

	protected void Trap_End2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE8A04ABEL /*Trap_End2*/);
		if(getCallCount() == 2) {
			if (changeState(state -> Die(0.1)))
				return;
		}
		doAction(747099284L /*START_ACTION1_Idx2*/, blendTime, onDoActionEnd -> scheduleState(state -> Trap_End2(blendTime), 1000));
	}

	protected void Wait_Idx3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD035A947L /*Wait_Idx3*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 3000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Turn_Logic(0.2)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Idx3(blendTime), 5000));
	}

	protected void Turn_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x31999313L /*Turn_Logic*/);
		if(Rnd.getChance(25)) {
			if (changeState(state -> Turn(0.1)))
				return;
		}
		if(Rnd.getChance(25)) {
			if (changeState(state -> Turn1(0.1)))
				return;
		}
		if(Rnd.getChance(25)) {
			if (changeState(state -> Turn2(0.1)))
				return;
		}
		if(Rnd.getChance(100)) {
			if (changeState(state -> Turn3(0.1)))
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
		}, onExit -> scheduleState(state -> Wait2_Idx3(blendTime), 3000)));
	}

	protected void Turn1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x82FC64EL /*Turn1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -90, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait2_Idx3(blendTime), 3000)));
	}

	protected void Turn2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x65EA08C9L /*Turn2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, 90, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait2_Idx3(blendTime), 3000)));
	}

	protected void Turn3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xA2662369L /*Turn3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Absolute, -170, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait2_Idx3(blendTime), 3000)));
	}

	protected void Wait2_Idx3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x62DEDB1BL /*Wait2_Idx3*/);
		doAction(162901052L /*START_ACTION1_Idx3*/, blendTime, onDoActionEnd -> changeState(state -> Move_Back(blendTime)));
	}

	protected void Move_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xDD3BDB77L /*Move_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		doAction(4271683418L /*MOVE_WALK2*/, blendTime, onDoActionEnd -> escape(5000, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Random(blendTime), 3000)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (isTargetLost()) {
			if (changeState(state -> Target_Logic(blendTime)))
				return;
		}
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 6000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_Chase(0.2)))
				return;
		}
		doAction(4271683418L /*MOVE_WALK2*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 1000, 1500, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Random(blendTime), 3000)));
	}

	protected void Target_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC2659E7L /*Target_Logic*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 6000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Move_Chase(0.2)))
				return;
		}
		changeState(state -> Target_Logic(blendTime));
	}

	protected void Move_Chase(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x21564578L /*Move_Chase*/);
		if (isTargetLost()) {
			if (changeState(state -> Target_Logic(blendTime)))
				return;
		}
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (getVariable(0xE26EA7A0L /*_EndTime*/) >= 40000) {
			if (changeState(state -> Attack1(0.2)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 2500 && getTargetHp(object) > 0)) {
			if (changeState(state -> Attack1(0.2)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Chase(blendTime), 500)));
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
	public EAiHandlerResult AttackChild(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x1995ACA8L /*_isMove*/) != 1 && getState() != 0x7570BAE5L /*Bomb_Die*/ && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Attack1(0.5)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
