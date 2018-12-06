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
@IAIName("donkey_quest")
public class Ai_donkey_quest extends CreatureAI {
	public Ai_donkey_quest(Creature actor, Map<Long, Integer> aiVariables) {
		super(actor, aiVariables);
	}

	protected void InitialState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xAE5FEAC2L /*InitialState*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, 0);
		setVariable(0x42A6EC2DL /*_isRunning*/, 0);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 1000));
	}

	protected void Start_Action(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x881B0A76L /*Start_Action*/);
		doAction(3910697700L /*START_ACTION*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 1000));
	}

	protected void TerminateState(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.none);
		setState(0xF74207F6L /*TerminateState*/);
		// Prevent recursion;
	}

	protected void Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x866C7489L /*Wait*/);
		if(getCallCount() == 50) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		if (target == null) {
			if (changeState(state -> Move_Return(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Wait_1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x96AB8779L /*Wait_1*/);
		if(getCallCount() == 10) {
			if (changeState(state -> Wait(0.3)))
				return;
		}
		doAction(1569894142L /*WAIT_1*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait_1(blendTime), 1000));
	}

	protected void FailFindPath(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x80CB99B0L /*FailFindPath*/);
		clearAggro(true);
		doTeleport(EAIMoveDestType.Random, 0, 0, 1, 1);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Start_Action(blendTime), 2000));
	}

	protected void Move_Return(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.return_);
		setState(0xD61E465EL /*Move_Return*/);
		clearAggro(true);
		doAction(1793358191L /*MOVE_LV2_ING*/, blendTime, onDoActionEnd -> recoveryAndReturn(() -> {
			return false;
		}, onExit -> scheduleState(state -> Start_Action(blendTime), 3000)));
	}

	protected void Trigger_Check(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xB699DBCDL /*Trigger_Check*/);
		if (getActor().isVehicle() && getActor().getOwner() != null) {
			if (changeState(state -> Suicide_Die(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 3000));
	}

	protected void Suicide_Die_Wait(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x64526570L /*Suicide_Die_Wait*/);
		if (getActor().isVehicle() && getActor().getOwner() != null) {
			if (changeState(state -> Suicide_Die(0.3)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 3000));
	}

	protected void Suicide_Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x2BD8C797L /*Suicide_Die*/);
		doAction(1426790432L /*END_DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Suicide_Die(blendTime), 3000));
	}

	protected void Die(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.dead);
		setState(0x90DBFD38L /*Die*/);
		clearAggro(true);
		doAction(2544805566L /*DIE*/, blendTime, onDoActionEnd -> scheduleState(state -> Die(blendTime), 3000));
	}

	protected void ChaseOwner_Stance(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x24359F08L /*ChaseOwner_Stance*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 350) {
			if (changeState(state -> ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> ChaseOwner_Stance(blendTime), 1000));
	}

	protected void ChaseOwner_MoveLv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x643D5C4CL /*ChaseOwner_MoveLv1*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 200) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 800) {
			if (changeState(state -> ChaseOwner_MoveLv2(blendTime)))
				return;
		}
		if(getCallCount() == 10) {
			if (changeState(state -> MovingStop_Lv1(blendTime)))
				return;
		}
		doAction(3283123083L /*MOVE_LV1_ING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> ChaseOwner_MoveLv1(blendTime), 500)));
	}

	protected void ChaseOwner_MoveLv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0xF1003A81L /*ChaseOwner_MoveLv2*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 450) {
			if (changeState(state -> ChaseOwner_MoveLv1(blendTime)))
				return;
		}
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) > 1100) {
			if (changeState(state -> ChaseOwner_MoveLv4(blendTime)))
				return;
		}
		if(getCallCount() == 10) {
			if (changeState(state -> MovingStop_Lv2(blendTime)))
				return;
		}
		doAction(1793358191L /*MOVE_LV2_ING*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> ChaseOwner_MoveLv2(blendTime), 300)));
	}

	protected void ChaseOwner_MoveLv4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.walk);
		setState(0x6A17EDF0L /*ChaseOwner_MoveLv4*/);
		setVariable(0xCBEEF8C7L /*_ownerDistance*/, getDistanceToOwner());
		if (getVariable(0xCBEEF8C7L /*_ownerDistance*/) < 800) {
			if (changeState(state -> ChaseOwner_Stop(blendTime)))
				return;
		}
		doAction(2609545190L /*MOVE_LV4_HALF*/, blendTime, onDoActionEnd -> move(EAIMoveDestType.OwnerPosition, 0, 0, 0, 0, 0, false, ENaviType.ground, () -> {
			return false;
		}, onExit -> scheduleState(state -> ChaseOwner_MoveLv4(blendTime), 100)));
	}

	protected void ChaseOwner_Stop(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0xC795B6F3L /*ChaseOwner_Stop*/);
		doAction(799326965L /*MOVE_SLOW_STOP_SHORT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1500));
	}

	protected void MovingStop_Back(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x41B9C8BCL /*MovingStop_Back*/);
		doAction(2514775444L /*WAIT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void MovingStop_Lv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x13E9B7F1L /*MovingStop_Lv1*/);
		doAction(4014228058L /*AI_MOVE_LV1_ING*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void MovingStop_Lv2(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.action);
		setState(0x5828E0F4L /*MovingStop_Lv2*/);
		doAction(4213640064L /*AI_MOVE_LV2_ING*/, blendTime, onDoActionEnd -> changeState(state -> Wait(blendTime)));
	}

	protected void MovingStop_Lv4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.idle);
		setState(0x41DEFE21L /*MovingStop_Lv4*/);
		doAction(799326965L /*MOVE_SLOW_STOP_SHORT*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Escape_Lv4(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0x184316BBL /*Escape_Lv4*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 1200) {
			if (changeState(state -> MovingStop_Lv4(blendTime)))
				return;
		}
		doAction(2609545190L /*MOVE_LV4_HALF*/, blendTime, onDoActionEnd -> escape(2500, () -> {
			return false;
		}, onExit -> scheduleState(state -> Escape_Lv4(blendTime), 500)));
	}

	protected void Escape_Lv1(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.escape);
		setState(0xB21D7758L /*Escape_Lv1*/);
		if (isTargetLost()) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		if (target != null && getDistanceToTarget(target) > 500) {
			if (changeState(state -> Wait(blendTime)))
				return;
		}
		doAction(3283123083L /*MOVE_LV1_ING*/, blendTime, onDoActionEnd -> escape(2500, () -> {
			return false;
		}, onExit -> scheduleState(state -> Escape_Lv1(blendTime), 500)));
	}

	protected void Damage_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x69E1FC3AL /*Damage_KnockDown*/);
		doAction(1872990688L /*KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void Damage_KnockDown_At_Moving(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0x25EE997L /*Damage_KnockDown_At_Moving*/);
		doAction(2575501718L /*KNOCKDOWN_AT_MOVING*/, blendTime, onDoActionEnd -> scheduleState(state -> Wait(blendTime), 1000));
	}

	protected void UnderWater_KnockDown(double blendTime) {
		Creature target = getTarget();
		setBehavior(EAIBehavior.knockback);
		setState(0xBF7E038EL /*UnderWater_KnockDown*/);
		doAction(1872990688L /*KNOCKDOWN*/, blendTime, onDoActionEnd -> scheduleState(state -> Move_Return(blendTime), 1000));
	}

	@Override
	public EAiHandlerResult HandleTakeDamage(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (isSelfRidable()) {
			if(Rnd.getChance(35)) {
				if (changeState(state -> Escape_Lv1(0.1)))
					return EAiHandlerResult.CHANGE_STATE;
			}
		}
		if (getState() == 0xB21D7758L /*Escape_Lv1*/ && isSelfRidable()) {
			if (changeState(state -> Escape_Lv4(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleOnResetAI(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		getActor().getAggroList().addCreature(sender.getAggroList().getTarget());
		if (changeState(state -> TerminateState(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleDead(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Die(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRideEnd(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> Wait(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleMoveInWater(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> UnderWater_KnockDown(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleKnockDown(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (getVariable(0x42A6EC2DL /*_isRunning*/) == 1) {
			if (changeState(state -> Damage_KnockDown_At_Moving(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		if (getVariable(0x42A6EC2DL /*_isRunning*/) != 1) {
			if (changeState(state -> Damage_KnockDown(0.1)))
				return EAiHandlerResult.CHANGE_STATE;
		}
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleWhistle(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> ChaseOwner_Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCallSummon(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> ChaseOwner_Stance(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRideOff_Back(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> MovingStop_Back(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRideOff_Lv1(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> MovingStop_Lv1(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRideOff_Lv2(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> MovingStop_Lv2(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleRideOff_Lv4(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		if (changeState(state -> MovingStop_Lv4(0.1)))
			return EAiHandlerResult.CHANGE_STATE;
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCheckStatus_Run(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x42A6EC2DL /*_isRunning*/, 1);
		return EAiHandlerResult.BYPASS;
	}

	@Override
	public EAiHandlerResult HandleCheckStatus_Idle(Creature sender, Integer[] eventData) {
		_sender = sender;
		Creature target = sender;
		setVariable(0x42A6EC2DL /*_isRunning*/, 0);
		return EAiHandlerResult.BYPASS;
	}
}
