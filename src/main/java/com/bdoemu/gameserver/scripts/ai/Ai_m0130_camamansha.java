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
@IAIName("m0130_camamansha")
public class Ai_m0130_camamansha extends CreatureAI {
	public Ai_m0130_camamansha(Creature actor, Map<Long, Integer> aiVariables) {
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
		setVariable(0x9CD32B63L /*_Park_Count*/, 0);
		setVariable(0xC2593DF3L /*_isSlow*/, getVariable(0x348B1EE8L /*AI_Slow*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Start_Action(blendTime)));
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

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 3000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
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

	protected void Delete_Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x91C9B7A2L /*Delete_Die_Logic*/);
		doTeleportToWaypoint("TombOfThunder", "teleport", 0, 0, 1, 1);
		changeState(state -> Delete_Die(blendTime));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2737950888L /*DELETE_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
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
		if (target != null && getDistanceToTarget(target) < 2000 && target != null && getDistanceToTarget(target) > 1000) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Dig_STR(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1500 && target != null && getDistanceToTarget(target) > 600) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Range(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 800) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_ChargeBomb(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_OnePunchMan(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Attack_Normal2(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Attack_Normal1(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600 && target != null && getDistanceToTarget(target) >= 400) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 600) {
			if (changeState(state -> Chaser_Run(0.3)))
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
		if (target != null && getDistanceToTarget(target) > 1300) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1500 && target != null && getDistanceToTarget(target) > 600) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Range(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Attack_Normal2(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Attack_Normal1(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 600) {
			if (changeState(state -> Chaser_Run(0.3)))
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

	protected void Chaser_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xE2DFC297L /*Chaser_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1500 && target != null && getDistanceToTarget(target) > 600) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Range(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Attack_Normal2(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 400) {
			if (changeState(state -> Attack_Normal1(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 6000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(blendTime)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 1000)));
	}

	protected void Attack_Normal1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB1EFA0F9L /*Attack_Normal1*/);
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_OnePunchMan(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFB319A27L /*Attack_OnePunchMan*/);
		doAction(2019327356L /*ATTACK_ONEPUNCHMAN*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_ChargeBomb(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD9F9BE1CL /*Attack_ChargeBomb*/);
		doAction(1350719040L /*ATTACK_CHARGEBOMB*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Range(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x93D918A4L /*Attack_Range*/);
		doAction(2119583064L /*BATTLE_RANGEATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Dig_STR(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x427AC02L /*Attack_Dig_STR*/);
		doAction(1662588112L /*BATTLE_POSITIONATTACK1*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Dig_ING(blendTime), 3000));
	}

	protected void Attack_Dig_ING(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xE3527FBEL /*Attack_Dig_ING*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Attack_Dig_End(blendTime)))
				return;
		}
		doAction(3114108399L /*BATTLE_POSITIONATTACK1_ING*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(blendTime)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Attack_Dig_ING(blendTime), 500)));
	}

	protected void Attack_Dig_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x609ABFCAL /*Attack_Dig_End*/);
		doAction(898509181L /*BATTLE_POSITIONATTACK1_END*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Park_STR1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF8C04D70L /*Attack_Park_STR1*/);
		doAction(4110575951L /*WATERPARK_STR1*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Park_Wait1(blendTime), 2500));
	}

	protected void Attack_Park_Wait1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4138BB53L /*Attack_Park_Wait1*/);
		if (getVariable(0x9CD32B63L /*_Park_Count*/) >= 15) {
			if (changeState(state -> Delete_Die_Logic(blendTime)))
				return;
		}
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 600, 1200);
		doAction(1464784331L /*WATERPARK_WAIT1*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Park_ING(blendTime), 100));
	}

	protected void Attack_Park_ING(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9BC2D2B8L /*Attack_Park_ING*/);
		doAction(1138183343L /*WATERPARK_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Park_END(blendTime), 2000));
	}

	protected void Attack_Park_END(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF5F4F901L /*Attack_Park_END*/);
		setVariable(0x9CD32B63L /*_Park_Count*/, getVariable(0x9CD32B63L /*_Park_Count*/) + 1);
		doAction(442556646L /*WATERPARK_END*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Park_Wait1(blendTime)));
	}

	protected void Attack_Park_STR2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x969487EBL /*Attack_Park_STR2*/);
		doAction(4110575951L /*WATERPARK_STR1*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Park_Wait2(blendTime), 2500));
	}

	protected void Attack_Park_Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB0F185B3L /*Attack_Park_Wait2*/);
		doAction(1891426170L /*WATERPARK_WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Park_Wait3(blendTime), 1000));
	}

	protected void Attack_Park_Wait3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF8741144L /*Attack_Park_Wait3*/);
		if (getVariable(0x9CD32B63L /*_Park_Count*/) >= 10) {
			if (changeState(state -> Delete_Die_Logic(blendTime)))
				return;
		}
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 1800, 2400);
		doAction(1464784331L /*WATERPARK_WAIT1*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Park_ING2(blendTime), 100));
	}

	protected void Attack_Park_ING2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8ADD2768L /*Attack_Park_ING2*/);
		doAction(1138183343L /*WATERPARK_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Park_END2(blendTime), 2000));
	}

	protected void Attack_Park_END2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2557E5C1L /*Attack_Park_END2*/);
		setVariable(0x9CD32B63L /*_Park_Count*/, getVariable(0x9CD32B63L /*_Park_Count*/) + 1);
		doAction(442556646L /*WATERPARK_END*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Park_Wait3(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Start_Bomb(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Attack_ChargeBomb(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult Park_Open(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xC2593DF3L /*_isSlow*/) == 1 && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Attack_Park_STR1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0xC2593DF3L /*_isSlow*/) == 0 && getState() != 0x90DBFD38L /*Die*/) {
			if (changeState(state -> Attack_Park_STR2(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
