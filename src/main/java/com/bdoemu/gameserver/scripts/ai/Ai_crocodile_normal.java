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
@IAIName("crocodile_normal")
public class Ai_crocodile_normal extends CreatureAI {
	public Ai_crocodile_normal(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xCBEEF8C7L /*_OwnerDistance*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x940229E0L /*_isState*/, 0);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		setVariableArray(0x2C4960E5L /*_WaypointValue1*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x3DFFB456L /*AI_WayPointKey1*/) });
		setVariableArray(0x11AADB03L /*_WaypointValue2*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xCD7084F0L /*AI_WayPointKey2*/) });
		setVariableArray(0x9425998CL /*_WaypointValue3*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x73DB50B5L /*AI_WayPointKey3*/) });
		setVariableArray(0x58ADA3CL /*_WaypointValue4*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x8385DA8EL /*AI_WayPointKey4*/) });
		setVariableArray(0x9FFA920EL /*_WaypointValue5*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xF9FBC5A4L /*AI_WayPointKey5*/) });
		doAction(1783340441L /*WAIT_UNDERWATER*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_UnderWater(blendTime), 100));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Loop_Logic(blendTime), 500));
	}

	protected void Wait_UnderWater(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x693F7AB5L /*Wait_UnderWater*/);
		doAction(1783340441L /*WAIT_UNDERWATER*/, blendTime, onDoActionEnd -> scheduleState(state -> Loop_Logic_UnderWater(blendTime), 500));
	}

	protected void Loop_Logic_UnderWater(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBB0A74EAL /*Loop_Logic_UnderWater*/);
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 0) {
			if(Rnd.getChance(30)) {
				if (changeState(state -> Loop_Logic(blendTime)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 0 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 1) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> MovePoint_UnderWater(0.3)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 1 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 2) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> MovePoint2_UnderWater(0.3)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 2 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 3) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> MovePoint3_UnderWater(0.3)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 3 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 4) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> MovePoint4_UnderWater(0.3)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 4 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 5) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> MovePoint5_UnderWater(0.3)))
					return;
			}
		}
		changeState(state -> Loop_Logic(blendTime));
	}

	protected void MovePoint_UnderWater(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6C8C61AAL /*MovePoint_UnderWater*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
		doAction(1897083925L /*MOVE_WALK_UNDERWATER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 1) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic_UnderWater(blendTime), 1000)));
	}

	protected void MovePoint2_UnderWater(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDC30097L /*MovePoint2_UnderWater*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
		doAction(1897083925L /*MOVE_WALK_UNDERWATER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 2) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic_UnderWater(blendTime), 1000)));
	}

	protected void MovePoint3_UnderWater(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBD4D1951L /*MovePoint3_UnderWater*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 3);
		doAction(1897083925L /*MOVE_WALK_UNDERWATER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 3) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic_UnderWater(blendTime), 1000)));
	}

	protected void MovePoint4_UnderWater(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBC2534AL /*MovePoint4_UnderWater*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 4);
		doAction(1897083925L /*MOVE_WALK_UNDERWATER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x58ADA3CL /*_WaypointValue4*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 4) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic_UnderWater(blendTime), 1000)));
	}

	protected void MovePoint5_UnderWater(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x28EAAD7DL /*MovePoint5_UnderWater*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		doAction(1897083925L /*MOVE_WALK_UNDERWATER*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9FFA920EL /*_WaypointValue5*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic_UnderWater(blendTime), 1000)));
	}

	protected void Loop_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x72F3DC2EL /*Loop_Logic*/);
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 0 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 1) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> MovePoint(0.3)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 1 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 2) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> MovePoint2(0.3)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 2 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 3) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> MovePoint3(0.3)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 3 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 4) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> MovePoint4(0.3)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 4 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 0) {
			if(Rnd.getChance(25)) {
				if (changeState(state -> MovePoint5(0.3)))
					return;
			}
		}
		changeState(state -> Loop_Logic(blendTime));
	}

	protected void MovePoint(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE03B724EL /*MovePoint*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 1) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic(blendTime), 1000)));
	}

	protected void MovePoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE8D86D5BL /*MovePoint2*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 2) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic(blendTime), 1000)));
	}

	protected void MovePoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBB5DA63EL /*MovePoint3*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 3);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 3) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic(blendTime), 1000)));
	}

	protected void MovePoint4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC4B7E7FAL /*MovePoint4*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 4);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x58ADA3CL /*_WaypointValue4*/), ENaviType.ground, () -> {
			if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 4) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic(blendTime), 1000)));
	}

	protected void MovePoint5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6F460A97L /*MovePoint5*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9FFA920EL /*_WaypointValue5*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Logic(blendTime), 1000)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500));
	}

	protected void Die_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x277B023DL /*Die_Ready*/);
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi()._Deadstate(getActor(), null));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die_Ready(blendTime), 1000));
	}

	protected void Die_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8DEF4394L /*Die_Str*/);
		setMonsterCollect(true);
		doAction(4053489827L /*DIE_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Die_Wait(blendTime), 1000));
	}

	protected void Die_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DC3CFB8L /*Die_Wait*/);
		if(getCallCount() == 240) {
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

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die_Str(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		doAction(2820327238L /*BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}
}
