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
@IAIName("skeletonking_boss")
public class Ai_skeletonking_boss extends CreatureAI {
	public Ai_skeletonking_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x49BE15E5L /*_HpRate*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
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
			if (changeState(state -> Detect_Target(0.35)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 500 + Rnd.get(-500,500)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void LostTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF06CDECAL /*LostTarget*/);
		doAction(933998996L /*LOST_TARGET*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 500));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
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
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 200));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xE5BD13F2L /*_Degree*/, getDegreeToTarget());
		setVariable(0x49BE15E5L /*_HpRate*/, getHpRate());
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> LostTarget(0.4)))
				return;
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -45) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Battle_Turn_Left(0.4)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) >= 45) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Battle_Turn_Right(0.4)))
					return;
			}
		}
		if (getVariable(0xE5BD13F2L /*_Degree*/) <= -90 && getVariable(0xE5BD13F2L /*_Degree*/) >= -179) {
			if(Rnd.getChance(100)) {
				if (changeState(state -> Battle_Turn_180(0.5)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 600 && getVariable(0x49BE15E5L /*_HpRate*/) < 20) {
			if(Rnd.getChance(20)) {
				if (changeState(state -> Attack_Around(0.5)))
					return;
			}
		}
		if(Rnd.getChance(10)) {
			if (changeState(state -> Attack_DoubleSwing(0.5)))
				return;
		}
		if(Rnd.getChance(12)) {
			if (changeState(state -> Attack_Whip(0.5)))
				return;
		}
		if(getCallCount() == 13) {
			if (changeState(state -> Attack_Upper(0.5)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 400) {
				if (changeState(state -> Battle_Run(0.5)))
					return;
			}
			scheduleState(state -> Battle_Wait(blendTime), 100);
		});
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x49BE15E5L /*_HpRate*/, getHpRate());
		if (target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1000) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if(getCallCount() == 30) {
				if (changeState(state -> Attack_Range(0.5)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 600 && getVariable(0x49BE15E5L /*_HpRate*/) < 20) {
			if(Rnd.getChance(5)) {
				if (changeState(state -> Attack_Around(0.5)))
					return;
			}
		}
		if(Rnd.getChance(12)) {
			if (changeState(state -> Attack_Whip(0.5)))
				return;
		}
		if(Rnd.getChance(15)) {
			if (changeState(state -> Attack_Upper(0.5)))
				return;
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 100)));
	}

	protected void Battle_Walk_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.around);
		setState(0x2EE72F2DL /*Battle_Walk_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Battle_Walk(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 250) {
			if (changeState(state -> Battle_Walk_Back(0.4)))
				return;
		}
		if(Rnd.getChance(5)) {
			if (changeState(state -> Attack_DoubleSwing(0.5)))
				return;
		}
		if(Rnd.getChance(8)) {
			if (changeState(state -> Attack_Whip(0.5)))
				return;
		}
		if(Rnd.getChance(8)) {
			if (changeState(state -> Attack_Upper(0.5)))
				return;
		}
		doAction(4272993707L /*BATTLE_MOVE*/, blendTime, onDoActionEnd -> moveAround(450 + Rnd.get(350, 550), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Wait(blendTime), 100)));
	}

	protected void Battle_Walk_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC3F442L /*Battle_Walk_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x49BE15E5L /*_HpRate*/, getHpRate());
		if (target != null && getDistanceToTarget(target) > 550) {
			if(getCallCount() == 50) {
				if (changeState(state -> Battle_Wait(0.4)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Attack_Range(0.5)))
					return;
			}
		}
		if(Rnd.getChance(3)) {
			if (changeState(state -> Attack_Upper(0.5)))
				return;
		}
		if(Rnd.getChance(3)) {
			if (changeState(state -> Attack_Whip(0.5)))
				return;
		}
		if(Rnd.getChance(2)) {
			if (changeState(state -> Attack_DoubleSwing(0.5)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(1)) {
				if (changeState(state -> Attack_Around(0.5)))
					return;
			}
		}
		doAction(662185133L /*BATTLE_WALK_BACK*/, blendTime, onDoActionEnd -> escape(1500, () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk_Back(blendTime), 100)));
	}

	protected void Battle_Run(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xEB438BF9L /*Battle_Run*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Battle_Wait(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if(getCallCount() == 30) {
				if (changeState(state -> Attack_Range(0.5)))
					return;
			}
		}
		doAction(375078785L /*BATTLE_WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Battle_Turn_Left(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x8508367EL /*Battle_Turn_Left*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x49BE15E5L /*_HpRate*/, getHpRate());
		doAction(1664053560L /*BATTLE_TURN_LEFT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 450) {
				if (changeState(state -> Battle_Walk(0.4)))
					return;
			}
			if (target != null && getDistanceToTarget(target) < 250) {
				if (changeState(state -> Battle_Walk_Back(0.4)))
					return;
			}
			if (target != null && getDistanceToTarget(target) < 350) {
				if(Rnd.getChance(12)) {
					if (changeState(state -> Attack_DoubleSwing(0.5)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) < 350) {
				if(Rnd.getChance(9)) {
					if (changeState(state -> Attack_Whip(0.5)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) < 500 && getVariable(0x49BE15E5L /*_HpRate*/) < 20) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Around(0.5)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Turn_Right(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7062C620L /*Battle_Turn_Right*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		setVariable(0x49BE15E5L /*_HpRate*/, getHpRate());
		doAction(2806128650L /*BATTLE_TURN_RIGHT*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) > 450) {
				if (changeState(state -> Battle_Walk(0.4)))
					return;
			}
			if (target != null && getDistanceToTarget(target) < 250) {
				if (changeState(state -> Battle_Walk_Back(0.4)))
					return;
			}
			if (target != null && getDistanceToTarget(target) < 350) {
				if(Rnd.getChance(12)) {
					if (changeState(state -> Attack_DoubleSwing(0.5)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) < 350) {
				if(Rnd.getChance(9)) {
					if (changeState(state -> Attack_Whip(0.5)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) < 500 && getVariable(0x49BE15E5L /*_HpRate*/) < 20) {
				if(Rnd.getChance(10)) {
					if (changeState(state -> Attack_Around(0.5)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Battle_Turn_180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF94905AEL /*Battle_Turn_180*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(956987616L /*BATTLE_TURN_180*/, blendTime, onDoActionEnd -> {
			if (target != null && getDistanceToTarget(target) < 500) {
				if(Rnd.getChance(15)) {
					if (changeState(state -> Attack_Whip(0.5)))
						return;
				}
			}
			if (target != null && getDistanceToTarget(target) < 800) {
				if(Rnd.getChance(25)) {
					if (changeState(state -> Attack_Upper(0.5)))
						return;
				}
			}
			changeState(state -> Battle_Wait(blendTime));
		});
	}

	protected void Attack_DoubleSwing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4CC2E4C5L /*Attack_DoubleSwing*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Whip(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF90ABC26L /*Attack_Whip*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 350) {
			if (changeState(state -> Attack_Upper(0.3)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Upper(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1554BBB1L /*Attack_Upper*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(931985982L /*BATTLE_ATTACK3*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Around(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4B699344L /*Attack_Around*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3495545464L /*BATTLE_SUPERATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Range(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x93D918A4L /*Attack_Range*/);
		if (isTargetLost()) {
			if (changeState(state -> LostTarget(blendTime)))
				return;
		}
		doAction(3719417244L /*BATTLE_RANGEATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
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
			if (changeState(state -> Detect_Target(0.4)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
