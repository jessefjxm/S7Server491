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
@IAIName("goldenchest2")
public class Ai_goldenchest2 extends CreatureAI {
	public Ai_goldenchest2(Creature actor, Map<Long, Integer> aiVariables) {
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
		doAction(1096688925L /*HIDE*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Logic(blendTime), 100));
	}

	protected void Teleport_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA5BDA01CL /*Teleport_Logic*/);
		setVariable(0x34B608CEL /*_TeleportReady_StartTime*/, getTime());
		setVariable(0xAA320A99L /*_DiceCount*/, getRandom(100));
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group1_01(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 30) {
			if (changeState(state -> TeleportPosition_Group1_02(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 30 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group1_03(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 50) {
			if (changeState(state -> TeleportPosition_Group1_04(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 50 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group1_05(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 70) {
			if (changeState(state -> TeleportPosition_Group1_06(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 70 && getVariable(0xAA320A99L /*_DiceCount*/) <= 90) {
			if (changeState(state -> TeleportPosition_Group1_07(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 90 && getVariable(0xAA320A99L /*_DiceCount*/) <= 100) {
			if (changeState(state -> TeleportPosition_Group1_08(blendTime)))
				return;
		}
		changeState(state -> Teleport_Logic(blendTime));
	}

	protected void Find_Player_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5DC7DEA1L /*Find_Player_Logic*/);
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object) <= 6000)) {
			if (changeState(state -> Hide(blendTime)))
				return;
		}
		changeState(state -> Wait(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		setVariable(0xD9E7438DL /*_TeleportReady_IngTime*/, getTime());
		setVariable(0xE6EB1712L /*_TeleportReady_EndTime*/, getVariable(0xD9E7438DL /*_TeleportReady_IngTime*/) - getVariable(0x34B608CEL /*_TeleportReady_StartTime*/));
		if (getVariable(0xE6EB1712L /*_TeleportReady_EndTime*/) > 3600000) {
			if (changeState(state -> Teleport_Logic(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Hide(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6D61FF50L /*Hide*/);
		setVariable(0xD9E7438DL /*_TeleportReady_IngTime*/, getTime());
		setVariable(0xE6EB1712L /*_TeleportReady_EndTime*/, getVariable(0xD9E7438DL /*_TeleportReady_IngTime*/) - getVariable(0x34B608CEL /*_TeleportReady_StartTime*/));
		if (getVariable(0xE6EB1712L /*_TeleportReady_EndTime*/) > 3600000) {
			if (changeState(state -> Teleport_Logic(blendTime)))
				return;
		}
		if (target != null && getTargetHp(target) == 0) {
			if (changeState(state -> Find_Player_Logic(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 6000) {
			if (changeState(state -> Find_Player_Logic(blendTime)))
				return;
		}
		doAction(1096688925L /*HIDE*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Logic(blendTime), 1000));
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

	protected void Chest_open(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE6D8715FL /*Chest_open*/);
		doAction(2609068454L /*OPEN_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 3600));
	}

	protected void Suicide_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x2BD8C797L /*Suicide_Die*/);
		doAction(2707805806L /*SUICIDE_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 1000));
	}

	protected void TeleportPosition_Group1_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xED485FBDL /*TeleportPosition_Group1_01*/);
		doTeleportToWaypoint("goldedchest_boss01", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC6D97BCL /*TeleportPosition_Group1_02*/);
		doTeleportToWaypoint("goldedchest_boss02", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDA524AD5L /*TeleportPosition_Group1_03*/);
		doTeleportToWaypoint("goldedchest_boss03", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x88DC0404L /*TeleportPosition_Group1_04*/);
		doTeleportToWaypoint("goldedchest_boss04", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3D9FECE2L /*TeleportPosition_Group1_05*/);
		doTeleportToWaypoint("goldedchest_boss05", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7F080E98L /*TeleportPosition_Group1_06*/);
		doTeleportToWaypoint("goldedchest_boss06", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD813DE15L /*TeleportPosition_Group1_07*/);
		doTeleportToWaypoint("goldedchest_boss07", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x513AD1B9L /*TeleportPosition_Group1_08*/);
		doTeleportToWaypoint("goldedchest_boss08", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
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
		if (getState() != 0xE6D8715FL /*Chest_open*/) {
			if (changeState(state -> Chest_open(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}
}
