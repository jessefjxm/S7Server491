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
@IAIName("guild_seizetent")
public class Ai_guild_seizetent extends CreatureAI {
	public Ai_guild_seizetent(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x3F487035L /*_HP*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_100(blendTime), 1000));
	}

	protected void Wait_100(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x55AB2B73L /*Wait_100*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0x3F487035L /*_HP*/) <= 60) {
			if (changeState(state -> Wait_60_Destruction(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 20) {
			if (changeState(state -> Wait_20_Destruction(blendTime)))
				return;
		}
		doAction(2593216165L /*WAIT_100*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_100(blendTime), 1000));
	}

	protected void Wait_60_Destruction(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x626D6EE0L /*Wait_60_Destruction*/);
		doAction(1697273633L /*WAIT_60_DESTRUCTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_60(blendTime), 1000));
	}

	protected void Wait_60(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCF57BB03L /*Wait_60*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0x3F487035L /*_HP*/) >= 75) {
			if (changeState(state -> Wait_100(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) <= 20) {
			if (changeState(state -> Wait_20_Destruction(blendTime)))
				return;
		}
		doAction(2518206426L /*WAIT_60*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_60(blendTime), 1000));
	}

	protected void Wait_20_Destruction(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1AEF235CL /*Wait_20_Destruction*/);
		doAction(1388346376L /*WAIT_20_DESTRUCTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_20(blendTime), 1000));
	}

	protected void Wait_20(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3A5CCBCEL /*Wait_20*/);
		setVariable(0x3F487035L /*_HP*/, getHpRate());
		if (getVariable(0x3F487035L /*_HP*/) >= 75) {
			if (changeState(state -> Wait_100(blendTime)))
				return;
		}
		if (getVariable(0x3F487035L /*_HP*/) >= 35) {
			if (changeState(state -> Wait_60(blendTime)))
				return;
		}
		doAction(3708323384L /*WAIT_20*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_20(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleChangeBuildingRate(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getBuildingRate() < 50) {
			if (changeState(state -> Wait_20(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getBuildingRate() >= 50 && getBuildingRate() < 100) {
			if (changeState(state -> Wait_60(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getBuildingRate() >= 100) {
			if (changeState(state -> Wait_100(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCompleteHouseMesh(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Wait_100(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
