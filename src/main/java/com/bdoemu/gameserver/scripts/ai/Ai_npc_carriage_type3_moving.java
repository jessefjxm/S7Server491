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
@IAIName("npc_carriage_type3_moving")
public class Ai_npc_carriage_type3_moving extends CreatureAI {
	public Ai_npc_carriage_type3_moving(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 4) {
			if (changeState(state -> Type1_Go(blendTime)))
				return;
		}
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 5) {
			if (changeState(state -> Type2_Go(blendTime)))
				return;
		}
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 6) {
			if (changeState(state -> Type3_Go(blendTime)))
				return;
		}
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 7) {
			if (changeState(state -> Type4_Go(blendTime)))
				return;
		}
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 8) {
			if (changeState(state -> Type5_Go(blendTime)))
				return;
		}
		if (getVariable(0x13BAEE6EL /*SelectRoute*/) == 9) {
			if (changeState(state -> Idx9_Go(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 300 + Rnd.get(-300,300)));
	}

	protected void Type1_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x32EFC16BL /*Type1_Go*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type1_Go_1(blendTime)))
				return;
		}
		doAction(3073053914L /*WALK_STEP1*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_01_1", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Go1(blendTime), 1000)));
	}

	protected void Type1_Go_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBCCFE7DL /*Type1_Go_1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type1_Go_2(blendTime)))
				return;
		}
		doAction(1710155101L /*WALK_STEP2*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_01_1", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Go1(blendTime), 1000)));
	}

	protected void Type1_Go_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE9C2B05AL /*Type1_Go_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_01_1", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Go1(blendTime), 1000)));
	}

	protected void Type1_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA574EF37L /*Type1_Go1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type1_Go1_1(blendTime)))
				return;
		}
		doAction(3073053914L /*WALK_STEP1*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_01_27", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Go2(blendTime), 1000)));
	}

	protected void Type1_Go1_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5A7DFA1EL /*Type1_Go1_1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type1_Go1_2(blendTime)))
				return;
		}
		doAction(1710155101L /*WALK_STEP2*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_01_27", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Go2(blendTime), 1000)));
	}

	protected void Type1_Go1_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x54958F3BL /*Type1_Go1_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_01_27", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Go2(blendTime), 1000)));
	}

	protected void Type1_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC4166300L /*Type1_Go2*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type1_Go2_1(blendTime)))
				return;
		}
		doAction(3073053914L /*WALK_STEP1*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_01_65", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Wait(blendTime), 1000)));
	}

	protected void Type1_Go2_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEC1B7540L /*Type1_Go2_1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type1_Go2_2(blendTime)))
				return;
		}
		doAction(1710155101L /*WALK_STEP2*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_01_65", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Wait(blendTime), 1000)));
	}

	protected void Type1_Go2_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD63F9BABL /*Type1_Go2_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_01_65", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type1_Wait(blendTime), 1000)));
	}

	protected void Type1_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7D88BE79L /*Type1_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type1_Go(blendTime), 10000));
	}

	protected void Type2_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8E38CD77L /*Type2_Go*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_02_1", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Go1(blendTime), 1000)));
	}

	protected void Type2_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6EC8E369L /*Type2_Go1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_02_26", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Go2(blendTime), 1000)));
	}

	protected void Type2_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5DDDA157L /*Type2_Go2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_02_58", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Go3(blendTime), 1000)));
	}

	protected void Type2_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA4974119L /*Type2_Go3*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_02_82", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Go(blendTime), 1000)));
	}

	protected void Type3_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xDB51A9DBL /*Type3_Go*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_03_1", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type3_Go1(blendTime), 1000)));
	}

	protected void Type3_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x327FB836L /*Type3_Go1*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_03_24", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type3_Go2(blendTime), 1000)));
	}

	protected void Type3_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x14A88AFDL /*Type3_Go2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_03_47", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type3_Go3(blendTime), 1000)));
	}

	protected void Type3_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x964940E4L /*Type3_Go3*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_03_79", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type3_Go(blendTime), 1000)));
	}

	protected void Type4_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB2580A99L /*Type4_Go*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type4_Go_1(blendTime)))
				return;
		}
		doAction(3073053914L /*WALK_STEP1*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_04_2", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Wait(blendTime), 1000)));
	}

	protected void Type4_Go_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x4F007B1AL /*Type4_Go_1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type4_Go_2(blendTime)))
				return;
		}
		doAction(1710155101L /*WALK_STEP2*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_04_2", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Wait(blendTime), 1000)));
	}

	protected void Type4_Go_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEE28F4B1L /*Type4_Go_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_04_2", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Wait(blendTime), 1000)));
	}

	protected void Type4_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x89162190L /*Type4_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type4_Go1(blendTime), 10000));
	}

	protected void Type4_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x749A6DF3L /*Type4_Go1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type4_Go1_1(blendTime)))
				return;
		}
		doAction(3073053914L /*WALK_STEP1*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_04_49", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go2(blendTime), 1000)));
	}

	protected void Type4_Go1_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2B06DAC1L /*Type4_Go1_1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type4_Go1_2(blendTime)))
				return;
		}
		doAction(1710155101L /*WALK_STEP2*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_04_49", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go2(blendTime), 1000)));
	}

	protected void Type4_Go1_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA4D58647L /*Type4_Go1_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_04_49", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go2(blendTime), 1000)));
	}

	protected void Type4_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3A4B3185L /*Type4_Go2*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type4_Go2_1(blendTime)))
				return;
		}
		doAction(3073053914L /*WALK_STEP1*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_04_100", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go3(blendTime), 1000)));
	}

	protected void Type4_Go2_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x23CB5CB6L /*Type4_Go2_1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type4_Go2_2(blendTime)))
				return;
		}
		doAction(1710155101L /*WALK_STEP2*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_04_100", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go3(blendTime), 1000)));
	}

	protected void Type4_Go2_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xEEC4D712L /*Type4_Go2_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_04_100", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go3(blendTime), 1000)));
	}

	protected void Type4_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7C9472F0L /*Type4_Go3*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type4_Go3_1(blendTime)))
				return;
		}
		doAction(3073053914L /*WALK_STEP1*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_04_151", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go(blendTime), 1000)));
	}

	protected void Type4_Go3_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x34852F82L /*Type4_Go3_1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type4_Go3_2(blendTime)))
				return;
		}
		doAction(1710155101L /*WALK_STEP2*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_04_151", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go(blendTime), 1000)));
	}

	protected void Type4_Go3_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA4ED7FC2L /*Type4_Go3_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "NorthGreatFarm_04_151", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type4_Go(blendTime), 1000)));
	}

	protected void Type5_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7B07709CL /*Type5_Go*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type5_Go_1(blendTime)))
				return;
		}
		doAction(3073053914L /*WALK_STEP1*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_SWCalpheon_to_Keplan_0170", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type5_Wait(blendTime), 1000)));
	}

	protected void Type5_Go_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA4493903L /*Type5_Go_1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type5_Go_2(blendTime)))
				return;
		}
		doAction(1710155101L /*WALK_STEP2*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_SWCalpheon_to_Keplan_0170", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type5_Wait(blendTime), 1000)));
	}

	protected void Type5_Go_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x531A1F91L /*Type5_Go_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_SWCalpheon_to_Keplan_0170", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type5_Wait(blendTime), 1000)));
	}

	protected void Type5_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8A9501C9L /*Type5_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type5_Go1(blendTime), 10000));
	}

	protected void Type5_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xD0249134L /*Type5_Go1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type5_Go1_1(blendTime)))
				return;
		}
		doAction(3073053914L /*WALK_STEP1*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_SWCalpheon_to_Keplan_0001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type5_Wait1(blendTime), 1000)));
	}

	protected void Type5_Go1_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB5C4BF38L /*Type5_Go1_1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Type5_Go1_2(blendTime)))
				return;
		}
		doAction(1710155101L /*WALK_STEP2*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_SWCalpheon_to_Keplan_0001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type5_Wait1(blendTime), 1000)));
	}

	protected void Type5_Go1_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x7D2D1CEDL /*Type5_Go1_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Wagon_SWCalpheon_to_Keplan_0001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type5_Wait1(blendTime), 1000)));
	}

	protected void Type5_Wait1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x54478EF1L /*Type5_Wait1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type5_Go(blendTime), 10000));
	}

	protected void Idx9_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x4E748829L /*Idx9_Go*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Idx9_Go_1(blendTime)))
				return;
		}
		doAction(2491589624L /*WALK_WITH_STUFF_STEP1*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Garrison_Supply_092", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Idx9_Clear_Stuff(blendTime), 1000)));
	}

	protected void Idx9_Go_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x3E2031B1L /*Idx9_Go_1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Idx9_Go_2(blendTime)))
				return;
		}
		doAction(3590249052L /*WALK_WITH_STUFF_STEP2*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Garrison_Supply_092", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Idx9_Clear_Stuff(blendTime), 1000)));
	}

	protected void Idx9_Go_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xB3FC642EL /*Idx9_Go_2*/);
		doAction(629689558L /*WALK_WITH_STUFF*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Garrison_Supply_092", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Idx9_Clear_Stuff(blendTime), 1000)));
	}

	protected void Idx9_Clear_Stuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFA850FB6L /*Idx9_Clear_Stuff*/);
		doAction(2849376549L /*CLEAR_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Idx9_GoBack(blendTime), 10000));
	}

	protected void Idx9_GoBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x10A253CDL /*Idx9_GoBack*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Idx9_GoBack_1(blendTime)))
				return;
		}
		doAction(3073053914L /*WALK_STEP1*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Garrison_Supply_030", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Idx9_Load_Stuff(blendTime), 1000)));
	}

	protected void Idx9_GoBack_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8EDBDC8BL /*Idx9_GoBack_1*/);
		if(getCallCount() == 5) {
			if (changeState(state -> Idx9_GoBack_2(blendTime)))
				return;
		}
		doAction(1710155101L /*WALK_STEP2*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Garrison_Supply_030", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Idx9_Load_Stuff(blendTime), 1000)));
	}

	protected void Idx9_GoBack_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x10C567F8L /*Idx9_GoBack_2*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("carriage", "Garrison_Supply_030", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Idx9_Load_Stuff(blendTime), 1000)));
	}

	protected void Idx9_Load_Stuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBAA1149FL /*Idx9_Load_Stuff*/);
		doAction(2453627260L /*LOAD_STUFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Idx9_Go(blendTime), 10000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
