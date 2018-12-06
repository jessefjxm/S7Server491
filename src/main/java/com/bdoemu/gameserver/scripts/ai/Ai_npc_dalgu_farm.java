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
@IAIName("npc_dalgu_farm")
public class Ai_npc_dalgu_farm extends CreatureAI {
	public Ai_npc_dalgu_farm(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 100));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		if (0 > 1000000) {
			if (changeState(state -> Progress_5(blendTime)))
				return;
		}
		if (0 > 800000) {
			if (changeState(state -> Progress_4(blendTime)))
				return;
		}
		if (0 > 600000) {
			if (changeState(state -> Progress_3(blendTime)))
				return;
		}
		if (0 > 400000) {
			if (changeState(state -> Progress_2(blendTime)))
				return;
		}
		if (0 > 200000) {
			if (changeState(state -> Progress_1(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 10000));
	}

	protected void Progress_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xABA9BCC9L /*Progress_1*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		if (0 > 1000000) {
			if (changeState(state -> Progress_5(blendTime)))
				return;
		}
		if (0 > 800000) {
			if (changeState(state -> Progress_4(blendTime)))
				return;
		}
		if (0 > 600000) {
			if (changeState(state -> Progress_3(blendTime)))
				return;
		}
		if (0 > 400000) {
			if (changeState(state -> Progress_2(blendTime)))
				return;
		}
		doAction(345528325L /*PROGRESS_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_1(blendTime), 10000));
	}

	protected void Progress_2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xD6F6FE24L /*Progress_2*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		if (0 > 1000000) {
			if (changeState(state -> Progress_5(blendTime)))
				return;
		}
		if (0 > 800000) {
			if (changeState(state -> Progress_4(blendTime)))
				return;
		}
		if (0 > 600000) {
			if (changeState(state -> Progress_3(blendTime)))
				return;
		}
		doAction(1045889831L /*PROGRESS_2*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_2(blendTime), 10000));
	}

	protected void Progress_3(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x8436ECD8L /*Progress_3*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		if (0 > 1000000) {
			if (changeState(state -> Progress_5(blendTime)))
				return;
		}
		if (0 > 800000) {
			if (changeState(state -> Progress_4(blendTime)))
				return;
		}
		doAction(4114963239L /*PROGRESS_3*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_3(blendTime), 10000));
	}

	protected void Progress_4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x30EFC6E8L /*Progress_4*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		if (0 > 1000000) {
			if (changeState(state -> Progress_5(blendTime)))
				return;
		}
		doAction(3800191001L /*PROGRESS_4*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_4(blendTime), 10000));
	}

	protected void Progress_5(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB183D0C3L /*Progress_5*/);
		if(Rnd.getChance(30)) {
			if (changeState(state -> Move_Random(blendTime)))
				return;
		}
		doAction(3527565963L /*PROGRESS_5*/, blendTime, onDoActionEnd -> scheduleState(state -> Progress_5(blendTime), 10000));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
			if (changeState(state -> FailFindPath(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 5000));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		if (0 > 1000000) {
			if (changeState(state -> Progress_5(blendTime)))
				return;
		}
		if (0 > 800000) {
			if (changeState(state -> Progress_4(blendTime)))
				return;
		}
		if (0 > 600000) {
			if (changeState(state -> Progress_3(blendTime)))
				return;
		}
		if (0 > 400000) {
			if (changeState(state -> Progress_2(blendTime)))
				return;
		}
		if (0 > 200000) {
			if (changeState(state -> Progress_1(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 5000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 5000)));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		if (getDistanceToSpawn() > 160) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(847017389L /*WALK*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 50, 150, false, ENaviType.ground, () -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0 && getVariable(0xFDC61BCCL /*_FailFindPathCount*/) >= 3) {
				if (changeState(state -> Wait(0.3)))
					return true;
			}
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath_Logic(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
