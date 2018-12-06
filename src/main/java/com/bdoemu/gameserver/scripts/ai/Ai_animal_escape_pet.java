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
@IAIName("animal_escape_pet")
public class Ai_animal_escape_pet extends CreatureAI {
	public Ai_animal_escape_pet(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x9B7E50C0L /*_RandomMove*/, getVariable(0x64490D98L /*AI_RandomMove*/));
		setVariable(0xD8612E5AL /*_Jump*/, getVariable(0x9A7AC287L /*AI_Jump*/));
		setVariable(0x444DFF4EL /*_isFindPathCompleted*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9F40743BL /*AI_ES_MinDistance*/))) {
			if (changeState(state -> Escape(0.3)))
				return;
		}
		if (getVariable(0x9B7E50C0L /*_RandomMove*/) > 0) {
			if(getCallCount() == 5) {
				if (changeState(state -> Walk_Random(0.3)))
					return;
			}
		}
		if (getDistanceToSpawn() > 5000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Walk_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x9FE5C5L /*Walk_Random*/);
		if (findTarget(EAIFindTargetType.Player, EAIFindType.normal, false, object -> getDistanceToTarget(object) < getVariable(0x9F40743BL /*AI_ES_MinDistance*/))) {
			if (changeState(state -> Escape(0.3)))
				return;
		}
		doAction(2337397317L /*MOVE*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 100, 200, true, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 24) {
			if (changeState(state -> Return_Wait(0.3)))
				return;
		}
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500));
	}

	protected void Return_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x95AE08DEL /*Return_Wait*/);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 2000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			setVariable(0x444DFF4EL /*_isFindPathCompleted*/, isFindPathCompleted());
			setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
			if (getVariable(0x444DFF4EL /*_isFindPathCompleted*/) == 0) {
				if (changeState(state -> FailFindPath(0.3)))
					return true;
			}
			return false;
		}, onExit -> scheduleState(state -> Wait(blendTime), 1000)));
	}

	protected void Lost_Target(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x67695F37L /*Lost_Target*/);
		clearAggro(true);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Escape(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xDCE8DF7DL /*Escape*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > getVariable(0x9F40743BL /*AI_ES_MinDistance*/)) {
			if (changeState(state -> Lost_Target(blendTime)))
				return;
		}
		if (getDistanceToSpawn() > 3000) {
			if (changeState(state -> Move_Return(blendTime)))
				return;
		}
		if (getVariable(0xD8612E5AL /*_Jump*/) == 1) {
			if(Rnd.getChance(80)) {
				if (changeState(state -> Jump_St(blendTime)))
					return;
			}
		}
		doAction(3326922924L /*MOVE_CHASER*/, blendTime, onDoActionEnd -> escape(getVariable(0x8FA1E7B6L /*AI_ES_MaxDistance*/), () -> {
			return false;
		}, onExit -> scheduleState(state -> Escape(blendTime), 1000)));
	}

	protected void Jump_St(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x35F317AL /*Jump_St*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(2956938432L /*JUMP_ST*/, blendTime, onDoActionEnd -> changeState(state -> Escape(blendTime)));
	}

	protected void Die_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x7DC3CFB8L /*Die_Wait*/);
		doAction(425277756L /*DIE_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 3000));
	}

	protected void Suicide_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x2BD8C797L /*Suicide_Die*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTargetInMyArea(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if ((getState() == 0x866C7489L /*Wait*/ || getState() == 0x9FE5C5L /*Walk_Random*/) && target != null && getTargetHp(target) > 0) {
			getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
			if (changeState(state -> Escape(0.3)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerTalkToDie(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die_Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleAtSpawnEndTime(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die_Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
