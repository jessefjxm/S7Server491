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
@IAIName("party_leader_whaleghost")
public class Ai_party_leader_whaleghost extends CreatureAI {
	public Ai_party_leader_whaleghost(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		setVariableArray(0x2C4960E5L /*_WaypointValue1*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x3DFFB456L /*AI_WayPointKey1*/) });
		setVariableArray(0x11AADB03L /*_WaypointValue2*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xCD7084F0L /*AI_WayPointKey2*/) });
		setVariableArray(0x9425998CL /*_WaypointValue3*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x73DB50B5L /*AI_WayPointKey3*/) });
		setVariableArray(0x58ADA3CL /*_WaypointValue4*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x8385DA8EL /*AI_WayPointKey4*/) });
		setVariableArray(0x9FFA920EL /*_WaypointValue5*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xF9FBC5A4L /*AI_WayPointKey5*/) });
		setVariableArray(0x95688E57L /*_WaypointValue6*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xD1342C91L /*AI_WayPointKey6*/) });
		setVariableArray(0x62D7BE48L /*_WaypointValue7*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x488AA4CFL /*AI_WayPointKey7*/) });
		setVariableArray(0x5C40916BL /*_WaypointValue8*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0x180FE931L /*AI_WayPointKey8*/) });
		setVariableArray(0x61317658L /*_WaypointValue9*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xD1B3C6F5L /*AI_WayPointKey9*/) });
		setVariableArray(0x178B3B3BL /*_WaypointValue10*/, new Integer[] { getVariable(0xC687E0D9L /*AI_WayPointType*/), getVariable(0xB0F128A8L /*AI_WayPointKey10*/) });
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_SummonPoint_Logic(blendTime), 1000));
	}

	protected void Move_SummonPoint_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCADF4E27L /*Move_SummonPoint_Logic*/);
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 1) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_SummonPoint1(blendTime)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 2) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_SummonPoint2(blendTime)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 3) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_SummonPoint3(blendTime)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 4) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_SummonPoint4(blendTime)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 5) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_SummonPoint5(blendTime)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 6) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_SummonPoint6(blendTime)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 7) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_SummonPoint7(blendTime)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 8) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_SummonPoint8(blendTime)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 9) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_SummonPoint9(blendTime)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 10) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_SummonPoint10(blendTime)))
					return;
			}
		}
		changeState(state -> Move_SummonPoint_Logic(blendTime));
	}

	protected void Move_SummonPoint1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x24ECC0D2L /*Move_SummonPoint1*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Wait(blendTime), 1000)));
	}

	protected void Move_SummonPoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEE91C08CL /*Move_SummonPoint2*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Wait(blendTime), 1000)));
	}

	protected void Move_SummonPoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x17833C37L /*Move_SummonPoint3*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Wait(blendTime), 1000)));
	}

	protected void Move_SummonPoint4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x675C15BCL /*Move_SummonPoint4*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x58ADA3CL /*_WaypointValue4*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Wait(blendTime), 1000)));
	}

	protected void Move_SummonPoint5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x56BF59CFL /*Move_SummonPoint5*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9FFA920EL /*_WaypointValue5*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Wait(blendTime), 1000)));
	}

	protected void Move_SummonPoint6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x42E6BF64L /*Move_SummonPoint6*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x95688E57L /*_WaypointValue6*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Wait(blendTime), 1000)));
	}

	protected void Move_SummonPoint7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF5B4C9D7L /*Move_SummonPoint7*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x62D7BE48L /*_WaypointValue7*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Wait(blendTime), 1000)));
	}

	protected void Move_SummonPoint8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB5948F71L /*Move_SummonPoint8*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x5C40916BL /*_WaypointValue8*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Wait(blendTime), 1000)));
	}

	protected void Move_SummonPoint9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9E224AE6L /*Move_SummonPoint9*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x61317658L /*_WaypointValue9*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Wait(blendTime), 1000)));
	}

	protected void Move_SummonPoint10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF2792701L /*Move_SummonPoint10*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x178B3B3BL /*_WaypointValue10*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Summon_Wait(blendTime), 1000)));
	}

	protected void Summon_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE4A6F0FL /*Summon_Wait*/);
		doAction(3602854557L /*SUMMON_WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Loop_Logic(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getPartyMembersCount()== 0) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Loop_Logic(blendTime), 1000));
	}

	protected void Loop_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFE94E551L /*Loop_Wait*/);
		if (getPartyMembersCount()== 0) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if(getCallCount() == 30) {
			if (changeState(state -> Loop_Logic(blendTime)))
				return;
		}
		if (getDistanceToChild() < 2000) {
			if (changeState(state -> Loop_Logic(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Loop_Wait(blendTime), 500));
	}

	protected void Loop_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x72F3DC2EL /*Loop_Logic*/);
		if (getPartyMembersCount()== 0) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 1 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 1) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> MovePoint1(blendTime)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 2 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 2) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> MovePoint2(blendTime)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 3 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 3) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> MovePoint3(blendTime)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 4 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 4) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> MovePoint4(blendTime)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 5 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 5) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> MovePoint5(blendTime)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 6 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 5) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> MovePoint6(blendTime)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 7 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 5) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> MovePoint7(blendTime)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 8 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 5) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> MovePoint8(blendTime)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 9 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 5) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> MovePoint9(blendTime)))
					return;
			}
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) != 10 && getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) >= 5) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> MovePoint10(blendTime)))
					return;
			}
		}
		changeState(state -> Wait(blendTime));
	}

	protected void AttEnd_Loop_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x352BE1ECL /*AttEnd_Loop_Logic*/);
		if (getPartyMembersCount()== 0) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 1) {
			if (changeState(state -> MovePoint1(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 2) {
			if (changeState(state -> MovePoint2(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 3) {
			if (changeState(state -> MovePoint3(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 4) {
			if (changeState(state -> MovePoint4(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 5) {
			if (changeState(state -> MovePoint5(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 6) {
			if (changeState(state -> MovePoint6(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 7) {
			if (changeState(state -> MovePoint7(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 8) {
			if (changeState(state -> MovePoint8(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 9) {
			if (changeState(state -> MovePoint9(blendTime)))
				return;
		}
		if (getVariable(0xD1A2EF4FL /*_WayPointCount*/) == 10) {
			if (changeState(state -> MovePoint10(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void MovePoint1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBF7F839AL /*MovePoint1*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
		if (getDistanceToChild() > 4000) {
			if (changeState(state -> Wait_Child(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Wait(blendTime), 1000)));
	}

	protected void MovePoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE8D86D5BL /*MovePoint2*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
		if (getDistanceToChild() > 4000) {
			if (changeState(state -> Wait_Child(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Wait(blendTime), 1000)));
	}

	protected void MovePoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBB5DA63EL /*MovePoint3*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 3);
		if (getDistanceToChild() > 4000) {
			if (changeState(state -> Wait_Child(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Wait(blendTime), 1000)));
	}

	protected void MovePoint4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC4B7E7FAL /*MovePoint4*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 4);
		if (getDistanceToChild() > 4000) {
			if (changeState(state -> Wait_Child(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x58ADA3CL /*_WaypointValue4*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Wait(blendTime), 1000)));
	}

	protected void MovePoint5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6F460A97L /*MovePoint5*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 5);
		if (getDistanceToChild() > 4000) {
			if (changeState(state -> Wait_Child(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9FFA920EL /*_WaypointValue5*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Wait(blendTime), 1000)));
	}

	protected void MovePoint6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDF964916L /*MovePoint6*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 6);
		if (getDistanceToChild() > 4000) {
			if (changeState(state -> Wait_Child(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x95688E57L /*_WaypointValue6*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Wait(blendTime), 1000)));
	}

	protected void MovePoint7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2CE9704L /*MovePoint7*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 7);
		if (getDistanceToChild() > 4000) {
			if (changeState(state -> Wait_Child(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x62D7BE48L /*_WaypointValue7*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Wait(blendTime), 1000)));
	}

	protected void MovePoint8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xAEE178D2L /*MovePoint8*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 8);
		if (getDistanceToChild() > 4000) {
			if (changeState(state -> Wait_Child(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x5C40916BL /*_WaypointValue8*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Wait(blendTime), 1000)));
	}

	protected void MovePoint9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA0239A14L /*MovePoint9*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 9);
		if (getDistanceToChild() > 4000) {
			if (changeState(state -> Wait_Child(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x61317658L /*_WaypointValue9*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Wait(blendTime), 1000)));
	}

	protected void MovePoint10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x860AAC8BL /*MovePoint10*/);
		getObjects(EAIFindTargetType.Child, object -> getDistanceToTarget(object) < 4000).forEach(consumer -> consumer.getAi().HandleFollowMeOwnerPath(getActor(), null));
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 10);
		if (getDistanceToChild() > 4000) {
			if (changeState(state -> Wait_Child(blendTime)))
				return;
		}
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x178B3B3BL /*_WaypointValue10*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Loop_Wait(blendTime), 1000)));
	}

	protected void Wait_Child(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DB702CFL /*Wait_Child*/);
		if (getPartyMembersCount()== 0) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if (getDistanceToChild() < 2000) {
			if (changeState(state -> AttEnd_Loop_Logic(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Child(blendTime), 1000));
	}

	protected void Battle_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x71F28994L /*Battle_Wait*/);
		if (getPartyMembersCount()== 0) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 1000));
	}

	protected void Battle_End_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA68A63E5L /*Battle_End_Wait*/);
		if (getPartyMembersCount()== 0) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if(getCallCount() == 5) {
			if (changeState(state -> Loop_Logic(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_End_Wait(blendTime), 1000));
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
	public EAiHandlerResult HandlePartyCheck(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _Battle_Str(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Battle_Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _Battle_End(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> AttEnd_Loop_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _PathEnd(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getState() == 0xFE94E551L /*Loop_Wait*/) {
			if (changeState(state -> Loop_Logic(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _TimeOut(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult _Deadstate(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	protected void Order_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x89C2F090L /*Order_Wait*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleBattleMode(getActor(), null));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Battle_Wait(blendTime), 500));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(633942026L /*MOVE_WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1500));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 10000));
	}
}
