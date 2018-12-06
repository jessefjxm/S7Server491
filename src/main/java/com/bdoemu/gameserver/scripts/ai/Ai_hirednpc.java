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
@IAIName("hirednpc")
public class Ai_hirednpc extends CreatureAI {
	public Ai_hirednpc(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xF06310B7L /*_stance*/, 0);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 3000));
	}

	protected void Stance(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9A34695AL /*Stance*/);
		if (getVariable(0xF06310B7L /*_stance*/) == 1) {
			if (changeState(state -> Stance_Idle(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 2) {
			if (changeState(state -> Stance_Follow(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) == 3) {
			if (changeState(state -> Stance_Collect_In_Follow(blendTime)))
				return;
		}
		if (getVariable(0x9A34695AL /*stance*/) == 4) {
			if (changeState(state -> Stance_Collect_Arround(blendTime)))
				return;
		}
		doAction(3507285129L /*WORK_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance(blendTime), 100));
	}

	protected void Stance_Idle(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x9C20F410L /*Stance_Idle*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Idle(blendTime), 500));
	}

	protected void Stance_Follow(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xC2EA9387L /*Stance_Follow*/);
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 100, 200, 400, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Follow_Wait(blendTime), 500)));
	}

	protected void Stance_Follow_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD8D35A2L /*Stance_Follow_Wait*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250) {
			if (changeState(state -> Stance_Follow(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Follow_Wait(blendTime), 1000));
	}

	protected void Stance_Collect_In_Follow(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xE8E0FC29L /*Stance_Collect_In_Follow*/);
		if (findTarget(EAIFindTargetType.Collect, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 1000)) {
			if (changeState(state -> Stance_Collect_Go(blendTime)))
				return;
		}
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 100, 200, 400, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Collect_In_Follow_Wait(blendTime), 500)));
	}

	protected void Stance_Collect_In_Follow_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFC461076L /*Stance_Collect_In_Follow_Wait*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250) {
			if (changeState(state -> Stance_Collect_In_Follow(blendTime)))
				return;
		}
		if (findTarget(EAIFindTargetType.Collect, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 1000)) {
			if (changeState(state -> Stance_Collect_Go(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Collect_In_Follow_Wait(blendTime), 1000));
	}

	protected void Stance_Collect_Arround(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x644B0D61L /*Stance_Collect_Arround*/);
		if (findTarget(EAIFindTargetType.Collect, EAIFindType.normal, false, object -> getDistanceToTarget(object) < 1000)) {
			if (changeState(state -> Stance_Collect_Go(blendTime)))
				return;
		}
		if(getCallCount() == 10) {
			if (changeState(state -> Stance_Follow(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Collect_Arround(blendTime), 1000));
	}

	protected void Stance_Collect_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.chase);
		setState(0xC97F84A1L /*Stance_Collect_Go*/);
		if (isTargetLost()) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		doAction(4062859220L /*RUN*/, blendTime, onDoActionEnd -> chase(10, () -> {
			return false;
		}, onExit -> scheduleState(state -> Stance_Collect(blendTime), 1000)));
	}

	protected void Stance_Collect(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB34D4DE9L /*Stance_Collect*/);
		doAction(3170037412L /*WORK_READY*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Collect_Wait(blendTime), 5000));
	}

	protected void Stance_Collect_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x85FA1EDBL /*Stance_Collect_Wait*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xF06310B7L /*_stance*/) == 3 && getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 250) {
			if (changeState(state -> Stance_Collect_In_Follow(blendTime)))
				return;
		}
		if (getVariable(0xF06310B7L /*_stance*/) != 3) {
			if (changeState(state -> Stance(blendTime)))
				return;
		}
		doAction(3507285129L /*WORK_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Stance_Collect(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (changeState(state -> Stance_Collect_Go(0.3)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlePartyInvited2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xF06310B7L /*_stance*/, 2);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleIdle(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xF06310B7L /*_stance*/, 1);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFollow(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xF06310B7L /*_stance*/, 2);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCollectFollow(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xF06310B7L /*_stance*/, 3);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCollectHere(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xF06310B7L /*_stance*/, 4);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xF06310B7L /*_stance*/, 1);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xF06310B7L /*_stance*/, 2);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xF06310B7L /*_stance*/, 3);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStance5(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0xF06310B7L /*_stance*/, 4);
		if (changeState(state -> Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
