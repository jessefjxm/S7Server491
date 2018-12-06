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
@IAIName("goldenchest")
public class Ai_goldenchest extends CreatureAI {
	public Ai_goldenchest(Creature actor, Map<Long, Integer> aiVariables) {
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
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) <= 3) {
			if (changeState(state -> TeleportPosition_Group1_01(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 3 && getVariable(0xAA320A99L /*_DiceCount*/) <= 6) {
			if (changeState(state -> TeleportPosition_Group1_02(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 6 && getVariable(0xAA320A99L /*_DiceCount*/) <= 9) {
			if (changeState(state -> TeleportPosition_Group1_03(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 9 && getVariable(0xAA320A99L /*_DiceCount*/) <= 12) {
			if (changeState(state -> TeleportPosition_Group1_04(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 12 && getVariable(0xAA320A99L /*_DiceCount*/) <= 15) {
			if (changeState(state -> TeleportPosition_Group1_05(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 15 && getVariable(0xAA320A99L /*_DiceCount*/) <= 18) {
			if (changeState(state -> TeleportPosition_Group1_06(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 18 && getVariable(0xAA320A99L /*_DiceCount*/) <= 21) {
			if (changeState(state -> TeleportPosition_Group1_07(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 21 && getVariable(0xAA320A99L /*_DiceCount*/) <= 24) {
			if (changeState(state -> TeleportPosition_Group1_08(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 24 && getVariable(0xAA320A99L /*_DiceCount*/) <= 27) {
			if (changeState(state -> TeleportPosition_Group1_09(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 27 && getVariable(0xAA320A99L /*_DiceCount*/) <= 30) {
			if (changeState(state -> TeleportPosition_Group1_10(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 30 && getVariable(0xAA320A99L /*_DiceCount*/) <= 33) {
			if (changeState(state -> TeleportPosition_Group1_11(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 33 && getVariable(0xAA320A99L /*_DiceCount*/) <= 36) {
			if (changeState(state -> TeleportPosition_Group1_12(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 36 && getVariable(0xAA320A99L /*_DiceCount*/) <= 39) {
			if (changeState(state -> TeleportPosition_Group1_13(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 39 && getVariable(0xAA320A99L /*_DiceCount*/) <= 42) {
			if (changeState(state -> TeleportPosition_Group1_14(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 42 && getVariable(0xAA320A99L /*_DiceCount*/) <= 45) {
			if (changeState(state -> TeleportPosition_Group1_15(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 45 && getVariable(0xAA320A99L /*_DiceCount*/) <= 48) {
			if (changeState(state -> TeleportPosition_Group1_16(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 48 && getVariable(0xAA320A99L /*_DiceCount*/) <= 51) {
			if (changeState(state -> TeleportPosition_Group1_17(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 51 && getVariable(0xAA320A99L /*_DiceCount*/) <= 54) {
			if (changeState(state -> TeleportPosition_Group1_18(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 54 && getVariable(0xAA320A99L /*_DiceCount*/) <= 57) {
			if (changeState(state -> TeleportPosition_Group1_19(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 57 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group1_20(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 64) {
			if (changeState(state -> TeleportPosition_Group1_21(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 64 && getVariable(0xAA320A99L /*_DiceCount*/) <= 68) {
			if (changeState(state -> TeleportPosition_Group1_22(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 68 && getVariable(0xAA320A99L /*_DiceCount*/) <= 72) {
			if (changeState(state -> TeleportPosition_Group1_23(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 72 && getVariable(0xAA320A99L /*_DiceCount*/) <= 76) {
			if (changeState(state -> TeleportPosition_Group1_24(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 76 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group1_25(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 80 && getVariable(0xAA320A99L /*_DiceCount*/) <= 84) {
			if (changeState(state -> TeleportPosition_Group1_26(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 84 && getVariable(0xAA320A99L /*_DiceCount*/) <= 88) {
			if (changeState(state -> TeleportPosition_Group1_27(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 88 && getVariable(0xAA320A99L /*_DiceCount*/) <= 92) {
			if (changeState(state -> TeleportPosition_Group1_28(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 92 && getVariable(0xAA320A99L /*_DiceCount*/) <= 96) {
			if (changeState(state -> TeleportPosition_Group1_29(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 1 && getVariable(0xAA320A99L /*_DiceCount*/) > 96) {
			if (changeState(state -> TeleportPosition_Group1_30(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) <= 3) {
			if (changeState(state -> TeleportPosition_Group2_01(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 3 && getVariable(0xAA320A99L /*_DiceCount*/) <= 6) {
			if (changeState(state -> TeleportPosition_Group2_02(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 6 && getVariable(0xAA320A99L /*_DiceCount*/) <= 9) {
			if (changeState(state -> TeleportPosition_Group2_03(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 9 && getVariable(0xAA320A99L /*_DiceCount*/) <= 12) {
			if (changeState(state -> TeleportPosition_Group2_04(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 12 && getVariable(0xAA320A99L /*_DiceCount*/) <= 15) {
			if (changeState(state -> TeleportPosition_Group2_05(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 15 && getVariable(0xAA320A99L /*_DiceCount*/) <= 18) {
			if (changeState(state -> TeleportPosition_Group2_06(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 18 && getVariable(0xAA320A99L /*_DiceCount*/) <= 21) {
			if (changeState(state -> TeleportPosition_Group2_07(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 21 && getVariable(0xAA320A99L /*_DiceCount*/) <= 24) {
			if (changeState(state -> TeleportPosition_Group2_08(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 24 && getVariable(0xAA320A99L /*_DiceCount*/) <= 27) {
			if (changeState(state -> TeleportPosition_Group2_09(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 27 && getVariable(0xAA320A99L /*_DiceCount*/) <= 30) {
			if (changeState(state -> TeleportPosition_Group2_10(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 30 && getVariable(0xAA320A99L /*_DiceCount*/) <= 33) {
			if (changeState(state -> TeleportPosition_Group2_11(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 33 && getVariable(0xAA320A99L /*_DiceCount*/) <= 36) {
			if (changeState(state -> TeleportPosition_Group2_12(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 36 && getVariable(0xAA320A99L /*_DiceCount*/) <= 39) {
			if (changeState(state -> TeleportPosition_Group2_13(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 39 && getVariable(0xAA320A99L /*_DiceCount*/) <= 42) {
			if (changeState(state -> TeleportPosition_Group2_14(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 42 && getVariable(0xAA320A99L /*_DiceCount*/) <= 45) {
			if (changeState(state -> TeleportPosition_Group2_15(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 45 && getVariable(0xAA320A99L /*_DiceCount*/) <= 48) {
			if (changeState(state -> TeleportPosition_Group2_16(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 48 && getVariable(0xAA320A99L /*_DiceCount*/) <= 51) {
			if (changeState(state -> TeleportPosition_Group2_17(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 51 && getVariable(0xAA320A99L /*_DiceCount*/) <= 54) {
			if (changeState(state -> TeleportPosition_Group2_18(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 54 && getVariable(0xAA320A99L /*_DiceCount*/) <= 57) {
			if (changeState(state -> TeleportPosition_Group2_19(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 57 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group2_20(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 64) {
			if (changeState(state -> TeleportPosition_Group2_21(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 64 && getVariable(0xAA320A99L /*_DiceCount*/) <= 68) {
			if (changeState(state -> TeleportPosition_Group2_22(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 68 && getVariable(0xAA320A99L /*_DiceCount*/) <= 72) {
			if (changeState(state -> TeleportPosition_Group2_23(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 72 && getVariable(0xAA320A99L /*_DiceCount*/) <= 76) {
			if (changeState(state -> TeleportPosition_Group2_24(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 76 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group2_25(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 80 && getVariable(0xAA320A99L /*_DiceCount*/) <= 84) {
			if (changeState(state -> TeleportPosition_Group2_26(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 84 && getVariable(0xAA320A99L /*_DiceCount*/) <= 88) {
			if (changeState(state -> TeleportPosition_Group2_27(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 88 && getVariable(0xAA320A99L /*_DiceCount*/) <= 92) {
			if (changeState(state -> TeleportPosition_Group2_28(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 92 && getVariable(0xAA320A99L /*_DiceCount*/) <= 96) {
			if (changeState(state -> TeleportPosition_Group2_29(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 2 && getVariable(0xAA320A99L /*_DiceCount*/) > 96) {
			if (changeState(state -> TeleportPosition_Group2_30(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) <= 3) {
			if (changeState(state -> TeleportPosition_Group3_01(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 3 && getVariable(0xAA320A99L /*_DiceCount*/) <= 6) {
			if (changeState(state -> TeleportPosition_Group3_02(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 6 && getVariable(0xAA320A99L /*_DiceCount*/) <= 9) {
			if (changeState(state -> TeleportPosition_Group3_03(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 9 && getVariable(0xAA320A99L /*_DiceCount*/) <= 12) {
			if (changeState(state -> TeleportPosition_Group3_04(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 12 && getVariable(0xAA320A99L /*_DiceCount*/) <= 15) {
			if (changeState(state -> TeleportPosition_Group3_05(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 15 && getVariable(0xAA320A99L /*_DiceCount*/) <= 18) {
			if (changeState(state -> TeleportPosition_Group3_06(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 18 && getVariable(0xAA320A99L /*_DiceCount*/) <= 21) {
			if (changeState(state -> TeleportPosition_Group3_07(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 21 && getVariable(0xAA320A99L /*_DiceCount*/) <= 24) {
			if (changeState(state -> TeleportPosition_Group3_08(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 24 && getVariable(0xAA320A99L /*_DiceCount*/) <= 27) {
			if (changeState(state -> TeleportPosition_Group3_09(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 27 && getVariable(0xAA320A99L /*_DiceCount*/) <= 30) {
			if (changeState(state -> TeleportPosition_Group3_10(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 30 && getVariable(0xAA320A99L /*_DiceCount*/) <= 33) {
			if (changeState(state -> TeleportPosition_Group3_11(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 33 && getVariable(0xAA320A99L /*_DiceCount*/) <= 36) {
			if (changeState(state -> TeleportPosition_Group3_12(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 36 && getVariable(0xAA320A99L /*_DiceCount*/) <= 39) {
			if (changeState(state -> TeleportPosition_Group3_13(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 39 && getVariable(0xAA320A99L /*_DiceCount*/) <= 42) {
			if (changeState(state -> TeleportPosition_Group3_14(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 42 && getVariable(0xAA320A99L /*_DiceCount*/) <= 45) {
			if (changeState(state -> TeleportPosition_Group3_15(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 45 && getVariable(0xAA320A99L /*_DiceCount*/) <= 48) {
			if (changeState(state -> TeleportPosition_Group3_16(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 48 && getVariable(0xAA320A99L /*_DiceCount*/) <= 51) {
			if (changeState(state -> TeleportPosition_Group3_17(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 51 && getVariable(0xAA320A99L /*_DiceCount*/) <= 54) {
			if (changeState(state -> TeleportPosition_Group3_18(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 54 && getVariable(0xAA320A99L /*_DiceCount*/) <= 57) {
			if (changeState(state -> TeleportPosition_Group3_19(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 57 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group3_20(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 64) {
			if (changeState(state -> TeleportPosition_Group3_21(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 64 && getVariable(0xAA320A99L /*_DiceCount*/) <= 68) {
			if (changeState(state -> TeleportPosition_Group3_22(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 68 && getVariable(0xAA320A99L /*_DiceCount*/) <= 72) {
			if (changeState(state -> TeleportPosition_Group3_23(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 72 && getVariable(0xAA320A99L /*_DiceCount*/) <= 76) {
			if (changeState(state -> TeleportPosition_Group3_24(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 76 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group3_25(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 80 && getVariable(0xAA320A99L /*_DiceCount*/) <= 84) {
			if (changeState(state -> TeleportPosition_Group3_26(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 84 && getVariable(0xAA320A99L /*_DiceCount*/) <= 88) {
			if (changeState(state -> TeleportPosition_Group3_27(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 88 && getVariable(0xAA320A99L /*_DiceCount*/) <= 92) {
			if (changeState(state -> TeleportPosition_Group3_28(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 92 && getVariable(0xAA320A99L /*_DiceCount*/) <= 96) {
			if (changeState(state -> TeleportPosition_Group3_29(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 3 && getVariable(0xAA320A99L /*_DiceCount*/) > 96) {
			if (changeState(state -> TeleportPosition_Group3_30(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) <= 3) {
			if (changeState(state -> TeleportPosition_Group4_01(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 3 && getVariable(0xAA320A99L /*_DiceCount*/) <= 6) {
			if (changeState(state -> TeleportPosition_Group4_02(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 6 && getVariable(0xAA320A99L /*_DiceCount*/) <= 9) {
			if (changeState(state -> TeleportPosition_Group4_03(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 9 && getVariable(0xAA320A99L /*_DiceCount*/) <= 12) {
			if (changeState(state -> TeleportPosition_Group4_04(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 12 && getVariable(0xAA320A99L /*_DiceCount*/) <= 15) {
			if (changeState(state -> TeleportPosition_Group4_05(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 15 && getVariable(0xAA320A99L /*_DiceCount*/) <= 18) {
			if (changeState(state -> TeleportPosition_Group4_06(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 18 && getVariable(0xAA320A99L /*_DiceCount*/) <= 21) {
			if (changeState(state -> TeleportPosition_Group4_07(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 21 && getVariable(0xAA320A99L /*_DiceCount*/) <= 24) {
			if (changeState(state -> TeleportPosition_Group4_08(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 24 && getVariable(0xAA320A99L /*_DiceCount*/) <= 27) {
			if (changeState(state -> TeleportPosition_Group4_09(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 27 && getVariable(0xAA320A99L /*_DiceCount*/) <= 30) {
			if (changeState(state -> TeleportPosition_Group4_10(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 30 && getVariable(0xAA320A99L /*_DiceCount*/) <= 33) {
			if (changeState(state -> TeleportPosition_Group4_11(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 33 && getVariable(0xAA320A99L /*_DiceCount*/) <= 36) {
			if (changeState(state -> TeleportPosition_Group4_12(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 36 && getVariable(0xAA320A99L /*_DiceCount*/) <= 39) {
			if (changeState(state -> TeleportPosition_Group4_13(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 39 && getVariable(0xAA320A99L /*_DiceCount*/) <= 42) {
			if (changeState(state -> TeleportPosition_Group4_14(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 42 && getVariable(0xAA320A99L /*_DiceCount*/) <= 45) {
			if (changeState(state -> TeleportPosition_Group4_15(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 45 && getVariable(0xAA320A99L /*_DiceCount*/) <= 48) {
			if (changeState(state -> TeleportPosition_Group4_16(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 48 && getVariable(0xAA320A99L /*_DiceCount*/) <= 51) {
			if (changeState(state -> TeleportPosition_Group4_17(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 51 && getVariable(0xAA320A99L /*_DiceCount*/) <= 54) {
			if (changeState(state -> TeleportPosition_Group4_18(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 54 && getVariable(0xAA320A99L /*_DiceCount*/) <= 57) {
			if (changeState(state -> TeleportPosition_Group4_19(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 57 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group4_20(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 64) {
			if (changeState(state -> TeleportPosition_Group4_21(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 64 && getVariable(0xAA320A99L /*_DiceCount*/) <= 68) {
			if (changeState(state -> TeleportPosition_Group4_22(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 68 && getVariable(0xAA320A99L /*_DiceCount*/) <= 72) {
			if (changeState(state -> TeleportPosition_Group4_23(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 72 && getVariable(0xAA320A99L /*_DiceCount*/) <= 76) {
			if (changeState(state -> TeleportPosition_Group4_24(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 76 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group4_25(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 80 && getVariable(0xAA320A99L /*_DiceCount*/) <= 84) {
			if (changeState(state -> TeleportPosition_Group4_26(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 84 && getVariable(0xAA320A99L /*_DiceCount*/) <= 88) {
			if (changeState(state -> TeleportPosition_Group4_27(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 88 && getVariable(0xAA320A99L /*_DiceCount*/) <= 92) {
			if (changeState(state -> TeleportPosition_Group4_28(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 92 && getVariable(0xAA320A99L /*_DiceCount*/) <= 96) {
			if (changeState(state -> TeleportPosition_Group4_29(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 4 && getVariable(0xAA320A99L /*_DiceCount*/) > 96) {
			if (changeState(state -> TeleportPosition_Group4_30(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) <= 3) {
			if (changeState(state -> TeleportPosition_Group5_01(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 3 && getVariable(0xAA320A99L /*_DiceCount*/) <= 6) {
			if (changeState(state -> TeleportPosition_Group5_02(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 6 && getVariable(0xAA320A99L /*_DiceCount*/) <= 9) {
			if (changeState(state -> TeleportPosition_Group5_03(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 9 && getVariable(0xAA320A99L /*_DiceCount*/) <= 12) {
			if (changeState(state -> TeleportPosition_Group5_04(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 12 && getVariable(0xAA320A99L /*_DiceCount*/) <= 15) {
			if (changeState(state -> TeleportPosition_Group5_05(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 15 && getVariable(0xAA320A99L /*_DiceCount*/) <= 18) {
			if (changeState(state -> TeleportPosition_Group5_06(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 18 && getVariable(0xAA320A99L /*_DiceCount*/) <= 21) {
			if (changeState(state -> TeleportPosition_Group5_07(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 21 && getVariable(0xAA320A99L /*_DiceCount*/) <= 24) {
			if (changeState(state -> TeleportPosition_Group5_08(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 24 && getVariable(0xAA320A99L /*_DiceCount*/) <= 27) {
			if (changeState(state -> TeleportPosition_Group5_09(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 27 && getVariable(0xAA320A99L /*_DiceCount*/) <= 30) {
			if (changeState(state -> TeleportPosition_Group5_10(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 30 && getVariable(0xAA320A99L /*_DiceCount*/) <= 33) {
			if (changeState(state -> TeleportPosition_Group5_11(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 33 && getVariable(0xAA320A99L /*_DiceCount*/) <= 36) {
			if (changeState(state -> TeleportPosition_Group5_12(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 36 && getVariable(0xAA320A99L /*_DiceCount*/) <= 39) {
			if (changeState(state -> TeleportPosition_Group5_13(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 39 && getVariable(0xAA320A99L /*_DiceCount*/) <= 42) {
			if (changeState(state -> TeleportPosition_Group5_14(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 42 && getVariable(0xAA320A99L /*_DiceCount*/) <= 45) {
			if (changeState(state -> TeleportPosition_Group5_15(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 45 && getVariable(0xAA320A99L /*_DiceCount*/) <= 48) {
			if (changeState(state -> TeleportPosition_Group5_16(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 48 && getVariable(0xAA320A99L /*_DiceCount*/) <= 51) {
			if (changeState(state -> TeleportPosition_Group5_17(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 51 && getVariable(0xAA320A99L /*_DiceCount*/) <= 54) {
			if (changeState(state -> TeleportPosition_Group5_18(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 54 && getVariable(0xAA320A99L /*_DiceCount*/) <= 57) {
			if (changeState(state -> TeleportPosition_Group5_19(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 57 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group5_20(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 64) {
			if (changeState(state -> TeleportPosition_Group5_21(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 64 && getVariable(0xAA320A99L /*_DiceCount*/) <= 68) {
			if (changeState(state -> TeleportPosition_Group5_22(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 68 && getVariable(0xAA320A99L /*_DiceCount*/) <= 72) {
			if (changeState(state -> TeleportPosition_Group5_23(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 72 && getVariable(0xAA320A99L /*_DiceCount*/) <= 76) {
			if (changeState(state -> TeleportPosition_Group5_24(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 76 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group5_25(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 80 && getVariable(0xAA320A99L /*_DiceCount*/) <= 84) {
			if (changeState(state -> TeleportPosition_Group5_26(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 84 && getVariable(0xAA320A99L /*_DiceCount*/) <= 88) {
			if (changeState(state -> TeleportPosition_Group5_27(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 88 && getVariable(0xAA320A99L /*_DiceCount*/) <= 92) {
			if (changeState(state -> TeleportPosition_Group5_28(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 92 && getVariable(0xAA320A99L /*_DiceCount*/) <= 96) {
			if (changeState(state -> TeleportPosition_Group5_29(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 5 && getVariable(0xAA320A99L /*_DiceCount*/) > 96) {
			if (changeState(state -> TeleportPosition_Group5_30(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) <= 10) {
			if (changeState(state -> TeleportPosition_Group6_01(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 10 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group6_02(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 30) {
			if (changeState(state -> TeleportPosition_Group6_03(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 30 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group6_04(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 50) {
			if (changeState(state -> TeleportPosition_Group6_05(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 50 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group6_06(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 70) {
			if (changeState(state -> TeleportPosition_Group6_07(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 70 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group6_08(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 80 && getVariable(0xAA320A99L /*_DiceCount*/) <= 90) {
			if (changeState(state -> TeleportPosition_Group6_09(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 6 && getVariable(0xAA320A99L /*_DiceCount*/) > 90) {
			if (changeState(state -> TeleportPosition_Group6_10(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) <= 10) {
			if (changeState(state -> TeleportPosition_Group7_01(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 10 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group7_02(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 30) {
			if (changeState(state -> TeleportPosition_Group7_03(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 30 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group7_04(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 50) {
			if (changeState(state -> TeleportPosition_Group7_05(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 50 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group7_06(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 70) {
			if (changeState(state -> TeleportPosition_Group7_07(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 70 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group7_08(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 80 && getVariable(0xAA320A99L /*_DiceCount*/) <= 90) {
			if (changeState(state -> TeleportPosition_Group7_09(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 7 && getVariable(0xAA320A99L /*_DiceCount*/) > 90) {
			if (changeState(state -> TeleportPosition_Group7_10(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 8 && getVariable(0xAA320A99L /*_DiceCount*/) <= 10) {
			if (changeState(state -> TeleportPosition_Group8_01(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 8 && getVariable(0xAA320A99L /*_DiceCount*/) > 10 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group8_02(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 8 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 30) {
			if (changeState(state -> TeleportPosition_Group8_03(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 8 && getVariable(0xAA320A99L /*_DiceCount*/) > 30 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group8_04(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 8 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 50) {
			if (changeState(state -> TeleportPosition_Group8_05(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 8 && getVariable(0xAA320A99L /*_DiceCount*/) > 50 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group8_06(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 8 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 70) {
			if (changeState(state -> TeleportPosition_Group8_07(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 8 && getVariable(0xAA320A99L /*_DiceCount*/) > 70 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group8_08(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 8 && getVariable(0xAA320A99L /*_DiceCount*/) > 80 && getVariable(0xAA320A99L /*_DiceCount*/) <= 90) {
			if (changeState(state -> TeleportPosition_Group8_09(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 8 && getVariable(0xAA320A99L /*_DiceCount*/) > 90) {
			if (changeState(state -> TeleportPosition_Group8_10(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 9 && getVariable(0xAA320A99L /*_DiceCount*/) <= 10) {
			if (changeState(state -> TeleportPosition_Group9_01(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 9 && getVariable(0xAA320A99L /*_DiceCount*/) > 10 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group9_02(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 9 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 30) {
			if (changeState(state -> TeleportPosition_Group9_03(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 9 && getVariable(0xAA320A99L /*_DiceCount*/) > 30 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group9_04(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 9 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 50) {
			if (changeState(state -> TeleportPosition_Group9_05(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 9 && getVariable(0xAA320A99L /*_DiceCount*/) > 50 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group9_06(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 9 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 70) {
			if (changeState(state -> TeleportPosition_Group9_07(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 9 && getVariable(0xAA320A99L /*_DiceCount*/) > 70 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group9_08(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 9 && getVariable(0xAA320A99L /*_DiceCount*/) > 80 && getVariable(0xAA320A99L /*_DiceCount*/) <= 90) {
			if (changeState(state -> TeleportPosition_Group9_09(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 9 && getVariable(0xAA320A99L /*_DiceCount*/) > 90) {
			if (changeState(state -> TeleportPosition_Group9_10(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) <= 5) {
			if (changeState(state -> TeleportPosition_Group10_01(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 5 && getVariable(0xAA320A99L /*_DiceCount*/) <= 10) {
			if (changeState(state -> TeleportPosition_Group10_02(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 10 && getVariable(0xAA320A99L /*_DiceCount*/) <= 15) {
			if (changeState(state -> TeleportPosition_Group10_03(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 15 && getVariable(0xAA320A99L /*_DiceCount*/) <= 20) {
			if (changeState(state -> TeleportPosition_Group10_04(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 20 && getVariable(0xAA320A99L /*_DiceCount*/) <= 25) {
			if (changeState(state -> TeleportPosition_Group10_05(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 25 && getVariable(0xAA320A99L /*_DiceCount*/) <= 30) {
			if (changeState(state -> TeleportPosition_Group10_06(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 30 && getVariable(0xAA320A99L /*_DiceCount*/) <= 35) {
			if (changeState(state -> TeleportPosition_Group10_07(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 35 && getVariable(0xAA320A99L /*_DiceCount*/) <= 40) {
			if (changeState(state -> TeleportPosition_Group10_08(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 40 && getVariable(0xAA320A99L /*_DiceCount*/) <= 45) {
			if (changeState(state -> TeleportPosition_Group10_09(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 45 && getVariable(0xAA320A99L /*_DiceCount*/) <= 50) {
			if (changeState(state -> TeleportPosition_Group10_10(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 50 && getVariable(0xAA320A99L /*_DiceCount*/) <= 55) {
			if (changeState(state -> TeleportPosition_Group10_11(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 55 && getVariable(0xAA320A99L /*_DiceCount*/) <= 60) {
			if (changeState(state -> TeleportPosition_Group10_12(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 60 && getVariable(0xAA320A99L /*_DiceCount*/) <= 65) {
			if (changeState(state -> TeleportPosition_Group10_13(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 65 && getVariable(0xAA320A99L /*_DiceCount*/) <= 70) {
			if (changeState(state -> TeleportPosition_Group10_14(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 70 && getVariable(0xAA320A99L /*_DiceCount*/) <= 75) {
			if (changeState(state -> TeleportPosition_Group10_15(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 75 && getVariable(0xAA320A99L /*_DiceCount*/) <= 80) {
			if (changeState(state -> TeleportPosition_Group10_16(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 80 && getVariable(0xAA320A99L /*_DiceCount*/) <= 85) {
			if (changeState(state -> TeleportPosition_Group10_17(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 85 && getVariable(0xAA320A99L /*_DiceCount*/) <= 90) {
			if (changeState(state -> TeleportPosition_Group10_18(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 90 && getVariable(0xAA320A99L /*_DiceCount*/) <= 95) {
			if (changeState(state -> TeleportPosition_Group10_19(blendTime)))
				return;
		}
		if (getVariable(0xBF12F1B8L /*_GroupNo*/) == 10 && getVariable(0xAA320A99L /*_DiceCount*/) > 95) {
			if (changeState(state -> TeleportPosition_Group10_20(blendTime)))
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
			if (changeState(state -> Die(blendTime)))
				return;
		}
		doAction(1610588688L /*WAIT_READY*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Hide(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6D61FF50L /*Hide*/);
		setVariable(0xD9E7438DL /*_TeleportReady_IngTime*/, getTime());
		setVariable(0xE6EB1712L /*_TeleportReady_EndTime*/, getVariable(0xD9E7438DL /*_TeleportReady_IngTime*/) - getVariable(0x34B608CEL /*_TeleportReady_StartTime*/));
		if (getVariable(0xE6EB1712L /*_TeleportReady_EndTime*/) > 3600000) {
			if (changeState(state -> Die(blendTime)))
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
		doTeleportToWaypoint("goldedchest_balenos01", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC6D97BCL /*TeleportPosition_Group1_02*/);
		doTeleportToWaypoint("goldedchest_balenos02", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDA524AD5L /*TeleportPosition_Group1_03*/);
		doTeleportToWaypoint("goldedchest_balenos03", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x88DC0404L /*TeleportPosition_Group1_04*/);
		doTeleportToWaypoint("goldedchest_balenos04", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3D9FECE2L /*TeleportPosition_Group1_05*/);
		doTeleportToWaypoint("goldedchest_balenos05", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7F080E98L /*TeleportPosition_Group1_06*/);
		doTeleportToWaypoint("goldedchest_balenos06", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD813DE15L /*TeleportPosition_Group1_07*/);
		doTeleportToWaypoint("goldedchest_balenos07", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x513AD1B9L /*TeleportPosition_Group1_08*/);
		doTeleportToWaypoint("goldedchest_balenos08", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xFC2A25B2L /*TeleportPosition_Group1_09*/);
		doTeleportToWaypoint("goldedchest_balenos09", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4D692DE9L /*TeleportPosition_Group1_10*/);
		doTeleportToWaypoint("goldedchest_balenos10", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_11(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB1B8E8B2L /*TeleportPosition_Group1_11*/);
		doTeleportToWaypoint("goldedchest_balenos11", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_12(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3E0D21E8L /*TeleportPosition_Group1_12*/);
		doTeleportToWaypoint("goldedchest_balenos12", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_13(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1C70C442L /*TeleportPosition_Group1_13*/);
		doTeleportToWaypoint("goldedchest_balenos13", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_14(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x845BAAF9L /*TeleportPosition_Group1_14*/);
		doTeleportToWaypoint("goldedchest_balenos14", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC513BA2L /*TeleportPosition_Group1_15*/);
		doTeleportToWaypoint("goldedchest_balenos15", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_16(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC49462EAL /*TeleportPosition_Group1_16*/);
		doTeleportToWaypoint("goldedchest_balenos16", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_17(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xEEC9B17CL /*TeleportPosition_Group1_17*/);
		doTeleportToWaypoint("goldedchest_balenos17", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_18(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x974B6738L /*TeleportPosition_Group1_18*/);
		doTeleportToWaypoint("goldedchest_balenos18", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_19(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD9B38DD7L /*TeleportPosition_Group1_19*/);
		doTeleportToWaypoint("goldedchest_balenos19", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_20(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7EBED1B1L /*TeleportPosition_Group1_20*/);
		doTeleportToWaypoint("goldedchest_balenos20", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_21(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x71A74B7L /*TeleportPosition_Group1_21*/);
		doTeleportToWaypoint("goldedchest_balenos21", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_22(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3AE33C40L /*TeleportPosition_Group1_22*/);
		doTeleportToWaypoint("goldedchest_balenos22", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_23(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA5162402L /*TeleportPosition_Group1_23*/);
		doTeleportToWaypoint("goldedchest_balenos23", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_24(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC8E60345L /*TeleportPosition_Group1_24*/);
		doTeleportToWaypoint("goldedchest_balenos24", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBCE607F4L /*TeleportPosition_Group1_25*/);
		doTeleportToWaypoint("goldedchest_balenos25", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_26(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF6016284L /*TeleportPosition_Group1_26*/);
		doTeleportToWaypoint("goldedchest_balenos26", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_27(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xFD2A311CL /*TeleportPosition_Group1_27*/);
		doTeleportToWaypoint("goldedchest_balenos27", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_28(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x93BBB2D0L /*TeleportPosition_Group1_28*/);
		doTeleportToWaypoint("goldedchest_balenos28", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_29(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1D2EA0F9L /*TeleportPosition_Group1_29*/);
		doTeleportToWaypoint("goldedchest_balenos29", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group1_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA1675FF3L /*TeleportPosition_Group1_30*/);
		doTeleportToWaypoint("goldedchest_balenos30", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x12D949A7L /*TeleportPosition_Group2_01*/);
		doTeleportToWaypoint("goldedchest_serendia01", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBB03F0EDL /*TeleportPosition_Group2_02*/);
		doTeleportToWaypoint("goldedchest_serendia02", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC7928483L /*TeleportPosition_Group2_03*/);
		doTeleportToWaypoint("goldedchest_serendia03", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x35586C68L /*TeleportPosition_Group2_04*/);
		doTeleportToWaypoint("goldedchest_serendia04", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC49B39F3L /*TeleportPosition_Group2_05*/);
		doTeleportToWaypoint("goldedchest_serendia05", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x688DCFBFL /*TeleportPosition_Group2_06*/);
		doTeleportToWaypoint("goldedchest_serendia06", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x355EF995L /*TeleportPosition_Group2_07*/);
		doTeleportToWaypoint("goldedchest_serendia07", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF4072ABEL /*TeleportPosition_Group2_08*/);
		doTeleportToWaypoint("goldedchest_serendia08", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE6AD62A3L /*TeleportPosition_Group2_09*/);
		doTeleportToWaypoint("goldedchest_serendia09", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3278362L /*TeleportPosition_Group2_10*/);
		doTeleportToWaypoint("goldedchest_serendia10", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_11(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1E0D821CL /*TeleportPosition_Group2_11*/);
		doTeleportToWaypoint("goldedchest_serendia11", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_12(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x24A8AEF9L /*TeleportPosition_Group2_12*/);
		doTeleportToWaypoint("goldedchest_serendia12", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_13(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5B0D8651L /*TeleportPosition_Group2_13*/);
		doTeleportToWaypoint("goldedchest_serendia13", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_14(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE8B37EECL /*TeleportPosition_Group2_14*/);
		doTeleportToWaypoint("goldedchest_serendia14", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xAF1F14A9L /*TeleportPosition_Group2_15*/);
		doTeleportToWaypoint("goldedchest_serendia15", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_16(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD7669B10L /*TeleportPosition_Group2_16*/);
		doTeleportToWaypoint("goldedchest_serendia16", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_17(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xAF24E769L /*TeleportPosition_Group2_17*/);
		doTeleportToWaypoint("goldedchest_serendia17", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_18(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x53F80E15L /*TeleportPosition_Group2_18*/);
		doTeleportToWaypoint("goldedchest_serendia18", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_19(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9DEBC040L /*TeleportPosition_Group2_19*/);
		doTeleportToWaypoint("goldedchest_serendia19", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_20(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCFF5EEDAL /*TeleportPosition_Group2_20*/);
		doTeleportToWaypoint("goldedchest_serendia20", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_21(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBD18362CL /*TeleportPosition_Group2_21*/);
		doTeleportToWaypoint("goldedchest_serendia21", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_22(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x699AABEAL /*TeleportPosition_Group2_22*/);
		doTeleportToWaypoint("goldedchest_serendia22", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_23(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1289DC2FL /*TeleportPosition_Group2_23*/);
		doTeleportToWaypoint("goldedchest_serendia23", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_24(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x304BDA65L /*TeleportPosition_Group2_24*/);
		doTeleportToWaypoint("goldedchest_serendia24", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x65FA9A77L /*TeleportPosition_Group2_25*/);
		doTeleportToWaypoint("goldedchest_serendia25", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_26(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4291D5D5L /*TeleportPosition_Group2_26*/);
		doTeleportToWaypoint("goldedchest_serendia26", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_27(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBF6131EAL /*TeleportPosition_Group2_27*/);
		doTeleportToWaypoint("goldedchest_serendia27", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_28(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF7F62D70L /*TeleportPosition_Group2_28*/);
		doTeleportToWaypoint("goldedchest_serendia28", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_29(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9EDD60CL /*TeleportPosition_Group2_29*/);
		doTeleportToWaypoint("goldedchest_serendia29", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group2_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x728A05F2L /*TeleportPosition_Group2_30*/);
		doTeleportToWaypoint("goldedchest_serendia30", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x81679C27L /*TeleportPosition_Group3_01*/);
		doTeleportToWaypoint("goldedchest_calpheon01", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1A00A6C6L /*TeleportPosition_Group3_02*/);
		doTeleportToWaypoint("goldedchest_calpheon02", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCB77347DL /*TeleportPosition_Group3_03*/);
		doTeleportToWaypoint("goldedchest_calpheon03", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5560CC63L /*TeleportPosition_Group3_04*/);
		doTeleportToWaypoint("goldedchest_calpheon04", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xAF87D55BL /*TeleportPosition_Group3_05*/);
		doTeleportToWaypoint("goldedchest_calpheon05", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6917CC4BL /*TeleportPosition_Group3_06*/);
		doTeleportToWaypoint("goldedchest_calpheon06", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8C6EE48DL /*TeleportPosition_Group3_07*/);
		doTeleportToWaypoint("goldedchest_calpheon07", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7D8C7474L /*TeleportPosition_Group3_08*/);
		doTeleportToWaypoint("goldedchest_calpheon08", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE08F5671L /*TeleportPosition_Group3_09*/);
		doTeleportToWaypoint("goldedchest_calpheon09", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x25B5CA9EL /*TeleportPosition_Group3_10*/);
		doTeleportToWaypoint("goldedchest_calpheon10", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_11(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x75201ECEL /*TeleportPosition_Group3_11*/);
		doTeleportToWaypoint("goldedchest_calpheon11", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_12(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x95E32858L /*TeleportPosition_Group3_12*/);
		doTeleportToWaypoint("goldedchest_calpheon12", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_13(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2F3EEB4EL /*TeleportPosition_Group3_13*/);
		doTeleportToWaypoint("goldedchest_calpheon13", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_14(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7DFD57F0L /*TeleportPosition_Group3_14*/);
		doTeleportToWaypoint("goldedchest_calpheon14", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD0BF4E12L /*TeleportPosition_Group3_15*/);
		doTeleportToWaypoint("goldedchest_calpheon15", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_16(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7374018DL /*TeleportPosition_Group3_16*/);
		doTeleportToWaypoint("goldedchest_calpheon16", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_17(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x51E2E444L /*TeleportPosition_Group3_17*/);
		doTeleportToWaypoint("goldedchest_calpheon17", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_18(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x74008C44L /*TeleportPosition_Group3_18*/);
		doTeleportToWaypoint("goldedchest_calpheon18", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_19(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCD2EB933L /*TeleportPosition_Group3_19*/);
		doTeleportToWaypoint("goldedchest_calpheon19", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_20(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE6FBAD5FL /*TeleportPosition_Group3_20*/);
		doTeleportToWaypoint("goldedchest_calpheon20", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_21(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x30A32CL /*TeleportPosition_Group3_21*/);
		doTeleportToWaypoint("goldedchest_calpheon21", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_22(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB55EF9BL /*TeleportPosition_Group3_22*/);
		doTeleportToWaypoint("goldedchest_calpheon22", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_23(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x668955F2L /*TeleportPosition_Group3_23*/);
		doTeleportToWaypoint("goldedchest_calpheon23", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_24(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE4DF36CCL /*TeleportPosition_Group3_24*/);
		doTeleportToWaypoint("goldedchest_calpheon24", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3172F2FCL /*TeleportPosition_Group3_25*/);
		doTeleportToWaypoint("goldedchest_calpheon25", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_26(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD5FEEC95L /*TeleportPosition_Group3_26*/);
		doTeleportToWaypoint("goldedchest_calpheon26", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_27(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC15AF359L /*TeleportPosition_Group3_27*/);
		doTeleportToWaypoint("goldedchest_calpheon27", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_28(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF5875201L /*TeleportPosition_Group3_28*/);
		doTeleportToWaypoint("goldedchest_calpheon28", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_29(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2C835EF1L /*TeleportPosition_Group3_29*/);
		doTeleportToWaypoint("goldedchest_calpheon29", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group3_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9FFEE32CL /*TeleportPosition_Group3_30*/);
		doTeleportToWaypoint("goldedchest_calpheon30", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF6B4F570L /*TeleportPosition_Group4_01*/);
		doTeleportToWaypoint("goldedchest_media01", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBF6D3767L /*TeleportPosition_Group4_02*/);
		doTeleportToWaypoint("goldedchest_media02", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2BDED53CL /*TeleportPosition_Group4_03*/);
		doTeleportToWaypoint("goldedchest_media03", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x374ABEA9L /*TeleportPosition_Group4_04*/);
		doTeleportToWaypoint("goldedchest_media04", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x45EF9607L /*TeleportPosition_Group4_05*/);
		doTeleportToWaypoint("goldedchest_media05", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xECE9AD00L /*TeleportPosition_Group4_06*/);
		doTeleportToWaypoint("goldedchest_media06", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6BF891FAL /*TeleportPosition_Group4_07*/);
		doTeleportToWaypoint("goldedchest_media07", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1747C545L /*TeleportPosition_Group4_08*/);
		doTeleportToWaypoint("goldedchest_media08", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7DE6D30BL /*TeleportPosition_Group4_09*/);
		doTeleportToWaypoint("goldedchest_media09", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7ED6A4A3L /*TeleportPosition_Group4_10*/);
		doTeleportToWaypoint("goldedchest_media10", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_11(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC111CB4AL /*TeleportPosition_Group4_11*/);
		doTeleportToWaypoint("goldedchest_media11", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_12(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xFCB58EE2L /*TeleportPosition_Group4_12*/);
		doTeleportToWaypoint("goldedchest_media12", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_13(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x76AB5E55L /*TeleportPosition_Group4_13*/);
		doTeleportToWaypoint("goldedchest_media13", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_14(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6C98C941L /*TeleportPosition_Group4_14*/);
		doTeleportToWaypoint("goldedchest_media14", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDC8F590L /*TeleportPosition_Group4_15*/);
		doTeleportToWaypoint("goldedchest_media15", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_16(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3034A8E2L /*TeleportPosition_Group4_16*/);
		doTeleportToWaypoint("goldedchest_media16", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_17(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1AE96888L /*TeleportPosition_Group4_17*/);
		doTeleportToWaypoint("goldedchest_media17", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_18(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD2B4EB78L /*TeleportPosition_Group4_18*/);
		doTeleportToWaypoint("goldedchest_media18", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_19(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6448AFA2L /*TeleportPosition_Group4_19*/);
		doTeleportToWaypoint("goldedchest_media19", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_20(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD1F54F91L /*TeleportPosition_Group4_20*/);
		doTeleportToWaypoint("goldedchest_media20", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_21(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4D82232AL /*TeleportPosition_Group4_21*/);
		doTeleportToWaypoint("goldedchest_media21", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_22(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x228CF2A5L /*TeleportPosition_Group4_22*/);
		doTeleportToWaypoint("goldedchest_media22", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_23(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x250D4024L /*TeleportPosition_Group4_23*/);
		doTeleportToWaypoint("goldedchest_media23", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_24(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2B782DC9L /*TeleportPosition_Group4_24*/);
		doTeleportToWaypoint("goldedchest_media24", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA12BE937L /*TeleportPosition_Group4_25*/);
		doTeleportToWaypoint("goldedchest_media25", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_26(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x62BE9838L /*TeleportPosition_Group4_26*/);
		doTeleportToWaypoint("goldedchest_media26", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_27(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4E37CBA7L /*TeleportPosition_Group4_27*/);
		doTeleportToWaypoint("goldedchest_media27", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_28(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9B8B5309L /*TeleportPosition_Group4_28*/);
		doTeleportToWaypoint("goldedchest_media28", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_29(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x18BE78F8L /*TeleportPosition_Group4_29*/);
		doTeleportToWaypoint("goldedchest_media29", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group4_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDA4F2B5AL /*TeleportPosition_Group4_30*/);
		doTeleportToWaypoint("goldedchest_media30", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9ABE129DL /*TeleportPosition_Group5_01*/);
		doTeleportToWaypoint("goldedchest_valencia01", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xFDABC2D2L /*TeleportPosition_Group5_02*/);
		doTeleportToWaypoint("goldedchest_valencia02", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x66D00BCL /*TeleportPosition_Group5_03*/);
		doTeleportToWaypoint("goldedchest_valencia03", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x88D3109EL /*TeleportPosition_Group5_04*/);
		doTeleportToWaypoint("goldedchest_valencia04", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xEAEE8DBAL /*TeleportPosition_Group5_05*/);
		doTeleportToWaypoint("goldedchest_valencia05", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7FBA1DCAL /*TeleportPosition_Group5_06*/);
		doTeleportToWaypoint("goldedchest_valencia06", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8F12A63DL /*TeleportPosition_Group5_07*/);
		doTeleportToWaypoint("goldedchest_valencia07", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2067560CL /*TeleportPosition_Group5_08*/);
		doTeleportToWaypoint("goldedchest_valencia08", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC2D95D05L /*TeleportPosition_Group5_09*/);
		doTeleportToWaypoint("goldedchest_valencia09", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE6555393L /*TeleportPosition_Group5_10*/);
		doTeleportToWaypoint("goldedchest_valencia10", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_11(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF7161F83L /*TeleportPosition_Group5_11*/);
		doTeleportToWaypoint("goldedchest_valencia11", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_12(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x303862C6L /*TeleportPosition_Group5_12*/);
		doTeleportToWaypoint("goldedchest_valencia12", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_13(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4EE5AEB2L /*TeleportPosition_Group5_13*/);
		doTeleportToWaypoint("goldedchest_valencia13", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_14(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x21CD685EL /*TeleportPosition_Group5_14*/);
		doTeleportToWaypoint("goldedchest_valencia14", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4EDB16CBL /*TeleportPosition_Group5_15*/);
		doTeleportToWaypoint("goldedchest_valencia15", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_16(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x91031D55L /*TeleportPosition_Group5_16*/);
		doTeleportToWaypoint("goldedchest_valencia16", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_17(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7FFB8652L /*TeleportPosition_Group5_17*/);
		doTeleportToWaypoint("goldedchest_valencia17", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_18(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x88FCD34AL /*TeleportPosition_Group5_18*/);
		doTeleportToWaypoint("goldedchest_valencia18", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_19(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9E2699C1L /*TeleportPosition_Group5_19*/);
		doTeleportToWaypoint("goldedchest_valencia19", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_20(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x25307E23L /*TeleportPosition_Group5_20*/);
		doTeleportToWaypoint("goldedchest_valencia20", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_21(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA6B3958FL /*TeleportPosition_Group5_21*/);
		doTeleportToWaypoint("goldedchest_valencia21", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_22(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x169B19E5L /*TeleportPosition_Group5_22*/);
		doTeleportToWaypoint("goldedchest_valencia22", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_23(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x920C399EL /*TeleportPosition_Group5_23*/);
		doTeleportToWaypoint("goldedchest_valencia23", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_24(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF669CCCAL /*TeleportPosition_Group5_24*/);
		doTeleportToWaypoint("goldedchest_valencia24", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_25(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9479F3F7L /*TeleportPosition_Group5_25*/);
		doTeleportToWaypoint("goldedchest_valencia25", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_26(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5B1F67ECL /*TeleportPosition_Group5_26*/);
		doTeleportToWaypoint("goldedchest_valencia26", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_27(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xB30AFE81L /*TeleportPosition_Group5_27*/);
		doTeleportToWaypoint("goldedchest_valencia27", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_28(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD2BF4F06L /*TeleportPosition_Group5_28*/);
		doTeleportToWaypoint("goldedchest_valencia28", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_29(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x61CD93C1L /*TeleportPosition_Group5_29*/);
		doTeleportToWaypoint("goldedchest_valencia29", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group5_30(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x654AC221L /*TeleportPosition_Group5_30*/);
		doTeleportToWaypoint("goldedchest_valencia30", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group6_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x64CC32FEL /*TeleportPosition_Group6_01*/);
		doTeleportToWaypoint("goldedchest_magoria01", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group6_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x24BBCC0DL /*TeleportPosition_Group6_02*/);
		doTeleportToWaypoint("goldedchest_magoria02", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group6_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2D4A4AF2L /*TeleportPosition_Group6_03*/);
		doTeleportToWaypoint("goldedchest_magoria03", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group6_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x10F379C8L /*TeleportPosition_Group6_04*/);
		doTeleportToWaypoint("goldedchest_magoria04", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group6_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3498B758L /*TeleportPosition_Group6_05*/);
		doTeleportToWaypoint("goldedchest_magoria05", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group6_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3FEE5FC6L /*TeleportPosition_Group6_06*/);
		doTeleportToWaypoint("goldedchest_magoria06", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group6_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x500F1D00L /*TeleportPosition_Group6_07*/);
		doTeleportToWaypoint("goldedchest_magoria07", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group6_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD9B45144L /*TeleportPosition_Group6_08*/);
		doTeleportToWaypoint("goldedchest_magoria08", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group6_09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE548F57BL /*TeleportPosition_Group6_09*/);
		doTeleportToWaypoint("goldedchest_magoria09", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group6_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA9C9E62EL /*TeleportPosition_Group6_10*/);
		doTeleportToWaypoint("goldedchest_magoria10", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group7_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD72F6318L /*TeleportPosition_Group7_01*/);
		doTeleportToWaypoint("goldedchest_undercave01", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group7_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x608E0403L /*TeleportPosition_Group7_02*/);
		doTeleportToWaypoint("goldedchest_undercave02", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group7_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3410D557L /*TeleportPosition_Group7_03*/);
		doTeleportToWaypoint("goldedchest_undercave03", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group7_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x7267A942L /*TeleportPosition_Group7_04*/);
		doTeleportToWaypoint("goldedchest_undercave04", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group7_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3D12368FL /*TeleportPosition_Group7_05*/);
		doTeleportToWaypoint("goldedchest_undercave05", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group7_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDE28E05AL /*TeleportPosition_Group7_06*/);
		doTeleportToWaypoint("goldedchest_undercave06", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group7_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xFF4CFDECL /*TeleportPosition_Group7_07*/);
		doTeleportToWaypoint("goldedchest_undercave07", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group7_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4D979353L /*TeleportPosition_Group7_08*/);
		doTeleportToWaypoint("goldedchest_undercave08", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group7_09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x428DA988L /*TeleportPosition_Group7_09*/);
		doTeleportToWaypoint("goldedchest_undercave09", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group7_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4EF9DFFFL /*TeleportPosition_Group7_10*/);
		doTeleportToWaypoint("goldedchest_undercave10", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group8_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xAA7F60DDL /*TeleportPosition_Group8_01*/);
		doTeleportToWaypoint("goldedchest_coral01", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group8_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1CDB29B8L /*TeleportPosition_Group8_02*/);
		doTeleportToWaypoint("goldedchest_coral02", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group8_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC984488FL /*TeleportPosition_Group8_03*/);
		doTeleportToWaypoint("goldedchest_coral03", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group8_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3870D22L /*TeleportPosition_Group8_04*/);
		doTeleportToWaypoint("goldedchest_coral04", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group8_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3A1498FEL /*TeleportPosition_Group8_05*/);
		doTeleportToWaypoint("goldedchest_coral05", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group8_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE7E54BEFL /*TeleportPosition_Group8_06*/);
		doTeleportToWaypoint("goldedchest_coral06", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group8_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD82AE457L /*TeleportPosition_Group8_07*/);
		doTeleportToWaypoint("goldedchest_coral07", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group8_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5A5D2717L /*TeleportPosition_Group8_08*/);
		doTeleportToWaypoint("goldedchest_coral08", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group8_09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA3F6A366L /*TeleportPosition_Group8_09*/);
		doTeleportToWaypoint("goldedchest_coral09", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group8_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9483BAA2L /*TeleportPosition_Group8_10*/);
		doTeleportToWaypoint("goldedchest_coral10", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group9_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xE8A86D5EL /*TeleportPosition_Group9_01*/);
		doTeleportToWaypoint("goldedchest_heritage01", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group9_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x19C8846DL /*TeleportPosition_Group9_02*/);
		doTeleportToWaypoint("goldedchest_heritage02", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group9_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6ED558BCL /*TeleportPosition_Group9_03*/);
		doTeleportToWaypoint("goldedchest_heritage03", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group9_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8A094CEL /*TeleportPosition_Group9_04*/);
		doTeleportToWaypoint("goldedchest_heritage04", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group9_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x2198B466L /*TeleportPosition_Group9_05*/);
		doTeleportToWaypoint("goldedchest_heritage05", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group9_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9A14F2B4L /*TeleportPosition_Group9_06*/);
		doTeleportToWaypoint("goldedchest_heritage06", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group9_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD4BE1926L /*TeleportPosition_Group9_07*/);
		doTeleportToWaypoint("goldedchest_heritage07", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group9_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x9B922AD9L /*TeleportPosition_Group9_08*/);
		doTeleportToWaypoint("goldedchest_heritage08", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group9_09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xBD550C93L /*TeleportPosition_Group9_09*/);
		doTeleportToWaypoint("goldedchest_heritage09", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group9_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF16AFE13L /*TeleportPosition_Group9_10*/);
		doTeleportToWaypoint("goldedchest_heritage10", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_01(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x137DFD8DL /*TeleportPosition_Group10_01*/);
		doTeleportToWaypoint("goldedchest_island01", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_02(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF94FB256L /*TeleportPosition_Group10_02*/);
		doTeleportToWaypoint("goldedchest_island02", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_03(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF474E5FCL /*TeleportPosition_Group10_03*/);
		doTeleportToWaypoint("goldedchest_island03", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_04(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF482F10DL /*TeleportPosition_Group10_04*/);
		doTeleportToWaypoint("goldedchest_island04", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_05(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD45CD45L /*TeleportPosition_Group10_05*/);
		doTeleportToWaypoint("goldedchest_island05", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_06(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xDCF7206AL /*TeleportPosition_Group10_06*/);
		doTeleportToWaypoint("goldedchest_island06", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_07(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x6F3ABBF6L /*TeleportPosition_Group10_07*/);
		doTeleportToWaypoint("goldedchest_island07", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_08(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8E520A52L /*TeleportPosition_Group10_08*/);
		doTeleportToWaypoint("goldedchest_island08", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_09(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x24490D17L /*TeleportPosition_Group10_09*/);
		doTeleportToWaypoint("goldedchest_island09", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_10(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xD1485B88L /*TeleportPosition_Group10_10*/);
		doTeleportToWaypoint("goldedchest_island10", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_11(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x5FBA9FBAL /*TeleportPosition_Group10_11*/);
		doTeleportToWaypoint("goldedchest_island11", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_12(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x945DC5C9L /*TeleportPosition_Group10_12*/);
		doTeleportToWaypoint("goldedchest_island12", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_13(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x482B041DL /*TeleportPosition_Group10_13*/);
		doTeleportToWaypoint("goldedchest_island13", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_14(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x4BEECB63L /*TeleportPosition_Group10_14*/);
		doTeleportToWaypoint("goldedchest_island14", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_15(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x8FE30F2BL /*TeleportPosition_Group10_15*/);
		doTeleportToWaypoint("goldedchest_island15", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_16(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC0B4A0B2L /*TeleportPosition_Group10_16*/);
		doTeleportToWaypoint("goldedchest_island16", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_17(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x3F480871L /*TeleportPosition_Group10_17*/);
		doTeleportToWaypoint("goldedchest_island17", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_18(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x17755BB1L /*TeleportPosition_Group10_18*/);
		doTeleportToWaypoint("goldedchest_island18", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_19(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x22EEF7DAL /*TeleportPosition_Group10_19*/);
		doTeleportToWaypoint("goldedchest_island19", "teleport", 0, 0, 1, 1);
		changeState(state -> Find_Player_Logic(blendTime));
	}

	protected void TeleportPosition_Group10_20(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA27EDD12L /*TeleportPosition_Group10_20*/);
		doTeleportToWaypoint("goldedchest_island20", "teleport", 0, 0, 1, 1);
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
