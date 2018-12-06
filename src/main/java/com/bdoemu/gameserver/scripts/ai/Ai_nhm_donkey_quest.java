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
@IAIName("nhm_donkey_quest")
public class Ai_nhm_donkey_quest extends CreatureAI {
	public Ai_nhm_donkey_quest(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xE5BD13F2L /*_Degree*/, 0);
		setVariable(0x4D347D06L /*_isUnderWater*/, 0);
		setVariable(0x21C76BFFL /*_isWater*/, 0);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0x549051CDL /*_WayPointLoopType*/, getVariable(0xA701F14DL /*AI_WayPointLoopType*/));
		setVariable(0x19EB970L /*_WayPointLoopCount*/, getVariable(0xABA96D52L /*AI_WayPointLoopCount*/));
		setVariable(0x610E183EL /*_WayPointType*/, getVariable(0xC687E0D9L /*AI_WayPointType*/));
		setVariable(0x692F3C5CL /*_WayPointKey1*/, getVariable(0x3DFFB456L /*AI_WayPointKey1*/));
		setVariable(0x9231A1DL /*_WayPointKey2*/, getVariable(0xCD7084F0L /*AI_WayPointKey2*/));
		setVariable(0x7F4A9F05L /*_WayPointKey3*/, getVariable(0x73DB50B5L /*AI_WayPointKey3*/));
		setVariable(0x3ECDBD02L /*_WayPointKey4*/, getVariable(0x8385DA8EL /*AI_WayPointKey4*/));
		setVariable(0x6013B50FL /*_WayPointKey5*/, getVariable(0xF9FBC5A4L /*AI_WayPointKey5*/));
		setVariableArray(0x2C4960E5L /*_WaypointValue1*/, new Integer[] { getVariable(0x610E183EL /*_WayPointType*/), getVariable(0x692F3C5CL /*_WayPointKey1*/) });
		setVariableArray(0x11AADB03L /*_WaypointValue2*/, new Integer[] { getVariable(0x610E183EL /*_WayPointType*/), getVariable(0x9231A1DL /*_WayPointKey2*/) });
		setVariableArray(0x9425998CL /*_WaypointValue3*/, new Integer[] { getVariable(0x610E183EL /*_WayPointType*/), getVariable(0x7F4A9F05L /*_WayPointKey3*/) });
		setVariableArray(0x58ADA3CL /*_WaypointValue4*/, new Integer[] { getVariable(0x610E183EL /*_WayPointType*/), getVariable(0x3ECDBD02L /*_WayPointKey4*/) });
		setVariableArray(0x9FFA920EL /*_WaypointValue5*/, new Integer[] { getVariable(0x610E183EL /*_WayPointType*/), getVariable(0x6013B50FL /*_WayPointKey5*/) });
		setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Wait(blendTime), 1000));
	}

	protected void Start_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9D7E92CDL /*Start_Wait*/);
		if (getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1) {
			if(getCallCount() == 5) {
				if (changeState(state -> Movepoint_Logic(0.2)))
					return;
			}
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x866C7489L /*Wait*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		if (target != null && (getDistanceToTarget(target, false) >= 0 && getDistanceToTarget(target, false) <= 4000) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(blendTime)))
				return;
		}
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x29A56175L /*Stop*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		setVariable(0x4D347D06L /*_isUnderWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("under_water_ground") ? 1 : 0));
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 35311 && getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 35311 && getVariable(0x4D347D06L /*_isUnderWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(getCallCount() == 120) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target == null) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 300 && getDistanceToTarget(target, false) <= 800) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 2000) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > getVariable(0xFCD51321L /*AI_Limitedtime*/)) {
			if (changeState(state -> TimeOut_Die(blendTime)))
				return;
		}
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> changeState(state -> Stop(blendTime)));
	}

	protected void QuestCheck_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x319C3006L /*QuestCheck_Logic*/);
		if (target != null && !isTargetProcessQuest(target, getVariable(0x20D4055CL /*AI_QuestGroupNo*/), getVariable(0x5948BE32L /*AI_QuestNo*/))) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0xA84F6B9AL /*AI_IsBranch*/) == 1 && target != null && !isTargetProcessQuest(target, getVariable(0xEDAF762FL /*AI_QuestGroupNo2*/), getVariable(0x2CADBFC7L /*AI_QuestNo2*/))) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		changeState(state -> Stop(blendTime));
	}

	protected void Stop2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC7E54F9L /*Stop2*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		setVariable(0x4D347D06L /*_isUnderWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("under_water_ground") ? 1 : 0));
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 35311 && getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 35311 && getVariable(0x4D347D06L /*_isUnderWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if(getCallCount() == 60) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target == null) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 300 && getDistanceToTarget(target, false) <= 800) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 2000) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > getVariable(0xFCD51321L /*AI_Limitedtime*/)) {
			if (changeState(state -> TimeOut_Die(blendTime)))
				return;
		}
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> changeState(state -> Stop2(blendTime)));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Start_Wait(blendTime), 3000)));
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
		clearAggro(true);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stop(blendTime), 2000));
	}

	protected void FailFindPath_ToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC53FD679L /*FailFindPath_ToTarget*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stop(blendTime), 2000));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		setVariable(0x4D347D06L /*_isUnderWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("under_water_ground") ? 1 : 0));
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 35311 && getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 35311 && getVariable(0x4D347D06L /*_isUnderWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 2000) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Stop(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Stop2(blendTime)))
				return;
		}
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > getVariable(0xFCD51321L /*AI_Limitedtime*/)) {
			if (changeState(state -> TimeOut_Die(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_ToTarget(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Move_Chaser(blendTime), 100)));
	}

	protected void HighSpeed_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x4C327D19L /*HighSpeed_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		setVariable(0x4D347D06L /*_isUnderWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("under_water_ground") ? 1 : 0));
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 35311 && getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 35311 && getVariable(0x4D347D06L /*_isUnderWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 200 && getDistanceToTarget(target, false) <= 600) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Stop(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 2000) {
			if (changeState(state -> Stop2(blendTime)))
				return;
		}
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath_ToTarget(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> HighSpeed_Chaser(blendTime), 100)));
	}

	protected void Movepoint_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x244DE952L /*Movepoint_Logic*/);
		if (getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 1) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_WayPoint(blendTime)))
					return;
			}
		}
		if (getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 2) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_WayPoint2(blendTime)))
					return;
			}
		}
		if (getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 3) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_WayPoint3(blendTime)))
					return;
			}
		}
		if (getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 4) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_WayPoint4(blendTime)))
					return;
			}
		}
		if (getVariable(0x19EB970L /*_WayPointLoopCount*/) >= 5) {
			if(Rnd.getChance(10)) {
				if (changeState(state -> Move_WayPoint5(blendTime)))
					return;
			}
		}
		changeState(state -> Movepoint_Logic(blendTime));
	}

	protected void Move_WayPoint(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8F2D3B47L /*Move_WayPoint*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x2C4960E5L /*_WaypointValue1*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 1);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 1) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			if (getVariable(0x549051CDL /*_WayPointLoopType*/) == 1) {
				setVariable(0x19EB970L /*_WayPointLoopCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Start_Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9554FCBFL /*Move_WayPoint2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x11AADB03L /*_WaypointValue2*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 2);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 2) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Start_Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x782710B0L /*Move_WayPoint3*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9425998CL /*_WaypointValue3*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 3);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 3) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Start_Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x750B7E6EL /*Move_WayPoint4*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x58ADA3CL /*_WaypointValue4*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 4);
			if (getVariable(0x19EB970L /*_WayPointLoopCount*/) == 4) {
				setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			}
			return false;
		}, onExit -> scheduleState(state -> Start_Wait(blendTime), 1000)));
	}

	protected void Move_WayPoint5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7897BB6FL /*Move_WayPoint5*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0x9FFA920EL /*_WaypointValue5*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			setVariable(0xD1A2EF4FL /*_WayPointCount*/, 0);
			return false;
		}, onExit -> scheduleState(state -> Start_Wait(blendTime), 1000)));
	}

	protected void End_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xB27633BDL /*End_Die*/);
		doAction(1426790432L /*END_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> End_Die(blendTime), 3000));
	}

	protected void TimeOut_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xF22BCAA1L /*TimeOut_Die*/);
		clearAggro(true);
		doAction(1426790432L /*END_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> TimeOut_Die(blendTime), 3000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		clearAggro(true);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTalkToDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target == null) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		}
		if (changeState(state -> Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerEscort_Reset(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> QuestCheck_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Stop2(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
