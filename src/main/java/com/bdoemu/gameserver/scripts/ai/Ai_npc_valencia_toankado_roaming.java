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
@IAIName("npc_valencia_toankado_roaming")
public class Ai_npc_valencia_toankado_roaming extends CreatureAI {
	public Ai_npc_valencia_toankado_roaming(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, 0);
		setVariable(0xACD660AFL /*WaypointValue*/, 0);
		setVariable(0xD43EFDE8L /*_RandomMovePoint*/, 0);
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
		if(Rnd.getChance(50)) {
			if (changeState(state -> Select_Valencia_Position(blendTime)))
				return;
		}
		if (changeState(state -> Select_Ankado_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 10000));
	}

	protected void Wait_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x96AB8779L /*Wait_1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Valencia_Position(blendTime), 10000));
	}

	protected void Wait_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD6E9C46CL /*Wait_2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Ankado_Position(blendTime), 10000));
	}

	protected void Select_Valencia_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA2B8C536L /*Select_Valencia_Position*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(315));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40675 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40685 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40695 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40705 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40715 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40725 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40735 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40745 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40755 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40765 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40775 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40785 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40795 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40805 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 14 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40815 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 15 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40825 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 16 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40835 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 17 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40845 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 18 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40855 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 19 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40865 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 20 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40875 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 21 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40885 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 22 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40895 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 23 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40905 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 24 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40915 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 25 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40925 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 26 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40935 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 27 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40945 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 28 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40955 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 29 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40965 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 30 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40975 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 31 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40985 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 32 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 40995 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 33 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41005 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 34 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41015 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 35 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41025 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 36 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41035 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 37 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 38) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41045 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 38 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 39) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41055 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 39 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 40) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41065 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 40 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 41) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41075 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 41 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 42) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41085 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 42 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 43) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41095 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 43 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 44) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41105 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 44 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 45) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41115 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 45 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 46) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41125 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 46 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 47) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41135 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 47 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 48) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41145 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 48 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 49) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41155 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 49 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 50) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 41165 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 50 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 51) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45395 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 51 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 52) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45405 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 52 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 53) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45415 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 53 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 54) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45425 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 54 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 55) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45435 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 55 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 56) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45445 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 56 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 57) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45455 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 57 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 58) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45465 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 58 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 59) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45475 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 59 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 60) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45485 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 60 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 61) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45495 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 61 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 62) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45505 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 62 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 63) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45515 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 63 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 64) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45525 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 64 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 65) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45535 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 65 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 66) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45545 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 66 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 67) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45555 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 67 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 68) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45565 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 68 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 69) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45575 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 69 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 70) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45585 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 70 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 71) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45595 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 71 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 72) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45605 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 72 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 73) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45615 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 73 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 74) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45625 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 74 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 75) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45635 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 75 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 76) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45645 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 76 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 77) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45655 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 77 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 78) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45665 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 78 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 79) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45675 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 79 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 80) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45685 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 80 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 81) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45695 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 81 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 82) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45705 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 82 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 83) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45715 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 83 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 84) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45725 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 84 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 85) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45735 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 85 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 86) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45745 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 86 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 87) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45755 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 87 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 88) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45765 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 88 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 89) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45775 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 89 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 90) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45785 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 90 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 91) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45795 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 91 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 92) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45805 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 92 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 93) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45815 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 93 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 94) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45825 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 94 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 95) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45835 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 95 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 96) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45845 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 96 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 97) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45855 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 97 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 98) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45865 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 98 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 99) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45875 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 99 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 100) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45885 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 100 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 101) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45895 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 101 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 102) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45905 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 102 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 103) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45915 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 103 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 104) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45925 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 104 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 105) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45935 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 105 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 106) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45945 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 106 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 107) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45955 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 107 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 108) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45965 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 108 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 109) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45975 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 109 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 110) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45985 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 110 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 111) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 45995 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 111 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 112) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46005 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 112 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 113) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46015 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 113 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 114) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46025 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 114 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 115) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46035 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 115 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 116) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46045 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 116 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 117) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46055 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 117 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 118) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46065 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 118 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 119) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46075 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 119 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 120) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46085 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 120 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 121) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46095 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 121 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 122) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46105 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 122 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 123) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46115 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 123 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 124) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46125 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 124 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 125) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46135 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 125 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 126) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46145 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 126 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 127) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46155 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 127 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 128) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46165 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 128 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 129) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46175 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 129 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 130) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46185 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 130 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 131) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46195 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 131 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 132) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46205 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 132 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 133) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46215 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 133 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 134) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46225 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 134 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 135) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46235 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 135 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 136) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46245 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 136 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 137) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46255 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 137 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 138) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46265 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 138 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 139) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46275 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 139 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 140) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46285 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 140 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 141) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46295 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 141 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 142) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46305 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 142 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 143) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46315 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 143 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 144) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46325 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 144 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 145) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46335 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 145 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 146) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46345 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 146 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 147) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46355 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 147 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 148) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46365 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 148 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 149) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46375 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 149 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 150) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46385 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 150 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 151) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46395 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 151 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 152) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46405 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 152 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 153) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46415 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 153 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 154) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46425 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 154 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 155) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46435 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 155 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 156) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46445 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 156 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 157) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46455 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 157 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 158) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46465 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 158 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 159) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46475 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 159 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 160) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46485 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 160 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 161) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46495 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 161 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 162) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46505 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 162 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 163) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46515 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 163 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 164) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46525 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 164 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 165) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46535 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 165 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 166) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46545 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 166 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 167) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46555 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 167 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 168) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46565 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 168 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 169) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46575 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 169 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 170) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46585 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 170 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 171) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46595 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 171 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 172) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46605 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 172 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 173) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46615 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 173 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 174) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46625 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 174 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 175) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46635 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 175 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 176) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46645 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 176 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 177) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46655 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 177 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 178) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46665 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 178 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 179) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46675 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 179 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 180) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46685 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 180 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 181) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46695 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 181 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 182) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46705 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 182 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 183) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46715 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 183 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 184) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46725 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 184 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 185) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46735 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 185 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 186) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46745 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 186 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 187) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46755 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 187 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 188) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46765 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 188 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 189) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46775 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 189 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 190) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46785 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 190 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 191) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46795 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 191 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 192) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46805 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 192 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 193) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46815 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 193 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 194) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46825 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 194 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 195) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46835 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 195 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 196) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46845 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 196 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 197) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46855 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 197 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 198) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46865 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 198 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 199) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46875 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 199 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 200) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46885 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 200 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 201) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46895 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 201 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 202) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46905 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 202 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 203) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46915 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 203 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 204) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46925 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 204 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 205) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46935 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 205 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 206) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46945 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 206 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 207) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46955 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 207 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 208) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46965 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 208 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 209) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46975 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 209 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 210) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46985 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 210 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 211) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 46995 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 211 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 212) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47005 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 212 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 213) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47015 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 213 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 214) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47025 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 214 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 215) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47055 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 215 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 216) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47065 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 216 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 217) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47075 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 217 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 218) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47085 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 218 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 219) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47095 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 219 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 220) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47105 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 220 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 221) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47115 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 221 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 222) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47125 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 222 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 223) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47135 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 223 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 224) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47145 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 224 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 225) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47155 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 225 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 226) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47165 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 226 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 227) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47175 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 227 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 228) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47185 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 228 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 229) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47195 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 229 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 230) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47205 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 230 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 231) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47215 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 231 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 232) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47225 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 232 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 233) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47235 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 233 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 234) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47905 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 234 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 235) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47915 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 235 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 236) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47925 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 236 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 237) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47935 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 237 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 238) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47945 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 238 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 239) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47955 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 239 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 240) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47965 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 240 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 241) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47975 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 241 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 242) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47985 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 242 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 243) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47995 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 243 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 244) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 48005 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 244 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 245) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 55725 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 245 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 246) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 56175 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 246 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 247) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 56185 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 247 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 248) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 56195 });
		}
		if (changeState(state -> Move_Valencia_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Valencia_Position(blendTime), 5000 + Rnd.get(-500,500)));
	}

	protected void Move_Valencia_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7079BB02L /*Move_Valencia_Position*/);
		if(Rnd.getChance(10)) {
			if (changeState(state -> Move_Random_Valencia(blendTime)))
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
		}, onExit -> scheduleState(state -> Wait_2(blendTime), 8000)));
	}

	protected void Move_Random_Valencia(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xCA524852L /*Move_Random_Valencia*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 500, 700, true, ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> Wait_for_Talk_Valencia_Position(blendTime), 15000)));
	}

	protected void Wait_for_Talk_Valencia_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFBA03DF8L /*Wait_for_Talk_Valencia_Position*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Valencia_Position(blendTime), 10000));
	}

	protected void Select_Ankado_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3DE10A78L /*Select_Ankado_Position*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(315));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47205 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47245 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47255 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47265 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47275 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47285 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47295 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47305 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47315 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47325 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47335 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47345 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47355 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47365 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 14 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47375 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 15 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47385 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 16 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47395 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 17 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47405 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 18 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47415 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 19 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47425 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 20 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47435 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 21 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47445 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 22 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47455 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 23 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47465 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 24 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47475 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 25 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47485 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 26 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47495 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 27 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47505 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 28 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47515 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 29 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47525 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 30 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47535 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 31 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47545 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 32 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47555 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 33 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47565 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 34 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47575 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 35 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47585 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 36 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47595 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 37 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 38) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47605 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 38 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 39) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47615 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 39 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 40) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47625 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 40 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 41) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47635 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 41 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 42) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47645 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 42 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 43) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47655 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 43 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 44) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47665 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 44 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 45) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47675 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 45 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 46) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47685 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 46 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 47) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47695 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 47 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 48) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47705 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 48 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 49) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47715 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 49 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 50) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47725 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 50 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 51) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47735 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 51 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 52) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47745 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 52 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 53) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47755 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 53 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 54) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47765 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 54 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 55) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47775 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 55 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 56) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47785 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 56 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 57) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47795 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 57 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 58) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 47805 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 58 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 59) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 55565 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 59 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 60) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 2, 55575 });
		}
		if (changeState(state -> Move_Ankado_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Ankado_Position(blendTime), 5000 + Rnd.get(-500,500)));
	}

	protected void Move_Ankado_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF6545618L /*Move_Ankado_Position*/);
		if(Rnd.getChance(10)) {
			if (changeState(state -> Move_Random_Ankado(blendTime)))
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
		}, onExit -> scheduleState(state -> Wait_1(blendTime), 8000)));
	}

	protected void Move_Random_Ankado(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xAD0AFDF8L /*Move_Random_Ankado*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 500, 700, true, ENaviType.ground, () -> {
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
		}, onExit -> scheduleState(state -> Wait_for_Talk_Ankado_Position(blendTime), 15000)));
	}

	protected void Wait_for_Talk_Ankado_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAC7969BDL /*Wait_for_Talk_Ankado_Position*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Ankado_Position(blendTime), 10000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
