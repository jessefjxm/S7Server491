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
@IAIName("haseragate")
public class Ai_haseragate extends CreatureAI {
	public Ai_haseragate(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xBF12F1B8L /*_GroupNo*/, getVariable(0xCE1E39D5L /*AI_GroupNo*/));
		setVariable(0xAA320A99L /*_DiceCount*/, 0);
		setVariable(0x34B608CEL /*_TeleportReady_StartTime*/, 0);
		setVariable(0xD9E7438DL /*_TeleportReady_IngTime*/, 0);
		setVariable(0xE6EB1712L /*_TeleportReady_EndTime*/, 0);
		setVariable(0x34B608CEL /*_TeleportReady_StartTime*/, getTime());
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Logic(blendTime), 100));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Logic(blendTime), 100));
	}

	protected void Teleport_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA5BDA01CL /*Teleport_Logic*/);
		setVariable(0x34B608CEL /*_TeleportReady_StartTime*/, getTime());
		setVariable(0xAA320A99L /*_DiceCount*/, getRandom(100));
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group1_1(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group1_2(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group1_3(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group1_4(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 80) {
			if (changeState(state -> TeleportPosition_Group1_5(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group2_1(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group2_2(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group2_3(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group2_4(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 80) {
			if (changeState(state -> TeleportPosition_Group2_5(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group3_1(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group3_2(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group3_3(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group3_4(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 80) {
			if (changeState(state -> TeleportPosition_Group3_5(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group4_1(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group4_2(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group4_3(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group4_4(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 80) {
			if (changeState(state -> TeleportPosition_Group4_5(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group5_1(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group5_2(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group5_3(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group5_4(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 80) {
			if (changeState(state -> TeleportPosition_Group5_5(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group6_1(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group6_2(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group6_3(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group6_4(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 80) {
			if (changeState(state -> TeleportPosition_Group6_5(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group7_1(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group7_2(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group7_3(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group7_4(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 80) {
			if (changeState(state -> TeleportPosition_Group7_5(blendTime)))
				return;
		}
		changeState(state -> Teleport_Logic(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xD9E7438DL /*_TeleportReady_IngTime*/, getTime());
		setVariable(0xE6EB1712L /*_TeleportReady_EndTime*/, getVariable(0xD9E7438DL /*_TeleportReady_IngTime*/) - getVariable(0x34B608CEL /*_TeleportReady_StartTime*/));
		if (getVariable(0xE6EB1712L /*_TeleportReady_EndTime*/) > 3600000) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Enemy, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 300)) {
			if (changeState(state -> Pull_Attack(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Pull_Attack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xE72BEEDFL /*Pull_Attack*/);
		setVariable(0xD9E7438DL /*_TeleportReady_IngTime*/, getTime());
		setVariable(0xE6EB1712L /*_TeleportReady_EndTime*/, getVariable(0xD9E7438DL /*_TeleportReady_IngTime*/) - getVariable(0x34B608CEL /*_TeleportReady_StartTime*/));
		if (getVariable(0xE6EB1712L /*_TeleportReady_EndTime*/) > 3600000) {
			if (changeState(state -> Die(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 300) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(3111235679L /*PULL_ATTACK*/, blendTime, onDoActionEnd -> changeState(state -> Pull_Attack(blendTime)));
	}

	protected void Teleport_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCAF8E69L /*Teleport_Wait*/);
		doAction(713040502L /*TELEPORT_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Logic(blendTime), 1000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	protected void TeleportPosition_Group1_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE2B49D4BL /*TeleportPosition_Group1_1*/);
		doTeleportToWaypoint("desertgate_01", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group1_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2C008D48L /*TeleportPosition_Group1_2*/);
		doTeleportToWaypoint("desertgate_02", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group1_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x287E025DL /*TeleportPosition_Group1_3*/);
		doTeleportToWaypoint("desertgate_03", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group1_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x33B10812L /*TeleportPosition_Group1_4*/);
		doTeleportToWaypoint("desertgate_04", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group1_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB74DD273L /*TeleportPosition_Group1_5*/);
		doTeleportToWaypoint("desertgate_05", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group2_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8ED46819L /*TeleportPosition_Group2_1*/);
		doTeleportToWaypoint("desertgate_06", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group2_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x34241D83L /*TeleportPosition_Group2_2*/);
		doTeleportToWaypoint("desertgate_07", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group2_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x381C55C4L /*TeleportPosition_Group2_3*/);
		doTeleportToWaypoint("desertgate_08", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group2_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4A2352A6L /*TeleportPosition_Group2_4*/);
		doTeleportToWaypoint("desertgate_09", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group2_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x729EC90CL /*TeleportPosition_Group2_5*/);
		doTeleportToWaypoint("desertgate_10", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group3_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x17EC9FB1L /*TeleportPosition_Group3_1*/);
		doTeleportToWaypoint("desertgate_11", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group3_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1B3652CCL /*TeleportPosition_Group3_2*/);
		doTeleportToWaypoint("desertgate_12", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group3_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x357D0023L /*TeleportPosition_Group3_3*/);
		doTeleportToWaypoint("desertgate_13", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group3_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF232F16EL /*TeleportPosition_Group3_4*/);
		doTeleportToWaypoint("desertgate_14", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group3_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF39D29ECL /*TeleportPosition_Group3_5*/);
		doTeleportToWaypoint("desertgate_15", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group4_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x41E6D7CDL /*TeleportPosition_Group4_1*/);
		doTeleportToWaypoint("desertgate_16", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group4_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xAD155363L /*TeleportPosition_Group4_2*/);
		doTeleportToWaypoint("desertgate_17", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group4_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7B8E557AL /*TeleportPosition_Group4_3*/);
		doTeleportToWaypoint("desertgate_18", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group4_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x24D1001L /*TeleportPosition_Group4_4*/);
		doTeleportToWaypoint("desertgate_19", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group4_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB0CB7F65L /*TeleportPosition_Group4_5*/);
		doTeleportToWaypoint("desertgate_20", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group5_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xEE6A3EC1L /*TeleportPosition_Group5_1*/);
		doTeleportToWaypoint("desertgate_21", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group5_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x589B2DE7L /*TeleportPosition_Group5_2*/);
		doTeleportToWaypoint("desertgate_22", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group5_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1099AFAEL /*TeleportPosition_Group5_3*/);
		doTeleportToWaypoint("desertgate_23", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group5_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9B19FF71L /*TeleportPosition_Group5_4*/);
		doTeleportToWaypoint("desertgate_24", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group5_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA6F77111L /*TeleportPosition_Group5_5*/);
		doTeleportToWaypoint("desertgate_25", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group6_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDF195006L /*TeleportPosition_Group6_1*/);
		doTeleportToWaypoint("desertgate_26", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group6_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB4A9A549L /*TeleportPosition_Group6_2*/);
		doTeleportToWaypoint("desertgate_27", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group6_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3C486FD2L /*TeleportPosition_Group6_3*/);
		doTeleportToWaypoint("desertgate_28", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group6_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC25E5338L /*TeleportPosition_Group6_4*/);
		doTeleportToWaypoint("desertgate_29", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group6_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB8C32168L /*TeleportPosition_Group6_5*/);
		doTeleportToWaypoint("desertgate_30", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group7_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1A97FF39L /*TeleportPosition_Group7_1*/);
		doTeleportToWaypoint("desertgate_31", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group7_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC87E509EL /*TeleportPosition_Group7_2*/);
		doTeleportToWaypoint("desertgate_32", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group7_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8BF1F143L /*TeleportPosition_Group7_3*/);
		doTeleportToWaypoint("desertgate_33", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group7_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4567716BL /*TeleportPosition_Group7_4*/);
		doTeleportToWaypoint("desertgate_34", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	protected void TeleportPosition_Group7_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3986FC57L /*TeleportPosition_Group7_5*/);
		doTeleportToWaypoint("desertgate_35", "teleport", 0, 0, 1, 1);
		changeState(state -> Wait(blendTime));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
