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
@IAIName("hirednpc_in_dialog")
public class Ai_hirednpc_in_dialog extends CreatureAI {
	public Ai_hirednpc_in_dialog(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xA7F32062L /*_isMessureMan*/, getVariable(0x6D3400B9L /*AI_isMeasureMan*/));
		setVariable(0x9B76844DL /*_stuffIndex*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Idle(blendTime), 3500));
	}

	protected void Stance_Idle(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9C20F410L /*Stance_Idle*/);
		if (getVariable(0xA7F32062L /*_isMessureMan*/) == 1) {
			if (changeState(state -> MeasureMan_Idle(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Idle1(blendTime), 4500));
	}

	protected void Stance_Idle1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE06F2A5EL /*Stance_Idle1*/);
		if (getVariable(0xA7F32062L /*_isMessureMan*/) == 1) {
			if (changeState(state -> MeasureMan_Idle(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Idle(blendTime), 4500));
	}

	protected void Order_to_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF1661870L /*Order_to_Go*/);
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderPosition, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Load_Stuff(blendTime), 1000)));
	}

	protected void Load_Stuff(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA55244FEL /*Load_Stuff*/);
		doAction(893097192L /*LOAD_ON*/, blendTime, onDoActionEnd -> scheduleState(state -> Load_Wait(blendTime), 5000));
	}

	protected void Load_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8A14C5C0L /*Load_Wait*/);
		doAction(4048736115L /*LOAD_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Load_Wait(blendTime), 4000));
	}

	protected void Load_Move(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x5A4589EBL /*Load_Move*/);
		doAction(387255725L /*LOAD_MOVE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderPosition, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Load_Off(blendTime), 1000)));
	}

	protected void Load_Off(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x38BF4642L /*Load_Off*/);
		getObjects(EAIFindTargetType.Interaction, object -> true).forEach(consumer -> consumer.getAi().handler_Measure(getActor(), null));
		doAction(3102563682L /*LOAD_OFF*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Idle(blendTime), 2000));
	}

	protected void Load_Stuff_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD064532CL /*Load_Stuff_1*/);
		doAction(1658944881L /*STUFF_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Load_Stuff_1_Load(blendTime), 1000));
	}

	protected void Load_Stuff_1_Load(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E8EED70L /*Load_Stuff_1_Load*/);
		doAction(3213742445L /*STUFF_1_LOAD*/, blendTime, onDoActionEnd -> scheduleState(state -> Load_Stuff_Wait(blendTime), 100));
	}

	protected void Load_Stuff_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5824A430L /*Load_Stuff_2*/);
		doAction(2313912612L /*STUFF_2*/, blendTime, onDoActionEnd -> scheduleState(state -> Load_Stuff_2_Load(blendTime), 1000));
	}

	protected void Load_Stuff_2_Load(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3D6E6AEFL /*Load_Stuff_2_Load*/);
		doAction(3756438858L /*STUFF_2_LOAD*/, blendTime, onDoActionEnd -> scheduleState(state -> Load_Stuff_Wait(blendTime), 100));
	}

	protected void Load_Stuff_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x52D96637L /*Load_Stuff_3*/);
		doAction(1650006225L /*STUFF_3*/, blendTime, onDoActionEnd -> scheduleState(state -> Load_Stuff_3_Load(blendTime), 1000));
	}

	protected void Load_Stuff_3_Load(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7F4F2725L /*Load_Stuff_3_Load*/);
		doAction(4248469857L /*STUFF_3_LOAD*/, blendTime, onDoActionEnd -> scheduleState(state -> Load_Stuff_Wait(blendTime), 100));
	}

	protected void Load_Stuff_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x262F4E3BL /*Load_Stuff_Wait*/);
		doAction(1543659967L /*STUFF_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Load_Stuff_Wait(blendTime), 1000));
	}

	protected void Load_Stuff_Reset(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD76DCAB1L /*Load_Stuff_Reset*/);
		doAction(2846600523L /*STUFF_RESET*/, blendTime, onDoActionEnd -> scheduleState(state -> MeasureMan_Idle(blendTime), 1000));
	}

	protected void ComeBack(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9048E24EL /*ComeBack*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance_Idle(blendTime)))
				return;
		}
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.SenderPosition, 0, 0, 0, 0, 1, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Idle(blendTime), 1000)));
	}

	protected void MeasureMan_Idle(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD2E3090BL /*MeasureMan_Idle*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> MeasureMan_Idle1(blendTime), 4500));
	}

	protected void MeasureMan_Idle1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xE7B99B67L /*MeasureMan_Idle1*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> MeasureMan_Idle(blendTime), 4500));
	}

	protected void MeasureMan_Measure(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x1994AFBFL /*MeasureMan_Measure*/);
		setVariable(0x9B76844DL /*_stuffIndex*/, getVariable(0x9B76844DL /*_stuffIndex*/) + 1);
		if (getVariable(0x9B76844DL /*_stuffIndex*/) == 1) {
			getObjects(EAIFindTargetType.Interaction, object -> true).forEach(consumer -> consumer.getAi().handler_ScaleStuff_1(getActor(), null));
		}
		if (getVariable(0x9B76844DL /*_stuffIndex*/) == 2) {
			getObjects(EAIFindTargetType.Interaction, object -> true).forEach(consumer -> consumer.getAi().handler_ScaleStuff_2(getActor(), null));
		}
		if (getVariable(0x9B76844DL /*_stuffIndex*/) == 3) {
			getObjects(EAIFindTargetType.Interaction, object -> true).forEach(consumer -> consumer.getAi().handler_ScaleStuff_3(getActor(), null));
		}
		doAction(3892454449L /*TALK*/, blendTime, onDoActionEnd -> scheduleState(state -> MeasureMan_Idle(blendTime), 4500));
	}

	protected void MeasureMan_Reset(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x1B4D9021L /*MeasureMan_Reset*/);
		getObjects(EAIFindTargetType.Interaction, object -> true).forEach(consumer -> consumer.getAi().handler_Scale_Reset(getActor(), null));
		scheduleState(state -> MeasureMan_Idle1(blendTime), 4500);
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handler_OrderToGo(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Order_to_Go(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handler_LoadStuff(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Load_Stuff(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handler_LoadMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Load_Move(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handler_LoadOff(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Load_Off(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handler_ComeBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> ComeBack(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handler_ScaleStuff_1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Load_Stuff_1(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handler_ScaleStuff_2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Load_Stuff_2(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handler_ScaleStuff_3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Load_Stuff_3(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handler_Reset(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x9B76844DL /*_stuffIndex*/, 0);
		if (changeState(state -> MeasureMan_Reset(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handler_Measure(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> MeasureMan_Measure(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult handler_Scale_Reset(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Load_Stuff_Reset(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
