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
@IAIName("easterduck_event")
public class Ai_easterduck_event extends CreatureAI {
	public Ai_easterduck_event(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xCB78182DL /*_TeleportNo*/, 0);
		setVariable(0x8C7C8B49L /*_MoveSelect*/, 0);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> TeleportLogic(blendTime)));
	}

	protected void TeleportLogic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1CCFA4F7L /*TeleportLogic*/);
		if(Rnd.getChance(50)) {
			if (changeState(state -> Group_01(0.3)))
				return;
		}
		changeState(state -> TeleportLogic(blendTime));
	}

	protected void Group_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB1D820BFL /*Group_01*/);
		setVariable(0xCB78182DL /*_TeleportNo*/, 1);
		doTeleportToWaypoint("flondor_goose_01", "monster_patrol", 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath(0.3)))
					return;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return;
			}
			scheduleState(state -> Wait_Egg(blendTime), 3000);
		});
	}

	protected void Wait_Egg(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA456594AL /*Wait_Egg*/);
		if (findTarget(EAIFindTargetType.Npc, EAIFindType.normal, false, object -> getTargetCharacterKey(object) == 37127 && getTargetHp(object) > 0 && getDistanceToTarget(object) < 1000 && isCreatureVisible(object, false))) {
			if (changeState(state -> Move_Logic(0.3)))
				return;
		}
		if(Rnd.getChance(50)) {
			if (changeState(state -> Summon_Egg(0.3)))
				return;
		}
		if(Rnd.getChance(50)) {
			if (changeState(state -> Move_Logic(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Egg(blendTime), 3000));
	}

	protected void Summon_Egg(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x2D304231L /*Summon_Egg*/);
		doAction(1668289803L /*SUMMON_EGG*/, blendTime, onDoActionEnd -> changeState(state -> Move_Logic(blendTime)));
	}

	protected void Move_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDA342E1BL /*Move_Logic*/);
		setVariable(0x8C7C8B49L /*_MoveSelect*/, getRandom());
		if (getVariable(0x8C7C8B49L /*_MoveSelect*/) <= 10 && getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 1) {
			if (changeState(state -> Move_Dest_Walk_01(0.3)))
				return;
		}
		if (getVariable(0x8C7C8B49L /*_MoveSelect*/) > 10 && getVariable(0x8C7C8B49L /*_MoveSelect*/) <= 20 && getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 2) {
			if (changeState(state -> Move_Dest_Walk_02(0.3)))
				return;
		}
		if (getVariable(0x8C7C8B49L /*_MoveSelect*/) > 20 && getVariable(0x8C7C8B49L /*_MoveSelect*/) <= 30 && getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 3) {
			if (changeState(state -> Move_Dest_Walk_03(0.3)))
				return;
		}
		if (getVariable(0x8C7C8B49L /*_MoveSelect*/) > 30 && getVariable(0x8C7C8B49L /*_MoveSelect*/) <= 40 && getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 4) {
			if (changeState(state -> Move_Dest_Walk_04(0.3)))
				return;
		}
		if (getVariable(0x8C7C8B49L /*_MoveSelect*/) > 40 && getVariable(0x8C7C8B49L /*_MoveSelect*/) <= 50 && getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 5) {
			if (changeState(state -> Move_Dest_Walk_05(0.3)))
				return;
		}
		if (getVariable(0x8C7C8B49L /*_MoveSelect*/) > 50 && getVariable(0x8C7C8B49L /*_MoveSelect*/) <= 60 && getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 6) {
			if (changeState(state -> Move_Dest_Run_01(0.3)))
				return;
		}
		if (getVariable(0x8C7C8B49L /*_MoveSelect*/) > 60 && getVariable(0x8C7C8B49L /*_MoveSelect*/) <= 70 && getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 7) {
			if (changeState(state -> Move_Dest_Run_02(0.3)))
				return;
		}
		if (getVariable(0x8C7C8B49L /*_MoveSelect*/) > 70 && getVariable(0x8C7C8B49L /*_MoveSelect*/) <= 80 && getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 8) {
			if (changeState(state -> Move_Dest_Run_03(0.3)))
				return;
		}
		if (getVariable(0x8C7C8B49L /*_MoveSelect*/) > 80 && getVariable(0x8C7C8B49L /*_MoveSelect*/) <= 90 && getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 9) {
			if (changeState(state -> Move_Dest_Run_04(0.3)))
				return;
		}
		if (getVariable(0x8C7C8B49L /*_MoveSelect*/) > 90 && getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 10) {
			if (changeState(state -> Move_Dest_Run_05(0.3)))
				return;
		}
		changeState(state -> Move_Logic(blendTime));
	}

	protected void Move_Dest_Walk_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x878DF1C2L /*Move_Dest_Walk_01*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "flondor_goose_38", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Dest_Walk_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE8F74B04L /*Move_Dest_Walk_02*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "flondor_goose_08", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Dest_Walk_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7C3F19EEL /*Move_Dest_Walk_03*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 3);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "flondor_goose_35", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Dest_Walk_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x4AF91912L /*Move_Dest_Walk_04*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 4);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "flondor_goose_30", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Dest_Walk_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCA0B6996L /*Move_Dest_Walk_05*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 5);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "flondor_goose_27", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Dest_Run_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7DDAEE8CL /*Move_Dest_Run_01*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 6);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(1022931502L /*MOVE_RUN*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "flondor_goose_20", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Dest_Run_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x391ACA83L /*Move_Dest_Run_02*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 7);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(1022931502L /*MOVE_RUN*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "flondor_goose_07", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Dest_Run_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB8F13D3BL /*Move_Dest_Run_03*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 8);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(1022931502L /*MOVE_RUN*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "flondor_goose_34", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Dest_Run_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8DA91FFFL /*Move_Dest_Run_04*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 9);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(1022931502L /*MOVE_RUN*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "flondor_goose_16", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Move_Dest_Run_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x261B3354L /*Move_Dest_Run_05*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 10);
		if(Rnd.getChance(5)) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(1022931502L /*MOVE_RUN*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "flondor_goose_28", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 5000));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 20000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(1562868502L /*MOVE_RETURN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
