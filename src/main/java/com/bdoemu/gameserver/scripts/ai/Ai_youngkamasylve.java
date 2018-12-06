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
@IAIName("youngkamasylve")
public class Ai_youngkamasylve extends CreatureAI {
	public Ai_youngkamasylve(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x20AF4AEAL /*_GrowPoint*/, 0);
		setVariable(0x21E9CB83L /*_GrowValue*/, getVariable(0x3833266AL /*AI_GrowValue*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> GrowProsess(blendTime), 1000));
	}

	protected void Growing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xCFE0FBB0L /*Growing*/);
		setVariable(0x20AF4AEAL /*_GrowPoint*/, getVariable(0x20AF4AEAL /*_GrowPoint*/) + 1);
		scheduleState(state -> GrowProsess(blendTime), 1000);
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void GrowProsess(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xFE84B5A6L /*GrowProsess*/);
		if (getVariable(0x21E9CB83L /*_GrowValue*/) == 1) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) < 501) {
			if (changeState(state -> Progress_1(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 500 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 1001) {
			if (changeState(state -> Progress_2(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 1000 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 1501) {
			if (changeState(state -> Progress_3(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 1500 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 2501) {
			if (changeState(state -> Progress_4(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 2500) {
			if (changeState(state -> Progress_5(blendTime)))
				return;
		}
		scheduleState(state -> GrowProsess(blendTime), 1000);
	}

	protected void Progress_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xABA9BCC9L /*Progress_1*/);
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 500 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 1001) {
			if (changeState(state -> Progress_2(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 1000 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 1501) {
			if (changeState(state -> Progress_3(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 1500 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 2501) {
			if (changeState(state -> Progress_4(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 2500) {
			if (changeState(state -> Progress_5(blendTime)))
				return;
		}
		doAction(345528325L /*PROGRESS_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_1(blendTime), 10000));
	}

	protected void Progress_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD6F6FE24L /*Progress_2*/);
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) < 501) {
			if (changeState(state -> Progress_1(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 1000 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 1501) {
			if (changeState(state -> Progress_3(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 1500 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 2501) {
			if (changeState(state -> Progress_4(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 2500) {
			if (changeState(state -> Progress_5(blendTime)))
				return;
		}
		doAction(1045889831L /*PROGRESS_2*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_2(blendTime), 10000));
	}

	protected void Progress_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8436ECD8L /*Progress_3*/);
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) < 501) {
			if (changeState(state -> Progress_1(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 500 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 1001) {
			if (changeState(state -> Progress_2(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 1500 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 2501) {
			if (changeState(state -> Progress_4(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 2500) {
			if (changeState(state -> Progress_5(blendTime)))
				return;
		}
		doAction(4114963239L /*PROGRESS_3*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_3(blendTime), 10000));
	}

	protected void Progress_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x30EFC6E8L /*Progress_4*/);
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) < 501) {
			if (changeState(state -> Progress_1(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 500 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 1001) {
			if (changeState(state -> Progress_2(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 1000 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 1501) {
			if (changeState(state -> Progress_3(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 2500) {
			if (changeState(state -> Progress_5(blendTime)))
				return;
		}
		doAction(3800191001L /*PROGRESS_4*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_4(blendTime), 10000));
	}

	protected void Progress_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB183D0C3L /*Progress_5*/);
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) < 501) {
			if (changeState(state -> Progress_1(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 500 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 1001) {
			if (changeState(state -> Progress_2(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 1000 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 1501) {
			if (changeState(state -> Progress_3(blendTime)))
				return;
		}
		if (getVariable(0x20AF4AEAL /*_GrowPoint*/) > 1500 && getVariable(0x20AF4AEAL /*_GrowPoint*/) < 2501) {
			if (changeState(state -> Progress_4(blendTime)))
				return;
		}
		getObjects(EAIFindTargetType.Parent, object -> true).forEach(consumer -> consumer.getAi().HandlerSendToEnd(getActor(), null));
		doAction(3527565963L /*PROGRESS_5*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_5(blendTime), 10000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerSendToGrow(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Growing(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
