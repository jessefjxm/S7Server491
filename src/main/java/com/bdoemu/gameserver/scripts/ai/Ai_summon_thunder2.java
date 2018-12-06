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
@IAIName("summon_thunder2")
public class Ai_summon_thunder2 extends CreatureAI {
	public Ai_summon_thunder2(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		useSkill(40436, 1, true, EAIFindTargetType.Player, object -> true);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> CallingThunder(blendTime), 500 + Rnd.get(-0,0)));
	}

	protected void CallingThunder(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0xD4BCE1E5L /*CallingThunder*/);
		if (isTargetLost()) {
			if (changeState(state -> CallingTreasure(blendTime)))
				return;
		}
		doAction(2952699266L /*CALLING_THUNDER*/, blendTime, onDoActionEnd -> changeState(state -> CallingTreasure(blendTime)));
	}

	protected void CallingTreasure(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x906AC08AL /*CallingTreasure*/);
		if (isTargetLost()) {
			if (changeState(state -> MoveThunder(blendTime)))
				return;
		}
		doAction(1737880709L /*CALLING_TREASURE*/, blendTime, onDoActionEnd -> changeState(state -> MoveThunder(blendTime)));
	}

	protected void MoveThunder(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x99AA27FFL /*MoveThunder*/);
		doTeleportToWaypoint("TombOfThunder", "teleport", 0, 0, 1, 1);
		changeState(state -> UnSummon(blendTime));
	}

	protected void UnSummon(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0xCC02DCCFL /*UnSummon*/);
		doAction(1340753835L /*UNSUMMON*/, blendTime, onDoActionEnd -> scheduleState(state -> UnSummon(blendTime), 100));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}
}
