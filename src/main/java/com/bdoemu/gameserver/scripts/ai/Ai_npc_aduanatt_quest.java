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
@IAIName("npc_aduanatt_quest")
public class Ai_npc_aduanatt_quest extends CreatureAI {
	public Ai_npc_aduanatt_quest(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x7492188CL /*_FailFindPath*/, 0);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, 0);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 100));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doTeleportToWaypoint("aduanatt_come_05", "monster_patrol", 0, 0, 1, 1);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Come(blendTime), 500));
	}

	protected void Move_Come(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x805E0B50L /*Move_Come*/);
		doAction(707470132L /*MOVE_ING*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "aduanatt_feed", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Peace(blendTime), 1000)));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if (getVariable(0xFDC61BCCL /*_FailFindPathCount*/) > 10) {
			if (changeState(state -> Start_Action(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Peace(blendTime), 1000));
	}

	protected void Wait_Peace(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xFEF1A5A8L /*Wait_Peace*/);
		doAction(3722897573L /*WAIT_PEACE*/, blendTime, onDoActionEnd -> scheduleState(state -> Roar(blendTime), 1000));
	}

	protected void Roar(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x60186BFFL /*Roar*/);
		doAction(133347576L /*ROAR*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Away(blendTime), 1000));
	}

	protected void Move_Away(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x48695A07L /*Move_Away*/);
		doAction(1620528238L /*MOVE_ACCELERATE*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "aduanatt_runaway", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Jump_Away(blendTime), 100)));
	}

	protected void Jump_Away(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x38365DE6L /*Jump_Away*/);
		doAction(337344561L /*JUMP_START*/, blendTime, onDoActionEnd -> moveToWaypoint("monster_patrol", "aduanatt_flyhigh", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Hide(blendTime), 10000)));
	}

	protected void Hide(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6D61FF50L /*Hide*/);
		doAction(1096688925L /*HIDE*/, blendTime, onDoActionEnd -> scheduleState(state -> Dead(blendTime), 3000));
	}

	protected void Dead(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x89495137L /*Dead*/);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Dead(blendTime), 3000));
	}

	protected void FailFindPath_Logic(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0x43584A24L /*FailFindPath_Logic*/);
		setVariable(0xFDC61BCCL /*_FailFindPathCount*/, getVariable(0xFDC61BCCL /*_FailFindPathCount*/) + 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
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
		return EAiHandlerResult.BYPASS;
	}
}
