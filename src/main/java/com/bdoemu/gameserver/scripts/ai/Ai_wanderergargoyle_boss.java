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
@IAIName("wanderergargoyle_boss")
public class Ai_wanderergargoyle_boss extends CreatureAI {
	public Ai_wanderergargoyle_boss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0x9AA4CDE2L /*_RegionNo*/, getVariable(0xD6D9F312L /*AI_RegionNo*/));
		setVariable(0x2594941FL /*_isair*/, 0);
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
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 2) {
			if (changeState(state -> Start_Message_Bal(0.3)))
				return;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 0) {
			if (changeState(state -> Start_Message_Cal(0.3)))
				return;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 1) {
			if (changeState(state -> Start_Message_Med(0.3)))
				return;
		}
		doAction(973257915L /*FLYING_BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Start_Message_Bal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x355BCCDBL /*Start_Message_Bal*/);
		worldNotify(EChatNoticeType.Hunting, "GAME", "LUA_HUNTING_BAL_GARGOYLE");
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Summon(blendTime), 100));
	}

	protected void Start_Message_Cal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD8456834L /*Start_Message_Cal*/);
		worldNotify(EChatNoticeType.Hunting, "GAME", "LUA_HUNTING_CAL_GARGOYLE");
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Summon(blendTime), 100));
	}

	protected void Start_Message_Med(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7AD14352L /*Start_Message_Med*/);
		worldNotify(EChatNoticeType.Hunting, "GAME", "LUA_HUNTING_MED_GARGOYLE");
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Summon(blendTime), 100));
	}

	protected void Start_Summon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x81FE1707L /*Start_Summon*/);
		doAction(948972050L /*START_SUMMON*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(973257915L /*FLYING_BATTLE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> WayPoint_Logic(blendTime), 500));
	}

	protected void WayPoint_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7D3E3744L /*WayPoint_Logic*/);
		if (changeState(state -> Move_Cal_WayPoint1(0.3)))
			return;
		scheduleState(state -> WayPoint_Logic(blendTime), 500);
	}

	protected void Move_Cal_WayPoint1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3C7F54L /*Move_Cal_WayPoint1*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
		if (target != null && getDistanceToTarget(target) > 2000) {
			if(getCallCount() == 30 && Rnd.getChance(10)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		doAction(4216462350L /*FLYING_BATTLE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Cal_WayPoint1_Wait(blendTime), 1000)));
	}

	protected void Move_Cal_WayPoint1_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x628C2BEL /*Move_Cal_WayPoint1_Wait*/);
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) > 1) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint2(0.3)))
					return;
			}
		}
		doAction(3792584763L /*WAIT_TREECLING_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Cal_WayPoint1_Wait(blendTime), 1000));
	}

	protected void Move_Cal_WayPoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEFD6DB72L /*Move_Cal_WayPoint2*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
		if (target != null && getDistanceToTarget(target) > 2000) {
			if(getCallCount() == 30 && Rnd.getChance(10)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		doAction(4216462350L /*FLYING_BATTLE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Cal_WayPoint2_Wait(blendTime), 1000)));
	}

	protected void Move_Cal_WayPoint2_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1FDFE6BAL /*Move_Cal_WayPoint2_Wait*/);
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 2) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint1(0.3)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) > 2) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint3(0.3)))
					return;
			}
		}
		doAction(3792584763L /*WAIT_TREECLING_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Cal_WayPoint2_Wait(blendTime), 1000));
	}

	protected void Move_Cal_WayPoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xAF5685E4L /*Move_Cal_WayPoint3*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 3);
		if (target != null && getDistanceToTarget(target) > 2000) {
			if(getCallCount() == 30 && Rnd.getChance(10)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		doAction(4216462350L /*FLYING_BATTLE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Cal_WayPoint3_Wait(blendTime), 1000)));
	}

	protected void Move_Cal_WayPoint3_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5CED22A3L /*Move_Cal_WayPoint3_Wait*/);
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 3) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint1(0.3)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) > 3) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint4(0.3)))
					return;
			}
		}
		doAction(3792584763L /*WAIT_TREECLING_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Cal_WayPoint3_Wait(blendTime), 1000));
	}

	protected void Move_Cal_WayPoint4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9B7066C2L /*Move_Cal_WayPoint4*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 4);
		if (target != null && getDistanceToTarget(target) > 2000) {
			if(getCallCount() == 30 && Rnd.getChance(10)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		doAction(4216462350L /*FLYING_BATTLE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x58ADA3CL /*_WaypointValue4*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Cal_WayPoint4_Wait(blendTime), 1000)));
	}

	protected void Move_Cal_WayPoint4_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5B796B47L /*Move_Cal_WayPoint4_Wait*/);
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 4) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint1(0.3)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) > 4) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint5(0.3)))
					return;
			}
		}
		doAction(3792584763L /*WAIT_TREECLING_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Cal_WayPoint4_Wait(blendTime), 1000));
	}

	protected void Move_Cal_WayPoint5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2BCDFC02L /*Move_Cal_WayPoint5*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 5);
		if (target != null && getDistanceToTarget(target) > 2000) {
			if(getCallCount() == 30 && Rnd.getChance(10)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		doAction(4216462350L /*FLYING_BATTLE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9FFA920EL /*_WaypointValue5*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Cal_WayPoint5_Wait(blendTime), 1000)));
	}

	protected void Move_Cal_WayPoint5_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x51A0D5D9L /*Move_Cal_WayPoint5_Wait*/);
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 5) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint1(0.3)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) > 5) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint6(0.3)))
					return;
			}
		}
		doAction(3792584763L /*WAIT_TREECLING_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Cal_WayPoint5_Wait(blendTime), 1000));
	}

	protected void Move_Cal_WayPoint6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x492405C2L /*Move_Cal_WayPoint6*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 6);
		if (target != null && getDistanceToTarget(target) > 2000) {
			if(getCallCount() == 30 && Rnd.getChance(10)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		doAction(4216462350L /*FLYING_BATTLE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x95688E57L /*_WaypointValue6*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Cal_WayPoint6_Wait(blendTime), 1000)));
	}

	protected void Move_Cal_WayPoint6_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8F56C998L /*Move_Cal_WayPoint6_Wait*/);
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 6) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint1(0.3)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) > 6) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint7(0.3)))
					return;
			}
		}
		doAction(3792584763L /*WAIT_TREECLING_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Cal_WayPoint6_Wait(blendTime), 1000));
	}

	protected void Move_Cal_WayPoint7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9BEF8C10L /*Move_Cal_WayPoint7*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 7);
		if (target != null && getDistanceToTarget(target) > 2000) {
			if(getCallCount() == 30 && Rnd.getChance(10)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		doAction(4216462350L /*FLYING_BATTLE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x62D7BE48L /*_WaypointValue7*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Cal_WayPoint7_Wait(blendTime), 1000)));
	}

	protected void Move_Cal_WayPoint7_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3EA62C89L /*Move_Cal_WayPoint7_Wait*/);
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 7) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint1(0.3)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) > 7) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint8(0.3)))
					return;
			}
		}
		doAction(3792584763L /*WAIT_TREECLING_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Cal_WayPoint7_Wait(blendTime), 1000));
	}

	protected void Move_Cal_WayPoint8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2F6A9EFFL /*Move_Cal_WayPoint8*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 8);
		if (target != null && getDistanceToTarget(target) > 2000) {
			if(getCallCount() == 30 && Rnd.getChance(10)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		doAction(4216462350L /*FLYING_BATTLE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x5C40916BL /*_WaypointValue8*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Cal_WayPoint8_Wait(blendTime), 1000)));
	}

	protected void Move_Cal_WayPoint8_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5E328765L /*Move_Cal_WayPoint8_Wait*/);
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 8) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint1(0.3)))
					return;
			}
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) > 8) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint9(0.3)))
					return;
			}
		}
		doAction(3792584763L /*WAIT_TREECLING_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Cal_WayPoint8_Wait(blendTime), 1000));
	}

	protected void Move_Cal_WayPoint9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF85869C4L /*Move_Cal_WayPoint9*/);
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 9);
		if (target != null && getDistanceToTarget(target) > 2000) {
			if(getCallCount() == 30 && Rnd.getChance(10)) {
				if (changeState(state -> Attack_Normal(0.3)))
					return;
			}
		}
		doAction(4216462350L /*FLYING_BATTLE_WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x61317658L /*_WaypointValue9*/), ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Move_Cal_WayPoint9_Wait(blendTime), 1000)));
	}

	protected void Move_Cal_WayPoint9_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x749C36FAL /*Move_Cal_WayPoint9_Wait*/);
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 9) {
			if(getCallCount() == 5 && Rnd.getChance(40)) {
				if (changeState(state -> Move_Cal_WayPoint1(0.3)))
					return;
			}
		}
		doAction(3792584763L /*WAIT_TREECLING_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Cal_WayPoint9_Wait(blendTime), 1000));
	}

	protected void Move_Cal_WayPoint_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2923FE12L /*Move_Cal_WayPoint_Logic*/);
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 1) {
			if (changeState(state -> Move_Cal_WayPoint1(blendTime)))
				return;
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 2) {
			if (changeState(state -> Move_Cal_WayPoint2(blendTime)))
				return;
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 3) {
			if (changeState(state -> Move_Cal_WayPoint3(blendTime)))
				return;
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 4) {
			if (changeState(state -> Move_Cal_WayPoint4(blendTime)))
				return;
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 5) {
			if (changeState(state -> Move_Cal_WayPoint5(blendTime)))
				return;
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 6) {
			if (changeState(state -> Move_Cal_WayPoint6(blendTime)))
				return;
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 7) {
			if (changeState(state -> Move_Cal_WayPoint7(blendTime)))
				return;
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 8) {
			if (changeState(state -> Move_Cal_WayPoint8(blendTime)))
				return;
		}
		if (getVariable(0xABA96D52L /*AI_WayPointLoopCount*/) == 9) {
			if (changeState(state -> Move_Cal_WayPoint9(blendTime)))
				return;
		}
		changeState(state -> Move_Cal_WayPoint_Logic(blendTime));
	}

	protected void Die_Str_Cal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2B4ADFB9L /*Die_Str_Cal*/);
		doAction(425277756L /*DIE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die_Wait(blendTime), 1000));
	}

	protected void Die_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DC3CFB8L /*Die_Wait*/);
		if(getCallCount() == 300) {
			if (changeState(state -> Die_End(0.3)))
				return;
		}
		doAction(2533048970L /*DIE_WAIT_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Die_Wait(blendTime), 1000));
	}

	protected void Die_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8B54EEF5L /*Die_End*/);
		setMonsterCollect(false);
		doAction(2533048970L /*DIE_WAIT_ING*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void Fall_TreeClingOff_Bal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.revive);
		setState(0x120B5B26L /*Fall_TreeClingOff_Bal*/);
		worldNotify(EChatNoticeType.HuntingEnd, "GAME", "LUA_HUNTINGEND_BAL_GARGOYLE");
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi()._GargoyleDie(getActor(), null));
		doAction(3792584763L /*WAIT_TREECLING_STR*/, blendTime, onDoActionEnd -> revive(1, () -> {
			return false;
		}, onExit -> scheduleState(state -> Fall_Str(blendTime), 500)));
	}

	protected void Fall_TreeClingOff_Cal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.revive);
		setState(0xF7FDEA97L /*Fall_TreeClingOff_Cal*/);
		worldNotify(EChatNoticeType.HuntingEnd, "GAME", "LUA_HUNTINGEND_CAL_GARGOYLE");
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi()._GargoyleDie(getActor(), null));
		doAction(3792584763L /*WAIT_TREECLING_STR*/, blendTime, onDoActionEnd -> revive(1, () -> {
			return false;
		}, onExit -> scheduleState(state -> Fall_Str(blendTime), 500)));
	}

	protected void Fall_TreeClingOff_Med(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.revive);
		setState(0x9B9E817DL /*Fall_TreeClingOff_Med*/);
		worldNotify(EChatNoticeType.HuntingEnd, "GAME", "LUA_HUNTINGEND_MED_GARGOYLE");
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi()._GargoyleDie(getActor(), null));
		doAction(3792584763L /*WAIT_TREECLING_STR*/, blendTime, onDoActionEnd -> revive(1, () -> {
			return false;
		}, onExit -> scheduleState(state -> Fall_Str(blendTime), 500)));
	}

	protected void Fall_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xDA8975A9L /*Fall_Str*/);
		doAction(3604366830L /*FALLING_KNOCKDOWN*/, blendTime, onDoActionEnd -> changeState(state -> Fall(blendTime)));
	}

	protected void Fall_Wait_Bal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.revive);
		setState(0xDD91E9ECL /*Fall_Wait_Bal*/);
		worldNotify(EChatNoticeType.HuntingEnd, "GAME", "LUA_HUNTINGEND_BAL_GARGOYLE");
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi()._GargoyleDie(getActor(), null));
		doAction(973257915L /*FLYING_BATTLE_WAIT*/, blendTime, onDoActionEnd -> revive(1, () -> {
			return false;
		}, onExit -> scheduleState(state -> Fall(blendTime), 1000)));
	}

	protected void Fall_Wait_Cal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.revive);
		setState(0xE3C3077BL /*Fall_Wait_Cal*/);
		worldNotify(EChatNoticeType.HuntingEnd, "GAME", "LUA_HUNTINGEND_CAL_GARGOYLE");
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi()._GargoyleDie(getActor(), null));
		doAction(973257915L /*FLYING_BATTLE_WAIT*/, blendTime, onDoActionEnd -> revive(1, () -> {
			return false;
		}, onExit -> scheduleState(state -> Fall(blendTime), 1000)));
	}

	protected void Fall_Wait_Med(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.revive);
		setState(0xC4E29919L /*Fall_Wait_Med*/);
		worldNotify(EChatNoticeType.HuntingEnd, "GAME", "LUA_HUNTINGEND_MED_GARGOYLE");
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi()._GargoyleDie(getActor(), null));
		doAction(973257915L /*FLYING_BATTLE_WAIT*/, blendTime, onDoActionEnd -> revive(1, () -> {
			return false;
		}, onExit -> scheduleState(state -> Fall(blendTime), 1000)));
	}

	protected void Fall(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x433E1948L /*Fall*/);
		doAction(2369942272L /*FALLING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Die_Str_Cal(blendTime), 1000)));
	}

	protected void Fall_ing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD5CC3C50L /*Fall_ing*/);
		doAction(2369942272L /*FALLING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Die_Str_Cal(blendTime), 1000)));
	}

	protected void Attack_Normal(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xA730A939L /*Attack_Normal*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Cal_WayPoint_Logic(blendTime)))
				return;
		}
		doAction(2520365536L /*FLYING_ATTACKNORMAL*/, blendTime, onDoActionEnd -> changeState(state -> Move_Cal_WayPoint_Logic(blendTime)));
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
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 2 && (getState() == 0x628C2BEL /*Move_Cal_WayPoint1_Wait*/ || getState() == 0x1FDFE6BAL /*Move_Cal_WayPoint2_Wait*/ || getState() == 0x5CED22A3L /*Move_Cal_WayPoint3_Wait*/ || getState() == 0x5B796B47L /*Move_Cal_WayPoint4_Wait*/ || getState() == 0x51A0D5D9L /*Move_Cal_WayPoint5_Wait*/ || getState() == 0x8F56C998L /*Move_Cal_WayPoint6_Wait*/ || getState() == 0x3EA62C89L /*Move_Cal_WayPoint7_Wait*/ || getState() == 0x5E328765L /*Move_Cal_WayPoint8_Wait*/ || getState() == 0x749C36FAL /*Move_Cal_WayPoint9_Wait*/)) {
			if (changeState(state -> Fall_TreeClingOff_Bal(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 0 && (getState() == 0x628C2BEL /*Move_Cal_WayPoint1_Wait*/ || getState() == 0x1FDFE6BAL /*Move_Cal_WayPoint2_Wait*/ || getState() == 0x5CED22A3L /*Move_Cal_WayPoint3_Wait*/ || getState() == 0x5B796B47L /*Move_Cal_WayPoint4_Wait*/ || getState() == 0x51A0D5D9L /*Move_Cal_WayPoint5_Wait*/ || getState() == 0x8F56C998L /*Move_Cal_WayPoint6_Wait*/ || getState() == 0x3EA62C89L /*Move_Cal_WayPoint7_Wait*/ || getState() == 0x5E328765L /*Move_Cal_WayPoint8_Wait*/ || getState() == 0x749C36FAL /*Move_Cal_WayPoint9_Wait*/)) {
			if (changeState(state -> Fall_TreeClingOff_Cal(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 1 && (getState() == 0x628C2BEL /*Move_Cal_WayPoint1_Wait*/ || getState() == 0x1FDFE6BAL /*Move_Cal_WayPoint2_Wait*/ || getState() == 0x5CED22A3L /*Move_Cal_WayPoint3_Wait*/ || getState() == 0x5B796B47L /*Move_Cal_WayPoint4_Wait*/ || getState() == 0x51A0D5D9L /*Move_Cal_WayPoint5_Wait*/ || getState() == 0x8F56C998L /*Move_Cal_WayPoint6_Wait*/ || getState() == 0x3EA62C89L /*Move_Cal_WayPoint7_Wait*/ || getState() == 0x5E328765L /*Move_Cal_WayPoint8_Wait*/ || getState() == 0x749C36FAL /*Move_Cal_WayPoint9_Wait*/)) {
			if (changeState(state -> Fall_TreeClingOff_Med(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 2) {
			if (changeState(state -> Fall_Wait_Bal(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 0) {
			if (changeState(state -> Fall_Wait_Cal(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x9AA4CDE2L /*_RegionNo*/) == 1) {
			if (changeState(state -> Fall_Wait_Med(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
