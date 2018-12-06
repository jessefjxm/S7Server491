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
@IAIName("sorceress_boss")
public class Ai_sorceress_boss extends CreatureAI {
	public Ai_sorceress_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 100);
		setVariable(0x49BE15E5L /*_HpRate*/, 0);
		setVariable(0x91E3CC69L /*_Random*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Move_Random(0.4)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Detect_Target(0.3)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1500, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		doAction(2658402471L /*DETECT_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		doAction(1544982863L /*BATTLE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Summon_Bat(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x69AD88DEL /*Summon_Bat*/);
		doAction(512753472L /*SUMMON_CREATURE*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < 3000 && target != null && getTargetCharacterKey(target) == 60013) {
				createParty(1, 1);
			}
			changeState(state -> Wait(blendTime));
		});
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
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2500));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		setVariable(0x49BE15E5L /*_HpRate*/, getHpRate());
		setVariable(0x91E3CC69L /*_Random*/, getVariable(0x1F7FA779L /*getRandom(80*/) + getVariable(0xF785039FL /*_HpRate)*/));
		if(getCallCount() == 20) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 800 && target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) >= 40 && target != null && getDistanceToTarget(target) <= 100) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Battle_Walk_Around(0.5)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> Attack_Closed(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1600 && target != null && getDistanceToTarget(target) >= 300) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Attack_Ranged(0.3)))
					return;
			}
		}
		if (getVariable(0x91E3CC69L /*_Random*/) < 10) {
			if (changeState(state -> Attack_DarkBlaze(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 100));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1600 && target != null && getDistanceToTarget(target) > 800) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Ranged(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 300) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 200)));
	}

	protected void Battle_Walk_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC3F442L /*Battle_Walk_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 100) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		doAction(2606257652L /*BATTLE_RUN_BACK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk_Back(blendTime), 200)));
	}

	protected void Battle_Teleport_TargetPosition(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xD2894EE8L /*Battle_Teleport_TargetPosition*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3465682096L /*BATTLE_TELEPORT_START*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Attack_FearContagion(blendTime), 2000)));
	}

	protected void Battle_Teleport_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xAD6BF1B3L /*Battle_Teleport_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3251075909L /*BATTLE_TELEPORT_B*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Battle_Walk_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x2EE72F2DL /*Battle_Walk_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 100) {
			if (changeState(state -> Battle_Walk_Back(0.5)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Battle_Walk(0.5)))
				return;
		}
		doAction(2689517725L /*BATTLE_RUN*/, blendTime, onDoActionEnd -> moveAround(150 + Rnd.get(100, 150), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 100)));
	}

	protected void Attack_Closed(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x75A13CA4L /*Attack_Closed*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3312738253L /*ATTACK_C_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Closed_2(blendTime)));
	}

	protected void Attack_Closed_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7BF052DFL /*Attack_Closed_2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(940316987L /*ATTACK_C_NORMAL_a*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Closed_3(blendTime)));
	}

	protected void Attack_Closed_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA3E5224EL /*Attack_Closed_3*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2011212284L /*ATTACK_C_NORMAL_b*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Closed_4(blendTime)));
	}

	protected void Attack_Closed_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB45606F4L /*Attack_Closed_4*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1401362024L /*ATTACK_C_NORMAL_c*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Ranged(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x22BD8483L /*Attack_Ranged*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(4220176001L /*ATTACK_R_NORMAL_1*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Ranged_2(blendTime)));
	}

	protected void Attack_Ranged_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x9E5DB730L /*Attack_Ranged_2*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1279591049L /*ATTACK_R_NORMAL_1a*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Ranged_3(blendTime)));
	}

	protected void Attack_Ranged_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC72FCB4AL /*Attack_Ranged_3*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if(Rnd.getChance(12)) {
			if (changeState(state -> Attack_MentalCollapse(0.3)))
				return;
		}
		doAction(4235053359L /*ATTACK_R_NORMAL_1b*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Ranged_4(blendTime)));
	}

	protected void Attack_Ranged_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCCA3E27L /*Attack_Ranged_4*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(563593664L /*ATTACK_R_NORMAL_1c*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_MentalCollapse(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x31914C1BL /*Attack_MentalCollapse*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(2051736602L /*Attack_Skill_MentalCollapse*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_DarkBlaze(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4D3CF45CL /*Attack_DarkBlaze*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(1963106238L /*Attack_Skill_DarkBlaze_Start*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_FearContagion(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3289DFD5L /*Attack_FearContagion*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3180798925L /*Attack_Skill_FearContagion*/, blendTime, onDoActionEnd -> changeState(state -> Attack_MentalCollapse(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target != null && getTargetHp(target) > 0 && (getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/) && target != null && isCreatureVisible(target, false)) {
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
		if (getState() != 0x4D3CF45CL /*Attack_DarkBlaze*/ && target != null && getDistanceToTarget(target) < 2000 && target != null && getDistanceToTarget(target) >= 800) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Battle_Teleport_TargetPosition(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() != 0x4D3CF45CL /*Attack_DarkBlaze*/ && target != null && getDistanceToTarget(target) < 1200 && target != null && getDistanceToTarget(target) >= 300) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Ranged(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() != 0x4D3CF45CL /*Attack_DarkBlaze*/ && target != null && getDistanceToTarget(target) < 300) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Battle_Teleport_Back(0.3)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		return EAiHandlerResult.BYPASS;
	}
}
