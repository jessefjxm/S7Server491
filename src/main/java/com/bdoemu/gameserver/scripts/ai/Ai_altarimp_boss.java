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
@IAIName("altarimp_boss")
public class Ai_altarimp_boss extends CreatureAI {
	public Ai_altarimp_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_Hp*/, 0);
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, 0);
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x3826DD12L /*_CrazySwingAttCount*/, 2);
		setVariable(0xA20D0C7BL /*_AttackNormalCount*/, 0);
		setVariable(0xEBAA9AEAL /*_isRage*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		doAction(3630482305L /*AI_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A053101L /*Logic*/);
		if (findTargetInAggro(EAIFindTargetType.Enemy, EAIFindType.normal, object -> getDistanceToTarget(object) < 2000 && getTargetHp(object) > 0)) {
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
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 2000)) {
			if (changeState(state -> Detect_Target(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 1000, 1500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Detect_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC3F34D2L /*Detect_Target*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 0);
		setVariable(0xEBAA9AEAL /*_isRage*/, 0);
		setVariable(0x3826DD12L /*_CrazySwingAttCount*/, 0);
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

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		if (getVariable(0xFA9DA674L /*_isBattleMode*/) == 1 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 5) {
			if (changeState(state -> FailFindPathToTarget(0.3)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
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

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0xFA9DA674L /*_isBattleMode*/, 1);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		setVariable(0x3715AB9DL /*_MyBodyHeight*/, getBodyHeight());
		setVariable(0x27274876L /*_TargetBodyHeight*/, 0 - getTargetBodyHeight());
		if (target != null && checkBuff(target, 35)) {
			if (changeState(state -> Special_Damage_Stun(0.3)))
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
		if(Rnd.getChance(10)) {
			if (changeState(state -> Logic(0.3)))
				return;
		}
		if (getVariable(0xEBAA9AEAL /*_isRage*/) == 0 && getVariable(0x3F487035L /*_Hp*/) <= 40) {
			if (changeState(state -> Attack_Rage(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) <= 300 && getDistanceToSpawn() < 3000) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Back_Walk(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) > 150) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Turn180(0.3)))
					return;
			}
		}
		if (target != null && getAngleToTarget(target) < -150) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_Turn180(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 600 && getVariable(0x3F487035L /*_Hp*/) <= 60 && getVariable(0x3826DD12L /*_CrazySwingAttCount*/) >= 2 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_CrazySwing_Start(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 600 && getVariable(0x3F487035L /*_Hp*/) <= 40 && getVariable(0x3826DD12L /*_CrazySwingAttCount*/) >= 1 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Attack_CrazySwing_Start(0.3)))
					return;
			}
		}
		if (getVariable(0xA20D0C7BL /*_AttackNormalCount*/) >= 3 && target != null && getDistanceToTarget(target) < 1200 && target != null && getAngleToTarget(target) > -25 && target != null && getAngleToTarget(target) < 25 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Hook_Normal(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Attack_Swing_Normal(0.3)))
					return;
			}
		}
		if (getVariable(0x3F487035L /*_Hp*/) >= 50 && target != null && getDistanceToTarget(target) < 200 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Attack_Normal(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 1200 && target != null && getAngleToTarget(target) > -25 && target != null && getAngleToTarget(target) < 25 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(40)) {
				if (changeState(state -> Attack_Hook_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) >= 600) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
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
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && checkBuff(target, 35)) {
			if (changeState(state -> Special_Damage_Stun(0.3)))
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
		if (target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Attack_Swing_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1200 && target != null && getAngleToTarget(target) > -25 && target != null && getAngleToTarget(target) < 25 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Hook_Normal(0.3)))
					return;
			}
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Run(blendTime), 100)));
	}

	protected void Back_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xF006D382L /*Back_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && checkBuff(target, 35)) {
			if (changeState(state -> Special_Damage_Stun(0.3)))
				return;
		}
		doAction(919797391L /*BACK_WALK*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Hook_Normal(blendTime)));
	}

	protected void Battle_Walk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x1416A51CL /*Battle_Walk*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && checkBuff(target, 35)) {
			if (changeState(state -> Special_Damage_Stun(0.3)))
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
		if (target != null && getDistanceToTarget(target) >= 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.4)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Attack_Swing_Normal(0.3)))
					return;
			}
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Battle_Walk(blendTime), 100)));
	}

	protected void Battle_Walk_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xAC3F442L /*Battle_Walk_Back*/);
		if (isTargetLost()) {
			if (changeState(state -> Logic(blendTime)))
				return;
		}
		if (target != null && checkBuff(target, 35)) {
			if (changeState(state -> Special_Damage_Stun(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 600 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(60)) {
				if (changeState(state -> Attack_Swing_Normal(0.3)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1500 && target != null && getAngleToTarget(target) > -25 && target != null && getAngleToTarget(target) < 25 && target != null && getTargetHeightdiff(target) > getVariable(0x27274876L /*_TargetBodyHeight*/) && target != null && getTargetHeightdiff(target) < getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Hook_Normal(0.3)))
					return;
			}
		}
		if (target != null && getTargetHeightdiff(target) < getVariable(0x27274876L /*_TargetBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		if (target != null && getTargetHeightdiff(target) > getVariable(0x3715AB9DL /*_MyBodyHeight*/)) {
			if (changeState(state -> Battle_Run(0.3)))
				return;
		}
		doAction(1516377807L /*MOVE_BACK*/, blendTime, onDoActionEnd -> escape(1000, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
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
		doAction(1531277180L /*DAMAGE_STUN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Stun_End(blendTime), 10000));
	}

	protected void Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA432B7EDL /*Damage_Stun_End*/);
		doAction(3912128442L /*DAMAGE_STUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Special_Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEF3EFF55L /*Special_Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> Special_Damage_Stun_Ing(blendTime), 1000));
	}

	protected void Special_Damage_Stun_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB97BF3E2L /*Special_Damage_Stun_Ing*/);
		doAction(1531277180L /*DAMAGE_STUN_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Special_Damage_Stun_End(blendTime), 10000));
	}

	protected void Special_Damage_Stun_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC21C1C3DL /*Special_Damage_Stun_End*/);
		doAction(3912128442L /*DAMAGE_STUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Attack_Rage(blendTime), 1000));
	}

	protected void Special_Stun_Attack_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3712F3ABL /*Special_Stun_Attack_Ready*/);
		doAction(3776020037L /*ATTACK_RAGE*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Rage(blendTime)));
	}

	protected void Attack_Hook_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x729CBFFDL /*Attack_Hook_Normal*/);
		setVariable(0xA20D0C7BL /*_AttackNormalCount*/, 0);
		if (target != null && checkBuff(target, 35)) {
			if (changeState(state -> Special_Damage_Stun(0.3)))
				return;
		}
		doAction(3919158726L /*ATTACK_HOOK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (target != null && checkBuff(target, 35)) {
			if (changeState(state -> Special_Damage_Stun(0.3)))
				return;
		}
		doAction(3824158542L /*ATTACK_NORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Swing_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB9E27D3FL /*Attack_Swing_Normal*/);
		setVariable(0xA20D0C7BL /*_AttackNormalCount*/, getVariable(0xA20D0C7BL /*_AttackNormalCount*/) + 1);
		if (target != null && checkBuff(target, 35)) {
			if (changeState(state -> Special_Damage_Stun(0.3)))
				return;
		}
		doAction(1334856842L /*ATTACK_HOOKSWING1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Attack_Rage(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x51E2364EL /*Attack_Rage*/);
		setVariable(0xEBAA9AEAL /*_isRage*/, 1);
		doAction(3776020037L /*ATTACK_RAGE*/, blendTime, onDoActionEnd -> changeState(state -> Attack_Rage_Logic(blendTime)));
	}

	protected void Attack_Rage_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD1EDD18FL /*Attack_Rage_Logic*/);
		if (target != null && getDistanceToTarget(target) < 600) {
			if (changeState(state -> Attack_CrazySwing_Start(blendTime)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Attack_CrazySwing_Start(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1CD1CF14L /*Attack_CrazySwing_Start*/);
		setVariable(0x3826DD12L /*_CrazySwingAttCount*/, getVariable(0x3826DD12L /*_CrazySwingAttCount*/) - 1);
		doAction(2933996377L /*ATTACK_CRAZYSWING_START*/, blendTime, onDoActionEnd -> changeState(state -> Attack_CrazySwing_Ing(blendTime)));
	}

	protected void Attack_CrazySwing_Ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x1E2B017FL /*Attack_CrazySwing_Ing*/);
		if (getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if(getCallCount() == 5) {
				if (changeState(state -> Attack_CrazySwing_Logic(0.3)))
					return;
			}
		}
		if (getVariable(0xEBAA9AEAL /*_isRage*/) == 1) {
			if(getCallCount() == 10) {
				if (changeState(state -> Attack_CrazySwing_Logic(0.3)))
					return;
			}
		}
		doAction(3179342358L /*ATTACK_CRAZYSWING_LOOP*/, blendTime, onDoActionEnd -> changeState(state -> Attack_CrazySwing_Ing(blendTime)));
	}

	protected void Attack_CrazySwing_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x26EE5BCAL /*Attack_CrazySwing_Logic*/);
		if (getVariable(0xEBAA9AEAL /*_isRage*/) == 0) {
			if (changeState(state -> Attack_CrazySwing_End(0.3)))
				return;
		}
		if (getVariable(0xEBAA9AEAL /*_isRage*/) == 1) {
			if (changeState(state -> Attack_CrazySwing_End2(0.3)))
				return;
		}
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Attack_CrazySwing_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8CC0C4FAL /*Attack_CrazySwing_End*/);
		doAction(2042492754L /*ATTACK_CRAZYSWING_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_CrazySwing_End2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x94571D16L /*Attack_CrazySwing_End2*/);
		doAction(3490353332L /*ATTACK_CRAZYSWING_END2*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Attack_Turn180(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x70EBE789L /*Attack_Turn180*/);
		doAction(107658405L /*ATTACK_TURN180*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Delete_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xD43BC680L /*Delete_Die*/);
		doAction(2978442901L /*DIE_DELETE*/, blendTime, onDoActionEnd -> scheduleState(state -> Delete_Die(blendTime), 1000));
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
		if (target != null && checkBuff(target, 35) && getState() != 0xEF3EFF55L /*Special_Damage_Stun*/ && getState() != 0xB97BF3E2L /*Special_Damage_Stun_Ing*/ && getState() != 0xC21C1C3DL /*Special_Damage_Stun_End*/ && getState() != 0x51E2364EL /*Attack_Rage*/ && getState() != 0xD1EDD18FL /*Attack_Rage_Logic*/ && getState() != 0x1CD1CF14L /*Attack_CrazySwing_Start*/ && getState() != 0x1E2B017FL /*Attack_CrazySwing_Ing*/ && getState() != 0x26EE5BCAL /*Attack_CrazySwing_Logic*/ && getState() != 0x8CC0C4FAL /*Attack_CrazySwing_End*/ && getState() != 0x94571D16L /*Attack_CrazySwing_End2*/) {
			if (changeState(state -> Special_Damage_Stun(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getState() == 0x8377635AL /*Move_Random*/) {
			if (changeState(state -> Detect_Target(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
