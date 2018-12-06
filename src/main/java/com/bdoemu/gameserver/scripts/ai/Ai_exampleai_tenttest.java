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
@IAIName("exampleai_tenttest")
public class Ai_exampleai_tenttest extends CreatureAI {
	public Ai_exampleai_tenttest(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		if (getBuildingRate() < 50) {
			if (changeState(state -> BuildingRate_0(blendTime)))
				return;
		}
		if (getBuildingRate() < 95) {
			if (changeState(state -> BuildingRate_50(blendTime)))
				return;
		}
		if (changeState(state -> BuildingRate_100(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> BuildingRate_0(blendTime), 1000));
	}

	protected void BuildingRate_0(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD48D4B83L /*BuildingRate_0*/);
		doAction(2141413530L /*BuildingRate_0*/, blendTime, onDoActionEnd -> scheduleState(state -> BuildingRate_0(blendTime), 1000));
	}

	protected void BuildingRate_50(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE3E3E073L /*BuildingRate_50*/);
		doAction(894180651L /*BuildingRate_50*/, blendTime, onDoActionEnd -> scheduleState(state -> BuildingRate_50(blendTime), 1000));
	}

	protected void BuildingRate_100(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7779C48CL /*BuildingRate_100*/);
		doAction(2466847146L /*BuildingRate_100*/, blendTime, onDoActionEnd -> scheduleState(state -> BuildingRate_100(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getHpRate() < 50) {
			if (changeState(state -> BuildingRate_0(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getHpRate() < 95) {
			if (changeState(state -> BuildingRate_50(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (changeState(state -> BuildingRate_100(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleChangeBuildingRate(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getBuildingRate() < 50) {
			if (changeState(state -> BuildingRate_0(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getBuildingRate() < 95) {
			if (changeState(state -> BuildingRate_50(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (changeState(state -> BuildingRate_100(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCompleteHouseMesh(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> BuildingRate_100(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
