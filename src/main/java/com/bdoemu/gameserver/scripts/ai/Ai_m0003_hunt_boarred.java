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
@IAIName("m0003_hunt_boarred")
public class Ai_m0003_hunt_boarred extends CreatureAI {
	public Ai_m0003_hunt_boarred(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xB0A90ED8L /*_RunAwayCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 800)) {
			if (changeState(state -> Search_Enemy(0.2)))
				return;
		}
		if (getVariable(0x64490D98L /*AI_RandomMove*/) == 1) {
			if(getCallCount() == 5) {
				if (changeState(state -> Move_Random(0.4)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xCF465EDCL /*Search_Enemy*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 800) {
			if (changeState(state -> Wait(0.2)))
				return;
		}
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> changeState(state -> Wait2(blendTime)));
	}

	protected void Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x84794EB4L /*Wait2*/);
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 300) {
			if (changeState(state -> Battle_Wait(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) >= 800) {
			if (changeState(state -> Wait(0.2)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait2(blendTime), 2000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 800, 1500, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 800)) {
				if (changeState(state -> Search_Enemy(0.2)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void TargetLost(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3496EAEL /*TargetLost*/);
		clearAggro(true);
		changeState(state -> Move_Return(blendTime));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		changeState(state -> Move_Return(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doAction(2407452960L /*Wait*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	protected void Run_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x5E320D92L /*Run_Random*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		setVariable(0xB0A90ED8L /*_RunAwayCount*/, getVariable(0xB0A90ED8L /*_RunAwayCount*/) + 1);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> escape(2500, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			if (getDistanceToSpawn() > 8000) {
				if (changeState(state -> Move_Return(0.1)))
					return true;
			}
			if (getVariable(0xB0A90ED8L /*_RunAwayCount*/) == 1) {
				if (changeState(state -> Battle_Wait(0.1)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> AggroClear_Run_Random(blendTime), 5000)));
	}

	protected void AggroClear_Run_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB85F042L /*AggroClear_Run_Random*/);
		clearAggro(true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 3000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (getDistanceToSpawn() > 8000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Attack1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 50 && target != null && getDistanceToTarget(target) <= 800) {
			if (changeState(state -> Move_Chaser(0.2)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) <= 5000) {
			if (changeState(state -> Chaser_Run(0.2)))
				return;
		}
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 2000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 3000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (getDistanceToSpawn() > 8000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Attack1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 800 && target != null && getDistanceToTarget(target) <= 5000) {
			if (changeState(state -> Chaser_Run(0.2)))
				return;
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
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
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 3000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (getDistanceToSpawn() > 8000) {
			if (changeState(state -> Move_Return(0.1)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Attack1(0.3)))
				return;
		}
		doAction(3208508703L /*CHASER_RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Chaser_Run(blendTime), 2000)));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Battle_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5FDC949L /*Battle_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> TargetChange_Logic(blendTime)));
	}

	protected void TargetChange_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A6D56A5L /*TargetChange_Logic*/);
		setVariable(0xB0A90ED8L /*_RunAwayCount*/, 0);
		changeState(state -> Battle_Wait(blendTime));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(840787941L /*DAMAGE_KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Run_Random(blendTime), 4000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xB0A90ED8L /*_RunAwayCount*/) == 0) {
			if (changeState(state -> Run_Random(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if(Rnd.getChance(50)) {
			if (changeState(state -> Damage_KnockDown(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
