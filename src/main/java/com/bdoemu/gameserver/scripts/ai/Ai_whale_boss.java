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
@IAIName("whale_boss")
public class Ai_whale_boss extends CreatureAI {
	public Ai_whale_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, 0);
		setVariable(0x22E0142EL /*_TakeDamageCount*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x940229E0L /*_isState*/, 0);
		setVariable(0x9AA4CDE2L /*_RegionNo*/, getVariable(0xD6D9F312L /*AI_RegionNo*/));
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 0) {
			if (changeState(state -> Start_Message_Bal(0.3)))
				return;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 1) {
			if (changeState(state -> Start_Message_Cal(0.3)))
				return;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 2) {
			if (changeState(state -> Start_Message_Med(0.3)))
				return;
		}
		doAction(2887951558L /*MOVE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Message_Bal(blendTime), 100));
	}

	protected void Start_Message_Bal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x355BCCDBL /*Start_Message_Bal*/);
		worldNotify(EChatNoticeType.Hunting, "GAME", "LUA_HUNTING_BAL_WHALE");
		doAction(2887951558L /*MOVE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void Start_Message_Cal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD8456834L /*Start_Message_Cal*/);
		worldNotify(EChatNoticeType.Hunting, "GAME", "LUA_HUNTING_CAL_WHALE");
		doAction(2887951558L /*MOVE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void Start_Message_Med(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7AD14352L /*Start_Message_Med*/);
		worldNotify(EChatNoticeType.Hunting, "GAME", "LUA_HUNTING_MED_WHALE");
		doAction(2887951558L /*MOVE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void Test_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1B49A884L /*Test_Logic*/);
		if(getCallCount() == 5 && Rnd.getChance(50)) {
			if (changeState(state -> Attack_Normal(blendTime)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Attack_Normal2(blendTime)))
				return;
		}
		doAction(2887951558L /*MOVE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Test_Logic(blendTime), 3000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (isPartyMember()) {
			setVariable(0xCBEEF8C7L /*_OwnerDistance*/, getDistanceToOwner());
		}
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 5400000) {
			if (changeState(state -> Die_Timeout_Logic(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 5000) {
			if (changeState(state -> Move_ReturnToParent(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_OwnerDistance*/) > 600) {
			if (changeState(state -> Move_ChaseToParent(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi()._PathEnd(getActor(), null));
		doAction(2887951558L /*MOVE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Move_PathEnd(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4D6E4BCAL /*Move_PathEnd*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi()._PathEnd(getActor(), null));
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 5400000) {
			if (changeState(state -> Die_Timeout_Logic(blendTime)))
				return;
		}
		doAction(2887951558L /*MOVE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 5400000) {
			if (changeState(state -> Die_Timeout_Logic(blendTime)))
				return;
		}
		doAction(1897083925L /*MOVE_WALK_UNDERWATER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 600, 1000, true, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> Move_ReturnToParent(blendTime)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(blendTime)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_TurnToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.turn);
		setState(0x4A712BB5L /*Move_TurnToParent*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 5400000) {
			if (changeState(state -> Die_Timeout_Logic(blendTime)))
				return;
		}
		doAction(1616075405L /*MOVE_TURN*/, blendTime, onDoActionEnd -> turn(EAITurnMethod.ToParent, 0, () -> {
			return false;
		}, onExit -> changeState(state -> Move_ActionChaseToParent(blendTime))));
	}

	protected void Move_ActionChaseToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x7E78DF25L /*Move_ActionChaseToParent*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 5400000) {
			if (changeState(state -> Die_Timeout_Logic(blendTime)))
				return;
		}
		doAction(3675136411L /*MOVE_ACTIONWALK*/, blendTime, onDoActionEnd -> {
			if (Rnd.get(100) < 30) {
				if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 2000)) {
					if (changeState(state -> Attack_Logic(blendTime)))
						return;
				}
			}
			changeState(state -> Move_TurnToParent(blendTime));
		});
	}

	protected void Move_ChaseToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBCCBD2E0L /*Move_ChaseToParent*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (Rnd.get(100) < 30) {
			if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 2500)) {
				if (changeState(state -> Attack_Logic(blendTime)))
					return;
			}
		}
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 5400000) {
			if (changeState(state -> Die_Timeout_Logic(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_ChaseToParent(blendTime), 1000)));
	}

	protected void Move_ChaseToParent_UnderWater(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB8B4061L /*Move_ChaseToParent_UnderWater*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Move_ChaseToParent(1.3)))
				return;
		}
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 5400000) {
			if (changeState(state -> Die_Timeout_Logic(blendTime)))
				return;
		}
		doAction(1897083925L /*MOVE_WALK_UNDERWATER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_ChaseToParent(blendTime), 1000)));
	}

	protected void Move_ParentPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x838FBB02L /*Move_ParentPath*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 5400000) {
			if (changeState(state -> Die_Timeout_Logic(blendTime)))
				return;
		}
		if(getCallCount() == 30 && Rnd.getChance(10)) {
			if (changeState(state -> Move_ParentPath_UnderWater(1.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPath, 0, 0, 0, 0, 350, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_PathEnd(blendTime), 1000)));
	}

	protected void Move_ParentPath_UnderWater(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFC87FF5AL /*Move_ParentPath_UnderWater*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 5400000) {
			if (changeState(state -> Die_Timeout_Logic(blendTime)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Move_ParentPath_OnWater(1.3)))
				return;
		}
		doAction(1897083925L /*MOVE_WALK_UNDERWATER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OnPath, 0, 0, 0, 0, 350, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_PathEnd(blendTime), 1000)));
	}

	protected void Move_ParentPath_OnWater(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1A559776L /*Move_ParentPath_OnWater*/);
		setVariable(0x64931736L /*_Summon_IngTime*/, getTime());
		setVariable(0x20784437L /*_Summon_EndTime*/, getVariable(0x64931736L /*_Summon_IngTime*/) - getVariable(0x96CB9F57L /*_Summon_StartTime*/));
		if (getVariable(0x20784437L /*_Summon_EndTime*/) >= 5400000) {
			if (changeState(state -> Die_Timeout_Logic(blendTime)))
				return;
		}
		if(getCallCount() == 30 && Rnd.getChance(10)) {
			if (changeState(state -> Move_ParentPath_UnderWater(1.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OnPath, 0, 0, 0, 0, 350, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_PathEnd(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2887951558L /*MOVE_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.OwnerPosition, 0, 0, 1, 1);
		doAction(2887951558L /*MOVE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500));
	}

	protected void Move_ReturnToParent(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1471881CL /*Move_ReturnToParent*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 350, 550, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_ReturnToParent_Complete(blendTime), 10000)));
	}

	protected void Move_ReturnToParent_Complete(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE5801D43L /*Move_ReturnToParent_Complete*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi()._Battle_End(getActor(), null));
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Chase_OnWater(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xE452CC90L /*Chase_OnWater*/);
		if (isTargetLost()) {
			if (changeState(state -> Attack_Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if (changeState(state -> Attack_Normal2(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Chase_OnWater(blendTime), 100)));
	}

	protected void Chase_UnderWater_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF8C02D32L /*Chase_UnderWater_Str*/);
		doAction(860407287L /*MOVE_WALK_UNDERWATER_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Chase_UnderWater(blendTime), 1000));
	}

	protected void Chase_UnderWater(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x5FF57FCDL /*Chase_UnderWater*/);
		if (isTargetLost()) {
			if (changeState(state -> Attack_Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 500) {
			if (changeState(state -> Attack_Normal2(blendTime)))
				return;
		}
		doAction(1897083925L /*MOVE_WALK_UNDERWATER*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Chase_UnderWater(blendTime), 100)));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		setVariable(0x22E0142EL /*_TakeDamageCount*/, 0);
		doAction(1616805723L /*ATTACK_NORMAL1*/, blendTime, onDoActionEnd -> changeState(state -> Move_TurnToParent(blendTime)));
	}

	protected void Attack_Normal2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6031669BL /*Attack_Normal2*/);
		setVariable(0x22E0142EL /*_TakeDamageCount*/, 0);
		doAction(2463627859L /*ATTACK_NORMAL2*/, blendTime, onDoActionEnd -> changeState(state -> Move_TurnToParent(blendTime)));
	}

	protected void Attack_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x50B0953FL /*Attack_Logic*/);
		if (target != null && getDistanceToTarget(target) < 1000) {
			if(Rnd.getChance(50)) {
				if (changeState(state -> Attack_Normal(blendTime)))
					return;
			}
		}
		if (target != null && getDistanceToTarget(target) < 1000) {
			if (changeState(state -> Attack_Normal2(blendTime)))
				return;
		}
		changeState(state -> Move_TurnToParent(blendTime));
	}

	protected void Battle_End_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1BCC6E41L /*Battle_End_Logic*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi()._Battle_End(getActor(), null));
		changeState(state -> Move_ChaseToParent_UnderWater(blendTime));
	}

	protected void Die_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x277B023DL /*Die_Ready*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi()._Deadstate(getActor(), null));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die_Ready(blendTime), 1000));
	}

	protected void Die_Str_Bal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x16F4D328L /*Die_Str_Bal*/);
		worldNotify(EChatNoticeType.HuntingEnd, "GAME", "LUA_HUNTINGEND_BAL_WHALE");
		doAction(4053489827L /*DIE_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Die_Wait(blendTime), 1000));
	}

	protected void Die_Str_Cal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2B4ADFB9L /*Die_Str_Cal*/);
		worldNotify(EChatNoticeType.HuntingEnd, "GAME", "LUA_HUNTINGEND_CAL_WHALE");
		doAction(4053489827L /*DIE_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Die_Wait(blendTime), 1000));
	}

	protected void Die_Str_Med(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x203B7544L /*Die_Str_Med*/);
		worldNotify(EChatNoticeType.HuntingEnd, "GAME", "LUA_HUNTINGEND_MED_WHALE");
		doAction(4053489827L /*DIE_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Die_Wait(blendTime), 1000));
	}

	protected void Die_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DC3CFB8L /*Die_Wait*/);
		if(getCallCount() == 480) {
			if (changeState(state -> Die_End(0.3)))
				return;
		}
		doAction(425277756L /*DIE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die_Wait(blendTime), 1000));
	}

	protected void Die_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8B54EEF5L /*Die_End*/);
		setMonsterCollect(false);
		doAction(2337863545L /*DIE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Die_Timeout_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC0B63BFL /*Die_Timeout_Logic*/);
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 0) {
			if (changeState(state -> Die_Timeout_Bal(0.3)))
				return;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 1) {
			if (changeState(state -> Die_Timeout_Cal(0.3)))
				return;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 2) {
			if (changeState(state -> Die_Timeout_Med(0.3)))
				return;
		}
		changeState(state -> Die_Timeout_Logic(blendTime));
	}

	protected void Die_Timeout_Bal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x38096507L /*Die_Timeout_Bal*/);
		doAction(3912161941L /*DIE_TIMEOUT*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi()._TimeOut(getActor(), null));
			worldNotify(EChatNoticeType.HuntingEnd, "GAME", "LUA_HUNTINGTIMEOUT_BAL_WHALE");
			scheduleState(state -> Die(blendTime), 4000);
		});
	}

	protected void Die_Timeout_Cal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC115E264L /*Die_Timeout_Cal*/);
		doAction(3912161941L /*DIE_TIMEOUT*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi()._TimeOut(getActor(), null));
			worldNotify(EChatNoticeType.HuntingEnd, "GAME", "LUA_HUNTINGTIMEOUT_CAL_WHALE");
			scheduleState(state -> Die(blendTime), 4000);
		});
	}

	protected void Die_Timeout_Med(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8C43EADL /*Die_Timeout_Med*/);
		doAction(3912161941L /*DIE_TIMEOUT*/, blendTime, onDoActionEnd -> {
			getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi()._TimeOut(getActor(), null));
			worldNotify(EChatNoticeType.HuntingEnd, "GAME", "LUA_HUNTINGTIMEOUT_MED_WHALE");
			scheduleState(state -> Die(blendTime), 4000);
		});
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x71F28994L /*Battle_Wait*/);
		changeState(state -> Chase_OnWater(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x22E0142EL /*_TakeDamageCount*/, getVariable(0x22E0142EL /*_TakeDamageCount*/) + 1);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die_Ready(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFollowMeOwnerPath(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0xA730A939L /*Attack_Normal*/ || getState() == 0x6031669BL /*Attack_Normal2*/ || getState() == 0x8377635AL /*Move_Random*/ || getState() == 0x866C7489L /*Wait*/)) {
			if (changeState(state -> Move_TurnToParent(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyInvited(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 0) {
			if (changeState(state -> Die_Str_Bal(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 1) {
			if (changeState(state -> Die_Str_Cal(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 2) {
			if (changeState(state -> Die_Str_Med(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
