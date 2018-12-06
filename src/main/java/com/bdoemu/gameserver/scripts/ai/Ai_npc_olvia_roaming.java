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
@IAIName("npc_olvia_roaming")
public class Ai_npc_olvia_roaming extends CreatureAI {
	public Ai_npc_olvia_roaming(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
		setVariable(0xACD660AFL /*WaypointValue*/, 0);
		setVariable(0xD43EFDE8L /*_RandomMovePoint*/, 0);
		setVariable(0xE60F92ADL /*_MinMove*/, 0);
		setVariable(0xF7AC801L /*_MaxMove*/, 0);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
			if (changeState(state -> Die_Wait(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 5000));
	}

	protected void Die_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DC3CFB8L /*Die_Wait*/);
		doAction(1926787974L /*HIDEMESH*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 3000));
	}

	protected void Damage_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x4E1B659L /*Damage_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Damage_Die(blendTime), 20000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xE60F92ADL /*_MinMove*/, 100);
		setVariable(0xF7AC801L /*_MaxMove*/, 400);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 10000));
	}

	protected void Select_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xEC5E05D2L /*Select_Position*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(37));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30975 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30732 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30705 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30716 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30729 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30722 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30931 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30933 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30934 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30939 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30679 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 32576 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 32584 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 21282 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 14 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30845 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 15 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 32587 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 16 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30850 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 17 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 32589 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 18 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 21209 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 19 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30852 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 20 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30857 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 21 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30864 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 22 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30971 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 23 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30963 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 24 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30807 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 25 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 32592 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 26 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30863 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 27 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30853 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 28 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 21257 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 29 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 21243 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 30 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30885 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 31 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30882 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 32 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30733 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 33 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30805 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 34 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30867 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 35 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30891 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 36 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 30896 });
		}
		if (changeState(state -> Move_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Position(blendTime), 5000 + Rnd.get(-500,500)));
	}

	protected void Move_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xFF3E58ABL /*Move_Position*/);
		if(Rnd.getChance(10)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.WaypointVariable, getVariableArray(0xACD660AFL /*WaypointValue*/), ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> Die_Wait(0.3)))
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
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 400, true, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> Die_Wait(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait_for_Talk(blendTime), 15000)));
	}

	protected void Wait_for_Talk(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7850D1C4L /*Wait_for_Talk*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Position(blendTime), 20000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
