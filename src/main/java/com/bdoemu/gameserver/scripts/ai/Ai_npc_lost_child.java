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
@IAIName("npc_lost_child")
public class Ai_npc_lost_child extends CreatureAI {
	public Ai_npc_lost_child(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
		setVariable(0x4D347D06L /*_isUnderWater*/, 0);
		setVariable(0x21C76BFFL /*_isWater*/, 0);
		setVariable(0xACD660AFL /*WaypointValue*/, 0);
		setVariable(0x5A19B264L /*_IsBranch*/, getVariable(0xA84F6B9AL /*AI_IsBranch*/));
		setVariable(0x95AC12F8L /*_triggerkey*/, getVariable(0x60D03638L /*AI_triggerkey*/));
		setVariable(0x13CBC00EL /*_triggerkey2*/, getVariable(0xCBC80771L /*AI_triggerkey2*/));
		setVariable(0xE60F92ADL /*_MinMove*/, 0);
		setVariable(0xF7AC801L /*_MaxMove*/, 50);
		setVariable(0x55680CB8L /*_callcountcheck*/, 0);
		setVariable(0x53FF57D0L /*_StartTime*/, 0);
		setVariable(0x6DDCA962L /*_IngTime*/, 0);
		setVariable(0xE26EA7A0L /*_EndTime*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		setVariable(0xEFDADC56L /*_isFindPathCompleted01*/, 0);
		setVariable(0xBA6091EBL /*_FailFindPathCount01*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41201) {
			if (changeState(state -> Select_Position_41201(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41202) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41203) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41204) {
			if (changeState(state -> Select_Position_41204(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41205) {
			if (changeState(state -> Select_Position_41205(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41206) {
			if (changeState(state -> Select_Position_41206(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41207) {
			if (changeState(state -> Select_Position_41207(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41208) {
			if (changeState(state -> Select_Position_41208(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41209) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41210) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41211) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41212) {
			if (changeState(state -> Select_Position_41212(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41213) {
			if (changeState(state -> Select_Position_41213(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41214) {
			if (changeState(state -> Select_Position_41214(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41215) {
			if (changeState(state -> _41215_Go(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41216) {
			if (changeState(state -> Select_Position_41216(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41217) {
			if (changeState(state -> Select_Position_41217(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41218) {
			if (changeState(state -> Select_Position_41218(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41219) {
			if (changeState(state -> Select_Position_41219(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41220) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41226) {
			if (changeState(state -> Select_Position_41226(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41227) {
			if (changeState(state -> Select_Position_41227(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41228) {
			if (changeState(state -> Select_Position_41228(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42901) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41234) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42921) {
			if (changeState(state -> Select_Position_42921(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42922) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42923) {
			if (changeState(state -> _42923_Go(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42924) {
			if (changeState(state -> Select_Position_42924(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42925) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42926) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42927) {
			if (changeState(state -> Select_Position_42927(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42928) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42929) {
			if (changeState(state -> Select_Position_42929(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42930) {
			if (changeState(state -> Select_Position_42930(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42931) {
			if (changeState(state -> Select_Position_42931(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42932) {
			if (changeState(state -> Select_Position_42932(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42933) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42934) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42935) {
			if (changeState(state -> Select_Position_42935(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 35051) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 35617) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 5000));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 10000));
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

	protected void Select_Position_41201(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x55042306L /*Select_Position_41201*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(6));
		setVariable(0xE60F92ADL /*_MinMove*/, 0);
		setVariable(0xF7AC801L /*_MaxMove*/, 0);
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12132 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12134 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18569 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18576 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12062 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12060 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41201(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41204(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD867371AL /*Select_Position_41204*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(3));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 305 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 306 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 307 });
		}
		if (changeState(state -> FastMove_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41204(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41206(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAC5AB491L /*Select_Position_41206*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(5));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12455 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12797 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12468 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12451 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12725 });
		}
		if (changeState(state -> FastMove_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41206(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41205(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE98ABA57L /*Select_Position_41205*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(4));
		setVariable(0xE60F92ADL /*_MinMove*/, 0);
		setVariable(0xF7AC801L /*_MaxMove*/, 0);
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12823 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12483 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12483 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12483 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41205(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41207(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7B94F35EL /*Select_Position_41207*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(4));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12698 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12712 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12724 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12443 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41207(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41208(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC31D3D3EL /*Select_Position_41208*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(3));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12700 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12695 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12734 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41208(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41212(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC804826AL /*Select_Position_41212*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(3));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12504 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12858 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12497 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41212(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41213(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE2B8F9A8L /*Select_Position_41213*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(8));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12502 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12501 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12500 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12503 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12504 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12857 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12499 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12498 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41213(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41214(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x604ED54CL /*Select_Position_41214*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(3));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12839 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12817 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12813 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41214(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void _41215_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x54F4F0EAL /*41215_Go*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_107", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _41215_Wait(blendTime), 1000)));
	}

	protected void _41215_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2E296710L /*41215_Go1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_095", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _41215_Wait1(blendTime), 1000)));
	}

	protected void _41215_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB38CB088L /*41215_Go2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_086", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _41215_Wait2(blendTime), 1000)));
	}

	protected void _41215_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDBED91C1L /*41215_Go3*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_076", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _41215_Wait3(blendTime), 1000)));
	}

	protected void _41215_Go4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3C1F8D09L /*41215_Go4*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_069", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _41215_Wait4(blendTime), 1000)));
	}

	protected void _41215_Go5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x41C05AE6L /*41215_Go5*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_063", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _41215_Wait5(blendTime), 1000)));
	}

	protected void _41215_Go6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB02413F3L /*41215_Go6*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_1046", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _41215_Wait6(blendTime), 1000)));
	}

	protected void _41215_Go7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE24092C1L /*41215_Go7*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_1040", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _41215_Wait7(blendTime), 1000)));
	}

	protected void _41215_Go8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2C20B75CL /*41215_Go8*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_133", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _41215_Wait8(blendTime), 1000)));
	}

	protected void _41215_Go9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB44F00A6L /*41215_Go9*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_129", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _41215_Wait9(blendTime), 1000)));
	}

	protected void _41215_Go10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFC7D9AA5L /*41215_Go10*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("explore", "road(serendia)_124", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _41215_Wait10(blendTime), 1000)));
	}

	protected void _41215_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8995916CL /*41215_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _41215_Go1(blendTime), 100000));
	}

	protected void _41215_Wait1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x30D072D2L /*41215_Wait1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _41215_Go2(blendTime), 100000));
	}

	protected void _41215_Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1B9F9FB4L /*41215_Wait2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _41215_Go3(blendTime), 100000));
	}

	protected void _41215_Wait3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x920599B7L /*41215_Wait3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _41215_Go4(blendTime), 100000));
	}

	protected void _41215_Wait4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x844ABE2EL /*41215_Wait4*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _41215_Go5(blendTime), 100000));
	}

	protected void _41215_Wait5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8AC958DBL /*41215_Wait5*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _41215_Go6(blendTime), 100000));
	}

	protected void _41215_Wait6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x35257BAL /*41215_Wait6*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _41215_Go7(blendTime), 100000));
	}

	protected void _41215_Wait7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xF703BA2BL /*41215_Wait7*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _41215_Go8(blendTime), 100000));
	}

	protected void _41215_Wait8(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1BA81879L /*41215_Wait8*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _41215_Go9(blendTime), 100000));
	}

	protected void _41215_Wait9(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x65A3B073L /*41215_Wait9*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _41215_Go10(blendTime), 100000));
	}

	protected void _41215_Wait10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x723ED957L /*41215_Wait10*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _41215_Go(blendTime), 100000));
	}

	protected void Select_Position_41216(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2DB146BFL /*Select_Position_41216*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(8));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12601 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12608 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12614 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12622 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12632 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12643 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12337 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12341 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41216(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41217(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8BCAF620L /*Select_Position_41217*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(8));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12133 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12129 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12126 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12121 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12118 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12114 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12110 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12101 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41217(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41218(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC66D32C3L /*Select_Position_41218*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(6));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12379 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12375 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12374 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12372 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12369 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12365 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41218(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41219(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x29DD1E14L /*Select_Position_41219*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(7));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12873 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12878 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12880 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12882 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12884 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12886 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 12889 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41219(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41226(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3D784253L /*Select_Position_41226*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(14));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10861 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10854 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10908 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10849 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10844 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10885 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10874 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10870 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10868 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10834 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10832 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10825 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10823 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10896 });
		}
		if(Rnd.getChance(70)) {
			if (changeState(state -> FastMove_Position(blendTime)))
				return;
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41226(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41227(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x39E60FA8L /*Select_Position_41227*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(14));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10861 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10854 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10908 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10849 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10844 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10885 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10874 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10870 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10868 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10834 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10832 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10825 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10823 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10896 });
		}
		if(Rnd.getChance(70)) {
			if (changeState(state -> FastMove_Position(blendTime)))
				return;
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41226(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_41228(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5426865EL /*Select_Position_41228*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(14));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10861 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10854 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10908 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10849 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10844 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10885 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10874 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10870 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10868 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10834 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10832 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10825 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10823 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 10896 });
		}
		if(Rnd.getChance(70)) {
			if (changeState(state -> FastMove_Position(blendTime)))
				return;
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_41226(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_42921(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x85099106L /*Select_Position_42921*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(3));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14780 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14796 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14765 });
		}
		if(Rnd.getChance(70)) {
			if (changeState(state -> FastMove_Position(blendTime)))
				return;
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_42921(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void _42923_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8EA9EE0DL /*42923_Go*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "42923_Roaming_02", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _42923_Wait(blendTime), 1000)));
	}

	protected void _42923_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD6F6505CL /*42923_Go1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "42923_Roaming_04", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _42923_Wait1(blendTime), 1000)));
	}

	protected void _42923_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9FD5EFCFL /*42923_Go2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "42923_Roaming_06", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _42923_Wait2(blendTime), 1000)));
	}

	protected void _42923_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x1D8B15F5L /*42923_Go3*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "42923_Roaming_08", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _42923_Wait3(blendTime), 1000)));
	}

	protected void _42923_Go4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6707DE94L /*42923_Go4*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "42923_Roaming_09", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _42923_Wait4(blendTime), 1000)));
	}

	protected void _42923_Go5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEE1AE700L /*42923_Go5*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "42923_Roaming_11", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _42923_Wait5(blendTime), 1000)));
	}

	protected void _42923_Go6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6506D772L /*42923_Go6*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "42923_Roaming_13", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _42923_Wait6(blendTime), 1000)));
	}

	protected void _42923_Go7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9DDB070EL /*42923_Go7*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("npc", "42923_Roaming_15", ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> _42923_Wait7(blendTime), 1000)));
	}

	protected void _42923_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1D4203F4L /*42923_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _42923_Go1(blendTime), 100000));
	}

	protected void _42923_Wait1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2CE85110L /*42923_Wait1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _42923_Go2(blendTime), 100000));
	}

	protected void _42923_Wait2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA781139DL /*42923_Wait2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _42923_Go3(blendTime), 100000));
	}

	protected void _42923_Wait3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1562FD44L /*42923_Wait3*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _42923_Go4(blendTime), 100000));
	}

	protected void _42923_Wait4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x50BD7C24L /*42923_Wait4*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _42923_Go5(blendTime), 100000));
	}

	protected void _42923_Wait5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5ADF1AA1L /*42923_Wait5*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _42923_Go6(blendTime), 100000));
	}

	protected void _42923_Wait6(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8536881DL /*42923_Wait6*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _42923_Go7(blendTime), 100000));
	}

	protected void _42923_Wait7(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC1A256F7L /*42923_Wait7*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> _42923_Go(blendTime), 100000));
	}

	protected void Select_Position_42924(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x815A4DA9L /*Select_Position_42924*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(4));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15170 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15165 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15161 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15156 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_42924(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_42927(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xDBA74412L /*Select_Position_42927*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(4));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15273 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15229 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15261 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15234 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_42927(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_42929(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD0F35B1CL /*Select_Position_42929*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(6));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15097 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15093 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15092 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15018 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15015 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15017 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_42929(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_42930(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA4C5655FL /*Select_Position_42930*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(13));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12187 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12190 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12191 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12194 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12195 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12198 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12199 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12202 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12203 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12206 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12207 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12210 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12211 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_42930(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_42931(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x706DE982L /*Select_Position_42931*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(3));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15121 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15113 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 18452 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_42931(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_42932(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x164C28EFL /*Select_Position_42932*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(11));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12232 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12249 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12233 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12246 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12244 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12236 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12235 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12234 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12237 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12241 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 12242 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_42932(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Select_Position_42935(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC0D9B34EL /*Select_Position_42935*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(3));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 15220 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14976 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 14980 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position_42935(blendTime), 10000 + Rnd.get(-5000,5000)));
	}

	protected void Move_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFF3E58ABL /*Move_Position*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0xACD660AFL /*WaypointValue*/), ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> Wait(blendTime), 5000)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 50, 200, true, ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> Wait_for_Talk(blendTime), 5000)));
	}

	protected void Wait_for_Talk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7850D1C4L /*Wait_for_Talk*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Position(blendTime), 200000));
	}

	protected void FastMove_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF3F1A33L /*FastMove_Position*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Random_1(blendTime)))
				return;
		}
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0xACD660AFL /*WaypointValue*/), ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> Wait(blendTime), 5000)));
	}

	protected void Move_Random_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF315D8BBL /*Move_Random_1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 50, 200, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_for_Talk_1(blendTime), 5000)));
	}

	protected void Wait_for_Talk_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x83F0C5FCL /*Wait_for_Talk_1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> FastMove_Position(blendTime), 200000));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void FailFindPath_Logic01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3F8799ECL /*FailFindPath_Logic01*/);
		setVariable(0xBA6091EBL /*_FailFindPathCount01*/, getVariable(0xBA6091EBL /*_FailFindPathCount01*/) + 1);
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> changeState(state -> Following_Wait(blendTime)));
	}

	protected void Following_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6D1420BEL /*Following_Wait*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		setVariable(0x53FF57D0L /*_StartTime*/, getTime());
		if (target != null && (getDistanceToTarget(target, false) >= 200 && getDistanceToTarget(target, false) <= 800) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(blendTime)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 3000) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> HighSpeed_Chaser(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 3000) {
			if (changeState(state -> Stop2(blendTime)))
				return;
		}
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> changeState(state -> Following_Wait(blendTime)));
	}

	protected void Move_Chaser(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0x39B3FBC2L /*Move_Chaser*/);
		if (isTargetLost()) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		setVariable(0x55680CB8L /*_callcountcheck*/, getVariable(0x55680CB8L /*_callcountcheck*/) - 1);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		setVariable(0x4D347D06L /*_isUnderWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("under_water_ground") ? 1 : 0));
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 41228 && getVariable(0x47C57E4AL /*CharIDValue*/) != 41211 && getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 41228 && getVariable(0x47C57E4AL /*CharIDValue*/) != 41211 && getVariable(0x4D347D06L /*_isUnderWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 3000) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Stop(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 3000) {
			if (changeState(state -> Stop2(blendTime)))
				return;
		}
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > getVariable(0xFCD51321L /*AI_Limitedtime*/)) {
			if (changeState(state -> TimeOut_Die(blendTime)))
				return;
		}
		doAction(102581846L /*FASTCHASE*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0xEFDADC56L /*_isFindPathCompleted01*/, isFindPathCompleted());
			if (getVariable(0xEFDADC56L /*_isFindPathCompleted01*/) == 0 && getVariable(0xBA6091EBL /*_FailFindPathCount01*/) >= 3) {
				if (changeState(state -> FailFindPath_ToTarget(0.3)))
					return true;
			}
			if (getVariable(0xEFDADC56L /*_isFindPathCompleted01*/) == 0) {
				if (changeState(state -> FailFindPath_Logic01(0.3)))
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
		setVariable(0x55680CB8L /*_callcountcheck*/, getVariable(0x55680CB8L /*_callcountcheck*/) - 1);
		setVariable(0x6DDCA962L /*_IngTime*/, getTime());
		setVariable(0xE26EA7A0L /*_EndTime*/, getVariable(0x6DDCA962L /*_IngTime*/) - getVariable(0x53FF57D0L /*_StartTime*/));
		setVariable(0x4D347D06L /*_isUnderWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("under_water_ground") ? 1 : 0));
		setVariable(0x21C76BFFL /*_isWater*/, (getCurrentPos_NaviType()==getNaviTypeStringToEnum("water") ? 1 : 0));
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 41228 && getVariable(0x47C57E4AL /*CharIDValue*/) != 41211 && getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 41228 && getVariable(0x47C57E4AL /*CharIDValue*/) != 41211 && getVariable(0x4D347D06L /*_isUnderWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 200 && getDistanceToTarget(target, false) <= 800) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) < 200) {
			if (changeState(state -> Stop(0.3)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 3000) {
			if (changeState(state -> Stop2(blendTime)))
				return;
		}
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > getVariable(0xFCD51321L /*AI_Limitedtime*/)) {
			if (changeState(state -> TimeOut_Die(blendTime)))
				return;
		}
		doAction(102581846L /*FASTCHASE*/, blendTime, onDoActionEnd -> chase(getVariable(0xD2BFE684L /*TargetCycle*/), () -> {
			setVariable(0xEFDADC56L /*_isFindPathCompleted01*/, isFindPathCompleted());
			if (getVariable(0xEFDADC56L /*_isFindPathCompleted01*/) == 0 && getVariable(0xBA6091EBL /*_FailFindPathCount01*/) >= 3) {
				if (changeState(state -> FailFindPath_ToTarget(0.3)))
					return true;
			}
			if (getVariable(0xEFDADC56L /*_isFindPathCompleted01*/) == 0) {
				if (changeState(state -> FailFindPath_Logic01(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> HighSpeed_Chaser(blendTime), 100)));
	}

	protected void AddAggroMove(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x6BD1234EL /*AddAggroMove*/);
		setVariable(0x55680CB8L /*_callcountcheck*/, 20);
		doAction(4268605662L /*ADD_AGGRO_LV2*/, blendTime, onDoActionEnd -> changeState(state -> Following_Wait(blendTime)));
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
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 41228 && getVariable(0x47C57E4AL /*CharIDValue*/) != 41211 && getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 41228 && getVariable(0x47C57E4AL /*CharIDValue*/) != 41211 && getVariable(0x4D347D06L /*_isUnderWater*/) == 1) {
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
		if (target != null && (getDistanceToTarget(target, false) >= 200 && getDistanceToTarget(target, false) <= 800) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 3000) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> HighSpeed_Chaser(0.3)))
				return;
		}
		if (getVariable(0xE26EA7A0L /*_EndTime*/) > getVariable(0xFCD51321L /*AI_Limitedtime*/)) {
			if (changeState(state -> TimeOut_Die(blendTime)))
				return;
		}
		doAction(3401480022L /*WAIT2*/, blendTime, onDoActionEnd -> changeState(state -> Stop(blendTime)));
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
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 41228 && getVariable(0x47C57E4AL /*CharIDValue*/) != 41211 && getVariable(0x21C76BFFL /*_isWater*/) == 1) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) != 41228 && getVariable(0x47C57E4AL /*CharIDValue*/) != 41211 && getVariable(0x4D347D06L /*_isUnderWater*/) == 1) {
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
		if (target != null && (getDistanceToTarget(target, false) >= 200 && getDistanceToTarget(target, false) <= 800) && target != null && getTargetHp(target) > 0) {
			if (changeState(state -> Move_Chaser(0.3)))
				return;
		}
		if (target != null && (getDistanceToTarget(target, false) >= 800 && getDistanceToTarget(target, false) <= 3000) && target != null && getTargetHp(target) > 0) {
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
		}, onExit -> scheduleState(state -> InitialState(blendTime), 3000)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 2000));
	}

	protected void FailFindPath_ToTarget(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC53FD679L /*FailFindPath_ToTarget*/);
		setVariable(0xEFDADC56L /*_isFindPathCompleted01*/, 0);
		doTeleport(EAIMoveDestType.Random, 100, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stop(blendTime), 2000));
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
	public EAiHandlerResult HandlerEscort_Reset(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> QuestCheck_Logic(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTalkToDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (target == null) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41201) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41202) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41203) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41206) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41207) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41208) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41209) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41210) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41211) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41216) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41217) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41218) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41219) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41226) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41227) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41228) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42901) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 41234) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42921) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42922) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42923) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42924) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42925) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42926) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42928) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42929) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42931) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42932) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42933) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42934) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 42935) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 35051) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x47C57E4AL /*CharIDValue*/) == 35617) {
			if (changeState(state -> Following_Wait(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
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
