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
@IAIName("npc_altioutside_a_roaming")
public class Ai_npc_altioutside_a_roaming extends CreatureAI {
	public Ai_npc_altioutside_a_roaming(Creature actor, Map<Long, Integer> aiVariables) {
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
			if (changeState(state -> Select_TarifTown_Position(blendTime)))
				return;
		}
		if (changeState(state -> Select_Abun_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 10000));
	}

	protected void Wait_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x96AB8779L /*Wait_1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_TarifTown_Position(blendTime), 10000));
	}

	protected void Wait_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD6E9C46CL /*Wait_2*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Abun_Position(blendTime), 10000));
	}

	protected void Select_TarifTown_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x82E07296L /*Select_TarifTown_Position*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(60));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 26342 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 26343 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29721 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29732 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29733 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29740 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29734 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29724 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29741 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29715 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29727 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29719 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29722 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29729 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 14 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29737 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 15 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29713 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 16 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29730 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 17 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29723 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 18 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29725 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 19 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29728 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 20 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29735 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 21 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29718 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 22 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29720 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 23 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29714 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 24 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29739 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 25 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29726 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 26 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29716 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 27 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29717 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 28 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29736 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 29 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29738 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 30 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 29731 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 31 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 32) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20292 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 32 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 33) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20287 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 33 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 34) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20289 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 34 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 35) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20295 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 35 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 36) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20296 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 36 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 37) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20286 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 37 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 38) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20288 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 38 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 39) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20284 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 39 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 40) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20290 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 40 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 41) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20285 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 41 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 42) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20291 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 42 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 43) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20294 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 43 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 44) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20293 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 44 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 45) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20271 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 45 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 46) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20273 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 46 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 47) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20277 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 47 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 48) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20279 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 48 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 49) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20269 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 49 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 50) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20274 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 50 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 51) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20275 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 51 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 52) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20281 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 52 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 53) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20282 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 53 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 54) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20272 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 54 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 55) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20276 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 55 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 56) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20280 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 56 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 57) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20283 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 57 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 58) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20270 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 58 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 59) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20278 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 59 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 60) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20268 });
		}
		if (changeState(state -> Move_TarifTown_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_TarifTown_Position(blendTime), 5000 + Rnd.get(-500,500)));
	}

	protected void Move_TarifTown_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8438A882L /*Move_TarifTown_Position*/);
		if(Rnd.getChance(10)) {
			if (changeState(state -> Move_Random_TarifTown(blendTime)))
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

	protected void Move_Random_TarifTown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x895FF33L /*Move_Random_TarifTown*/);
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
		}, onExit -> scheduleState(state -> Wait_for_Talk_TarifTown_Position(blendTime), 15000)));
	}

	protected void Wait_for_Talk_TarifTown_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBE0A5F7EL /*Wait_for_Talk_TarifTown_Position*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_TarifTown_Position(blendTime), 10000));
	}

	protected void Select_Abun_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE26652FBL /*Select_Abun_Position*/);
		setVariable(0x2377F21EL /*_Waypointpositoin*/, getRandom(31));
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 1) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20703 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 1 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 2) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20704 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 2 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 3) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20705 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 3 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 4) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20706 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 4 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 5) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20707 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 5 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 6) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20708 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 6 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 7) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20709 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 7 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 8) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20710 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 8 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 9) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20711 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 9 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 10) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20712 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 10 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 11) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20713 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 11 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 12) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20714 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 12 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 13) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20715 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 13 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 14) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20716 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 14 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 15) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20717 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 15 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 16) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20718 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 16 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 17) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20719 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 17 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 18) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20720 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 18 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 19) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20721 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 19 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 20) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 20722 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 20 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 21) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31141 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 21 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 22) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31142 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 22 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 23) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31143 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 23 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 24) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31144 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 24 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 25) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31145 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 25 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 26) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31146 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 26 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 27) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31147 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 27 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 28) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31148 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 28 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 29) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31149 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 29 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 30) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31150 });
		}
		if (getVariable(0x2377F21EL /*_Waypointpositoin*/) > 30 && getVariable(0x2377F21EL /*_Waypointpositoin*/) <= 31) {
			setVariableArray(0xACD660AFL /*WaypointValue*/, new Integer[] { 1, 31151 });
		}
		if (changeState(state -> Move_Abun_Position(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Select_Abun_Position(blendTime), 5000 + Rnd.get(-500,500)));
	}

	protected void Move_Abun_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x80F2D3E2L /*Move_Abun_Position*/);
		if(Rnd.getChance(10)) {
			if (changeState(state -> Move_Random_Abun(blendTime)))
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

	protected void Move_Random_Abun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB2E2DE45L /*Move_Random_Abun*/);
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
		}, onExit -> scheduleState(state -> Wait_for_Talk_Abun_Position(blendTime), 15000)));
	}

	protected void Wait_for_Talk_Abun_Position(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6622242L /*Wait_for_Talk_Abun_Position*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Abun_Position(blendTime), 10000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
