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
@IAIName("gargoyle_test")
public class Ai_gargoyle_test extends CreatureAI {
	public Ai_gargoyle_test(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Type2_Go(blendTime), 1000));
	}

	protected void Type2_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8E38CD77L /*Type2_Go*/);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gargoyle_Test_001", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Go1(blendTime), 1000)));
	}

	protected void Type2_Go1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6EC8E369L /*Type2_Go1*/);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gargoyle_Test_003", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Go2(blendTime), 1000)));
	}

	protected void Type2_Go2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5DDDA157L /*Type2_Go2*/);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gargoyle_Test_003", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Go3(blendTime), 1000)));
	}

	protected void Type2_Go3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xA4974119L /*Type2_Go3*/);
		doAction(3136286049L /*FLYING_WAIT*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "Gargoyle_Test_005", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Type2_Go(blendTime), 1000)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
