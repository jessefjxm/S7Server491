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
@IAIName("party_leader_trollboss")
public class Ai_party_leader_trollboss extends CreatureAI {
	public Ai_party_leader_trollboss(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xEB91AA22L /*_IsTest*/, getVariable(0xB510D3CEL /*AI_Test*/));
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getVariable(0xEB91AA22L /*_IsTest*/) == 0) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 5000 && (getTargetCharacterKey(object) == 23102 || getTargetCharacterKey(object) == 23097)).forEach(consumer -> consumer.getAi().WakeUp(getActor(), null));
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xEB91AA22L /*_IsTest*/) == 1) {
				getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 5000 && (getTargetCharacterKey(object) == 23102 || getTargetCharacterKey(object) == 23097)).forEach(consumer -> consumer.getAi().WakeUp_Test(getActor(), null));
			}
			scheduleState(state -> TeleportToOgreKing(blendTime), 1000);
		});
	}

	protected void TeleportToOgreKing(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA85D0605L /*TeleportToOgreKing*/);
		doTeleport(EAIMoveDestType.Absolute, -400988, -106137, 0, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> WakeUpOgre(blendTime), 1000));
	}

	protected void WakeUpOgre(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x24268E95L /*WakeUpOgre*/);
		if (getVariable(0xEB91AA22L /*_IsTest*/) == 0) {
			getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 5000 && (getTargetCharacterKey(object) == 23102 || getTargetCharacterKey(object) == 23097)).forEach(consumer -> consumer.getAi().WakeUp(getActor(), null));
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> {
			if (getVariable(0xEB91AA22L /*_IsTest*/) == 1) {
				getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 5000 && (getTargetCharacterKey(object) == 23102 || getTargetCharacterKey(object) == 23097)).forEach(consumer -> consumer.getAi().WakeUp_Test(getActor(), null));
			}
			scheduleState(state -> Teleport(blendTime), 1000);
		});
	}

	protected void Teleport(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x88C3A72EL /*Teleport*/);
		doTeleport(EAIMoveDestType.Absolute, -564652, 677042, 0, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 1000));
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
}
