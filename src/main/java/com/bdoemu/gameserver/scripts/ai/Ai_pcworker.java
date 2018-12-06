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
@IAIName("pcworker")
public class Ai_pcworker extends CreatureAI {
	public Ai_pcworker(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0x9C1A0E76L /*_fear*/, 0);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void ReleaseState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xCBE083E6L /*ReleaseState*/);
		doAction(2524986171L /*BT_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void StanceCheck(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xC7700F3AL /*StanceCheck*/);
		if (getVariable(0x9C1A0E76L /*_fear*/) == 0) {
			if (changeState(state -> ReleaseState(blendTime)))
				return;
		}
		if (getVariable(0x9C1A0E76L /*_fear*/) == 1) {
			if (changeState(state -> Damage_Fear(blendTime)))
				return;
		}
		changeState(state -> TerminateState(blendTime));
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000 + Rnd.get(-500,500)));
	}

	protected void AAAAA(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x3DBB6A61L /*AAAAA*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(3429420047L /*BT_ATTACK_FIST_2*/, blendTime, onDoActionEnd -> changeState(state -> AAAAA(blendTime)));
	}

	protected void KnockBack_State(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x3E51A4D1L /*KnockBack_State*/);
		doAction(4059115659L /*KNOCKBACK*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 100));
	}

	protected void KnockDown_State(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xAB2D9772L /*KnockDown_State*/);
		doAction(1872990688L /*KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 100));
	}

	protected void Damage_Rigid(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6A4B0B1DL /*Damage_Rigid*/);
		doAction(4101779004L /*DAMAGE_RIGID*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 1000));
	}

	protected void Damage_Capture(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5374AB60L /*Damage_Capture*/);
		doAction(3486436380L /*DAMAGE_CAPTURE*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 10000));
	}

	protected void Damage_Release(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x82D0AC8EL /*Damage_Release*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 3000));
	}

	protected void Damage_Bound(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x119675D3L /*Damage_Bound*/);
		doAction(1109738762L /*DAMAGE_BOUND*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 3000));
	}

	protected void Damage_Stun(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x3FB3341CL /*Damage_Stun*/);
		doAction(1092723167L /*DAMAGE_STUN*/, blendTime, onDoActionEnd -> scheduleState(state -> StanceCheck(blendTime), 3000));
	}

	protected void Damage_Fear(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xBF1D8728L /*Damage_Fear*/);
		doAction(1002473142L /*DAMAGE_FEAR*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 0, 1000, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Damage_Fear(blendTime), 100)));
	}

	protected void Wait_Robby(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x191F53ECL /*Wait_Robby*/);
		doAction(2642664867L /*SELECT_IDLE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Robby2(blendTime), 15000));
	}

	protected void Wait_Robby2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x6B78390BL /*Wait_Robby2*/);
		doAction(2642664867L /*SELECT_IDLE*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_Robby(blendTime), 15000));
	}

	protected void Move_Random(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x8377635AL /*Move_Random*/);
		doAction(2758252219L /*WALKING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.Random, 0, 0, 0, 200, 300, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Wait_Robby(blendTime), 10000)));
	}

	protected void Play_Flute_Go(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF8E6D1AAL /*Play_Flute_Go*/);
		doAction(380055388L /*PLAY_FLUTE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("instance_waypoint", "w133_30", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Play_Flute_End(blendTime), 1000)));
	}

	protected void Play_Flute_Go_Short(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x2BC7ACB0L /*Play_Flute_Go_Short*/);
		doAction(380055388L /*PLAY_FLUTE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("instance_waypoint", "w133_50", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Play_Flute_End(blendTime), 1000)));
	}

	protected void Play_Flute_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x2E73BC37L /*Play_Flute_End*/);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 700).forEach(consumer -> consumer.getAi().Suicide(getActor(), null));
		doAction(1075905676L /*PLAY_FLUTE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Play_Flute_Go_234(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x18362A2EL /*Play_Flute_Go_234*/);
		doAction(380055388L /*PLAY_FLUTE_WALK*/, blendTime, onDoActionEnd -> moveToWaypoint("instance_waypoint", "w234_30", ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> Play_Flute_End_234(blendTime), 1000)));
	}

	protected void Play_Flute_End_234(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x134EC0AEL /*Play_Flute_End_234*/);
		getObjects(EAIFindTargetType.Monster, object -> getDistanceToTarget(object) < 700).forEach(consumer -> consumer.getAi().Suicide(getActor(), null));
		doAction(1075905676L /*PLAY_FLUTE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void Play_Flute_Go_301(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAB5C9AF7L /*Play_Flute_Go_301*/);
		doAction(4193103538L /*SOCIAL_FLUTE_WALK_LOW*/, blendTime, onDoActionEnd -> scheduleState(state -> Play_Flute_Go_301(blendTime), 1000));
	}

	protected void Play_Flute_End_301(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x5E273D7L /*Play_Flute_End_301*/);
		doAction(1075905676L /*PLAY_FLUTE_END*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	protected void GunFire_Ready(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x96A7CEFBL /*GunFire_Ready*/);
		doAction(3411802288L /*ID_GUN_START*/, blendTime, onDoActionEnd -> scheduleState(state -> GunFire_Wait(blendTime), 3000));
	}

	protected void GunFire_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xA05DD58DL /*GunFire_Wait*/);
		doAction(2106596449L /*ID_GUN_WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 9000));
	}

	protected void GunFire_End(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881FFA8FL /*GunFire_End*/);
		doAction(4271580473L /*ID_GUN_END*/, blendTime, onDoActionEnd -> scheduleState(state -> TerminateState(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockBack(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> KnockBack_State(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> KnockDown_State(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCapture(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Capture(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRigid(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Rigid(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleBound(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Bound(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleStun(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Damage_Stun(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFeared(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x9C1A0E76L /*_fear*/, 1);
		if (changeState(state -> StanceCheck(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleFearReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x9C1A0E76L /*_fear*/, 0);
		if (changeState(state -> StanceCheck(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleLobby(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Wait_Robby(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRestrictReleased(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> TerminateState(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleInitialize(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> InitialState(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Play_Flute_Go_Short(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Play_Flute_Go_234(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleEvent3(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> GunFire_Ready(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandlerDestinationMove(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> GunFire_End(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOrderSkill1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Play_Flute_Go_301(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOrderSkill2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Play_Flute_End_301(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}
}
