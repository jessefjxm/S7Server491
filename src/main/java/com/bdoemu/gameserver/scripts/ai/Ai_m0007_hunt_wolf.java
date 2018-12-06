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
@IAIName("m0007_hunt_wolf")
public class Ai_m0007_hunt_wolf extends CreatureAI {
	public Ai_m0007_hunt_wolf(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x1995ACA8L /*_isMove*/, getVariable(0xAA621BBFL /*AI_Move*/));
		setVariable(0xEF7AE808L /*_RoarCount*/, 1);
		setVariable(0xA2E704C3L /*_Party_Vanish*/, getVariable(0xFD5848ABL /*AI_Party_Vanish*/));
		if (getVariable(0x1995ACA8L /*_isMove*/) == 2) {
			if (changeState(state -> Wait2(0.1)))
				return;
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) != 2) {
			if (changeState(state -> Wait(0.1)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> InitialState(blendTime), 100));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getVariable(0x1995ACA8L /*_isMove*/) != 2) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1200)) {
				if (changeState(state -> Search_Enemy(0.2)))
					return;
			}
		}
		if (getVariable(0x1995ACA8L /*_isMove*/) == 2) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1600)) {
				if (changeState(state -> Battle_Wait(0.2)))
					return;
			}
		}
		if (getVariable(0x64490D98L /*AI_RandomMove*/) == 1) {
			if(getCallCount() == 5) {
				if (changeState(state -> Move_Random(0.4)))
					return;
			}
		}
		if (target != null && getTargetHp(target) == 0 && getVariable(0x1995ACA8L /*_isMove*/) == 2) {
			if(getCallCount() == 5) {
				if (changeState(state -> Wait3(0.3)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
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
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1600)) {
				if (changeState(state -> Search_Enemy(0.2)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 12) {
				if (changeState(state -> Move_Return_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Search_Enemy(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF465EDCL /*Search_Enemy*/);
		doAction(11967395L /*SEARCH_ENEMY*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 6000));
	}

	protected void Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x60186BFFL /*Roar*/);
		setVariable(0xEF7AE808L /*_RoarCount*/, getVariable(0xEF7AE808L /*_RoarCount*/) - 1);
		summonCharacter(23110, 2, 200, 0, 0, true);
		summonCharacter(23110, 2, 400, 0, 200, true);
		summonCharacter(23110, 2, -200, 0, 0, true);
		doAction(133347576L /*ROAR*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 5000));
	}

	protected void Move_Return_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7FF7456BL /*Move_Return_Logic*/);
		setVariable(0xEF7AE808L /*_RoarCount*/, 1);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		changeState(state -> Move_Return(blendTime));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xEF7AE808L /*_RoarCount*/, 1);
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
		changeState(state -> Move_Return_Logic(blendTime));
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
		changeState(state -> Move_Return_Logic(blendTime));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return_Logic(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		setVariable(0x3F487035L /*_Hp*/, getHpRate());
		if (target != null && getTargetHp(target) == 0 && getVariable(0x1995ACA8L /*_isMove*/) == 2) {
			if (changeState(state -> Wait3(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 3000) {
			if (changeState(state -> Move_Return_Logic(0.1)))
				return;
		}
		if (getDistanceToSpawn() > 8000) {
			if (changeState(state -> Move_Return_Logic(0.1)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 1000 && getVariable(0x1995ACA8L /*_isMove*/) != 2 && getVariable(0x3F487035L /*_Hp*/) < 70 && getVariable(0xEF7AE808L /*_RoarCount*/) == 1 && getPartyMembersCount()<= 0) {
			if (changeState(state -> Roar(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 200) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Battle_Attack2(0.3)))
					return;
			}
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Attack1(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 50 && target != null && getDistanceToTarget(target) <= 5000) {
			if (changeState(state -> Move_Chaser(0.2)))
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
		if (target != null && getTargetHp(target) == 0 && getVariable(0x1995ACA8L /*_isMove*/) == 2) {
			if (changeState(state -> Wait3(0.3)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> TargetLost(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 3000) {
			if (changeState(state -> Move_Return_Logic(0.1)))
				return;
		}
		if (getDistanceToSpawn() > 8000) {
			if (changeState(state -> Move_Return_Logic(0.1)))
				return;
		}
		if (target != null && getTargetHp(target) > 0 && target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Battle_Attack2(0.3)))
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

	protected void Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x84794EB4L /*Wait2*/);
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Escape(blendTime), 1000));
	}

	protected void Wait3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x75E9DA83L /*Wait3*/);
		clearAggro(true);
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getTargetHp(object) > 0 && getDistanceToTarget(object) < 1600)) {
			if (changeState(state -> Search_Enemy(0.2)))
				return;
		}
		if(getCallCount() == 10) {
			if (changeState(state -> Die_Logic(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait3(blendTime), 1000));
	}

	protected void Move_Escape(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCBA2B4E2L /*Move_Escape*/);
		doAction(3452277295L /*MOVE_WALK_HIDE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, -1500, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Position(blendTime), 1000)));
	}

	protected void Move_Escape2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB53CEA3AL /*Move_Escape2*/);
		doAction(3452277295L /*MOVE_WALK_HIDE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, -1000, -1500, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Position(blendTime), 1000)));
	}

	protected void Move_Escape3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x367DF841L /*Move_Escape3*/);
		doAction(3452277295L /*MOVE_WALK_HIDE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 1000, -1500, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Position(blendTime), 1000)));
	}

	protected void Move_Escape4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x58356677L /*Move_Escape4*/);
		doAction(3452277295L /*MOVE_WALK_HIDE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Relative, 0, 0, -300, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Position(blendTime), 1000)));
	}

	protected void Move_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFF3E58ABL /*Move_Position*/);
		doAction(1932048698L /*MOVE_WALK_REVEAL*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> Find_Boss(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Find_Boss(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8717ED05L /*Find_Boss*/);
		doTeleport(EAIMoveDestType.Random, 0, -500, 0, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Position(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Die_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE70D4D89L /*Die_Logic*/);
		doTeleport(EAIMoveDestType.Absolute, -333188, 61581, 1, 1);
		if(getCallCount() == 2) {
			if (changeState(state -> Die2(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die_Logic(blendTime), 5000));
	}

	protected void Die2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x6E60EEB7L /*Die2*/);
		doAction(4260439228L /*DIE2*/, blendTime, onDoActionEnd -> scheduleState(state -> Die2(blendTime), 1000));
	}

	protected void Battle_Attack1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xB5FDC949L /*Battle_Attack1*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(31162842L /*BATTLE_ATTACK1*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	protected void Battle_Attack2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD72BCC90L /*Battle_Attack2*/);
		if (isTargetLost()) {
			if (changeState(state -> TargetLost(blendTime)))
				return;
		}
		doAction(2323327157L /*BATTLE_ATTACK2*/, blendTime, onDoActionEnd -> changeState(state -> Battle_Wait(blendTime)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0x866C7489L /*Wait*/) {
			if (changeState(state -> Battle_Wait(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0xA2E704C3L /*_Party_Vanish*/) == 0 && getVariable(0x1995ACA8L /*_isMove*/) == 2) {
			if (changeState(state -> Battle_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
