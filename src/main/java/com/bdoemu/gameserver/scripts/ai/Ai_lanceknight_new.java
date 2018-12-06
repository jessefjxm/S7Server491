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
@IAIName("lanceknight_new")
public class Ai_lanceknight_new extends CreatureAI {
	public Ai_lanceknight_new(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0xAF2158BEL /*_isBerserk*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1700 && getTargetHp(object) > 0)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 1700 && getTargetHp(object) > 0)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0xEC3F34D2L /*Detect_Target*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(356021664L /*DETECT_ENEMY_LEFT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToTarget, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 3000)));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 5000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xAF2158BEL /*_isBerserk*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Return_Wait(blendTime), 1000)));
	}

	protected void Move_Return_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x47391F8FL /*Move_Return_Wait*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.Relative, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		setVariable(0xAF2158BEL /*_isBerserk*/, getVariable(0xAF2158BEL /*_isBerserk*/) - 1);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return_Wait(blendTime), 1500));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0xAF2158BEL /*_isBerserk*/) == 0 && getVariable(0x3F487035L /*_HP*/) <= 65) {
			if (changeState(state -> Battle_Berserk(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 3500) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) <= 0) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Battle_Walk(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Walk_Back(blendTime)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= -20 && getVariable(0xE5BD13F2L /*_Degree*/) <= 20 && getVariable(0xAF2158BEL /*_isBerserk*/) == 0) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Battle_Attack_4(blendTime)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= -20 && getVariable(0xE5BD13F2L /*_Degree*/) <= 20 && getVariable(0xAF2158BEL /*_isBerserk*/) == 0) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack_3(blendTime)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 20 && getDistanceToTarget(target, false) <= 300) && getVariable(0xAF2158BEL /*_isBerserk*/) >= 1) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Attack_2(blendTime)))
					return;
			}
		}
		if (getVariable(0xAF2158BEL /*_isBerserk*/) >= 1) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Battle_Attack_1_B(blendTime)))
					return;
			}
		}
		if (getVariable(0xAF2158BEL /*_isBerserk*/) >= 1) {
			if(Rnd.getChance(45)) {
				if (changeState(state -> Battle_Attack_2_B(blendTime)))
					return;
			}
		}
		if (getVariable(0xAF2158BEL /*_isBerserk*/) >= 1) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Battle_Attack_3_B(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Attack_1(blendTime)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Battle_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xEB438BF9L /*Battle_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getDistanceToSpawn() > 3500) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Attack_JumpAttack(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 550) {
			if (changeState(state -> Battle_Walk(blendTime)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (getVariable(0xAF2158BEL /*_isBerserk*/) == 0 && getVariable(0x3F487035L /*_HP*/) <= 65) {
			if (changeState(state -> Battle_Berserk(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 3500) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1000) {
			if (changeState(state -> Battle_Run(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 370) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= -20 && getVariable(0xE5BD13F2L /*_Degree*/) <= 20 && target != null && getDistanceToTarget(target) < 300) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack_4(blendTime)))
					return;
			}
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 100)));
	}

	protected void Battle_Walk_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC3F442L /*Battle_Walk_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0xAF2158BEL /*_isBerserk*/) == 0 && getVariable(0x3F487035L /*_HP*/) <= 65) {
			if (changeState(state -> Battle_Berserk(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 3500) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 100) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Battle_Walk(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Battle_Attack_1(blendTime)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= -20 && getVariable(0xE5BD13F2L /*_Degree*/) <= 20 && target != null && getDistanceToTarget(target) < 300) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack_2(blendTime)))
					return;
			}
		}
		if (getVariable(0xAF2158BEL /*_isBerserk*/) >= 1 && target != null && getDistanceToTarget(target) < 300) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Battle_Attack_4(blendTime)))
					return;
			}
		}
		doAction(1509390199L /*BATTLE_MOVE_BACK*/, blendTime, onDoActionEnd -> escape(450, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk_Back(blendTime), 100)));
	}

	protected void Battle_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x5C7C8662L /*Battle_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Battle_Walk(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 250) {
			if (changeState(state -> Battle_Walk_Back(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(12)) {
				if (changeState(state -> Battle_Attack_1(blendTime)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= -20 && getVariable(0xE5BD13F2L /*_Degree*/) <= 20) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Battle_Attack_3(blendTime)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= -20 && getVariable(0xE5BD13F2L /*_Degree*/) <= 20) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Battle_Attack_1(blendTime)))
					return;
			}
		}
		doAction(4272993707L /*BATTLE_MOVE*/, blendTime, onDoActionEnd -> moveAround(350 + Rnd.get(300, 700), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 100)));
	}

	protected void Battle_Turn(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x3B7EDA0FL /*Battle_Turn*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3101627639L /*BATTLE_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToTarget, 0, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 500)));
	}

	protected void Battle_Attack_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x100909D7L /*Battle_Attack_1*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1561812367L /*ATTACK_NORMAL_A*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCB4271F4L /*Battle_Attack_2*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(319900647L /*ATTACK_NORMAL_1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x92CAF548L /*Battle_Attack_3*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(4047174547L /*ATTACK_NORMAL_2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6A52455DL /*Battle_Attack_4*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1120120107L /*ATTACK_NORMAL_3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_JumpAttack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE2EB572AL /*Battle_Attack_JumpAttack*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(4203360271L /*ATTACK_JUMP_D*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x557F7B7FL /*Battle_Attack_Roar*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(93247687L /*ATTACK_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Berserk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xEE02281EL /*Battle_Berserk*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xAF2158BEL /*_isBerserk*/, 1);
		doAction(93247687L /*ATTACK_ROAR*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_1_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC6C606ADL /*Battle_Attack_1_B*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3178080055L /*ATTACK_NORMAL_1_B*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_2_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x45A491CBL /*Battle_Attack_2_B*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3389003829L /*ATTACK_NORMAL_2_B*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack_3_B(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDA68227L /*Battle_Attack_3_B*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(157864114L /*ATTACK_NORMAL_3_B*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 3000));
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
		if (target != null && getTargetHp(target) > 0 && getState() == 0x866C7489L /*Wait*/ && target != null && isCreatureVisible(target, false)) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/) {
			if (changeState(state -> Detect_Target(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStuned(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
