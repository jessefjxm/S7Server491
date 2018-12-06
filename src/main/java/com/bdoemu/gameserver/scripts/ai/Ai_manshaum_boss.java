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
@IAIName("manshaum_boss")
public class Ai_manshaum_boss extends CreatureAI {
	public Ai_manshaum_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xD94DEC74L /*_C4_Count*/, 0);
		setVariable(0xC1B232F1L /*_Flood_Count*/, 4);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 3000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-1000,1000)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.attack);
		setState(0xEC3F34D2L /*Detect_Target*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		doAction(4003178407L /*ATTACK_SUMMON*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(blendTime)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(blendTime)))
				return;
		}
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 5) {
			if (changeState(state -> FailFindPathToTarget(blendTime)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void FailFindPathToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC3EA7010L /*FailFindPathToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
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
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if(Rnd.getChance(30)) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (getPartyMembersCount()== 2) {
			if (changeState(state -> Attack_Park(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 75 && getVariable(0xC1B232F1L /*_Flood_Count*/) == 2) {
			if (changeState(state -> Attack_Flood1(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 30 && getVariable(0xC1B232F1L /*_Flood_Count*/) == 2) {
			if (changeState(state -> Attack_Flood2(blendTime)))
				return;
		}
		if (getVariable(0xD94DEC74L /*_C4_Count*/) >= 20 && getPartyMembersCount()>= 1) {
			if (changeState(state -> Attack_C4(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal3(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Normal4(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Attack_Normal2(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Attack_Normal1(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 800) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Normal3(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Normal4(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Attack_Normal2(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800) {
			if (changeState(state -> Attack_Normal1(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(blendTime)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 1000)));
	}

	protected void Attack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB1EFA0F9L /*Attack_Normal1*/);
		setVariable(0xD94DEC74L /*_C4_Count*/, getVariable(0xD94DEC74L /*_C4_Count*/) + 1);
		doAction(1616805723L /*ATTACK_NORMAL1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		setVariable(0xD94DEC74L /*_C4_Count*/, getVariable(0xD94DEC74L /*_C4_Count*/) + 1);
		doAction(2463627859L /*ATTACK_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5F2C1966L /*Attack_Normal3*/);
		setVariable(0xD94DEC74L /*_C4_Count*/, getVariable(0xD94DEC74L /*_C4_Count*/) + 1);
		doAction(2957630933L /*ATTACK_NORMAL3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5CA8868L /*Attack_Normal4*/);
		setVariable(0xD94DEC74L /*_C4_Count*/, getVariable(0xD94DEC74L /*_C4_Count*/) + 1);
		doAction(1678640124L /*ATTACK_NORMAL4*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_C4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x917F9997L /*Attack_C4*/);
		setVariable(0xD94DEC74L /*_C4_Count*/, 0);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Start_Bomb(getActor(), null));
		doAction(1325750659L /*ATTACK_C4*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4A9D8D69L /*Attack_Summon*/);
		doAction(4003178407L /*ATTACK_SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Flood1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE383658L /*Attack_Flood1*/);
		setVariable(0xC1B232F1L /*_Flood_Count*/, getVariable(0xC1B232F1L /*_Flood_Count*/) - 1);
		doAction(221197001L /*ATTACK_FLOOD_STR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Flood_ING(blendTime)));
	}

	protected void Attack_Flood2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x92766CA9L /*Attack_Flood2*/);
		setVariable(0xC1B232F1L /*_Flood_Count*/, getVariable(0xC1B232F1L /*_Flood_Count*/) - 1);
		doAction(221197001L /*ATTACK_FLOOD_STR*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Flood_ING(blendTime)));
	}

	protected void Attack_Flood_ING(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8F99A186L /*Attack_Flood_ING*/);
		if(getCallCount() == 10) {
			if (changeState(state -> Attack_Flood_END(blendTime)))
				return;
		}
		doAction(3412488560L /*ATTACK_FLOOD_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Flood_ING(blendTime), 1000));
	}

	protected void Attack_Flood_END(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x383F072AL /*Attack_Flood_END*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Charge_Complete(getActor(), null));
		doAction(398934512L /*ATTACK_FLOOD_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Flood_FAIL(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x41BD7E8AL /*Attack_Flood_FAIL*/);
		doAction(2680246857L /*ATTACK_FLOOD_FAIL*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_Park(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE3A912D6L /*Attack_Park*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().Park_Open(getActor(), null));
		doAction(473463894L /*ATTACK_WATERPARK_STR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult GuardBreak(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x383F072AL /*Attack_Flood_END*/) {
			if (changeState(state -> Attack_Flood_FAIL(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
