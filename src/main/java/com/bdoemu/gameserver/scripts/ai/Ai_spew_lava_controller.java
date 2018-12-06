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
@IAIName("spew_lava_controller")
public class Ai_spew_lava_controller extends CreatureAI {
	public Ai_spew_lava_controller(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, 0);
		setVariable(0x64931736L /*_Summon_IngTime*/, 0);
		setVariable(0x20784437L /*_Summon_EndTime*/, 0);
		setVariable(0x96CB9F57L /*_Summon_StartTime*/, getTime());
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Teleport_Logic(blendTime), 100));
	}

	protected void Teleport_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xA5BDA01CL /*Teleport_Logic*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		changeState(state -> Summon_Logic(blendTime));
	}

	protected void Summon_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xECE60874L /*Summon_Logic*/);
		if (changeState(state -> Summon_Type1(0.3)))
			return;
		changeState(state -> Summon_Logic(blendTime));
	}

	protected void Summon_Type1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xC67EE65DL /*Summon_Type1*/);
		doAction(1813410895L /*SUMMON_LAVA1*/, blendTime, onDoActionEnd -> changeState(state -> send_command(blendTime)));
	}

	protected void Summon_Type2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x93849D2FL /*Summon_Type2*/);
		doAction(1813410895L /*SUMMON_LAVA1*/, blendTime, onDoActionEnd -> changeState(state -> send_command(blendTime)));
	}

	protected void Summon_Type3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xFDFA6615L /*Summon_Type3*/);
		doAction(1813410895L /*SUMMON_LAVA1*/, blendTime, onDoActionEnd -> changeState(state -> send_command(blendTime)));
	}

	protected void send_command(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xAE65FF20L /*send_command*/);
		getObjects(EAIFindTargetType.Child, object -> true).forEach(consumer -> consumer.getAi().HandleSpewLava(getActor(), null));
		changeState(state -> Die_Wait(blendTime));
	}

	protected void Die_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DC3CFB8L /*Die_Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 10000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 100000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
