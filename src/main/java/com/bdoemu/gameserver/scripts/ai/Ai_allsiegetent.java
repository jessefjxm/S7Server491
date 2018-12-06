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
@IAIName("allsiegetent")
public class Ai_allsiegetent extends CreatureAI {
	public Ai_allsiegetent(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		if (getBuildingRate() < 20 && getHpRate() < 25) {
			if (changeState(state -> HpCondition_0(blendTime)))
				return;
		}
		if (getBuildingRate() < 20 && getHpRate() < 50) {
			if (changeState(state -> HpCondition_1(blendTime)))
				return;
		}
		if (getBuildingRate() < 25) {
			if (changeState(state -> BuildingCondition_0(blendTime)))
				return;
		}
		if (getBuildingRate() < 50) {
			if (changeState(state -> BuildingCondition_1(blendTime)))
				return;
		}
		if (getBuildingRate() < 75) {
			if (changeState(state -> BuildingCondition_2(blendTime)))
				return;
		}
		if (getBuildingRate() < 100) {
			if (changeState(state -> BuildingCondition_3(blendTime)))
				return;
		}
		if (changeState(state -> BuildingComplete(blendTime)))
			return;
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x866C7489L /*Wait*/);
		changeState(state -> Wait(blendTime));
	}

	protected void HpCondition_0_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xBC3C869AL /*HpCondition_0_Str*/);
		doAction(3053307871L /*HpCondition_0_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> HpCondition_0(blendTime), 1000));
	}

	protected void HpCondition_0(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x4262FAC8L /*HpCondition_0*/);
		if (getHpRate() > 35 && getState() != 0x23DB8BC4L /*HpCondition_1*/ && getState() != 0xBC3C869AL /*HpCondition_0_Str*/) {
			if (changeState(state -> HpCondition_1(blendTime)))
				return;
		}
		doAction(2944371278L /*HpCondition_0*/, blendTime, onDoActionEnd -> scheduleState(state -> HpCondition_0(blendTime), 1000));
	}

	protected void HpCondition_1_Str(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2D807886L /*HpCondition_1_Str*/);
		doAction(374891535L /*HpCondition_1_STR*/, blendTime, onDoActionEnd -> scheduleState(state -> HpCondition_1(blendTime), 1000));
	}

	protected void HpCondition_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x23DB8BC4L /*HpCondition_1*/);
		if (getHpRate() < 25 && getState() != 0x4262FAC8L /*HpCondition_0*/ && getState() != 0xBC3C869AL /*HpCondition_0_Str*/) {
			if (changeState(state -> HpCondition_0_Str(blendTime)))
				return;
		}
		if (getHpRate() > 60 && getState() == 0x23DB8BC4L /*HpCondition_1*/ && getState() != 0x639578B1L /*BuildingComplete*/) {
			if (changeState(state -> BuildingComplete(blendTime)))
				return;
		}
		doAction(796867815L /*HpCondition_1*/, blendTime, onDoActionEnd -> scheduleState(state -> HpCondition_1(blendTime), 1000));
	}

	protected void BuildingCondition_0(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD463E68FL /*BuildingCondition_0*/);
		doAction(2141413530L /*BuildingRate_0*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void BuildingCondition_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE798A058L /*BuildingCondition_1*/);
		doAction(4057472477L /*BuildingRate_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void BuildingCondition_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA66C163L /*BuildingCondition_2*/);
		doAction(2897567355L /*BuildingRate_2*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void BuildingCondition_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x520245E2L /*BuildingCondition_3*/);
		doAction(3537813483L /*BuildingRate_3*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void BuildingComplete(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x639578B1L /*BuildingComplete*/);
		if (getHpRate() < 25 && getState() != 0x4262FAC8L /*HpCondition_0*/ && getState() != 0xBC3C869AL /*HpCondition_0_Str*/) {
			if (changeState(state -> HpCondition_0_Str(blendTime)))
				return;
		}
		if (getHpRate() < 50 && getState() != 0x23DB8BC4L /*HpCondition_1*/ && getState() != 0x2D807886L /*HpCondition_1_Str*/) {
			if (changeState(state -> HpCondition_1_Str(blendTime)))
				return;
		}
		doAction(2322915412L /*BuildingComplete*/, blendTime, onDoActionEnd -> scheduleState(state -> BuildingComplete(blendTime), 1000));
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
		if (getBuildingRate() < 25) {
			if (changeState(state -> BuildingCondition_0(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getBuildingRate() < 50) {
			if (changeState(state -> BuildingCondition_1(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getBuildingRate() < 75) {
			if (changeState(state -> BuildingCondition_2(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getBuildingRate() < 100) {
			if (changeState(state -> BuildingCondition_3(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getBuildingRate() > 99) {
			if (changeState(state -> BuildingComplete(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCompleteHouseMesh(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> BuildingComplete(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
