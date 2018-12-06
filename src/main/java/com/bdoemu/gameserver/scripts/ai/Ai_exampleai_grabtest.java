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
@IAIName("exampleai_grabtest")
public class Ai_exampleai_grabtest extends CreatureAI {
	public Ai_exampleai_grabtest(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0xEBAA9AEAL /*_isRage*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		clearAggro(true);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Battle_Wait(blendTime)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000 && getTargetHp(object) > 0)) {
			if (changeState(state -> Search_Enemy(blendTime)))
				return;
		}
		if(getCallCount() == 10) {
			if (changeState(state -> Move_Random(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-1000,1000)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object, false) >= 0 && getDistanceToTarget(object, false) <= 2000)) {
			if (changeState(state -> Search_Enemy(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 1000, 1500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Clear_Buff(blendTime), 1000)));
	}

	protected void Clear_Buff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCFE88B9CL /*Clear_Buff*/);
		doAction(4188110314L /*CLEAR_BUFF*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Logic(blendTime), 500));
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
		clearAggro(true);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > 30000 && getVariable(0xEBAA9AEAL /*_isRage*/) == 1) {
			if (changeState(state -> Damage_Attack_Cancel(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_Hp*/) <= 50 && getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if (changeState(state -> Attack_Rage(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1300 && target != null && getAngleToTarget(target) > -25 && target != null && getAngleToTarget(target) < 25 && getVariable(0xEBAA9AEAL /*_isRage*/) == 1) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Hook_Fast(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 650 && getVariable(0xEBAA9AEAL /*_isRage*/) == 1) {
			if(Rnd.getChance(15)) {
				if (changeState(state -> Attack_Hook180(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) > 150 && target != null && getAngleToTarget(target) < -150) {
			if (changeState(state -> Attack_Turn180(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 650 && getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_HookSwing(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 600 && getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Swing_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1500 && target != null && getAngleToTarget(target) > -25 && target != null && getAngleToTarget(target) < 25 && getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Hook_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) <= 100) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Walk_Back(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) > 650 && getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if (changeState(state -> Battle_Walk(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 650 && getVariable(0xEBAA9AEAL /*_isRage*/) == 1) {
			if (changeState(state -> Battle_Run(0.3)))
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
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Swing_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 700 && target != null && getAngleToTarget(target) > -25 && target != null && getAngleToTarget(target) < 25) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Hook_Normal(0.3)))
					return;
			}
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
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
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getDistanceToSpawn() > 4000) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1300) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 650 && getDistanceToTarget(target, false) <= 1300) && getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Hook_Normal(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 650 && getDistanceToTarget(target, false) <= 1300) && getVariable(0xEBAA9AEAL /*_isRage*/) == 1) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Hook_Fast(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 650) {
			if(Rnd.getChance(45)) {
				if (changeState(state -> Attack_HookSwing(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 650) {
			if(Rnd.getChance(45)) {
				if (changeState(state -> Attack_Swing_Normal(0.3)))
					return;
			}
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
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
		if (target != null && (getDistanceToTarget(target, false) >= 650 && getDistanceToTarget(target, false) <= 1300) && getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Hook_Normal(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 650 && getDistanceToTarget(target, false) <= 1300) && getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Hook_Fast(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 650) && getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_HookSwing(0.3)))
					return;
			}
		}
		if (target != null && (getDistanceToTarget(target, false) >= 500 && getDistanceToTarget(target, false) <= 650) && getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Swing_Normal(0.3)))
					return;
			}
		}
		doAction(1516377807L /*MOVE_BACK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk_Back(blendTime), 100)));
	}

	protected void Damage_KnockBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF725BC4L /*Damage_KnockBack*/);
		doAction(3633065904L /*DAMAGE_KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Down(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB85CDA8DL /*Damage_Down*/);
		doAction(844699197L /*DAMAGE_DOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_StandUp(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3891BF54L /*Damage_StandUp*/);
		doAction(927041621L /*DAMAGE_STANDUP*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_Ing(blendTime), 1000));
	}

	protected void Damage_Stun_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E79F126L /*Damage_Stun_Ing*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_End(blendTime), 10000));
	}

	protected void Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA432B7EDL /*Damage_Stun_End*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_Hook_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x729CBFFDL /*Attack_Hook_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3318338574L /*ATTACK_HOOK*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Swing_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB9E27D3FL /*Attack_Swing_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(1334856842L /*ATTACK_HOOKSWING1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Hook_Fast(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD5C8F353L /*Attack_Hook_Fast*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3919158726L /*ATTACK_HOOK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_HookSwing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x4ADBE634L /*Attack_HookSwing*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(3250900532L /*ATTACK_HOOKSWING*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Hook180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xBEB6DB78L /*Attack_Hook180*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(2596937255L /*ATTACK_HOOK180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Rage(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x51E2364EL /*Attack_Rage*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		setVariable(0xEBAA9AEAL /*_isRage*/, 1);
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		doAction(3776020037L /*ATTACK_RAGE*/, blendTime, onDoActionEnd -> changeState(state -> Attack_CrazySwing_Start(blendTime)));
	}

	protected void Attack_CrazySwing_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1CD1CF14L /*Attack_CrazySwing_Start*/);
		if (isTargetLost()) {
			if (changeState(state -> Attack_CrazySwing_End(blendTime)))
				return;
		}
		doAction(2933996377L /*ATTACK_CRAZYSWING_START*/, blendTime, onDoActionEnd -> changeState(state -> Attack_CrazySwing_Ing(blendTime)));
	}

	protected void Attack_CrazySwing_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1E2B017FL /*Attack_CrazySwing_Ing*/);
		if (isTargetLost()) {
			if (changeState(state -> Attack_CrazySwing_End(blendTime)))
				return;
		}
		doAction(3179342358L /*ATTACK_CRAZYSWING_LOOP*/, blendTime, onDoActionEnd -> {
			if(getCallCount() == 6) {
				if (changeState(state -> Attack_CrazySwing_End(0.3)))
					return;
			}
			changeState(state -> Attack_CrazySwing_Ing(blendTime));
		});
	}

	protected void Attack_CrazySwing_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8CC0C4FAL /*Attack_CrazySwing_End*/);
		doAction(2042492754L /*ATTACK_CRAZYSWING_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Damage_Attack_Cancel(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x6C5483BEL /*Damage_Attack_Cancel*/);
		setVariable(0xEBAA9AEAL /*_isRage*/, 0);
		doAction(3152228051L /*DAMAGE_CANCEL*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_Turn180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x70EBE789L /*Attack_Turn180*/);
		if (isTargetLost()) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		doAction(107658405L /*ATTACK_TURN180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Search_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/) {
			if (changeState(state -> Search_Enemy(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && checkBuff(target, 34) && getState() != 0x69E1FC3AL /*Damage_KnockDown*/ && getState() != 0xBF725BC4L /*Damage_KnockBack*/ && getState() != 0x3FB3341CL /*Damage_Stun*/ && getState() != 0x2E79F126L /*Damage_Stun_Ing*/ && getState() != 0xA432B7EDL /*Damage_Stun_End*/) {
			if (changeState(state -> Damage_KnockDown(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (target != null && checkBuff(target, 34) && getState() != 0xBF725BC4L /*Damage_KnockBack*/ && getState() != 0x3FB3341CL /*Damage_Stun*/ && getState() != 0x2E79F126L /*Damage_Stun_Ing*/ && getState() != 0xA432B7EDL /*Damage_Stun_End*/) {
			if (changeState(state -> Damage_KnockBack(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleUpdateCombineWave(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target == null) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		}
		if ((getState() == 0x51E2364EL /*Attack_Rage*/ || getState() == 0x1CD1CF14L /*Attack_CrazySwing_Start*/ || getState() == 0x1E2B017FL /*Attack_CrazySwing_Ing*/) && getSelfCombinePoint() == 14) {
			if (changeState(state -> Damage_Attack_Cancel(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfCombinePoint() == 59 && (getState() == 0x6C5483BEL /*Damage_Attack_Cancel*/ || getState() == 0x51E2364EL /*Attack_Rage*/ || getState() == 0x866C7489L /*Wait*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0x69E1FC3AL /*Damage_KnockDown*/)) {
			if (changeState(state -> Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getSelfCombinePoint() == 26 && getVariable(0xEBAA9AEAL /*_isRage*/) == 1 && getState() != 0x6C5483BEL /*Damage_Attack_Cancel*/ && getState() != 0x51E2364EL /*Attack_Rage*/) {
			if (changeState(state -> Attack_Rage(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
